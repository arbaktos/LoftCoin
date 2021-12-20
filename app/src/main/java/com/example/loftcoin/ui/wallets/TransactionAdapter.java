package com.example.loftcoin.ui.wallets;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loftcoin.R;
import com.example.loftcoin.data.Transaction;
import com.example.loftcoin.databinding.TransactionItemBinding;
import com.example.loftcoin.utils.PriceFormatter;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

public class TransactionAdapter extends ListAdapter<Transaction, TransactionAdapter.TransactionViewHolder> {

    private LayoutInflater inflater;
    private PriceFormatter priceFormatter;
    private int colorNegative;
    private int colorPositive;

    @Inject
    protected TransactionAdapter(PriceFormatter priceFormatter) {
        super(new DiffUtil.ItemCallback<Transaction>() {
            @Override
            public boolean areItemsTheSame(@NonNull Transaction oldItem, @NonNull Transaction newItem) {
                return Objects.equals(oldItem, newItem);
            }

            @Override
            public boolean areContentsTheSame(@NonNull Transaction oldItem, @NonNull Transaction newItem) {
                return Objects.equals(oldItem, newItem);
            }
        });
        this.priceFormatter = priceFormatter;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransactionViewHolder(TransactionItemBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = getItem(position);
        holder.binding.amount1.setText(priceFormatter.format(transaction.amount()));
        holder.binding.amount2.setText(priceFormatter.format(transaction.amount() * transaction.coin().price()));
        holder.binding.amount2.setTextColor(transaction.amount() > 0 ? colorPositive : colorNegative);
        holder.binding.timestamp.setText(DateFormat.getDateInstance(2, Locale.UK).format(transaction.time()));

    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        Context context = recyclerView.getContext();
        inflater = LayoutInflater.from(context);
        TypedValue v = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.textNegative, v, true);
        colorNegative = v.data;
        context.getTheme().resolveAttribute(R.attr.textPositive, v, true);
        colorPositive = v.data;
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {

        TransactionItemBinding binding;

        public TransactionViewHolder(TransactionItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
