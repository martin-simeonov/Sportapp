package bg.nbu.sportapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import bg.nbu.sportapp.R;
import bg.nbu.sportapp.adapters.TeamsAdapter;
import bg.nbu.sportapp.models.League;
import bg.nbu.sportapp.models.Team;
import bg.nbu.sportapp.services.SportsService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamListPageFragment extends Fragment {

    private ListView teamList;
    private TextView emptyMessage;

    public TeamListPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_team_list_page, container, false);

        teamList = view.findViewById(R.id.team_list);
        emptyMessage = view.findViewById(R.id.empty_team_list);

        emptyMessage.setVisibility(View.VISIBLE);
        return view;
    }

    public void setTeamList(int leagueId) {
        SportsService.GetSportsService().getTeams(leagueId).enqueue(new Callback<List<Team>>() {
            @Override
            public void onResponse(Call<List<Team>> call, Response<List<Team>> response) {
                if (response.body() != null) {
                    List<Team> teamsList = response.body();
                    teamList.setAdapter(new TeamsAdapter(getActivity(), teamsList));

                    emptyMessage.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Team>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
