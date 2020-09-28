package org.artilapx.bytepsec.source;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.artilapx.bytepsec.models.ScheduleResponse;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebHandler {

    private final static String TAG = WebHandler.class.getSimpleName();

    private final static String PSEC_URL = "https://pgaek.by/";

    private final static WebHandler ourInstance = new WebHandler();

    private final static int TIMEOUT_SECS = 30;

    private WebHandler() {
        final Gson gson = new GsonBuilder()
                .create();

        final OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT_SECS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_SECS, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_SECS, TimeUnit.SECONDS)
                .build();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PSEC_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
    }

    public static WebHandler getInstance() {
        return ourInstance;
    }

    public Call<ScheduleResponse> getSchedule() {
        return null;
    }

}
