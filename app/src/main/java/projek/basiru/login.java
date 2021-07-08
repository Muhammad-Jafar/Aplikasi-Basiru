package projek.basiru;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
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
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import projek.basiru.auth.Client;
import projek.basiru.auth.ResponsesAuth;
import projek.basiru.auth.Service;
import retrofit2.Call;
import retrofit2.Callback;


public class login extends AppCompatActivity
{
    //captcha
    private final String Sitekey = "6LevYhcbAAAAAFTPaCAjCmmHclPtj2aMSabPRAnD";
    private final String Secretkey = "6LevYhcbAAAAAPkr8-D3Z1R-h2fDEAkefnxQvS5v";
    public String TAG = "Success";
    RequestQueue queue;

    //login
    TextInputEditText imel, sandi;
    MaterialButton loginapp, registerapp;
    ProgressBar loding;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        getSupportActionBar().hide();

        loginapp = findViewById(R.id.logindulu);
        imel = findViewById(R.id.inputimel);
        sandi = findViewById(R.id.inputsandi);
        registerapp = findViewById(R.id.daftardulu);
        loding = findViewById(R.id.lodinganim);
        loginapp.setOnClickListener(this::logindulu);
        loding.setVisibility(View.GONE);
    }

    public void logindulu(View view)
    {

        if (imel.getText().toString().trim().isEmpty())
        {
            imel.requestFocus();
            imel.setError("Email tidak boleh kosong!");
        }
        else if (sandi.getText().toString().trim().isEmpty())
        {
            sandi.requestFocus();
            sandi.setError("Kata Sandi tidak boleh kosong!");
        }
        else { cekPassword(); }
    }

    void cekPassword()
    {
        loding.setVisibility(View.VISIBLE);
        try {
            Client Client = new Client();
            Service apiService = Client.getClient().create(Service.class);
            Call<ResponsesAuth> call = apiService.login(imel.getText().toString(),
                                                        sandi.getText().toString());
            call.enqueue(new Callback<ResponsesAuth>()
            {
                @Override
                public void onResponse(Call<ResponsesAuth> call, retrofit2.Response<ResponsesAuth> response)
                {
                    if (response.isSuccessful())
                    {
                        ResponsesAuth auth = response.body();

                        if (auth.getMsg().equals("Login Berhasil!"))
                        {
                            captcha();
                            Intent go = new Intent(login.this, MainActivity.class);
                            startActivity(go);
                            loding.setVisibility(View.GONE);
                            finish();
                        }
                        else
                        {
                            loding.setVisibility(View.GONE);
                            Toast.makeText(login.this, "Email dan Kata Sandi tidak cocok!", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponsesAuth> call, Throwable t)
                {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(login.this, "Server tidak merespon", Toast.LENGTH_LONG).show();
                    loding.setVisibility(View.GONE);
                }
            });

        } catch (Exception e) {
            loding.setVisibility(View.GONE);
            Toast.makeText(login.this, "Gagal menghubungkan!", Toast.LENGTH_LONG).show();
        }
    }

    void captcha()
    {
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
