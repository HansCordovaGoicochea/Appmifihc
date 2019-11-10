package tesis.hyc.com.appmifihc.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import tesis.hyc.com.appmifihc.R;

import static tesis.hyc.com.appmifihc.Utils.Constantes.POPUP_NOT_INTERNET;


public class CheckInternetAsyncTask extends AsyncTask<Void, Integer, Boolean> {

    private Context context;
    private AppCompatActivity activity;
    AlertDialog alertDialog;

    public CheckInternetAsyncTask(AppCompatActivity activity) {
        this.context = activity.getApplicationContext();
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... params) {

        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        assert cm != null;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();


        if (isConnected) {
            try {
                HttpURLConnection urlc = (HttpURLConnection)
                        (new URL("https://clients3.google.com/generate_204")
                                .openConnection());
                urlc.setRequestProperty("User-Agent", "Android");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                if (urlc.getResponseCode() == 204 &&
                        urlc.getContentLength() == 0)
                    return true;

            } catch (IOException e) {
                Log.e("TAG", "Error checking internet connection", e);
                return false;
            }
        } else {
            Log.d("TAG", "No network available!");
            return false;
        }


        return null;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        Log.d("TAG", "result" + result);
        POPUP_NOT_INTERNET = result;
        if(!result){
            // do ur code
            //abrir el dialogo de no hay internet
//            Toast.makeText(context, "No tiene internet", Toast.LENGTH_LONG).show();
            //calling this method to show our android custom alert dialog
            showAlertDialogButtonClicked();

        }

    }


    public void showAlertDialogButtonClicked() {

        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(false);
        View modal_nointernet = activity.getLayoutInflater().inflate(R.layout.modal_nointernet, null);
        builder.setView(modal_nointernet);
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

        Button ingresar = (Button) modal_nointernet.findViewById(R.id.buttonOk);
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.recreate();
            }
        });


    }



}