package annaikanyouzoo.com.outerspacemanager.outerspacemanager.activities;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import annaikanyouzoo.com.outerspacemanager.outerspacemanager.OuterSpaceService;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.R;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.array_adapters.UsersArrayAdapter;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.dao.AttackDAO;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.db.OuterSpaceManagerDB;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.listeners.OnAttackListener;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.Attack;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.User;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.response.GetUsersResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GalaxyActivity extends AppCompatActivity {

    // Database Helper
    OuterSpaceManagerDB db;
    // Database Access Object
    AttackDAO attackDAO;

    private RecyclerView rvUsers;
    private RecyclerView.LayoutManager rvLayoutManager;
    private RecyclerView.Adapter aaUsers;
    private OnAttackListener attackListener;

    private List<User> users;

    public static final String PREFS_NAME = "Token";
    private static SharedPreferences settings;
    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://outer-space-manager.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private OuterSpaceService service = retrofit.create(OuterSpaceService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galaxy);

        // Database helper
        db = new OuterSpaceManagerDB(getApplicationContext());
        // Database Access Object initiation
        attackDAO = new AttackDAO(getApplicationContext());

        // On récupère les composants
        this.rvUsers = (RecyclerView) findViewById(R.id.rvUsers);

        // use a linear layout manager
        rvLayoutManager = new LinearLayoutManager(this);
        rvUsers.setLayoutManager(rvLayoutManager);

        // On récupère le token
        this.settings = getSharedPreferences(PREFS_NAME, 0);
        String token = settings.getString("token", null);

        // On récupère les ennemis
        Call<GetUsersResponse> request = service.getUsers(token, 0, 4);
        request.enqueue(new Callback<GetUsersResponse>() {
            @Override
            public void onResponse(Call<GetUsersResponse> call, Response<GetUsersResponse> response) {

                if(response.isSuccessful()){
                    Log.d("App", "Récupération des joueurs.");
                    users = response.body().getUsers();
                    Log.d("App", "Nb joueurs = " + users.size());

                    attackListener = new OnAttackListener() {
                        @Override
                        public void onAttack(View v, int position) {
                            Toast.makeText(getApplicationContext(), "Attaque!!!", Toast.LENGTH_SHORT).show();
                        }
                    };
                    aaUsers = new UsersArrayAdapter(getApplicationContext(), users, attackListener);
                    // on remplit la listView avec notre liste de joueurs
                    rvUsers.setAdapter(aaUsers);
                } else {
                    Log.d("App", "onFailure: Echec de la récupération des joueurs 2");
                    // on affiche le feedback comme quoi la récupération des joueurs a échoué
                    Toast.makeText(getApplicationContext(), "Echec de la récupération des joueurs 2", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetUsersResponse> call, Throwable t) {
                Log.d("App", "onFailure: Echec de la récupération des joueurs 1");
                Log.d("App", "onFailure: "+t);
                // on affiche le feedback comme quoi la récupération des bâtiments a échoué
                Toast.makeText(getApplicationContext(),"Echec de la récupération des joueurs 1",Toast.LENGTH_SHORT).show();
            }
        });

        // Création des listeners sur les élements de la liste d'ennemis
        //rvUsers.setOnItemClickListener(GalaxyActivity.this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 4444: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the buildings-related task you need to do.

                    // construct building object and inserting it in db
                    //this.saveAttack(attackSelected);

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

    public void saveAttack(Attack attack) {

        // open database connection
        attackDAO.open();

        // create timestamp to set constructionstart
        Long constructionStart = System.currentTimeMillis() / 1000;

        // calculate constructionEnd
        //Long constructionEnd =  constructionStart + attack.getTimeToFinish();

        // insert building in database
        //attackDAO.addRunningAttack(attack, attackStart, attackEnd);

        // Don't forget to close database connection
        attackDAO.close();

    }
}


