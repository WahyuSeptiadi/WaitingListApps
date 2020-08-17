package com.wahyu.waitinglistapps.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.wahyu.waitinglistapps.Model.PatientModel;
import com.wahyu.waitinglistapps.Model.UserModel;
import com.wahyu.waitinglistapps.R;
import com.wahyu.waitinglistapps.View.Activity.PatientDetailsActivity;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wahyu_septiadi on 15, August 2020.
 * Visit My GitHub --> https://github.com/WahyuSeptiadi
 */

public class PatientListAdapter extends RecyclerView.Adapter<PatientListAdapter.ViewHolder> {

    private Activity mActivity;
    private ArrayList<PatientModel> patientModelArrayList;

    private String userType;
    private UserModel userModel;

    public PatientListAdapter(Activity mActivity, ArrayList<PatientModel> patientModelArrayList) {
        this.mActivity = mActivity;
        this.patientModelArrayList = patientModelArrayList;
    }

    @NonNull
    @Override
    public PatientListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.list_item_patient, parent, false);
        userModel = new UserModel();
        getTypeUser();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientListAdapter.ViewHolder holder, int position) {
        PatientModel patientModel = patientModelArrayList.get(position);

        if (!patientModel.getImageURL().equals("")) {
            if (patientModel.getImageURL().substring(0, 4).equals("http")) {
                Picasso.get().load(patientModel.getImageURL()).into(holder.civProfilePatient);
            } else {
                Picasso.get().load(R.drawable.icon_default_profile).into(holder.civProfilePatient);
            }
        } else {
            Picasso.get().load(R.drawable.icon_default_profile).into(holder.civProfilePatient);
        }

        holder.tvNamePatient.setText(patientModel.getNamaPasien());
        holder.tvEstimationFinish.setText(patientModel.getWaktuSelesai());

        if (patientModel.getWaktuSelesai().equals("SELESAI")) {
            holder.tvEstimate.setVisibility(View.GONE);
            holder.tvEstimationFinish.setTextColor(mActivity.getResources().getColor(R.color.colorAccent));
        }

        int queueSort = position + 1;
        holder.tvQueueSort.setText(String.valueOf(queueSort));

        holder.itemView.setOnClickListener(view -> {
            String image = patientModel.getImageURL();
            String name = patientModel.getNamaPasien();
            String keluhan = patientModel.getKeluhanPasien();
            String penyakit = patientModel.getPenyakitPasien();
            String alamat = patientModel.getAlamatPasien();
            String umur = patientModel.getUmurPasien();
            String jenis = patientModel.getJenisPasien();
            String daftar = patientModel.getWaktuDaftar();
            String selesai = patientModel.getWaktuSelesai();

            Intent toPatientDetails = new Intent(mActivity, PatientDetailsActivity.class);
            toPatientDetails.putExtra("image", image);
            toPatientDetails.putExtra("name", name);
            toPatientDetails.putExtra("keluhan", keluhan);
            toPatientDetails.putExtra("penyakit", penyakit);
            toPatientDetails.putExtra("alamat", alamat);
            toPatientDetails.putExtra("umur", umur);
            toPatientDetails.putExtra("jenis", jenis);
            toPatientDetails.putExtra("daftar", daftar);
            toPatientDetails.putExtra("selesai", selesai);
            mActivity.startActivity(toPatientDetails);
        });

        holder.itemView.setOnLongClickListener(view -> {
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

            assert firebaseUser != null;
            if (userType.equals("admin") || firebaseUser.getUid().equals(patientModel.getIdPasien())) {
                final Dialog dialog = new Dialog(mActivity);
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.dialog_edit_delete);
                dialog.show();

                TextView choice = dialog.findViewById(R.id.tv_choice_action);
                Button editButton = dialog.findViewById(R.id.btnEdit);
                Button deleteButton = dialog.findViewById(R.id.btnDelete);

                //sementara btn Edit jadi Hapus
                editButton.setText(R.string.str_delete);
                choice.setVisibility(View.GONE);
                deleteButton.setVisibility(View.GONE);

                //apabila tombol edit diklik
                editButton.setOnClickListener(view1 -> {
                            dialog.dismiss();
                            showDialogAlertDelete(patientModel.getIdDokter(), patientModel.getIdPasien());
                        }
                );

//                //apabila tombol delete diklik
//                deleteButton.setOnClickListener(view2 -> {
//                            dialog.dismiss();
//                            showDialogAlertDelete(patientModel.getIdDokter(), patientModel.getIdPasien());
//                        }
//                );
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return patientModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView civProfilePatient;
        private TextView tvNamePatient, tvEstimationFinish, tvQueueSort, tvEstimate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            civProfilePatient = itemView.findViewById(R.id.civ_patientlist);
            tvNamePatient = itemView.findViewById(R.id.tv_name_patientlist);
            tvEstimationFinish = itemView.findViewById(R.id.tv_estimationfinish_patientlist);
            tvQueueSort = itemView.findViewById(R.id.tv_queuesort_patientlist);
            tvEstimate = itemView.findViewById(R.id.tv_estimationfinish);
        }
    }

    private void getTypeUser() {
        DatabaseReference rootRoomChats = FirebaseDatabase.getInstance().getReference("Users");
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        String userid = firebaseUser.getUid();

        rootRoomChats.child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userModel = snapshot.getValue(UserModel.class);
                assert userModel != null;

                userType = userModel.getType();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showDialogAlertDelete(String doctorId, String patientId) {
        DatabaseReference rootWaitingList = FirebaseDatabase.getInstance().getReference("WaitingList").child(doctorId);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mActivity);
        alertDialogBuilder.setTitle("HAPUS DATA");
        alertDialogBuilder
                .setMessage("Apakah Anda yakin ingin menghapus data pendaftaran ini ?")
                .setCancelable(false)
                .setPositiveButton("Ya, tentu", (dialog, id) -> {
                    rootWaitingList.child(patientId).removeValue();

                    mActivity.overridePendingTransition(0, 0);
                    mActivity.startActivity(mActivity.getIntent());
                    mActivity.finish();
                    mActivity.overridePendingTransition(0, 0);

                    Toast.makeText(mActivity, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Gak jadi", (dialog, id) -> dialog.cancel());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
