package bg.nbu.sportapp.services;

import java.util.List;

import bg.nbu.sportapp.models.League;
import bg.nbu.sportapp.models.Player;
import bg.nbu.sportapp.models.Sport;
import bg.nbu.sportapp.models.Team;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SportsApi {

    @GET("/api/the-sport-db/sports")
    Call<List<Sport>> getSports();

    @GET("/api/the-sport-db/leagues")
    Call<List<League>> getLeagues(@Query("sportName") String sportName);

    @GET("/api/the-sport-db/teams")
    Call<List<Team>> getTeams(@Query("leagueId") int leagueId);

    @GET("/api/the-sport-db/teams/{id}")
    Call<TeamResponse> getTeam(@Path("id") int id);

    class TeamResponse {
        private Team team;
        private List<Player> players;

        public Team getTeam() {
            return team;
        }

        public void setTeam(Team team) {
            this.team = team;
        }

        public List<Player> getPlayers() {
            return players;
        }

        public void setPlayers(List<Player> players) {
            this.players = players;
        }
    }

}
