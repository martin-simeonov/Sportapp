package bg.nbu.sportapp.fragments;


import android.view.View;

import java.util.List;

import bg.nbu.sportapp.adapters.EventsPreviousAdapter;
import bg.nbu.sportapp.models.Team;
import bg.nbu.sportapp.services.SportsService;

public class EventsPreviousFragment extends EventsIncomingFragment {


    public EventsPreviousFragment() {
        // Required empty public constructor
    }

    public void setAdapter(List<Integer> teams) {
        EventsPreviousAdapter adapter = new EventsPreviousAdapter(getActivity());
        events.setAdapter(adapter);
        SportsService.GetTeamWithEventsPrevious(teams, new SportsService.TeamWithEventsCallback() {
            @Override
            public void onResult(List<Team> teamList) {
                adapter.setData(teamList);
                events.setOnChildClickListener(adapter.myClickListener);

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
