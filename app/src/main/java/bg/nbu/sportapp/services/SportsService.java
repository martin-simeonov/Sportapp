package bg.nbu.sportapp.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SportsService {

    //private static final String URL = "http://localhost:9000";
    private static final String URL = "http://10.0.2.2:9000";

    private static SportsApi sportsApi;

    public static SportsApi GetSportsService() {
        if (sportsApi == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder().baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            sportsApi = retrofit.create(SportsApi.class);
        }
        return sportsApi;
    }

}
