package com.example.loftcoin.ui.rates;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.loftcoin.R;
import com.example.loftcoin.databinding.FragmentRatesBinding;

import javax.inject.Inject;

import timber.log.Timber;

public class RatesFragment extends Fragment{

   // private final RatesComponent component;

    private FragmentRatesBinding binding;

    private RatesAdapter adapter;

    RatesViewModel viewmodel;

    @Inject
    public RatesFragment() {
      //  component = Dagger
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewmodel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> aClass) {
                return null;
            }
        }
        ).get(RatesViewModel.class);
        adapter = new RatesAdapter();
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
        viewmodel.coins().observe(getViewLifecycleOwner(), coins -> {
            adapter.submitList(coins);
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.rates, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Timber.d("%s", item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        binding.ratesRecycler.swapAdapter(null, false);
        super.onDestroyView();
    }
}
