package tesis.hyc.com.appmifihc;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tesis.hyc.com.appmifihc.Actividades.ActividadAgencias;
import tesis.hyc.com.appmifihc.Clases.Customer;
import tesis.hyc.com.appmifihc.Prefs.SessionPrefs;
import tesis.hyc.com.appmifihc.SingletonVolley.VolleyPeticiones;
import tesis.hyc.com.appmifihc.SingletonVolley.MySingleton;
import tesis.hyc.com.appmifihc.Utils.CheckInternetAsyncTask;
import tesis.hyc.com.appmifihc.Utils.Funciones;


public class LoginActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    boolean doubleBackToExitPressedOnce = false;
    private static final int REQUEST_CALL_PHONE = 1;
    private static final int REQUEST_READ_CONTACTS = 2;
    private static final int REQUEST_WRITE_CONTACTS = 3;
    private static boolean isRationale = true;
    private static boolean isFirst = true;
    EditText username;
    EditText pass_user;
    TextView ingresar;
    CheckBox cbRecordar;

    Dialog pd;

    Customer customer;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //permisos
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            askPermissions(true);<
//        }

        //Si se puede guardar el usuariox
        prefs = getSharedPreferences("PrefUserSave", Context.MODE_PRIVATE);

        dialogProgress();

        cbRecordar = findViewById(R.id.cbRecordar);
