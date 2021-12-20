package com.example.loftcoin.fcm;

import com.example.loftcoin.BaseComponent;

import dagger.Component;

@Component(modules = FcmModule.class,
dependencies = BaseComponent.class)
abstract class FcmComponent {

    abstract void inject(FcmService service);
}
