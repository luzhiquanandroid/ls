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
 * 生产管理已完成和待处理适配器
 */
public class ScDateAdapter extends RecyclerView.Adapter<ScDateAdapter.ViewHoder> {
    private List<String> list = new ArrayList<>();

    public ScDateAdapter(List<String> list) {
        this.list = list;
    }

    @Override
    public ScDateAdapter.ViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHoder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_scdate_order, parent, false));
    }

    @Override
    public void onBindViewHolder(ScDateAdapter.ViewHoder holder, int position) {
        holder.setData(list, position, holder);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tv_num, tvDate, tvType, tvStatus;
        private ImageView iv_move, iv_up;
        private TextView tv_money, tv_status;//价钱 状态

        public ViewHoder(View itemView) {
            super(itemView);
            tv_num = itemView.findViewById(R.id.tv_num);
            iv_move = itemView.findViewById(R.id.iv_move);
            iv_up = itemView.findViewById(R.id.iv_up);
            itemView.setOnClickListener(this);
        }

        public void setData(List<String> list, final int position, final ViewHoder viewHoder) {
            tv_num.setTextColor(itemView.getResources().getColor(R.color.blue_01));
            tv_num.setText(list.get(position));
//            iv_move.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
//                        mDragStartListener.onStartDrag(holder);
//                    }
//                    return false;
//                }
//            });
            if (0 == position) {
                iv_up.setVisibility(View.GONE);
            }
            iv_move.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onAcceptClickListener != null) {
                        onAcceptClickListener.onAccept(v, position, viewHoder);
                    }
                }
            });
            iv_up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onUpClickListener != null) {
                        onUpClickListener.onUp(v, position, viewHoder);
                    }
                }
            });
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

    private ScDateAdapter.OnAcceptClickListener onAcceptClickListener;

    public interface OnAcceptClickListener {
        void onAccept(View view, int position, ViewHoder viewHoder);
    }

    public void setOnAcceptClickListener(ScDateAdapter.OnAcceptClickListener listener) {
        this.onAcceptClickListener = listener;
    }

    private ScDateAdapter.OnUpClickListener onUpClickListener;

    public interface OnUpClickListener {
        void onUp(View view, int position, ViewHoder viewHoder);
    }

    public void setOnUpClickListener(ScDateAdapter.OnUpClickListener listener) {
        this.onUpClickListener = listener;
    }
}