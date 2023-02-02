package my.edu.utar.healthhelper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeTrackerAdapter extends RecyclerView.Adapter<HomeTrackerAdapter.homeTrackerViewHolder>{

    private Context context;
    private ArrayList<Tracker> list;

    public HomeTrackerAdapter(Context context, ArrayList<Tracker> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HomeTrackerAdapter.homeTrackerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list, parent, false);
        return new HomeTrackerAdapter.homeTrackerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull homeTrackerViewHolder holder, int position) {
        Tracker tracker = list.get(position);
        holder.time.setText(tracker.getTime());
        holder.name.setText(tracker.getTrackerName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class homeTrackerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView time, name;

        public homeTrackerViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            time = itemView.findViewById(R.id.time);
            name = itemView.findViewById(R.id.name);
        }

        @Override
        public void onClick(View view) {
            int position = this.getAdapterPosition();
            Tracker tracker = list.get(position);
            List<String> status = tracker.getStatus();

            Intent intent = new Intent(context,UpdateTracker.class);
            intent.putExtra("tId", tracker.getId());
            intent.putExtra("tName", tracker.getTrackerName());
            intent.putExtra("tUnit", tracker.getUnit());
            intent.putStringArrayListExtra("status", (ArrayList<String>) status);
            context.startActivity(intent);
        }
    }
}
