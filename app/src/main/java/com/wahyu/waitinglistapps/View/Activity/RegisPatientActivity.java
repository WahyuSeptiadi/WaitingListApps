package com.wahyu.waitinglistapps.View.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.wahyu.waitinglistapps.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisPatientActivity extends AppCompatActivity {

    EditText et_patientname, et_patientsick, et_patientcomplain, et_patientage, et_patientaddress, et_patientgender, et_pickdoctor;
    CardView btn_regis, btn_searchdoctor;
    CircleImageView civ_profilepatient;
    String imagePasien, namaPasien, nama_dokter, id_dokter, foto_dokter, spesialis_dokter;

    private DatabaseReference reference;
    private FirebaseUser firebaseUser;
    private Calendar calendar;

    private String profile, name, penyakit, keluhan, alamat, umur, kelamin;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis_patient);

        //inisialisasi
        reference = FirebaseDatabase.getInstance().getReference("WaitingList");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        calendar = Calendar.getInstance();

        et_patientname = findViewById(R.id.et_patientname);
        et_patientsick = findViewById(R.id.et_patientsick);
        et_patientcomplain = findViewById(R.id.et_patientcomplains);
        et_patientage = findViewById(R.id.et_patientage);
        et_patientaddress = findViewById(R.id.et_patientaddress);
        et_patientgender = findViewById(R.id.et_patientgender);
        et_pickdoctor = findViewById(R.id.et_pickdoctor);
        civ_profilepatient = findViewById(R.id.civ_imageProfilePatient);
        btn_regis = findViewById(R.id.cv_btnpatientregis);
        btn_searchdoctor = findViewById(R.id.cv_btnpickdoctor);

        Intent data = getIntent();
        id_dokter = data.getStringExtra("id_doctor");
        nama_dokter = data.getStringExtra("name_doctor");
        foto_dokter = data.getStringExtra("image_doctor");
        spesialis_dokter = data.getStringExtra("spesialis");
        imagePasien = data.getStringExtra("imagepasien");
        namaPasien = data.getStringExtra("namapasien");

        if (namaPasien != null && imagePasien != null) {
            if (imagePasien.substring(0, 4).equals("http")) {
                Picasso.get().load(imagePasien).into(civ_profilepatient);
            } else {
                Picasso.get().load(R.drawable.icon_default_profile).into(civ_profilepatient);
            }
            et_patientname.setText(namaPasien);
        } else {
            getPreference();
        }

        if (id_dokter != null && nama_dokter != null) {
            et_pickdoctor.setText(nama_dokter);
        }

        btn_searchdoctor.setOnClickListener(view -> {
            setPreference();
            Intent toListDoctor = new Intent(RegisPatientActivity.this, DoctorListActivity.class);
            toListDoctor.putExtra("daftar", "daftar");
            startActivity(toListDoctor);
        });

        btn_regis.setOnClickListener(view -> {
            String penyakit = et_patientsick.getText().toString();
            String keluhan = et_patientcomplain.getText().toString();
            String umur = et_patientage.getText().toString();
            String jenis = et_patientgender.getText().toString();
            String alamat = et_patientaddress.getText().toString();

            if (!TextUtils.isEmpty(penyakit) && !TextUtils.isEmpty(keluhan) && !TextUtils.isEmpty(umur) &&
                    !TextUtils.isEmpty(jenis) && !TextUtils.isEmpty(alamat)) {
                if (!TextUtils.isEmpty(et_pickdoctor.getText())) {
                    daftarPatient(penyakit, keluhan, umur, jenis, alamat, nama_dokter);
                } else {
                    Toast.makeText(this, "Silahkan pilih dokter terlebih dahulu", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Tolong lengkapi semua fieldnya ya", Toast.LENGTH_SHORT).show();
            }
        });


        if (profile != null) {
            initPrefRegistPatient();
        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    private void setPreference() {
        SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
        editor.putString("profile", imagePasien);
        editor.putString("namepasien", namaPasien);
        editor.putString("penyakit", et_patientsick.getText().toString());
        editor.putString("keluhan", et_patientcomplain.getText().toString());
        editor.putString("alamat", et_patientaddress.getText().toString());
        editor.putString("umur", et_patientage.getText().toString());
        editor.putString("kelamin", et_patientgender.getText().toString());
        editor.apply();
    }

    private void getPreference() {
        preferences = getSharedPreferences("PREFS", MODE_PRIVATE);
        profile = preferences.getString("profile", "");
        name = preferences.getString("namepasien", "");
        penyakit = preferences.getString("penyakit", "");
        keluhan = preferences.getString("keluhan", "");
        alamat = preferences.getString("alamat", "");
        umur = preferences.getString("umur", "");
        kelamin = preferences.getString("kelamin", "");
    }

    private void initPrefRegistPatient() {
        if (!TextUtils.isEmpty(profile)) {
            Picasso.get().load(profile).into(civ_profilepatient);
            et_patientname.setText(name);
        } else {
            Picasso.get().load(R.drawable.icon_default_profile).into(civ_profilepatient);
            et_patientname.setText(R.string.str_null);
        }

        et_patientsick.setText(penyakit);
        et_patientcomplain.setText(keluhan);
        et_patientaddress.setText(alamat);
        et_patientage.setText(umur);
        et_patientgender.setText(kelamin);
    }

    private void removePreference() {
        preferences.edit().remove("profile").apply();
        preferences.edit().remove("namepasien").apply();
        preferences.edit().remove("penyakit").apply();
        preferences.edit().remove("keluhan").apply();
        preferences.edit().remove("alamat").apply();
        preferences.edit().remove("umur").apply();
        preferences.edit().remove("kelamin").apply();
    }

    public String getCurrentLocalTimeStamp(int plus) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        calendar.add(Calendar.MINUTE, plus);
        return currentTime.format(calendar.getTime());
    }

    public String getCurrentLocalDateStamp() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDate = new SimpleDateFormat("dd MMM, yyyy");
        return currentDate.format(calendar.getTime());
    }

    private void daftarPatient(String penyakit, String keluhan, String umur, String jenisKelamin, String alamat, String dokter) {
        String userId = firebaseUser.getUid();

        HashMap<String, Object> daftarPatient = new HashMap<>();
        daftarPatient.put("idPasien", userId);
        daftarPatient.put("idDokter", id_dokter);
        daftarPatient.put("namaDokter", dokter);
        daftarPatient.put("penyakitPasien", penyakit);
        daftarPatient.put("keluhanPasien", keluhan);
        daftarPatient.put("umurPasien", umur);
        daftarPatient.put("jenisPasien", jenisKelamin);
        daftarPatient.put("alamatPasien", alamat);
        daftarPatient.put("waktuDaftar", getCurrentLocalTimeStamp(0));
        daftarPatient.put("waktuSelesai", getCurrentLocalTimeStamp(10));
        daftarPatient.put("tanggalDaftar", getCurrentLocalDateStamp());

        if (profile != null && name != null) {
            daftarPatient.put("imageURL", profile);
            daftarPatient.put("namaPasien", name);
        } else {
            daftarPatient.put("imageURL", "default");
            daftarPatient.put("namaPasien", "default");
        }
        reference.child(id_dokter).child(userId).setValue(daftarPatient);

        // buat list antrian di home activity
        if (foto_dokter != null && spesialis_dokter != null) {
            daftarPatient.put("imageDoctor", foto_dokter);
            daftarPatient.put("spesialis", spesialis_dokter);
            daftarPatient.put("status", "MENUNGGU");

            DatabaseReference dbRefMyQueue = FirebaseDatabase.getInstance().getReference("MyQueue");
            dbRefMyQueue.child(userId).child(id_dokter).setValue(daftarPatient);
            Toast.makeText(this, "Data berhasil mendaftar", Toast.LENGTH_SHORT).show();
            removePreference();

            Intent toListPatient = new Intent(RegisPatientActivity.this, PatientListActivity.class);
            toListPatient.putExtra("id_dokter", id_dokter);
            startActivity(toListPatient);
            finish();
        }
    }
}