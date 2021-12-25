package com.vasilisasycheva.loftcoin.ui.wallets;

import androidx.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

@Module
abstract class WalletsModule {

    @Binds
    @IntoMap
    @ClassKey(WalletViewModel.class)
    abstract ViewModel walletsViewModel(WalletViewModel impl);
}
