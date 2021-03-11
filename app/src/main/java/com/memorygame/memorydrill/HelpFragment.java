package com.memorygame.memorydrill;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

/**
 * Created by aspire on 04-07-2016.
 */
public class HelpFragment extends Fragment implements View.OnClickListener {

    private Button btnClose;
    private TextView tvMasterMindFragment;
    private TextView tvHelpTitle;
    private TextView tvHelpText;
    HelpFragmentListener helpFragmentListener;


    public HelpFragment() {
    }

    public interface HelpFragmentListener{
        void destroyFragment();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(com.memorygame.memorydrill.R.layout.fragment_help, container, false);

        return  rootView;
    }
        @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            helpFragmentListener = (HelpFragmentListener) getActivity();

            tvMasterMindFragment = (TextView) getActivity().findViewById(com.memorygame.memorydrill.R.id.tvMasterMindFragment);
            tvHelpText = (TextView) getActivity().findViewById(com.memorygame.memorydrill.R.id.tvHelpText);
            tvHelpTitle = (TextView) getActivity().findViewById(com.memorygame.memorydrill.R.id.tvHelpTitle);

            Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/papyrus.ttf");
            tvMasterMindFragment.setTypeface(face);

            face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/bradhitc.ttf");
            tvHelpText.setTypeface(face);

            face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/bradhitc.ttf");
            tvHelpTitle.setTypeface(face);

            btnClose = (Button)getActivity().findViewById(com.memorygame.memorydrill.R.id.btnClose);
            btnClose.setOnClickListener(this);

        }
    @Override
    public void onStart(){
        super.onStart();
    }


    @Override
    public void onClick(View v) {

        helpFragmentListener.destroyFragment();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        helpFragmentListener = null;

    }
}

