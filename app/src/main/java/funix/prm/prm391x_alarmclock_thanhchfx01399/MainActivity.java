package funix.prm.prm391x_alarmclock_thanhchfx01399;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ToggleButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TimeData db;
    ToggleButton btnStatusAlarm;
    ListView lvAlarm;
    List<AlarmClock> arrAlarmClock;
    AlarmAdapter adapter;
    ImageView imgAddAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgAddAlarm = findViewById(R.id.imgAddAlarm);

        addControls();
        setAdapter();
    }

    public void addAlarm(View view) {
        Intent intent = new Intent(MainActivity.this, AddAlarmClock.class);
        startActivity(intent);
    }

    public void addControls() {
        db = new TimeData(this);
        lvAlarm = findViewById(R.id.lvAlarm);
        btnStatusAlarm = findViewById(R.id.btnStatusAlarm);
        arrAlarmClock = db.getAllAlarmClock();
    }

    public void setAdapter() {
        if (adapter == null) {
            adapter = new AlarmAdapter(this, R.layout.row_alarm, arrAlarmClock);
            lvAlarm.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
            lvAlarm.setSelection(adapter.getCount() - 1);
        }
    }
}
