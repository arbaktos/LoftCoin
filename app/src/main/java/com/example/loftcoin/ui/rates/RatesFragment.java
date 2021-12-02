package com.example.loftcoin.ui.rates;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.loftcoin.R;
import com.example.loftcoin.data.Coin;
import com.example.loftcoin.databinding.FragmentRatesBinding;

import java.util.List;

public class RatesFragment extends Fragment implements RatesView {

    FragmentRatesBinding binding;
    private RatesPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new RatesPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRatesBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.ratesRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        binding.ratesRecycler.setHasFixedSize(true);
        presenter.attach(this);
    }

    @Override
    public void onDestroyView() {
        presenter.detach(this);
        binding.ratesRecycler.setAdapter(null);
        super.onDestroyView();
    }

    @Override
    public void showCoins(@NonNull List<? extends Coin> coins) {
        binding.ratesRecycler.setAdapter(new RatesAdapter(coins));
    }

    @Override
    public void showError(@NonNull String error) {

    }
}
