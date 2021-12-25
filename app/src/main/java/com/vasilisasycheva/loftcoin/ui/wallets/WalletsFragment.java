package com.vasilisasycheva.loftcoin.ui.wallets;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.vasilisasycheva.loftcoin.BaseComponent;
import com.vasilisasycheva.loftcoin.R;
import com.vasilisasycheva.loftcoin.databinding.FragmentWalletsBinding;
import com.vasilisasycheva.loftcoin.ui.widget.RxRecyclerViews;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class WalletsFragment extends Fragment {

    private final CompositeDisposable disposable = new CompositeDisposable();

    private final WalletsComponent component;

    private FragmentWalletsBinding binding;

    private WalletViewModel viewModel;

    private SnapHelper walletSnapHelper;

    private WalletsAdapter walletsAdapter;

    private TransactionAdapter transactionAdapter;

    @Inject
    public WalletsFragment(BaseComponent baseComponent) {
        component = DaggerWalletsComponent.builder()
                .baseComponent(baseComponent)
                .build();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, component.viewModelFactory())
                .get(WalletViewModel.class);
        walletsAdapter = component.walletsAdapter();
        transactionAdapter = component.transactionAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentWalletsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        walletSnapHelper = new PagerSnapHelper();
        walletSnapHelper.attachToRecyclerView(binding.walletRecycler);

        final TypedValue value = new TypedValue();
        view.getContext().getTheme().resolveAttribute(R.attr.walletCardWidth, value, true);
        final DisplayMetrics displayMetrics = view.getContext().getResources().getDisplayMetrics();
        final int padding = (int) (displayMetrics.widthPixels - value.getDimension(displayMetrics)) / 2;
        binding.walletRecycler.setPadding(padding, 0, padding, 0);
        binding.walletRecycler.setClipToPadding(false);

        binding.transactionsRecycler.setAdapter(transactionAdapter);
        binding.transactionsRecycler
                .setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        binding.transactionsRecycler.setHasFixedSize(true);

        binding.walletRecycler.setAdapter(walletsAdapter);
        binding.walletRecycler.setLayoutManager(
                new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.walletRecycler.addOnScrollListener(new CarouselScroller());
        disposable.add(viewModel.wallets().subscribe((wallets) -> {
            walletsAdapter.submitList(wallets);
            Timber.d(String.valueOf(wallets));
            if (!wallets.isEmpty()) disposable.add(viewModel.transactions().subscribe(transactionAdapter::submitList));
        }));
        disposable.add(viewModel.wallets().map(List::isEmpty).subscribe((isEmpty) -> {
            binding.newWallet.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
            binding.walletRecycler.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
        }));
        disposable.add(RxRecyclerViews
                .onSnap(binding.walletRecycler, walletSnapHelper)
                .subscribe(viewModel::changeWallet)
        );
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.new_wallet, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        walletSnapHelper.attachToRecyclerView(null);
        disposable.clear();
        binding.walletRecycler.setAdapter(null);
        binding.transactionsRecycler.setAdapter(null);
        super.onDestroyView();
    }

    private static class CarouselScroller extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            final int centerX = (recyclerView.getLeft() + recyclerView.getRight()) / 2;
            for (int i = 0; i <recyclerView.getChildCount(); ++i) {
                final View child = recyclerView.getChildAt(i);
                final int childCenterX = (child.getLeft() + child.getRight()) / 2;
                final float childOffset = Math.abs(centerX - childCenterX) / (float) centerX;
                float factor = (float) (Math.pow(0.85, childOffset));
                child.setScaleX(factor);
                child.setScaleY(factor);
            }
        }
    }
}
