package annaikanyouzoo.com.outerspacemanager.outerspacemanager.activities;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import annaikanyouzoo.com.outerspacemanager.outerspacemanager.OuterSpaceService;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.R;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.ShipsArrayAdapter;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.Amount;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.Ship;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.User;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.response.GetShipsResponse;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.response.NewShipResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShipsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private List<Ship> ships;
    public static final String PREFS_NAME = "Token";
    private static SharedPreferences settings;
    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://outer-space-manager.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private User user;
    private OuterSpaceService service = retrofit.create(OuterSpaceService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ships);

        // On récupère les composants
        final ListView lvShips = (ListView) findViewById(R.id.lvShips);

        // On récupère le token
        this.settings = getSharedPreferences(PREFS_NAME, 0);
        String token = settings.getString("token", null);

        // On récupère les infos du joueur correspondant au token
        Call<User> request = service.getUser(token);
        request.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.errorBody() != null){
                    Log.d("App", "Echec de la récupération du joueur");
                    // on affiche le feedback comme quoi la création du compte a échoué
                    Toast.makeText(getApplicationContext(), response.errorBody().toString(),Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("App", response.body().toString());
                    user = response.body();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("App", "onFailure: Echec de la récupération du joueur");
                // on affiche le feedback comme quoi la création du compte a échoué
                Toast.makeText(getApplicationContext(),"Echec de la récupération du joueur",Toast.LENGTH_SHORT).show();
            }
        });

        // On récupère la liste des bâtiments
        Call<GetShipsResponse> requestShips = service.getShips(token);
        requestShips.enqueue(new Callback<GetShipsResponse>() {
            @Override
            public void onResponse(Call<GetShipsResponse> call, Response<GetShipsResponse> response) {

                if(response.errorBody()!= null){
                    Log.d("App", "onFailure: Echec de la récupération des vaisseaux 2");
                    // on affiche le feedback comme quoi la création du compte a échoué
                    Toast.makeText(getApplicationContext(),response.errorBody().toString(),Toast.LENGTH_SHORT).show();
                } else {
                    ships = response.body().getShips();

                    // on remplit la listView avec notre liste de bâtiments
                    lvShips.setAdapter(new ShipsArrayAdapter(getApplicationContext(), ships));

                    // Création des listeners sur les élements de la liste
                    lvShips.setOnItemClickListener(ShipsActivity.this);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        // On récupère le token
        this.settings = getSharedPreferences(PREFS_NAME, 0);
        final String token = settings.getString("token", null);

        final Ship ship =  ships.get(position);
        final float gasCost = ship.getGasCost();
        final float mineralCost = ship.getMineralCost();

        // Si le joueur n'a pas assez de ressources on affiche une alerte pour l'en informer
        if(gasCost > user.getGas() || mineralCost > user.getMinerals()){
            new AlertDialog.Builder(this)
                    .setTitle("Erreur")
                    .setMessage("Vous n'avez pas assez de ressources!\n" +
                            " Coût en gaz : " + gasCost + "\n" +
                            " Coût en minéraux : " + mineralCost + "\n" +
                            " Or vous n'avez que " + user.getGas() + " gaz et " + user.getMinerals() + "  minéraux.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }else{
            new AlertDialog.Builder(this)
                    .setTitle("Construire un vaisseau")
                    .setMessage("Voulez-vous construire ce vaisseau?\n" +
                            " Coût en gaz : " + gasCost + "\n" +
                            " Coût en minéraux : " + mineralCost + "\n" +
                            " Il vous restera " + (user.getGas()-gasCost) + " gaz et " + (user.getMinerals()-mineralCost) + " minéraux.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            // On créé le vaisseau
                            Call<NewShipResponse> request = service.newShip(token, ship.getShipId(), new Amount(1));
                            request.enqueue(new Callback<NewShipResponse>() {

                                @Override
                                public void onResponse(Call<NewShipResponse> call, Response<NewShipResponse> response) {
                                    if(response.errorBody() != null){
                                        Log.d("App", "Echec de la création du vaisseau");
                                        // on affiche le feedback comme quoi la création du compte a échoué
                                        Toast.makeText(getApplicationContext(), "Echec de la création du vaisseau",Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.d("App", "Vaisseau en cours de construction!");
                                        Toast.makeText(getApplicationContext(),"Vaisseau créé!",Toast.LENGTH_SHORT).show();
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
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }
}
