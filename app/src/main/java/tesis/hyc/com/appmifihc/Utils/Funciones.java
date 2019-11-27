package tesis.hyc.com.appmifihc.Utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import tesis.hyc.com.appmifihc.R;

public class Funciones {

    private static Dialog pd;

    public static void dialogProgress(Context context){
        pd = new Dialog(context, android.R.style.Theme_Black);
        View view = LayoutInflater.from(context).inflate(R.layout.remove_border, null);
        pd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pd.getWindow().setBackgroundDrawableResource(R.color.transparent);
        pd.setContentView(view);
        pd.setCancelable(false);
    }

    public static void setProgressShow(){
        pd.show();
    }
    public static void setProgressHide(){
        pd.hide();
    }

}
