package tesis.hyc.com.appmifihc.Animaciones;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;
import android.widget.TextView;

import tesis.hyc.com.appmifihc.ActividadPrincipal;

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
        progressBar.setProgress((int)value);
        textView.setText((int)value+" %");

        if(value == to){
            Intent intent = new Intent(context, ActividadPrincipal.class);
            context.startActivity(intent);

            //Remove activity
            ((Activity) context).finish();

        }
    }
}
