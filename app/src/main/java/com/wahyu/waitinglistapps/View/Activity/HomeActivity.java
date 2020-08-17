package com.wahyu.waitinglistapps.View.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.wahyu.waitinglistapps.Model.UserModel;
import com.wahyu.waitinglistapps.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private CircleImageView civProfileUser;

    private DatabaseReference reference;
    private FirebaseUser firebaseUser;
    private Calendar calendar;

    private SharedPreferences preferences;
    private String userType, imageURL, name, email, isTerdaftar, terdaftarReal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.topbar_home);
        setSupportActionBar(toolbar);
        setTitle("");

        civProfileUser = findViewById(R.id.civ_imageProfileHome);
        CardView cvDoctorList = findViewById(R.id.cv_doctorlist_home);
        TextView tvCurrentDate = findViewById(R.id.tv_currentDate);
        CardView pickQueue = findViewById(R.id.cv_pickqueue_home);

        // ubah ke adapter list
        TextView tvBlmTerdaftar = findViewById(R.id.tv_belumdaftar);
        TextView tvTerdaftar = findViewById(R.id.tv_terdaftar);

        //inisialisasi
        reference = FirebaseDatabase.getInstance().getReference("Users");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        calendar = Calendar.getInstance();

//        getPrefTerdaftar(); // salah

//        // preference
//        if (isTerdaftar != null) {
//            if (isTerdaftar.equals("terdaftar")) {
//                tvTerdaftar.setVisibility(View.VISIBLE);
//                tvBlmTerdaftar.setVisibility(View.INVISIBLE);
//            } else {
//                tvTerdaftar.setVisibility(View.INVISIBLE);
//                tvBlmTerdaftar.setVisibility(View.VISIBLE);
//            }
//        }

//        // for realtime db
//        if (terdaftarReal != null) {
//            if (terdaftarReal.equals("true")) {
//                tvTerdaftar.setVisibility(View.VISIBLE);
//                tvBlmTerdaftar.setVisibility(View.INVISIBLE);
//            } else {
//                tvTerdaftar.setVisibility(View.INVISIBLE);
//                tvBlmTerdaftar.setVisibility(View.VISIBLE);
//            }
//        }else {
//            terdaftarReal = "false";
//            if (terdaftarReal.equals("true")) {
//                tvTerdaftar.setVisibility(View.VISIBLE);
//                tvBlmTerdaftar.setVisibility(View.INVISIBLE);
//            } else {
//                tvTerdaftar.setVisibility(View.INVISIBLE);
//                tvBlmTerdaftar.setVisibility(View.VISIBLE);
//            }
//        }

        // set tanggal sekarang //ubah ke adapter list aja
        tvCurrentDate.setText(getCurrentLocalDateStamp());

        pickQueue.setOnClickListener(this);
        cvDoctorList.setOnClickListener(this);
        civProfileUser.setOnClickListener(view -> {
            Intent toProfile = new Intent(HomeActivity.this, ProfileActivity.class);
            toProfile.putExtra("usertype", userType);
            toProfile.putExtra("image", imageURL);
            toProfile.putExtra("name", name);
            toProfile.putExtra("email", email);
            startActivity(toProfile);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getDataUser();
    }

    private void getPrefTerdaftar() {
        preferences = getSharedPreferences("PREF_REGIST", MODE_PRIVATE);
        isTerdaftar = preferences.getString("terdaftar", "");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cv_doctorlist_home:
                startActivity(new Intent(this, DoctorListActivity.class));
                break;
            case R.id.cv_pickqueue_home:
                Intent toDaftar = new Intent(HomeActivity.this, RegisPatientActivity.class);
                toDaftar.putExtra("namapasien", name);
                toDaftar.putExtra("imagepasien", imageURL);
                startActivity(toDaftar);
                break;
            default:
                break;
        }
    }

    public String getCurrentLocalDateStamp() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDate = new SimpleDateFormat("dd MMM, yyyy");
        return currentDate.format(calendar.getTime());
    }

    private void getDataUser() {
        reference.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel userModel = snapshot.getValue(UserModel.class);
                if (snapshot.exists()) {
                    assert userModel != null;
                    if (userModel.getImageURL().substring(0, 4).equals("http")) {
                        Picasso.get().load(userModel.getImageURL()).into(civProfileUser);
                    } else { // default atau file:\\
                        Picasso.get().load(R.drawable.icon_default_profile).into(civProfileUser);
                    }
                    userType = userModel.getType();
                    imageURL = userModel.getImageURL();
                    name = userModel.getUsername();
                    email = userModel.getEmail();
                    terdaftarReal = userModel.getTerdaftar(); // salah
                } else {
                    Toast.makeText(HomeActivity.this, "Tidak ada user yang ditemukan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.help:
                startActivity(new Intent(this, HelpActivity.class));
                return true;
            case R.id.logout:
                showAlertDialogLogout();
                return true;
        }
        return false;
    }

    private void showAlertDialogLogout() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("KELUAR");
        alertDialogBuilder
                .setMessage("Apakah Anda yakin ingin keluar dari Aplikasi ?")
                .setCancelable(false)
                .setPositiveButton("Ya, tentu", (dialog, id) -> {

                    FirebaseAuth.getInstance().signOut();

                    startActivity(new Intent(HomeActivity.this, LoginActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
                })
                .setNegativeButton("Gak jadi", (dialog, id) -> dialog.cancel());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}