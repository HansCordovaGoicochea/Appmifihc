package tesis.hyc.com.appmifihc.io;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import tesis.hyc.com.appmifihc.io.response.CustomerResponse;

public interface MiApiService {


    @GET("customers")
    Call<CustomerResponse> getCustomerDetails(
            @Query(value = "display", encoded = true) String display,
            @Query(value = "filter[num_document]", encoded = true) String numero_doc,
            @Query("output_format") String json,
            @Query("ws_key") String key,
            @Query("limit") Integer limit
    );

}
