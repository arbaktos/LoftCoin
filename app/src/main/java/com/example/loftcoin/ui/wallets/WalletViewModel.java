package com.example.loftcoin.ui.wallets;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.room.OnConflictStrategy;

import com.example.loftcoin.data.CoinsRepo;
import com.example.loftcoin.data.CurrencyRepo;
import com.example.loftcoin.data.Transaction;
import com.example.loftcoin.data.Wallet;
import com.example.loftcoin.data.WalletsRepo;
import com.example.loftcoin.utils.RxSchedulers;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import timber.log.Timber;

public class WalletViewModel extends ViewModel {

    private final Subject<Integer> walletPosition = BehaviorSubject.createDefault(0);

    private final Observable<List<Transaction>> transactions;

    private final Observable<List<Wallet>> wallets;

    private final RxSchedulers schedulers;
    private final WalletsRepo walletsRepo;
    private final CurrencyRepo currencyRepo;

//    @Inject
//    public WalletViewModel(WalletsRepo walletsRepo, CurrencyRepo currencyRepo, RxSchedulers schedulers) {
//        this.schedulers = schedulers;
//        wallets = currencyRepo.currency()
//                .switchMap((walletsRepo::wallets))
//                .replay(1)
//                .autoConnect();
//
//        transactions = wallets
//                .switchMap((wallets) -> walletPosition.map(wallets::get))
//                .switchMap(walletsRepo::transactions)
//                .replay(1)
//                .autoConnect();
//    }

    @Inject
    WalletViewModel(WalletsRepo walletsRepo, CurrencyRepo currencyRepo, RxSchedulers schedulers) {
        this.walletsRepo = walletsRepo;
        this.currencyRepo = currencyRepo;
        this.schedulers = schedulers;
        wallets = currencyRepo.currency()
                .switchMap(walletsRepo::wallets)
                .replay(1)
                .autoConnect()
                .doOnNext((wallets) -> Timber.d("%d", wallets.size()));

        transactions = wallets
                .switchMap((wallets) -> walletPosition
                        .map(wallets::get))
                .switchMap(walletsRepo::transactions)
                .replay(1)
                .autoConnect();
    }

    Observable<List<Wallet>> wallets() {
        return wallets.observeOn(schedulers.main());
    }

    @NonNull
    Observable<List<Transaction>> transactions() {
        return transactions.observeOn(schedulers.main());
    }

    @NonNull
    Completable addWallet() {
        return Completable.fromAction(() -> Timber.d("~"));
    }

    public void changeWallet(Integer position) {
        walletPosition.onNext(position);
    }
}
