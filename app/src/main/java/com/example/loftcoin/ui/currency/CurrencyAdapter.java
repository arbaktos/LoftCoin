package com.example.loftcoin.ui.currency;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;


import com.example.loftcoin.data.Currency;
import com.example.loftcoin.databinding.CurrencyItemBinding;

import java.util.Objects;

public class CurrencyAdapter extends ListAdapter<Currency, CurrencyAdapter.ViewHolder> {

    private LayoutInflater inflater;

    CurrencyAdapter() {
        super(new DiffUtil.ItemCallback<Currency>() {
            @Override
            public boolean areItemsTheSame(@NonNull Currency oldItem, @NonNull Currency newItem) {
                return Objects.equals(oldItem, newItem);
            }

            @Override
            public boolean areContentsTheSame(@NonNull Currency oldItem, @NonNull Currency newItem) {
                return Objects.equals(oldItem, newItem);
            }
        });
    }

    @Override
    public Currency getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(CurrencyItemBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Currency currency = getItem(position);
        holder.binding.name.setText(currency.name());
        holder.binding.symbol.setText(currency.symbol());
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        inflater = LayoutInflater.from(recyclerView.getContext());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final CurrencyItemBinding binding;

        ViewHolder(@NonNull CurrencyItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
