package tesis.hyc.com.appmifihc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import customfonts.CustomTypefaceSpan;
import tesis.hyc.com.appmifihc.Fragmentos.FragmentoInicio;
import tesis.hyc.com.appmifihc.Fragmentos.FragmentoPerfil;
import tesis.hyc.com.appmifihc.Prefs.SessionPrefs;
import tesis.hyc.com.appmifihc.Utils.CheckInternetAsyncTask;

public class ActividadPrincipal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    boolean doubleBackToExitPressedOnce = false;
    private BottomNavigationView bottomNavigationView;
    public FragmentManager fragmentManager;

    public TextView txtUserName;
    public TextView txtDocument;

    SharedPreferences prefs;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            fragmentManager = getSupportFragmentManager();
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigationMyProfile:
                    FragmentoPerfil test1 = (FragmentoPerfil) getSupportFragmentManager().findFragmentByTag("FragmentoPerfil");
                    if (test1 != null && test1.isVisible()) {
                        //Si esta visible el fragmento no hacer nada

                    }
                    else {
                        Bundle args = new Bundle();
                        args.putString(FragmentoPerfil.ARG_SECTION_TITLE, "Perfil");
                        fragment = FragmentoPerfil.newInstance("", "");
                        fragment.setArguments(args);

                        FragmentTransaction fragmentTransaction2 = fragmentManager.beginTransaction();

                        // Replace whatever is in the fragment_container view with this fragment,
                        // and add the transaction to the back stack so the user can navigate back
                        fragmentTransaction2.replace(R.id.contenedor_principal, fragment, "FragmentoPerfil");
                        fragmentTransaction2.addToBackStack(null);
                        fragmentTransaction2.commit();
                        setTitle("Perfil"); // Setear título actual
//                Toast.makeText(getApplicationContext(), "Carrrioto", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                case R.id.navigationHome:
                    FragmentoInicio test = (FragmentoInicio) getSupportFragmentManager().findFragmentByTag("FragmentoInicio");
                    if (test != null && test.isVisible()) {
                        //Si esta visible el fragmento de inicio no hacer nada

                    }
                    else {
                        Bundle args = new Bundle();
                        args.putString(FragmentoInicio.ARG_SECTION_TITLE, "Inicio");
                        fragment = FragmentoInicio.newInstance("", "");
                        fragment.setArguments(args);

                        FragmentTransaction fragmentTransaction2 = fragmentManager.beginTransaction();

                        // Replace whatever is in the fragment_container view with this fragment,
                        // and add the transaction to the back stack so the user can navigate back
                        fragmentTransaction2.replace(R.id.contenedor_principal, fragment, "FragmentoInicio");
                        fragmentTransaction2.addToBackStack(null);
                        fragmentTransaction2.commit();
                        setTitle("Inicio"); // Setear título actual
//                Toast.makeText(getApplicationContext(), "Carrrioto", Toast.LENGTH_SHORT).show();
                    }

                    return true;
                case  R.id.navigationMenu:
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.openDrawer(GravityCompat.START);
                    return true;
            }
            return false;
        }
    };


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);

        // Redirección al Login
        if (!SessionPrefs.get(this).isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        Menu m = navigationView.getMenu();
        for (int i=0;i<m.size();i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu!=null && subMenu.size() >0 ) {
                for (int j=0; j <subMenu.size();j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }

        navigationView.getMenu().getItem(0).setChecked(true);

        View headerView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView = findViewById(R.id.navigation);
        for (int i = 0; i <bottomNavigationView.getMenu().size(); i++) {
            MenuItem menuItem = bottomNavigationView.getMenu().getItem(i);
            applyFontToMenuItem(menuItem);
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//
//        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
//        layoutParams.setBehavior(new BottomNavigationBehavior());

        bottomNavigationView.setSelectedItemId(R.id.navigationHome);

        txtUserName = headerView.findViewById(R.id.txtUserName);
        txtDocument = headerView.findViewById(R.id.txtDocument);

        String doc_user = SessionPrefs.get(this).getPrefCustomerNumeroDoc().substring(5);
        txtUserName.setText(""+SessionPrefs.get(getApplicationContext()).getPrefCustomerName());
        txtDocument.setText("*****"+doc_user);

        // Salir de la aplicacion toobar

        final AlertDialog.Builder builder = new AlertDialog.Builder(ActividadPrincipal.this);
        builder
                .setIcon(R.drawable.exit)
                .setTitle("¿Seguro de salir?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        System.exit(0);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);


        ImageView imagenSalir = findViewById(R.id.imgExit);
        imagenSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.show();
                Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                nbutton.setTextColor(Color.BLACK);
                Button pbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                pbutton.setTextColor(Color.BLACK);
            }
        });

    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Regular.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                SessionPrefs.get(this).logOut();
                finish();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Haga clic ATRÁS nuevamente para salir", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);

        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SessionPrefs.get(this).logOut();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_dark_mode) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
