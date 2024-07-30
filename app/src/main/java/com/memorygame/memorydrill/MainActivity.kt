package com.memorygame.memorydrill

import android.app.Dialog
import android.content.pm.ActivityInfo
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.Window
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.memorygame.memorydrill.HelpFragment.HelpFragmentListener
import com.memorygame.memorydrill.LevelInfoFragment.LevelInfoFragmentListener
import com.memorygame.memorydrill.LevelsFragment.LevelsFragmentListener
import com.memorygame.memorydrill.SelectLevelFragment.SelectLevelFragmentListener

/**
 * Created by aspire on 04-07-2016.
 */
class MainActivity : AppCompatActivity(), SelectLevelFragmentListener, LevelsFragmentListener,
    HelpFragmentListener, LevelInfoFragmentListener {
    var timerDialog: Dialog? = null
    var textOops: TextView? = null
    var textAlert: TextView? = null
    var textLevelFailed: TextView? = null
    var textLevelCleared: TextView? = null
    var levelsfragment: LevelsFragment? = null
    var levelInfoFragment: LevelInfoFragment? = null
    var selectLevelFragment: SelectLevelFragment? = null
    var flagDoNotShowPause: Boolean = false

    var flagShowAnimOnBack: Boolean = false

    var flagBadgeEarned: Boolean = false
    var calcTime: Long = 0
    var numImgs: Int = 0
    var numColumns: Int = 0
    var textviewText: String? = null
    var timeAllowed: String? = null
    var stage: Int = 0

    var helpFragment: HelpFragment? = null

    fun setDialog(dialog: Dialog?) {
        this.timerDialog = dialog
    }

    var taskToDoOne: TextView? = null
    var taskToDoTwo: TextView? = null
    var llToDoOne: LinearLayout? = null
    var llToDoSecond: LinearLayout? = null
    var level: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setContentView(R.layout.activity_main)

        flagBadgeEarned = false
        flagShowAnimOnBack = false
        val preferences = getPreferences(MODE_PRIVATE)
        level = preferences.getInt("Level", 1)

        if (!isFirstTime) {
            if (helpFragment != null) {
                helpFragment = null
            }
            helpFragment = HelpFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.main_container, helpFragment!!, "HELP_FRAGMENT")
            transaction.commit()
            fragmentShown = HELP_FRAGMENT_SHOWN
        } else {
            if (selectLevelFragment != null) {
                selectLevelFragment = null
            }
            selectLevelFragment = SelectLevelFragment()
            val args = Bundle()
            args.putInt("Level", level)

            selectLevelFragment!!.arguments = args

            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.main_container, selectLevelFragment!!, "SELECT_LEVEL_FRAGMENT")
            transaction.commit()
            fragmentShown = SELECT_LEVELS_FRAGMENT_SHOWN
        }
    }

    override fun onBtnSelected(level: Int) {
        if (level == 10000) {
            if (helpFragment != null) {
                helpFragment = null
            }
            helpFragment = HelpFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.main_container, helpFragment!!, "HELP_FRAGMENT")
            transaction.commit()
            fragmentShown = HELP_FRAGMENT_SHOWN
        } else {
            if (levelInfoFragment != null) {
                levelInfoFragment = null
            }
            levelInfoFragment = LevelInfoFragment()

            setStageLevelImages(level)

            val args = Bundle()
            args.putInt("Level", level)
            args.putInt("Stage", stage)
            args.putString("TimeAllowed", timeAllowed)
            args.putInt("NumImages", numImgs)


            levelInfoFragment!!.arguments = args

            val transaction = supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
            transaction.replace(R.id.main_container, levelInfoFragment!!, "LEVEL_INFO_FRAGMENT")
            transaction.commit()
            fragmentShown = LEVEL_INFO_FRAGMENT_SHOWN
        }
    }

    override fun onImageSelected(
        newPos: Int,
        position: Int,
        timeRemaining: Long,
        itemPickedId: Int,
        newItemId: Int,
        level: Int
    ) {
        //Create intent
        if (this.timerDialog != null) {
            if (timerDialog!!.isShowing) timerDialog!!.dismiss()
            this.timerDialog = null
        }
        setDialogForGame()

        if (itemPickedId == newItemId) {
            val ll_next: LinearLayout
            val ll_home: LinearLayout
            val ll_home_temp: LinearLayout

            if ((level == 4) || (level == 8) || (level == 12) || (level == 16)) {
                flagBadgeEarned = true
                showScreenDialogs(
                    R.layout.layout_check_image_correct_tag_earned,
                    R.id.tvNext,
                    R.id.tvHome,
                    R.id.ll_next,
                    R.id.ll_home,
                    "Next",
                    "Home",
                    null,
                    level
                )
                val ivTag = timerDialog!!.findViewById<View>(R.id.ivTagEarned) as ImageView

                if (level == 4) {
                    ivTag.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.badge_icon_level_4,
                            null
                        )
                    )

                    // ivTag.setImageDrawable(getResources().getDrawable(com.memorygame.memorydrill.R.drawable.badge_icon_level_4, getTheme()));
                } else if (level == 8) {
                    ivTag.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.badge_icon_level_8,
                            null
                        )
                    )

                    // ivTag.setImageDrawable(getResources().getDrawable(com.memorygame.memorydrill.R.drawable.badge_icon_level_8, getTheme()));
                } else if (level == 12) {
                    ivTag.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.badge_icon_level_12,
                            null
                        )
                    )

                    //  ivTag.setImageDrawable(getResources().getDrawable(com.memorygame.memorydrill.R.drawable.badge_icon_level_12, getTheme()));
                } else if (level == 16) {
                    ivTag.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.badge_icon_level_16,
                            null
                        )
                    )

                    //   ivTag.setImageDrawable(getResources().getDrawable(com.memorygame.memorydrill.R.drawable.badge_icon_level_16, getTheme()));
                }


                val tagEarned = timerDialog!!.findViewById<View>(R.id.textTagEarned) as TextView

                val face = Typeface.createFromAsset(assets, "fonts/chiller.ttf")
                tagEarned.setTypeface(face)
            } else {
                showScreenDialogs(
                    R.layout.layout_check_image_correct,
                    R.id.tvNext,
                    R.id.tvHome,
                    R.id.ll_next,
                    R.id.ll_home,
                    "Next",
                    "Home",
                    null,
                    level
                )
            }

            textLevelCleared = timerDialog!!.findViewById<View>(R.id.textLevelCleared) as TextView

            val face = Typeface.createFromAsset(assets, "fonts/chiller.ttf")
            textLevelCleared!!.setTypeface(face)

            textLevelCleared!!.text = "Level $level cleared"
            val preferences = getPreferences(MODE_PRIVATE)
            val editor = preferences.edit()

            if (level == 16) {
                ll_next = timerDialog!!.findViewById<View>(R.id.ll_next) as LinearLayout
                ll_next.visibility = View.GONE
                ll_home = timerDialog!!.findViewById<View>(R.id.ll_home) as LinearLayout
                ll_home.visibility = View.GONE

                ll_home_temp = timerDialog!!.findViewById<View>(R.id.ll_home_temp) as LinearLayout
                ll_home_temp.visibility = View.VISIBLE
                ll_home_temp.setOnClickListener {
                    if (timerDialog != null) {
                        if (timerDialog?.isShowing == true) timerDialog?.dismiss()
                        timerDialog = null
                        onButtonsSelected("Home", level)
                    }
                }
            }

            if (level >= preferences.getInt("Level", 1)) {
                editor.putInt("Level", level + 1)
                editor.commit()
            }
        } else {
            val imgs = resources.obtainTypedArray(R.array.image_ids)

            showScreenDialogs(
                R.layout.layout_check_image_incorrect,
                R.id.tvRetry,
                R.id.tvHome,
                R.id.ll_retry,
                R.id.ll_home,
                "Retry",
                "Home",
                null,
                level
            )

            textAlert = timerDialog!!.findViewById<View>(R.id.textAlert) as TextView
            textLevelFailed = timerDialog!!.findViewById<View>(R.id.textLevelFailed) as TextView

            val imageViewNew = timerDialog!!.findViewById<View>(R.id.imageAlert) as ImageView

            val bitmapP = BitmapFactory.decodeResource(resources, imgs.getResourceId(newPos, -1))
            imageViewNew.setImageBitmap(bitmapP)

            var face = Typeface.createFromAsset(assets, "fonts/chiller.ttf")
            textAlert!!.setTypeface(face)

            face = Typeface.createFromAsset(assets, "fonts/chiller.ttf")
            textLevelFailed!!.setTypeface(face)
        }
    }

    override fun timerFinished(level: Int) {
        showScreenDialogs(
            R.layout.custom_alert,
            R.id.tvRetry,
            R.id.tvHome,
            R.id.ll_retry,
            R.id.ll_home,
            "Retry",
            "Home",
            null,
            level
        )

        textAlert = timerDialog!!.findViewById<View>(R.id.textAlert) as TextView
        textLevelFailed = timerDialog!!.findViewById<View>(R.id.textLevelFailed) as TextView

        var face = Typeface.createFromAsset(assets, "fonts/chiller.ttf")
        textAlert!!.typeface = face

        face = Typeface.createFromAsset(assets, "fonts/chiller.ttf")
        textLevelFailed!!.typeface = face
    }

    fun setDialogForGame() {
        val dialog = Dialog(this)
        dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(false)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)

        dialog.setOnKeyListener { dialog, keyCode, event -> // Prevent dialog close on back press button
            keyCode == KeyEvent.KEYCODE_BACK
        }
        setDialog(dialog)
    }

    fun showScreenDialogs(
        layoutId: Int,
        tvToDoIdOne: Int,
        tvToDoIdTwo: Int,
        llToDoIdOne: Int,
        llToDoIdTwo: Int,
        taskOne: String,
        taskTwo: String,
        calledFrom: String?,
        level: Int
    ) {
        if (this.timerDialog != null) {
            if (timerDialog!!.isShowing) timerDialog!!.dismiss()
            this.timerDialog = null
        }
        setDialogForGame()
        timerDialog!!.setContentView(layoutId)

        textOops = timerDialog!!.findViewById<View>(R.id.textOops) as TextView

        taskToDoOne = timerDialog!!.findViewById<View>(tvToDoIdOne) as TextView
        taskToDoTwo = timerDialog!!.findViewById<View>(tvToDoIdTwo) as TextView

        var face = Typeface.createFromAsset(assets, "fonts/forte.ttf")
        textOops!!.setTypeface(face)

        face = Typeface.createFromAsset(assets, "fonts/mvboli.ttf")
        taskToDoOne!!.setTypeface(face)

        face = Typeface.createFromAsset(assets, "fonts/mvboli.ttf")
        taskToDoTwo!!.setTypeface(face)

        val preferences = getPreferences(MODE_PRIVATE)
        val globalLevel = preferences.getInt("Level", 1)

        llToDoOne = timerDialog!!.findViewById<View>(llToDoIdOne) as LinearLayout
        llToDoSecond = timerDialog!!.findViewById<View>(llToDoIdTwo) as LinearLayout

        llToDoOne!!.setOnClickListener {
            if (taskOne == "Resume" && calledFrom == "Select") {
                if (timerDialog != null) {
                    if (timerDialog?.isShowing == true) timerDialog?.dismiss()
                    timerDialog = null
                }
            } else if (taskOne == "Resume" && calledFrom == "Pause") {
                if (timerDialog != null) {
                    if (timerDialog?.isShowing == true) timerDialog?.dismiss()
                    timerDialog = null
                }

                if (levelsfragment != null) {
                    levelsfragment!!.setState(true)
                    levelsfragment!!.setSaveflg(false)
                    levelsfragment!!.onResume()
                }
            } else if (taskOne == "Retry") {
                if (timerDialog != null) {
                    if (timerDialog?.isShowing == true) timerDialog?.dismiss()
                    timerDialog = null
                    onButtonsSelected("Retry", level)
                }
            } else if (taskOne == "Next") {
                if (timerDialog != null) {
                    if (timerDialog?.isShowing == true) timerDialog?.dismiss()
                    timerDialog = null
                    onButtonsSelected("Next", level + 1)
                }
            }
        }
        llToDoSecond!!.setOnClickListener {
            if (taskTwo == "Exit") {
                if (timerDialog != null) {
                    if (timerDialog?.isShowing == true) timerDialog?.dismiss()
                    timerDialog = null
                }
                finish()
            }
            if (taskTwo == "Home") {
                if (timerDialog != null) {
                    if (timerDialog?.isShowing == true) timerDialog?.dismiss()
                    timerDialog = null
                    onButtonsSelected("Home", globalLevel)
                }
            }
        }


        timerDialog!!.show()
    }

    fun pauseGameOnPause() {
        showScreenDialogs(
            R.layout.layout_check_image_pause,
            R.id.tvResume,
            R.id.tvHome,
            R.id.ll_resume,
            R.id.ll_home,
            "Resume",
            "Home",
            "Pause",
            level
        )
        flagDoNotShowPause = false
    }

    public override fun onPause() {
        super.onPause()


        if (fragmentShown == LEVELS_FRAGMENT_SHOWN) {
            if (this.timerDialog != null) {
                if (timerDialog!!.isShowing) {
                    //do nothing
                } else {
                    if (flagDoNotShowPause == false) {
                        pauseGameOnPause()
                    }
                }
            } else {
                if (flagDoNotShowPause == false) {
                    pauseGameOnPause()
                }
            }
        }
    }

    public override fun onResume() {
        super.onResume()
        if (this.timerDialog != null) {
            if (timerDialog!!.isShowing) {
                if (levelsfragment != null) levelsfragment!!.setState(false)
            }
        }
    }

    fun onButtonsSelected(task: String, level: Int) {
        if (task == "Next" || task == "Retry") {
            val f = findViewById<View>(R.id.main_container) as FrameLayout
            f.removeAllViewsInLayout()

            if (levelInfoFragment != null) {
                levelInfoFragment = null
            }
            levelInfoFragment = LevelInfoFragment()

            setStageLevelImages(level)

            val args = Bundle()
            args.putInt("Level", level)
            args.putInt("Stage", stage)
            args.putString("TimeAllowed", timeAllowed)
            args.putInt("NumImages", numImgs)


            levelInfoFragment!!.arguments = args

            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.main_container, levelInfoFragment!!, "LEVEL_INFO_FRAGMENT")
            transaction.commit()
            fragmentShown = LEVEL_INFO_FRAGMENT_SHOWN
        } else if (task == "Home") {
            val f = findViewById<View>(R.id.main_container) as FrameLayout
            f.removeAllViewsInLayout()

            val preferences = getPreferences(MODE_PRIVATE)
            val globalLevel = preferences.getInt("Level", 1)

            if (selectLevelFragment != null) {
                selectLevelFragment = null
            }
            selectLevelFragment = SelectLevelFragment()
            val args = Bundle()
            args.putInt("Level", globalLevel)

            selectLevelFragment!!.arguments = args
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.main_container, selectLevelFragment!!, "SELECT_LEVEL_FRAGMENT")
            transaction.commit()
            fragmentShown = SELECT_LEVELS_FRAGMENT_SHOWN
        }

        if (flagBadgeEarned == true) {
            flagBadgeEarned = false
        }
    }

    private val isFirstTime: Boolean
        get() {
            val preferences = getPreferences(MODE_PRIVATE)
            val ranBefore = preferences.getBoolean("RanBefore", false)
            if (!ranBefore) {
                val editor = preferences.edit()
                editor.putBoolean("RanBefore", true)
                editor.commit()
            }
            return ranBefore
        }

    public override fun onStop() {
        super.onStop()
    }

    public override fun onDestroy() {
        super.onDestroy()


        val f = findViewById<View>(R.id.main_container) as FrameLayout
        f.removeAllViewsInLayout()
        timerDialog = null
        textOops = null
        textAlert = null
        textLevelFailed = null
        textLevelCleared = null
        levelsfragment = null
        levelInfoFragment = null
        selectLevelFragment = null
    }

    override fun onBackPressed() {
        super.onBackPressed()

        if (selectLevelFragment != null && fragmentShown == SELECT_LEVELS_FRAGMENT_SHOWN) {
            flagDoNotShowPause = true

            showScreenDialogs(
                R.layout.layout_select_level_alert,
                R.id.tvResume,
                R.id.tvExit,
                R.id.ll_resume,
                R.id.ll_exit,
                "Resume",
                "Exit",
                "Select",
                level
            )
        } else if (levelsfragment != null && fragmentShown == LEVELS_FRAGMENT_SHOWN) {
            flagDoNotShowPause = true

            if (this.timerDialog != null) {
                if (timerDialog!!.isShowing) {
                    levelsfragment!!.setState(false)
                    levelsfragment!!.setIntent()
                    levelsfragment!!.onPause()
                } else {
                    pauseGameOnPause()
                    levelsfragment!!.setState(false)
                    levelsfragment!!.setIntent()
                    levelsfragment!!.onPause()
                }
            } else {
                pauseGameOnPause()

                levelsfragment!!.setState(false)
                levelsfragment!!.setIntent()
                levelsfragment!!.onPause()
            }
        } else if (helpFragment != null && fragmentShown == HELP_FRAGMENT_SHOWN) {
            flagDoNotShowPause = true
        } else if (levelInfoFragment != null && fragmentShown == LEVEL_INFO_FRAGMENT_SHOWN) {
            flagDoNotShowPause = true
            flagShowAnimOnBack = true
            // showScreenDialogs(com.memorygame.memorydrill.R.layout.layout_select_level_alert, com.memorygame.memorydrill.R.id.tvResume, com.memorygame.memorydrill.R.id.tvExit, com.memorygame.memorydrill.R.id.ll_resume, com.memorygame.memorydrill.R.id.ll_exit, "Resume", "Exit", "Select" , level);
            destroyFragment()
        }
    }

    override fun destroyFragment() {
        if (selectLevelFragment != null) {
            selectLevelFragment = null
        }
        selectLevelFragment = SelectLevelFragment()

        val preferences = getPreferences(MODE_PRIVATE)
        val globalLevel = preferences.getInt("Level", 1)

        val args = Bundle()
        args.putInt("Level", globalLevel)

        selectLevelFragment!!.arguments = args

        val transaction = supportFragmentManager.beginTransaction()
        if (flagShowAnimOnBack == true) {
            transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right)
            flagShowAnimOnBack = false
        }
        transaction.replace(R.id.main_container, selectLevelFragment!!, "SELECT_LEVEL_FRAGMENT")
        transaction.commit()
        fragmentShown = SELECT_LEVELS_FRAGMENT_SHOWN
    }

    override fun startLevel(level: Int) {
        if (levelsfragment != null) levelsfragment = null

        levelsfragment = LevelsFragment()

        val args = Bundle()
        args.putInt(LevelsFragment.LEVEL, level)
        args.putInt("NumColumns", numColumns)
        args.putInt("NumImgs", numImgs)
        levelsfragment!!.arguments = args

        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
        transaction.replace(R.id.main_container, levelsfragment!!, "LEVELS_FRAGMENT")
        transaction.commit()
        fragmentShown = LEVELS_FRAGMENT_SHOWN
    }

    fun setStageLevelImages(level: Int) {
        if (level == 1) {
            numColumns = STAGE_ONE_NUM_COLUMNS
            numImgs = STAGE_ONE_NUM_IMGS
            calcTime = TIMER_ONE
            textviewText = TIMER_TEXT_STAGE_ONE
            timeAllowed = TIME_ALLOWED_ONE
            stage = STAGE_ONE
        } else if (level == 2) {
            numColumns = STAGE_ONE_NUM_COLUMNS
            numImgs = STAGE_ONE_NUM_IMGS
            calcTime = TIMER_TWO
            timeAllowed = TIME_ALLOWED_TWO

            textviewText = TIMER_TEXT_STAGE_TWO
            stage = STAGE_ONE
        } else if (level == 3) {
            numColumns = STAGE_ONE_NUM_COLUMNS
            numImgs = STAGE_ONE_NUM_IMGS
            calcTime = TIMER_THREE
            timeAllowed = TIME_ALLOWED_THREE

            textviewText = TIMER_TEXT_STAGE_THREE
            stage = STAGE_ONE
        } else if (level == 4) {
            numColumns = STAGE_ONE_NUM_COLUMNS
            numImgs = STAGE_ONE_NUM_IMGS
            calcTime = TIMER_FOUR
            timeAllowed = TIME_ALLOWED_FOUR

            textviewText = TIMER_TEXT_STAGE_FOUR
            stage = STAGE_ONE
        } else if (level == 5) {
            numColumns = STAGE_TWO_NUM_COLUMNS
            numImgs = STAGE_TWO_NUM_IMGS
            calcTime = TIMER_ONE
            timeAllowed = TIME_ALLOWED_ONE

            textviewText = TIMER_TEXT_STAGE_ONE
            stage = STAGE_TWO
        } else if (level == 6) {
            numColumns = STAGE_TWO_NUM_COLUMNS
            numImgs = STAGE_TWO_NUM_IMGS
            calcTime = TIMER_TWO
            timeAllowed = TIME_ALLOWED_TWO

            textviewText = TIMER_TEXT_STAGE_TWO
            stage = STAGE_TWO
        } else if (level == 7) {
            numColumns = STAGE_TWO_NUM_COLUMNS
            numImgs = STAGE_TWO_NUM_IMGS
            calcTime = TIMER_THREE
            timeAllowed = TIME_ALLOWED_THREE

            textviewText = TIMER_TEXT_STAGE_THREE
            stage = STAGE_TWO
        } else if (level == 8) {
            numColumns = STAGE_TWO_NUM_COLUMNS
            numImgs = STAGE_TWO_NUM_IMGS
            calcTime = TIMER_FOUR
            timeAllowed = TIME_ALLOWED_FOUR

            textviewText = TIMER_TEXT_STAGE_FOUR
            stage = STAGE_TWO
        } else if (level == 9) {
            numColumns = STAGE_THREE_NUM_COLUMNS
            numImgs = STAGE_THREE_NUM_IMGS
            calcTime = TIMER_ONE
            timeAllowed = TIME_ALLOWED_ONE

            textviewText = TIMER_TEXT_STAGE_ONE
            stage = STAGE_THREE
        } else if (level == 10) {
            numColumns = STAGE_THREE_NUM_COLUMNS
            numImgs = STAGE_THREE_NUM_IMGS
            calcTime = TIMER_TWO
            timeAllowed = TIME_ALLOWED_TWO

            textviewText = TIMER_TEXT_STAGE_TWO
            stage = STAGE_THREE
        } else if (level == 11) {
            numColumns = STAGE_THREE_NUM_COLUMNS
            numImgs = STAGE_THREE_NUM_IMGS
            calcTime = TIMER_THREE
            timeAllowed = TIME_ALLOWED_THREE

            textviewText = TIMER_TEXT_STAGE_THREE
            stage = STAGE_THREE
        } else if (level == 12) {
            numColumns = STAGE_THREE_NUM_COLUMNS
            numImgs = STAGE_THREE_NUM_IMGS
            calcTime = TIMER_FOUR
            timeAllowed = TIME_ALLOWED_FOUR

            textviewText = TIMER_TEXT_STAGE_FOUR
            stage = STAGE_THREE
        } else if (level == 13) {
            numColumns = STAGE_FOUR_NUM_COLUMNS
            numImgs = STAGE_FOUR_NUM_IMGS
            calcTime = TIMER_ONE
            timeAllowed = TIME_ALLOWED_ONE

            textviewText = TIMER_TEXT_STAGE_ONE
            stage = STAGE_FOUR
        } else if (level == 14) {
            numColumns = STAGE_FOUR_NUM_COLUMNS
            numImgs = STAGE_FOUR_NUM_IMGS
            calcTime = TIMER_TWO
            timeAllowed = TIME_ALLOWED_TWO

            textviewText = TIMER_TEXT_STAGE_TWO
            stage = STAGE_FOUR
        } else if (level == 15) {
            numColumns = STAGE_FOUR_NUM_COLUMNS
            numImgs = STAGE_FOUR_NUM_IMGS
            calcTime = TIMER_THREE
            timeAllowed = TIME_ALLOWED_THREE

            textviewText = TIMER_TEXT_STAGE_THREE
            stage = STAGE_FOUR
        } else if (level == 16) {
            numColumns = STAGE_FOUR_NUM_COLUMNS
            numImgs = STAGE_FOUR_NUM_IMGS
            calcTime = TIMER_FOUR
            timeAllowed = TIME_ALLOWED_FOUR

            textviewText = TIMER_TEXT_STAGE_FOUR

            stage = STAGE_FOUR
        }
    }

    companion object {
        private const val LEVELS_FRAGMENT_SHOWN = 0
        private const val SELECT_LEVELS_FRAGMENT_SHOWN = 1
        private const val HELP_FRAGMENT_SHOWN = 2
        private const val LEVEL_INFO_FRAGMENT_SHOWN = 3

        const val STAGE_ONE_NUM_COLUMNS: Int = 2
        const val STAGE_TWO_NUM_COLUMNS: Int = 3
        const val STAGE_THREE_NUM_COLUMNS: Int = 3
        const val STAGE_FOUR_NUM_COLUMNS: Int = 4
        const val STAGE_ONE_NUM_IMGS: Int = 4
        const val STAGE_TWO_NUM_IMGS: Int = 6
        const val STAGE_THREE_NUM_IMGS: Int = 9
        const val STAGE_FOUR_NUM_IMGS: Int = 12

        const val TIMER_TEXT_STAGE_ONE: String = "01:00"
        const val TIMER_TEXT_STAGE_TWO: String = "00:45"
        const val TIMER_TEXT_STAGE_THREE: String = "00:30"
        const val TIMER_TEXT_STAGE_FOUR: String = "00:15"

        const val TIMER_ONE: Long = 60000
        const val TIMER_TWO: Long = 45000
        const val TIMER_THREE: Long = 30000
        const val TIMER_FOUR: Long = 15000
        const val TIME_ALLOWED_ONE: String = "60 seconds"
        const val TIME_ALLOWED_TWO: String = "45 seconds"
        const val TIME_ALLOWED_THREE: String = "30 seconds"
        const val TIME_ALLOWED_FOUR: String = "15 seconds"

        const val STAGE_ONE: Int = 1
        const val STAGE_TWO: Int = 2
        const val STAGE_THREE: Int = 3
        const val STAGE_FOUR: Int = 4

        private var fragmentShown = 0
    }
}
