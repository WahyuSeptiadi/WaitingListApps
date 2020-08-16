package com.wahyu.waitinglistapps.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.wahyu.waitinglistapps.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class PatientDetailsActivity extends AppCompatActivity {

    private CircleImageView civProfilePatient;
    private TextView tvName, tvKeluhan, tvPenyakit, tvAlamat, tvUmur, tvJenis, tvDaftar, tvSelesai;

    private String getImage, getName, getKeluhan, getPenyakit, getAlamat, getUmur, getJenis, getDaftar, getSelesai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);

        civProfilePatient = findViewById(R.id.civ_profile_patientdetails);
        tvName = findViewById(R.id.tv_nama_patientdetails);
        tvKeluhan = findViewById(R.id.tv_keluhan_patientdetails);
        tvPenyakit = findViewById(R.id.tv_penyakit_patientdetails);
        tvAlamat = findViewById(R.id.tv_alamat_patientdetails);
        tvUmur = findViewById(R.id.tv_umur_patientdetails);
        tvJenis = findViewById(R.id.tv_jenis_patientdetails);
        tvDaftar = findViewById(R.id.tv_waktu_daftar_patientdetails);
        tvSelesai = findViewById(R.id.tv_estimasi_patientdetails);

        getDataIntent();

        //set init detail pasien
        if (getImage != null && getName != null && getKeluhan != null && getPenyakit != null && getAlamat != null &&
                getUmur != null && getJenis != null && getDaftar != null && getSelesai != null) {

            if (getImage.substring(0, 4).equals("http")) {
                Picasso.get().load(getImage).into(civProfilePatient);
            } else {
                Picasso.get().load(R.drawable.icon_default_profile).into(civProfilePatient);
            }

            tvName.setText(getName);
            tvKeluhan.setText(getKeluhan);
            tvPenyakit.setText(getPenyakit);
            tvAlamat.setText(getAlamat);
            tvUmur.setText(getUmur);
            tvJenis.setText(getJenis);
            tvDaftar.setText(getDaftar);
            tvSelesai.setText(getSelesai);

        } else {
            Toast.makeText(this, "Silahkan reload kembali untuk set data", Toast.LENGTH_SHORT).show();
        }
    }

    private void getDataIntent() {
        Intent data = getIntent();
        getImage = data.getStringExtra("image");
        getName = data.getStringExtra("name");
        getKeluhan = data.getStringExtra("keluhan");
        getPenyakit = data.getStringExtra("penyakit");
        getAlamat = data.getStringExtra("alamat");
        getUmur = data.getStringExtra("umur");
        getJenis = data.getStringExtra("jenis");
        getDaftar = data.getStringExtra("daftar");
        getSelesai = data.getStringExtra("selesai");
    }

}