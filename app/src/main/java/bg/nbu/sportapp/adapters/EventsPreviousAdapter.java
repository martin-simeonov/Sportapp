package bg.nbu.sportapp.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import bg.nbu.sportapp.R;
import bg.nbu.sportapp.models.Event;
import bg.nbu.sportapp.models.Team;

public class EventsPreviousAdapter extends BaseExpandableListAdapter {

    private LayoutInflater inflater;
    private List<Team> teamList;
    private Context context;

    public EventsPreviousAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public EventsPreviousAdapter(Context context, List<Team> teamList) {
        this(context);
        setData(teamList);
    }

    public void setData(List<Team> teamList) {
        this.teamList = teamList;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        if (teamList != null)
            return teamList.size();
        return 0;
    }

    @Override
    public int getChildrenCount(int i) {
        int count = 0;
        if (teamList != null && teamList.get(i).getEvents() != null)
            count = teamList.get(i).getEvents().size();
        if (count == 0)
            return 1;
        return count;
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    class HolderGroup {
        TextView name;
        ImageView badge;
    }

    @Override
    public View getGroupView(int position, boolean b, View view, ViewGroup viewGroup) {
        HolderGroup holder;

        if (view == null) {
            holder = new HolderGroup();
            view = inflater.inflate(R.layout.events_group_list_item, null);

            holder.name = view.findViewById(R.id.team_name);
            holder.badge = view.findViewById(R.id.team_badge);

            view.setTag(holder);
        } else {
            holder = (HolderGroup) view.getTag();
        }
        Team team = teamList.get(position);
        holder.name.setText(team.getName());
        Picasso.get().load(team.getBadgeUrl()).placeholder(R.drawable.progress_image).into(holder.badge);

        return view;
    }


    class HolderChild {
        TextView homeTeam, awayTeam, score, dateTime, name, emptyMessage;
        ImageView video;
        LinearLayout main;
    }

    @Override
    public View getChildView(int position, int childPosition, boolean b, View view, ViewGroup viewGroup) {
        HolderChild holder;

        if (view == null) {
            holder = new HolderChild();

            view = inflater.inflate(R.layout.previous_events_list_item, null);
            holder.name = view.findViewById(R.id.event_name);
            holder.homeTeam = view.findViewById(R.id.event_home_team);
            holder.awayTeam = view.findViewById(R.id.event_away_team);
            holder.score = view.findViewById(R.id.event_score);
            holder.dateTime = view.findViewById(R.id.event_date_time);
            holder.main = view.findViewById(R.id.event_list_item);
            holder.emptyMessage = view.findViewById(R.id.event_list_item_message);
            holder.video = view.findViewById(R.id.event_video);

            view.setTag(holder);
        } else {
            holder = (HolderChild) view.getTag();
        }

        // Child is empty
        if (teamList.get(position).getEvents() == null || teamList.get(position).getEvents().isEmpty()) {
            holder.emptyMessage.setVisibility(View.VISIBLE);
            holder.main.setVisibility(View.GONE);
        } else {
            holder.main.setVisibility(View.VISIBLE);
            holder.emptyMessage.setVisibility(View.GONE);
            Event event = teamList.get(position).getEvents().get(childPosition);

            holder.name.setText(event.getEvent());
            if (event.getAwayTeam().isEmpty()) {
                holder.score.setVisibility(View.INVISIBLE);
            } else {
                holder.score.setText(context.getString(R.string.score, event.getHomeTeamScore(), event.getAwayTeamScore()));
            }
            holder.homeTeam.setText(event.getHomeTeam());
            holder.awayTeam.setText(event.getAwayTeam());
            holder.dateTime.setText(context.getString(R.string.date_time_placeholder, event.getEventDate(),
                    event.getEventStartTime() == null ? "" : event.getEventStartTime()));

            if (event.getVideoUrl() != null && !event.getVideoUrl().isEmpty()) {
                holder.video.setVisibility(View.VISIBLE);
                String videoId = event.getVideoUrl().split("=")[1];
                Picasso.get().load(String.format("https://img.youtube.com/vi/%s/0.jpg", videoId)).placeholder(R.drawable.progress_image).into(holder.video);

                holder.video.setOnClickListener(v -> {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(event.getVideoUrl()));
                    context.startActivity(intent);
                });
            } else {
                holder.video.setVisibility(View.GONE);
            }

            view.setTag(R.string.event, event);
        }

        return view;
    }

    public ExpandableListView.OnChildClickListener myClickListener = (expandableListView, view, i, i1, l) -> {
        Event event = (Event) view.getTag(R.string.event);
        if (event == null || event.getResultDescription() == null || event.getResultDescription().isEmpty())
            return false;

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setMessage(event.getDescription())
                .setPositiveButton("Ok", (dialog1, which) -> {
                    dialog1.dismiss();
                })
                .create();
        dialog.show();
        return true;
    };

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

}
