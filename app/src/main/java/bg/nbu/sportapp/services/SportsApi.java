package bg.nbu.sportapp.services;

import java.util.List;

import bg.nbu.sportapp.models.Sport;
import retrofit2.Call;
import retrofit2.http.GET;

public interface SportsApi {

    @GET("/api/the-sport-db/sports")
    Call<SportsResponse> getSports();

    //   @GET("/api/the-sport-db/leagues")
    // Call<String> getLeagues();

    class SportsResponse {
        public List<Sport> sports;
    }

}
