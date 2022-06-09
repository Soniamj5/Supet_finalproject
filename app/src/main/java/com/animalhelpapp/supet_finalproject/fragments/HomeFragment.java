package com.animalhelpapp.supet_finalproject.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.animalhelpapp.supet_finalproject.R;
import com.animalhelpapp.supet_finalproject.petInfo.CrearPetInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeFragment extends Fragment {

    ImageButton addInfo;
    /*firabase*/
    FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        /*--------------------Add info------------------------*/
        addInfo = view.findViewById(R.id.addInfo);
        /*Inicializar Firebase*/
        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        addInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*conectar con CreatePetInfo*/
                crearPetInfo();
            }
        });

        return view;
    }

    private void crearPetInfo() {
        Intent intent = new Intent(getActivity(), CrearPetInfo.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}