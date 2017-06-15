package com.example.pangxinlong.animationcollection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pxl on 2017/6/6.
 */

public class ListAdapter extends BaseAdapter {

    private Context mContext;

    private List<String> mDataList;

    public ListAdapter(Context context) {
        mContext = context;
        mDataList = new ArrayList<>();
    }

    public void setData(List<String> list) {
        mDataList.clear();
        mDataList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder ;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.bindData(mDataList.get(position));
        return convertView;
    }

    class ViewHolder {

        @BindView(R.id.tv_tag_name)
        TextView mTvTagName;

        public ViewHolder(View rootView) {
            ButterKnife.bind(this, rootView);
        }

        public void bindData(String tagName) {
            mTvTagName.setText(tagName);
        }

    }
}
