package com.memorygame.memorydrill

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

/**
 * Created by aspire on 04-07-2016.
 */
class HelpFragment : Fragment(), View.OnClickListener {
    private var btnClose: Button? = null
    private var tvMasterMindFragment: TextView? = null
    private var tvHelpTitle: TextView? = null
    private var tvHelpText: TextView? = null
    var helpFragmentListener: HelpFragmentListener? = null


    interface HelpFragmentListener {
        fun destroyFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_help, container, false)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        helpFragmentListener = activity as HelpFragmentListener?

        tvMasterMindFragment = activity!!.findViewById<View>(R.id.tvMasterMindFragment) as TextView
        tvHelpText = activity!!.findViewById<View>(R.id.tvHelpText) as TextView
        tvHelpTitle = activity!!.findViewById<View>(R.id.tvHelpTitle) as TextView

        var face = Typeface.createFromAsset(
            activity!!.assets, "fonts/papyrus.ttf"
        )
        tvMasterMindFragment!!.typeface = face

        face = Typeface.createFromAsset(activity!!.assets, "fonts/bradhitc.ttf")
        tvHelpText!!.typeface = face

        face = Typeface.createFromAsset(activity!!.assets, "fonts/bradhitc.ttf")
        tvHelpTitle!!.typeface = face

        btnClose = activity!!.findViewById<View>(R.id.btnClose) as Button
        btnClose!!.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
    }


    override fun onClick(v: View) {
        helpFragmentListener!!.destroyFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        helpFragmentListener = null
    }
}

