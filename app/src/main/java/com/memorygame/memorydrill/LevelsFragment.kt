package com.memorygame.memorydrill

import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.Button
import android.widget.GridView
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.util.Collections
import java.util.concurrent.TimeUnit

/**
 * Created by aspire on 24-06-2016.
 */
class LevelsFragment : Fragment(), View.OnClickListener, OnItemClickListener {
    private var tvMasterMind: TextView? = null
    private var currentState: Boolean? = null
    private var gridView: GridView? = null
    private var gridAdapter: GridViewAdapter? = null
    private var btnContinue: Button? = null
    var numbers: MutableList<Int> = mutableListOf()
    var imgs: TypedArray? = null
    var textViewTime: TextView? = null
    var tvMemorizeInfo: AutoResizeTextView? = null
    var tvLevelNo: TextView? = null
    var timeRemaining: Long = -1
    var millis: Long = 0
    var timer: CounterClass? = null
    var calcTime: Long = 0
    var args: Bundle? = null
    var level: Int = 0
    var numImgs: Int = 0
    var numNewImgs: Int = 0
    var numColumns: Int = 0
    var newPos: Int = 0
    var textviewText: String? = null
    var imageNew: GridImageView? = null
    var columnWidth: Int = 0
    var mListener: LevelsFragmentListener? = null
    var saveFlag: Boolean? = null
    var flagChangeGridImages: Boolean? = null
    var gridItems: ArrayList<GridImageView>? = null
    var tempGridItems: ArrayList<GridImageView>? = null

    var tempGridAdapter: GridViewAdapter? = null


    interface LevelsFragmentListener {
        fun onImageSelected(
            newPos: Int,
            position: Int,
            timeRemaining: Long,
            itemPickedId: Int,
            newItemId: Int,
            level: Int
        )

        fun timerFinished(level: Int)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentState = false
        imgs = null
        retainInstance =
            true //Will ignore onDestroy Method (Nested Fragments no need this if parent have it)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = R.layout.fragment_level_play


        // Inflate the layout for this fragment
        return inflater.inflate(layout, container, false)
    }

    private fun setTimer() {
        if (level == 1) {
            calcTime = TIMER_ONE
            textviewText = TIMER_TEXT_STAGE_ONE
        } else if (level == 2) {
            calcTime = TIMER_TWO
            textviewText = TIMER_TEXT_STAGE_TWO
        } else if (level == 3) {
            calcTime = TIMER_THREE
            textviewText = TIMER_TEXT_STAGE_THREE
        } else if (level == 4) {
            calcTime = TIMER_FOUR
            textviewText = TIMER_TEXT_STAGE_FOUR
        } else if (level == 5) {
            calcTime = TIMER_ONE
            textviewText = TIMER_TEXT_STAGE_ONE
        } else if (level == 6) {
            calcTime = TIMER_TWO
            textviewText = TIMER_TEXT_STAGE_TWO
        } else if (level == 7) {
            calcTime = TIMER_THREE
            textviewText = TIMER_TEXT_STAGE_THREE
        } else if (level == 8) {
            calcTime = TIMER_FOUR
            textviewText = TIMER_TEXT_STAGE_FOUR
        } else if (level == 9) {
            calcTime = TIMER_ONE
            textviewText = TIMER_TEXT_STAGE_ONE
        } else if (level == 10) {
            calcTime = TIMER_TWO
            textviewText = TIMER_TEXT_STAGE_TWO
        } else if (level == 11) {
            calcTime = TIMER_THREE
            textviewText = TIMER_TEXT_STAGE_THREE
        } else if (level == 12) {
            calcTime = TIMER_FOUR
            textviewText = TIMER_TEXT_STAGE_FOUR
        } else if (level == 13) {
            calcTime = TIMER_ONE
            textviewText = TIMER_TEXT_STAGE_ONE
        } else if (level == 14) {
            calcTime = TIMER_TWO
            textviewText = TIMER_TEXT_STAGE_TWO
        } else if (level == 15) {
            calcTime = TIMER_THREE
            textviewText = TIMER_TEXT_STAGE_THREE
        } else if (level == 16) {
            calcTime = TIMER_FOUR
            textviewText = TIMER_TEXT_STAGE_FOUR
        }

        if (timer != null) timer = null

        timer = CounterClass(calcTime, 1000)
        textViewTime!!.text = textviewText
        timer!!.start()
    }

