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

import java.util.ArrayList;
import java.util.Date;

public class groupChatActivity extends AppCompatActivity {
    ImageView back_arrow, profile_image, phone_call, video_call, menu_bar, send_message;
    TextView user_name;
    EditText chat_message_section;
    RecyclerView chatting_RecyclerView;
    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);
        getSupportActionBar().hide();

        back_arrow = findViewById(R.id.Gback_Arrow);
        profile_image = findViewById(R.id.Gprofile_image);
        phone_call = findViewById(R.id.phone_call);
        video_call = findViewById(R.id.Gvideo_call);
        menu_bar = findViewById(R.id.menu);
        send_message = findViewById(R.id.Gsend_message);
        user_name = findViewById(R.id.Guser_name);
        chat_message_section = findViewById(R.id.Gmessage_typing);
        chatting_RecyclerView = findViewById(R.id.Gchatting_RecyclerView);

        firebaseDatabase = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(groupChatActivity.this);
        linearLayoutManager.setStackFromEnd(true);
        chatting_RecyclerView.setLayoutManager(linearLayoutManager);

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(groupChatActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        final ArrayList<messageModel> messageModelArrayList = new ArrayList<>();
        final chatAdapter chatadapter = new chatAdapter(messageModelArrayList, this);
        chatting_RecyclerView.setAdapter(chatadapter);
        final String senderId = auth.getUid();
        user_name.setText("Friends Group");

        send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String message=chat_message_section.getText().toString();
                final messageModel model=new messageModel(message,senderId);
                model.setTimeStamp(new Date().getTime());
                chat_message_section.setText("");

                firebaseDatabase.getReference().child("Group chat").push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                });

            }
        });

        firebaseDatabase.getReference().child("Group chat")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messageModelArrayList.clear();
                        for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                            messageModel model=dataSnapshot.getValue(messageModel.class);
                            messageModelArrayList.add(model);
                        }
                        chatadapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}