package my.edu.utar.healthhelper;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Calendar;

public class AddSymptomCheck extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TimePickerDialog timePicker;
    Spinner spin_day, spin_num;
    String[] num = {"Once a day", "Twice a day", "Once a week"};
    String[] day = {"", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" , "Saturday", "Sunday"};
    EditText time1, time2;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_symptom_check);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Add Symptom Check");
        }

        save = findViewById(R.id.save_symptom_check);
        time1 = findViewById(R.id.time1);
        time2 = findViewById(R.id.time2);
        time2.setVisibility(View.GONE);

        spin_day = findViewById(R.id.day);
        spin_day.setOnItemSelectedListener(AddSymptomCheck.this);
        ArrayAdapter aa2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, day);
        aa2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_day.setAdapter(aa2);
        spin_day.setVisibility(View.GONE);

        spin_num = findViewById(R.id.num);
        spin_num.setOnItemSelectedListener(AddSymptomCheck.this);
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, num);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_num.setAdapter(aa);

        time1.setInputType(InputType.TYPE_NULL);
        time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                timePicker = new TimePickerDialog(AddSymptomCheck.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        time1.setText(sHour + ":" + sMinute);
                    }
                }, hour, minutes, true);
                timePicker.show();
            }
        });

        time2.setInputType(InputType.TYPE_NULL);
        time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                timePicker = new TimePickerDialog(AddSymptomCheck.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        time2.setText(sHour + ":" + sMinute);
                    }
                }, hour, minutes, true);
                timePicker.show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getItemAtPosition(i).equals("Once a day")) {
            time2.setVisibility(View.GONE);
            spin_day.setVisibility(View.GONE);
        }
        else if (adapterView.getItemAtPosition(i).equals("Twice a day")) {
            time2.setVisibility(View.VISIBLE);
            spin_day.setVisibility(View.GONE);
        }
        else {
            time2.setVisibility(View.GONE);
            spin_day.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}