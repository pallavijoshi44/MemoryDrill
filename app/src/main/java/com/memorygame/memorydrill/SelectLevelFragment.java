package com.memorygame.memorydrill;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by aspire on 04-07-2016.
 */
public class SelectLevelFragment extends Fragment implements View.OnClickListener {

    private TextView tvMasterMind;
    private TextView tvStageOne;
    private TextView tvStageTwo;
    private TextView tvStageThree;
    private TextView tvStageFour;
    private AutoResizeTextView tvSelectLevel;
    private Button helpBtn;
    private int level;
    Bundle args;
    SelectLevelFragmentListener mListener;


    public interface SelectLevelFragmentListener{
        void onBtnSelected(int level);
    }
    public SelectLevelFragment() {
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }
    @Override
    public void onPause(){

        super.onPause();
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(com.memorygame.memorydrill.R.layout.activity_level, container, false);

        return  rootView;
    }
        @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            mListener = (SelectLevelFragmentListener) getActivity();
            args = getArguments();
            level = args.getInt("Level");
            tvMasterMind = (TextView) getActivity().findViewById(com.memorygame.memorydrill.R.id.tvMasterMind);
            tvStageOne = (TextView) getActivity().findViewById(com.memorygame.memorydrill.R.id.tvStageOne);
            tvStageTwo = (TextView) getActivity().findViewById(com.memorygame.memorydrill.R.id.tvStageTwo);
            tvStageThree = (TextView) getActivity().findViewById(com.memorygame.memorydrill.R.id.tvStageThree);
            tvStageFour = (TextView) getActivity().findViewById(com.memorygame.memorydrill.R.id.tvStageFour);
            helpBtn = (Button)getActivity().findViewById(com.memorygame.memorydrill.R.id.btnHelp);
            tvSelectLevel = (AutoResizeTextView) getActivity().findViewById(com.memorygame.memorydrill.R.id.tv_select_level);
            helpBtn.setOnClickListener(this);

            Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/papyrus.ttf");
            tvMasterMind.setTypeface(face);

            face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/mvboli.ttf");
            tvSelectLevel.setTypeface(face);


            face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/mvboli.ttf");
            tvStageOne.setTypeface(face);

            face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/mvboli.ttf");
            tvStageTwo.setTypeface(face);

            face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/mvboli.ttf");
            tvStageThree.setTypeface(face);

            face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/mvboli.ttf");
            tvStageFour.setTypeface(face);

            Button btn;
            for(int i=1; i<=level;i++)
            {
                if(i == 17){
                    break;
                }
                else {
                    String resourceName = "btn_" + i;
                    try {
                        Class res = com.memorygame.memorydrill.R.id.class;
                        Field field = res.getField(resourceName);
                        int resID = field.getInt(null);
                        btn = (Button) getActivity().findViewById(resID);
                        btn.setText(" " + i + " ");
                        btn.setTextColor(Color.WHITE);
                        btn.setTextSize(25);
                        btn.setBackground(null);
                        btn.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.button_selector));
                      //  btn.setBackground(getActivity().getDrawable(com.memorygame.memorydrill.R.drawable.button_selector));
                        btn.setEnabled(true);
                        btn.setOnClickListener(this);
                    } catch (Exception e) {
                        Log.e("MyTag", "Failure to get drawable id.", e);
                    }
                }



            }

        }
    @Override
    public void onStart(){
        super.onStart();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case com.memorygame.memorydrill.R.id.btn_1:
                mListener.onBtnSelected(1);
                break;
            case com.memorygame.memorydrill.R.id.btn_2:
                mListener.onBtnSelected(2);
                break;
            case com.memorygame.memorydrill.R.id.btn_3:
                mListener.onBtnSelected(3);
                break;
            case com.memorygame.memorydrill.R.id.btn_4:
                mListener.onBtnSelected(4);
                break;
            case com.memorygame.memorydrill.R.id.btn_5:
                mListener.onBtnSelected(5);
                break;
            case com.memorygame.memorydrill.R.id.btn_6:
                mListener.onBtnSelected(6);
                break;
            case com.memorygame.memorydrill.R.id.btn_7:
                mListener.onBtnSelected(7);
                break;
            case com.memorygame.memorydrill.R.id.btn_8:
                mListener.onBtnSelected(8);
                break;
            case com.memorygame.memorydrill.R.id.btn_9:
                mListener.onBtnSelected(9);
                break;
            case com.memorygame.memorydrill.R.id.btn_10:
                mListener.onBtnSelected(10);
                break;
            case com.memorygame.memorydrill.R.id.btn_11:
                mListener.onBtnSelected(11);
                break;
            case com.memorygame.memorydrill.R.id.btn_12:
                mListener.onBtnSelected(12);
                break;
            case com.memorygame.memorydrill.R.id.btn_13:
                mListener.onBtnSelected(13);
                break;
            case com.memorygame.memorydrill.R.id.btn_14:
                mListener.onBtnSelected(14);
                break;
            case com.memorygame.memorydrill.R.id.btn_15:
                mListener.onBtnSelected(15);
                break;
            case com.memorygame.memorydrill.R.id.btn_16:
                mListener.onBtnSelected(16);
                break;
            case com.memorygame.memorydrill.R.id.btnHelp:
                mListener.onBtnSelected(10000);
                break;


        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        tvMasterMind = null;
        tvStageOne = null;
        tvStageTwo = null;
        tvStageThree = null;
        tvStageFour = null;
        args = null;
        mListener = null;
    }
}

