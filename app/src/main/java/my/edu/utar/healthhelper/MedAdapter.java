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


public class MedAdapter extends RecyclerView.Adapter<MedAdapter.medViewHolder> {

    private Context context;
    private ArrayList<Medicine> list;

    public MedAdapter(Context context, ArrayList<Medicine> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public void onBindViewHolder(@NonNull medViewHolder holder, int position) {

        Medicine medicine = list.get(position);
        List<String> day = medicine.getDayToTake();
        String days = "";
        for (int i=0; i<day.size(); i++) {
            days = days + day.get(i) + " ";
        }
        holder.medName.setText(medicine.getMedName());
        holder.time.setText(days + " - " + medicine.getTime());
        holder.numLeft.setText(String.valueOf(medicine.getMedNum()) + " left");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @NonNull
    @Override
    public medViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine, parent, false);
        return new medViewHolder(view);
    }

    public class medViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView medName, time, numLeft;

        public medViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            medName = itemView.findViewById(R.id.medName);
            time = itemView.findViewById(R.id.time);
            numLeft = itemView.findViewById(R.id.numLeft);
        }

        @Override
        public void onClick(View view) {
            int position = this.getAdapterPosition();
            Medicine medicine = list.get(position);
            String name = medicine.getMedName();
            String strength = medicine.getMedStrength();
            String time = medicine.getTime();
            String numLeft = String.valueOf(medicine.getMedNum());
            String refillNum = String.valueOf(medicine.getRefillNum());
            List<String> dayToTake = medicine.getDayToTake();

            Intent intent = new Intent(context,EditMed.class);
            intent.putExtra("mId", medicine.getId());
            intent.putExtra("mName", name);
            intent.putExtra("mStrength", strength);
            intent.putExtra("mNumLeft", numLeft);
            intent.putExtra("mRefillNum", refillNum);
            intent.putExtra("mTime", time);
            intent.putStringArrayListExtra("mDay", (ArrayList<String>) dayToTake);
            context.startActivity(intent);
        }
    }
}

