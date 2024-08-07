package com.memorygame.memorydrill

import android.content.Context
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.TextView
import kotlin.math.max
import kotlin.math.min

/**
 * Created by aspire on 09-07-2016.
 */
/**
 * DO WHAT YOU WANT TO PUBLIC LICENSE
 * Version 2, December 2004
 *
 * Copyright (C) 2004 Sam Hocevar <sam></sam>@hocevar.net>
 *
 * Everyone is permitted to copy and distribute verbatim or modified
 * copies of this license document, and changing it is allowed as long
 * as the name is changed.
 *
 * DO WHAT YOU WANT TO PUBLIC LICENSE
 * TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 *
 * 0. You just DO WHAT YOU WANT TO.
 */

/**
 * Text view that auto adjusts text size to fit within the view.
 * If the text size equals the minimum text size and still does not
 * fit, append with an ellipsis.
 *
 * @author Chase Colburn
 * @since Apr 4, 2011
 */
class AutoResizeTextView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : TextView(context, attrs, defStyle) {
    // Interface for resize notifications
    interface OnTextResizeListener {
        fun onTextResize(textView: TextView?, oldSize: Float, newSize: Float)
    }

    // Registered resize listener
    private var mTextResizeListener: OnTextResizeListener? = null

    // Flag for text and/or size changes to force a resize
    private var mNeedsResize = false

    // Text size that is set from code. This acts as a starting point for resizing
    private var mTextSize: Float

    // Temporary upper bounds on the starting text size
    private var mMaxTextSize = 0f

    // Lower bounds for text size
    private var mMinTextSize = MIN_TEXT_SIZE

    // Text view line spacing multiplier
    private var mSpacingMult = 1.0f

    // Text view additional line spacing
    private var mSpacingAdd = 0.0f

    /**
     * Return flag to add ellipsis to text that overflows at the smallest text size
     * @return
     */
    /**
     * Set flag to add ellipsis to text that overflows at the smallest text size
     * @param addEllipsis
     */
    // Add ellipsis to text that overflows at the smallest text size
    var addEllipsis: Boolean = true

    // Default constructor override
    // Default constructor when inflating from XML file
    // Default constructor override
    init {
        mTextSize = textSize
    }

    /**
     * When text changes, set the force resize flag to true and reset the text size.
     */
    override fun onTextChanged(text: CharSequence, start: Int, before: Int, after: Int) {
        mNeedsResize = true
        // Since this view may be reused, it is good to reset the text size
        resetTextSize()
    }

    /**
     * If the text view size changed, set the force resize flag to true
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        if (w != oldw || h != oldh) {
            mNeedsResize = true
        }
    }

    /**
     * Register listener to receive resize notifications
     * @param listener
     */
    fun setOnResizeListener(listener: OnTextResizeListener?) {
        mTextResizeListener = listener
    }

    /**
     * Override the set text size to update our internal reference values
     */
    override fun setTextSize(size: Float) {
        super.setTextSize(size)
        mTextSize = textSize
    }

    /**
     * Override the set text size to update our internal reference values
     */
    override fun setTextSize(unit: Int, size: Float) {
        super.setTextSize(unit, size)
        mTextSize = textSize
    }

    /**
     * Override the set line spacing to update our internal reference values
     */
    override fun setLineSpacing(add: Float, mult: Float) {
        super.setLineSpacing(add, mult)
        mSpacingMult = mult
        mSpacingAdd = add
    }

    var maxTextSize: Float
        /**
         * Return upper text size limit
         * @return
         */
        get() = mMaxTextSize
        /**
         * Set the upper text size limit and invalidate the view
         * @param maxTextSize
         */
        set(maxTextSize) {
            mMaxTextSize = maxTextSize
            requestLayout()
            invalidate()
        }

    var minTextSize: Float
        /**
         * Return lower text size limit
         * @return
         */
        get() = mMinTextSize
        /**
         * Set the lower text size limit and invalidate the view
         * @param minTextSize
         */
        set(minTextSize) {
            mMinTextSize = minTextSize
            requestLayout()
            invalidate()
        }

