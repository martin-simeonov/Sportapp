package bg.nbu.sportapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import bg.nbu.sportapp.R;
import bg.nbu.sportapp.models.News;

public class NewsAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<News> newsList;

    public NewsAdapter(Context context) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public NewsAdapter(Context context, List<News> newsList) {
        this(context);
        setData(newsList);
    }

    public void setData(List<News> newsList) {
        this.newsList = newsList;
        notifyDataSetChanged();
    }

    private class Holder {
        TextView team, date, content;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Holder holder;

        if (view == null) {
            holder = new Holder();
            view = inflater.inflate(R.layout.news_list_item, null);

            holder.team = view.findViewById(R.id.team_name);
            holder.date = view.findViewById(R.id.news_date);
            holder.content = view.findViewById(R.id.news_text);

            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        News news = newsList.get(position);

        holder.team.setText(news.getTeam());
        holder.date.setText(news.getDateString());
        holder.content.setText(news.getContent());

        return view;
    }

    @Override
    public int getCount() {
        if (newsList != null)
            return newsList.size();
        return 0;
    }

    @Override
    public Object getItem(int i) {
        if (newsList != null)
            return newsList.get(i);
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}
