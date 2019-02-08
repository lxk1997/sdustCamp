package com.clxk.h.sdustcamp.listener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created on 19/2/3
 * 上拉加载更多
 */
public abstract class EndScrollListener extends RecyclerView.OnScrollListener {

    private LinearLayoutManager linearLayoutManager;
    private int curPage = 0;
    private int totalCnt;
    private int preTotalCnt = 0;
    private int visCnt;
    private int firVisItem;
    private boolean loading = true;

    public EndScrollListener(LinearLayoutManager linearLayoutManager) {
        this.linearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visCnt = recyclerView.getChildCount();
        totalCnt = linearLayoutManager.getItemCount();
        firVisItem = linearLayoutManager.findFirstVisibleItemPosition();
        if(loading) {

            if(totalCnt > preTotalCnt) {
                loading = false;
                preTotalCnt = totalCnt;
            }
        }
        if(!loading && totalCnt - visCnt <= firVisItem) {
            curPage++;
            onLoadMore(curPage);
            loading = true;
        }
    }

    protected abstract void onLoadMore(int curPage);
}
