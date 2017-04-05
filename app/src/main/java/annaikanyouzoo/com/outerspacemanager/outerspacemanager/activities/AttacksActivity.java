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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import annaikanyouzoo.com.outerspacemanager.outerspacemanager.OuterSpaceService;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.R;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.array_adapters.AttacksArrayAdapter;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.array_adapters.BuildingsArrayAdapter;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.dao.AttackDAO;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.dao.BuildingDAO;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.db.OuterSpaceManagerDB;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.Attack;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.Building;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.Report;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.User;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.response.GetBuildingsResponse;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.response.GetReportsResponse;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.response.NewBuildingResponse;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.view_holders.AttackViewHolder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AttacksActivity extends AppCompatActivity {

    // Database Helper
    OuterSpaceManagerDB db;
    // Database Access Object
    AttackDAO attackDAO;

    private RecyclerView rvAttacks;
    private RecyclerView.LayoutManager rvLayoutManager;

    private List<Report> attacks;
    public static final String PREFS_NAME = "Token";
    private static SharedPreferences settings;
    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://outer-space-manager.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private User user;
    private OuterSpaceService service = retrofit.create(OuterSpaceService.class);
    static Attack attackSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attacks);

        // Database helper
        db = new OuterSpaceManagerDB(getApplicationContext());
        // Database Access Object initiation
        attackDAO = new AttackDAO(getApplicationContext());

        // On récupère les composants
        this.rvAttacks = (RecyclerView) findViewById(R.id.rvAttacks);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //rvAttacks.setHasFixedSize(true);

        // use a linear layout manager
        rvLayoutManager = new LinearLayoutManager(this);
        rvAttacks.setLayoutManager(rvLayoutManager);

        // On récupère le token
        this.settings = getSharedPreferences(PREFS_NAME, 0);
        String token = settings.getString("token", null);

        //récupération de la liste des attaques dans l'API
        Call<GetReportsResponse> requestAttacks = service.getReports(token, 0, 10);
        requestAttacks.enqueue(new Callback<GetReportsResponse>() {
            @Override
            public void onResponse(Call<GetReportsResponse> call, Response<GetReportsResponse> response) {

                if(response.isSuccessful()){
                    Log.d("App", "Récupération des attaques.");
                    attacks = response.body().getReports();
                    Log.d("App", "Nb attacks = " + attacks.size());

                    // on remplit la listView avec notre liste de rapports d'attaques
                    rvAttacks.setAdapter(new AttacksArrayAdapter(getApplicationContext(), attacks));
                } else {
                    Log.d("App", "onFailure: Echec de la récupération des rapports d'attaques 2");
                    Log.d("App", "onFailure: "+response.errorBody() + " " + response.code() + " " + response.message());
                    // on affiche le feedback comme quoi la récupération des rapports d'attaques
                    Toast.makeText(getApplicationContext(), "ERROR: " + response.code() + " MESSAGE: " + response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GetReportsResponse> call, Throwable t) {
                Log.d("App", "onFailure: Echec de la récupération des rapports d'attaques 1");
                Log.d("App", "onFailure: "+t);
                // on affiche le feedback comme quoi la récupération des rapports d'attaque a échoué
                Toast.makeText(getApplicationContext(),"Echec de la récupération des rapports d'attaques 1",Toast.LENGTH_SHORT).show();
            }
        });

        //récupération de la liste des attaques en BDD
        //this.attacks = attackDAO.getAllAttacks();

        //if(this.attacks.size() != 0){

            // on remplit la listView avec notre liste de bâtiments
            //rvAttacks.setAdapter(new AttacksArrayAdapter(getApplicationContext(), attacks));

            // Création des listeners sur les élements de la liste
            //rvAttacks.setOnItemClickListener(AttackActivity.this);
        //}
    }

}
