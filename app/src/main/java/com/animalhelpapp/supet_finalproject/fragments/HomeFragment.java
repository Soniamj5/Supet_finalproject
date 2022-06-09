package com.animalhelpapp.supet_finalproject.fragments;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.animalhelpapp.supet_finalproject.Adapter;
import com.animalhelpapp.supet_finalproject.PetInfo;
import com.animalhelpapp.supet_finalproject.R;
import com.animalhelpapp.supet_finalproject.petInfo.CreatePetInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    ImageButton createInfo;
    /*firabase*/
    FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;

    /*coger los datos de petInfo de firestore*/
    RecyclerView recyclerView;
    ArrayList<PetInfo> petInfoArrayList;
    Adapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        /*--------------------Create info------------------------*/
        createInfo = view.findViewById(R.id.createInfo);
        /*Inicializar Firebase*/
        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        createInfo.setOnClickListener(v -> {
            /*conectar con CreatePetInfo*/
            crearPetInfo();
        });

        /*--------------------Coger info------------------------*/
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        /*inicializar lo que queda -- firestore ya está inicializado arriba*/
        petInfoArrayList = new ArrayList<>();
        adapter = new Adapter(getActivity(), petInfoArrayList);

        recyclerView.setAdapter(adapter);

        cogerDatos();


        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void cogerDatos() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = FirebaseAuth.getInstance().getUid();
        if(firebaseUser != null) {
            Log.d(TAG, "onAuthStateChanged: signed_in: " + firebaseUser.getUid());
            CollectionReference userCollection = firebaseFirestore.collection("user");
            assert uid != null;
            userCollection.document(uid).collection("petInfo").addSnapshotListener((value, error) -> {
                if(error != null) {
                    Log.e("Firestore error", error.getMessage());
                } else {
                    assert value != null;
                    for (DocumentChange dc : value.getDocumentChanges()) {
                        if (dc.getType() == DocumentChange.Type.ADDED) {
                            petInfoArrayList.add(dc.getDocument().toObject(PetInfo.class));
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }

    }

    private void crearPetInfo() {
        Intent intent = new Intent(getActivity(), CreatePetInfo.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}