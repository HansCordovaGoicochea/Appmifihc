package tesis.hyc.com.appmifihc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
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
        ProgressSplashAnimation progressSplashAnimation = new ProgressSplashAnimation(this, progressBar, textView, 0f, 100f);
        progressSplashAnimation.setDuration(6000);
        progressBar.setAnimation(progressSplashAnimation);
    }

    @Override
    protected void onStart() {
        super.onStart();



    }

    @Override
    protected void onResume() {
        super.onResume();

        new CheckInternetAsyncTask(getApplicationContext()).execute();

        Toast.makeText(this, "ffffffffffffffff"+POPUP_NOT_INTERNET, Toast.LENGTH_LONG).show();
        if (!POPUP_NOT_INTERNET){
            showCustomDialog();
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    private void showCustomDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.modal_nointernet, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }



}
