package projek.basiru;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class Tentangapk extends AppCompatActivity
{
    @Override
    protected void onCreate( Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tentangapk);
        Objects.requireNonNull(getSupportActionBar()).hide();

    }

    public void keluartentang(View view)
    {
        Intent keluar = new Intent(Tentangapk.this, MainActivity.class);
        startActivity(keluar);
    }
}
