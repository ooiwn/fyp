package my.edu.utar.healthhelper;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OwnTracker extends AppCompatActivity implements DurationDialog.DurationListener {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseUsers;
    private DatabaseReference mDatabaseTracker;

    TimePickerDialog timePicker;
    Calendar myCalendar = Calendar.getInstance();
    LinearLayout untilDate, hour, day;
    EditText trackerName, trackerUnit, trackerDuration, trackerFrequency, trackerTime, untilDatePicker, everyHour;
    CheckBox monCB, tueCB, wedCB, thuCB, friCB, satCB, sunCB;
    Button btnSave;
    int position = 0;
    Tracker tracker;
    List<String> days;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_tracker);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Add Tracker");
        }

        trackerName = findViewById(R.id.tracker_name);
        trackerUnit = findViewById(R.id.unit);
        trackerDuration = findViewById(R.id.duration);
        trackerFrequency = findViewById(R.id.frequency);
        trackerTime = findViewById(R.id.time);
        btnSave = findViewById(R.id.save_own_tracker);
        untilDate = findViewById(R.id.untilDate);
        untilDatePicker = findViewById(R.id.untilDatePicker);
        hour = findViewById(R.id.hours);
        everyHour = findViewById(R.id.everyHour);
        day = findViewById(R.id.days);
        monCB = findViewById(R.id.mon);
        tueCB = findViewById(R.id.tue);
        wedCB = findViewById(R.id.wed);
        thuCB = findViewById(R.id.thu);
        friCB = findViewById(R.id.fri);
        satCB = findViewById(R.id.sat);
        sunCB = findViewById(R.id.sun);

        untilDate.setVisibility(View.GONE);
        hour.setVisibility(View.GONE);
        day.setVisibility(View.GONE);

        String date = new SimpleDateFormat("MM/dd/yy", Locale.getDefault()).format(new Date());
        untilDatePicker.setText(date);

        trackerDuration.setInputType(InputType.TYPE_NULL);
        trackerDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment durationDialog = new DurationDialog();
                durationDialog.setCancelable(false);
                durationDialog.show(getSupportFragmentManager(), "Duration Dialog");
            }
        });

        trackerFrequency.setInputType(InputType.TYPE_NULL);
        trackerFrequency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(OwnTracker.this);
                String[] list = OwnTracker.this.getResources().getStringArray(R.array.frequency_items);
                builder.setTitle("Frequency").setSingleChoiceItems(list, position, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        position = i;
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        trackerFrequency.setText(list[position]);
                        if (position == 0) {
                            hour.setVisibility(View.VISIBLE);
                            day.setVisibility(View.GONE);
                        } else if (position == 2) {
                            day.setVisibility(View.VISIBLE);
                            hour.setVisibility(View.GONE);
                        } else {
                            hour.setVisibility(View.GONE);
                            day.setVisibility(View.GONE);
                        }
                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setCancelable(false);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        trackerTime.setInputType(InputType.TYPE_NULL);
        trackerTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                timePicker = new TimePickerDialog(OwnTracker.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        trackerTime.setText(String.format("%02d", sHour) + ":" + String.format("%02d", sMinute));
                    }
                }, hour, minutes, true);
                timePicker.show();
            }
        });

        days = new ArrayList<>();
        days.add("Day");
        //check the days to take med
        monCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (monCB.isChecked()) {
                    days.add("Mon");
                }
                else
                    days.remove("Mon");
            }
        });
        tueCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tueCB.isChecked()) {
                    days.add("Tue");
                }
                else
                    days.remove("Tue");
            }
        });
        wedCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (wedCB.isChecked()) {
                    days.add("Wed");
                }
                else
                    days.remove("Wed");
            }
        });
        thuCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (thuCB.isChecked()) {
                    days.add("Thu");
                }
                else
                    days.remove("Thu");
            }
        });
        friCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (friCB.isChecked()) {
                    days.add("Fri");
                }
                else
                    days.remove("Fri");
            }
        });
        satCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (satCB.isChecked()) {
                    days.add("Sat");
                }
                else
                    days.remove("Sat");
            }
        });
        sunCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sunCB.isChecked()) {
                    days.add("Sun");
                }
                else
                    days.remove("Sun");
            }
        });

        //Firebase
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();
        mDatabaseTracker = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("tracker");

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = trackerName.getText().toString();
                String unit = trackerUnit.getText().toString();
                String duration = trackerDuration.getText().toString();
                String frequency = trackerFrequency.getText().toString();
                String untilDate = untilDatePicker.getText().toString();
                String time = trackerTime.getText().toString();

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(unit) && !TextUtils.isEmpty(duration)
                        && !TextUtils.isEmpty(frequency) && !TextUtils.isEmpty(time) && !days.isEmpty()) {
                    tracker = new Tracker();
                    tracker.setTrackerName(name);
                    tracker.setUnit(unit);
                    tracker.setDuration(duration);
                    tracker.setUntilDate(untilDate);
                    tracker.setFrequency(frequency);
                    tracker.setEveryHour(Integer.parseInt(everyHour.getText().toString()));
                    tracker.setDayToTrack(days);
                    tracker.setTime(time);

                    addTrackerToFirebase(tracker);
                } else
                    Toast.makeText(OwnTracker.this, "Please fill in all details", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void addTrackerToFirebase(Tracker tracker) {
        String id = mDatabaseTracker.push().getKey();
        tracker.setId(id);
        mDatabaseTracker.child(id).setValue(tracker);
        Toast.makeText(this, "Tracker Added Successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onPositiveButtonClicked(String[] list, int position) {
        trackerDuration.setText(list[position]);
        if (position == 1) {
            untilDate.setVisibility(View.VISIBLE);
            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int day) {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, month);
                    myCalendar.set(Calendar.DAY_OF_MONTH, day);
                    String myFormat = "MM/dd/yy";
                    SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
                    untilDatePicker.setText(dateFormat.format(myCalendar.getTime()));
                }
            };
            untilDatePicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new DatePickerDialog(OwnTracker.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
        } else {
            untilDate.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNegativeButtonClicked() {
    }
}