package com.wahyu.waitinglistapps.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.wahyu.waitinglistapps.Model.PatientModel;
import com.wahyu.waitinglistapps.R;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wahyu_septiadi on 17, August 2020.
 * Visit My GitHub --> https://github.com/WahyuSeptiadi
 */

public class MyQueueAdapter extends RecyclerView.Adapter<MyQueueAdapter.ViewHolder> {

    private Activity mActivity;
    private ArrayList<PatientModel> patientModelArrayList;

    public MyQueueAdapter(Activity mActivity, ArrayList<PatientModel> patientModelArrayList) {
        this.mActivity = mActivity;
        this.patientModelArrayList = patientModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.list_item_queue, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PatientModel patientModel = patientModelArrayList.get(position);

        if (patientModel.getImageDoctor().substring(0, 4).equals("http")) {
            Picasso.get().load(patientModel.getImageDoctor()).into(holder.civProfileDoctor);
        } else {
            Picasso.get().load(R.drawable.icon_default_profile).into(holder.civProfileDoctor);
        }

        holder.tvNameDoctor.setText(patientModel.getNamaDokter());
        holder.tvSpesialisDoctor.setText(patientModel.getSpesialis());
        holder.tvDateRegist.setText(patientModel.getTanggalDaftar());
        holder.tvTimeFinish.setText(patientModel.getWaktuSelesai());
        holder.tvStatus.setText(patientModel.getStatus());

        if (patientModel.getStatus().equals("MENUNGGU")) {
            holder.tvStatus.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
        } else {
            holder.tvStatus.setTextColor(mActivity.getResources().getColor(R.color.colorAccent));
        }

        holder.ivFinish.setOnClickListener(view -> {
            holder.tvStatus.setText(patientModel.getStatus());
            showDialogAlertUpdate(patientModel.getIdDokter(), patientModel.getNamaDokter());
        });

        holder.ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.tvBtnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return patientModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView civProfileDoctor;
        private TextView tvNameDoctor, tvSpesialisDoctor;
        private TextView tvTimeFinish, tvDateRegist;
        private ImageView ivFinish, ivCancel;
        private TextView tvBtnDetails;
        private TextView tvStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            civProfileDoctor = itemView.findViewById(R.id.civ_mydoctor_queuelist);
            tvNameDoctor = itemView.findViewById(R.id.tv_namedoctor_queuelist);
            tvSpesialisDoctor = itemView.findViewById(R.id.tv_spesialisdoctor_queuelist);
            tvDateRegist = itemView.findViewById(R.id.tv_dateRegist_queuelist);
            tvTimeFinish = itemView.findViewById(R.id.tv_start_estimation_queuelist);
            tvStatus = itemView.findViewById(R.id.tv_status_queuelist);

            ivFinish = itemView.findViewById(R.id.checklist_queuelist);
            ivCancel = itemView.findViewById(R.id.cancel_queuelist);
            tvBtnDetails = itemView.findViewById(R.id.tv_btnCheck_queuelist);
        }
    }

    private void showDialogAlertUpdate(String dokterId, String namaDokter) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        String userid = firebaseUser.getUid();
        DatabaseReference rootMyQueue = FirebaseDatabase.getInstance().getReference("MyQueue").child(userid);
        DatabaseReference rootWaitingList = FirebaseDatabase.getInstance().getReference("WaitingList").child(dokterId);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mActivity);
        alertDialogBuilder.setTitle("SELESAI");
        alertDialogBuilder
                .setMessage("Apakah Anda telah selesai diperiksa oleh dokter " + namaDokter + " ?")
                .setCancelable(false)
                .setPositiveButton("Sudah", (dialog, id) -> {

                    //for home
                    HashMap<String, Object> myQueue = new HashMap<>();
                    myQueue.put("status", "SELESAI");
                    rootMyQueue.child(dokterId).updateChildren(myQueue);

                    //for patientlist per doctor
                    HashMap<String, Object> waitingList = new HashMap<>();
                    waitingList.put("waktuSelesai", "SELESAI");
                    rootWaitingList.child(userid).updateChildren(waitingList);

                    mActivity.overridePendingTransition(0, 0);
                    mActivity.startActivity(mActivity.getIntent());
                    mActivity.overridePendingTransition(0, 0);

                    Toast.makeText(mActivity, "Data berhasil diupdate", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Belum", (dialog, id) -> dialog.cancel());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
