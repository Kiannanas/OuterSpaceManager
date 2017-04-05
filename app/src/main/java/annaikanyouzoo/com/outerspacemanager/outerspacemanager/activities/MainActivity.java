package annaikanyouzoo.com.outerspacemanager.outerspacemanager.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import annaikanyouzoo.com.outerspacemanager.outerspacemanager.OuterSpaceService;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.R;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.array_adapters.BuildingsArrayAdapter;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.dao.AttackDAO;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.dao.BuildingDAO;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.db.OuterSpaceManagerDB;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.fragments.GalaxyFragment;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.fragments.ShopBuildingsFragment;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.listeners.OnBuyListener;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.Attack;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.Building;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.Report;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.User;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.response.GetBuildingsResponse;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.response.NewBuildingResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {

    private User user;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;

    private MenuItem searchMenuItem;
    private TextView tvUsername;
    private TextView tvPointsAmount;
    private Button btnGeneralView;
    private Button btnBuildings;
    private Button btnFleet;
    private Button btnResearches;
    private Button btnSpatialSite;
    private Button btnGalaxy;
    private Button btnDeconnect;

    public static final String PREFS_NAME = "Token";
    private static String token;
    private static SharedPreferences settings;
    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://outer-space-manager.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    OuterSpaceService service = retrofit.create(OuterSpaceService.class);

    // Database Helper
    private static OuterSpaceManagerDB db;

    private RecyclerView.LayoutManager rvLayoutManager = new LinearLayoutManager(this);;
    private OnBuyListener buyListener;

    // Buildings related DAO, List, RecyclerView, RecyclerView Adapter
    private static BuildingDAO buildingDAO;    // Database Access Object
    private List<Building> buildings;
    //private RecyclerView rvBuildings = (RecyclerView) findViewById(R.id.rvBuildings);
    private RecyclerView.Adapter aaBuildings;
    static Building building;

    // Attacks related DAO, List, RecyclerView, RecyclerView Adapter
    //AttackDAO attackDAO = new AttackDAO(getApplicationContext());    // Database Access Object
    private List<Report> attacks;
    //private RecyclerView rvAttacks = (RecyclerView) findViewById(R.id.rvAttacks);;
    private RecyclerView.Adapter aaAttacks;
    static Attack attack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // database helper initiation
        this.db = new OuterSpaceManagerDB(getApplicationContext());
        // building DAO initiation
        this.buildingDAO = new BuildingDAO(getApplicationContext());

        // On récupère les composants
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.drawerToggle = new ActionBarDrawerToggle(
                this,                       /* host Activity */
                drawerLayout,               /* DrawerLayout object */
                toolbar,                    /* Toolbar object */
                R.string.nav_drawer_open,   /* "open drawer" description */
                R.string.nav_drawer_close   /* "close drawer" description */
        );

        // Set the drawer toggle as the DrawerListener
        drawerLayout.addDrawerListener(this.drawerToggle);
        this.drawerToggle.syncState();

        this.navigationView = (NavigationView) findViewById(R.id.nav_view);
        this.navigationView.setNavigationItemSelectedListener(this);

        //rvBuildings.setLayoutManager(rvLayoutManager);
        //rvAttacks.setLayoutManager(rvLayoutManager);

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
        this.token = settings.getString("token", null);

        // On récupère le joueur correspondant au token
        Call<User> request = service.getCurrentUser(token);
        request.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.errorBody() != null){
                    // on affiche le feedback comme quoi la récupération des infos du joueur a échoué
                    Log.d("App", "Echec de la récupération des infos du joueur 2");
                    Toast.makeText(getApplicationContext(), "Echec de la récupération des infos du joueur 2", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("App", response.body().toString());
                    user = response.body();
                    tvUsername.setText(response.body().getUsername().toString());
                    tvPointsAmount.setText("POINTS : " + String.valueOf(response.body().getPoints()));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // on affiche le feedback comme quoi la récupération des infos du joueur a échoué
                Log.d("App", "onFailure: Echec de la récupération des infos du joueur 1");
                Toast.makeText(getApplicationContext(),"Echec de la récupération des infos du joueur 1",Toast.LENGTH_SHORT).show();
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        // Handle navigation view item clicks here

        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (id) {

            case R.id.nav_user_stats :
                Toast.makeText(this, "STATS", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_user_army :
                Toast.makeText(this, "ARMY", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_searches :
                Toast.makeText(this, "SEARCHES", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_shop :
                ShopBuildingsFragment shopBuildingsFragment = ShopBuildingsFragment.newInstance(this.user, this.token);
                fragmentManager.beginTransaction().replace(
                        R.id.rl_for_fragment,
                        shopBuildingsFragment,
                        shopBuildingsFragment.getTag()
                ).commit();
                Toast.makeText(this, "SHOP", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_galaxy :
                GalaxyFragment galaxyFragment = new GalaxyFragment();
                fragmentManager.beginTransaction().replace(
                        R.id.rl_for_fragment,
                        galaxyFragment,
                        galaxyFragment.getTag()
                ).commit();
                Toast.makeText(this, "GALAXIE", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_attacks :
                Toast.makeText(this, "ATTAQUES", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_change_account :
                Toast.makeText(this, "STATS", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_deconnect :
                Toast.makeText(this, "DECONNEXION", Toast.LENGTH_SHORT).show();
                break;
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
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

                // On va dans BuildingActivity
                Log.d("App", "Go to GalaxyActivity");
                myIntent = new Intent(getApplicationContext(), GalaxyActivity.class);
                startActivity(myIntent);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        searchMenuItem = menu.findItem(R.id.action_search);
        SearchView searchViewAction = (SearchView) MenuItemCompat.getActionView(searchMenuItem); searchViewAction.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        MenuItemCompat.collapseActionView(searchMenuItem);
        //TODO Handler
        return false;
    }
    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_search:
                //TODO Handler
                return true;

            case R.id.action_settings:
                //TODO Handler
                return true; default:
                return super.onOptionsItemSelected(item); }
    }
}
