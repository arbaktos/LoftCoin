package com.example.loftcoin.ui.widget;


import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import io.reactivex.Observable;
import io.reactivex.android.MainThreadDisposable;

public class RxRecyclerViews {

    @NonNull
    public static Observable<Integer> onSnap(@NonNull RecyclerView recyclerView, @NonNull SnapHelper snapHelper) {
        return Observable.create((emitter -> {
            MainThreadDisposable.verifyMainThread();
            RecyclerView.OnScrollListener listener = new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    if (RecyclerView.SCROLL_STATE_IDLE == newState) {
                        View snapView = snapHelper.findSnapView(recyclerView.getLayoutManager());
                        if (snapView != null) {
                            final RecyclerView.ViewHolder viewHolder = recyclerView.findContainingViewHolder(snapView);
                            if (viewHolder != null) {
                                emitter.onNext(viewHolder.getAdapterPosition());
                            }
                        }
                    }
                }
            };
            emitter.setCancellable(() -> recyclerView.removeOnScrollListener(listener));
            recyclerView.addOnScrollListener(listener);
        }));

    }
}
