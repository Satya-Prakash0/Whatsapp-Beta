package com.example.whatsappbeta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.whatsappbeta.Adapter.chatAdapter;
import com.example.whatsappbeta.Model.messageModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class chatDetailActivity extends AppCompatActivity {
   ImageView back_arrow,profile_image,phone_call,video_call,menu_bar,send_message;
   TextView user_name;
   EditText chat_message_section;
   RecyclerView chatting_RecyclerView;
   FirebaseAuth auth;
   FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);
         getSupportActionBar().hide();

        back_arrow=findViewById(R.id.back_Arrow);
        profile_image=findViewById(R.id.profile_image);
        phone_call=findViewById(R.id.phone_call);
        video_call=findViewById(R.id.video_call);
        menu_bar=findViewById(R.id.menu);
        send_message=findViewById(R.id.send_message);
        user_name=findViewById(R.id.user_name);
        chat_message_section=findViewById(R.id.message_typing);
        chatting_RecyclerView=findViewById(R.id.chatting_RecyclerView);

        firebaseDatabase=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(chatDetailActivity.this);
        linearLayoutManager.setStackFromEnd(true);
        chatting_RecyclerView.setLayoutManager(linearLayoutManager);

        final String senderId=auth.getUid();
        String receiverId=getIntent().getStringExtra("userID");
        String userName=getIntent().getStringExtra("userName");
        String profilePic=getIntent().getStringExtra("profilePic");

          user_name.setText(userName);
         Picasso.get().load(profilePic).placeholder(R.drawable.person).into(profile_image);

         back_arrow.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 //finish();
                 Intent intent=new Intent(chatDetailActivity.this,MainActivity.class);
                 startActivity(intent);
             }
         });

         final ArrayList<messageModel> messageModelArrayList=new ArrayList<>();
        // ArrayList<messageModel>localmessagemodel=new ArrayList<>();
         final chatAdapter chatadapter =new chatAdapter(messageModelArrayList,this);
         chatting_RecyclerView.setAdapter(chatadapter);

         final String senderRoom=senderId+receiverId;
         final String receiverRoom=receiverId+senderId;

         firebaseDatabase.getReference().child("chat").child(senderRoom)
                 .addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot snapshot) {
                         messageModelArrayList.clear();
                         for(DataSnapshot snapshot1:snapshot.getChildren()){
                             messageModel model2=snapshot1.getValue(messageModel.class);
                             messageModelArrayList.add(model2);
                         }
                        chatadapter.notifyDataSetChanged();
                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError error) {

                     }
                 });

         send_message.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String message = chat_message_section.getText().toString();
                 if (!message.isEmpty()) {
                     final messageModel model = new messageModel(message, senderId);
                     model.setTimeStamp(new Date().getTime());
                     chat_message_section.setText("");

                     firebaseDatabase.getReference().child("chat").child(senderRoom).push()
                             .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                         @Override
                         public void onSuccess(Void unused) {
                             firebaseDatabase.getReference().child("chat").child(receiverRoom).push()
                                     .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                 @Override
                                 public void onSuccess(Void unused) {

                                 }
                             });
                         }
                     });
                 }
             }
         });

    }
}