    override fun onStop() {
        super.onStop()

        saveFlag = false
        setIntent()
    }

    fun setIntent() {
        requireActivity().intent.putExtra("timerStageText", textViewTime!!.text.toString())
        requireActivity().intent.putExtra("timerValue", millis)
    }

    fun setState(value: Boolean?) {
        currentState = value
    }

    fun setSaveflg(value: Boolean?) {
        saveFlag = value
    }

    override fun onResume() {
        super.onResume()

        if (currentState == true) {
            if (saveFlag == false) {
                if (requireActivity().intent.extras != null) {
                    if (gridView != null) {
                        gridAdapter = tempGridAdapter
                        gridView!!.adapter = gridAdapter
                        flagChangeGridImages = false
                    }
                    calcTime = requireActivity().intent.getLongExtra("timerValue", 60000)
                    textviewText = requireActivity().intent.getStringExtra("timerStageText")
                    if (timer != null) timer = null

                    timer = CounterClass(calcTime, 1000)
                    textViewTime!!.text = textviewText
                    timer!!.start()
                }
            }
        }
        tvLevelNo!!.text = "Level $level"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args = arguments
        if (args != null) {
            level = args!!.getInt(LEVEL)
            numColumns = args!!.getInt("NumColumns")
            numImgs = args!!.getInt("NumImgs")
        }

        flagChangeGridImages = false

        mListener = activity as LevelsFragmentListener?
        textViewTime = requireActivity().findViewById<View>(R.id.textViewTime) as TextView
        tvMasterMind = requireActivity().findViewById<View>(R.id.tvMasterMindFragment) as TextView
        tvMemorizeInfo =
            requireActivity().findViewById<View>(R.id.tvMemorizeInfo) as AutoResizeTextView
        tvLevelNo = requireActivity().findViewById<View>(R.id.tvLevelNo) as TextView

        numNewImgs = 1

        var face = Typeface.createFromAsset(
            requireActivity().assets, "fonts/papyrus.ttf"
        )
        tvMasterMind!!.typeface = face

        face = Typeface.createFromAsset(requireActivity().assets, "fonts/digital.ttf")
        textViewTime!!.typeface = face

        face = Typeface.createFromAsset(requireActivity().assets, "fonts/chiller.ttf")
        tvMemorizeInfo!!.typeface = face

        face = Typeface.createFromAsset(requireActivity().assets, "fonts/forte.ttf")
        tvLevelNo!!.typeface = face

        if (imgs == null) {
            imgs = resources.obtainTypedArray(R.array.image_ids)
        }
        setNumbers()

        columnWidth = WIDTH_ONE


        btnContinue = requireActivity().findViewById<View>(R.id.btncontinue) as Button
        face = Typeface.createFromAsset(requireActivity().assets, "fonts/mvboli.ttf")
        btnContinue!!.typeface = face
        btnContinue!!.textSize = 20f

        gridView = requireActivity().findViewById<View>(R.id.gridView) as GridView
        saveFlag = true

        if (gridAdapter != null) {
            gridAdapter = null
        }

        gridItems = images
        tempGridItems = setBrainImage()

        gridAdapter = GridViewAdapter(requireActivity(), R.layout.layout_grid_item, gridItems!!)
        gridView!!.adapter = gridAdapter
        gridView!!.numColumns = numColumns
        gridView!!.columnWidth = columnWidth

        btnContinue!!.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()

        if (saveFlag == true) {
            setTimer()
        }
    }

    fun setBrainImage(): ArrayList<GridImageView> {
        // Prepare some dummy data for gridview

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.brain)

        val imageItems = ArrayList<GridImageView>()

