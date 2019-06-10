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
import bg.nbu.sportapp.models.League;
import bg.nbu.sportapp.services.SportsService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeagueListPageFragment extends Fragment {

    private ListView leagueList;
    private TextView emptyMessage;

    public LeagueListPageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_league_list_page, container, false);

        leagueList = view.findViewById(R.id.league_list);
        emptyMessage = view.findViewById(R.id.empty_league_list);

        emptyMessage.setVisibility(View.VISIBLE);
        return view;
    }

    public void setLeaguesList(String sport) {
        SportsService.GetSportsService().getLeagues(sport).enqueue(new Callback<List<League>>() {
            @Override
            public void onResponse(Call<List<League>> call, Response<List<League>> response) {
                if (response.body() != null) {
                    List<League> leaguesList = response.body();
                    leagueList.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, leaguesList));

                    emptyMessage.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<League>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
