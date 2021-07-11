package projek.basiru;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class donasi extends AppCompatActivity
{
    @Override
    protected void onCreate( Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program);
        getSupportActionBar().hide();
    }


    public void balikemenu(View view)
    {
        Intent balikemenu = new Intent(donasi.this, MainActivity.class);
        startActivity(balikemenu);
    }
}
