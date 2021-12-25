package com.vasilisasycheva.loftcoin.data;

import androidx.annotation.NonNull;

import com.google.auto.value.AutoValue;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;


public interface CoinsRepo {

    Observable<List<Coin>> listings(@NonNull Query query);

    @NonNull
    Single<Coin> coin(Currency currency, Long id);


    Observable<List<Coin>> topCoins(Currency currency);

    @AutoValue
    abstract class Query {

        public static Builder builder() {
            return new AutoValue_CoinsRepo_Query.Builder()
                    .forceUpdate(true);
        }
        public abstract String currency();
        public abstract boolean forceUpdate();
        public abstract SortBy sortBy();

        @AutoValue.Builder
        public abstract static class Builder {
            public abstract Builder currency(String currency);
            public abstract Builder forceUpdate(boolean forceUpdate);
            public abstract Builder sortBy(SortBy sortBy);
            public abstract Query build();
        }

    }
}
