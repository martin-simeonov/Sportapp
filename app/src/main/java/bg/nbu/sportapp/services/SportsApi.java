package bg.nbu.sportapp.services;

import java.util.List;

import bg.nbu.sportapp.models.League;
import bg.nbu.sportapp.models.Sport;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SportsApi {

    @GET("/api/the-sport-db/sports")
    Call<List<Sport>> getSports();

    @GET("/api/the-sport-db/leagues")
    Call<List<League>> getLeagues(@Query("sportName") String sportName);

}
