package com.wahyu.waitinglistapps.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.wahyu.waitinglistapps.Model.PatientModel;
import com.wahyu.waitinglistapps.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wahyu_septiadi on 15, August 2020.
 * Visit My GitHub --> https://github.com/WahyuSeptiadi
 */

public class PatientListAdapter extends RecyclerView.Adapter<PatientListAdapter.ViewHolder> {

    private Activity mActivity;
    private ArrayList<PatientModel> patientModelArrayList;

    public PatientListAdapter(Activity mActivity, ArrayList<PatientModel> patientModelArrayList) {
        this.mActivity = mActivity;
        this.patientModelArrayList = patientModelArrayList;
    }

    @NonNull
    @Override
    public PatientListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.list_item_patient, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientListAdapter.ViewHolder holder, int position) {
        PatientModel patientModel = patientModelArrayList.get(position);

        if (patientModel.getImageURL().substring(0, 4).equals("http")) {
            Picasso.get().load(patientModel.getImageURL()).into(holder.civProfilePatient);
        } else {
            Picasso.get().load(R.drawable.icon_default_profile).into(holder.civProfilePatient);
        }
        holder.tvNamePatient.setText(patientModel.getNamaPasien());
        holder.tvEstimationFinish.setText(patientModel.getWaktuSelesai());

        int queueSort = position + 1;
        holder.tvQueueSort.setText(String.valueOf(queueSort));
    }

    @Override
    public int getItemCount() {
        return patientModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView civProfilePatient;
        private TextView tvNamePatient, tvEstimationFinish, tvQueueSort;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            civProfilePatient = itemView.findViewById(R.id.civ_patientlist);
            tvNamePatient = itemView.findViewById(R.id.tv_name_patientlist);
            tvEstimationFinish = itemView.findViewById(R.id.tv_estimationfinish_patientlist);
            tvQueueSort = itemView.findViewById(R.id.tv_queuesort_patientlist);
        }
    }
}
