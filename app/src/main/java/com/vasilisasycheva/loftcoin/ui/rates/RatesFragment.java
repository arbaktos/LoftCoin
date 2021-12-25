package com.vasilisasycheva.loftcoin.ui.rates;

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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.vasilisasycheva.loftcoin.BaseComponent;
import com.vasilisasycheva.loftcoin.R;
import com.vasilisasycheva.loftcoin.databinding.FragmentRatesBinding;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class RatesFragment extends Fragment{

    private final CompositeDisposable disposable = new CompositeDisposable();

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
        adapter = component.ratesAdapter();
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
        disposable.add(viewModel.coins().subscribe(adapter::submitList));
        disposable.add(viewModel.isRefreshing().subscribe(binding.refresher::setRefreshing));
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
        } else if (R.id.sort_dialog == item.getItemId()) {
            viewModel.switchSortingOrder();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        binding.ratesRecycler.swapAdapter(null, false);
        disposable.clear();
        super.onDestroyView();
    }
}
