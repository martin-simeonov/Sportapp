package bg.nbu.sportapp.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import bg.nbu.sportapp.R;
import bg.nbu.sportapp.models.Player;
import bg.nbu.sportapp.models.Team;

public class PlayersAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Player> players;
    private FragmentActivity context;

    public PlayersAdapter(FragmentActivity context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public PlayersAdapter(FragmentActivity context, List<Player> players) {
        this(context);
        setData(players);
    }

    class Holder {
        TextView name, position, nationality, birthDate;
        ImageView photo;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Holder holder;

        if (view == null) {
            holder = new Holder();
            view = inflater.inflate(R.layout.player_list_item, null);

            holder.name = view.findViewById(R.id.player_name);
            holder.position = view.findViewById(R.id.player_position);
            holder.nationality = view.findViewById(R.id.player_nationality);
            holder.birthDate = view.findViewById(R.id.player_birth_date);
            holder.photo = view.findViewById(R.id.player_image);

            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        Player player = players.get(position);

        holder.name.setText(player.getName());
        holder.position.setText(context.getString(R.string.player_position, player.getPlayingPosition()));
        holder.nationality.setText(player.getNationality());
        holder.birthDate.setText(context.getString(R.string.birth_date, player.getDateOfBirth()));

        if (player.getImageUrl() != null && !player.getImageUrl().isEmpty())
            Picasso.get().load(player.getImageUrl()).placeholder(R.drawable.progress_image).into(holder.photo);
        else
            Picasso.get().load(player.getProfileImageUrl()).placeholder(R.drawable.progress_image).into(holder.photo);

        return view;
    }

    public void setData(List<Player> players) {
        this.players = players;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return players.size();
    }

    @Override
    public Object getItem(int i) {
        return players.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}
