package com.example.loftcoin.ui.rates;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loftcoin.BuildConfig;
import com.example.loftcoin.R;
import com.example.loftcoin.data.CmcCoin;
import com.example.loftcoin.data.Coin;
import com.example.loftcoin.databinding.RatesItemBinding;
import com.example.loftcoin.utils.Formatter;
import com.example.loftcoin.utils.OutlineCircle;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class RatesAdapter extends ListAdapter<Coin, RatesAdapter.ViewHolder> {

    LayoutInflater inflater;
    private int colorNegative;
    private int colorPositive;
    private Formatter<Double> priceFormatter;
    private Formatter<Double> percentFormatter;

    RatesAdapter(Formatter<Double> priceFormatter, Formatter<Double> percentFormatter) {
        super(new DiffUtil.ItemCallback<Coin>() {
            @Override
            public boolean areItemsTheSame(@NonNull Coin oldItem, @NonNull Coin newItem) {
                return oldItem.id() == newItem.id();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Coin oldItem, @NonNull Coin newItem) {
                return Objects.equals(oldItem, newItem);
            }
        });
        this.priceFormatter = priceFormatter;
        this.percentFormatter = percentFormatter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RatesItemBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Coin coin = getItem(position);
        holder.binding.symbol.setText(getItem(position).symbol());
        holder.binding.priceDollar.setText(priceFormatter.format(coin.price()));
        holder.binding.changePercent.setText(percentFormatter.format(coin.change24h()));
        if (coin.change24h() > 0) {
            holder.binding.changePercent.setTextColor(colorPositive);
        } else {
            holder.binding.changePercent.setTextColor(colorNegative);
        }
        Picasso.get()
                .load(BuildConfig.IMG_END_POINT + coin.id() +".png")
                .into(holder.binding.icon);
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

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final RatesItemBinding binding;

        public ViewHolder(RatesItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            OutlineCircle.apply(binding.icon);
        }
    }
}
