package com.example.loftcoin.ui.welcome;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loftcoin.R;
import com.example.loftcoin.databinding.ItemWelcomeBinding;

public class WelcomeRecyclerAdapter extends RecyclerView.Adapter<WelcomeRecyclerAdapter.WelcomeViewHolder> {

    public static final int[] IMAGES = {
            R.drawable.welcome_pic_1,
            R.drawable.welcome_pic_2,
            R.drawable.welcome_pic_3,
    };

    public static final int[] TITLES = {
            R.string.welcome_title1,
            R.string.welcome_title2,
            R.string.welcome_title3,
    };

    public static final int[] TEXTS = {
            R.string.welcome_text1,
            R.string.welcome_text2,
            R.string.welcome_text3,
    };

    LayoutInflater inflater;
    @NonNull
    @Override
    public WelcomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final ItemWelcomeBinding binding = ItemWelcomeBinding.inflate(inflater, parent, false);
        return new WelcomeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull WelcomeViewHolder holder, int position) {
        holder.binding.welcomePicture.setImageResource(IMAGES[position]);
        holder.binding.welcomeTitle.setText(TITLES[position]);
        holder.binding.welcomeText.setText(TEXTS[position]);
    }

    @Override
    public int getItemCount() {
        return IMAGES.length;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        inflater = LayoutInflater.from(recyclerView.getContext());
        super.onAttachedToRecyclerView(recyclerView);
    }

    static class WelcomeViewHolder extends RecyclerView.ViewHolder {

        final ItemWelcomeBinding binding;

        public WelcomeViewHolder(ItemWelcomeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int position) {

        }
    }
}
