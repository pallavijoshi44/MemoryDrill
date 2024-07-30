package com.memorygame.memorydrill

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

/**
 * Created by aspire on 04-07-2016.
 */
class SelectLevelFragment : Fragment(), View.OnClickListener {
    private var tvMasterMind: TextView? = null
    private var tvStageOne: TextView? = null
    private var tvStageTwo: TextView? = null
    private var tvStageThree: TextView? = null
    private var tvStageFour: TextView? = null
    private var tvSelectLevel: AutoResizeTextView? = null
    private var helpBtn: Button? = null
    private var level = 0
    var args: Bundle? = null
    var mListener: SelectLevelFragmentListener? = null


    interface SelectLevelFragmentListener {
        fun onBtnSelected(level: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.activity_level, container, false)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mListener = activity as SelectLevelFragmentListener?
        args = arguments
        level = args!!.getInt("Level")
        tvMasterMind = activity!!.findViewById<View>(R.id.tvMasterMind) as TextView
        tvStageOne = activity!!.findViewById<View>(R.id.tvStageOne) as TextView
        tvStageTwo = activity!!.findViewById<View>(R.id.tvStageTwo) as TextView
        tvStageThree = activity!!.findViewById<View>(R.id.tvStageThree) as TextView
        tvStageFour = activity!!.findViewById<View>(R.id.tvStageFour) as TextView
        helpBtn = activity!!.findViewById<View>(R.id.btnHelp) as Button
        tvSelectLevel = activity!!.findViewById<View>(R.id.tv_select_level) as AutoResizeTextView
        helpBtn!!.setOnClickListener(this)

        var face = Typeface.createFromAsset(
            activity!!.assets, "fonts/papyrus.ttf"
        )
        tvMasterMind!!.typeface = face

        face = Typeface.createFromAsset(activity!!.assets, "fonts/mvboli.ttf")
        tvSelectLevel!!.typeface = face


        face = Typeface.createFromAsset(activity!!.assets, "fonts/mvboli.ttf")
        tvStageOne!!.typeface = face

        face = Typeface.createFromAsset(activity!!.assets, "fonts/mvboli.ttf")
        tvStageTwo!!.typeface = face

        face = Typeface.createFromAsset(activity!!.assets, "fonts/mvboli.ttf")
        tvStageThree!!.typeface = face

        face = Typeface.createFromAsset(activity!!.assets, "fonts/mvboli.ttf")
        tvStageFour!!.typeface = face

        var btn: Button
        for (i in 1..level) {
            if (i == 17) {
                break
            } else {
                val resourceName = "btn_$i"
                try {
                    val res: Class<*> = R.id::class.java
                    val field = res.getField(resourceName)
                    val resID = field.getInt(null)
                    btn = activity!!.findViewById<View>(resID) as Button
                    btn.text = " $i "
                    btn.setTextColor(Color.WHITE)
                    btn.textSize = 25f
                    btn.background = null
                    btn.background =
                        ContextCompat.getDrawable(activity!!, R.drawable.button_selector)
                    //  btn.setBackground(getActivity().getDrawable(com.memorygame.memorydrill.R.drawable.button_selector));
                    btn.isEnabled = true
                    btn.setOnClickListener(this)
                } catch (e: Exception) {
                    Log.e("MyTag", "Failure to get drawable id.", e)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_1 -> mListener!!.onBtnSelected(1)
            R.id.btn_2 -> mListener!!.onBtnSelected(2)
            R.id.btn_3 -> mListener!!.onBtnSelected(3)
            R.id.btn_4 -> mListener!!.onBtnSelected(4)
            R.id.btn_5 -> mListener!!.onBtnSelected(5)
            R.id.btn_6 -> mListener!!.onBtnSelected(6)
            R.id.btn_7 -> mListener!!.onBtnSelected(7)
            R.id.btn_8 -> mListener!!.onBtnSelected(8)
            R.id.btn_9 -> mListener!!.onBtnSelected(9)
            R.id.btn_10 -> mListener!!.onBtnSelected(10)
            R.id.btn_11 -> mListener!!.onBtnSelected(11)
            R.id.btn_12 -> mListener!!.onBtnSelected(12)
            R.id.btn_13 -> mListener!!.onBtnSelected(13)
            R.id.btn_14 -> mListener!!.onBtnSelected(14)
            R.id.btn_15 -> mListener!!.onBtnSelected(15)
            R.id.btn_16 -> mListener!!.onBtnSelected(16)
            R.id.btnHelp -> mListener!!.onBtnSelected(10000)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        tvMasterMind = null
        tvStageOne = null
        tvStageTwo = null
        tvStageThree = null
        tvStageFour = null
        args = null
        mListener = null
    }
}

