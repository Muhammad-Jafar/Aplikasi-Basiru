package projek.basiru;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;

public class login extends AppCompatActivity {
    //captcha
    final String Sitekey = "6LevYhcbAAAAAFTPaCAjCmmHclPtj2aMSabPRAnD";
    final String Secretkey = "6LevYhcbAAAAAPkr8-D3Z1R-h2fDEAkefnxQvS5v";
    public String TAG = "login";
    public String userResponToken;
    Button btnreq;
    RequestQueue queue;

    //login
    TextInputLayout imel, sandi;
    MaterialButton loginapp, registerapp;
    private String simel, ssandi;
    String url_login = "https://www.basirusamalewa.com/auth.php";
//    JSONParser jsonParser = new JSONParser();
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        btnreq = findViewById(R.id.login);
        imel = findViewById(R.id.inputimel);
        sandi = findViewById(R.id.inputsandi);
        registerapp = findViewById(R.id.daftardulu);
        Objects.requireNonNull(getSupportActionBar()).hide();


        //captcha
        queue = Volley.newRequestQueue(getApplicationContext());
    }

    public void login(View view)
    {
        SafetyNet.getClient(this).verifyWithRecaptcha(Sitekey)
                .addOnSuccessListener(this, response -> {
                    if (!response.getTokenResult().isEmpty()) {
                        handleSiteVerify(response.getTokenResult());
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
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
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getBoolean("success"))
                            {
                                //code logic when captcha returns true Toast.makeText(getApplicationContext(),String.valueOf(jsonObject.getBoolean("success")),Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(),String.valueOf(jsonObject.getString("error-codes")),Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception ex)
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


        //kodingan login
//        simel = imel.getEditText().toString().trim();
//        ssandi = sandi.getEditText().toString().trim();
//
//        if (!simel.equals("") && !ssandi.equals(""))
//        {
//            StringRequest permintaan = new StringRequest(Request.Method.POST, url_login, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    if (response.equals("success")) {
//                        Intent loginaplikasi = new Intent(login.this, MainActivity.class);
//                        startActivity(loginaplikasi);
//                        finish();
//                    } else if (response.equals("failure")) {
//                        Toast.makeText(login.this, "Email atau sandi anda salah", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }, new Response.ErrorListener()
//            {
//                @Override
//                public void onErrorResponse(VolleyError error)
//                {
//                    Toast.makeText(login.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
//                }
//            });
////            {
////                @Override
////                protected Map<String, String> getParams() throws AuthFailureError
////                {
////                    Map<String, string> data = new HashMap<>();
////                    data.put("email", simel);
////                    data.put("password", ssandi);
////                    return data;
////                }
////            };
//            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//            requestQueue.add(permintaan);
//
//        }
//        else {
//            Toast.makeText(this, "email atau sandi tidak boleh kosong", Toast.LENGTH_SHORT).show();
//        }

    public void daftardulu(View view)
    {
        Intent daftardulu = new Intent(login.this, registrasi.class);
        startActivity(daftardulu);
    }
}
