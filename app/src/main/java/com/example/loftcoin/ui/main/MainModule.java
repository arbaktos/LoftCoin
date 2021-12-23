package com.example.loftcoin.ui.main;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;

import com.example.loftcoin.ui.converter.CoinsSheet;
import com.example.loftcoin.ui.converter.ConverterFragment;
import com.example.loftcoin.ui.currency.CurrencyDialog;
import com.example.loftcoin.ui.rates.RatesAdapter;
import com.example.loftcoin.ui.rates.RatesFragment;
import com.example.loftcoin.ui.wallets.WalletsFragment;
import com.example.loftcoin.utils.LoftFragmentFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainModule {

    @Binds
    abstract FragmentFactory fragmentFactory (LoftFragmentFactory impl);

    @Binds
    @IntoMap
    @ClassKey(RatesFragment.class)
    abstract Fragment ratesFragment(RatesFragment impl);

    @Binds
    @IntoMap
    @ClassKey(WalletsFragment.class)
    abstract Fragment walletsFragment(WalletsFragment impl);

    @Binds
    @IntoMap
    @ClassKey(ConverterFragment.class)
    abstract Fragment converterFragment(ConverterFragment impl);

    @Binds
    @IntoMap
    @ClassKey(CurrencyDialog.class)
    abstract Fragment dialogFragment(CurrencyDialog impl);

    @Binds
    @IntoMap
    @ClassKey(CoinsSheet.class)
    abstract Fragment coinsSheetFragment(CoinsSheet impl);
}
