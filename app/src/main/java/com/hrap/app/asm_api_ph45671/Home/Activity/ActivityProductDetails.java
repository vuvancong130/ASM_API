package com.hrap.app.asm_api_ph45671.Home.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.hrap.app.asm_api_ph45671.APIService;
import com.hrap.app.asm_api_ph45671.Cart.Model.BillModel;
import com.hrap.app.asm_api_ph45671.R;
import com.hrap.app.asm_api_ph45671.RetrofitClient;
import com.hrap.app.asm_api_ph45671.databinding.ActivityProductDetailsBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ActivityProductDetails extends AppCompatActivity {

    ActivityProductDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        binding = ActivityProductDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        ImageView btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Intent intent = getIntent();
        if (intent != null) {
            String productName = intent.getStringExtra("productName");
            double productPrice = intent.getDoubleExtra("productPrice", 0);
            String productImage = intent.getStringExtra("productImage");
            String productCate = intent.getStringExtra("productCate");
            String productDes = intent.getStringExtra("productDes");
            double productWeight = intent.getDoubleExtra("productWeight", 0);

            binding.txtName.setText("Name: " + productName);
            binding.txtPrice.setText(String.valueOf(productPrice) + " VND");
            binding.txtDes.setText(productDes);
            binding.edtKg.setText(String.valueOf(productWeight));
            binding.txtCate.setText(productCate);
            Glide.with(this)
                    .load(productImage)
                    .into(binding.imgProduct);

            binding.btnAdd.setOnClickListener(v -> {

                // Lấy giá trị từ edtKg
                String weightStr = binding.edtKg.getText().toString();
                double weight = weightStr.isEmpty() ? 0 : Double.parseDouble(weightStr);

                if (user != null) {
                    BillModel billModel = new BillModel();
                    billModel.setUserId(userId);
                    billModel.setProductName(productName);
                    billModel.setProductPrice(productPrice);
                    billModel.setProductImage(productImage);
                    billModel.setProductCate(productCate);
                    billModel.setProductDes(productDes);
                    billModel.setProductWeight(weight);

                    APIService apiService = RetrofitClient.getInstance().create(APIService.class);
                    Call<BillModel> call = apiService.createBill(billModel);
                    call.enqueue(new Callback<BillModel>() {
                        @Override
                        public void onResponse(Call<BillModel> call, Response<BillModel> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(ActivityProductDetails.this, "Bill added successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ActivityProductDetails.this, "Failed to add bill", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<BillModel> call, Throwable t) {
                            Toast.makeText(ActivityProductDetails.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }  else {
                    Toast.makeText(ActivityProductDetails.this, "User not logged in", Toast.LENGTH_SHORT).show();
                }

            });

        }
    }
}