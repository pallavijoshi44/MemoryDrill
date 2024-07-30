package com.memorygame.memorydrill;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

/**
 * Created by aspire on 04-07-2016.
 */
public class LevelInfoFragment extends Fragment implements View.OnClickListener {

    private Button btnClose;
    private TextView tvMasterMindFragment;
    private TextView tvTextAlert;
    private TextView textLevelCheck;
    private TextView tvTap;
    private TextView textOops;
    private TextView imageAlert;
    Bundle args;
    int level;
    int stage;
    String timeAllowed;
    int numImages;
    private RelativeLayout llLevelInfo;
    LevelInfoFragmentListener levelInfoFragmentListener;


    public LevelInfoFragment() {
    }

    public interface LevelInfoFragmentListener{
        void startLevel(int level);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(com.memorygame.memorydrill.R.layout.layout_level_info, container, false);

        return  rootView;
    }
        @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            levelInfoFragmentListener = (LevelInfoFragmentListener) getActivity();

            args = getArguments();

            if(args != null){
                level = args.getInt("Level");
                stage = args.getInt("Stage");
                timeAllowed = args.getString("TimeAllowed");
                numImages = args.getInt("NumImages");

            }
            tvMasterMindFragment = (TextView) getActivity().findViewById(com.memorygame.memorydrill.R.id.tvMasterMindFragment);
            textLevelCheck = (TextView) getActivity().findViewById(com.memorygame.memorydrill.R.id.textLevelCheck);
            tvTextAlert = (TextView) getActivity().findViewById(com.memorygame.memorydrill.R.id.textAlert);
            tvTap = (TextView)getActivity().findViewById(com.memorygame.memorydrill.R.id.tvTap);
            llLevelInfo = (RelativeLayout)getActivity().findViewById(com.memorygame.memorydrill.R.id.rlLevelInfo);
            textOops = (TextView)getActivity().findViewById(com.memorygame.memorydrill.R.id.textOops);
            textOops.setText("Level " + level);
            imageAlert = (TextView) getActivity().findViewById(com.memorygame.memorydrill.R.id.imageAlert);
            // tvNext = (TextView) dialog.findViewById(R.id.tvNext);
            // tvHome = (TextView) dialog.findViewById(R.id.tvHome);

             Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/forte.ttf");
             textOops.setTypeface(face);

             face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/chiller.ttf");
            tvTextAlert.setTypeface(face);

            face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/chiller.ttf");
            textLevelCheck.setTypeface(face);

            face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/forte.ttf");
            imageAlert.setTypeface(face);

            face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/papyrus.ttf");
            tvMasterMindFragment.setTypeface(face);

            face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/mvboli.ttf");
            tvTap.setTypeface(face);

            tvTap.setVisibility(View.INVISIBLE);
            tvTap.postDelayed(new Runnable() {
                public void run() {
                    tvTap.setVisibility(View.VISIBLE);
                    llLevelInfo.setOnClickListener(LevelInfoFragment.this);
                }
            }, 1500);

            String numOfImages = numImages + " images";
            String stageText =  "Stage " + stage;

            String text = "\n".concat(numOfImages).concat("\n").concat(timeAllowed);

            imageAlert.setText(text);
        }


    /*
    public Bitmap drawTextToBitmap(Context mContext,  int resourceId,  String numImages, String timeAllowed, String stageText, int angle, int value) {
        try {
            Resources resources = mContext.getResources();
            float scale = resources.getDisplayMetrics().density;
            Bitmap bitmap = BitmapFactory.decodeResource(resources, resourceId);

            android.graphics.Bitmap.Config bitmapConfig =   bitmap.getConfig();
            // set default bitmap config if none
            if(bitmapConfig == null) {
                bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
            }
            // resource bitmaps are imutable,
            // so we need to convert it to mutable one
            bitmap = bitmap.copy(bitmapConfig, true);

            Canvas canvas = new Canvas(bitmap);
            // new antialised Paint
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            // text color - #3D3D3D
            paint.setColor(Color.rgb(250, 250, 250));
            // text size in pixels
            paint.setTextSize((int) (50 * scale));

            // text shadow
            paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);

            String text = numImages;
            // draw text to the Canvas center
            Rect bounds = new Rect();
            paint.getTextBounds(text, 0, text.length(), bounds);
            int x = 0, y=0;

                 x = (int)((bitmap.getWidth() - bounds.width()) / 6 + 20*scale);
                 y = (int)((bitmap.getHeight() + bounds.height())/5 - 50*scale);
                canvas.rotate(-15, x * scale, y * scale);

                canvas.drawText(text, x * scale, y * scale, paint);

            canvas.rotate(15, x * scale, y * scale);


            text = timeAllowed;

                 x = (int)((bitmap.getWidth() - bounds.width()) / 6 + 25*scale);
            y = (int)((bitmap.getHeight() + bounds.height())/5 - 5*scale);
            canvas.rotate(-15, x * scale, y * scale);

            canvas.drawText(text, x * scale, y * scale, paint);
            canvas.rotate(15, x * scale, y * scale);


            text = stageText;
                 x = (int)((bitmap.getWidth() - bounds.width()) /6 + 40*scale);
                 y = (int)((bitmap.getHeight() + bounds.height())/5 + 28*scale);
                canvas.rotate(-15, x * scale, y * scale);

                canvas.drawText(text, x * scale, y * scale, paint);



            return bitmap;
        } catch (Exception e) {
            // TODO: handle exception



            return null;
        }

    }*/
    @Override
    public void onStart(){
        super.onStart();


    }


    @Override
    public void onClick(View v) {

        levelInfoFragmentListener.startLevel(level);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        levelInfoFragmentListener = null;
    }
}

