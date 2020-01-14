package funix.prm.prm391x_alarmclock_thanhchfx01399;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Calendar;
import java.util.List;

public class AlarmAdapter extends BaseAdapter {

    private static final String AM = "AM";
    private static final String PM = "PM";
    private Context context;
    private int layout;
    private List<AlarmClock> listAlarmClock;
    TimeData db;

    public AlarmAdapter(Context context, int layout, List<AlarmClock> listAlarmClock) {
        this.context = context;
        this.layout = layout;
        this.listAlarmClock = listAlarmClock;
        db = new TimeData(context);
    }

    @Override
    public int getCount() {
        return listAlarmClock.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout, null);

        TextView tvHours = convertView.findViewById(R.id.tvHours);
        TextView tvAmPm = convertView.findViewById(R.id.tvAmPm);
        final ToggleButton btnStatusAlarm = convertView.findViewById(R.id.btnStatusAlarm);

        final AlarmClock alarmClock = listAlarmClock.get(position);
        final int timeHours = alarmClock.getmHours();
        final int timeMinutes = alarmClock.getmMinutes();
        if (timeMinutes >= 10) {
            if (timeHours > 12) {
                tvHours.setText(String.valueOf(timeHours - 12) + ":" + String.valueOf(timeMinutes));
            } else {
                tvHours.setText(String.valueOf(timeHours) + ":" + String.valueOf(timeMinutes));
            }
        } else {
            if (timeHours > 12) {
                tvHours.setText(String.valueOf(timeHours - 12) + ":" + "0" + String.valueOf(timeMinutes));
            } else {
                tvHours.setText(String.valueOf(timeHours) + ":" + "0" + String.valueOf(timeMinutes));
            }
        }
        tvAmPm.setText(alarmClock.getmMidday());
        btnStatusAlarm.setChecked(alarmClock.ismStatus());

        // Set on check change event for button btnStatusAlarm to on/off alarm clock
        btnStatusAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                Intent mIntent = new Intent(context, AlarmReceiver.class);
                PendingIntent pendingIntent;
                if (isChecked) {
                    Calendar calendar = Calendar.getInstance();
                    long temp = calendar.getTimeInMillis();
                    calendar.set(Calendar.HOUR_OF_DAY, timeHours);
                    calendar.set(Calendar.MINUTE, timeMinutes);
                    calendar.set(Calendar.SECOND, 0);

                    // Check time, if over alarm time, set alarm to next day
                    if (calendar.getTimeInMillis() < temp) {
                        calendar.add(Calendar.DATE, 1);
                    }

                    // Put key on to alarm receiver
                    mIntent.putExtra("keyStatus", "On");
                    pendingIntent = PendingIntent.getBroadcast(context, alarmClock.getmID(), mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                }
                if (!isChecked) {
                    pendingIntent = PendingIntent.getBroadcast(context, alarmClock.getmID(), mIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                    // Put key off  to alarm receiver
                    mIntent.putExtra("keyStatus", "Off");

                    // Cancel alarm manager and pending intent
                    alarmManager.cancel(pendingIntent);
                    pendingIntent.cancel();

                    Calendar calendar = Calendar.getInstance();
                    long temp = calendar.getTimeInMillis();
                    calendar.set(Calendar.HOUR_OF_DAY, timeHours);
                    calendar.set(Calendar.MINUTE, timeMinutes);
                    calendar.set(Calendar.SECOND, 0);
                    long ringTime = temp - calendar.getTimeInMillis();
                    if (Math.abs(ringTime) < 60000 && temp > calendar.getTimeInMillis()) {
                        context.sendBroadcast(mIntent);
                    }
                }
            }
        });
        return convertView;
    }

    private void updateListView() {
        listAlarmClock.clear();
        listAlarmClock.addAll(db.getAllAlarmClock());
        notifyDataSetChanged();
    }
}
