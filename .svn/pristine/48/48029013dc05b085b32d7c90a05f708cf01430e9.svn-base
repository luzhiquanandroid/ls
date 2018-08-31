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
 * 生产计划详情进度
 */
public class ScPlanDetailProAdapter extends RecyclerView.Adapter<ScPlanDetailProAdapter.ViewHoder> {
    private List<String> list = new ArrayList<>();

    public ScPlanDetailProAdapter(List<String> list) {
        this.list = list;
    }

    @Override
    public ScPlanDetailProAdapter.ViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHoder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_detail_pro, parent, false));
    }

    @Override
    public void onBindViewHolder(ScPlanDetailProAdapter.ViewHoder holder, int position) {
        holder.setData(list, position);
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
        private View view_show;

        public ViewHoder(View itemView) {
            super(itemView);
            view_show = itemView.findViewById(R.id.view_show);
            itemView.setOnClickListener(this);
        }

        public void setData(List<String> list, final int position) {
            if (position == 3 - 1) {
                view_show.setVisibility(View.GONE);
            } else {
                view_show.setVisibility(View.VISIBLE);
            }
        }

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
