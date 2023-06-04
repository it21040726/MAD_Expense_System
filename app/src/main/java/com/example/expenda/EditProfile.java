package com.example.expenda;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.expenda.databinding.ActivityEditProfileBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class EditProfile extends AppCompatActivity {

    private ActivityEditProfileBinding binding;
    private FirebaseAuth firebaseAuth;
    private static final String TAG = "PROFILE_EDIT_TAG";
    private String name ="", email="", age="", contact="";
    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        binding.toolBarPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditProfile.this, Profile.class));
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        loadUserInfo();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        binding.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });

    }

    private void validateData(){
        name = binding.updateName.getText().toString().trim();
        age = binding.updateAge.getText().toString().trim();
        contact = binding.updateContact.getText().toString().trim();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Enter Name...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(age)){
            Toast.makeText(this, "Enter Age...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(contact)){
            Toast.makeText(this, "Enter contact...", Toast.LENGTH_SHORT).show();
        }

        else{
            updateProfile();
        }
    }

    private void deleteProfile(){
        Log.d(TAG, "deleteProfile: Deleting user profile...");
    }

    private void updateProfile(){
        Log.d(TAG, "updateProfile: Updating user profile...");
        progressDialog.setMessage("Updating user profile");
        progressDialog.show();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("name", ""+name);
        hashMap.put("contact", ""+contact);
        hashMap.put("age", ""+age);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child(firebaseAuth.getUid())
                .updateChildren(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "onSuccess: Profile Updated...");
                        progressDialog.dismiss();
                        Toast.makeText(EditProfile.this, "Profile Updated :)", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: Failed to update database due to "+e.getMessage());
                        progressDialog.dismiss();
                        Toast.makeText(EditProfile.this, "Failed to update database due to"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadUserInfo() {
        Log.d(TAG, "loadUserInfo: Loading user information..."+firebaseAuth.getUid());

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(firebaseAuth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name = ""+snapshot.child("name").getValue();
                        String email = ""+snapshot.child("email").getValue();
                        String contact = ""+snapshot.child("contact").getValue();
                        String age = ""+snapshot.child("age").getValue();
                        String uid = ""+snapshot.child("uid").getValue();
                        String timestamp = ""+snapshot.child("timestamp").getValue();

                        binding.updateAge.setText(age);
                        binding.updateName.setText(name);
                        binding.updateContact.setText(contact);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

    }
}