        for (i in 0 until numImgs) {
            imageItems.add(GridImageView(bitmap, null, R.drawable.brain))
        }
        return imageItems
    }

    override fun onPause() {
        super.onPause()

        timer!!.cancel()

        if (flagChangeGridImages == false) {
            tempGridAdapter = gridAdapter
        }

        if (gridAdapter != null) {
            gridAdapter = null
        }
        if (gridView != null) {
            gridAdapter =
                GridViewAdapter(requireActivity(), R.layout.layout_grid_item, tempGridItems!!)
            flagChangeGridImages = true
            gridView!!.adapter = gridAdapter
        }
    }


    override fun onClick(v: View) {
        val text = "Identify the new image by tapping on it..."
        tvMemorizeInfo!!.text = text

        btnContinue!!.visibility = View.GONE

        val bitmap = BitmapFactory.decodeResource(
            resources, imgs!!.getResourceId(
                (numbers!![numImgs] as Int), -1
            )
        )

        if (imageNew != null) {
            imageNew = null
        }
        imageNew =
            GridImageView(bitmap, null, imgs!!.getResourceId((numbers!![numImgs] as Int), -1))

        newPos = numbers!![numImgs] as Int
        numbers!!.set(numImgs - 1, numbers!![numImgs])
        numbers!!.removeAt(numImgs)

        Collections.shuffle(numbers)

        if (gridAdapter != null) {
            gridAdapter = null
        }

        if (gridItems != null) {
            gridItems = null
        }
        gridItems = images
        gridAdapter = GridViewAdapter(requireActivity(), R.layout.layout_grid_item, gridItems!!)
        gridView!!.adapter = gridAdapter
        gridView!!.numColumns = numColumns
        gridView!!.columnWidth = columnWidth
        gridView!!.onItemClickListener = this
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        timer!!.cancel()
        timeRemaining = (calcTime - millis) / 1000 + 1
        val item = parent.getItemAtPosition(position) as GridImageView
        mListener!!.onImageSelected(
            newPos,
            numbers!![position] as Int,
            timeRemaining,
            item.id,
            imageNew!!.id,
            level
        )
    }

    private fun setNumbers() {
        while (numbers.size < numImgs + numNewImgs) {
            val random = getRandomNumberInRange(
                0,
                imgs!!.length() - 1
            ) //this is your method to return a random int

            if (!numbers.contains(random)) numbers.add(random)
        }
    }

    private val images: ArrayList<GridImageView>
        // Prepare some dummy data for gridview
        get() {
            var bitmap: Bitmap? = null
            val imageItems = ArrayList<GridImageView>()

            for (i in 0 until numImgs) {
                bitmap = BitmapFactory.decodeResource(
                    resources,
                    imgs!!.getResourceId((numbers!![i] as Int), -1)
                )
                imageItems.add(
                    GridImageView(
                        bitmap,
                        null,
                        imgs!!.getResourceId((numbers!![i] as Int), -1)
                    )
                )
            }
            return imageItems
        }

    override fun onDestroy() {
        super.onDestroy()
        saveFlag = true
        requireActivity().intent.removeExtra("timerValue")
        requireActivity().intent.removeExtra("timerStageText")


        requireArguments().remove("LEVEL")
        tvMasterMind = null
        gridAdapter = null
        btnContinue = null
        numbers = mutableListOf()
        imgs = null
        timer = null
        args = null
        imageNew = null
        mListener = null
        gridView = null
        textViewTime = null
        tvMemorizeInfo = null
        tvLevelNo = null

        textviewText = null
        gridItems = null
        tempGridItems = null
    }

    inner class CounterClass(millisInFuture: Long, countDownInterval: Long) :
        CountDownTimer(millisInFuture, countDownInterval) {
        override fun onTick(millisUntilFinished: Long) {
            // TODO Auto-generated method stub

            millis = millisUntilFinished
            val hms = String.format(
                "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(
                    TimeUnit.MILLISECONDS.toMinutes(
                        millis
                    )
                )
            )

            textViewTime!!.text = hms
        }

        override fun onFinish() {
            // TODO Auto-generated method stub
            textViewTime!!.text = "00:00"
            mListener!!.timerFinished(level)
        }
    }

    companion object {
        const val LEVEL: String = "LEVEL"
        const val WIDTH_ONE: Int = 35

        const val TIMER_TEXT_STAGE_ONE: String = "01:00"
        const val TIMER_TEXT_STAGE_TWO: String = "00:45"
        const val TIMER_TEXT_STAGE_THREE: String = "00:30"
        const val TIMER_TEXT_STAGE_FOUR: String = "00:15"

        const val TIMER_ONE: Long = 60000
        const val TIMER_TWO: Long = 45000
        const val TIMER_THREE: Long = 30000
        const val TIMER_FOUR: Long = 15000
        private fun getRandomNumberInRange(min: Int, max: Int): Int {
            require(min < max) { "max must be greater than min" }

            return (Math.random() * ((max - min) + 1)).toInt() + min
        }
    }
}





