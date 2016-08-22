package in.silive.scrolls2015.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import in.silive.scrolls2015.R;
import in.silive.scrolls2015.models.ImageItem;

/**
 * Created by kone on 23/9/15.
 */
public class SlidingPaneDrawerAdapter extends ArrayAdapter {
    private Context context;
    private int layoutResourceId;
    private ArrayList data = new ArrayList();


    public SlidingPaneDrawerAdapter(Context context, int layoutResourceId, ArrayList data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;
    /*    LayoutInflater inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.drawer_list_item,null);
        ImageView iv = (ImageView) view.findViewById(R.id.iv_drawer);
        TextView tv= (TextView) view.findViewById(R.id.tv_drawer);
        tv.setText(title_array[i]);
        iv.setImageDrawable(tit);
        holder.image.setImageBitmap(item.getImage());

        return view;
    */

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageTitle = (TextView) row.findViewById(R.id.tv_drawer);
            holder.image = (ImageView) row.findViewById(R.id.iv_drawer);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        ImageItem item = (ImageItem) data.get(position);
        holder.imageTitle.setText(item.getTitle());
        holder.image.setImageBitmap(item.getImage());
        return row;
    }

    static class ViewHolder {
        TextView imageTitle;
        ImageView image;
    }
}