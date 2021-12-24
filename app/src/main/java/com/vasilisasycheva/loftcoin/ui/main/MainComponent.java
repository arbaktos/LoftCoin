package com.vasilisasycheva.loftcoin.ui.main;


import com.vasilisasycheva.loftcoin.BaseComponent;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = MainModule.class,
        dependencies = {
                BaseComponent.class
        }
)
public abstract class MainComponent {

    abstract void inject(MainActivity activity);
}
