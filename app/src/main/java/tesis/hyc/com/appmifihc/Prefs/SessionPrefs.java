package tesis.hyc.com.appmifihc.Prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import tesis.hyc.com.appmifihc.Clases.Customer;

/**
 * Manejador de preferencias de la sesión del afiliado
 */
public class SessionPrefs {

    public static final String PREFS_NAME = "MIFI_PREFS";
    public static final String PREF_CUSTOMER_ID = "PREF_USER_ID";
    public static final String PREF_CUSTOMER_NAME = "PREF_CUSTOMER_NAME";
    public static final String PREF_CUSTOMER_NUMERO_DOC = "PREF_CUSTOMER_NUMERO_DOC";
    public static final String PREF_CUSTOMER_DIRECCION = "PREF_CUSTOMER_DIRECCION";
    public static final String PREF_CUSTOMER_EMAIL = "PREF_CUSTOMER_EMAIL";
    public static final String PREF_CUSTOMER_CELULAR = "PREF_CUSTOMER_CELULAR";

    private final SharedPreferences mPrefs;

    private boolean mIsLoggedIn = false;

    private static SessionPrefs INSTANCE;

    public static SessionPrefs get(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SessionPrefs(context);
        }
        return INSTANCE;
    }

    private SessionPrefs(Context context) {
        mPrefs = context.getApplicationContext()
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        mIsLoggedIn = !TextUtils.isEmpty(mPrefs.getString(PREF_CUSTOMER_NUMERO_DOC, null));
    }

    public boolean isLoggedIn() {
        return mIsLoggedIn;
    }

    public void saveCustomer(Customer customer) {
        if (customer != null) {
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putString(PREF_CUSTOMER_ID, String.valueOf(customer.getIdcustomer()));
            editor.putString(PREF_CUSTOMER_NAME, customer.getNombre_completo());
            editor.putString(PREF_CUSTOMER_NUMERO_DOC, customer.getNum_document());
            editor.putString(PREF_CUSTOMER_DIRECCION, customer.getDireccion());
            editor.putString(PREF_CUSTOMER_EMAIL, customer.getEmail());
            editor.putString(PREF_CUSTOMER_CELULAR, customer.getCelular());
            editor.apply();

            mIsLoggedIn = true;
        }
    }

    public void logOut(){
        mIsLoggedIn = false;
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(PREF_CUSTOMER_ID, null);
        editor.putString(PREF_CUSTOMER_NAME, null);
        editor.putString(PREF_CUSTOMER_NUMERO_DOC, null);
        editor.putString(PREF_CUSTOMER_DIRECCION, null);
        editor.putString(PREF_CUSTOMER_EMAIL, null);
        editor.putString(PREF_CUSTOMER_CELULAR, null);
        editor.apply();
    }
}
