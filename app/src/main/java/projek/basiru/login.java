package projek.basiru;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class login extends AppCompatActivity {
    //captcha
    private final String Sitekey = "6LevYhcbAAAAAFTPaCAjCmmHclPtj2aMSabPRAnD";
    private final String Secretkey = "6LevYhcbAAAAAPkr8-D3Z1R-h2fDEAkefnxQvS5v";
    public String TAG = "Success";
    public String userResponToken;
    RequestQueue queue;

    //login
    TextInputLayout imel, sandi;
    MaterialButton loginapp, registerapp;
    private String simel, ssandi;
    private final String url_login = "https://www.basirusamalewa.com/Loginapi.php";
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //objek XML
        loginapp = findViewById(R.id.login);
        imel = findViewById(R.id.inputimel);
        sandi = findViewById(R.id.inputsandi);
        registerapp = findViewById(R.id.daftardulu);

        //logindatabase
        queue = Volley.newRequestQueue(getApplicationContext());
    }

    public void login(View view)
    {
        //Captcha
        SafetyNet.getClient(this).verifyWithRecaptcha(Sitekey)
                .addOnSuccessListener (this, response ->
                {
                    if (!response.getTokenResult().isEmpty())
                    { handleSiteVerify(response.getTokenResult()); }
                })
                .addOnFailureListener(this, new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof ApiException) {
                            ApiException apiException = (ApiException) e;
                            Log.d(TAG, "Error message: " +
                                    CommonStatusCodes.getStatusCodeString(apiException.getStatusCode()));
                        } else {
                            Log.d(TAG, "Unknown type of error: " + e.getMessage());
                        }
                    }
                });

        //login
        final String username = imel.getEditText().toString().trim();
        final String katasandi = sandi.getEditText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_login,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        if (response.contains(TAG))
                        {
                            Intent loginaplikasi = new Intent(login.this, MainActivity.class);
                            startActivity(loginaplikasi);
                        }
                        else { Toast.makeText(login.this, "Email atau sandi tidak valid", Toast.LENGTH_LONG).show(); }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(login.this, "Gagal mengakses server", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(url_login, username);
                params.put(url_login, katasandi);
                return params;
            }
        };

        queue.add(stringRequest);
        //login
    }

    protected  void handleSiteVerify(final String responseToken)
    {
        //it is google recaptcha siteverify server
        //you can place your server url
        String url = "https://www.google.com/recaptcha/api/siteverify";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getBoolean("success"))
                            { Toast.makeText(getApplicationContext(),String.valueOf(jsonObject.getBoolean("success")),Toast.LENGTH_LONG).show();}
                            else { Toast.makeText(getApplicationContext(),String.valueOf(jsonObject.getString("error-codes")),Toast.LENGTH_LONG).show(); }
                        }
                        catch (Exception ex)
                        {
                            Log.d(TAG, "JSON exception: " + ex.getMessage());

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error message: " + error.getMessage());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("secret", Secretkey);
                params.put("response", responseToken);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    public void daftardulu(View view)
    {
        Intent daftardulu = new Intent(login.this, registrasi.class);
        startActivity(daftardulu);
    }
}
