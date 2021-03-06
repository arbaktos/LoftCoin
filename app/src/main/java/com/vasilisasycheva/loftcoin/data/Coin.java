package com.vasilisasycheva.loftcoin.data;

public interface Coin {

    int id();

    String name();

    String symbol();

    double price();

    double change24h();

    int rank();

    String currencyCode();

}
