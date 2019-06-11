package bg.nbu.sportapp.services;

import android.app.AlarmManager;
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
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import bg.nbu.sportapp.MainActivity;
import bg.nbu.sportapp.R;
import bg.nbu.sportapp.models.Event;
import bg.nbu.sportapp.models.Team;

public class NotificationAlarm extends BroadcastReceiver {

    private static boolean DEBUG = false;

    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();

        this.context = context;
        if (DEBUG) {
            Toast.makeText(context, "Alarm triggered", Toast.LENGTH_SHORT).show();
        }

        getIncomingEvents();

        wl.release();
    }

    public void setAlarm(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, NotificationAlarm.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        if (DEBUG) {
            am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 60 * 1, pi); // Millisec * Second * Minute
        } else {
            am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), AlarmManager.INTERVAL_HOUR, pi); // Millisec * Second * Minute
        }
    }

    public void cancelAlarm(Context context) {
        Intent intent = new Intent(context, NotificationAlarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    private void setNotification(Event event) {
        Intent resultIntent = new Intent();
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        String channelId = "SportAppId";
        String channelName = "Events";

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.notification_icon)
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
        mNotificationManager.notify(event.getId(), mBuilder.build());
    }


    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private void checkTimes(List<Team> teamList) {
        for (Team team : teamList) {
            if (team.getEvents() != null) {
                for (Event event : team.getEvents()) {
                    if (DEBUG) {
                        event.setEventDate("2019-06-11");
                        event.setEventStartTime("23:50:00");
                    }
                    Date currentTime = new Date();
                    try {
                        Date eventTime = dateFormatter.parse(event.getEventDate() + " " + event.getEventStartTime());
                        // Less than hour
                        if ((eventTime.getTime() - currentTime.getTime()) < 1000 * 60 * 60) {
                            setNotification(event);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
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
