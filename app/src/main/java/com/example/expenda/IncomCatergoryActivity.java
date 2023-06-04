package com.example.expenda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.expenda.databinding.ActivityIncomCatergoryBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class IncomCatergoryActivity extends AppCompatActivity {

    private ActivityIncomCatergoryBinding binding;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIncomCatergoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();

        binding.toolBarPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IncomCatergoryActivity.this, Profile.class));
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        binding.incomCbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();

            }
        });
    }

    private String incomeC = "";
    private  int a;


    private void validateData() {

        incomeC = binding.categoryEt.getText().toString().trim();


        if(TextUtils.isEmpty(incomeC)){
            Toast.makeText(this,"Enter Your name..",Toast.LENGTH_SHORT).show();
        }

        else{
            createCategory();
        }

    }

    private void createCategory() {

        progressDialog.setTitle("Saving User Info..");
        progressDialog.show();

        long timestamps = System.currentTimeMillis();

        String  uid = firebaseAuth.getUid();

        HashMap<String , Object> hashMap = new HashMap<>();
        hashMap.put("id",""+timestamps);
        hashMap.put("uid",uid);
        hashMap.put("category",incomeC);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("incomeC");
        ref.child(firebaseAuth.getUid()).child(""+timestamps)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(IncomCatergoryActivity.this,"Account Created",Toast.LENGTH_SHORT).show();
                        onBackPressed();
                        finish();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(IncomCatergoryActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });

    }

}