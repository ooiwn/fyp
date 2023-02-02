package my.edu.utar.healthhelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    HomeMedAdapter madapter;
    HomeTrackerAdapter tadapter;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseUsers;
    private DatabaseReference mDatabase;
    ArrayList<Medicine> mlist;
    ArrayList<Tracker> tlist;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //Firebase
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        if (current_user == null) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
        String uid = current_user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(uid);

        recyclerView = view.findViewById(R.id.home_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mlist = new ArrayList<>();
        mDatabase.child("medicine").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Medicine medicine = dataSnapshot.getValue(Medicine.class);
                    mlist.add(medicine);

                }
                madapter = new HomeMedAdapter(getActivity(), mlist);
                recyclerView.setAdapter(madapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        tlist = new ArrayList<>();
        mDatabase.child("tracker").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Tracker tracker = dataSnapshot.getValue(Tracker.class);
                    Calendar calendar = Calendar.getInstance();
                    int day = calendar.get(Calendar.DAY_OF_WEEK);
                    String currentDay;
                    switch (day) {
                        case 1:
                            currentDay = "Sun";
                            break;
                        case 2:
                            currentDay = "Mon";
                            break;
                        case 3:
                            currentDay = "Tue";
                            break;
                        case 4:
                            currentDay = "Wed";
                            break;
                        case 5:
                            currentDay = "Thu";
                            break;
                        case 6:
                            currentDay = "Fri";
                            break;
                        case 7:
                            currentDay = "Sat";
                            break;
                        default:
                            currentDay = "Error";
                    }
                    String today = new SimpleDateFormat("MM/dd/yy", Locale.getDefault()).format(new Date());

                    if (tracker.getDuration().equals("No end date") || (tracker.getDuration().equals("until a certain date")
                            && today.compareTo(tracker.getUntilDate()) < 0)) {
                        if (tracker.getFrequency().equals("Daily") || tracker.getFrequency().equals("Every few hours") ||
                                (tracker.getFrequency().equals("Specific days") && tracker.getDayToTrack().contains(currentDay))) {
                            tlist.add(tracker);
                        }
                    }

                }
                tadapter = new HomeTrackerAdapter(getActivity(), tlist);
                recyclerView.setAdapter(tadapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }

}