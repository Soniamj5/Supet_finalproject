package com.animalhelpapp.supet_finalproject;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.animalhelpapp.supet_finalproject.databinding.ActivityMainBinding;
import com.animalhelpapp.supet_finalproject.fragments.CalendarFragment;
import com.animalhelpapp.supet_finalproject.fragments.HomeFragment;
import com.animalhelpapp.supet_finalproject.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    /*Binding de fragmentos*/
    ActivityMainBinding binding;

/*    FirebaseAuth mAuth;*/

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*se cambia el contentView para que se vean los fragmentos*/
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        /*home fragment default*/
        reemplazarFragmento(new HomeFragment());

        /*bottom nav menu tab*/
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    reemplazarFragmento(new HomeFragment());
                    break;
                case R.id.calendar:
                    reemplazarFragmento(new CalendarFragment());
                    break;
                case R.id.profile:
                    reemplazarFragmento(new ProfileFragment());
                    break;
            }
            return true;
        });
    }

    /*método para poder ir a los distintos fragments*/
    private void reemplazarFragmento(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}