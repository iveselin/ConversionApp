package hr.ferit.iveselin.conversionapp.data.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {

    public static final String BASE_URL = "http://hnbex.eu/api/v1/rates/";

    private static Retrofit retrofit;
    private static ApiEndpoint apiEndpoint;

    private static Retrofit getRetrofitInstance() {
        if (retrofit == null) {

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.serializeNulls();
            Gson gson = gsonBuilder.create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    public static ApiEndpoint getApiEndpoint() {
        if (apiEndpoint == null) {
            apiEndpoint = getRetrofitInstance().create(ApiEndpoint.class);
        }
        return apiEndpoint;
    }

}
