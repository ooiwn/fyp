package my.edu.utar.healthhelper;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.List;

public class UpdateTracker extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser current_user;
    DatabaseReference mDatabaseTracker;

    TimePickerDialog timePicker;
    EditText value, time;
    Button complete, skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_tracker);

        Intent intent = getIntent();
        String tId = intent.getStringExtra("tId");
        String tName = intent.getStringExtra("tName");
        String tUnit = intent.getStringExtra("tUnit");
        List<String> status = intent.getStringArrayListExtra("status");

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(tName);
        }

        value = findViewById(R.id.value);
        time = findViewById(R.id.time);
        complete = findViewById(R.id.complete);
        skip = findViewById(R.id.skip);

        value.setText("0 " + tUnit);

        time.setInputType(InputType.TYPE_NULL);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                timePicker = new TimePickerDialog(UpdateTracker.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        time.setText(String.format("%02d", sHour) + ":" + String.format("%02d", sMinute));
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


        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(value.getText().toString()) && !TextUtils.isEmpty(time.getText().toString())) {
                    status.add("Completed");
                    mDatabaseTracker.child(tId).child("status").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(UpdateTracker.this, "Completed", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(UpdateTracker.this, MainActivity.class);
                            startActivity(i);
                        }
                    });
                }
                else {
                    Toast.makeText(UpdateTracker.this, "Please fill in the data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status.add("Skipped");
                mDatabaseTracker.child(tId).child("status").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(UpdateTracker.this, "Skipped", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(UpdateTracker.this, MainActivity.class);
                        startActivity(i);
                    }
                });

            }
        });
    }
}