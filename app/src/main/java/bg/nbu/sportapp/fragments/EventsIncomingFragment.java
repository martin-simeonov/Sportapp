package bg.nbu.sportapp.fragments;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import bg.nbu.sportapp.MainActivity;
import bg.nbu.sportapp.R;
import bg.nbu.sportapp.adapters.EventsAdapter;
import bg.nbu.sportapp.models.Team;
import bg.nbu.sportapp.services.SportsService;

public class EventsIncomingFragment extends Fragment {


    protected ExpandableListView events;
    private TextView emptyMessage;
    protected TextView errorMessage;
    protected ProgressDialog progressDialog;

    public EventsIncomingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_events_list, container, false);

        events = view.findViewById(R.id.events);
        emptyMessage = view.findViewById(R.id.empty_events);
        errorMessage = view.findViewById(R.id.events_error);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading. Please wait...");
        progressDialog.setCancelable(true);

        setEventsList();

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

    private void setEventsList() {
        progressDialog.show();

        List<Integer> teams = getFavoriteTeams();
        if (!teams.isEmpty())
            emptyMessage.setVisibility(View.GONE);

        setAdapter(teams);
    }

    public void setAdapter(List<Integer> teams) {
        EventsAdapter adapter = new EventsAdapter(getActivity());
        events.setAdapter(adapter);
        SportsService.GetTeamWithEvents(teams, new SportsService.TeamsCallback() {
            @Override
            public void onResult(List<Team> teamList) {
                adapter.setData(teamList);
                progressDialog.cancel();
            }

            @Override
            public void onFail() {
                errorMessage.setVisibility(View.VISIBLE);
                progressDialog.cancel();
            }
        });
    }

}
