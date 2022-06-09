package com.animalhelpapp.supet_finalproject.fragments;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.animalhelpapp.supet_finalproject.R;
import com.animalhelpapp.supet_finalproject.account.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {

    ImageButton sign_out;
    /*firebase*/
    FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        /*--------------------Sign Out------------------------*/
        /*Button sign_out*/
        sign_out = view.findViewById(R.id.sign_out);
        conectarFirebase();
        sign_out.setOnClickListener(v -> {
            Log.d(TAG, "onClick: intentando cerrar sesión.");
            /*cerramos sesión*/
            FirebaseAuth.getInstance().signOut();
        });
        return view;
    }

    /*--------------------firebase------------------------*/

    private void conectarFirebase() {
        Log.d(TAG, "conectarFirebase: conectando con auth state listener");
        mAuthStateListener = firebaseAuth -> {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            if(firebaseUser != null) {
                Log.d(TAG, "onAuthStateChanged: signed_in: " + firebaseUser.getUid());
            } else {
                Log.d(TAG, "onAuthStateChanged: signed_out");
                Toast.makeText(getActivity(), "Sesión cerrada", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mAuthStateListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthStateListener);
        }
    }
}