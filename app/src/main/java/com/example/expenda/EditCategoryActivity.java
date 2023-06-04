package com.example.expenda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.expenda.databinding.ActivityEditCategoryBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class EditCategoryActivity extends AppCompatActivity {

    private ActivityEditCategoryBinding binding;

    private String id,uid;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private ArrayList<String> categoryEdit,categoryIDArray;
    private static final String TAG = "Book_Edit_ID";
    private ArrayList<ModelCategory> categoryArrayList;
    private long timestamps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        id= getIntent().getStringExtra("id");
        uid= getIntent().getStringExtra("uid");



        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);

        loadCategories();


        binding.upbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });


    }




    private void loadCategories() {

        ModelCategory model = new ModelCategory();

        Log.d(TAG,"loadCategories: Loading categories");


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("incomeC");
        ref.child(firebaseAuth.getUid()).child(id)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String cName = ""+snapshot.child("category").getValue();
                        binding.upTxt.setText(cName);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }

    private String category="";





    private void validateData() {

        category=binding.upTxt.getText().toString().trim();

        if(TextUtils.isEmpty(category)){
            Toast.makeText(this,"Enter Category",Toast.LENGTH_SHORT).show();
        }else{
            updataData();
        }



    }

    private void updataData() {
        progressDialog.setTitle("Saving ");
        progressDialog.show();
        Log.d(TAG,"updataData: Starting DB Loading categories");

        HashMap<String , Object> hashMap = new HashMap<>();
        hashMap.put("category",category);
        hashMap.put("id",id);
        hashMap.put("uid",uid);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("incomeC");
        ref.child(firebaseAuth.getUid()).child(id)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        onBackPressed();



                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();

                    }
                });


    }

}