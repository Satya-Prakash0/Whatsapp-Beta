package com.example.whatsappbeta.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappbeta.Model.UsersData;
import com.example.whatsappbeta.R;
import com.example.whatsappbeta.chatDetailActivity;
import com.example.whatsappbeta.signIn;
import com.example.whatsappbeta.testtrailActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{

ArrayList<UsersData> list;
Context context;

    public UserAdapter(ArrayList<UsersData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.single_user,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      UsersData usersData= list.get(position);
        Picasso.get().load(usersData.getProfilepic()).placeholder(R.drawable.person).into(holder.profile_image);
        holder.userName.setText(usersData.getUsername());

        FirebaseDatabase.getInstance().getReference().child("chat")
                .child(FirebaseAuth.getInstance().getUid() + usersData.getUserId())
                .orderByChild("timeStamp")
                .limitToLast(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChildren()){
                            for(DataSnapshot dataSnapshot1:snapshot.getChildren()){
                                holder.lastMessage.setText(dataSnapshot1.child("message").getValue().toString());
                            }
                        }
                        // notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

       // holder.lastMessage.setText(usersData.getLastmessage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  Intent intent=new Intent(context, chatDetailActivity.class);
                intent.putExtra("userID",usersData.getUserId());
                intent.putExtra("profilePic",usersData.getProfilepic());
                intent.putExtra("userName",usersData.getUsername());
                  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView userName,lastMessage;
        ImageView profile_image;
        public ViewHolder(View itemView){
            super(itemView);
            userName=itemView.findViewById(R.id.userName);
            lastMessage=itemView.findViewById(R.id.lastMessage);
            profile_image=itemView.findViewById(R.id.profile_image);

        }
    }

}
