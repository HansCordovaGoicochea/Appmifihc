package tesis.hyc.com.appmifihc.Fragmentos;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import tesis.hyc.com.appmifihc.Clases.PromocionesBanner;
import tesis.hyc.com.appmifihc.Clases.Servicios;
import tesis.hyc.com.appmifihc.Prefs.SessionPrefs;
import tesis.hyc.com.appmifihc.R;
import tesis.hyc.com.appmifihc.SingletonVolley.MySingleton;
import tesis.hyc.com.appmifihc.SingletonVolley.VolleyPeticiones;
import tesis.hyc.com.appmifihc.Utils.Funciones;

import static tesis.hyc.com.appmifihc.Utils.Constantes.API_KEY;
import static tesis.hyc.com.appmifihc.Utils.Constantes.BASE_URL;

public class FragmentoInicio extends Fragment {
    public static final String ARG_SECTION_TITLE = "";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ArrayList<String> promociones = new ArrayList<>();

    private List<Servicios> ServiciosList = new ArrayList<>();


    CarouselView carouselView;
    ProgressBar progressBar;

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

        Funciones.setProgressShow();
        peticionServicioOfertas();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Locale locale = new Locale("es", "ES");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getContext().getApplicationContext().getResources().updateConfiguration(config, null);

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_inicio, container, false);
        carouselView = view.findViewById(R.id.carouselView);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        TextView dateTxt = view.findViewById(R.id.dateTextView);
        dateTxt.setText(currentDate);


        return view;
    }

    ImageListener imageListener = new ImageListener() {
        public void setImageForPosition(int position, ImageView imageView) {
//            imageView.setImageResource(promociones.get(position));
//            Log.e("asdsadasd", promociones.get(position));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            RequestOptions requestOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE) // because file name is always same
//                    .placeholder(R.drawable.empty_banner)
                    ;

            Glide.with(FragmentoInicio.this)
                    .load(promociones.get(position))
                    .apply(requestOptions)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })

                    .into(imageView);
        }
    };

    public void peticionServicioOfertas()
    {

        final String uri = VolleyPeticiones.getPromocionesSlider();
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
                                carouselView.setImageListener(imageListener);
                                carouselView.setPageCount(promociones.size());

//                                mContents = new ViewPagerModel();
//                                mContents.setImages(id_oferta);
//                                mContents.setNames(nombre);
//                                mContents.setDescripcion(descripcion);
//                                //Se añade el objeto creado a la colección de tipo List<Tienda>.
//                                mContents.save(); //guardar en la DB

                            }

                            Funciones.setProgressHide();

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

    public void peticionServicios()
    {

        final String uri = VolleyPeticiones.getServicios(SessionPrefs.get(getContext()).getPrefCustomerId());
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

                            JSONArray jsonMainNode = response.getJSONArray("mifi_servicios");

                            /*Se construye un bucle for() para recorrer la respuesta parseada y
                            construir un nuevo objeto Tienda por cada registro de la base de datos MySQL.*/
                            for (int i = 0; i < jsonMainNode.length(); i++)
                            {

                                JSONObject jsObjectTiendas = (JSONObject) jsonMainNode.get(i);
                                int id_mifi_servicios = jsObjectTiendas.getInt("id");
                                int id_customer = jsObjectTiendas.getInt("id_customer");
                                String nombre_servicio = jsObjectTiendas.getString("nombre_servicio");
                                Double monto_servicio = jsObjectTiendas.getDouble("monto_servicio");
                                int plazo_servicio = jsObjectTiendas.getInt("plazo_servicio");
                                String fecha_servicio = jsObjectTiendas.getString("fecha_servicio");
                                int num_certificado_servicio = jsObjectTiendas.getInt("num_certificado_servicio");
                                Double monto_restante_servicio = jsObjectTiendas.getDouble("monto_restante_servicio");
                                Double monto_mensual_prest = jsObjectTiendas.getDouble("monto_mensual_prest");
                                Double tasa_interes_servicio = jsObjectTiendas.getDouble("tasa_interes_servicio");
                                int id_currecy = jsObjectTiendas.getInt("id_currecy");
                                int estado_servicio = jsObjectTiendas.getInt("estado_servicio");
                                int codigo_servicio = jsObjectTiendas.getInt("codigo_servicio");

                            }

                            Funciones.setProgressHide();

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
