package com.example.expenda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.expenda.databinding.ActivityCatergoryBinding;

public class CatergoryActivity extends AppCompatActivity {

    private ActivityCatergoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCatergoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolBarPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CatergoryActivity.this, Profile.class));
            }
        });

        binding.incomeMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CatergoryActivity.this,IncomeCMainActivity.class));
            }
        });

        binding.expenseMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CatergoryActivity.this,ExpenseCMainActivity.class));

            }
        });
    }
}