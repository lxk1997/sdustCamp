package com.clxk.h.sdustcamp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clxk.h.sdustcamp.R;

/**
 * Created by Chook_lxk
 *
 * 二手市场-市场-我有闲置Fragment
 */
public class FragmentMarketInXZ extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_market_market_xz,container,false);
    }
}
