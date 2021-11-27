package com.example.loftcoin.ui.rates;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.loftcoin.R;
import com.example.loftcoin.databinding.FragmentRatesBinding;

public class RatesFragment extends Fragment {
    FragmentRatesBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRatesBinding.inflate(inflater);
        return binding.getRoot();
    }
}
