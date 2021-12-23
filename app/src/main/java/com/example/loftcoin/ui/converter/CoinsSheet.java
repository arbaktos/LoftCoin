package com.example.loftcoin.ui.converter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.loftcoin.BaseComponent;
import com.example.loftcoin.R;
import com.example.loftcoin.databinding.CurrencyDialogBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import javax.inject.Inject;

public class CoinsSheet extends BottomSheetDialogFragment {


    public static final String KEY_MODE = "mode";
    public static final int MODE_FROM = 1;
    public static final int MODE_TO = 2;

    private final ConverterComponent component;

    private LayoutInflater inflater;

    private CurrencyDialogBinding binding;

    private ConverterViewModel viewModel;

    @Inject
    public CoinsSheet(BaseComponent baseComponent) {
        component = DaggerConverterComponent.builder()
                .baseComponent(baseComponent)
                .build();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireParentFragment(), component.viewModelFactory())
                .get(ConverterViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.currency_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = CurrencyDialogBinding.bind(view);
    }
}
