package my.edu.utar.healthhelper;

import android.content.Context;
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

public class HomeMedAdapter extends RecyclerView.Adapter<HomeMedAdapter.medViewHolder> {

    private Context context;
    private ArrayList<Medicine> list;

    public HomeMedAdapter(Context context, ArrayList<Medicine> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeMedAdapter.medViewHolder holder, int position) {
        Medicine medicine = list.get(position);
        List<String> day = medicine.getDayToTake();
        String days = "";
        for (int i=0; i<day.size(); i++) {
            days = days + day.get(i) + " ";
        }
        holder.time.setText(medicine.getTime());
        holder.name.setText(medicine.getMedName());
        holder.unit.setText(medicine.getMedStrength() + " pills");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @NonNull
    @Override
    public HomeMedAdapter.medViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list, parent, false);
        return new HomeMedAdapter.medViewHolder(view);
    }

    static class medViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView time, name, unit;

        public medViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            name = itemView.findViewById(R.id.name);
            unit = itemView.findViewById(R.id.strength);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
