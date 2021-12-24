package com.vasilisasycheva.loftcoin.utils;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class UtilModule {

    @Binds
    abstract ImageLoader imageLoader(PicassoImageLoader impl);

    @Binds
    abstract RxSchedulers rxSchedulers(RxSchedulersImpl impl);

    @Binds
    abstract Notifier notifier(NotifierImpl impl);

}
