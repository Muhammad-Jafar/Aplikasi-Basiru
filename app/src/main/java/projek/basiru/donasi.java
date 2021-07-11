package projek.basiru;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import projek.basiru.auth.Client;
import projek.basiru.auth.ResponsesAuth;
import projek.basiru.auth.Service;
import retrofit2.Call;
import retrofit2.Callback;

public class donasi extends AppCompatActivity
{
    TextInputEditText donasinama,
                      donasiprogram,
                      donasihp,
                      donasinominal,
                      donasibank;

    MaterialButton donasikan;

    ProgressBar lodingdonasi;

    @Override
    protected void onCreate( Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program);
        getSupportActionBar().hide();

        //input teks donnasi
        donasinama = findViewById(R.id.donasinama);
        donasiprogram = findViewById(R.id.donasiprogram);
        donasihp = findViewById(R.id.donasihp);
        donasinominal = findViewById(R.id.donasinominal);
        donasibank = findViewById(R.id.donasipilihbank);

        //loding
        lodingdonasi = findViewById(R.id.lodingdonasi);
        lodingdonasi.setVisibility(View.GONE);

        //tombol donasi
        donasikan = findViewById(R.id.donasi);
        donasikan.setOnClickListener(this::donasikan);

    }

    public void donasikan(View view)
    {
        if (donasinama.getText().toString().trim().isEmpty())
        {
            donasinama.requestFocus();
            donasinama.setError("Nama tidak boleh kosong!");
        }
        else if (donasiprogram.getText().toString().trim().isEmpty())
        {
            donasiprogram.requestFocus();
            donasiprogram.setError("Nama program tidak boleh kosong!");
        }
        else if (donasihp.getText().toString().trim().isEmpty())
        {
            donasihp.requestFocus();
            donasihp.setError("Nomor HP tidak boleh kosong!");
        }
        else if (donasinominal.getText().toString().trim().isEmpty())
        {
            donasinominal.requestFocus();
            donasinominal.setError("Nominal harus diisi!");
        }
        else if (donasibank.getText().toString().trim().isEmpty())
        {
            donasibank.requestFocus();
            donasibank.setError("Pilih bank tidak boleh kosong!");
        }

        else { storedatadonasi(); }

    }

    void storedatadonasi()
    {
        lodingdonasi.setVisibility(View.VISIBLE);
        try {
            Client Client = new Client();
            Service apiService = Client.getClient().create(Service.class);
            Call<ResponsesAuth> call = apiService.donasi( donasinama.getText().toString(),
                                                          donasiprogram.getText().toString(),
                                                          donasihp.getText().toString(),
                                                          donasinominal.getText().toString(),
                                                          donasibank.getText().toString());
            call.enqueue(new Callback<ResponsesAuth>()
            {
                @Override
                public void onResponse(Call<ResponsesAuth> call, retrofit2.Response<ResponsesAuth> response)
                {
                    if (response.isSuccessful())
                    {
                        ResponsesAuth auth = response.body();

                        if (auth.getMsg().equals("Donasi Berhasil!"))
                        {
                            Toast.makeText(getApplicationContext(), "Donasi Berhasil",Toast.LENGTH_SHORT).show();
                            Intent go = new Intent(donasi.this, MainActivity.class);
                            startActivity(go);
                            lodingdonasi.setVisibility(View.GONE);
                            finish();
                        }
                        else
                        {
                            lodingdonasi.setVisibility(View.GONE);
                            Toast.makeText(donasi.this, "Donasi Gagal!", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponsesAuth> call, Throwable t)
                {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(donasi.this, "Server tidak merespon", Toast.LENGTH_LONG).show();
                    lodingdonasi.setVisibility(View.GONE);
                }
            });

        } catch (Exception e) {
            lodingdonasi.setVisibility(View.GONE);
            Toast.makeText(donasi.this, "Gagal menghubungkan!", Toast.LENGTH_LONG).show();
        }
    }

    public void balikemenu(View view)
    {
        Intent balikemenu = new Intent(donasi.this, MainActivity.class);
        startActivity(balikemenu);
    }
}
