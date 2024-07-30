package com.memorygame.memorydrill

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.ViewFlipper

/**
 * Created by aspire on 27-06-2016.
 */
class GridViewAdapter(
    private val context: Context,
    private val layoutResourceId: Int,
    data: ArrayList<*>
) : ArrayAdapter<Any?>(
    context, layoutResourceId, data
) {
    private var data: ArrayList<*> = ArrayList<Any?>()

    init {
        this.data = data
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var row = convertView
        var holder: ViewHolder? = null

        if (row == null) {
            val inflater = (context as Activity).layoutInflater
            row = inflater.inflate(layoutResourceId, parent, false)
            holder = ViewHolder()
            holder.image = row.findViewById<View>(R.id.image) as ImageView
            row.tag = holder
        } else {
            holder = row.tag as ViewHolder
        }


        /*  final ViewFlipper flipper = (ViewFlipper) row.findViewById(R.id.my_view_flipper);
        final ViewHolder newHolder = holder;
        final int pos = position;
        //holder.image.setImageBitmap(null);*/


        //now you set your onclick and pass it the current viewflipper to control the displayed child
        /* flipper.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View click) {

                flipViewFlipper(flipper, newHolder, pos);

            }

        });*/
        val item = data[position] as GridImageView
        holder.image!!.setImageBitmap(item.image)

        // if(item.getTitle() != null)
        // holder.imageTitle.setText(item.getTitle());
        return row!!
    }

    private fun flipViewFlipper(flipper: ViewFlipper, newHolder: ViewHolder, position: Int) {
        if (flipper.displayedChild == 0) {
            flipper.displayedChild = 1
            val item = data[position] as GridImageView
            newHolder.image!!.setImageBitmap(item.image)
        } else {
            flipper.displayedChild = 0
        }
    }

    internal class ViewHolder {
        var image: ImageView? = null
        var imageTitle: TextView? = null
    }
}
