package com.qysd.lawtree.lawtreeview.fancyindexer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.qysd.lawtree.R;
import com.qysd.lawtree.lawtreebean.MyQiyeQunLiaoBean;
import com.qysd.lawtree.lawtreebean.SelectPersonBean;
import com.qysd.uikit.business.team.activity.lawtree.fancyindexer.adapter.PinyinKeyMapList;

import java.util.HashMap;
import java.util.List;


public abstract class MyQiyePingyinAdapter<T> extends BaseExpandableListAdapter {

    private Context context;
    private ExpandableListView listView;
    private int layoutChildId;
    private PinyinKeyMapList<T> keyMapList;
    private LayoutInflater inflater;
    public static HashMap<String, Boolean> isSelected;


    public MyQiyePingyinAdapter(ExpandableListView listView, List<T> list, int layoutChildId) {
        this.context = listView.getContext();
        this.listView = listView;
        this.layoutChildId = layoutChildId;
        inflater = LayoutInflater.from(context);
        keyMapList = new PinyinKeyMapList<T>(list){

            @Override
            public String getField(T t) {
                return getItemName(t);
            }
        };
        isSelected = new HashMap<String, Boolean>();
        for (int i = 0; i < list.size(); i++) {
            MyQiyeQunLiaoBean.Status a = (MyQiyeQunLiaoBean.Status) list.get(i);
            isSelected.put(a.getUserId(), false);
        }
    }

    public void expandGroup(){
        listView.setAdapter(this);
        for (int i = 0, length = this.getGroupCount(); i < length; i++) {
            listView.expandGroup(i);
        }
    }


    public abstract String getItemName(T t);

    public abstract ViewHolder getViewHolder(T t);

    public abstract void onItemClick(T t, View view, int position);



    public Object getChild(int group, int child) {
        // TODO Auto-generated method stub
        return keyMapList.getIndexList(group).get(child);
    }

    public long getChildId(int group, int child) {
        return child;
    }


    public int getChildrenCount(int group) {
        // TODO Auto-generated method stub
        return keyMapList.getIndexList(group).size();
    }

    public Object getGroup(int group) {
        return keyMapList.getIndexList(group);
    }

    public int getGroupCount() {
        return keyMapList.getTypes().size();
    }

    public long getGroupId(int group) {
        // TODO Auto-generated method stub
        return group;
    }

    public View getGroupView(int group, boolean arg1, View contentView,
                             ViewGroup arg3) {
        if (contentView == null) {
            contentView = inflater.inflate(R.layout.group_item, null);
            contentView.setClickable(true);
        }
        TextView textView = (TextView) contentView.findViewById(R.id.tv_index);
        textView.setText(keyMapList.getTypes().get(group));
        return contentView;
    }

    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return true;
    }

    public boolean isChildSelectable(int arg0, int arg1) {
        // TODO Auto-generated method stub
        return true;
    }

    public View getChildView(int group, int child, boolean arg2,
                             View convertView, ViewGroup arg4) {
        View view;
        if (convertView == null) {
            view = View.inflate(context, layoutChildId, null);
        } else {
            view = convertView;
        }

        T t = keyMapList.getIndexList(group).get(child);
        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null) {
            holder = getViewHolder(t);
            holder.getHolder(view);
            view.setTag(holder);
        }
        holder.setItem(t);
        holder.show();
        return view;
    }

    public Context getContext() {
        return context;
    }

    public MyQiyePingyinAdapter setContext(Context context) {
        this.context = context;
        return this;
    }

    public PinyinKeyMapList<T> getKeyMapList() {
        return keyMapList;
    }

    public MyQiyePingyinAdapter setKeyMapList(PinyinKeyMapList<T> keyMapList) {
        this.keyMapList = keyMapList;
        return this;
    }

    public abstract class ViewHolder {
        public T item;


        public ViewHolder(T t) {
            this.item = t;
        }

        public abstract ViewHolder getHolder(View view);

        public abstract void show();

        public T getItem() {
            return item;
        }

        public ViewHolder setItem(T item) {
            this.item = item;
            return this;
        }

    }



}
