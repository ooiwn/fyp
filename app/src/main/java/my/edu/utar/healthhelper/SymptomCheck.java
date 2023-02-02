package my.edu.utar.healthhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SymptomCheck extends AppCompatActivity {

    ImageView sad, normal, happy, veryHappy;
    TextView bad, moderate, good, veryGood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptom_check);

        sad = findViewById(R.id.sad);
        normal = findViewById(R.id.normal);
        happy = findViewById(R.id.happy);
        veryHappy = findViewById(R.id.veryHappy);
        bad = findViewById(R.id.bad);
        moderate = findViewById(R.id.moderate);
        good = findViewById(R.id.good);
        veryGood = findViewById(R.id.veryGood);

        bad.setVisibility(View.GONE);
        moderate.setVisibility(View.GONE);
        good.setVisibility(View.GONE);
        veryGood.setVisibility(View.GONE);

        sad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bad.setVisibility(View.VISIBLE);
                moderate.setVisibility(View.GONE);
                good.setVisibility(View.GONE);
                veryGood.setVisibility(View.GONE);
            }
        });
        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bad.setVisibility(View.GONE);
                moderate.setVisibility(View.VISIBLE);
                good.setVisibility(View.GONE);
                veryGood.setVisibility(View.GONE);
            }
        });
        happy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bad.setVisibility(View.GONE);
                moderate.setVisibility(View.GONE);
                good.setVisibility(View.VISIBLE);
                veryGood.setVisibility(View.GONE);
            }
        });
        veryHappy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bad.setVisibility(View.GONE);
                moderate.setVisibility(View.GONE);
                good.setVisibility(View.GONE);
                veryGood.setVisibility(View.VISIBLE);
            }
        });

    }
}