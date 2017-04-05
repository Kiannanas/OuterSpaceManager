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

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tvUsername;
    private TextView tvPassword;
    private EditText etUsername;
    private EditText etEmail;
    private EditText etPassword;
    private Button btnRegister;
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
        setContentView(R.layout.activity_signup);

        // Récupération des composants
        etUsername = (EditText) findViewById(R.id.etUsername);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        // On récupère le token
        this.settings = getSharedPreferences(PREFS_NAME, 0);
        String token = settings.getString("token", null);
        Log.d("App", "Token = "+token);

        // S'il y a déjà un token on envoit directement sur la MainActivity (menu)
        if(token != null && token != ""){
            Intent myIntent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(myIntent);
        }

        // Création des listeners sur les boutons
        btnRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id){
            case R.id.btnRegister :

                String username = etUsername.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if(( username != "" || username != null) && ( password != "" || username != null) && ( email != "" || email != null)){
                    Log.d("OuterSpaceManager", "username + email + password OK");
                    User user = new User(username, email, password);

                    OuterSpaceService service = retrofit.create(OuterSpaceService.class);
                    Call<User> request = service.newUser(user);
                    request.enqueue(new Callback<User>() {

                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {

                            if(response.errorBody()!= null){
                                Log.d("OuterSpaceManager", "onFailure: Echec de l'inscription.");
                                // on affiche le feedback comme quoi la création du compte a échoué
                                Toast.makeText(getApplicationContext(),response.errorBody().toString(),Toast.LENGTH_SHORT).show();
                            } else {
                                // on sauvegarde le token dans les SharedPreferences
                                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                                SharedPreferences.Editor editor = settings.edit();
                                String token = response.body().getToken().toString();
                                editor.putString("token", token);
                                editor.commit();

                                Toast.makeText(getApplicationContext(),"L'inscription s'est déroulée avec succès!",Toast.LENGTH_SHORT).show();

                                // Si l'inscription a été réalisée avec succès on passe au menu
                                Intent myIntent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(myIntent);
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable throwable) {
                            Log.d("App", "onFailure: Echec de l'inscription");
                            // on affiche le feedback comme quoi la création du compte a échoué
                            Toast.makeText(getApplicationContext(),"Echec de l'inscription",Toast.LENGTH_SHORT).show();
                        }
                    });

                } else if (username == "" || username == null){
                    Toast.makeText(getApplicationContext(),"Saisir un Pseudo",Toast.LENGTH_SHORT).show();
                } else if (password == "" || password == null){
                    Toast.makeText(getApplicationContext(),"Saisir un mot de passe",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnLogin :

                Intent myIntent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(myIntent);

        }
    }
}
