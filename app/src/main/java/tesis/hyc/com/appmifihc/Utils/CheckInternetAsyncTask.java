package tesis.hyc.com.appmifihc.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import tesis.hyc.com.appmifihc.R;

import static tesis.hyc.com.appmifihc.Utils.Constantes.POPUP_NOT_INTERNET;


public class CheckInternetAsyncTask extends AsyncTask<Void, Integer, Boolean> {

    private Context context;

    public CheckInternetAsyncTask(Context context) {
        this.context = context;
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
            Toast.makeText(context.getApplicationContext(), "No tiene internet", Toast.LENGTH_LONG).show();
            //calling this method to show our android custom alert dialog
//            showCustomDialog();
        }

    }

    private void showCustomDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = ((Activity)context).findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.modal_nointernet, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(context.getApplicationContext());

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }


}