package tesis.hyc.com.appmifihc;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tesis.hyc.com.appmifihc.Clases.Customer;
import tesis.hyc.com.appmifihc.io.MiApiAdapter;
import tesis.hyc.com.appmifihc.io.response.CustomerResponse;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.mindrot.jbcrypt.BCrypt;

import static tesis.hyc.com.appmifihc.Utils.Constantes.API_KEY;

public class LoginActivity extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;
    private static final int REQUEST_CALL_PHONE = 1;
    private static final int REQUEST_READ_CONTACTS = 2;
    private static final int REQUEST_WRITE_CONTACTS = 3;
    private static boolean isRationale = true;
    private static boolean isFirst = true;
    EditText username;
    EditText pass_user;
    TextView ingresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //permisos
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            askPermissions(true);
//        }

        username = findViewById(R.id.username_input);
        pass_user = findViewById(R.id.pass);
        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/Poppins-Regular.ttf");
        pass_user.setTypeface(face);
        ingresar = findViewById(R.id.btnIngresar);
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerCliente(username.getText().toString());
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().getItem(0).setCheckable(false);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_recents:
                        item.setCheckable(true);
                        Toast.makeText(LoginActivity.this, "Recents", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_favorites:
                        Toast.makeText(LoginActivity.this, "Favorites", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_nearby:
                        Toast.makeText(LoginActivity.this, "Nearby", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });



    }

    private void login(ArrayList<Customer> customers) {
        String pass = String.valueOf(pass_user.getText());
//        String hash = "$2y$10$cmhjBxhi6RwY14IvTxKHHOsiuUh6vjABRByN7P0ed9f.duD.k73QG".replaceFirst("2y", "2a");

        for (Customer member : customers){
            //Ver esta referecia para poder comparar las claves //libs/bcrypt
            //https://stackoverflow.com/questions/44614380/how-can-i-make-bcrypt-in-php-and-jbcrypt-in-java-compatible
            //https://github.com/patrickfav/bcrypt
            String pass_hash = member.passwd.replaceFirst("2y", "2a");
            boolean isSuccessful = BCrypt.checkpw(pass, pass_hash);
            if (isSuccessful){
                Log.e("asdas", "Login correcto");
                Toast.makeText(LoginActivity.this, "Login correcto", Toast.LENGTH_SHORT).show();
            }else{
                Log.e("asdas", "Login incorrecto");
                Toast.makeText(LoginActivity.this, "Login incorrecto", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void obtenerCliente(String username) {
        Call<CustomerResponse> call = MiApiAdapter.getApiService().getCustomerDetails("full", username, "JSON", API_KEY, 1);
        call.enqueue(new ResponsablesCallback());
    }

    class ResponsablesCallback implements Callback<CustomerResponse> {

        @Override
        public void onResponse(Call<CustomerResponse> call, Response<CustomerResponse> response) {
//            Log.e("RESPONSE", response.toString());
            if(response.isSuccessful()){
                CustomerResponse customerResponse = response.body();
                login(customerResponse.getCustomers());
            }else{

                Toast.makeText(LoginActivity.this, "Error en el formato de respuesta", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<CustomerResponse> call, Throwable t) {
            Log.e("Error-ache", t.getLocalizedMessage());
            Toast.makeText(LoginActivity.this, "Error: "+ t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
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
