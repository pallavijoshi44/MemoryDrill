package com.memorygame.memorydrill;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentTransaction;

import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * Created by aspire on 04-07-2016.
 */
public class MainActivity extends AppCompatActivity
        implements SelectLevelFragment.SelectLevelFragmentListener, LevelsFragment.LevelsFragmentListener,
         HelpFragment.HelpFragmentListener,
        LevelInfoFragment.LevelInfoFragmentListener{

    Dialog dialog ;
    TextView textOops = null;
    TextView textAlert = null;
    TextView textLevelFailed = null;
    TextView textLevelCleared = null;
    LevelsFragment levelsfragment = null;
    LevelInfoFragment levelInfoFragment = null;
    SelectLevelFragment selectLevelFragment = null;
    Boolean flagDoNotShowPause =false;

    private static int LEVELS_FRAGMENT_SHOWN =0;
    private static int SELECT_LEVELS_FRAGMENT_SHOWN =1;
    private static int HELP_FRAGMENT_SHOWN =2;
    private static int LEVEL_INFO_FRAGMENT_SHOWN=3;

    public final static int STAGE_ONE_NUM_COLUMNS = 2;
    public final static int STAGE_TWO_NUM_COLUMNS = 3;
    public final static int STAGE_THREE_NUM_COLUMNS = 3;
    public final static int STAGE_FOUR_NUM_COLUMNS = 4;
    public final static int STAGE_ONE_NUM_IMGS = 4;
    public final static int STAGE_TWO_NUM_IMGS = 6;
    public final static int STAGE_THREE_NUM_IMGS = 9;
    public final static int STAGE_FOUR_NUM_IMGS = 12;

    public final static String TIMER_TEXT_STAGE_ONE = "01:00";
    public final static String TIMER_TEXT_STAGE_TWO = "00:45";
    public final static String TIMER_TEXT_STAGE_THREE = "00:30";
    public final static String TIMER_TEXT_STAGE_FOUR = "00:15";

    public final static long TIMER_ONE = 60000;
    public final static long TIMER_TWO = 45000;
    public final static long TIMER_THREE = 30000;
    public final static long TIMER_FOUR = 15000;
    public final static String TIME_ALLOWED_ONE = "60 seconds";
    public final static String TIME_ALLOWED_TWO = "45 seconds";
    public final static String TIME_ALLOWED_THREE= "30 seconds";
    public final static String TIME_ALLOWED_FOUR = "15 seconds";

    public final static int STAGE_ONE = 1;
    public final static int STAGE_TWO = 2;
    public final static int STAGE_THREE = 3;
    public final static int STAGE_FOUR = 4;

    boolean flagShowAnimOnBack;

    boolean flagBadgeEarned;
    long calcTime;
    int numImgs;
    int numColumns;
    String textviewText;
    String timeAllowed;
    int stage;

    private static int fragmentShown;
    HelpFragment helpFragment = null;

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

   TextView taskToDoOne;
    TextView taskToDoTwo;
    LinearLayout llToDoOne;
    LinearLayout llToDoSecond;
    int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(com.memorygame.memorydrill.R.layout.activity_main);

        flagBadgeEarned = false;
        flagShowAnimOnBack = false;
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        level = preferences.getInt("Level", 1);

        if(!isFirstTime()){

            if(helpFragment != null){
                helpFragment = null;
            }
            helpFragment = new HelpFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(com.memorygame.memorydrill.R.id.main_container, helpFragment, "HELP_FRAGMENT");
            transaction.commit();
            fragmentShown = HELP_FRAGMENT_SHOWN;
        }
        else{
            if(selectLevelFragment != null){
                selectLevelFragment = null;
            }
            selectLevelFragment = new SelectLevelFragment();
            Bundle args = new Bundle();
            args.putInt("Level", level);

            selectLevelFragment.setArguments(args);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(com.memorygame.memorydrill.R.id.main_container, selectLevelFragment, "SELECT_LEVEL_FRAGMENT");
            transaction.commit();
            fragmentShown = SELECT_LEVELS_FRAGMENT_SHOWN;
        }


    }

    @Override
    public void onBtnSelected(int level) {

        if(level == 10000){
            if(helpFragment != null){
                helpFragment = null;
            }
            helpFragment = new HelpFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(com.memorygame.memorydrill.R.id.main_container, helpFragment, "HELP_FRAGMENT");
            transaction.commit();
            fragmentShown = HELP_FRAGMENT_SHOWN;
        }
        else {
            if(levelInfoFragment != null) {
                levelInfoFragment = null;
            }
            levelInfoFragment = new LevelInfoFragment();

            setStageLevelImages(level);

            Bundle args = new Bundle();
            args.putInt("Level", level);
            args.putInt("Stage", stage);
            args.putString("TimeAllowed", timeAllowed);
            args.putInt("NumImages", numImgs);


            levelInfoFragment.setArguments(args);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(com.memorygame.memorydrill.R.anim.enter_from_right, com.memorygame.memorydrill.R.anim.exit_to_left);
            transaction.replace(com.memorygame.memorydrill.R.id.main_container, levelInfoFragment, "LEVEL_INFO_FRAGMENT");
            transaction.commit();
            fragmentShown = LEVEL_INFO_FRAGMENT_SHOWN;
        }
    }

    @Override
    public void onImageSelected(int newPos, int position, long timeRemaining, int itemPickedId, int newItemId, final int level){
        //Create intent
        if(this.dialog != null){
            if(dialog.isShowing())
                dialog.dismiss();
            this.dialog = null;
        }
        setDialogForGame();

        if (itemPickedId  == newItemId) {
            LinearLayout ll_next;
            LinearLayout ll_home;
            LinearLayout ll_home_temp;

            if ((level == 4) || level == 8 || level == 12 || level == 16) {

                flagBadgeEarned = true;
                showScreenDialogs(com.memorygame.memorydrill.R.layout.layout_check_image_correct_tag_earned, com.memorygame.memorydrill.R.id.tvNext, com.memorygame.memorydrill.R.id.tvHome, com.memorygame.memorydrill.R.id.ll_next, com.memorygame.memorydrill.R.id.ll_home, "Next", "Home", null, level);
                ImageView ivTag = (ImageView) dialog.findViewById(com.memorygame.memorydrill.R.id.ivTagEarned);

                 if(level == 4) {
                     ivTag.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.badge_icon_level_4, null));

                    // ivTag.setImageDrawable(getResources().getDrawable(com.memorygame.memorydrill.R.drawable.badge_icon_level_4, getTheme()));

                 }
                else if(level == 8){
                     ivTag.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.badge_icon_level_8, null));

                    // ivTag.setImageDrawable(getResources().getDrawable(com.memorygame.memorydrill.R.drawable.badge_icon_level_8, getTheme()));
                }
                else if(level == 12){
                     ivTag.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.badge_icon_level_12, null));

                   //  ivTag.setImageDrawable(getResources().getDrawable(com.memorygame.memorydrill.R.drawable.badge_icon_level_12, getTheme()));
                }
                else if(level == 16){
                     ivTag.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.badge_icon_level_16, null));

                  //   ivTag.setImageDrawable(getResources().getDrawable(com.memorygame.memorydrill.R.drawable.badge_icon_level_16, getTheme()));
                }


            TextView tagEarned = (TextView) dialog.findViewById(com.memorygame.memorydrill.R.id.textTagEarned);

            Typeface face = Typeface.createFromAsset(getAssets(), "fonts/chiller.ttf");
            tagEarned.setTypeface(face);

            }
           else{
                showScreenDialogs(com.memorygame.memorydrill.R.layout.layout_check_image_correct, com.memorygame.memorydrill.R.id.tvNext, com.memorygame.memorydrill.R.id.tvHome, com.memorygame.memorydrill.R.id.ll_next, com.memorygame.memorydrill.R.id.ll_home, "Next", "Home", null, level);
            }

                textLevelCleared = (TextView) dialog.findViewById(com.memorygame.memorydrill.R.id.textLevelCleared);

                Typeface face = Typeface.createFromAsset(getAssets(), "fonts/chiller.ttf");
                textLevelCleared.setTypeface(face);

                 textLevelCleared.setText("Level " + level + " cleared");
                 SharedPreferences preferences = getPreferences(Activity.MODE_PRIVATE);
                 SharedPreferences.Editor editor = preferences.edit();

            if(level == 16){
                ll_next = (LinearLayout) dialog.findViewById(R.id.ll_next);
                ll_next.setVisibility(View.GONE);
                ll_home = (LinearLayout)dialog.findViewById(R.id.ll_home);
                ll_home.setVisibility(View.GONE);

                ll_home_temp = (LinearLayout)dialog.findViewById(R.id.ll_home_temp);
                ll_home_temp.setVisibility(View.VISIBLE);
                ll_home_temp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog != null) {
                            if (dialog.isShowing())
                                dialog.dismiss();
                            dialog = null;
                            onButtonsSelected("Home", level);
                        }
                    }
                });
            }

            if(level >= preferences.getInt("Level",1)){
                editor.putInt("Level", level+1);
                editor.commit();
            }

        }
        else{
            TypedArray imgs = getResources().obtainTypedArray(com.memorygame.memorydrill.R.array.image_ids);

            showScreenDialogs(com.memorygame.memorydrill.R.layout.layout_check_image_incorrect, com.memorygame.memorydrill.R.id.tvRetry, com.memorygame.memorydrill.R.id.tvHome, com.memorygame.memorydrill.R.id.ll_retry, com.memorygame.memorydrill.R.id.ll_home, "Retry", "Home", null, level);

            textAlert = (TextView) dialog.findViewById(com.memorygame.memorydrill.R.id.textAlert);
            textLevelFailed = (TextView) dialog.findViewById(com.memorygame.memorydrill.R.id.textLevelFailed);

            ImageView imageViewNew = (ImageView) dialog.findViewById(com.memorygame.memorydrill.R.id.imageAlert);

            Bitmap bitmapP = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(newPos, -1));
            imageViewNew.setImageBitmap(bitmapP);

          Typeface  face = Typeface.createFromAsset(getAssets(), "fonts/chiller.ttf");
            textAlert.setTypeface(face);

            face = Typeface.createFromAsset(getAssets(), "fonts/chiller.ttf");
            textLevelFailed.setTypeface(face);

        }

    }

    @Override
    public void timerFinished(final int level) {

        showScreenDialogs(com.memorygame.memorydrill.R.layout.custom_alert, com.memorygame.memorydrill.R.id.tvRetry, com.memorygame.memorydrill.R.id.tvHome, com.memorygame.memorydrill.R.id.ll_retry, com.memorygame.memorydrill.R.id.ll_home, "Retry", "Home", null, level);

        textAlert = (TextView) dialog.findViewById(com.memorygame.memorydrill.R.id.textAlert);
        textLevelFailed = (TextView) dialog.findViewById(com.memorygame.memorydrill.R.id.textLevelFailed);

        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/chiller.ttf");
        textAlert.setTypeface(face);

        face = Typeface.createFromAsset(getAssets(), "fonts/chiller.ttf");
        textLevelFailed.setTypeface(face);
    }

    public void setDialogForGame(){

        Dialog dialog = new Dialog(this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                // Prevent dialog close on back press button
                return keyCode == KeyEvent.KEYCODE_BACK;
            }
        });
            setDialog(dialog);


    }
    public void showScreenDialogs(int layoutId, int tvToDoIdOne, int tvToDoIdTwo, int llToDoIdOne, int llToDoIdTwo, final String taskOne, final String taskTwo, final String calledFrom, final int level){

        if(this.dialog != null){
            if(dialog.isShowing())
                dialog.dismiss();
            this.dialog = null;
        }
        setDialogForGame();
        dialog.setContentView(layoutId);

        textOops = (TextView) dialog.findViewById(com.memorygame.memorydrill.R.id.textOops);

        taskToDoOne = (TextView) dialog.findViewById(tvToDoIdOne);
        taskToDoTwo = (TextView) dialog.findViewById(tvToDoIdTwo);

        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/forte.ttf");
        textOops.setTypeface(face);

        face = Typeface.createFromAsset(getAssets(), "fonts/mvboli.ttf");
        taskToDoOne.setTypeface(face);

        face = Typeface.createFromAsset(getAssets(), "fonts/mvboli.ttf");
        taskToDoTwo.setTypeface(face);

        SharedPreferences preferences = getPreferences(Activity.MODE_PRIVATE);
        final int globalLevel = preferences.getInt("Level",1);

        llToDoOne = (LinearLayout) dialog.findViewById(llToDoIdOne);
        llToDoSecond = (LinearLayout) dialog.findViewById(llToDoIdTwo);

        llToDoOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(taskOne.equals("Resume") && calledFrom.equals("Select")) {
                    if(dialog != null){
                        if(dialog.isShowing())
                            dialog.dismiss();
                        dialog = null;
                    }

                }
                else if(taskOne.equals("Resume") && calledFrom.equals("Pause")){
                    if(dialog != null){
                        if(dialog.isShowing())
                            dialog.dismiss();
                        dialog = null;
                    }

                    if(levelsfragment != null){
                        levelsfragment.setState(true);
                        levelsfragment.setSaveflg(false);
                        levelsfragment.onResume();
                    }
                }
                else if(taskOne.equals("Retry") ) {
                    if(dialog != null){
                        if(dialog.isShowing())
                            dialog.dismiss();
                        dialog = null;
                        onButtonsSelected("Retry", level);
                    }

                }
                else if(taskOne.equals("Next") ) {
                    if(dialog != null){
                        if(dialog.isShowing())
                            dialog.dismiss();
                        dialog = null;
                        onButtonsSelected("Next", level+1);
                    }

                }


            }
        });
        llToDoSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (taskTwo.equals("Exit")) {
                    if (dialog != null) {
                        if(dialog.isShowing())
                            dialog.dismiss();
                        dialog = null;
                    }
                    finish();

                }
                if(taskTwo.equals("Home")){
                    if(dialog != null) {
                        if(dialog.isShowing())
                            dialog.dismiss();
                        dialog = null;
                        onButtonsSelected("Home", globalLevel);
                    }
                }
            }
        });


        dialog.show();
    }

    public void pauseGameOnPause(){

        showScreenDialogs(com.memorygame.memorydrill.R.layout.layout_check_image_pause, com.memorygame.memorydrill.R.id.tvResume, com.memorygame.memorydrill.R.id.tvHome, com.memorygame.memorydrill.R.id.ll_resume, com.memorygame.memorydrill.R.id.ll_home,"Resume", "Home", "Pause", level);
        flagDoNotShowPause = false;

    }

    @Override
    public void onPause(){
        super.onPause();


        if(fragmentShown == LEVELS_FRAGMENT_SHOWN){
            if (this.dialog != null) {

                if (this.dialog.isShowing()) {
                    //do nothing
                }
                else{
                    if(flagDoNotShowPause == false) {
                        pauseGameOnPause();
                    }
                }

            }else {
                if(flagDoNotShowPause == false){
                     pauseGameOnPause();
                }

            }
        }

    }
