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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.wahyu.waitinglistapps.Model.DoctorModel;
import com.wahyu.waitinglistapps.Model.UserModel;
import com.wahyu.waitinglistapps.R;
import com.wahyu.waitinglistapps.View.Activity.DoctorDetailsActivity;
import com.wahyu.waitinglistapps.View.Activity.DoctorListActivity;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wahyu_septiadi on 15, August 2020.
 * Visit My GitHub --> https://github.com/WahyuSeptiadi
 */

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.ViewHolder> {

    private Activity mActivity;
    private ArrayList<DoctorModel> doctorModelArrayList;

    private String userType;
    private UserModel userModel;

    public DoctorListAdapter(Activity mActivity, ArrayList<DoctorModel> doctorModelArrayList) {
        this.mActivity = mActivity;
        this.doctorModelArrayList = doctorModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.list_item_doctor, parent, false);
        userModel = new UserModel();
        getTypeUser();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DoctorModel doctorModel = doctorModelArrayList.get(position);
        if (doctorModel.getImageURL().substring(0, 4).equals("http")) {
            Picasso.get().load(doctorModel.getImageURL()).into(holder.civProfileDoctor);
        } else {
            Picasso.get().load(R.drawable.icon_default_profile).into(holder.civProfileDoctor);
        }

        holder.tvNameDr.setText(doctorModel.getName());
        holder.tvSpesialis.setText(doctorModel.getSpesialis());

        holder.itemView.setOnClickListener(view -> {
            Intent toDetails = new Intent(mActivity, DoctorDetailsActivity.class);
            mActivity.startActivity(toDetails);
        });

        holder.itemView.setOnLongClickListener(view -> {
            if (userType.equals("admin")) {
                final Dialog dialog = new Dialog(mActivity);
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.dialog_edit_delete);
                dialog.show();

                Button editButton = dialog.findViewById(R.id.btnEdit);
                Button deleteButton = dialog.findViewById(R.id.btnDelete);

                //apabila tombol edit diklik
                editButton.setOnClickListener(view1 -> {
                            dialog.dismiss();
                        }
                );

                //apabila tombol delete diklik
                deleteButton.setOnClickListener(view2 -> {
                            dialog.dismiss();
                            showDialogAlertDelete(doctorModel.getId());
                        }
                );
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return doctorModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView civProfileDoctor;
        private TextView tvNameDr, tvSpesialis;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            civProfileDoctor = itemView.findViewById(R.id.civ_doctorlist);
            tvNameDr = itemView.findViewById(R.id.tv_name_doctorlist);
            tvSpesialis = itemView.findViewById(R.id.tv_spesialis_doctorlist);
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

    private void showDialogAlertDelete(String key) {
        DatabaseReference rootDoctors = FirebaseDatabase.getInstance().getReference("Doctors");

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mActivity);
        alertDialogBuilder.setTitle("HAPUS DATA DOKTER");
        alertDialogBuilder
                .setMessage("Apakah Anda yakin ingin menghapus data user ini ?")
                .setCancelable(false)
                .setPositiveButton("Ya, tentu", (dialog, id) -> {
                    rootDoctors.child(key).removeValue();

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
