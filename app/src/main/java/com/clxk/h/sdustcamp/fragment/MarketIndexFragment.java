package com.clxk.h.sdustcamp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.INotificationSideChannel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.ui.MainActivity;

public class MarketIndexFragment extends Fragment implements View.OnClickListener {

    private View currentView;

    private ImageButton ib_market_back;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        currentView = inflater.inflate(R.layout.fragment_market_index,container,false);

        initView();

        initEvent();
        return  currentView;
    }

    private void initEvent() {

        ib_market_back.setOnClickListener(this);
    }

    private void initView() {

        ib_market_back = currentView.findViewById(R.id.ib_market_back);
    }

    @Override
    public void onClick(View v) {

        Intent intent;
        switch (v.getId()) {

            case R.id.ib_market_back:
                intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("frId",R.id.ll_service);
                startActivity(intent);
                getActivity().onBackPressed();
                break;
        }
    }
}
