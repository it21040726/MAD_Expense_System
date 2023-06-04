package com.example.expenda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;


import com.example.expenda.databinding.ActivityExpenseCatergoryBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ExpenseCatergoryActivity extends AppCompatActivity {

    private ActivityExpenseCatergoryBinding binding;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExpenseCatergoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolBarPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ExpenseCatergoryActivity.this, Profile.class));
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        binding.expenseCbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });

    }

    private String expenseC = "";

    private void validateData() {
        expenseC = binding.categoryEt.getText().toString().trim();
        if(TextUtils.isEmpty(expenseC)){
            Toast.makeText(this,"Enter Your name..",Toast.LENGTH_SHORT).show();
        }

        else{
            createCategory();
        }

    }
// Expenses Create CRUD
    private void createCategory() {

        progressDialog.setTitle("Saving User Info..");
        progressDialog.show();

        long timestamps = System.currentTimeMillis();

        String  uid = firebaseAuth.getUid();

        HashMap<String , Object> hashMap = new HashMap<>();
        hashMap.put("id",""+timestamps);
        hashMap.put("uid",uid);
        hashMap.put("category",expenseC);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ExpensesC");
        ref.child(firebaseAuth.getUid()).child(""+timestamps)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(ExpenseCatergoryActivity.this,"Account Created",Toast.LENGTH_SHORT).show();
                        onBackPressed();
                        finish();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(ExpenseCatergoryActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });
    }

}