package annaikanyouzoo.com.outerspacemanager.outerspacemanager.activities;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import annaikanyouzoo.com.outerspacemanager.outerspacemanager.OuterSpaceService;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.R;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.array_adapters.ShipsArrayAdapter;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.dao.BuildingDAO;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.dao.ShipDAO;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.db.OuterSpaceManagerDB;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.listeners.OnBuyListener;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.Amount;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.Building;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.Ship;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.User;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.response.GetShipsResponse;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.response.NewShipResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShipsActivity extends AppCompatActivity {

    // Database Helper
    OuterSpaceManagerDB db;
    // Database Access Object
    ShipDAO shipDAO;

    private List<Ship> ships;

    private RecyclerView rvShips;
    private RecyclerView.LayoutManager rvLayoutManager;
    private RecyclerView.Adapter aaShips;
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

    static Ship ship;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ships);

        // Database helper
        db = new OuterSpaceManagerDB(getApplicationContext());
        // Database Access Object initiation
        shipDAO = new ShipDAO(getApplicationContext());

        // On récupère les composants
        this.rvShips = (RecyclerView) findViewById(R.id.rvShips);

        // use a linear layout manager
        rvLayoutManager = new LinearLayoutManager(this);
        rvShips.setLayoutManager(rvLayoutManager);

        // On récupère le token
        this.settings = getSharedPreferences(PREFS_NAME, 0);
        this.token = settings.getString("token", null);

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

        // On récupère la liste des vaisseaux
        Call<GetShipsResponse> requestShips = service.getShips(token);
        requestShips.enqueue(new Callback<GetShipsResponse>() {
            @Override
            public void onResponse(Call<GetShipsResponse> call, Response<GetShipsResponse> response) {

                if(response.isSuccessful()){
                    Log.d("App", "Récupération des vaisseaux.");
                    ships = response.body().getShips();
                    Log.d("App", "Nb de vaisseaux = " + ships.size());

                    buyListener = new OnBuyListener() {
                        @Override
                        public void onBuy(View v, int position) {
                            buyShip(position);
                        }
                    };
                    aaShips = new ShipsArrayAdapter(getApplicationContext(), ships, buyListener);
                    // on remplit la listView avec notre liste de vaisseaux
                    rvShips.setAdapter(aaShips);
                } else {
                    Log.d("App", "onFailure: Echec de la récupération des vaisseaux 2");
                    // on affiche le feedback comme quoi la création du compte a échoué
                    Toast.makeText(getApplicationContext(),response.errorBody().toString(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GetShipsResponse> call, Throwable t) {
                Log.d("App", "onFailure: Echec de la récupération des vaisseaux 1");
                Log.d("App", "onFailure: "+t);
                // on affiche le feedback comme quoi la récupération des bâtiments a échoué
                Toast.makeText(getApplicationContext(),"Echec de la récupération des vaisseaux 1",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void buyShip(int position){

        ship =  ships.get(position);

        // Si le joueur n'a pas assez de ressources on affiche une alerte pour l'en informer
        if(ship.getGasCost() > user.getGas() || ship.getMineralCost() > user.getMinerals()){
            new AlertDialog.Builder(this)
                    .setTitle("Erreur")
                    .setMessage("Vous n'avez pas assez de ressources!\n\n" +
                            "Coût en gaz : " + ship.getGasCost() + "\n" +
                            "Coût en minéraux : " + ship.getMineralCost() + "\n\n" +
                            "Or vous n'avez que " + user.getGas() + " gaz et " + user.getMinerals() + "  minéraux.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //do nothing
                        }
                    })
                    .show();
        }else{
            new AlertDialog.Builder(this)
                    .setTitle("Construire un vaisseau")
                    .setMessage("Voulez-vous construire ce vaisseau?\n\n" +
                            "Coût en gaz : " + ship.getGasCost() + "\n" +
                            "Coût en minéraux : " + ship.getMineralCost() + "\n\n" +
                            "Il vous restera " + (user.getGas()-ship.getGasCost()) + " gaz et " + (user.getMinerals()-ship.getMineralCost()) + " minéraux.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            // On créé le vaisseau
                            Call<NewShipResponse> request = service.newShip(token, ship.getShipId(), new Amount(1));
                            request.enqueue(new Callback<NewShipResponse>() {

                                @Override
                                public void onResponse(Call<NewShipResponse> call, Response<NewShipResponse> response) {
                                    if(response.errorBody() != null){
                                        Log.d("App", "Echec de la création du vaisseau");
                                        // on affiche le feedback comme quoi la création du vaisseau a échoué
                                        Toast.makeText(getApplicationContext(), "Erreur " + response.code() + " : " + response.message(),Toast.LENGTH_SHORT).show();
                                        Toast.makeText(getApplicationContext(), "Echec de la création du vaisseau",Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.d("App", "Vaisseau en cours de construction!");
                                        Toast.makeText(getApplicationContext(),"Vaisseau en cours de construction!",Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<NewShipResponse> call, Throwable t) {
                                    Log.d("App", "onFailure: Echec de la création du vaisseau");
                                    // on affiche le feedback comme quoi la création du compte a échoué
                                    Toast.makeText(getApplicationContext(),"Echec de la création du vaisseau",Toast.LENGTH_SHORT).show();
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
                    this.saveShip(ship);

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

    public void saveShip(Ship ship){

        // open database connection
        shipDAO.open();

        // create timestamp to set constructionstart
        Long constructionStart = System.currentTimeMillis()/1000;

        // calculate constructionEnd
        Long constructionEnd =  constructionStart + ship.getTimeToBuild();

        // insert building in database
        shipDAO.addShipRunning(ship, constructionStart, constructionEnd);

        // Don't forget to close database connection
        shipDAO.close();
    }
}
