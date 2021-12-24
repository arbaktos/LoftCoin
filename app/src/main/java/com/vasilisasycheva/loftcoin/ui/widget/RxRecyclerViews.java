package com.vasilisasycheva.loftcoin.ui.widget;


import android.os.Parcelable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.vasilisasycheva.loftcoin.utils.OnItemClick;

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

    @NonNull
    public static Observable<Integer> onClick (@NonNull RecyclerView recyclerView) {
        return Observable.create((emitter -> {
            MainThreadDisposable.verifyMainThread();
            final RecyclerView.OnItemTouchListener onItemClick = new OnItemClick((v) -> {
                RecyclerView.ViewHolder holder = recyclerView.findContainingViewHolder(v);
                if (holder != null) {
                    emitter.onNext(holder.getAdapterPosition());
                }
            });
            emitter.setCancellable(() -> recyclerView.removeOnItemTouchListener(onItemClick));
            recyclerView.addOnItemTouchListener(onItemClick);
        }));
    }
}
