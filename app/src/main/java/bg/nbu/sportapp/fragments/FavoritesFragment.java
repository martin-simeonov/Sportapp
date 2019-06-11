package bg.nbu.sportapp.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import bg.nbu.sportapp.MainActivity;
import bg.nbu.sportapp.R;
import bg.nbu.sportapp.adapters.TeamsAdapter;
import bg.nbu.sportapp.models.Team;
import bg.nbu.sportapp.services.SportsService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritesFragment extends TeamListPageFragment {

    private ListView teamList;
    private TextView emptyMessage;

    public FavoritesFragment() {
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

        setTeamList();
        return view;
    }

    private List<Integer> getFavoriteTeams() {
        SharedPreferences favoriteTeamsStore = getActivity().getSharedPreferences(MainActivity.FAVORITE_TEAMS_STORE, 0);
        Set<String> favoriteTeams = favoriteTeamsStore.getStringSet(MainActivity.FAVORITE_TEAMS, null);

        if (favoriteTeams == null)
            return new ArrayList<>();

        final List<Integer> teamIds = new ArrayList<>();
        favoriteTeams.forEach(team -> teamIds.add(Integer.valueOf(team)));

        return teamIds;
    }

    public void setTeamList() {
        SportsService.GetTeams(getFavoriteTeams(), new SportsService.TeamsCallback() {
            @Override
            public void onResult(List<Team> list) {
                TeamsAdapter adapter = new TeamsAdapter(getActivity(), list);
                teamList.setAdapter(adapter);
                teamList.setOnItemClickListener(adapter.myClickListener);

                emptyMessage.setVisibility(View.GONE);
            }

            @Override
            public void onFail() {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
