package projek.basiru;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
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

import java.io.File;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import projek.basiru.auth.Client;
import projek.basiru.auth.ResponsesAuth;
import projek.basiru.auth.Service;
import projek.basiru.network.BaseResponse;
import projek.basiru.network.UploadService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class donasi extends AppCompatActivity implements View.OnClickListener
{
    TextInputEditText donasinama,
                      donasiprogram,
                      donasihp,
                      donasinominal,
                      donasipilihbank;

    MaterialButton donasikan, aplodbukti;
    ProgressBar lodingdonasi;

    //aplodresi
    public static final int REQUEST_IMAGE = 100;
    Uri uri;
    private ImageView imgThumb;
    private static final int PICK_IMAGE = 1;
    private static final int PERMISSION_REQUEST_STORAGE = 2;
    private static final String TYPE_1 = "multipart";

    @Override
    protected void onCreate( Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //input teks donnasi
        donasinama = findViewById(R.id.donasinama);
        donasiprogram = findViewById(R.id.donasiprogram);
        donasihp = findViewById(R.id.donasihp);
        donasinominal = findViewById(R.id.donasinominal);
        donasipilihbank = findViewById(R.id.donasipilihbank);
        aplodbukti = findViewById(R.id.aplodresi);
        imgThumb = (ImageView) findViewById(R.id.img_thumb);
        aplodbukti.setOnClickListener(this::choosePhoto);


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

    @Override
    public void onClick(View view)
    {
        if (view == aplodbukti)
        {
            choosePhoto(view);
        }
        else if (view == donasikan)
        {
            if (uri != null)
            {
                File file = FileUtils.getFile(this, uri);
                uploadMultipart(file);
            } else { Toast.makeText(this, "You must choose the image", Toast.LENGTH_SHORT).show(); }
        }
//        else if(view == btnUpload2)
//        {
//            if(uri != null) {
//                Bitmap bitmap = null;
//                try {
//                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                String encoded = ImageUtils.bitmapToBase64String(bitmap, 100);
//                uploadBase64(encoded);
//            }else{
//                Toast.makeText(this, "You must choose the image", Toast.LENGTH_SHORT).show();
//            }
//        }
//        }
    }

    public void choosePhoto(View view)
    {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_STORAGE);
        }
        else { openGallery(); }
    }

    public void openGallery()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
    }

    public void uploadMultipart(File file)
    {
        RequestBody photoBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part photoPart = MultipartBody.Part.createFormData("photo", file.getName(), photoBody);

        RequestBody action = RequestBody.create(MediaType.parse("text/plain"), TYPE_1);
        UploadService uploadService = new UploadService();
        uploadService.uploadPhotoMultipart(action, photoPart, new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                BaseResponse baseResponse = (BaseResponse) response.body();
                if(baseResponse != null) {
                    Toast.makeText(donasi.this, baseResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call call, Throwable t) { t.printStackTrace(); }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if(data != null) {
                uri = data.getData();
                imgThumb.setImageURI(uri);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                { openGallery(); }
                return ;
            }
        }
    }


    public void balikemenu(View view)
    {
        Intent balikemenu = new Intent(donasi.this, MainActivity.class);
        startActivity(balikemenu);
    }
}
