package com.vasilisasycheva.loftcoin.ui.converter;

import androidx.lifecycle.ViewModelProvider;

import com.vasilisasycheva.loftcoin.BaseComponent;
import com.vasilisasycheva.loftcoin.utils.ViewModelModule;

import dagger.Component;

@Component(modules = {
        ConverterModule.class,
        ViewModelModule.class
},
dependencies = BaseComponent.class)
abstract class ConverterComponent {

    abstract ViewModelProvider.Factory viewModelFactory();

    abstract CoinsSheetAdapter coinsSheetAdapter();
}
