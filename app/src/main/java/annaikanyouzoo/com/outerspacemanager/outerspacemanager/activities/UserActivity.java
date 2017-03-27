package annaikanyouzoo.com.outerspacemanager.outerspacemanager.activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class UserActivity extends AppCompatActivity {

    private User user;
    private static TextView tvUsername;
    private static TextView tvPointsValue;
    private static TextView tvGasModifierValue;
    private static TextView tvGasValue;
    private static TextView tvMineralsModifierValue;
    private static TextView tvMineralsValue;
    public static final String PREFS_NAME = "Token";
    private static SharedPreferences settings;
    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://outer-space-manager.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // On récupère les composants
        this.tvUsername = (TextView) findViewById(R.id.tvUsername);
        this.tvPointsValue = (TextView) findViewById(R.id.tvPointsValue);
        this.tvGasModifierValue = (TextView) findViewById(R.id.tvGasModifierValue);
        this.tvGasValue = (TextView) findViewById(R.id.tvMineralsModifierValue);
        this.tvMineralsModifierValue = (TextView) findViewById(R.id.tvMineralsModifierValue);
        this.tvMineralsValue = (TextView) findViewById(R.id.tvMineralsValue);

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
                    tvPointsValue.setText(String.valueOf(response.body().getPoints()));
                    tvGasModifierValue.setText(String.valueOf(response.body().getGasModifier()));
                    tvGasValue.setText(String.valueOf(response.body().getGas()));
                    tvMineralsModifierValue.setText(String.valueOf(response.body().getMineralsModifier()));
                    tvMineralsValue.setText(String.valueOf(response.body().getMinerals()));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("App", "onFailure: Echec de la récupération des infos du joueur");
                // on affiche le feedback comme quoi la création du compte a échoué
                Toast.makeText(getApplicationContext(),"Echec de la récupération des infos du joueur",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
