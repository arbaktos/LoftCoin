package com.example.loftcoin.ui.rates;


import static com.google.common.truth.Truth.assertThat;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.loftcoin.data.Coin;
import com.example.loftcoin.data.CoinsRepo;
import com.example.loftcoin.data.FakeCoin;
import com.example.loftcoin.data.TestCoinsRepo;
import com.example.loftcoin.data.TestCurrencyRepo;
import com.example.loftcoin.data.TestRxSchedulers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import edu.emory.mathcs.backport.java.util.Arrays;
import io.reactivex.observers.TestObserver;

@RunWith(AndroidJUnit4.class)
public class RatesViewModelTest {

    Context context;

    private RatesViewModel viewModel;

    private TestCurrencyRepo currencyRepo;

    private TestCoinsRepo coinsRepo;

    private TestRxSchedulers schedulers;

    @Before
    public void setUp() throws Exception {
        context = ApplicationProvider.getApplicationContext();
        currencyRepo = new TestCurrencyRepo(context);
        coinsRepo = new TestCoinsRepo();
        schedulers = new TestRxSchedulers();
        viewModel = new RatesViewModel(coinsRepo, currencyRepo, schedulers);
    }

    @Test
    public void coins() {
        final TestObserver<List<Coin>> coinsTest = viewModel.coins().test();
        viewModel.isRefreshing().test().assertValue(true);
        final List<Coin> coins = Arrays.asList(new FakeCoin[]{new FakeCoin(), new FakeCoin()});
        coinsRepo.listings.onNext(coins);
        viewModel.isRefreshing().test().assertValue(false);
        coinsTest.assertValue(coins);

        CoinsRepo.Query query = coinsRepo.lastListingsQuery;
        assertThat(query).isNotNull();
        assertThat(query.forceUpdate()).isTrue();

        viewModel.switchSortingOrder();

        viewModel.isRefreshing().test().assertValue(false);
        coinsRepo.listings.onNext(coins);
        viewModel.isRefreshing().test().assertValue(false);

        query = coinsRepo.lastListingsQuery;
        assertThat(query).isNotNull();
        assertThat(query.forceUpdate()).isFalse();
    }

    @After
    public void tearDown() throws Exception {

    }
}