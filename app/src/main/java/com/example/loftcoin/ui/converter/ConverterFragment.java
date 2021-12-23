package com.example.loftcoin.ui.converter;

import static com.example.loftcoin.ui.converter.CoinsSheet.KEY_MODE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.loftcoin.BaseComponent;
import com.example.loftcoin.R;
import com.example.loftcoin.databinding.FragmentConverterBinding;
import com.jakewharton.rxbinding3.view.RxView;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class ConverterFragment extends Fragment {

    private ConverterComponent component;
    private ConverterViewModel viewModel;
    FragmentConverterBinding binding;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public ConverterFragment(BaseComponent baseComponent) {
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
        binding = FragmentConverterBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = NavHostFragment.findNavController(this);
        disposable.add(RxView.clicks(binding.fromCoin).subscribe(v -> {
            final Bundle args = new Bundle();
            args.putInt(KEY_MODE, CoinsSheet.MODE_FROM);
            navController.navigate(R.id.coins_sheet, args);
        }));

        disposable.add(RxView.clicks(binding.toCoin).subscribe(v -> {
            final Bundle args = new Bundle();
            args.putInt(KEY_MODE, CoinsSheet.MODE_TO);
            navController.navigate(R.id.coins_sheet, args);
        }));
    }


    @Override
    public void onDestroyView() {
        disposable.dispose();
        super.onDestroyView();
    }
}
