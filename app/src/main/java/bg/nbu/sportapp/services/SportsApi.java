package bg.nbu.sportapp.services;

import java.util.List;

import bg.nbu.sportapp.models.League;
import bg.nbu.sportapp.models.Sport;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SportsApi {

    @GET("/api/the-sport-db/sports")
    Call<SportsResponse> getSports();

    @GET("/api/the-sport-db/leagues")
    Call<LeaguesResponse> getLeagues(@Query("sportName") String sportName);

    class SportsResponse {
        public List<Sport> sports;
    }

    class LeaguesResponse {
        public  List<League> leagues;
    }

}
