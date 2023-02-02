package my.edu.utar.healthhelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MoreFragment extends Fragment {

    public MoreFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);

        ListView listView = view.findViewById(R.id.listview);
        Button btnLogout = view.findViewById(R.id.btnLogout);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        //String list
        ArrayList<String> values = new ArrayList<>();
        values.add("Refill");
        values.add("Helpful Message");
        values.add("Appointment");
        values.add("Doctor");
        values.add("Friend");

        //display the string in listView
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, values);
        listView.setAdapter(adapter);

        //Log Out
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        return view;
    }
}