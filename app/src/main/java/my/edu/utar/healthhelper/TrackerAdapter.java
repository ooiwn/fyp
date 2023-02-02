package my.edu.utar.healthhelper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;
import java.util.List;

public class TrackerAdapter extends RecyclerView.Adapter<TrackerAdapter.trackerViewHolder> {

    private Context context;
    private ArrayList<Tracker> list;

    public TrackerAdapter(Context context, ArrayList<Tracker> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public void onBindViewHolder(@NonNull TrackerAdapter.trackerViewHolder holder, int position) {
        Tracker tracker = list.get(position);
        String freq = tracker.getFrequency();
        List<String> day = tracker.getDayToTrack();
        String freq2 = "";
        if (freq.equals("Every few hours"))
            freq2 = "Every " + tracker.getEveryHour() + " hours";
        else if (freq.equals("Specific days")) {
            if (!day.isEmpty()) {
                if (day.contains("Day")) {
                    for (int i = 1; i < day.size(); i++) {
                        freq2 = freq2 + day.get(i) + " ";
                    }
                } else {
                    for (int i = 0; i < day.size(); i++) {
                        freq2 = freq2 + day.get(i) + " ";
                    }
                }
            }
        }
        else
            freq2 = "Daily";
        holder.tName.setText(tracker.getTrackerName());
        holder.tFrequency.setText(freq2);
        holder.tTime.setText(tracker.getTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @NonNull
    @Override
    public TrackerAdapter.trackerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tracker, parent, false);
        return new TrackerAdapter.trackerViewHolder(view);
    }

    public class trackerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tName, tFrequency, tTime;

        public trackerViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            tName = itemView.findViewById(R.id.tName);
            tFrequency = itemView.findViewById(R.id.tFrequency);
            tTime = itemView.findViewById(R.id.tTime);
        }

        @Override
        public void onClick(View view) {
            int position = this.getAdapterPosition();
            Tracker tracker = list.get(position);
            String name = tracker.getTrackerName();
            String unit = tracker.getUnit();
            String duration = tracker.getDuration();
            String untilDate = tracker.getUntilDate();
            String frequency = tracker.getFrequency();
            String everyHour = String.valueOf(tracker.getEveryHour());
            List<String> dayToTrack = tracker.getDayToTrack();
            String time = tracker.getTime();

            Intent intent = new Intent(context,EditTracker.class);
            intent.putExtra("tId", tracker.getId());
            intent.putExtra("tName", name);
            intent.putExtra("tUnit", unit);
            intent.putExtra("tDuration", duration);
            intent.putExtra("tUntilDate", untilDate);
            intent.putExtra("tFrequency", frequency);
            intent.putExtra("tEveryHour", everyHour);
            intent.putStringArrayListExtra("tDay", (ArrayList<String>) dayToTrack);
            intent.putExtra("tTime", time);
            context.startActivity(intent);
        }
    }
}