@Override
public void onResume() {
    super.onResume();
   if(this.dialog!=null) {
       if (this.dialog.isShowing()) {
           if(levelsfragment !=null)
             levelsfragment.setState(false);

       }

   }

}

    public void onButtonsSelected(String task, int level) {

        if(task.equals("Next") || task.equals("Retry"))
        {

            FrameLayout f = (FrameLayout)findViewById(com.memorygame.memorydrill.R.id.main_container);
            f.removeAllViewsInLayout();

            if(levelInfoFragment != null) {
                levelInfoFragment = null;
            }
            levelInfoFragment = new LevelInfoFragment();

            setStageLevelImages(level);

            Bundle args = new Bundle();
            args.putInt("Level", level);
            args.putInt("Stage", stage);
            args.putString("TimeAllowed", timeAllowed);
            args.putInt("NumImages", numImgs);


            levelInfoFragment.setArguments(args);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(com.memorygame.memorydrill.R.id.main_container, levelInfoFragment, "LEVEL_INFO_FRAGMENT");
            transaction.commit();
            fragmentShown = LEVEL_INFO_FRAGMENT_SHOWN;


        }
        else if(task.equals("Home")){

            FrameLayout f = (FrameLayout)findViewById(com.memorygame.memorydrill.R.id.main_container);
            f.removeAllViewsInLayout();

            SharedPreferences preferences = getPreferences(MODE_PRIVATE);
            int globalLevel = preferences.getInt("Level", 1);

            if(selectLevelFragment != null){
                selectLevelFragment = null;
            }
            selectLevelFragment = new SelectLevelFragment();
            Bundle args = new Bundle();
            args.putInt("Level", globalLevel);

            selectLevelFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(com.memorygame.memorydrill.R.id.main_container, selectLevelFragment,"SELECT_LEVEL_FRAGMENT");
            transaction.commit();
            fragmentShown = SELECT_LEVELS_FRAGMENT_SHOWN;


        }

        if(flagBadgeEarned == true) {
            flagBadgeEarned = false;
        }
    }
    private boolean isFirstTime()
    {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore", false);
        if (!ranBefore) {

            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.commit();


        }
        return ranBefore;

    }

    @Override
    public void onStop() {
        super.onStop();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();


        FrameLayout f = (FrameLayout)findViewById(com.memorygame.memorydrill.R.id.main_container);
        f.removeAllViewsInLayout();
        dialog = null;
        textOops = null;
        textAlert = null;
        textLevelFailed = null;
        textLevelCleared = null;
        levelsfragment = null;
        levelInfoFragment = null;
        selectLevelFragment = null;

    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        if(selectLevelFragment != null && fragmentShown == SELECT_LEVELS_FRAGMENT_SHOWN){
            flagDoNotShowPause = true;

           showScreenDialogs(com.memorygame.memorydrill.R.layout.layout_select_level_alert, com.memorygame.memorydrill.R.id.tvResume, com.memorygame.memorydrill.R.id.tvExit, com.memorygame.memorydrill.R.id.ll_resume, com.memorygame.memorydrill.R.id.ll_exit, "Resume", "Exit", "Select" , level);
        }
        else if(levelsfragment !=null && fragmentShown == LEVELS_FRAGMENT_SHOWN) {
            flagDoNotShowPause = true;

            if (this.dialog != null) {

                        if (this.dialog.isShowing()) {
                            levelsfragment.setState(false);
                            levelsfragment.setIntent();
                            levelsfragment.onPause();
                        }
                        else{
                            pauseGameOnPause();
                            levelsfragment.setState(false);
                            levelsfragment.setIntent();
                            levelsfragment.onPause();
                        }

                    }else {
                        pauseGameOnPause();

                        levelsfragment.setState(false);
                        levelsfragment.setIntent();
                        levelsfragment.onPause();
                    }
                }
        else if(helpFragment != null && fragmentShown == HELP_FRAGMENT_SHOWN){
            flagDoNotShowPause = true;
        }
        else if(levelInfoFragment != null && fragmentShown == LEVEL_INFO_FRAGMENT_SHOWN){
            flagDoNotShowPause = true;
            flagShowAnimOnBack = true;
           // showScreenDialogs(com.memorygame.memorydrill.R.layout.layout_select_level_alert, com.memorygame.memorydrill.R.id.tvResume, com.memorygame.memorydrill.R.id.tvExit, com.memorygame.memorydrill.R.id.ll_resume, com.memorygame.memorydrill.R.id.ll_exit, "Resume", "Exit", "Select" , level);
destroyFragment();
        }
    }

    @Override
    public void destroyFragment() {
        if(selectLevelFragment != null){
            selectLevelFragment = null;
        }
        selectLevelFragment = new SelectLevelFragment();

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        int globalLevel = preferences.getInt("Level", 1);

        Bundle args = new Bundle();
        args.putInt("Level", globalLevel);

        selectLevelFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(flagShowAnimOnBack == true){
            transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
            flagShowAnimOnBack = false;

        }
        transaction.replace(com.memorygame.memorydrill.R.id.main_container, selectLevelFragment, "SELECT_LEVEL_FRAGMENT");
        transaction.commit();
        fragmentShown = SELECT_LEVELS_FRAGMENT_SHOWN;
    }

    @Override
    public void startLevel(int level) {
        if (levelsfragment != null)
            levelsfragment = null;

        levelsfragment = new LevelsFragment();

        Bundle args = new Bundle();
        args.putInt(LevelsFragment.LEVEL, level);
        args.putInt("NumColumns", numColumns);
        args.putInt("NumImgs", numImgs);
        levelsfragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(com.memorygame.memorydrill.R.anim.enter_from_right, com.memorygame.memorydrill.R.anim.exit_to_left);
        transaction.replace(com.memorygame.memorydrill.R.id.main_container, levelsfragment, "LEVELS_FRAGMENT");
        transaction.commit();
        fragmentShown = LEVELS_FRAGMENT_SHOWN;
    }

    public void setStageLevelImages(int level){
        if (level == 1) {
            numColumns = STAGE_ONE_NUM_COLUMNS;
            numImgs = STAGE_ONE_NUM_IMGS;
            calcTime = TIMER_ONE;
                    textviewText = TIMER_TEXT_STAGE_ONE;
            timeAllowed = TIME_ALLOWED_ONE;
            stage = STAGE_ONE;

        } else if (level == 2) {
            numColumns = STAGE_ONE_NUM_COLUMNS;
            numImgs = STAGE_ONE_NUM_IMGS;
                    calcTime = TIMER_TWO;
            timeAllowed = TIME_ALLOWED_TWO;

            textviewText = TIMER_TEXT_STAGE_TWO;
            stage = STAGE_ONE;


        } else if (level == 3) {
            numColumns = STAGE_ONE_NUM_COLUMNS;
            numImgs = STAGE_ONE_NUM_IMGS;
                    calcTime = TIMER_THREE;
            timeAllowed = TIME_ALLOWED_THREE;

            textviewText = TIMER_TEXT_STAGE_THREE;
            stage = STAGE_ONE;

        } else if (level == 4) {

            numColumns = STAGE_ONE_NUM_COLUMNS;
            numImgs = STAGE_ONE_NUM_IMGS;
                   calcTime = TIMER_FOUR;
            timeAllowed = TIME_ALLOWED_FOUR;

            textviewText = TIMER_TEXT_STAGE_FOUR;
            stage = STAGE_ONE;

        } else if (level == 5) {

            numColumns = STAGE_TWO_NUM_COLUMNS;
            numImgs = STAGE_TWO_NUM_IMGS;
                   calcTime = TIMER_ONE;
            timeAllowed = TIME_ALLOWED_ONE;

            textviewText = TIMER_TEXT_STAGE_ONE;
            stage = STAGE_TWO;


        } else if (level == 6) {

            numColumns = STAGE_TWO_NUM_COLUMNS;
            numImgs = STAGE_TWO_NUM_IMGS;
                   calcTime = TIMER_TWO;
            timeAllowed = TIME_ALLOWED_TWO;

            textviewText = TIMER_TEXT_STAGE_TWO;
            stage = STAGE_TWO;


        } else if (level == 7) {

            numColumns = STAGE_TWO_NUM_COLUMNS;
            numImgs = STAGE_TWO_NUM_IMGS;
                    calcTime = TIMER_THREE;
            timeAllowed = TIME_ALLOWED_THREE;

            textviewText = TIMER_TEXT_STAGE_THREE;
            stage = STAGE_TWO;

        } else if (level == 8) {

            numColumns = STAGE_TWO_NUM_COLUMNS;
            numImgs = STAGE_TWO_NUM_IMGS;
                  calcTime = TIMER_FOUR;
            timeAllowed = TIME_ALLOWED_FOUR;

            textviewText = TIMER_TEXT_STAGE_FOUR;
            stage = STAGE_TWO;

        } else if (level == 9) {

            numColumns = STAGE_THREE_NUM_COLUMNS;
            numImgs = STAGE_THREE_NUM_IMGS;
                   calcTime = TIMER_ONE;
            timeAllowed = TIME_ALLOWED_ONE;

            textviewText = TIMER_TEXT_STAGE_ONE;
            stage = STAGE_THREE;

        } else if (level == 10) {

            numColumns = STAGE_THREE_NUM_COLUMNS;
            numImgs = STAGE_THREE_NUM_IMGS;
                   calcTime = TIMER_TWO;
            timeAllowed = TIME_ALLOWED_TWO;

            textviewText = TIMER_TEXT_STAGE_TWO;
            stage = STAGE_THREE;

        } else if (level == 11) {

            numColumns = STAGE_THREE_NUM_COLUMNS;
            numImgs = STAGE_THREE_NUM_IMGS;
                   calcTime = TIMER_THREE;
            timeAllowed = TIME_ALLOWED_THREE;

            textviewText = TIMER_TEXT_STAGE_THREE;
            stage = STAGE_THREE;

        } else if (level == 12) {

            numColumns = STAGE_THREE_NUM_COLUMNS;
            numImgs = STAGE_THREE_NUM_IMGS;
                  calcTime = TIMER_FOUR;
            timeAllowed = TIME_ALLOWED_FOUR;

            textviewText = TIMER_TEXT_STAGE_FOUR;
            stage = STAGE_THREE;

        } else if (level == 13) {

            numColumns = STAGE_FOUR_NUM_COLUMNS;
            numImgs = STAGE_FOUR_NUM_IMGS;
                    calcTime = TIMER_ONE;
            timeAllowed = TIME_ALLOWED_ONE;

            textviewText = TIMER_TEXT_STAGE_ONE;
            stage = STAGE_FOUR;


        } else if (level == 14) {
            numColumns = STAGE_FOUR_NUM_COLUMNS;
            numImgs = STAGE_FOUR_NUM_IMGS;
            calcTime = TIMER_TWO;
            timeAllowed = TIME_ALLOWED_TWO;

            textviewText = TIMER_TEXT_STAGE_TWO;
            stage = STAGE_FOUR;

        } else if (level == 15) {

            numColumns = STAGE_FOUR_NUM_COLUMNS;
            numImgs = STAGE_FOUR_NUM_IMGS;
                    calcTime = TIMER_THREE;
            timeAllowed = TIME_ALLOWED_THREE;

            textviewText = TIMER_TEXT_STAGE_THREE;
            stage = STAGE_FOUR;

        } else if (level == 16) {
            numColumns = STAGE_FOUR_NUM_COLUMNS;
            numImgs = STAGE_FOUR_NUM_IMGS;
             calcTime = TIMER_FOUR;
            timeAllowed = TIME_ALLOWED_FOUR;

            textviewText = TIMER_TEXT_STAGE_FOUR;

            stage = STAGE_FOUR;

        }

    }
}
