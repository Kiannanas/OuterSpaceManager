package annaikanyouzoo.com.outerspacemanager.outerspacemanager.activities;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import annaikanyouzoo.com.outerspacemanager.outerspacemanager.OuterSpaceService;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.R;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.array_adapters.ShipsAmountSelectArrayAdapter;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.dao.AttackDAO;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.db.OuterSpaceManagerDB;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.ShipAmount;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.array_adapters.UserShipsAmountArrayAdapter;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.Fleet;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.User;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.response.AttackUserResponse;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.response.GetUserFleetResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FleetActivity extends AppCompatActivity {

    // Database Helper
    OuterSpaceManagerDB db;
    // Database Access Object
    AttackDAO attackDAO;

    private User user;
    private Fleet fleet;
    private List<ShipAmount> shipsAmount;

    private RecyclerView rvShipsAmount;
    private RecyclerView.LayoutManager rvLayoutManager;
    private RecyclerView.Adapter rvaShipsAmount;
    private TextView tvSizeValue;

    public static final String PREFS_NAME = "Token";
    static String token;
    private static SharedPreferences settings;
    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://outer-space-manager.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private OuterSpaceService service = retrofit.create(OuterSpaceService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fleet);

        // Database helper
        db = new OuterSpaceManagerDB(getApplicationContext());
        // Database Access Object initiation
        attackDAO = new AttackDAO(getApplicationContext());

        // On récupère les composants
        this.rvShipsAmount = (RecyclerView) findViewById(R.id.rvShipsAmount);
        this.tvSizeValue = (TextView) findViewById(R.id.tvSizeValue);

        // use a linear layout manager
        rvLayoutManager = new LinearLayoutManager(this);
        rvShipsAmount.setLayoutManager(rvLayoutManager);

        // On récupère le token
        this.settings = getSharedPreferences(PREFS_NAME, 0);
        String token = settings.getString("token", null);

        // On récupère la flotte
        Call<GetUserFleetResponse> request = service.getUserFleet(token);
        request.enqueue(new Callback<GetUserFleetResponse>() {
            @Override
            public void onResponse(Call<GetUserFleetResponse> call, Response<GetUserFleetResponse> response) {

                if(response.isSuccessful()){
                    Log.d("App", "Récupération de la flotte du joueur.");
                    shipsAmount = response.body().getShips();
                    Log.d("App", "Nb de vaisseaux différents = " + shipsAmount.size());

                    rvaShipsAmount = new UserShipsAmountArrayAdapter(getApplicationContext(), shipsAmount);
                    // on remplit la listView avec notre liste de vaisseaux
                    rvShipsAmount.setAdapter(rvaShipsAmount);
                    // on renseigne le nombre total de vaisseaux
                    tvSizeValue.setText(""+response.body().getSize());
                } else {
                    Log.d("App", "onFailure: Echec de la récupération de la flotte");
                    // on affiche le feedback comme quoi la création du compte a échoué
                    Toast.makeText(getApplicationContext(), "Echec de la récupération de la flotte", Toast.LENGTH_SHORT).show();

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
