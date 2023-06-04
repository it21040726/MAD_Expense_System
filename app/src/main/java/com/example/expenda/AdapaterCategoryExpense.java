package com.example.expenda;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expenda.databinding.RowCategoryBinding;
import com.example.expenda.databinding.RowCategoryExpenseBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapaterCategoryExpense extends RecyclerView.Adapter<AdapaterCategoryExpense.HolderCategory> {

    private Context context;
    private ArrayList<ModelCategory> categoryArrayList;


    private RowCategoryExpenseBinding expenseBinding;

    private FirebaseAuth firebaseAuth;


    private ProgressDialog progressDialog;
    private static final String TAG = "Category";





    public AdapaterCategoryExpense(Context context, ArrayList<ModelCategory> categoryArrayList) {
        this.context = context;
        this.categoryArrayList = categoryArrayList;
        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @NonNull
    @Override
    public HolderCategory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        expenseBinding = RowCategoryExpenseBinding.inflate(LayoutInflater.from(context),parent,false);
        return new HolderCategory(expenseBinding.getRoot());


    }



    @Override
    public void onBindViewHolder(@NonNull HolderCategory holder, int position) {
        ModelCategory model = categoryArrayList.get(position);
        String id = model.getId();
        String category = model.getCategory();
        long timestamp = model.getTimestamp();



        holder.categoryTv.setText(category);

        holder.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(context);
                builder.setTitle("Delete")
                        .setMessage("Are you sure ?")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                Toast.makeText(context,"Deleting..",Toast.LENGTH_SHORT).show();

                                deleteExpensecat(model,holder);

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();

                            }
                        })
                        .show();


            }
        });

        holder.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,EditCatActivity.class);
                intent.putExtra("id",model.id);
                intent.putExtra("uid",model.Uid);
                context.startActivity(intent);
            }
        });



    }
//Delete CRUD expenses
    private void deleteExpensecat(ModelCategory model, HolderCategory holder) {
        String id = firebaseAuth.getUid();
        String id2 = model.getId();
        long timestamp = model.getTimestamp();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ExpensesC");
        reference.child(id).child(id2)
                .removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context,"SuccessFully Deleting..",Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,""+e.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });

    }




    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    class HolderCategory extends RecyclerView.ViewHolder{
        TextView categoryTv;
        ImageButton updateBtn,delBtn;



        public HolderCategory(@NonNull View itemView) {
            super(itemView);
            categoryTv = expenseBinding.categoryTv;
            updateBtn = expenseBinding.updateBtn;
            delBtn = expenseBinding.delBtn;


        }
    }
}
