package projek.basiru.auth;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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

    @Multipart
    @POST("Api/uploadMultipart")
    Call<ResponsesAuth> uploadMultipart(@Part("action") RequestBody action,
                                        @Part MultipartBody.Part photo);

//        @FormUrlEncoded
//    @POST(Api/uploadMultipart)
//    Call<BaseResponse> uploadPhotoBase64(
//            @Field("action") String action,
//            @Field("photo") String photo);

//    @FormUrlEncoded
//    @POST("Api/listprogram")
//    Call<ResponsesAuth> listprogram(@Field("judul") String judul,
//                                    @Field("mulai") String mulai,
//                                    @Field("deadline") String deadline,
//                                    @Field("status") String status);

}
