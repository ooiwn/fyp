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

public class AddActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Add Activity");
        }

        listView = findViewById(R.id.activity_listview);
        ArrayList<String> measureList = new ArrayList<>();
        measureList.add("Walking");
        measureList.add("Running");
        measureList.add("Deep Breathing");
        measureList.add("Sleep");
        measureList.add("Create own activity");

        //display the string in listView
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddActivity.this, android.R.layout.simple_list_item_1, measureList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0) {
                    //click walking
                    Intent intent = new Intent(AddActivity.this, DefineTracker.class);
                    intent.putExtra("tName", "Walking");
                    intent.putExtra("tUnit", "min");
                    startActivity(intent);
                }
                else if (position == 1){
                    //click running
                    Intent intent = new Intent(AddActivity.this, DefineTracker.class);
                    intent.putExtra("tName", "Running");
                    intent.putExtra("tUnit", "min");
                    startActivity(intent);
                }
                else if (position == 2) {
                    //click deep breathing
                    Intent intent = new Intent(AddActivity.this, DefineTracker.class);
                    intent.putExtra("tName", "Deep Breathing");
                    intent.putExtra("tUnit", "min");
                    startActivity(intent);
                }
                else if (position == 3) {
                    //click sleep
                    Intent intent = new Intent(AddActivity.this, DefineTracker.class);
                    intent.putExtra("tName", "Sleep");
                    intent.putExtra("tUnit", "hour");
                    startActivity(intent);
                }
                else {
                    //create own
                    startActivity(new Intent(AddActivity.this, OwnTracker.class));
                }
            }
        });
    }
}