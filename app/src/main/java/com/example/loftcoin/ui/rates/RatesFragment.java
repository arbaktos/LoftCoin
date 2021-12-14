package com.example.loftcoin.ui.rates;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.loftcoin.BaseComponent;
import com.example.loftcoin.R;
import com.example.loftcoin.databinding.FragmentRatesBinding;
import com.example.loftcoin.utils.PercentFormatter;
import com.example.loftcoin.utils.PriceFormatter;

import javax.inject.Inject;

import timber.log.Timber;

public class RatesFragment extends Fragment{

    private final RatesComponent component;

    private FragmentRatesBinding binding;

    private RatesAdapter adapter;

    RatesViewModel viewModel;

    @Inject
    public RatesFragment(BaseComponent baseComponent) {
        component = DaggerRatesComponent.builder()
                .baseComponent(baseComponent)
                .build();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, component.viewModelFactory()).get(RatesViewModel.class);
        adapter = new RatesAdapter(new PriceFormatter(), new PercentFormatter());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rates, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        binding = FragmentRatesBinding.bind(view);
        binding.ratesRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        binding.ratesRecycler.setHasFixedSize(true);
        binding.ratesRecycler.swapAdapter(adapter, false);
        binding.refresher.setOnRefreshListener(viewModel::refresh);
        viewModel.coins().observe(getViewLifecycleOwner(), coins -> {
            adapter.submitList(coins);
                });
        viewModel.isRefreshing().observe(getViewLifecycleOwner(), binding.refresher::setRefreshing);
        binding.refresher.setProgressViewOffset(false, 56, 140);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.rates, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (R.id.currency_dialog == item.getItemId()) {
            NavHostFragment
                    .findNavController(this)
                    .navigate(R.id.currency_dialog);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        binding.ratesRecycler.swapAdapter(null, false);
        super.onDestroyView();
    }
}
