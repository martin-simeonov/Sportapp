package bg.nbu.sportapp.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import bg.nbu.sportapp.R;
import bg.nbu.sportapp.fragments.TeamFragment;
import bg.nbu.sportapp.models.Team;

public class TeamsAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Team> teamList;
    private FragmentActivity context;

    public TeamsAdapter(FragmentActivity context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public TeamsAdapter(FragmentActivity context, List<Team> teamList) {
        this(context);
        setData(teamList);
    }

    public void setData(List<Team> teamList) {
        this.teamList = teamList;
        notifyDataSetChanged();
    }

    private class Holder {
        TextView name;
        ImageView badge;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Holder holder;

        if (view == null) {
            holder = new Holder();
            view = inflater.inflate(R.layout.teams_list_item, null);

            holder.name = view.findViewById(R.id.team_name);
            holder.badge = view.findViewById(R.id.team_badge);

            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        Team team = teamList.get(position);

        holder.name.setText(team.getName());

        Picasso.get().load(team.getBadgeUrl()).into(holder.badge);

        view.setTag(R.string.team, team);

        return view;
    }

    public AdapterView.OnItemClickListener myClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            Team team = (Team) v.getTag(R.string.team);

            FragmentTransaction ft = context.getSupportFragmentManager().beginTransaction();
            Fragment prev = context.getSupportFragmentManager().findFragmentByTag("TeamView");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);

            Bundle bundle = new Bundle();
            bundle.putSerializable("team", team);

            // Create and show the dialog.
            TeamFragment newFragment = new TeamFragment();
            newFragment.setArguments(bundle);
            newFragment.show(ft, "TeamView");
        }
    };

    @Override
    public int getCount() {
        return teamList.size();
    }

    @Override
    public Object getItem(int i) {
        return teamList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}
