package com.example.newapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private List<Transaction> transactions;
    private final DecimalFormat df = new DecimalFormat("#,###.00");
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault());

    public void submitList(List<Transaction> list) {
        this.transactions = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Transaction tx = transactions.get(position);

        boolean isReceive = "RECEIVE".equalsIgnoreCase(tx.type);
        
        holder.txTitle.setText(isReceive ? "Received from " + tx.counterParty : "Sent to " + tx.counterParty);
        holder.txDate.setText(dateFormat.format(new Date(tx.time)));
        
        String amountText = (isReceive ? "+₦" : "-₦") + df.format(tx.amount);
        holder.txAmount.setText(amountText);
        holder.txAmount.setTextColor(isReceive ? 
                ContextCompat.getColor(holder.itemView.getContext(), android.R.color.holo_green_light) : 
                ContextCompat.getColor(holder.itemView.getContext(), android.R.color.holo_red_light));

        holder.txIcon.setImageResource(isReceive ? R.drawable.arrow_circle_down : R.drawable.send_money);
        holder.txIcon.setRotation(isReceive ? 0 : 0);
    }

    @Override
    public int getItemCount() {
        return transactions != null ? transactions.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView txIcon;
        TextView txTitle, txDate, txAmount;

        ViewHolder(View itemView) {
            super(itemView);
            txIcon = itemView.findViewById(R.id.txIcon);
            txTitle = itemView.findViewById(R.id.txTitle);
            txDate = itemView.findViewById(R.id.txDate);
            txAmount = itemView.findViewById(R.id.txAmount);
        }
    }
}