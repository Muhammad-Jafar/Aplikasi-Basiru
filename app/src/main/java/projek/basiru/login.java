package projek.basiru;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS );

        setContentView(R.layout.login);
        getSupportActionBar().hide();
    }

    public void login(View view) {
        Intent loginaplikasi = new Intent(login.this, MainActivity.class);
        startActivity(loginaplikasi);
    }

    public void daftardulu(View view) {
        Intent daftardulu = new Intent(login.this, registrasi.class);
        startActivity(daftardulu);
    }
}
