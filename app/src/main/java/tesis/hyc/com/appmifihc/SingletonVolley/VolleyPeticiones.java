package tesis.hyc.com.appmifihc.SingletonVolley;

import java.util.ArrayList;

import tesis.hyc.com.appmifihc.Utils.Constantes;

public class VolleyPeticiones {


    public static String getCustomerApi(String num_document){
        return Constantes.BASE_URL+"customers/?ws_key=" + Constantes.API_KEY + "&output_format=JSON&filter[num_document]="+num_document+"&display=full&limit=1";
    }

    public static String getPromocionesSlider(){
        return Constantes.BASE_URL+"ofertas/?ws_key=" + Constantes.API_KEY + "&output_format=JSON&display=full";
    }


    public static String getServicios(Integer id_customer){
        return Constantes.BASE_URL+"mifi_servicios/?ws_key=" + Constantes.API_KEY + "&output_format=JSON&display=full&filter[id_customer]="+id_customer;
    }


    public static String getUrlImagePromocion(Integer id_oferta){
        return Constantes.BASE_URL+"images/ofertas/"+id_oferta+"?ws_key=" + Constantes.API_KEY;
    }


    public static String getUrlAgencias(){
        return Constantes.BASE_URL+"stores/?ws_key=" + Constantes.API_KEY + "&output_format=JSON&display=full";
    }

}
