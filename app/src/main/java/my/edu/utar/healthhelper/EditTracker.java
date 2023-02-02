package my.edu.utar.healthhelper;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class EditTracker extends AppCompatActivity implements DurationDialog.DurationListener {
    FirebaseAuth mAuth;
    FirebaseUser current_user;
    DatabaseReference mDatabaseTracker;

    TimePickerDialog timePicker;
    Calendar myCalendar = Calendar.getInstance();
    LinearLayout untilDate, hour, day, ll;
    TextView trackerName, trackerUnit, deleteTxt;
    EditText trackerDuration, trackerFrequency, trackerTime, untilDatePicker, everyHour;
    CheckBox monCB, tueCB, wedCB, thuCB, friCB, satCB, sunCB;
    Button btnSave;
    int position = 0;
    Tracker tracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_tracker);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Edit Tracker");
        }

        Intent intent = getIntent();
        String tId = intent.getStringExtra("tId");
        String tName = intent.getStringExtra("tName");
        String tUnit = intent.getStringExtra("tUnit");
        String tDuration = intent.getStringExtra("tDuration");
        String tUntilDate = intent.getStringExtra("tUntilDate");
        String tFrequency = intent.getStringExtra("tFrequency");
        String tEveryHour = intent.getStringExtra("tEveryHour");
        List<String> tDay= intent.getStringArrayListExtra("tDay");
        String tTime = intent.getStringExtra("tTime");

        ll = findViewById(R.id.ll);
        trackerName = findViewById(R.id.defineTrackerName);
        trackerUnit = findViewById(R.id.defineTrackerUnit);
        trackerDuration = findViewById(R.id.defineTrackerDuration);
        trackerFrequency = findViewById(R.id.defineTrackerFrequency);
        trackerTime = findViewById(R.id.defineTrackerTime);
        btnSave = findViewById(R.id.save_define_tracker);
        untilDate = findViewById(R.id.defineTrackerUntilDate);
        untilDatePicker = findViewById(R.id.defineTrackerUntilDatePicker);
        hour = findViewById(R.id.defineTrackerHours);
        everyHour = findViewById(R.id.defineTrackerEveryHour);
        day = findViewById(R.id.defineTrackerDays);
        monCB = findViewById(R.id.mon);
        tueCB = findViewById(R.id.tue);
        wedCB = findViewById(R.id.wed);
        thuCB = findViewById(R.id.thu);
        friCB = findViewById(R.id.fri);
        satCB = findViewById(R.id.sat);
        sunCB = findViewById(R.id.sun);

        deleteTxt = new TextView(EditTracker.this);
        deleteTxt.setText("Delete Tracker");
        deleteTxt.setTextColor(Color.RED);
        deleteTxt.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        deleteTxt.setTextSize(16);
        ll.addView(deleteTxt);
        deleteTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(EditTracker.this);
                builder.setMessage("Do you sure want to delete this tracker?");
                builder.setCancelable(false);
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteTrackerFromFirebase(tId);
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });


        untilDate.setVisibility(View.GONE);
        hour.setVisibility(View.GONE);
        day.setVisibility(View.GONE);

        trackerName.setText(tName);
        trackerUnit.setText(tUnit);
        trackerDuration.setText(tDuration);
        trackerFrequency.setText(tFrequency);
        trackerTime.setText(tTime);
        untilDatePicker.setText(tUntilDate);
        everyHour.setText(tEveryHour);
        if (tDay.contains("Mon")) {
            monCB.setChecked(true);
        }
        if (tDay.contains("Tue")) {
            tueCB.setChecked(true);
        }
        if (tDay.contains("Wed")) {
            wedCB.setChecked(true);
        }
        if (tDay.contains("Thu")) {
            thuCB.setChecked(true);
        }
        if (tDay.contains("Fri")) {
            friCB.setChecked(true);
        }
        if (tDay.contains("Sat")) {
            satCB.setChecked(true);
        }
        if (tDay.contains("Sun")) {
            sunCB.setChecked(true);
        }

        //check the days to take med
        //tDay.add("Day");
        monCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (monCB.isChecked()) {
                    tDay.add("Mon");
                }
                else
                    tDay.remove("Mon");
            }
        });
        tueCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tueCB.isChecked()) {
                    tDay.add("Tue");
                }
                else
                    tDay.remove("Tue");
            }
        });
        wedCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (wedCB.isChecked()) {
                    tDay.add("Wed");
                }
                else
                    tDay.remove("Wed");
            }
        });
        thuCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (thuCB.isChecked()) {
                    tDay.add("Thu");
                }
                else
                    tDay.remove("Thu");
            }
        });
        friCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (friCB.isChecked()) {
                    tDay.add("Fri");
                }
                else
                    tDay.remove("Fri");
            }
        });
        satCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (satCB.isChecked()) {
                    tDay.add("Sat");
                }
                else
                    tDay.remove("Sat");
            }
        });
        sunCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sunCB.isChecked()) {
                    tDay.add("Sun");
                }
                else
                    tDay.remove("Sun");
            }
        });


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
                AlertDialog.Builder builder = new AlertDialog.Builder(EditTracker.this);
                String[] list = EditTracker.this.getResources().getStringArray(R.array.frequency_items);
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
                timePicker = new TimePickerDialog(EditTracker.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        trackerTime.setText(String.format("%02d", sHour) + ":" + String.format("%02d", sMinute));
                    }
                }, hour, minutes, true);
                timePicker.show();
            }
        });

        //Firebase
        mAuth = FirebaseAuth.getInstance();
        current_user = FirebaseAuth.getInstance().getCurrentUser();
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
                        && !TextUtils.isEmpty(frequency) && !TextUtils.isEmpty(time) && !tDay.isEmpty()) {

                    tracker = new Tracker();
                    tracker.setId(tId);
                    tracker.setTrackerName(name);
                    tracker.setUnit(unit);
                    tracker.setDuration(duration);
                    tracker.setUntilDate(untilDate);
                    tracker.setFrequency(frequency);
                    tracker.setEveryHour(Integer.parseInt(everyHour.getText().toString()));
                    tracker.setDayToTrack(tDay);
                    tracker.setTime(time);

                    updateTrackerToFirebase(tracker);

                } else
                    Toast.makeText(EditTracker.this, "Please fill in all details", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void deleteTrackerFromFirebase(String id) {
        if (id != null ) {
            mDatabaseTracker.child(id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(EditTracker.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(EditTracker.this, MainActivity.class);
                    startActivity(i);
                }
            });
        }
        else {
            Toast.makeText(EditTracker.this, "Failed to Delete", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateTrackerToFirebase(Tracker tracker) {
        if (tracker.getId()!=null) {
            mDatabaseTracker.child(tracker.getId()).setValue(tracker).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(EditTracker.this, "Tracker Updated Successfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(EditTracker.this, MainActivity.class);
                    startActivity(i);
                }
            });
        } else {
            Toast.makeText(EditTracker.this, "Failed to Update", Toast.LENGTH_SHORT).show();
        }
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
                    new DatePickerDialog(EditTracker.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
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
