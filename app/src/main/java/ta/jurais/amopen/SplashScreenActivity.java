package ta.jurais.amopen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import java.util.HashMap;

import ta.jurais.amopen.util.SessionManager;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;

    SessionManager session;
    int posisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //cek login
        session = new SessionManager(SplashScreenActivity.this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                session.checkLogin();

                if(!session.isLoggedIn()){
                    finish();
                }

                //ambil data user
                HashMap<String, String> user = session.getUserDetails();
                String id_jenis = user.get(SessionManager.KEY_ID_JENIS);

                if(id_jenis == null){
                    posisi = 0;
                }
                else{
                    posisi = Integer.valueOf(id_jenis);
                }

                switch(posisi) {
                    case 1 :
                        startActivity(new Intent(SplashScreenActivity.this, AdminActivity.class));
                        // tutup activity
                        finish();
                        break; // optional

                    case 2 :
                        startActivity(new Intent(SplashScreenActivity.this, MahasiswaActivity.class));
                        // tutup activity
                        finish();
                        break; // optional

                    case 3 :
                        startActivity(new Intent(SplashScreenActivity.this, StaffActivity.class));
                        // tutup activity
                        finish();
                        break; // optional
                    case 4 :
                        startActivity(new Intent(SplashScreenActivity.this, DosenActivity.class));
                        // tutup activity
                        finish();
                        break; // optional
                    default : // Optional
                        //.. tidak ada
                }




            }
        }, SPLASH_TIME_OUT);
    }
}
