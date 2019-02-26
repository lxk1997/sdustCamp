package com.clxk.h.sdustcamp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.clxk.h.sdustcamp.R;
import com.clxk.h.sdustcamp.ui.Cet46Activity;
import com.clxk.h.sdustcamp.ui.EmptyClass;
import com.clxk.h.sdustcamp.ui.MarketActivity;
import com.clxk.h.sdustcamp.ui.MobileFeeActivity;
import com.clxk.h.sdustcamp.ui.Schedule;
import com.clxk.h.sdustcamp.ui.ScoreActivity;

/**
 * Created by Chook_lxk on 18/9
 * 服务Fragment
 */
public class ServiceFragment extends Fragment {

    private LinearLayout ll_timeTable;
    private LinearLayout ll_cet46;
    private LinearLayout ll_library;
    private LinearLayout ll_market;
    private LinearLayout ll_mealCard;
    private  LinearLayout ll_mobile;
    private LinearLayout ll_stuCardRecharge;
    private LinearLayout ll_stuClass;
    private LinearLayout ll_queryScore;

    private EditText et_search;

    private View currentView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        currentView =  inflater.inflate(R.layout.fragment_service,container,false);

        initView();

        initEvent();

        return currentView;
    }

    /**
     * View初始化
     */
    private void initView() {

        ll_cet46 = currentView.findViewById(R.id.ll_cet46);
        ll_timeTable = currentView.findViewById(R.id.ll_timeTable);
        ll_library = currentView.findViewById(R.id.ll_library);
        ll_market = currentView.findViewById(R.id.ll_2market);
        ll_mealCard = currentView.findViewById(R.id.ll_mealCard);
        ll_mobile = currentView.findViewById(R.id.ll_mobile);
        ll_stuCardRecharge = currentView.findViewById(R.id.ll_stuCardRecharge);
        ll_stuClass = currentView.findViewById(R.id.ll_stuClass);
        ll_queryScore = currentView.findViewById(R.id.ll_queryScore);

      //  et_search = currentView.findViewById(R.id.et_search);

    }

    /**
     * View监听
     */
    private void initEvent() {

        ll_cet46.setOnClickListener(new onClick());
        ll_stuClass.setOnClickListener(new onClick());
        ll_stuCardRecharge.setOnClickListener(new onClick());
        ll_queryScore.setOnClickListener(new onClick());
        ll_mobile.setOnClickListener(new onClick());
        ll_timeTable.setOnClickListener(new onClick());
        ll_library.setOnClickListener(new onClick());
        ll_market.setOnClickListener(new onClick());
        ll_mealCard.setOnClickListener(new onClick());
    }

    /**
     * ClickListener
     */
    class onClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()) {
                case R.id.ll_timeTable:
                    intent = new Intent(getActivity(), Schedule.class);
                    startActivity(intent);
                    getActivity().onBackPressed();
                    break;
                case R.id.ll_2market:
                    intent = new Intent(getActivity(), MarketActivity.class);
                    startActivity(intent);
                    getActivity().onBackPressed();
                    break;
                case R.id.ll_cet46:
                    intent = new Intent(getActivity(), Cet46Activity.class);
                    startActivity(intent);
                    getActivity().onBackPressed();
                    break;
                case R.id.ll_mobile:
                    intent = new Intent(getActivity(), MobileFeeActivity.class);
                    startActivity(intent);
                    getActivity().onBackPressed();
                    break;
                case R.id.ll_stuClass:
                    intent = new Intent(getActivity(), EmptyClass.class);
                    startActivity(intent);
                    getActivity().onBackPressed();
                    break;
                case R.id.ll_queryScore:
                    intent = new Intent(getActivity(), ScoreActivity.class);
                    startActivity(intent);
                    getActivity().onBackPressed();
                    break;
            }
        }
    }
}
