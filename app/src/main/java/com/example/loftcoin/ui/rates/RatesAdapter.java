package com.example.loftcoin.ui.rates;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loftcoin.data.Coin;
import com.example.loftcoin.databinding.RatesItemBinding;

import java.util.List;

public class RatesAdapter extends RecyclerView.Adapter<RatesAdapter.CoinViewHolder> {

    private LayoutInflater inflater;

    private final List<? extends Coin> coins;

    public RatesAdapter(List<? extends Coin> coins) {
        setHasStableIds(true);
        this.coins = coins;
    }

    @Override
    public long getItemId(int position) {
        return coins.get(position).id();
    }

    @NonNull
    @Override
    public CoinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CoinViewHolder(RatesItemBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CoinViewHolder holder, int position) {
        holder.bind(coins.get(position));
    }

    @Override
    public int getItemCount() {
        return coins.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        inflater = LayoutInflater.from(recyclerView.getContext());
    }

    class CoinViewHolder extends RecyclerView.ViewHolder {

        private final RatesItemBinding binding;

        public CoinViewHolder(@NonNull RatesItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Coin coin) {

            binding.symbol.setText(coin.symbol());
//            binding.priceDollar.setText(coin.price());
//            binding.changePercent.setText(coin.percent_change_1h());
        }
    }
}
