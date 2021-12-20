package com.example.loftcoin.ui.converter;

import androidx.lifecycle.ViewModelProvider;

import com.example.loftcoin.BaseComponent;
import com.example.loftcoin.utils.ViewModelModule;

import dagger.Component;

@Component(modules = {
        ConverterModule.class,
        ViewModelModule.class
},
dependencies = BaseComponent.class)
abstract class ConverterComponent {

    abstract ViewModelProvider.Factory viewModelFactory();
}
