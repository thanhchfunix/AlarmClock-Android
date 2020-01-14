package funix.prm.prm391x_alarmclock_thanhchfx01399;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class Music extends Service {

    MediaPlayer ringTone;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String status = intent.getStringExtra("status");
        if (status.equals("On")) {
            ringTone = MediaPlayer.create(this, R.raw.ringtone);
            ringTone.start();
        } else if (status.equals("Off")) {
            ringTone.stop();
            ringTone.reset();
        }
        return START_NOT_STICKY;
    }
}
