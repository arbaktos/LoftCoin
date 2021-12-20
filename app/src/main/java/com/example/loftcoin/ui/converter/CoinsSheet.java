package com.example.loftcoin.ui.converter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.loftcoin.BaseComponent;
import com.example.loftcoin.R;
import com.example.loftcoin.databinding.DialogCurrencyBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import javax.inject.Inject;

public class CoinsSheet extends BottomSheetDialogFragment {

    private final ConverterComponent component;

    private DialogCurrencyBinding binding;

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
        return inflater.inflate(R.layout.fragment_converter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = DialogCurrencyBinding.bind(view);
    }
}
