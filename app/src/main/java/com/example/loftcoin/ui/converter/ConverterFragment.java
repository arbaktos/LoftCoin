package com.example.loftcoin.ui.converter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.loftcoin.R;
import com.example.loftcoin.databinding.FragmentConverterBinding;

public class ConverterFragment extends Fragment {

    FragmentConverterBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentConverterBinding.inflate(inflater);
        return binding.getRoot();
    }
}
