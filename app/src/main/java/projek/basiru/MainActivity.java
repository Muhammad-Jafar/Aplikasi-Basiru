package projek.basiru;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import projek.basiru.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity
{
    //UI
    AppCompatButton logout, tentang, ubahprofil, gantisandi;
    TextView namalogin, imelogin, jumlah, namaprogram, mulaiprogram, deadlineprogram,statusprogram;
    MaterialButton keluar, balikemenu, donasikan;

    //share preference
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        findViewById(R.id.nav_view);

        //Bottom navigation bar
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_program, R.id.navigation_dashboard, R.id.navigation_profil)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        getSupportActionBar().hide();


        //share prefenrence data login user
        namalogin = findViewById(R.id.namaprofil);
        imelogin = findViewById(R.id.namaimel);
//        jumlah = findViewById(R.id.jumlahterkumpul);

//        if(getIntent().getStringExtra("email") != null)
//        {
//            String email = getIntent().getStringExtra("email");
//            imelogin.setText(email);
//        }

        namaprogram = findViewById(R.id.judulprogram);
        mulaiprogram = findViewById(R.id.mulaiprogram);
        deadlineprogram = findViewById(R.id.deadlineprogram);
        statusprogram = findViewById(R.id.statusprogram);
//        totaldonasi();

    }

    //API dashboard
    void totaldonasi()
    {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://basirusamalewa.com/Api/totaldonasi";
        JSONObject jsonBody = new JSONObject();
        final String requestBody = jsonBody.toString();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                                //menaruh data JSON kedalam variabel JSON Object
                                JSONObject jsonPost = new JSONObject(response.toString());

                                //men set data ke dalam tampilan
                                jumlah.setText(jsonPost.getString("nominal"));

                            } catch (JSONException e) { e.printStackTrace(); } }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.d("Error Response",error.toString());
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void tentangapk(View view)
    {
        Intent tentangapk = new Intent(MainActivity.this, Tentangapk.class);
        startActivity(tentangapk);
    }

    public void logoutacc(View view)
    {
        Intent logoutacc = new Intent(MainActivity.this, login.class);
        startActivity(logoutacc);
    }

    public void donasikan (View view)
    {
        Intent donasikan = new Intent(MainActivity.this, donasi.class);
        startActivity(donasikan);
    }

}