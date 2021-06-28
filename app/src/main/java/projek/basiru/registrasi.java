package projek.basiru;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class registrasi extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedIsntanceState){
        super.onCreate(savedIsntanceState);

        setContentView(R.layout.registrasi);
        getSupportActionBar().hide();
    }

    public void registrasi(View view) {

        Intent registrasi = new Intent(registrasi.this, login.class );
        startActivity(registrasi);
    }

    public void loginbalik(View view) {
        Intent loginbalik = new Intent(registrasi.this, login.class);
        startActivity(loginbalik);
    }
}
