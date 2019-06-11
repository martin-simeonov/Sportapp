package bg.nbu.sportapp.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBootReceiver extends BroadcastReceiver {

    NotificationAlarm alarm = new NotificationAlarm();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            alarm.setAlarm(context);
        }
    }
}
