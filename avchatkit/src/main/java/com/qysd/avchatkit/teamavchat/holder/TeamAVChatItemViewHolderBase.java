package com.qysd.avchatkit.teamavchat.holder;


import com.qysd.avchatkit.common.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;
import com.qysd.avchatkit.common.recyclerview.holder.BaseViewHolder;
import com.qysd.avchatkit.common.recyclerview.holder.RecyclerViewHolder;
import com.qysd.avchatkit.teamavchat.module.TeamAVChatItem;

/**
 * Created by huangjun on 2017/5/9.
 */

abstract class TeamAVChatItemViewHolderBase extends RecyclerViewHolder<BaseMultiItemFetchLoadAdapter, BaseViewHolder, TeamAVChatItem> {

    TeamAVChatItemViewHolderBase(BaseMultiItemFetchLoadAdapter adapter) {
        super(adapter);
    }

    @Override
    public void convert(final BaseViewHolder holder, TeamAVChatItem data, int position, boolean isScrolling) {
        inflate(holder);
        refresh(data);
    }

    protected abstract void inflate(final BaseViewHolder holder);

    protected abstract void refresh(final TeamAVChatItem data);
}
