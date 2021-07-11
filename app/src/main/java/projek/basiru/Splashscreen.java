package projek.basiru;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class Splashscreen extends AppCompatActivity
{
    private int waktu_loading = 4000; //4000 = 4 detik
//    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        //handle splashscreen
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                //setelah loading maka akan langsung berpindah ke home activity
                Intent splashscreen = new Intent(Splashscreen.this, login.class);
                startActivity(splashscreen);
                finish();
            }
        },waktu_loading);

//        //handle session
//        sessionManager = new SessionManager(getApplicationContext());
//
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                sessionManager.checkLogin();
//                finish();
//            }
//        },5000);
    }
}
