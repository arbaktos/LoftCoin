package com.vasilisasycheva.loftcoin.ui.wallets;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.vasilisasycheva.loftcoin.BuildConfig;
import com.vasilisasycheva.loftcoin.data.Wallet;
import com.vasilisasycheva.loftcoin.databinding.WalletItemBinding;
import com.vasilisasycheva.loftcoin.utils.BalanceFormatter;
import com.vasilisasycheva.loftcoin.utils.ImageLoader;
import com.vasilisasycheva.loftcoin.utils.PriceFormatter;

import java.util.Objects;

import javax.inject.Inject;

public class WalletsAdapter extends ListAdapter<Wallet, WalletsAdapter.WalletsViewHolder> {

    private LayoutInflater inflater;
    private final PriceFormatter priceFormatter;
    private BalanceFormatter balanceFormatter;
    private ImageLoader imageLoader;

    @Inject
    public WalletsAdapter(PriceFormatter priceFormatter, BalanceFormatter balanceFormatter, ImageLoader imageLoader) {
        super(new DiffUtil.ItemCallback<Wallet>() {
            @Override
            public boolean areItemsTheSame(@NonNull Wallet oldItem, @NonNull Wallet newItem) {
                return Objects.equals(oldItem, newItem);
            }

            @Override
            public boolean areContentsTheSame(@NonNull Wallet oldItem, @NonNull Wallet newItem) {
                return Objects.equals(oldItem, newItem);
            }
        });
        this.priceFormatter = priceFormatter;
        this.balanceFormatter = balanceFormatter;
        this.imageLoader = imageLoader;
    }

    @NonNull
    @Override
    public WalletsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WalletsViewHolder(WalletItemBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WalletsViewHolder holder, int position) {
        final Wallet wallet = getItem(position);
        holder.binding.symbol.setText(wallet.coin().symbol());
        holder.binding.walletBalance.setText(balanceFormatter.format(wallet));
        imageLoader
                .load(BuildConfig.IMG_END_POINT + wallet.coin().id() +".png")
                .into(holder.binding.currencyIcon);
        final double balance = wallet.balance() * wallet.coin().price();
        holder.binding.balanceDollars.setText(priceFormatter.format(wallet.coin().currencyCode(), balance));

    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        inflater = LayoutInflater.from(recyclerView.getContext());
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class WalletsViewHolder extends RecyclerView.ViewHolder {

        private final WalletItemBinding binding;

        public WalletsViewHolder(@NonNull WalletItemBinding binding) {
            super(binding.getRoot());
            binding.getRoot().setClipToOutline(true);
            this.binding = binding;
        }
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }
}
