package funix.prm.prm391x_alarmclock_thanhchfx01399;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TimePicker;

public class AddAlarmClock extends AppCompatActivity {

    private static final String PM = "PM";
    private static final String AM = "AM";
    Intent mIntent;
    TimePicker timePicker;
    Button btnAdd;
    TimeData db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm_clock);
    }

}
