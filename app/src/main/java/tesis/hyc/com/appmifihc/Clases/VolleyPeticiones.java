package tesis.hyc.com.appmifihc.Clases;

import tesis.hyc.com.appmifihc.Utils.Constantes;

public class VolleyPeticiones {


    public static String getCustomerApi(String num_document){
        return Constantes.BASE_URL+"customers/?ws_key=" + Constantes.API_KEY + "&output_format=JSON&filter[num_document]="+num_document+"&display=full&limit=1";
    }

}
