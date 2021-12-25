package com.vasilisasycheva.loftcoin.utils;

import androidx.annotation.NonNull;

import java.util.Locale;

import timber.log.Timber;

public class DebugTree extends Timber.DebugTree {

    @Override
    protected void log(int priority, String tag, @NonNull String message, Throwable t) {
        final StackTraceElement[] stackTrace = new Throwable().fillInStackTrace().getStackTrace();
        final StackTraceElement ste = stackTrace[5];

        super.log(priority, tag, String.format(Locale.US,
                "[%s] %s.%s(%s:%d): %s",
                Thread.currentThread().getName(),
                ste.getClassName(),
                ste.getMethodName(),
                ste.getFileName(),
                ste.getLineNumber(),
                message
        ), t);
    }
}
