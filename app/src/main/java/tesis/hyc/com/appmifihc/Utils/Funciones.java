package tesis.hyc.com.appmifihc.Utils;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
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


    public static class Font extends AppCompatActivity {
        public static void persian_iran_font(final Context context, final View v) {
            try {
                if (v instanceof ViewGroup) {
                    ViewGroup vg = (ViewGroup) v;
                    for (int i = 0; i < vg.getChildCount(); i++) {
                        View child = vg.getChildAt(i);
                        persian_iran_font(context, child);
                    }
                } else if (v instanceof TextView) {
                    ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Poppins-Light.ttf"));
                }
            } catch (Exception e) {
            }
        }
    }

    public static String FACEBOOK_URL = "https://www.facebook.com/mifinancieraperu";
    public static String FACEBOOK_PAGE_ID = "1767701070176889";

    //method to get the right URL to use in the intent
    public static String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
//            if (versionCode >= 3002850) { //newer versions of fb app
//                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
//            } else { //older versions of fb app
//                return "fb://page/" + FACEBOOK_PAGE_ID;
//            }
            if(!FACEBOOK_PAGE_ID.isEmpty()) {
                // open the Facebook app using facebookID (fb://profile/facebookID or fb://page/facebookID)
                return "fb://page/" + FACEBOOK_PAGE_ID;
            } else if (versionCode >= 3002850) {
                // open Facebook app using facebook url
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else {
                // Facebook is not installed. Open the browser
                return ""+FACEBOOK_URL;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }

    public static boolean isAppInstalled(Context context) {
        try {
            context.getPackageManager().getApplicationInfo("com.facebook.katana", 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

}
