package com.example.loftcoin.ui.wallets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.loftcoin.databinding.FragmentWalletsBinding;

import javax.inject.Inject;

public class WalletsFragment extends Fragment {

    @Inject
    public WalletsFragment() {
    }

    FragmentWalletsBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentWalletsBinding.inflate(inflater);
        return binding.getRoot();
    }
}
