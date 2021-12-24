package com.vasilisasycheva.loftcoin.ui.converter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.vasilisasycheva.loftcoin.BaseComponent;
import com.vasilisasycheva.loftcoin.R;
import com.vasilisasycheva.loftcoin.databinding.CurrencyDialogBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.vasilisasycheva.loftcoin.ui.widget.RxRecyclerViews;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;



public class CoinsSheet extends BottomSheetDialogFragment {

    static final String KEY_MODE = "mode";

    static final int MODE_FROM = 1;

    static final int MODE_TO = 2;

    private final ConverterComponent component;

    private CurrencyDialogBinding binding;

    private CompositeDisposable disposable = new CompositeDisposable();

    private ConverterViewModel viewModel;
    
    private CoinsSheetAdapter adapter;

    private int mode;

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
        adapter = component.coinsSheetAdapter();
        mode = requireArguments().getInt(KEY_MODE, MODE_FROM);
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
        binding.recycler.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        binding.recycler.setAdapter(adapter);

        disposable.add(viewModel.topCoins().subscribe(adapter::submitList));
        disposable.add(RxRecyclerViews.onClick(binding.recycler)
                .map((position) -> adapter.getItem(position))
                .subscribe(coin -> {
                    if (MODE_FROM == mode) {
                        viewModel.fromCoin(coin);
                    }else {
                        viewModel.toCoin(coin);
                    }
                    dismissAllowingStateLoss();
                })
        );

    }

    @Override
    public void onDestroyView() {
        binding.recycler.setAdapter(null);
        disposable.dispose();
        super.onDestroyView();
    }
}
