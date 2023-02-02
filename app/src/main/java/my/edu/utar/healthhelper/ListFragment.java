package my.edu.utar.healthhelper;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class ListFragment extends Fragment {

    private TabLayout tablayout;
    private ViewPager viewpager;
    private VPAdapter vpAdapter;
    FloatingActionButton med_fab, measure_fab, activity_fab, symptom_fab;
    ExtendedFloatingActionButton fab;
    TextView medTxt, measureTxt, activityTxt, symptomTxt;
    Boolean isAllFabsVisible;

    public ListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        fab = view.findViewById(R.id.floating_action_button);
        med_fab = view.findViewById(R.id.add_med_fab);
        measure_fab = view.findViewById(R.id.add_measure_fab);
        activity_fab = view.findViewById(R.id.add_activity_fab);
        symptom_fab = view.findViewById(R.id.symptom_fab);
        medTxt = view.findViewById(R.id.add_med_text);
        measureTxt = view.findViewById(R.id.add_measure_text);
        activityTxt = view.findViewById(R.id.add_activity_text);
        symptomTxt = view.findViewById(R.id.symptom_text);

        med_fab.setVisibility(View.GONE);
        measure_fab.setVisibility(View.GONE);
        activity_fab.setVisibility(View.GONE);
        symptom_fab.setVisibility(View.GONE);
        medTxt.setVisibility(View.GONE);
        measureTxt.setVisibility(View.GONE);
        activityTxt.setVisibility(View.GONE);
        symptomTxt.setVisibility(View.GONE);

        isAllFabsVisible = false;

        // shrinked state initially
        fab.shrink();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isAllFabsVisible) {
                    //if true, all text and fab visible
                    med_fab.show();
                    measure_fab.show();
                    activity_fab.show();
                    symptom_fab.show();
                    medTxt.setVisibility(View.VISIBLE);
                    measureTxt.setVisibility(View.VISIBLE);
                    activityTxt.setVisibility(View.VISIBLE);
                    symptomTxt.setVisibility(View.VISIBLE);
                    medTxt.bringToFront();
                    measureTxt.bringToFront();
                    activityTxt.bringToFront();
                    symptomTxt.bringToFront();

                    //expand the extended fab
                    fab.extend();
                    isAllFabsVisible = true;
                }
                else {
                    //make all text and fab invisible
                    med_fab.hide();
                    measure_fab.hide();
                    activity_fab.hide();
                    symptom_fab.hide();
                    medTxt.setVisibility(View.GONE);
                    measureTxt.setVisibility(View.GONE);
                    activityTxt.setVisibility(View.GONE);
                    symptomTxt.setVisibility(View.GONE);

                    fab.shrink();
                    isAllFabsVisible = false;
                }
            }
        });

        med_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddMed.class));
            }
        });

        measure_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddMeasure.class));
            }
        });
        activity_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddActivity.class));
            }
        });
        symptom_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddSymptomCheck.class));
            }
        });

        tablayout = view.findViewById(R.id.listTab);
        viewpager = view.findViewById(R.id.viewPager);

        setupViewPager(viewpager);
        tablayout.setupWithViewPager(viewpager);

        return view;
    }

    private void setupViewPager(ViewPager viewPager) {

        VPAdapter adapter = new VPAdapter(getChildFragmentManager());
        adapter.addFragment(new MedList(), "Medicine");
        adapter.addFragment(new TrackerList(), "Tracker");
        viewPager.setAdapter(adapter);
    }

}