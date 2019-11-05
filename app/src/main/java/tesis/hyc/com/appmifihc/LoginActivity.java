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

public class LoginActivity extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;
    private static final int REQUEST_CALL_PHONE = 1;
    private static final int REQUEST_READ_CONTACTS = 2;
    private static final int REQUEST_WRITE_CONTACTS = 3;
    private static boolean isRationale = true;
    private static boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //permisos
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            askPermissions(true);
        }

        TextView tv = (TextView) findViewById(R.id.pass);
        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/Poppins-Regular.ttf");
        tv.setTypeface(face);
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
                for (int i = 0; i < permissions.length; i++) {
                    perms.put(permissions[i], grantResults[i]);
                }
                // Check for ACCESS_FINE_LOCATION
                if (perms.get(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    // All Permissions Granted
//                    startActivity(new Intent(PermissionsActivity.this, SplashActivity.class));
//                    finish();
                } else {
                    // Permission Denied
                    Toast.makeText(this, "Algunos permisos son denegados.", Toast.LENGTH_SHORT)
                            .show();
                    isFirst = false;
                    askPermissions(true);
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        }
    }

}
