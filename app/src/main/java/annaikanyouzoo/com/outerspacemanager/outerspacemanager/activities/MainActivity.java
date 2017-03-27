package annaikanyouzoo.com.outerspacemanager.outerspacemanager.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import annaikanyouzoo.com.outerspacemanager.outerspacemanager.OuterSpaceService;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.R;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private User user;
    private static TextView tvUsername;
    private TextView tvPoints;
    private static TextView tvPointsAmount;
    private Button btnGeneralView;
    private Button btnBuildings;
    private Button btnFleet;
    private Button btnResearches;
    private Button btnSpatialSite;
    private Button btnGalaxy;
    private Button btnDeconnect;
    public static final String PREFS_NAME = "Token";
    private static SharedPreferences settings;
    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://outer-space-manager.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // On récupère les composants
        this.tvUsername = (TextView) findViewById(R.id.tvUsername);
        this.tvPointsAmount = (TextView) findViewById(R.id.tvPointsAmount);
        this.btnGeneralView = (Button) findViewById(R.id.btnGeneralView);
        this.btnBuildings = (Button) findViewById(R.id.btnBuildings);
        this.btnFleet = (Button) findViewById(R.id.btnFleet);
        this.btnResearches = (Button) findViewById(R.id.btnResearches);
        this.btnSpatialSite = (Button) findViewById(R.id.btnSpatialSite);
        this.btnGalaxy = (Button) findViewById(R.id.btnGalaxy);
        this.btnDeconnect = (Button) findViewById(R.id.btnDeconnect);

        // On récupère le token
        this.settings = getSharedPreferences(PREFS_NAME, 0);
        String token = settings.getString("token", null);

        // On récupère les infos du joueur correspondant au token
        OuterSpaceService service = retrofit.create(OuterSpaceService.class);
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
                    tvUsername.setText(response.body().getUsername().toString());
                    tvPointsAmount.setText(String.valueOf(response.body().getPoints()));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("App", "onFailure: Echec de la récupération des infos");
                // on affiche le feedback comme quoi la création du compte a échoué
                Toast.makeText(getApplicationContext(),"Echec de la récupération des infos",Toast.LENGTH_SHORT).show();
            }
        });

        // Création des listeners sur les boutons
        this.btnGeneralView.setOnClickListener(this);
        this.btnBuildings.setOnClickListener(this);
        this.btnFleet.setOnClickListener(this);
        this.btnResearches.setOnClickListener(this);
        this.btnSpatialSite.setOnClickListener(this);
        this.btnGalaxy.setOnClickListener(this);
        this.btnDeconnect.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        Intent myIntent;

        switch (id) {
            case R.id.btnGeneralView :

                // On va dans BuildingActivity
                Log.d("App", "Go to UserActivity");
                myIntent = new Intent(getApplicationContext(),UserActivity.class);
                startActivity(myIntent);

                break;

            case R.id.btnBuildings :

                // On va dans BuildingActivity
                Log.d("App", "Go to BuildingActivity");
                myIntent = new Intent(getApplicationContext(),BuildingActivity.class);
                startActivity(myIntent);

                break;

            case R.id.btnFleet :

                // On va dans BuildingActivity
                Log.d("App", "Go to FleetActivity");
                myIntent = new Intent(getApplicationContext(),FleetActivity.class);
                startActivity(myIntent);

                break;

            case R.id.btnResearches :
                break;
            case R.id.btnSpatialSite :

                // On va dans BuildingActivity
                Log.d("App", "Go to ShipsActivity");
                myIntent = new Intent(getApplicationContext(),ShipsActivity.class);
                startActivity(myIntent);

                break;

            case R.id.btnGalaxy :
                break;
            case R.id.btnDeconnect :

                // On supprime le token
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                String token = settings.getString("token", null);
                Log.d("App", "Token = "+token);
                editor.remove("token");
                editor.commit();

                // On informe le joueur qu'il est déconnecté
                Log.d("App", "Déconnexion");
                Toast.makeText(getApplicationContext(),"Déconnexion",Toast.LENGTH_SHORT).show();

                // On revient à SignUpActivity
                myIntent = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(myIntent);

                break;
        }

    }
}
