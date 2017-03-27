package annaikanyouzoo.com.outerspacemanager.outerspacemanager.activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import annaikanyouzoo.com.outerspacemanager.outerspacemanager.OuterSpaceService;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.R;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.ShipAmount;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.ShipsAmountArrayAdapter;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.Fleet;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.User;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.response.GetUserFleetResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FleetActivity extends AppCompatActivity {

    private Fleet fleet;
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
        setContentView(R.layout.activity_fleet);

        // On récupère les composants
        final ListView lvShipsAmount = (ListView) findViewById(R.id.lvShipsAmount);

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
                    Toast.makeText(getApplicationContext(), "Echec de la récupération du joueur", Toast.LENGTH_SHORT).show();
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
        Call<GetUserFleetResponse> requestFleet = service.getUserFleet(token);
        requestFleet.enqueue(new Callback<GetUserFleetResponse>() {
            @Override
            public void onResponse(Call<GetUserFleetResponse> call, Response<GetUserFleetResponse> response) {

                if(response.errorBody()!= null){
                    Log.d("App", "onFailure: Echec de la récupération de la flotte");
                    // on affiche le feedback comme quoi la création du compte a échoué
                    Toast.makeText(getApplicationContext(), "Echec de la récupération de la flotte", Toast.LENGTH_SHORT).show();
                } else {
                    int size = response.body().getSize();
                    List<ShipAmount> ships = response.body().getShips();

                    // on remplit la listView avec notre liste de bâtiments
                    lvShipsAmount.setAdapter(new ShipsAmountArrayAdapter(getApplicationContext(), ships));
                }

            }

            @Override
            public void onFailure(Call<GetUserFleetResponse> call, Throwable t) {
                Log.d("App", "onFailure: Echec de la récupération de la flotte 1");
                Log.d("App", "onFailure: "+t);
                // on affiche le feedback comme quoi la récupération des bâtiments a échoué
                Toast.makeText(getApplicationContext(),"Echec de la récupération de la flotte 1",Toast.LENGTH_SHORT).show();
            }
        });


    }
}
