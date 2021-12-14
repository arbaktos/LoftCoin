package com.example.loftcoin.data;


import com.google.auto.value.AutoValue;

import java.util.List;

@AutoValue
abstract class Listing {
    public abstract List<AutoValue_CmcCoin> data();
}
