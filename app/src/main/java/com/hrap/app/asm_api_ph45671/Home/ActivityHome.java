package com.hrap.app.asm_api_ph45671.Home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.hrap.app.asm_api_ph45671.Cart.FragmentCart;
import com.hrap.app.asm_api_ph45671.Favorite.FragmentFavorite;
import com.hrap.app.asm_api_ph45671.Home.Fragment.FragmentProduct;
import com.hrap.app.asm_api_ph45671.Notification.FragmentNotification;
import com.hrap.app.asm_api_ph45671.R;
import com.hrap.app.asm_api_ph45671.databinding.ActivityHomeBinding;

public class ActivityHome extends AppCompatActivity {

    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fr_container, new FragmentProduct())
                .commit();

        binding.bottomNavigation.setOnItemSelectedListener( item -> {
            Fragment selectedFragment =  null;
            if (item.getItemId() == R.id.navHome) {
                selectedFragment = new FragmentProduct();
            } else if (item.getItemId() == R.id.navCart) {
                selectedFragment = new FragmentCart();
            } else if (item.getItemId() == R.id.navFavorite) {
                selectedFragment = new FragmentFavorite();
            } else {
                selectedFragment = new FragmentNotification();
            }
            return loadFragment(selectedFragment);
        });

    }
    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fr_container, fragment);
            transaction.commit();
            return true;
        }
        return false;
    }
}