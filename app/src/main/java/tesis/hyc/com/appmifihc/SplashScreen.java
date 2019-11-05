package tesis.hyc.com.appmifihc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import tesis.hyc.com.appmifihc.Animaciones.ProgressSplashAnimation;
import tesis.hyc.com.appmifihc.Utils.CheckInternetAsyncTask;

import static tesis.hyc.com.appmifihc.Utils.Constantes.POPUP_NOT_INTERNET;

public class SplashScreen extends AppCompatActivity {

    ProgressBar progressBar;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        progressBar = findViewById(R.id.pgSplash);
        textView = findViewById(R.id.txtSplash);

        progressBar.setMax(100);
        progressBar.setScaleY(3f);
        progressAnimation();


    }

    private void progressAnimation() {
        ProgressSplashAnimation progressSplashAnimation = new ProgressSplashAnimation(SplashScreen.this, progressBar, textView, 0f, 100f);
        progressSplashAnimation.setDuration(4500);
        progressBar.setAnimation(progressSplashAnimation);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new CheckInternetAsyncTask(SplashScreen.this).execute();

    }



}
