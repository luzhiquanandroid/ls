package com.qysd.lawtree.lawtreeadapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qysd.lawtree.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 生产计划已完成和待完成适配器
 */
public class ScPlanAdapter extends RecyclerView.Adapter<ScPlanAdapter.ViewHoder> {
    private List<String> list = new ArrayList<>();

    public ScPlanAdapter(List<String> list) {
        this.list = list;
    }

    @Override
    public ScPlanAdapter.ViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHoder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_scplan_order, parent, false));
    }

    @Override
    public void onBindViewHolder(ScPlanAdapter.ViewHoder holder, int position) {
        //holder.setData(list, position);
    }

    @Override
    public int getItemCount() {
        // return list == null ? 0 : list.size();
        return 3;
    }

    public class ViewHoder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //姓名 时间  类型 状态
        private TextView tvName, tvDate, tvType, tvStatus;
        private ImageView ivPic;//订单图片
        private TextView tv_money, tv_status;//价钱 状态

        public ViewHoder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

//        public void setData(List<VideoOrderBean.MeetingMinutes> list, final int position) {
//
//        }

        @Override
        public void onClick(View view) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    /**
     * item的点击事件
     */
    private OnRecyclerViewItemClickListener mOnItemClickListener;

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

}
