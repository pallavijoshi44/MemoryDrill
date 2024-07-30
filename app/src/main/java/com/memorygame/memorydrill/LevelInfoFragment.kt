package com.memorygame.memorydrill

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment

/**
 * Created by aspire on 04-07-2016.
 */
class LevelInfoFragment : Fragment(), View.OnClickListener {
    private val btnClose: Button? = null
    private var tvMasterMindFragment: TextView? = null
    private var tvTextAlert: TextView? = null
    private var textLevelCheck: TextView? = null
    private var tvTap: TextView? = null
    private var textOops: TextView? = null
    private var imageAlert: TextView? = null
    var args: Bundle? = null
    var level: Int = 0
    var stage: Int = 0
    var timeAllowed: String? = null
    var numImages: Int = 0
    private var llLevelInfo: RelativeLayout? = null
    var levelInfoFragmentListener: LevelInfoFragmentListener? = null


    interface LevelInfoFragmentListener {
        fun startLevel(level: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.layout_level_info, container, false)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        levelInfoFragmentListener = activity as LevelInfoFragmentListener?

        args = arguments

        if (args != null) {
            level = args!!.getInt("Level")
            stage = args!!.getInt("Stage")
            timeAllowed = args!!.getString("TimeAllowed")
            numImages = args!!.getInt("NumImages")
        }
        tvMasterMindFragment = activity!!.findViewById<View>(R.id.tvMasterMindFragment) as TextView
        textLevelCheck = activity!!.findViewById<View>(R.id.textLevelCheck) as TextView
        tvTextAlert = activity!!.findViewById<View>(R.id.textAlert) as TextView
        tvTap = activity!!.findViewById<View>(R.id.tvTap) as TextView
        llLevelInfo = activity!!.findViewById<View>(R.id.rlLevelInfo) as RelativeLayout
        textOops = activity!!.findViewById<View>(R.id.textOops) as TextView
        textOops!!.text = "Level $level"
        imageAlert = activity!!.findViewById<View>(R.id.imageAlert) as TextView

        // tvNext = (TextView) dialog.findViewById(R.id.tvNext);
        // tvHome = (TextView) dialog.findViewById(R.id.tvHome);
        var face = Typeface.createFromAsset(
            activity!!.assets, "fonts/forte.ttf"
        )
        textOops!!.typeface = face

        face = Typeface.createFromAsset(activity!!.assets, "fonts/chiller.ttf")
        tvTextAlert!!.typeface = face

        face = Typeface.createFromAsset(activity!!.assets, "fonts/chiller.ttf")
        textLevelCheck!!.typeface = face

        face = Typeface.createFromAsset(activity!!.assets, "fonts/forte.ttf")
        imageAlert!!.typeface = face

        face = Typeface.createFromAsset(activity!!.assets, "fonts/papyrus.ttf")
        tvMasterMindFragment!!.typeface = face

        face = Typeface.createFromAsset(activity!!.assets, "fonts/mvboli.ttf")
        tvTap!!.typeface = face

        tvTap!!.visibility = View.INVISIBLE
        tvTap!!.postDelayed({
            tvTap!!.visibility = View.VISIBLE
            llLevelInfo!!.setOnClickListener(this@LevelInfoFragment)
        }, 1500)

        val numOfImages = "$numImages images"
        val stageText = "Stage $stage"

        val text = """
            
            $numOfImages
            $timeAllowed
            """.trimIndent()

        imageAlert!!.text = text
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
    override fun onStart() {
        super.onStart()
    }


    override fun onClick(v: View) {
        levelInfoFragmentListener!!.startLevel(level)
    }

    override fun onDestroy() {
        super.onDestroy()
        levelInfoFragmentListener = null
    }
}

