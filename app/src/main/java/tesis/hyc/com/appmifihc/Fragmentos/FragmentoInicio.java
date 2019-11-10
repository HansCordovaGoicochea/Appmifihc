package tesis.hyc.com.appmifihc.Fragmentos;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import tesis.hyc.com.appmifihc.R;
import tesis.hyc.com.appmifihc.SingletonVolley.MySingleton;
import tesis.hyc.com.appmifihc.SingletonVolley.VolleyPeticiones;

public class FragmentoInicio extends Fragment {
    public static final String ARG_SECTION_TITLE = "";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ArrayList<String> promociones = new ArrayList<String>();


    CarouselView carouselView;
    int[] sampleImages = {R.drawable.image_1, R.drawable.image_2, R.drawable.image_3, R.drawable.image_4, R.drawable.image_5};

    public FragmentoInicio() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentoInicio newInstance(String param1, String param2) {
        FragmentoInicio fragment = new FragmentoInicio();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        peticionServicioOfertas();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_inicio, container, false);
        carouselView = (CarouselView) view.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);

        return view;
    }

    ImageListener imageListener = new ImageListener() {
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
//            Glide.with(FragmentoInicio.this)
//                    .load(promociones.get(position))
//                    .into(imageView)
//            ;
        }
    };

    public void peticionServicioOfertas()
    {

        String uri = VolleyPeticiones.getPromocionesSlider();
        /*Se declara e inicializa un objeto de tipo JsonObjectRequest, que permite
        recuperar un JSONObject a partir de la URL que recibe. El constructor de la clase JsonObjectRequest
        recibe como argumentos de entrada el método para que el cliente realice operaciones sobre el servidor web, la uri
        para el acceso al recurso, y la interfaz Response.Listener, encargada de devolver la respuesta parseada a la petición
        del cliente, y la interfaz Response.ErrorListener encargada de entregar una respuesta errónea desde el servicio web.*/
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, uri, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonMainNode = response.getJSONArray("ofertas");

                            /*Se construye un bucle for() para recorrer la respuesta parseada y
                            construir un nuevo objeto Tienda por cada registro de la base de datos MySQL.*/
                            for (int i = 0; i < jsonMainNode.length(); i++)
                            {

                                JSONObject jsObjectTiendas = (JSONObject) jsonMainNode.get(i);
                                int id_oferta = jsObjectTiendas.getInt("id");
                                String nombre = jsObjectTiendas.getString("nombre");
                                String descripcion = jsObjectTiendas.getString("descripcion");

                                String url_imagen = VolleyPeticiones.getUrlImagePromocion(id_oferta);
                                promociones.add(url_imagen);

//                                mContents = new ViewPagerModel();
//                                mContents.setImages(id_oferta);
//                                mContents.setNames(nombre);
//                                mContents.setDescripcion(descripcion);
//                                //Se añade el objeto creado a la colección de tipo List<Tienda>.
//                                mContents.save(); //guardar en la DB

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try
                {
                    Log.e("ERROR DE VOLLEY TIENDAS", "Error de petición de servicio: " + error.toString());
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
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
        MySingleton.getInstance(getContext()).addToRequestQueue(request);

    }


}