    /**
     * Reset the text to the original size
     */
    fun resetTextSize() {
        if (mTextSize > 0) {
            super.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize)
            mMaxTextSize = mTextSize
        }
    }

    /**
     * Resize text after measuring
     */
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        if (changed || mNeedsResize) {
            val widthLimit = (right - left) - compoundPaddingLeft - compoundPaddingRight
            val heightLimit = (bottom - top) - compoundPaddingBottom - compoundPaddingTop
            resizeText(widthLimit, heightLimit)
        }
        super.onLayout(changed, left, top, right, bottom)
    }

    /**
     * Resize the text size with default width and height
     */
    fun resizeText() {
        val heightLimit = height - paddingBottom - paddingTop
        val widthLimit = width - paddingLeft - paddingRight
        resizeText(widthLimit, heightLimit)
    }

    /**
     * Resize the text size with specified width and height
     * @param width
     * @param height
     */
    fun resizeText(width: Int, height: Int) {
        var text = text
        // Do not resize if the view does not have dimensions or there is no text
        if (text == null || text.length == 0 || height <= 0 || width <= 0 || mTextSize == 0f) {
            return
        }

        if (transformationMethod != null) {
            text = transformationMethod.getTransformation(text, this)
        }

        // Get the text view's paint object
        val textPaint = paint

        // Store the current text size
        val oldTextSize = textPaint.textSize
        // If there is a max text size set, use the lesser of that and the default text size
        var targetTextSize =
            if (mMaxTextSize > 0) min(mTextSize.toDouble(), mMaxTextSize.toDouble())
                .toFloat() else mTextSize

        // Get the required text height
        var textHeight = getTextHeight(text, textPaint, width, targetTextSize)

        // Until we either fit within our text view or we had reached our min text size, incrementally try smaller sizes
        while (textHeight > height && targetTextSize > mMinTextSize) {
            targetTextSize = max((targetTextSize - 2).toDouble(), mMinTextSize.toDouble())
                .toFloat()
            textHeight = getTextHeight(text, textPaint, width, targetTextSize)
        }

        // If we had reached our minimum text size and still don't fit, append an ellipsis
        if (addEllipsis && targetTextSize == mMinTextSize && textHeight > height) {
            // Draw using a static layout
            // modified: use a copy of TextPaint for measuring
            val paint = TextPaint(textPaint)
            // Draw using a static layout
            val layout = StaticLayout(
                text,
                paint,
                width,
                Layout.Alignment.ALIGN_NORMAL,
                mSpacingMult,
                mSpacingAdd,
                false
            )
            // Check that we have a least one line of rendered text
            if (layout.lineCount > 0) {
                // Since the line at the specific vertical position would be cut off,
                // we must trim up to the previous line
                val lastLine = layout.getLineForVertical(height) - 1
                // If the text would not even fit on a single line, clear it
                if (lastLine < 0) {
                    setText("")
                } else {
                    val start = layout.getLineStart(lastLine)
                    var end = layout.getLineEnd(lastLine)
                    var lineWidth = layout.getLineWidth(lastLine)
                    val ellipseWidth = textPaint.measureText(mEllipsis)

                    // Trim characters off until we have enough room to draw the ellipsis
                    while (width < lineWidth + ellipseWidth) {
                        lineWidth =
                            textPaint.measureText(text!!.subSequence(start, --end + 1).toString())
                    }
                    setText(text!!.subSequence(0, end).toString() + mEllipsis)
                }
            }
        }

        // Some devices try to auto adjust line spacing, so force default line spacing
        // and invalidate the layout as a side effect
        setTextSize(TypedValue.COMPLEX_UNIT_PX, targetTextSize)
        setLineSpacing(mSpacingAdd, mSpacingMult)

        // Notify the listener if registered
        if (mTextResizeListener != null) {
            mTextResizeListener!!.onTextResize(this, oldTextSize, targetTextSize)
        }

        // Reset force resize flag
        mNeedsResize = false
    }

    // Set the text size of the text paint object and use a static layout to render text off screen before measuring
    private fun getTextHeight(
        source: CharSequence?,
        paint: TextPaint,
        width: Int,
        textSize: Float
    ): Int {
        // modified: make a copy of the original TextPaint object for measuring
        // (apparently the object gets modified while measuring, see also the
        // docs for TextView.getPaint() (which states to access it read-only)
        val paintCopy = TextPaint(paint)
        // Update the text paint object
        paintCopy.textSize = textSize
        // Measure using a static layout
        val layout = StaticLayout(
            source,
            paintCopy,
            width,
            Layout.Alignment.ALIGN_NORMAL,
            mSpacingMult,
            mSpacingAdd,
            true
        )
        return layout.height
    }

    companion object {
        // Minimum text size for this text view
        const val MIN_TEXT_SIZE: Float = 20f

        // Our ellipse string
        private const val mEllipsis = "..."
    }
}