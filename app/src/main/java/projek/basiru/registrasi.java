package projek.basiru;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;

import projek.basiru.auth.Client;
import projek.basiru.auth.ResponsesAuth;
import projek.basiru.auth.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class registrasi extends AppCompatActivity
{

    TextInputEditText namanya, imelnya, sandinya;
    MaterialButton daftarnow;
    CircularProgressIndicator lodingregis;

    @Override
    protected void onCreate(Bundle savedIsntanceState){
        super.onCreate(savedIsntanceState);

        setContentView(R.layout.registrasi);
        getSupportActionBar().hide();

        //inisialisasi variabel
        namanya = findViewById(R.id.daftarnama);
        imelnya = findViewById(R.id.daftarimel);
        sandinya = findViewById(R.id.daftarsandi);
        lodingregis = findViewById(R.id.lodingdaftar);
        daftarnow = findViewById(R.id.Daftarsekarang);
        daftarnow.setOnClickListener(this::registrasi);
    }

    public void registrasi(View view)
    {
        if (namanya.getText().toString().trim().isEmpty())
        {
            namanya.requestFocus();
            namanya.setError("Silahkan isi nama anda");
        }
        else if (imelnya.getText().toString().trim().isEmpty())
        {
            imelnya.requestFocus();
            imelnya.setError("Silahkan isi email anda");
        }
        else if (sandinya.getText().toString().trim().isEmpty())
        {
            sandinya.requestFocus();
            sandinya.setError("Silahkan isi sandi anda");
        }
        else
        {
            ceksandi();
        }
    }

    void ceksandi()
    {
        lodingregis.setVisibility(View.VISIBLE);
        try
        {
            Client client = new Client();
            Service apiservice = new Client().getClient().create(Service.class);
            Call<ResponsesAuth> call = apiservice.registrasi(namanya.getText().toString(),
                                                            imelnya.getText().toString(),
                                                            sandinya.getText().toString());
            call.enqueue(new Callback<ResponsesAuth>()
            {
                @Override
                public void onResponse(Call<ResponsesAuth> call, retrofit2.Response<ResponsesAuth> response)
                {
                    if (response.isSuccessful())
                    {
                        ResponsesAuth auth = response.body();

                        if (auth.getMsg().equals("Registrasi Berhasil"))
                        {
                            Intent registrasi = new Intent(registrasi.this, login.class );
                            startActivity(registrasi);
                            lodingregis.setVisibility(View.GONE);
                            finish();
                        }
                        else
                        {
                            lodingregis.setVisibility(View.GONE);
                            Toast.makeText(registrasi.this, "Data gagal tersimpan", Toast.LENGTH_SHORT).show();;
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponsesAuth> call, Throwable t)
                {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(registrasi.this, "Server tidak merespon", Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e)
        {
            lodingregis.setVisibility(View.GONE);
            Toast.makeText(registrasi.this,"Gagal menghubungkan!", Toast.LENGTH_LONG).show();
        }
    }

    public void loginbalik(View view) {
        Intent loginbalik = new Intent(registrasi.this, login.class);
        startActivity(loginbalik);
    }
}
