package annaikanyouzoo.com.outerspacemanager.outerspacemanager.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tvUsername;
    private TextView tvPassword;
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private String token;
    public static final String PREFS_NAME = "Token";
    private static SharedPreferences settings;
    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://outer-space-manager.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Récupération des composants
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        // Création des listeners sur les boutons
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if(id == R.id.btnLogin){

            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();

            if(( username != "" || username != null) && ( password != "" || username != null)){
                Log.d("App Login", "username + password OK");
                User user = new User(username, password);

                OuterSpaceService service = retrofit.create(OuterSpaceService.class);
                Call<User> request = service.login(user);
                request.enqueue(new Callback<User>() {

                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {

                        if(response.errorBody()!= null){
                            Log.d("App", "onFailure: Echec de l'authentification.");
                            // on affiche le feedback comme quoi la création du compte a échoué
                            Toast.makeText(getApplicationContext(), "Echec de l'authentification",Toast.LENGTH_SHORT).show();
                        } else {
                            // on sauvegarde le token dans les SharedPreferences
                            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                            SharedPreferences.Editor editor = settings.edit();
                            String token = response.body().getToken().toString();
                            editor.putString("token", token);
                            editor.commit();

                            Toast.makeText(getApplicationContext(),"Bonjour!",Toast.LENGTH_SHORT).show();

                            // Si l'inscription a été réalisée avec succès on passe au menu
                            Intent myIntent = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(myIntent);
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable throwable) {
                        Log.d("App Login", "onFailure: Echec de l'authentification");
                        // on affiche le feedback comme quoi la création du compte a échoué
                        Toast.makeText(getApplicationContext(),"Echec de l'authentification",Toast.LENGTH_SHORT).show();
                    }
                });

            } else if (username == "" || username == null){
                Toast.makeText(getApplicationContext(),"Saisir un Pseudo",Toast.LENGTH_SHORT).show();
            } else if (password == "" || password == null){
                Toast.makeText(getApplicationContext(),"Saisir un mot de passe",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
