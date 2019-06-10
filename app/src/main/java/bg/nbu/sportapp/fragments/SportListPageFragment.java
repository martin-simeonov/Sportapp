package bg.nbu.sportapp.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.List;

import bg.nbu.sportapp.R;
import bg.nbu.sportapp.adapters.SportsAdapter;
import bg.nbu.sportapp.models.Sport;
import bg.nbu.sportapp.services.SportsService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SportListPageFragment extends Fragment {

    private ProgressDialog progressDialog;
    private ExpandableListView sportExpandableList;

    private String selectedSport;

    private SportListPageFragment context = this;
    private SportsFragment sportsFragment;

    public SportListPageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sport_list_page, container, false);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading. Please wait...");
        progressDialog.setCancelable(true);

        sportExpandableList = view.findViewById(R.id.sport_expandable_list);

        setSportsList();

        return view;
    }

    private void setSportsList() {
        toggleProgressDialog();

        SportsService.GetSportsService().getSports().enqueue(new Callback<List<Sport>>() {
            @Override
            public void onResponse(Call<List<Sport>> call, Response<List<Sport>> response) {
                if (response.body() != null) {
                    List<Sport> sportList = response.body();
                    sportExpandableList.setAdapter(new SportsAdapter(getActivity(), sportList, context));

                    toggleProgressDialog();
                } else {
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Sport>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();

                toggleProgressDialog();
            }
        });
    }

    public void toggleProgressDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.cancel();
        } else {
            progressDialog.show();
        }
    }

    public void setSelectedSport(String sport) {
        this.selectedSport = sport;
        if (sportsFragment != null)
            sportsFragment.setSelectedSport(sport);
    }

    public String getSelectedSport() {
        return selectedSport;
    }

    public void setSportsFragment(SportsFragment sportsFragment) {
        this.sportsFragment = sportsFragment;
    }
}
