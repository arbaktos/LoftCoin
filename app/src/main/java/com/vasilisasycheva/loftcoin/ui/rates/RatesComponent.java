package com.vasilisasycheva.loftcoin.ui.rates;

import androidx.lifecycle.ViewModelProvider;

import com.vasilisasycheva.loftcoin.BaseComponent;
import com.vasilisasycheva.loftcoin.utils.ViewModelModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                RatesModule.class,
                ViewModelModule.class
        },
        dependencies = BaseComponent.class
)
abstract class RatesComponent {

        abstract ViewModelProvider.Factory viewModelFactory();

        abstract RatesAdapter ratesAdapter();
}
