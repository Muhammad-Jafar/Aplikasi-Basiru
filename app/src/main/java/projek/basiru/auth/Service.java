package projek.basiru.auth;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Service
{

    @FormUrlEncoded
    @POST("Api/login")
    Call<ResponsesAuth> login (@Field("email") String email,
                               @Field("password") String password);

    @FormUrlEncoded
    @POST("Api/registrasi")
    Call<ResponsesAuth> registrasi( @Field("name") String nama,
                                    @Field("email") String email,
                                    @Field("password") String password );

    @FormUrlEncoded
    @POST("Api/donasi")
    Call<ResponsesAuth> donasi(@Field("nama") String nama,
                               @Field("program") String program,
                               @Field("no_telp") String no_telp,
                               @Field("nominal") String nominal,
                               @Field("bank") String bank);

}
