package com.example.whatsappbeta.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.whatsappbeta.Adapter.UserAdapter;
import com.example.whatsappbeta.Model.UsersData;
import com.example.whatsappbeta.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class chatsfragments extends Fragment {



    public chatsfragments() {
        // Required empty public constructor
    }
ArrayList<UsersData>list=new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    RecyclerView recyclerView;
    FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_chatsfragments, container, false);
        recyclerView=rootView.findViewById(R.id.chatRecyclerView);
        UserAdapter userAdapter=new UserAdapter(list,getContext());
        recyclerView.setAdapter(userAdapter);
        firebaseDatabase=FirebaseDatabase.getInstance();

        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        ProgressDialog progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        auth=FirebaseAuth.getInstance();
        String senderId=auth.getUid();
        firebaseDatabase.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                progressDialog.dismiss();
               for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                   UsersData usersData=dataSnapshot.getValue(UsersData.class);
                   if(senderId== dataSnapshot.getKey()) continue;
                   usersData.setUserId(dataSnapshot.getKey());
                   list.add(usersData);
               }
               userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return rootView;
    }
}