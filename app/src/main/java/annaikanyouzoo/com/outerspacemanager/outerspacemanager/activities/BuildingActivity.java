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

import annaikanyouzoo.com.outerspacemanager.outerspacemanager.BuildingsArrayAdapter;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.OuterSpaceService;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.R;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.Building;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.User;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.response.GetBuildingsResponse;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.response.NewBuildingResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BuildingActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private List<Building> buildings;
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
        setContentView(R.layout.activity_building);

        // On récupère les composants
        final ListView lvBuildings = (ListView) findViewById(R.id.lvBuildings);

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
                Log.d("App", "onFailure: Echec de la récupération des infos");
                // on affiche le feedback comme quoi la création du compte a échoué
                Toast.makeText(getApplicationContext(), "Echec de la récupération des infos", Toast.LENGTH_SHORT).show();
            }
        });

        // On récupère la liste des bâtiments
        Call<GetBuildingsResponse> requestBuildings = service.getBuildings(token);
        requestBuildings.enqueue(new Callback<GetBuildingsResponse>() {
            @Override
            public void onResponse(Call<GetBuildingsResponse> call, Response<GetBuildingsResponse> response) {

                if(response.errorBody()!= null){
                    Log.d("App", "onFailure: Echec de la récupération des bâtiments 2");
                    // on affiche le feedback comme quoi la création du compte a échoué
                    Toast.makeText(getApplicationContext(), "Echec de la récupération des bâtiments 2", Toast.LENGTH_SHORT).show();
                } else {
                    buildings = response.body().getBuildings();

                    // on remplit la listView avec notre liste de bâtiments
                    lvBuildings.setAdapter(new BuildingsArrayAdapter(getApplicationContext(), buildings));

                    // Création des listeners sur les élements de la liste
                    lvBuildings.setOnItemClickListener(BuildingActivity.this);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        // On récupère le token
        this.settings = getSharedPreferences(PREFS_NAME, 0);
        final String token = settings.getString("token", null);

        final Building building =  buildings.get(position);
        final float gasCost;
        final float mineralCost;
        if(building.getLevel() == 0){
            gasCost = building.getGasCostLevel0();
            mineralCost = building.getMineralCostLevel0();
        }else{
            gasCost = building.getGasCostByLevel() * building.getLevel();
            mineralCost = building.getMineralCostByLevel() * building.getLevel();
        }

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
                    .setTitle("Construire un bâtiment")
                    .setMessage("Voulez-vous construire ce bâtiment?\n" +
                            " Coût en gaz : " + gasCost + "\n" +
                            " Coût en minéraux : " + mineralCost + "\n" +
                            " Il vous restera " + (user.getGas()-gasCost) + " gaz et " + (user.getMinerals()-mineralCost) + " minéraux.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            // On créé le bâtiment
                            Call<NewBuildingResponse> request = service.newBuilding(token, building.getBuildingId());
                            request.enqueue(new Callback<NewBuildingResponse>() {

                                @Override
                                public void onResponse(Call<NewBuildingResponse> call, Response<NewBuildingResponse> response) {
                                    if(response.errorBody() != null){
                                        Log.d("App", "Echec de la création du bâtiment");
                                        // on affiche le feedback comme quoi la création du compte a échoué
                                        Toast.makeText(getApplicationContext(), "Echec de la création du bâtiment",Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.d("App", "Bâtiment en cours de construction!");
                                        Toast.makeText(getApplicationContext(),"Bâtiment créé!",Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<NewBuildingResponse> call, Throwable t) {
                                    Log.d("App", "onFailure: Echec de la création du bâtiment");
                                    // on affiche le feedback comme quoi la création du compte a échoué
                                    Toast.makeText(getApplicationContext(),"Echec de la création du bâtiment",Toast.LENGTH_SHORT).show();
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
