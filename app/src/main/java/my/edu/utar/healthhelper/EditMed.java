package my.edu.utar.healthhelper;

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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditMed extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseUsers;
    private DatabaseReference mDatabaseMedicine;

    LinearLayout ll;
    TimePickerDialog timePicker;
    TextView deleteTxt;
    EditText medName, medStrength, medNum, refillNum, timeTxt;
    CheckBox monCB, tueCB, wedCB, thuCB, friCB, satCB, sunCB;
    Button saveBtn;
    Medicine medicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_med);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Edit Medicine");
        }

        Intent intent = getIntent();
        String mId = intent.getStringExtra("mId");
        String mName = intent.getStringExtra("mName");
        String mStrength = intent.getStringExtra("mStrength");
        String mNumLeft = intent.getStringExtra("mNumLeft");
        String mRefillNum = intent.getStringExtra("mRefillNum");
        String mTime = intent.getStringExtra("mTime");
        List<String> mDay = intent.getStringArrayListExtra("mDay");

        ll = findViewById(R.id.ll);
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

        medName.setText(mName);
        medStrength.setText(mStrength);
        medNum.setText(mNumLeft);
        refillNum.setText(mRefillNum);
        timeTxt.setText(mTime);
        if (mDay.contains("Mon")) {
            monCB.setChecked(true);
        }
        if (mDay.contains("Tue")) {
            tueCB.setChecked(true);
        }
        if (mDay.contains("Wed")) {
            wedCB.setChecked(true);
        }
        if (mDay.contains("Thu")) {
            thuCB.setChecked(true);
        }
        if (mDay.contains("Fri")) {
            friCB.setChecked(true);
        }
        if (mDay.contains("Sat")) {
            satCB.setChecked(true);
        }
        if (mDay.contains("Sun")) {
            sunCB.setChecked(true);
        }

        deleteTxt = new TextView(EditMed.this);
        deleteTxt.setText("Delete Medicine");
        deleteTxt.setTextColor(Color.RED);
        deleteTxt.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        deleteTxt.setTextSize(16);
        ll.addView(deleteTxt);
        deleteTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(EditMed.this);
                builder.setMessage("Do you sure want to delete this medicine?");
                builder.setCancelable(false);
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteMedicineFromFirebase(mId);
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


        //check the days to take med
        monCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (monCB.isChecked()) {
                    mDay.add("Mon");
                }
                else
                    mDay.remove("Mon");
            }
        });
        tueCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tueCB.isChecked()) {
                    mDay.add("Tue");
                }
                else
                    mDay.remove("Tue");
            }
        });
        wedCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (wedCB.isChecked()) {
                    mDay.add("Wed");
                }
                else
                    mDay.remove("Wed");
            }
        });
        thuCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (thuCB.isChecked()) {
                    mDay.add("Thu");
                }
                else
                    mDay.remove("Thu");
            }
        });
        friCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (friCB.isChecked()) {
                    mDay.add("Fri");
                }
                else
                    mDay.remove("Fri");
            }
        });
        satCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (satCB.isChecked()) {
                    mDay.add("Sat");
                }
                else
                    mDay.remove("Sat");
            }
        });
        sunCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sunCB.isChecked()) {
                    mDay.add("Sun");
                }
                else
                    mDay.remove("Sun");
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
                timePicker = new TimePickerDialog(EditMed.this, new TimePickerDialog.OnTimeSetListener() {
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
                        && !TextUtils.isEmpty(rNum) && !TextUtils.isEmpty(time) && !mDay.isEmpty()) {
                    //create med
                    medicine = new Medicine();
                    medicine.setId(mId);
                    medicine.setMedName(name);
                    medicine.setMedStrength(strength);
                    medicine.setMedNum(Integer.parseInt(num));
                    medicine.setRefillNum(Integer.parseInt(rNum));
                    medicine.setDayToTake(mDay);
                    medicine.setTime(time);

                    updateMedToFirebase(medicine);
                } else
                    Toast.makeText(EditMed.this, "Please fill in all details", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void deleteMedicineFromFirebase(String id) {
        if (id != null ) {
            mDatabaseMedicine.child(id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(EditMed.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(EditMed.this, MainActivity.class);
                    startActivity(i);
                }
            });
        }
        else {
            Toast.makeText(EditMed.this, "Failed to Delete", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateMedToFirebase(Medicine medicine) {
        if (medicine.getId()!=null) {
        mDatabaseMedicine.child(medicine.getId()).setValue(medicine).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(EditMed.this, "Medicine Updated Successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        } else {
            Toast.makeText(EditMed.this, "Failed to Update", Toast.LENGTH_SHORT).show();
        }
    }
}
