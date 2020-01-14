package funix.prm.prm391x_alarmclock_thanhchfx01399;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String status = intent.getStringExtra("status");
        Intent myIntent = new Intent(context, Music.class);
        myIntent.putExtra("status", status);
        context.startService(myIntent);
    }
}
