package com.example.expenda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.expenda.databinding.ActivityExpenseCmainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ExpenseCMainActivity extends AppCompatActivity {

    private ActivityExpenseCmainBinding binding;
    private FirebaseAuth firebaseAuth;

    private ArrayList<ModelCategory> categoryArrayList;

    private AdapaterCategoryExpense adapaterCategoryExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExpenseCmainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        loadCategories();


        binding.ctr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ExpenseCMainActivity.this,ExpenseCatergoryActivity.class));

            }
        });
    }

    //Expenses Categories Read Crud
    private void loadCategories() {

        categoryArrayList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ExpensesC");
        ref.child(firebaseAuth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        categoryArrayList.clear();
                        for (DataSnapshot ds: snapshot.getChildren()){

                            ModelCategory model = ds.getValue(ModelCategory.class);
                            categoryArrayList.add(model);

                        }
                        adapaterCategoryExpense = new AdapaterCategoryExpense(ExpenseCMainActivity.this,categoryArrayList);
                        binding.ctagory.setAdapter(adapaterCategoryExpense);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}