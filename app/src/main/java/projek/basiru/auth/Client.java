package projek.basiru.auth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
//import projek.basiru.network.UploadInterface;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client
{
    public static final String BASE_URL = "https://basirusamalewa.com/";
    public static Retrofit retrofit = null;
    private static final Object LOCK = new Object();


    public static void clear()
    {
        synchronized (LOCK) { retrofit = null; }
    }
    public Retrofit getClient()
    {
        synchronized (LOCK)
        {
            if (retrofit == null)
            {
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .build();

                retrofit = new Retrofit.Builder()
                        .client(okHttpClient)
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
            }
            return retrofit;

        }
    }
}
