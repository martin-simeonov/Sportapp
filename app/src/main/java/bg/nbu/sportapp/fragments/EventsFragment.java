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

public class EventsFragment extends Fragment {

    private ViewPager mPager;
    private ScreenSlidePagerAdapter pagerAdapter;

    public EventsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = view.findViewById(R.id.events_pager);
        mPager.setOffscreenPageLimit(1);
        pagerAdapter = new ScreenSlidePagerAdapter(getActivity().getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);

        return view;
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new EventsIncomingFragment();
                case 1:
            }
            return null;
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Incoming";
                case 1:
                    return "Previous";
            }
            return "";
        }
    }

}
