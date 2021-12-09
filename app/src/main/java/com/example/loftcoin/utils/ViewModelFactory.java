package com.example.loftcoin.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final Map<Class<?>, Provider<ViewModel>> providers;

    public ViewModelFactory(Map<Class<?>, Provider<ViewModel>> providers) {
        this.providers = providers;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> aClass) {
        return null;
    }
}
