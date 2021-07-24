package projek.basiru;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import projek.basiru.auth.Client;
import projek.basiru.auth.ResponsesAuth;
import projek.basiru.auth.Service;
import projek.basiru.network.BaseResponse;
import projek.basiru.network.UploadService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class donasi extends AppCompatActivity
{
    TextInputEditText donasinama,
                      donasiprogram,
                      donasihp,
                      donasinominal,
                      donasipilihbank;

    MaterialButton donasikan, aplodbukti;
    ProgressBar lodingdonasi;
    ImageView imgThumb;

    private static final int PICK_IMAGE = 1;
    private static final int PERMISSION_REQUEST_STORAGE = 2;

    private static final String TYPE_1 = "multipart";
    private static final String TYPE_2 = "base64";

    private UploadService uploadService;
    private Uri uri;

    @Override
    protected void onCreate( Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program);
        getSupportActionBar().hide();

        //input teks donasi
        donasinama = findViewById(R.id.donasinama);
        donasiprogram = findViewById(R.id.donasiprogram);
        donasihp = findViewById(R.id.donasihp);
        donasinominal = findViewById(R.id.donasinominal);
        donasipilihbank = findViewById(R.id.donasipilihbank);
        aplodbukti = findViewById(R.id.aplodresi);
        aplodbukti.setOnClickListener(this::choosePhoto);
        imgThumb = findViewById(R.id.previewpic);

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
        else if (donasipilihbank.getText().toString().trim().isEmpty())
        {
            donasipilihbank.requestFocus();
            donasipilihbank.setError("Pilih bank tidak boleh kosong!");
        }

        else { storedatadonasi(); onClick(view); }
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
                                                          donasipilihbank.getText().toString());
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
                            Toast.makeText(getApplicationContext(), "" + response.body().getMsg(),Toast.LENGTH_SHORT).show();
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
                public void onFailure(@NotNull Call<ResponsesAuth> call, @NotNull Throwable t)
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


    //tombol klik
    public void onClick(View view)
    {
//        if(view == btnChoose) {
//            choosePhoto();
//        }else if(view == btnUpload1) {
//            if(uri != null) {
//                File file = FileUtils.getFile(this, uri);
//                uploadMultipart(file);
//            }else{
//                Toast.makeText(this, "You must choose the image", Toast.LENGTH_SHORT).show();
//            }
//        }
        if(view == aplodbukti)
        {
            if(uri != null)
            {
                Bitmap bitmap = null;
                try { bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri); }
                catch (IOException e) { e.printStackTrace(); }

                String encoded = ImageUtils.bitmapToBase64String(bitmap, 100);
                uploadBase64(encoded);
            }else{
                Toast.makeText(this, "You must choose the image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //method base64
    private void uploadBase64(String imgBase64)
    {
        uploadService = new UploadService();
        uploadService.uploadPhotoBase64(TYPE_2, imgBase64, new Callback()
        {
            @Override
            public void onResponse(Call call, Response response)
            {
                BaseResponse baseResponse = (BaseResponse) response.body();
                if(baseResponse != null) { Toast.makeText(donasi.this, baseResponse.getMessage(), Toast.LENGTH_SHORT).show(); }
            }
            @Override
            public void onFailure(Call call, Throwable t) { t.printStackTrace(); }
        });
    }

    private void choosePhoto(View view)
    {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_STORAGE);
        }
        else{ openGallery(); }
    }

    public void openGallery()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK)
        {
            if(data != null)
            {
                uri = data.getData();
                imgThumb.setImageURI(uri);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case PERMISSION_REQUEST_STORAGE:
                {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                { openGallery(); }
                return;
            }
        }
    }

    public void balikemenu(View view)
    {
        Intent balikemenu = new Intent(donasi.this, MainActivity.class);
        startActivity(balikemenu);
    }
}
