package my.edu.utar.healthhelper;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddMed extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseUsers;
    private DatabaseReference mDatabaseMedicine;

    TimePickerDialog timePicker;
    EditText medName, medStrength, medNum, refillNum, timeTxt;
    CheckBox monCB, tueCB, wedCB, thuCB, friCB, satCB, sunCB;
    Button saveBtn;
    Medicine medicine;
    List<String> days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_med);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Add Medicine");
        }

        medName = findViewById(R.id.med_name);
        medStrength = findViewById(R.id.med_strength);
        medNum = findViewById(R.id.med_num);
        refillNum = findViewById(R.id.refill_num);
        timeTxt = findViewById(R.id.time);
        monCB = findViewById(R.id.mon);
        tueCB = findViewById(R.id.tue);
        wedCB = findViewById(R.id.wed);
        thuCB = findViewById(R.id.thu);
        friCB = findViewById(R.id.fri);
        satCB = findViewById(R.id.sat);
        sunCB = findViewById(R.id.sun);
        saveBtn = findViewById(R.id.save_med);

        days = new ArrayList<>();
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

        //set a time picker
        timeTxt.setInputType(InputType.TYPE_NULL);
        timeTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                timePicker = new TimePickerDialog(AddMed.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        timeTxt.setText(String.format("%02d", sHour) + ":" + String.format("%02d", sMinute));
                    }
                }, hour, minutes, true);
                timePicker.show();
            }
        });

        //Firebase
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();
        mDatabaseMedicine = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("medicine");

        //save med
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = medName.getText().toString();
                String strength = medStrength.getText().toString();
                String num = medNum.getText().toString();
                String rNum = refillNum.getText().toString();
                String time = timeTxt.getText().toString();


                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(strength) && !TextUtils.isEmpty(num)
                        && !TextUtils.isEmpty(rNum) && !TextUtils.isEmpty(time) && !days.isEmpty()) {
                    //create med
                    medicine = new Medicine();
                    medicine.setMedName(name);
                    medicine.setMedStrength(strength);
                    medicine.setMedNum(Integer.parseInt(num));
                    medicine.setRefillNum(Integer.parseInt(rNum));
                    medicine.setDayToTake(days);
                    medicine.setTime(time);

                    addMedToFirebase(medicine);
                }
                else
                    Toast.makeText(AddMed.this, "Please fill in all details", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void addMedToFirebase(Medicine medicine) {
        String id = mDatabaseMedicine.push().getKey();
        medicine.setId(id);
        mDatabaseMedicine.child(id).setValue(medicine);
        Toast.makeText(this, "Medicine Added Successfully", Toast.LENGTH_SHORT).show();

        //back to the fragment
//        Fragment fragment = new ListFragment();
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.activity_add_med, fragment).commit();
        finish();
    }
}