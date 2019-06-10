package bg.nbu.sportapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bg.nbu.sportapp.R;

public class SportsFragment extends Fragment {

    private ViewPager mPager;
    private ScreenSlidePagerAdapter pagerAdapter;
    private SportsFragment context = this;

    public SportsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sports, container, false);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = view.findViewById(R.id.pager);
        mPager.setOffscreenPageLimit(2);
        pagerAdapter = new ScreenSlidePagerAdapter(getActivity().getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);

        return view;
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        LeagueListPageFragment leagueListPageFragment;
        TeamListPageFragment teamListPageFragment;

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    SportListPageFragment fragment = new SportListPageFragment();
                    fragment.setSportsFragment(context);
                    return fragment;
                case 1:
                    leagueListPageFragment = new LeagueListPageFragment();
                    leagueListPageFragment.setSportsFragment(context);
                    return leagueListPageFragment;
                case 2:
                    teamListPageFragment = new TeamListPageFragment();
                    return teamListPageFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Sports";
                case 1:
                    return "Leagues";
                case 2:
                    return "Teams";
            }
            return "";
        }
    }

    public void setSelectedSport(String selectedSport) {
        if (pagerAdapter.leagueListPageFragment != null) {
            pagerAdapter.leagueListPageFragment.setLeaguesList(selectedSport);
            mPager.setCurrentItem(1, true);
        }
    }

    public void setSelectedLeague(int leagueId) {
        if (pagerAdapter.teamListPageFragment != null) {
            pagerAdapter.teamListPageFragment.setTeamList(leagueId);
            mPager.setCurrentItem(2, true);
        }
    }

}
