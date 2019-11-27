package tesis.hyc.com.appmifihc.Fragmentos;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.SupportMapFragment;


/**
 * Muestra el mapa
 */
public class AgenciasMapFragment extends SupportMapFragment {

    public AgenciasMapFragment() {
    }

    public static AgenciasMapFragment newInstance() {
        return new AgenciasMapFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);

        return root;
    }

}