//        dualcamera1.isChecked()/
        username = findViewById(R.id.username_input);
        pass_user = findViewById(R.id.pass);
        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/Poppins-Regular.ttf");
        pass_user.setTypeface(face);
        ingresar = findViewById(R.id.btnIngresar);
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setProgressShow();
                obtenerCliente(username.getText().toString());
            }
        });

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_fb);
        bottomNavigationView.getMenu().getItem(1).setCheckable(false);
        Funciones.Font.persian_iran_font(LoginActivity.this, bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_ubicanos:

                        Intent intent = new Intent(LoginActivity.this, ActividadAgencias.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
//                        finish();
                        break;
                    case R.id.action_fb:
                        item.setCheckable(true);
                        if (Funciones.isAppInstalled(LoginActivity.this)) {
//                            Toast.makeText(getApplicationContext(), "facebook app already installed", Toast.LENGTH_SHORT).show();
                            Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                            String facebookUrl = Funciones.getFacebookPageURL(LoginActivity.this);
                            facebookIntent.setData(Uri.parse(facebookUrl));
                            startActivity(facebookIntent);

                        } else {
                            Toast.makeText(getApplicationContext(), "Facebook App no esta instalada.", Toast.LENGTH_SHORT).show();
                        }

//                        Toast.makeText(LoginActivity.this, "Nearby", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_contactanos:
                        Toast.makeText(LoginActivity.this, "Contáctanos", Toast.LENGTH_SHORT).show();
                        break;

                }
                return true;
            }
        });


        boolean recordar = prefs.getBoolean("recordar_usuario", false);
        String usuarioshared = prefs.getString("usuario", "");
        // primera peticion al servidor
        if (recordar) {
            username.setText(usuarioshared);
            cbRecordar.setChecked(recordar);
        }

        new CheckInternetAsyncTask(LoginActivity.this).execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        bottomNavigationView.getMenu().getItem(0).setCheckable(false);
    }

    public void dialogProgress(){
        pd = new Dialog(this, android.R.style.Theme_Black);
        View view = LayoutInflater.from(this).inflate(R.layout.remove_border, null);
        pd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pd.getWindow().setBackgroundDrawableResource(R.color.transparent);
        pd.setContentView(view);
        pd.setCancelable(false);
    }

    public void setProgressShow(){
        pd.show();
    }
    public void setProgressHide(){
        pd.hide();
    }

    private void login(JSONObject jsonChildNode) {
        String pass = String.valueOf(pass_user.getText());
//        String hash = "$2y$10$cmhjBxhi6RwY14IvTxKHHOsiuUh6vjABRByN7P0ed9f.duD.k73QG".replaceFirst("2y", "2a");

        String passwd = jsonChildNode.optString("passwd");

        //Ver esta referecia para poder comparar las claves //libs/bcrypt
        //https://stackoverflow.com/questions/44614380/how-can-i-make-bcrypt-in-php-and-jbcrypt-in-java-compatible
        //https://github.com/patrickfav/bcrypt
        String pass_hash = passwd.replaceFirst("2y", "2a");
        boolean isSuccessful = BCrypt.checkpw(pass, pass_hash);
        if (isSuccessful){
            Log.e("asdas", "Login correcto");
            Toast.makeText(LoginActivity.this, "Login correcto", Toast.LENGTH_SHORT).show();

            Integer id_customer = jsonChildNode.optInt("id");
            String num_document = jsonChildNode.optString("num_document");
            String firstname = jsonChildNode.optString("firstname");
            String telefono_celular = jsonChildNode.optString("telefono_celular");
            String direccion = jsonChildNode.optString("direccion");
            String email = jsonChildNode.optString("email");
            String fecha_nacimiento = jsonChildNode.optString("birthday");


//            customer.delete();
            Customer.deleteAll(Customer.class);
            customer = new Customer(id_customer, num_document, firstname, email, telefono_celular, direccion, fecha_nacimiento);
            customer.save();

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("usuario", "");
            editor.putBoolean("recordar_usuario", false);
            if(cbRecordar.isChecked()){
                editor.putString("usuario", num_document);
                editor.putBoolean("recordar_usuario", true);
            }
            editor.apply();

            // Guardar afiliado en preferencias
            SessionPrefs.get(LoginActivity.this).saveCustomer(customer);

            Intent intent = new Intent(LoginActivity.this, ActividadPrincipal.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            //Remove activity
            finish();
        }else{
            Log.e("asdas", "Login incorrecto");
            Toast.makeText(LoginActivity.this, "Login incorrecto", Toast.LENGTH_SHORT).show();
            setProgressHide();
        }


    }

    public boolean validate() {
        boolean valid = true;

        String _username = username.getText().toString();
        String _pass_user = pass_user.getText().toString();

        if (_username.isEmpty() || _username.length() < 8) {
            username.setError("Al menos 8 caracteres");
            valid = false;
        } else {
            username.setError(null);
        }

        if (_pass_user.isEmpty() || _pass_user.length() < 6) {
            pass_user.setError("Al menos 6 caracteres");
            valid = false;
        } else {
            pass_user.setError(null);
        }


        return valid;
    }

    public void onSignupFailed() {
        Toast.makeText(getApplicationContext(), "Llene los datos correctamente", Toast.LENGTH_LONG).show();
        setProgressHide();
    }

    //consultar al cliente
    private void obtenerCliente(String numero_doc) {

        if (!validate()) {
            onSignupFailed();
            return;
        }

        String url = VolleyPeticiones.getCustomerApi(numero_doc);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonMainNode = response.getJSONArray("customers");

                            JSONObject jsonChildNode = jsonMainNode.getJSONObject(0);

                            login(jsonChildNode);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(LoginActivity.this,"El servidor ha tardado demasiado tiempo en responder",Toast.LENGTH_SHORT).show();
                error.printStackTrace();
                setProgressHide();
            }
        });


          /*Se definen las políticas para la petición realizada. Recibe como argumento una instancia de la clase
        DefaultRetryPolicy, que recibe como parámetros de entrada el tiempo inicial de espera para la respuesta,
        el número máximo de intentos, y el multiplicador de retardo de envío por defecto.*/
        request.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        /*Se declara e inicializa una variable de tipo RequestQueue, encargada de crear
        una nueva petición en la cola del servicio web.*/
        MySingleton.getInstance(LoginActivity.this).addToRequestQueue(request);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Haga clic en ATRÁS nuevamente para salir", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);

    }

    private void askPermissions(boolean isForOpen) {
        isRationale = false;
        List permissionsRequired = new ArrayList();

        final List<String> permissionsList = new ArrayList<String>();
        if (!checkPermission(permissionsList, Manifest.permission.ACCESS_COARSE_LOCATION))
            permissionsRequired.add("Location");
        if (!checkPermission(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION))
            permissionsRequired.add("Location");

        if (permissionsList.size() > 0 && !isRationale) {
            if (permissionsRequired.size() > 0) {

            }
            if (isForOpen) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    ActivityCompat.requestPermissions(this, permissionsList.toArray(new String[permissionsList.size()]),
                            11);
                }
            }

        } else if (isRationale) {
            if (isForOpen) {

                new AlertDialog.Builder(this, R.style.Theme_AppCompat)
                        .setTitle("Alerta de permiso")
                        .setMessage("Debe otorgar permisos manualmente. Vaya a permiso y otorgue todos los permisos.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                intent.setData(uri);
                                startActivityForResult(intent, 123);
                            }
                        })
                        .show();
            }
        }
//        else {
//            startActivity(new Intent(PermissionsActivity.this, SplashActivity.class));
//            finish();
//        }
    }

    private boolean checkPermission(List permissionsList, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission);
                // Check for Rationale Option
                if (!isFirst) {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                        isRationale = true;
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 11:
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial

                // Fill with results
//                for (int i = 0; i < permissions.length; i++) {
//                    perms.put(permissions[i], grantResults[i]);
//                }
//                // Check for ACCESS_FINE_LOCATION
//                if (perms.get(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
//                    // All Permissions Granted
////                    startActivity(new Intent(PermissionsActivity.this, SplashActivity.class));
////                    finish();
//                } else {
//                    // Permission Denied
//                    Toast.makeText(this, "Algunos permisos son denegados.", Toast.LENGTH_SHORT)
//                            .show();
//                    isFirst = false;
//                    askPermissions(true);
//                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        }
    }

}
