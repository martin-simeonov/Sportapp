package bg.nbu.sportapp.fragments;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import bg.nbu.sportapp.R;
import bg.nbu.sportapp.adapters.PlayersAdapter;
import bg.nbu.sportapp.models.Team;
import bg.nbu.sportapp.services.SportsApi;
import bg.nbu.sportapp.services.SportsService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamFragment extends DialogFragment {

    private Team team;
    private ListView playersList;

    public TeamFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_team, container, false);

        team = (Team) getArguments().getSerializable("team");

        TextView name = view.findViewById(R.id.team_name);
        name.setText(team.getName());

        TextView league = view.findViewById(R.id.team_league);
        league.setText(team.getLeagueName());

        TextView country = view.findViewById(R.id.team_country);
        country.setText(team.getCountry());

        TextView year = view.findViewById(R.id.team_year);
        year.setText(getString(R.string.formed_in, team.getFormedYear()));

        TextView website = view.findViewById(R.id.team_website);
        website.setText(team.getWebsiteUrl());

        ImageView badge = view.findViewById(R.id.badge);
        Picasso.get().load(team.getBadgeUrl()).placeholder(R.drawable.progress_image).into(badge);

        playersList = view.findViewById(R.id.players_list);

        setPlayersList();
        return view;
    }

    private void setPlayersList() {
        SportsService.GetSportsService().getTeam(team.getId()).enqueue(new Callback<SportsApi.TeamResponse>() {
            @Override
            public void onResponse(Call<SportsApi.TeamResponse> call, Response<SportsApi.TeamResponse> response) {
                if (response.body() != null) {
                    playersList.setAdapter(new PlayersAdapter(getActivity(), response.body().getPlayers()));
                }
            }

            @Override
            public void onFailure(Call<SportsApi.TeamResponse> call, Throwable t) {

            }
        });
    }

}
