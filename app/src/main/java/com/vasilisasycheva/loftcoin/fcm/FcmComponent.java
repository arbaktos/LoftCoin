package com.vasilisasycheva.loftcoin.fcm;

import com.vasilisasycheva.loftcoin.BaseComponent;

import dagger.Component;

@Component(modules = FcmModule.class,
dependencies = BaseComponent.class)
abstract class FcmComponent {

    abstract void inject(FcmService service);
}
