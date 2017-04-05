package annaikanyouzoo.com.outerspacemanager.outerspacemanager.fragments;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import annaikanyouzoo.com.outerspacemanager.outerspacemanager.OuterSpaceService;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.R;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.activities.BuildingActivity;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.array_adapters.BuildingsArrayAdapter;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.dao.BuildingDAO;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.db.OuterSpaceManagerDB;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.listeners.OnBuyListener;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.Building;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.User;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.response.GetBuildingsResponse;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.response.NewBuildingResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ShopBuildingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopBuildingsFragment extends Fragment {

    private View view;

    // the fragment initialization parameters
    public User user;
    public String token;

    public static final String PREFS_NAME = "Token";
    private static SharedPreferences settings;
    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://outer-space-manager.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public OuterSpaceService service = retrofit.create(OuterSpaceService.class);

    // Database Helper initiation
    private final OuterSpaceManagerDB db = new OuterSpaceManagerDB(getActivity());

    // Buildings related DAO, List, RecyclerView, RecyclerView Adapter
    private BuildingDAO buildingDAO;
    private static List<Building> buildings;
    private RecyclerView rvBuildings;
    private RecyclerView.Adapter aaBuildings;
    private RecyclerView.LayoutManager rvLayoutManager = new LinearLayoutManager(getActivity());
    private OnBuyListener buyListener;

    static Building building;

    //private OnFragmentInteractionListener mListener;

    public ShopBuildingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user user.
     * @param token token.
     * @return A new instance of fragment ShopBuildingsFragment.
     */
    public static ShopBuildingsFragment newInstance(User user, String token) {
        ShopBuildingsFragment fragment = new ShopBuildingsFragment();
        fragment.user = user;
        fragment.token = token;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_shop_buildings, container, false);
        this.view = v;

        this.buildings = this.getBuildings();

        // Database Access Object initiation
        this.buildingDAO = new BuildingDAO(getActivity());

        // récupération de la recycler view à laquelle on va ajouter les lignes repréentant chque bâtiment
        this.rvBuildings = (RecyclerView) v.findViewById(R.id.rvBuildings);

        // use a linear layout manager
        rvLayoutManager = new LinearLayoutManager(getActivity());
        rvBuildings.setLayoutManager(rvLayoutManager);

        buyListener = new OnBuyListener() {
            @Override
            public void onBuy(View v, int position) {
                buyBuilding(position);
            }
        };
        Log.d("App", "nb buildings"+this.buildings.size());
        aaBuildings = new BuildingsArrayAdapter(getActivity(), this.buildings, buyListener);
        // on remplit la listView avec notre liste de vaisseaux
        rvBuildings.setAdapter(aaBuildings);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        buyListener = new OnBuyListener() {
            @Override
            public void onBuy(View v, int position) {
                buyBuilding(position);
            }
        };
        aaBuildings = new BuildingsArrayAdapter(getActivity(), buildings, buyListener);
        // on remplit la listView avec notre liste de vaisseaux
        rvBuildings.setAdapter(aaBuildings);

    }

    public List<Building> getBuildings(){

        // You can create a final container object outside of your callback and then pass in your value to it from inside the callback.
        final BlockingQueue<List<Building>> blockingQueue = new ArrayBlockingQueue<>(1);

        // On récupère la liste des bâtiments
        Call<GetBuildingsResponse> request = service.getBuildings(token);
        request.enqueue(new Callback<GetBuildingsResponse>() {
            @Override
            public void onResponse(Call<GetBuildingsResponse> call, Response<GetBuildingsResponse> response) {
                if(response.isSuccessful()){
                    Log.d("App", "Récupération des bâtiments.");
                    List<Building> buildings = response.body().getBuildings();
                    blockingQueue.add(buildings);
                    Log.d("App", "Nb de bâtiments = " + buildings.size());
                } else {
                    Log.d("App", "onFailure: Echec de la récupération des bâtiments 2");
                    // on affiche le feedback comme quoi la création du compte a échoué
                    Toast.makeText(getActivity(), "Echec de la récupération des bâtiments 2", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetBuildingsResponse> call, Throwable t) {
                Log.d("App", "onFailure: Echec de la récupération des bâtiments 1");
                Log.d("App", "onFailure: "+t);
                // on affiche le feedback comme quoi la récupération des bâtiments a échoué
                Toast.makeText(getActivity(),"Echec de la récupération des bâtiments 1",Toast.LENGTH_SHORT).show();
            }
        });

        List<Building> b = new ArrayList<Building>();
        try{
            b = blockingQueue.take(); // this will block your thread
        }
        catch(InterruptedException e)
        {
            Toast.makeText(getActivity(),"Echec de la récupération des bâtiments",Toast.LENGTH_SHORT).show();
        }
        return b;

    }

    public void buyBuilding(int position) {

        building =  this.buildings.get(position);

        // display failure alert if player doesn't have enough resources
        // si le joueur n'a pas assez de ressources on affiche une alerte pour l'en informer
        if(building.getGasCost() > this.user.getGas() || building.getMineralCost() > this.user.getMinerals()){
            new AlertDialog.Builder(getActivity())
                    .setTitle("Erreur")
                    .setMessage("Vous n'avez pas assez de ressources!\n\n" +
                            "Coût en gaz : " + building.getGasCost() + "\n" +
                            "Coût en minéraux : " + building.getMineralCost() + "\n\n" +
                            "Or vous n'avez que " + this.user.getGas() + " gaz et " + this.user.getMinerals() + "  minéraux.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //do nothing
                        }
                    })
                    .show();
        }else{
            new AlertDialog.Builder(getActivity())
                    .setTitle("Construire un bâtiment")
                    .setMessage("Voulez-vous construire ce bâtiment?\n\n" +
                            "Coût en gaz : " + building.getGasCost() + "\n" +
                            "Coût en minéraux : " + building.getMineralCost() + "\n\n" +
                            "Il vous restera " + (this.user.getGas()-building.getGasCost()) + " gaz et " + (this.user.getMinerals()-building.getMineralCost()) + " minéraux.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            // building creation
                            Call<NewBuildingResponse> request = service.newBuilding(token, building.getBuildingId());
                            request.enqueue(new Callback<NewBuildingResponse>() {

                                @Override
                                public void onResponse(Call<NewBuildingResponse> call, Response<NewBuildingResponse> response) {
                                    if(response.isSuccessful()){
                                        Log.d("App", "Bâtiment en cours de construction!");
                                        Toast.makeText(getActivity(),"Bâtiment en cours de construction!",Toast.LENGTH_SHORT).show();
                                        // Here, thisActivity is the current activity
                                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                                        {
                                            // Should we show an explanation? (to avoid a never ask again response)
                                            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                                // Show an explanation to the user *asynchronously* -- don't block
                                                // this thread waiting for the user's response! After the user
                                                // sees the explanation, try again to request the permission.
                                            } else {
                                                // No explanation needed, we can request the permission.
                                                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 4444);
                                            }
                                        } else {
                                            saveBuilding(building);
                                        }
                                    } else {
                                        Log.d("App", "Echec de la création du bâtiment 2");
                                        // display building's construction failure feedback
                                        Toast.makeText(getActivity(), "Erreur " + response.code() + " : " + response.message(),Toast.LENGTH_SHORT).show();
                                        Toast.makeText(getActivity(), "Echec de la création du bâtiment 2",Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<NewBuildingResponse> call, Throwable t) {
                                    Log.d("App", "onFailure: Echec de la création du bâtiment 1");
                                    // on affiche le feedback comme quoi la création du compte a échoué
                                    Toast.makeText(getActivity(),"Echec de la création du bâtiment 1",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .show();
        }
    }

    public void saveBuilding(Building building){

        // open database connection
        buildingDAO.open();

        // create timestamp to set constructionstart
        Long constructionStart = System.currentTimeMillis()/1000;

        // calculate constructionEnd
        Long constructionEnd =  constructionStart + building.getTimeToBuild();

        // insert building in database
        buildingDAO.addBuildingRunning(building, constructionStart, constructionEnd);

        // Don't forget to close database connection
        buildingDAO.close();
    }
}
