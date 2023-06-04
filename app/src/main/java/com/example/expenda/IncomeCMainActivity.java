package com.example.expenda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.expenda.databinding.ActivityIncomeCmainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class IncomeCMainActivity extends AppCompatActivity {

    private ActivityIncomeCmainBinding binding;

    private FirebaseAuth firebaseAuth;

    private ArrayList<ModelCategory> categoryArrayList;
    private AdapaterCategory adapaterCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIncomeCmainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();
        loadCategories();

        binding.ctr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IncomeCMainActivity.this,IncomCatergoryActivity.class));

            }
        });
    }

    private void loadCategories() {

        categoryArrayList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("incomeC");
        ref.child(firebaseAuth.getUid())
        .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoryArrayList.clear();
                for (DataSnapshot ds: snapshot.getChildren()){

                    ModelCategory model = ds.getValue(ModelCategory.class);
                    categoryArrayList.add(model);

                }
                adapaterCategory = new AdapaterCategory(IncomeCMainActivity.this,categoryArrayList);
                binding.ctagory.setAdapter(adapaterCategory);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void checkUser() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser==null){
            startActivity(new Intent(this,activity_login.class));
            finish();
        }
        else{
            String email = firebaseUser.getEmail();

        }
    }
}