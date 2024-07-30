package com.memorygame.memorydrill;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.ArrayList;

/**
 * Created by aspire on 27-06-2016.
 */
public class GridViewAdapter extends ArrayAdapter{
    private Context context;
    private int layoutResourceId;
    private ArrayList data = new ArrayList();

    public GridViewAdapter(Context context, int layoutResourceId, ArrayList data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) row.findViewById(com.memorygame.memorydrill.R.id.image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
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
        GridImageView item = (GridImageView) data.get(position); holder.image.setImageBitmap(item.getImage());

       // if(item.getTitle() != null)
       // holder.imageTitle.setText(item.getTitle());

        return row;
    }
    private void flipViewFlipper(ViewFlipper flipper, ViewHolder newHolder, int position) {

        if (flipper.getDisplayedChild() == 0) {
            flipper.setDisplayedChild(1);
            GridImageView item = (GridImageView) data.get(position);
            newHolder.image.setImageBitmap(item.getImage());
        } else {
            flipper.setDisplayedChild(0);
        }
    }
    static class ViewHolder {
        ImageView image;
        TextView imageTitle;
    }

}
