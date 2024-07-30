package com.memorygame.memorydrill;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * Created by aspire on 24-06-2016.
 */
public class LevelsFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private TextView tvMasterMind;
    Boolean state;
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    private Button btnContinue;
    ArrayList numbers;
    TypedArray imgs;
    TextView textViewTime;
    AutoResizeTextView tvMemorizeInfo;
    TextView tvLevelNo;
    long timeRemaining = -1;
    long millis;
    CounterClass timer;
    long calcTime;
    Bundle args;
    int level;
    int numImgs;
    int numNewImgs;
    int numColumns;
    int newPos;
    String textviewText;
    GridImageView imageNew;
    int columnWidth;
    LevelsFragmentListener mListener;
    Boolean saveFlag;
    Boolean flagChangeGridImages;
    ArrayList<GridImageView> gridItems;
    ArrayList<GridImageView> tempGridItems;

    public final static String LEVEL = "LEVEL";
    public final static int WIDTH_ONE = 35;

    public final static String TIMER_TEXT_STAGE_ONE = "01:00";
    public final static String TIMER_TEXT_STAGE_TWO = "00:45";
    public final static String TIMER_TEXT_STAGE_THREE = "00:30";
    public final static String TIMER_TEXT_STAGE_FOUR = "00:15";

    public final static long TIMER_ONE = 60000;
    public final static long TIMER_TWO = 45000;
    public final static long TIMER_THREE = 30000;
    public final static long TIMER_FOUR = 15000;
    GridViewAdapter tempGridAdapter;


    public interface LevelsFragmentListener{

        void onImageSelected(int newPos, int position, long timeRemaining, int itemPickedId, int newItemId, int level);
        void timerFinished(int level);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        state = false;
        imgs = null;
        setRetainInstance(true); //Will ignore onDestroy Method (Nested Fragments no need this if parent have it)
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        int layout = com.memorygame.memorydrill.R.layout.fragment_level_play;


        // Inflate the layout for this fragment
        return inflater.inflate(layout, container, false);

    }
    private void setTimer(){

            if (level == 1) {

                calcTime = TIMER_ONE;
                textviewText = TIMER_TEXT_STAGE_ONE;

            } else if (level == 2) {

                calcTime = TIMER_TWO;
                textviewText = TIMER_TEXT_STAGE_TWO;


            } else if (level == 3) {

                calcTime = TIMER_THREE;
                textviewText = TIMER_TEXT_STAGE_THREE;

            } else if (level == 4) {


                calcTime = TIMER_FOUR;
                textviewText = TIMER_TEXT_STAGE_FOUR;
            } else if (level == 5) {


                calcTime = TIMER_ONE;
                textviewText = TIMER_TEXT_STAGE_ONE;

            } else if (level == 6) {


                calcTime = TIMER_TWO;
                textviewText = TIMER_TEXT_STAGE_TWO;


            } else if (level == 7) {


                calcTime = TIMER_THREE;
                textviewText = TIMER_TEXT_STAGE_THREE;

            } else if (level == 8) {


                calcTime = TIMER_FOUR;
                textviewText = TIMER_TEXT_STAGE_FOUR;

            } else if (level == 9) {


                calcTime = TIMER_ONE;
                textviewText = TIMER_TEXT_STAGE_ONE;

            } else if (level == 10) {


                calcTime = TIMER_TWO;
                textviewText = TIMER_TEXT_STAGE_TWO;

            } else if (level == 11) {


                calcTime = TIMER_THREE;
                textviewText = TIMER_TEXT_STAGE_THREE;

            } else if (level == 12) {


                calcTime = TIMER_FOUR;
                textviewText = TIMER_TEXT_STAGE_FOUR;

            } else if (level == 13) {


                calcTime = TIMER_ONE;
                textviewText = TIMER_TEXT_STAGE_ONE;


            } else if (level == 14) {

                calcTime = TIMER_TWO;
                textviewText = TIMER_TEXT_STAGE_TWO;

            } else if (level == 15) {


                calcTime = TIMER_THREE;
                textviewText = TIMER_TEXT_STAGE_THREE;

            } else if (level == 16) {

                calcTime = TIMER_FOUR;
                textviewText = TIMER_TEXT_STAGE_FOUR;


            }

        if(timer != null)
            timer = null;

        timer = new CounterClass(calcTime, 1000);
        textViewTime.setText(textviewText);
        timer.start();
    }

    @Override
    public void onStop(){
        super.onStop();

        saveFlag = false;
        setIntent();


    }
    public void setIntent(){
        getActivity().getIntent().putExtra("timerStageText", textViewTime.getText().toString());
        getActivity().getIntent().putExtra("timerValue", millis);
    }
    public  void setState(Boolean value){
        state = value;
    }

    public  void setSaveflg(Boolean value){
        saveFlag = value;
    }

    @Override
    public void onResume(){
        super.onResume();

        if(state == true){
                if(saveFlag == false){

                    if(getActivity().getIntent().getExtras() != null) {
                        if(gridView != null ){
                            gridAdapter = tempGridAdapter;
                            gridView.setAdapter(gridAdapter);
                            flagChangeGridImages = false;
                        }
                        calcTime = getActivity().getIntent().getLongExtra("timerValue", 60000);
                        textviewText = getActivity().getIntent().getStringExtra("timerStageText");
                        if(timer != null)
                            timer = null;

                        timer = new CounterClass(calcTime, 1000);
                        textViewTime.setText(textviewText);
                        timer.start();
                    }


        }}
        tvLevelNo.setText("Level " + level);
    }

        @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

            args = getArguments();
            if (args != null) {
                level = args.getInt(LEVEL);
                numColumns = args.getInt("NumColumns");
                numImgs = args.getInt("NumImgs");
            }

            flagChangeGridImages = false;

        mListener = (LevelsFragmentListener) getActivity();
        textViewTime = (TextView) getActivity().findViewById(com.memorygame.memorydrill.R.id.textViewTime);
        tvMasterMind = (TextView) getActivity().findViewById(com.memorygame.memorydrill.R.id.tvMasterMindFragment);
            tvMemorizeInfo= (AutoResizeTextView)getActivity().findViewById(com.memorygame.memorydrill.R.id.tvMemorizeInfo);
            tvLevelNo = (TextView)getActivity().findViewById(com.memorygame.memorydrill.R.id.tvLevelNo);

            numNewImgs = 1;

            Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/papyrus.ttf");
            tvMasterMind.setTypeface(face);

            face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/digital.ttf");
            textViewTime.setTypeface(face);

            face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/chiller.ttf");
            tvMemorizeInfo.setTypeface(face);

            face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/forte.ttf");
            tvLevelNo.setTypeface(face);

            if(imgs == null){

                imgs = getResources().obtainTypedArray(com.memorygame.memorydrill.R.array.image_ids);
            }
            setNumbers();

            columnWidth = WIDTH_ONE;


            btnContinue = (Button) getActivity().findViewById(com.memorygame.memorydrill.R.id.btncontinue);
            face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/mvboli.ttf");
            btnContinue.setTypeface(face);
            btnContinue.setTextSize(20);

            gridView = (GridView) getActivity().findViewById(com.memorygame.memorydrill.R.id.gridView);
            saveFlag = true;

            if(gridAdapter != null) {
                gridAdapter = null;
            }

            gridItems = getImages();
            tempGridItems = setBrainImage();

            gridAdapter = new GridViewAdapter(getActivity(), com.memorygame.memorydrill.R.layout.layout_grid_item, gridItems);
            gridView.setAdapter(gridAdapter);
            gridView.setNumColumns(numColumns);
            gridView.setColumnWidth(columnWidth);

            btnContinue.setOnClickListener(this);

            }

            @Override
            public void onStart() {
                super.onStart();

                if(saveFlag == true)
                {
                    setTimer();

                }

            }

    public  ArrayList<GridImageView> setBrainImage(){
        // Prepare some dummy data for gridview

            Bitmap bitmap= BitmapFactory.decodeResource(getResources(), com.memorygame.memorydrill.R.drawable.brain);

        final ArrayList<GridImageView> imageItems = new ArrayList<>();

            for (int i = 0; i < numImgs; i++) {

                imageItems.add(new GridImageView(bitmap, null, com.memorygame.memorydrill.R.drawable.brain));

            }
            return imageItems;
        }

            @Override
            public void onPause() {
                super.onPause();

                timer.cancel();

                if(flagChangeGridImages == false)
                {
                    tempGridAdapter = gridAdapter;
                }

                if (gridAdapter != null) {
                    gridAdapter = null;
                }
                if(gridView != null) {

                    gridAdapter = new GridViewAdapter(getActivity(), com.memorygame.memorydrill.R.layout.layout_grid_item, tempGridItems);
                    flagChangeGridImages = true;
                    gridView.setAdapter(gridAdapter);

                }

    }


    @Override
    public void onClick(View v) {

        String text = "Identify the new image by tapping on it...";
        tvMemorizeInfo.setText(text);

        btnContinue.setVisibility(View.GONE);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId((Integer) numbers.get(numImgs), -1));

        if (imageNew != null) {
            imageNew = null;
        }
        imageNew = new GridImageView(bitmap, null, imgs.getResourceId((Integer) numbers.get(numImgs), -1));

        newPos = (Integer) numbers.get(numImgs);
        numbers.set(numImgs - 1, numbers.get(numImgs));
        numbers.remove(numImgs);

        Collections.shuffle(numbers);

        if (gridAdapter != null) {
            gridAdapter = null;
        }

        if(gridItems!= null){
            gridItems = null;
        }
        gridItems = getImages();
        gridAdapter = new GridViewAdapter(getActivity(), com.memorygame.memorydrill.R.layout.layout_grid_item, gridItems);
        gridView.setAdapter(gridAdapter);
        gridView.setNumColumns(numColumns);
        gridView.setColumnWidth(columnWidth);
        gridView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        timer.cancel();
        timeRemaining = (calcTime - millis) / 1000 + 1;
        GridImageView item = (GridImageView) parent.getItemAtPosition(position);
        mListener.onImageSelected(newPos, (Integer) numbers.get(position), timeRemaining, item.getId(), imageNew.getId(), level);

    }

    private void setNumbers() {

        if(numbers == null){
            numbers = new ArrayList();
            while (numbers.size() < numImgs + numNewImgs) {
                int random = getRandomNumberInRange(0, imgs.length() - 1); //this is your method to return a random int

                if (!numbers.contains(random))
                    numbers.add(random);
            }
        }


    }

    // Prepare some dummy data for gridview
    private ArrayList<GridImageView> getImages() {
        Bitmap bitmap = null;
        final ArrayList<GridImageView> imageItems = new ArrayList<>();

        for (int i = 0; i < numImgs; i++) {

            bitmap= BitmapFactory.decodeResource(getResources(), imgs.getResourceId((Integer) numbers.get(i), -1));
            imageItems.add(new GridImageView(bitmap, null, imgs.getResourceId((Integer) numbers.get(i), -1)));

        }
        return imageItems;
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        return (int) (Math.random() * ((max - min) + 1)) + min;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        saveFlag = true;
        getActivity().getIntent().removeExtra("timerValue");
        getActivity().getIntent().removeExtra("timerStageText");


        getArguments().remove("LEVEL");
        tvMasterMind = null;
         gridAdapter = null;
         btnContinue = null;
         numbers = null;
         imgs = null;
         timer = null;
         args = null;
         imageNew= null;
         mListener= null;
        gridView = null;
         textViewTime = null;
        tvMemorizeInfo = null;
        tvLevelNo=null;

        textviewText = null;
         gridItems = null;
         tempGridItems = null;
    }

    public class CounterClass extends CountDownTimer {

        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onTick(long millisUntilFinished) {
            // TODO Auto-generated method stub

            millis = millisUntilFinished;
            String hms = String.format("%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(millis),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

            textViewTime.setText(hms);


        }

        @Override
        public void onFinish() {
            // TODO Auto-generated method stub
            textViewTime.setText("00:00");
            mListener.timerFinished(level);


        }
    }

}





