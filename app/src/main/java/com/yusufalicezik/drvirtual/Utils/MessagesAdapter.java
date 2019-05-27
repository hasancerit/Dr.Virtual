package com.yusufalicezik.drvirtual.Utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.yusufalicezik.drvirtual.Model.Mesajlar;
import com.yusufalicezik.drvirtual.R;

import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageViewHolder> {

    private List<Mesajlar> userMessagesList;

    private FirebaseAuth mAuth;



    public MessagesAdapter(List<Mesajlar> userMessagesList)
    {
        this.userMessagesList=userMessagesList;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {


        public TextView senderMessageText, receiverMessageText;



        public MessageViewHolder(View itemView) {

            super(itemView);
            senderMessageText=itemView.findViewById(R.id.tvMesajIGonderencerik);
            receiverMessageText=itemView.findViewById(R.id.tvMesajIAlancerik);
        }
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_message_list,parent,false);

        mAuth=FirebaseAuth.getInstance();

        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {


        String messageSenderID=mAuth.getCurrentUser().getUid();
        Mesajlar messages=userMessagesList.get(position);

        String fromUser=messages.getFrom();


        holder.receiverMessageText.setVisibility(View.INVISIBLE);
        holder.senderMessageText.setVisibility(View.INVISIBLE);

        if(!fromUser.equals(messageSenderID)) {

            holder.receiverMessageText.setVisibility(View.INVISIBLE);
            holder.senderMessageText.setVisibility(View.VISIBLE);


            holder.senderMessageText.setText(messages.getIcerik());

        }
        else
        {
            holder.senderMessageText.setVisibility(View.INVISIBLE);

            holder.receiverMessageText.setVisibility(View.VISIBLE);
            holder.receiverMessageText.setText(messages.getIcerik());

        }




    }

    @Override
    public int getItemCount() {
        return userMessagesList.size();
    }
}