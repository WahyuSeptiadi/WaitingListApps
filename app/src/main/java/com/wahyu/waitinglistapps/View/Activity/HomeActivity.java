package com.wahyu.waitinglistapps.View.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {

    private CircleImageView civProfileUser;

    private DatabaseReference reference;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.topbar_home);
        setSupportActionBar(toolbar);
        setTitle("");

        civProfileUser = findViewById(R.id.civ_imageProfileHome);

        //inisialisasi
        reference = FirebaseDatabase.getInstance().getReference("Users");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        getDataUser();

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
                })
                .setNegativeButton("Gak jadi", (dialog, id) -> dialog.cancel());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}