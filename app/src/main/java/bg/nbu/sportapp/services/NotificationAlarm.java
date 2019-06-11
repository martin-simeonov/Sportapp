package bg.nbu.sportapp.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import bg.nbu.sportapp.MainActivity;
import bg.nbu.sportapp.R;
import bg.nbu.sportapp.models.Event;
import bg.nbu.sportapp.models.Team;

public class NotificationAlarm extends BroadcastReceiver {

    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

        Toast.makeText(context, "Alarm Triggered", Toast.LENGTH_SHORT).show();

        Event e = new Event();
        e.setEvent("test");
        setNotification(e);

        getIncomingEvents();
    }

    private void setNotification(Event event) {
        Intent resultIntent = new Intent();
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        String channelId = "SportAppId";
        String channelName = "Events";

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.arsenal)
                .setPriority(Notification.PRIORITY_MAX)
                .setContentTitle("Sport app")
                .setContentText(event.getEvent() + " at " + event.getEventStartTime())
                .setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            NotificationChannel chan = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_NONE);
            mNotificationManager.createNotificationChannel(chan);
            mBuilder.setChannelId(channelId);
        }
        mNotificationManager.notify(10001, mBuilder.build());
    }


    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");

    private void checkTimes(List<Team> teamList) {
        for (Team team : teamList) {
            if (team.getEvents() != null) {
                for (Event event : team.getEvents()) {
                    /*if (event.getEventDate().equals(dateFormatter.format(new Date()))) {
                        Date currentTime = new Date();
                        try {
                            Date eventTime = timeFormatter.parse(event.getEventStartTime());
                            // Less than hour
                            if ((eventTime.getTime() - currentTime.getTime()) < 1000 * 60 * 60) {
                                setNotification(event);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }*/

                    setNotification(event);
                }
            }
        }
    }

    private void getIncomingEvents() {
        SportsService.GetTeamWithEvents(getFavoriteTeams(), new SportsService.TeamsCallback() {
            @Override
            public void onResult(List<Team> teamList) {
                checkTimes(teamList);
            }

            @Override
            public void onFail() {

            }
        });
    }

    private List<Integer> getFavoriteTeams() {
        SharedPreferences favoriteTeamsStore = context.getSharedPreferences(MainActivity.FAVORITE_TEAMS_STORE, 0);
        Set<String> favoriteTeams = favoriteTeamsStore.getStringSet(MainActivity.FAVORITE_TEAMS, null);

        if (favoriteTeams == null)
            return new ArrayList<>();

        final List<Integer> teamIds = new ArrayList<>();
        favoriteTeams.forEach(team -> teamIds.add(Integer.valueOf(team)));

        return teamIds;
    }
}
