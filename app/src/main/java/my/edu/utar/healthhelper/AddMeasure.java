package my.edu.utar.healthhelper;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class AddMeasure extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_measure);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Add Measurement");
        }

        listView = findViewById(R.id.measure_listview);
        ArrayList<String> measureList = new ArrayList<>();
        measureList.add("Blood Pressure");
        measureList.add("Heart Rate");
        measureList.add("Weight");
        measureList.add("Temperature");
        measureList.add("Create own measurement");

        //display the string in listView
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddMeasure.this, android.R.layout.simple_list_item_1, measureList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0) {
                    //click bp
                    Intent intent = new Intent(AddMeasure.this, DefineTracker.class);
                    intent.putExtra("tName", "Blood Pressure");
                    intent.putExtra("tUnit", "pulse");
                    startActivity(intent);
                }
                else if (position == 1){
                    //click hr
                    Intent intent = new Intent(AddMeasure.this, DefineTracker.class);
                    intent.putExtra("tName", "Heart Rate");
                    intent.putExtra("tUnit", "bpm");
                    startActivity(intent);
                }
                else if (position == 2) {
                    //click weight
                    Intent intent = new Intent(AddMeasure.this, DefineTracker.class);
                    intent.putExtra("tName", "Weight");
                    intent.putExtra("tUnit", "kg");
                    startActivity(intent);
                }
                else if (position == 3) {
                    //click temp
                    Intent intent = new Intent(AddMeasure.this, DefineTracker.class);
                    intent.putExtra("tName", "Temperature");
                    intent.putExtra("tUnit", "celsius");
                    startActivity(intent);
                }
                else {
                    //create own
                    startActivity(new Intent(AddMeasure.this, OwnTracker.class));
                }
            }
        });
    }
}