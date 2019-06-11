package bg.nbu.sportapp.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import bg.nbu.sportapp.models.Event;
import bg.nbu.sportapp.models.Team;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS).build();

            Retrofit retrofit = new Retrofit.Builder().baseUrl(URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            sportsApi = retrofit.create(SportsApi.class);
        }
        return sportsApi;
    }

    public static void GetTeamWithEvents(List<Integer> teamIds, TeamWithEventsCallback callback) {
        List<Team> teams = new ArrayList<>();
        for (Integer id : teamIds) {
            GetSportsService().getTeam(id).enqueue(new Callback<SportsApi.TeamResponse>() {
                @Override
                public void onResponse(Call<SportsApi.TeamResponse> call, Response<SportsApi.TeamResponse> response) {
                    if (response.body() != null) {
                        teams.add(response.body().getTeam());

                        if (teams.size() == teamIds.size()) {
                            getEvents(teams, callback);
                        }
                    }
                }

                @Override
                public void onFailure(Call<SportsApi.TeamResponse> call, Throwable t) {
                    callback.onFail();
                }
            });
        }
    }

    private static int countTeams = 0;

    private static void getEvents(List<Team> teams, TeamWithEventsCallback callback) {
        countTeams = 0;
        for (Team team : teams) {
            GetSportsService().getUpcomingEvents(team.getId()).enqueue(new Callback<List<Event>>() {
                @Override
                public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                    if (response.body() != null) {
                        team.setEvents(response.body());
                        countTeams++;
                        if (countTeams == teams.size())
                            callback.onResult(teams);
                    }
                }

                @Override
                public void onFailure(Call<List<Event>> call, Throwable t) {
                    callback.onFail();
                }
            });
        }
    }

    public interface TeamWithEventsCallback {
        void onResult(List<Team> teamList);

        void onFail();
    }

}
