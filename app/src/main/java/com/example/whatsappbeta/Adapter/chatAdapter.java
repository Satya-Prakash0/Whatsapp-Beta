package com.example.whatsappbeta.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.whatsappbeta.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappbeta.Model.messageModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class chatAdapter extends RecyclerView.Adapter {

ArrayList<messageModel>list;
Context context;

    public chatAdapter(ArrayList<messageModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    int SENDER_VIEW_TYPE=1;
    int RECEIVER_VIEW_TYPE=2;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==SENDER_VIEW_TYPE) {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_sender_background, parent, false);
            return new senderViewHolder(view);
        }
        else{
            View view = LayoutInflater.from(context).inflate(R.layout.chat_receiver_background, parent, false);
            return new receiverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        messageModel messagemodel= list.get(position);
       if(holder.getClass()==senderViewHolder.class){
           ((senderViewHolder)holder).senderText.setText(messagemodel.getMessage());
       }
       else{
           ((receiverViewHolder)holder).receiverText.setText(messagemodel.getMessage());
       }
    }

    @Override
    public int getItemViewType(int position) {
        if(list.get(position).getUid().equals(FirebaseAuth.getInstance().getUid())){
            return SENDER_VIEW_TYPE;
        }
        else{
            return RECEIVER_VIEW_TYPE;
        }
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class receiverViewHolder extends RecyclerView.ViewHolder {
        TextView receiverTime, receiverText;

        public receiverViewHolder(@NonNull View itemView) {
            super(itemView);
            receiverText = itemView.findViewById(R.id.receiverText);
            receiverTime = itemView.findViewById(R.id.receiverTime);
        }
    }
        public class senderViewHolder extends RecyclerView.ViewHolder {
            TextView senderTime, senderText;

            public senderViewHolder(@NonNull View itemView) {
                super(itemView);
                senderText = itemView.findViewById(R.id.sender_message);
                senderTime = itemView.findViewById(R.id.sender_time);
            }
        }

}
