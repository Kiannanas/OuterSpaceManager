package annaikanyouzoo.com.outerspacemanager.outerspacemanager.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import annaikanyouzoo.com.outerspacemanager.outerspacemanager.array_adapters.BuildingsArrayAdapter;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.OuterSpaceService;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.R;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.array_adapters.ShipsArrayAdapter;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.array_adapters.UsersArrayAdapter;
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

public class BuildingActivity extends AppCompatActivity {

    // Database Helper
    OuterSpaceManagerDB db;
    // Database Access Object
    BuildingDAO buildingDAO;

    private List<Building> buildings;

    private RecyclerView rvBuildings;
    private RecyclerView.LayoutManager rvLayoutManager;
    private RecyclerView.Adapter aaBuildings;
    private OnBuyListener buyListener;

    public static final String PREFS_NAME = "Token";
    static String token;
    private static SharedPreferences settings;
    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://outer-space-manager.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private User user;
    private OuterSpaceService service = retrofit.create(OuterSpaceService.class);

    static Building building;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buildings);

        // Database helper
        db = new OuterSpaceManagerDB(getApplicationContext());
        // Database Access Object initiation
        buildingDAO = new BuildingDAO(getApplicationContext());

        // On récupère les composants
        // Find the toolbar view inside the activity layout
        this.rvBuildings = (RecyclerView) findViewById(R.id.rvBuildings);

        // use a linear layout manager
        rvLayoutManager = new LinearLayoutManager(this);
        rvBuildings.setLayoutManager(rvLayoutManager);

        // On récupère le token
        this.settings = getSharedPreferences(PREFS_NAME, 0);
        String token = settings.getString("token", null);

        // On récupère le joueur correspondant au token
        Call<User> request = service.getCurrentUser(token);
        request.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    Log.d("App", response.body().toString());
                    user = response.body();
                } else {
                    // on affiche le feedback comme quoi la récupération des infos du joueur a échoué
                    Log.d("App", "Echec de la récupération des infos du joueur 2");
                    Toast.makeText(getApplicationContext(), "Echec de la récupération des infos du joueur 2", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // on affiche le feedback comme quoi la récupération des infos du joueur a échoué
                Log.d("App", "onFailure: Echec de la récupération des infos du joueur 1");
                Toast.makeText(getApplicationContext(),"Echec de la récupération des infos du joueur 1",Toast.LENGTH_SHORT).show();
            }
        });

        // On récupère la liste des bâtiments
        Call<GetBuildingsResponse> requestBuildings = service.getBuildings(token);
        requestBuildings.enqueue(new Callback<GetBuildingsResponse>() {
            @Override
            public void onResponse(Call<GetBuildingsResponse> call, Response<GetBuildingsResponse> response) {

                if(response.isSuccessful()){
                    Log.d("App", "Récupération des bâtiments.");
                    buildings = response.body().getBuildings();
                    Log.d("App", "Nb de bâtiments = " + buildings.size());

                    buyListener = new OnBuyListener() {
                        @Override
                        public void onBuy(View v, int position) {
                            buyBuilding(position);
                        }
                    };
                    aaBuildings = new BuildingsArrayAdapter(getApplicationContext(), buildings, buyListener);
                    // on remplit la listView avec notre liste de vaisseaux
                    rvBuildings.setAdapter(aaBuildings);
                } else {
                    Log.d("App", "onFailure: Echec de la récupération des bâtiments 2");
                    // on affiche le feedback comme quoi la création du compte a échoué
                    Toast.makeText(getApplicationContext(), "Echec de la récupération des bâtiments 2", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GetBuildingsResponse> call, Throwable t) {
                Log.d("App", "onFailure: Echec de la récupération des bâtiments 1");
                Log.d("App", "onFailure: "+t);
                // on affiche le feedback comme quoi la récupération des bâtiments a échoué
                Toast.makeText(getApplicationContext(),"Echec de la récupération des bâtiments 1",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void buyBuilding(int position) {

        building =  buildings.get(position);

        // display failure alert if player doesn't have enough resources
        // si le joueur n'a pas assez de ressources on affiche une alerte pour l'en informer
        if(building.getGasCost() > user.getGas() || building.getMineralCost() > user.getMinerals()){
            new AlertDialog.Builder(this)
                    .setTitle("Erreur")
                    .setMessage("Vous n'avez pas assez de ressources!\n\n" +
                            "Coût en gaz : " + building.getGasCost() + "\n" +
                            "Coût en minéraux : " + building.getMineralCost() + "\n\n" +
                            "Or vous n'avez que " + user.getGas() + " gaz et " + user.getMinerals() + "  minéraux.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //do nothing
                        }
                    })
                    .show();
        }else{
            new AlertDialog.Builder(this)
                    .setTitle("Construire un bâtiment")
                    .setMessage("Voulez-vous construire ce bâtiment?\n\n" +
                            "Coût en gaz : " + building.getGasCost() + "\n" +
                            "Coût en minéraux : " + building.getMineralCost() + "\n\n" +
                            "Il vous restera " + (user.getGas()-building.getGasCost()) + " gaz et " + (user.getMinerals()-building.getMineralCost()) + " minéraux.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            // building creation
                            Call<NewBuildingResponse> request = service.newBuilding(token, building.getBuildingId());
                            request.enqueue(new Callback<NewBuildingResponse>() {

                                @Override
                                public void onResponse(Call<NewBuildingResponse> call, Response<NewBuildingResponse> response) {
                                    if(response.isSuccessful()){
                                        Log.d("App", "Bâtiment en cours de construction!");
                                        Toast.makeText(getApplicationContext(),"Bâtiment en cours de construction!",Toast.LENGTH_SHORT).show();
                                        // Here, thisActivity is the current activity
                                        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                                        {
                                            // Should we show an explanation? (to avoid a never ask again response)
                                            if (ActivityCompat.shouldShowRequestPermissionRationale(BuildingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                                // Show an explanation to the user *asynchronously* -- don't block
                                                // this thread waiting for the user's response! After the user
                                                // sees the explanation, try again to request the permission.
                                            } else {
                                                // No explanation needed, we can request the permission.
                                                ActivityCompat.requestPermissions(BuildingActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 4444);
                                            }
                                        } else {
                                            saveBuilding(building);
                                        }
                                    } else {
                                        Log.d("App", "Echec de la création du bâtiment 2");
                                        // display building's construction failure feedback
                                        Toast.makeText(getApplicationContext(), "Erreur " + response.code() + " : " + response.message(),Toast.LENGTH_SHORT).show();
                                        Toast.makeText(getApplicationContext(), "Echec de la création du bâtiment 2",Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<NewBuildingResponse> call, Throwable t) {
                                    Log.d("App", "onFailure: Echec de la création du bâtiment 1");
                                    // on affiche le feedback comme quoi la création du compte a échoué
                                    Toast.makeText(getApplicationContext(),"Echec de la création du bâtiment 1",Toast.LENGTH_SHORT).show();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 4444: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the buildings-related task you need to do.

                    // construct building object and inserting it in db
                    this.saveBuilding(building);

                } else {
                // permission denied, boo! Disable the
                // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
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
