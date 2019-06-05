package bg.nbu.sportapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import bg.nbu.sportapp.R;
import bg.nbu.sportapp.models.News;
import bg.nbu.sportapp.models.Sport;

public class SportsAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Sport> sportsList;

    public SportsAdapter(Context context) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public SportsAdapter(Context context, List<Sport> sportsList) {
        this(context);
        setData(sportsList);
    }

    public void setData(List<Sport> sportsList) {
        this.sportsList = sportsList;
        notifyDataSetChanged();
    }

    private class Holder {
        TextView name, description;
        ImageView image;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Holder holder;

        if (view == null) {
            holder = new Holder();
            view = inflater.inflate(R.layout.sports_list_item, null);

            holder.name = view.findViewById(R.id.name);
            holder.description = view.findViewById(R.id.description);
            holder.image = view.findViewById((R.id.image));

            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        Sport sport = sportsList.get(position);

        holder.name.setText(sport.getName());
        holder.description.setText(sport.getDescription());

        Picasso.get().load(sport.getImageLink()).into(holder.image);

        return view;
    }

    @Override
    public int getCount() {
        if (sportsList != null)
            return sportsList.size();
        return 0;
    }

    @Override
    public Object getItem(int i) {
        if (sportsList != null)
            return sportsList.get(i);
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}
