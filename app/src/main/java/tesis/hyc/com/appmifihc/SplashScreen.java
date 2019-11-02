package tesis.hyc.com.appmifihc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import tesis.hyc.com.appmifihc.Animaciones.ProgressSplashAnimation;

public class SplashScreen extends AppCompatActivity {

    ProgressBar progressBar;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        progressBar = findViewById(R.id.pgSplash);
        textView = findViewById(R.id.txtSplash);

        progressBar.setMax(100);
        progressBar.setScaleY(3f);
        progressAnimation();
    }

    private void progressAnimation() {
        ProgressSplashAnimation progressSplashAnimation = new ProgressSplashAnimation(this, progressBar, textView, 0f, 100f);
        progressSplashAnimation.setDuration(8000);
        progressBar.setAnimation(progressSplashAnimation);
    }
}
