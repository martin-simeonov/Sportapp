package bg.nbu.sportapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import bg.nbu.sportapp.R;
import bg.nbu.sportapp.fragments.SportListPageFragment;
import bg.nbu.sportapp.models.Sport;

public class SportsAdapter extends BaseExpandableListAdapter {

    private LayoutInflater inflater;
    private List<Sport> sportsList;
    private Context context;
    private SportListPageFragment fragment;

    public SportsAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public SportsAdapter(Context context, List<Sport> sportsList, SportListPageFragment fragment) {
        this(context);
        this.fragment = fragment;
        setData(sportsList);
    }

    public void setData(List<Sport> sportsList) {
        this.sportsList = sportsList;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        if (sportsList != null)
            return sportsList.size();
        return 0;
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Sport getGroup(int i) {
        if (sportsList != null)
            return sportsList.get(i);
        return null;
    }

    @Override
    public String getChild(int i, int i1) {
        if (sportsList != null)
            return sportsList.get(i).getDescription();
        return "";
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int position, boolean b, View view, ViewGroup viewGroup) {
        final Holder holder;

        if (view == null) {
            holder = new Holder();
            view = inflater.inflate(R.layout.sports_list_item, null);

            holder.name = view.findViewById(R.id.name);
            holder.image = view.findViewById(R.id.image);
            holder.leagueSearchButton = view.findViewById(R.id.leagueSearchButton);

            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        final Sport sport = sportsList.get(position);

        holder.name.setText(sport.getName());
        Picasso.get().load(sport.getImageLink()).placeholder(R.drawable.progress_image).into(holder.image);

        holder.leagueSearchButton.setFocusable(false);
        holder.leagueSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.setSelectedSport(sport.getName());
            }
        });

        return view;
    }

    @Override
    public View getChildView(int position, int i1, boolean b, View view, ViewGroup viewGroup) {
        Holder holder;

        if (view == null) {
            holder = new Holder();
            view = inflater.inflate(R.layout.sports_list_item_expand, null);

            holder.description = view.findViewById(R.id.description);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        Sport sport = sportsList.get(position);

        holder.description.setText(sport.getDescription());

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    private class Holder {
        TextView name, description;
        ImageView image, leagueSearchButton;
    }

}
