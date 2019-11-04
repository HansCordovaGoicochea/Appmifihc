package tesis.hyc.com.appmifihc.Animaciones;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import tesis.hyc.com.appmifihc.ActividadPrincipal;
import tesis.hyc.com.appmifihc.LoginActivity;
import tesis.hyc.com.appmifihc.R;
import tesis.hyc.com.appmifihc.Utils.CheckInternetAsyncTask;

import static tesis.hyc.com.appmifihc.Utils.Constantes.POPUP_NOT_INTERNET;

public class ProgressSplashAnimation extends Animation {

    private Context context;
    private ProgressBar progressBar;
    private TextView textView;
    private float from;
    private float to;


    public ProgressSplashAnimation(Context context, ProgressBar progressBar, TextView textView, float from, float to) {

        this.context = context;
        this.progressBar = progressBar;
        this.textView = textView;
        this.from = from;
        this.to = to;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);

        float value = from + (to - from) * interpolatedTime;
        progressBar.setProgress((int) value);
        textView.setText((int) value + " %");

        if (value == to && POPUP_NOT_INTERNET) {
            Intent intent = new Intent(context, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(intent);
//            Toast.makeText(context, "ssssssssssss", Toast.LENGTH_LONG).show();
            //Remove activity
            ((Activity) context).finish();

        }
    }

}
