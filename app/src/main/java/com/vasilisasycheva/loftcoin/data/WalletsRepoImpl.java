package com.vasilisasycheva.loftcoin.data;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import timber.log.Timber;

@Singleton
public class WalletsRepoImpl implements WalletsRepo {


    private final FirebaseFirestore firestore;
    private final CoinsRepo coinsRepo;

    @Inject
    public WalletsRepoImpl(CoinsRepo coinsRepo) {
        this.coinsRepo = coinsRepo;
        this.firestore = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public Observable<List<Wallet>> wallets(@NonNull Currency currency) {
        return Observable
                .<QuerySnapshot>create(emitter -> {
                    final ListenerRegistration registration = firestore.collection("wallets")
                            .orderBy("time", Query.Direction.ASCENDING)
                            .addSnapshotListener((value, error) -> {
                                if (emitter.isDisposed()) return;
                                if (value != null) {
                                    emitter.onNext(value);
                                }
                                else if (error != null) {
                                    Timber.e(error);
                                    emitter.tryOnError(error);
                                }
                            });
                    emitter.setCancellable(registration::remove);
                })
                .map(QuerySnapshot::getDocuments)
                .switchMapSingle((documents) -> Observable
                        .fromIterable(documents)
                        .flatMapSingle((document) -> coinsRepo
                                .coin(currency, document.getLong("coinId"))
                                .map((coin) -> Wallet.create(
                                        document.getId(),
                                        coin,
                                        document.getDouble("balance")
                                ))
                        )
                        .toList()
                );

    }

    @NonNull
    @Override
    public Observable<List<Transaction>> transactions(@NonNull Wallet wallet) {
        return Observable
                .<QuerySnapshot>create(emitter -> {
                    final ListenerRegistration registration = firestore
                            .collection("wallets")
                            .document(wallet.uid())
                            .collection("transactions")
                            .addSnapshotListener((value, error) -> {
                                if (emitter.isDisposed()) return;
                                if (value != null) {
                                    emitter.onNext(value);
                                } else if (error != null) {
                                    Timber.e(error);
                                    emitter.tryOnError(error);
                                }
                            });
                    emitter.setCancellable(registration::remove);
                })
                .map(QuerySnapshot::getDocuments)
                .switchMapSingle((documents) -> Observable
                        .fromIterable(documents)
                        .map((document) -> Transaction.create(
                                document.getId(),
                                wallet.coin(),
                                document.getDouble("amount"),
                                document.getDate("time")
                        ))
                        .toList()
                );

    }
}
