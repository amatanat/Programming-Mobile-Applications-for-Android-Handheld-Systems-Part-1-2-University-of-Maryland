package selfie.com.example.dailyselfie;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {


    private ArrayList<Photo> list = new ArrayList<Photo>();
    private static LayoutInflater inflater = null;
    private Context mContext;

    public CustomAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View newView = convertView;
        ViewHolder holder;

        Photo curr = list.get(position);

        if (null == convertView) {
            holder = new ViewHolder();
            newView = inflater.inflate(R.layout.photo_layout, parent, false);
            holder.mImageView = (ImageView) newView.findViewById(R.id.imageView);
            holder.imageName = (TextView) newView.findViewById(R.id.textView);
            newView.setTag(holder);

        } else {
            holder = (ViewHolder) newView.getTag();
        }

        holder.mImageView.setImageBitmap(curr.getBitmap());
        holder.imageName.setText(curr.getImageName());
        return newView;
    }

    static class ViewHolder {

        ImageView mImageView;
        TextView imageName;
    }


    public void add(Photo listItem) {
        list.add(listItem);
        notifyDataSetChanged();
    }

    public ArrayList<Photo> getList() {
        return list;
    }

    public void setList(ArrayList<Photo> list) {this.list = list; }

    public void removeAllViews() {
        list.clear();
        this.notifyDataSetChanged();
    }
}
