package com.example.loftcoin.ui.converter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loftcoin.R;
import com.example.loftcoin.data.Coin;
import com.example.loftcoin.databinding.CurrencyDialogBinding;
import com.example.loftcoin.databinding.CurrencyItemBinding;
import com.example.loftcoin.ui.rates.RatesAdapter;

public class CoinsSheetAdapter extends ListAdapter<Coin, CoinsSheetAdapter.CoinsViewHolder> {

    private LayoutInflater inflater;

    protected CoinsSheetAdapter(@NonNull DiffUtil.ItemCallback<Coin> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public CoinsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CoinsViewHolder(CurrencyItemBinding.inflate(inflater));
    }

    @Override
    public void onBindViewHolder(@NonNull CoinsViewHolder holder, int position) {

    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        inflater = LayoutInflater.from(recyclerView.getContext());
    }

    public static class CoinsViewHolder extends RecyclerView.ViewHolder {

        CurrencyItemBinding binding;

        public CoinsViewHolder(@NonNull CurrencyItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
