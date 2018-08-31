package com.qysd.lawtree.lawtreefragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.QueryDirectionEnum;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.netease.nimlib.sdk.team.model.Team;
import com.netease.nimlib.sdk.team.model.TeamMember;
import com.qysd.lawtree.R;
import com.qysd.lawtree.lawtreeactivity.AddFriendActivity;
import com.qysd.lawtree.lawtreeactivity.ScanZxingCode;
import com.qysd.lawtree.lawtreeactivity.SendQunLiaoActivity;
import com.qysd.lawtree.lawtreebase.BaseFragment;
import com.qysd.lawtree.lawtreebusbean.RedCountEventBusBean;
import com.qysd.lawtree.lawtreeutils.CommonPopupWindow;
import com.qysd.uikit.api.NimUIKit;
import com.qysd.uikit.api.model.contact.ContactChangedObserver;
import com.qysd.uikit.api.model.main.OnlineStateChangeObserver;
import com.qysd.uikit.api.model.team.TeamDataChangedObserver;
import com.qysd.uikit.api.model.team.TeamMemberDataChangedObserver;
import com.qysd.uikit.api.model.user.UserInfoObserver;
import com.qysd.uikit.business.recent.RecentContactsCallback;
import com.qysd.uikit.business.recent.TeamMemberAitHelper;
import com.qysd.uikit.business.recent.adapter.RecentContactAdapter;
import com.qysd.uikit.business.uinfo.UserInfoHelper;
import com.qysd.uikit.common.badger.Badger;
import com.qysd.uikit.common.ui.dialog.CustomAlertDialog;
import com.qysd.uikit.common.ui.drop.DropCover;
import com.qysd.uikit.common.ui.drop.DropManager;
import com.qysd.uikit.common.ui.recyclerview.listener.SimpleClickListener;
import com.qysd.uikit.impl.NimUIKitImpl;
import com.qysd.uikit.impl.cache.TeamDataCache;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

import static com.qysd.uikit.business.recent.RecentContactsFragment.RECENT_TAG_STICKY;


/**
 * Created by zhouwei on 17/4/23.
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private String mFrom;
    private LinearLayout ll_send_qunliao, ll_saoyisao, ll_add_friend;
    private CommonPopupWindow window;
    private CommonPopupWindow.LayoutGravity layoutGravity;
    private RecyclerView recycler_view;
    private ImageView iv_xiaoxi_add;
    private List<RecentContact> items;
    private List<RecentContact> loadedRecents;
    private Map<String, RecentContact> cached; // 暂缓刷上列表的数据（未读数红点拖拽动画运行时用）

    private RecentContactAdapter adapter;

    private boolean msgLoaded = false;

    private RecentContactsCallback callback;

    private UserInfoObserver userInfoObserver;

    public static HomeFragment newInstance(String from) {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.home_fragment_layout;
    }

    @Override
    protected void initView() {
        findViews();
        initMessageList();
        requestMessages(false);
        registerObservers(true);
        registerDropCompletedListener(true);
        registerOnlineStateChangeListener(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        msgLoaded = false;
        Log.e("lzq", "onResume");
        initMessageList();
        requestMessages(false);
        registerObservers(true);
        registerDropCompletedListener(true);
        registerOnlineStateChangeListener(true);
        notifyDataSetChanged();
    }

    @Override
    protected void bindListener() {
        iv_xiaoxi_add.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initNav() {

    }

    @Override
    protected void loadData() {
        msgLoaded = false;
        initMessageList();
        requestMessages(true);
        registerObservers(true);
        registerDropCompletedListener(true);
        registerOnlineStateChangeListener(true);
    }

    /**
     * 查找页面控件
     */
    private void findViews() {
        recycler_view = getView().findViewById(R.id.recycler_view);
        iv_xiaoxi_add = getView().findViewById(R.id.iv_xiaoxi_add);

        window = new CommonPopupWindow(getActivity(), R.layout.add_popupwindow_layout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT) {
            @Override
            protected void initView() {
                View view = getContentView();
                ll_send_qunliao = view.findViewById(R.id.ll_send_qunliao);
                ll_saoyisao = view.findViewById(R.id.ll_saoyisao);
                ll_add_friend = view.findViewById(R.id.ll_add_friend);
            }

            @Override
            protected void initEvent() {
                ll_send_qunliao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        window.getPopupWindow().dismiss();
                        startActivity(new Intent(getActivity(), SendQunLiaoActivity.class));
                    }
                });
                ll_saoyisao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getActivity(), ScanZxingCode.class));
                        window.getPopupWindow().dismiss();
                        //Toast.makeText(context, "扫一扫", Toast.LENGTH_SHORT).show();
                    }
                });
                ll_add_friend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        window.getPopupWindow().dismiss();
                        startActivity(new Intent(getActivity(), AddFriendActivity.class));
                    }
                });
            }

            @Override
            protected void initWindow() {
                super.initWindow();
                getPopupWindow().setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                        lp.alpha = 1.0f;
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                        getActivity().getWindow().setAttributes(lp);
                    }
                });
            }
        };
        layoutGravity = new CommonPopupWindow.LayoutGravity(CommonPopupWindow.LayoutGravity.ALIGN_LEFT | CommonPopupWindow.LayoutGravity.TO_BOTTOM);
    }

    /**
     * 初始化消息列表
     */
    private void initMessageList() {
        items = new ArrayList<>();
        items.clear();
        cached = new HashMap<>(3);

        // adapter
        adapter = new RecentContactAdapter(recycler_view, items);
        initCallBack();
        adapter.setCallback(callback);

        // recyclerView
        recycler_view.setAdapter(adapter);
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler_view.addOnItemTouchListener(touchListener);

        // ios style
        OverScrollDecoratorHelper.setUpOverScroll(recycler_view, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);

        // drop listener
        DropManager.getInstance().setDropListener(new DropManager.IDropListener() {
            @Override
            public void onDropBegin() {
                touchListener.setShouldDetectGesture(false);
            }

            @Override
            public void onDropEnd() {
                touchListener.setShouldDetectGesture(true);
            }
        });
    }

    private void requestMessages(boolean delay) {
        if (msgLoaded) {
            return;
        }
        getHandler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if (msgLoaded) {
                    return;
                }
                // 查询最近联系人列表数据
                NIMClient.getService(MsgService.class).queryRecentContacts().setCallback(new RequestCallbackWrapper<List<RecentContact>>() {

                    @Override
                    public void onResult(int code, List<RecentContact> recents, Throwable exception) {
                        if (code != ResponseCode.RES_SUCCESS || recents == null) {
                            return;
                        }
                        loadedRecents = recents;
                        // 初次加载，更新离线的消息中是否有@我的消息
                        for (RecentContact loadedRecent : loadedRecents) {
                            if (loadedRecent.getSessionType() == SessionTypeEnum.Team) {
                                updateOfflineContactAited(loadedRecent);
                            }
                        }
                        // 此处如果是界面刚初始化，为了防止界面卡顿，可先在后台把需要显示的用户资料和群组资料在后台加载好，然后再刷新界面
                        //
                        msgLoaded = true;
                        if (isAdded()) {
                            onRecentContactsLoaded();
                        }
                    }
                });
            }
        }, delay ? 250 : 0);
    }

    /**
     * ********************** 收消息，处理状态变化 ************************
     */
    private void registerObservers(boolean register) {
        MsgServiceObserve service = NIMClient.getService(MsgServiceObserve.class);
        service.observeReceiveMessage(messageReceiverObserver, register);
        service.observeRecentContact(messageObserver, register);
        service.observeMsgStatus(statusObserver, register);
        service.observeRecentContactDeleted(deleteObserver, register);

        registerTeamUpdateObserver(register);
        registerTeamMemberUpdateObserver(register);
        NimUIKit.getContactChangedObservable().registerObserver(friendDataChangedObserver, register);
        if (register) {
            registerUserInfoObserver();
        } else {
            unregisterUserInfoObserver();
        }
    }

    /**
     * 注册群信息&群成员更新监听
     */
    private void registerTeamUpdateObserver(boolean register) {
        NimUIKit.getTeamChangedObservable().registerTeamDataChangedObserver(teamDataChangedObserver, register);
    }

    private void registerTeamMemberUpdateObserver(boolean register) {
        NimUIKit.getTeamChangedObservable().registerTeamMemberDataChangedObserver(teamMemberDataChangedObserver, register);
    }

    private void registerDropCompletedListener(boolean register) {
        if (register) {
            DropManager.getInstance().addDropCompletedListener(dropCompletedListener);
        } else {
            DropManager.getInstance().removeDropCompletedListener(dropCompletedListener);
        }
    }

    // 暂存消息，当RecentContact 监听回来时使用，结束后清掉
    private Map<String, Set<IMMessage>> cacheMessages = new HashMap<>();

    //监听在线消息中是否有@我
    private Observer<List<IMMessage>> messageReceiverObserver = new Observer<List<IMMessage>>() {
        @Override
        public void onEvent(List<IMMessage> imMessages) {
            if (imMessages != null) {
                for (IMMessage imMessage : imMessages) {
                    if (!TeamMemberAitHelper.isAitMessage(imMessage)) {
                        continue;
                    }
                    Set<IMMessage> cacheMessageSet = cacheMessages.get(imMessage.getSessionId());
                    if (cacheMessageSet == null) {
                        cacheMessageSet = new HashSet<>();
                        cacheMessages.put(imMessage.getSessionId(), cacheMessageSet);
                    }
                    cacheMessageSet.add(imMessage);
                }
            }
        }
    };

    Observer<List<RecentContact>> messageObserver = new Observer<List<RecentContact>>() {
        @Override
        public void onEvent(List<RecentContact> recentContacts) {
            Log.e("lzq_unread", NIMClient.getService(MsgService.class).getTotalUnreadCount() + "");
            EventBus.getDefault().post(new RedCountEventBusBean("xiaoxi", NIMClient.getService(MsgService.class).getTotalUnreadCount()));
            if (!DropManager.getInstance().isTouchable()) {
                // 正在拖拽红点，缓存数据
                for (RecentContact r : recentContacts) {
                    cached.put(r.getContactId(), r);
                }
                return;
            }

            onRecentContactChanged(recentContacts);
        }
    };

    private void onRecentContactChanged(List<RecentContact> recentContacts) {
        int index;
        for (RecentContact r : recentContacts) {
            index = -1;
            for (int i = 0; i < items.size(); i++) {
                if (r.getContactId().equals(items.get(i).getContactId())
                        && r.getSessionType() == (items.get(i).getSessionType())) {
                    index = i;
                    break;
                }
            }

            if (index >= 0) {
                items.remove(index);
            }

            items.add(r);
            if (r.getSessionType() == SessionTypeEnum.Team && cacheMessages.get(r.getContactId()) != null) {
                TeamMemberAitHelper.setRecentContactAited(r, cacheMessages.get(r.getContactId()));
            }
        }

        cacheMessages.clear();

        refreshMessages(true);
    }

    DropCover.IDropCompletedListener dropCompletedListener = new DropCover.IDropCompletedListener() {
        @Override
        public void onCompleted(Object id, boolean explosive) {
            if (cached != null && !cached.isEmpty()) {
                // 红点爆裂，已经要清除未读，不需要再刷cached
                if (explosive) {
                    if (id instanceof RecentContact) {
                        RecentContact r = (RecentContact) id;
                        cached.remove(r.getContactId());
                    } else if (id instanceof String && ((String) id).contentEquals("0")) {
                        cached.clear();
                    }
                }

                // 刷cached
                if (!cached.isEmpty()) {
                    List<RecentContact> recentContacts = new ArrayList<>(cached.size());
                    recentContacts.addAll(cached.values());
                    cached.clear();

                    onRecentContactChanged(recentContacts);
                }
            }
        }
    };

    Observer<IMMessage> statusObserver = new Observer<IMMessage>() {
        @Override
        public void onEvent(IMMessage message) {
            int index = getItemIndex(message.getUuid());
            if (index >= 0 && index < items.size()) {
                RecentContact item = items.get(index);
                item.setMsgStatus(message.getStatus());
                refreshViewHolderByIndex(index);
            }
        }
    };

    Observer<RecentContact> deleteObserver = new Observer<RecentContact>() {
        @Override
        public void onEvent(RecentContact recentContact) {
            if (recentContact != null) {
                for (RecentContact item : items) {
                    if (TextUtils.equals(item.getContactId(), recentContact.getContactId())
                            && item.getSessionType() == recentContact.getSessionType()) {
                        items.remove(item);
                        refreshMessages(true);
                        break;
                    }
                }
            } else {
                items.clear();
                refreshMessages(true);
            }
        }
    };

    TeamDataChangedObserver teamDataChangedObserver = new TeamDataChangedObserver() {

        @Override
        public void onUpdateTeams(List<Team> teams) {
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onRemoveTeam(Team team) {

        }
    };

    TeamMemberDataChangedObserver teamMemberDataChangedObserver = new TeamMemberDataChangedObserver() {
        @Override
        public void onUpdateTeamMember(List<TeamMember> members) {
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onRemoveTeamMember(List<TeamMember> member) {

        }
    };

    private int getItemIndex(String uuid) {
        for (int i = 0; i < items.size(); i++) {
            RecentContact item = items.get(i);
            if (TextUtils.equals(item.getRecentMessageId(), uuid)) {
                return i;
            }
        }

        return -1;
    }

    protected void refreshViewHolderByIndex(final int index) {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                adapter.notifyItemChanged(index);
            }
        });
    }

    public void setCallback(RecentContactsCallback callback) {
        this.callback = callback;
    }

    private void registerUserInfoObserver() {
        if (userInfoObserver == null) {
            userInfoObserver = new UserInfoObserver() {
                @Override
                public void onUserInfoChanged(List<String> accounts) {
                    refreshMessages(false);
                }
            };
        }
        NimUIKit.getUserInfoObservable().registerObserver(userInfoObserver, true);
    }

    private void unregisterUserInfoObserver() {
        if (userInfoObserver != null) {
            NimUIKit.getUserInfoObservable().registerObserver(userInfoObserver, false);
        }
    }

    ContactChangedObserver friendDataChangedObserver = new ContactChangedObserver() {
        @Override
        public void onAddedOrUpdatedFriends(List<String> accounts) {
            refreshMessages(false);
        }

        @Override
        public void onDeletedFriends(List<String> accounts) {
            refreshMessages(false);
        }

        @Override
        public void onAddUserToBlackList(List<String> account) {
            refreshMessages(false);
        }

        @Override
        public void onRemoveUserFromBlackList(List<String> account) {
            refreshMessages(false);
        }
    };

    private void updateOfflineContactAited(final RecentContact recentContact) {
        if (recentContact == null || recentContact.getSessionType() != SessionTypeEnum.Team
                || recentContact.getUnreadCount() <= 0) {
            return;
        }

        // 锚点
        List<String> uuid = new ArrayList<>(1);
        uuid.add(recentContact.getRecentMessageId());

        List<IMMessage> messages = NIMClient.getService(MsgService.class).queryMessageListByUuidBlock(uuid);

        if (messages == null || messages.size() < 1) {
            return;
        }
        final IMMessage anchor = messages.get(0);

        // 查未读消息
        NIMClient.getService(MsgService.class).queryMessageListEx(anchor, QueryDirectionEnum.QUERY_OLD,
                recentContact.getUnreadCount() - 1, false).setCallback(new RequestCallbackWrapper<List<IMMessage>>() {

            @Override
            public void onResult(int code, List<IMMessage> result, Throwable exception) {
                if (code == ResponseCode.RES_SUCCESS && result != null) {
                    result.add(0, anchor);
                    Set<IMMessage> messages = null;
                    // 过滤存在的@我的消息
                    for (IMMessage msg : result) {
                        if (TeamMemberAitHelper.isAitMessage(msg)) {
                            if (messages == null) {
                                messages = new HashSet<>();
                            }
                            messages.add(msg);
                        }
                    }

                    // 更新并展示
                    if (messages != null) {
                        TeamMemberAitHelper.setRecentContactAited(recentContact, messages);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

    }

    private void registerOnlineStateChangeListener(boolean register) {
        if (!NimUIKitImpl.enableOnlineState()) {
            return;
        }
        NimUIKitImpl.getOnlineStateChangeObservable().registerOnlineStateChangeListeners(onlineStateChangeObserver, register);
    }
    private void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
        boolean empty = items.isEmpty() && msgLoaded;
    }
    private void refreshMessages(boolean unreadChanged) {
        sortRecentContacts(items);
        adapter.notifyDataSetChanged();

        if (unreadChanged) {

            // 方式一：累加每个最近联系人的未读（快）

            int unreadNum = 0;
            for (RecentContact r : items) {
                unreadNum += r.getUnreadCount();
            }

            // 方式二：直接从SDK读取（相对慢）
            //int unreadNum = NIMClient.getService(MsgService.class).getTotalUnreadCount();

            if (callback != null) {
                callback.onUnreadCountChange(unreadNum);
            }

            Badger.updateBadgerCount(unreadNum);
        }
    }

    /**
     * **************************** 排序 ***********************************
     */
    private void sortRecentContacts(List<RecentContact> list) {
        if (list.size() == 0) {
            return;
        }
        Collections.sort(list, comp);
    }

    private static Comparator<RecentContact> comp = new Comparator<RecentContact>() {

        @Override
        public int compare(RecentContact o1, RecentContact o2) {
            // 先比较置顶tag
            long sticky = (o1.getTag() & RECENT_TAG_STICKY) - (o2.getTag() & RECENT_TAG_STICKY);
            if (sticky != 0) {
                return sticky > 0 ? -1 : 1;
            } else {
                long time = o1.getTime() - o2.getTime();
                return time == 0 ? 0 : (time > 0 ? -1 : 1);
            }
        }
    };
    OnlineStateChangeObserver onlineStateChangeObserver = new OnlineStateChangeObserver() {
        @Override
        public void onlineStateChange(Set<String> accounts) {
            adapter.notifyDataSetChanged();
        }
    };

    private void onRecentContactsLoaded() {
        items.clear();
        if (loadedRecents != null) {
            items.addAll(loadedRecents);
            loadedRecents = null;
        }
        refreshMessages(true);

        if (callback != null) {
            callback.onRecentContactsLoaded();
        }
    }

    private void initCallBack() {
        if (callback != null) {
            return;
        }
        callback = new RecentContactsCallback() {
            @Override
            public void onRecentContactsLoaded() {

            }

            @Override
            public void onUnreadCountChange(int unreadCount) {

            }

            @Override
            public void onItemClick(RecentContact recent) {
                if (recent.getSessionType() == SessionTypeEnum.Team) {
                    NimUIKit.startTeamSession(getActivity(), recent.getContactId());
                } else if (recent.getSessionType() == SessionTypeEnum.P2P) {
                    NimUIKit.startP2PSession(getActivity(), recent.getContactId());
                }
            }

            @Override
            public String getDigestOfAttachment(RecentContact recentContact, MsgAttachment attachment) {
                return null;
            }

            @Override
            public String getDigestOfTipMsg(RecentContact recent) {
                return null;
            }
        };
    }

    private SimpleClickListener<RecentContactAdapter> touchListener = new SimpleClickListener<RecentContactAdapter>() {
        @Override
        public void onItemClick(RecentContactAdapter adapter, View view, int position) {
            if (callback != null) {
                RecentContact recent = adapter.getItem(position);
                callback.onItemClick(recent);
            }
        }

        @Override
        public void onItemLongClick(RecentContactAdapter adapter, View view, int position) {
            showLongClickMenu(adapter.getItem(position), position);
        }

        @Override
        public void onItemChildClick(RecentContactAdapter adapter, View view, int position) {

        }

        @Override
        public void onItemChildLongClick(RecentContactAdapter adapter, View view, int position) {

        }
    };

    private void showLongClickMenu(final RecentContact recent, final int position) {
        CustomAlertDialog alertDialog = new CustomAlertDialog(getActivity());
        alertDialog.setTitle(UserInfoHelper.getUserTitleName(recent.getContactId(), recent.getSessionType()));
        String title = getString(R.string.main_msg_list_delete_chatting);
        alertDialog.addItem(title, new CustomAlertDialog.onSeparateItemClickListener() {
            @Override
            public void onClick() {
                // 删除会话，删除后，消息历史被一起删除
                NIMClient.getService(MsgService.class).deleteRecentContact(recent);
                NIMClient.getService(MsgService.class).clearChattingHistory(recent.getContactId(), recent.getSessionType());
                adapter.remove(position);

                postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        refreshMessages(true);
                    }
                });
            }
        });

        title = (isTagSet(recent, RECENT_TAG_STICKY) ? getString(R.string.main_msg_list_clear_sticky_on_top) : getString(R.string.main_msg_list_sticky_on_top));
        alertDialog.addItem(title, new CustomAlertDialog.onSeparateItemClickListener() {
            @Override
            public void onClick() {
                if (isTagSet(recent, RECENT_TAG_STICKY)) {
                    removeTag(recent, RECENT_TAG_STICKY);
                } else {
                    addTag(recent, RECENT_TAG_STICKY);
                }
                NIMClient.getService(MsgService.class).updateRecent(recent);

                refreshMessages(false);
            }
        });

        alertDialog.addItem("删除该聊天（仅服务器）", new CustomAlertDialog.onSeparateItemClickListener() {
            @Override
            public void onClick() {
                NIMClient.getService(MsgService.class)
                        .deleteRoamingRecentContact(recent.getContactId(), recent.getSessionType())
                        .setCallback(new RequestCallback<Void>() {
                            @Override
                            public void onSuccess(Void param) {
                                Toast.makeText(getActivity(), "delete success", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailed(int code) {
                                Toast.makeText(getActivity(), "delete failed, code:" + code, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onException(Throwable exception) {

                            }
                        });
            }
        });
        alertDialog.show();
    }

    private void addTag(RecentContact recent, long tag) {
        tag = recent.getTag() | tag;
        recent.setTag(tag);
    }

    private static final Handler handler = new Handler();

    protected final void postRunnable(final Runnable runnable) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                // validate
                // TODO use getActivity ?
                if (!isAdded()) {
                    return;
                }

                // run
                runnable.run();
            }
        });
    }

    private void removeTag(RecentContact recent, long tag) {
        tag = recent.getTag() & ~tag;
        recent.setTag(tag);
    }

    private boolean isTagSet(RecentContact recent, long tag) {
        return (recent.getTag() & tag) == tag;
    }

    protected final Handler getHandler() {
        return handler;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_xiaoxi_add:
                window.showBashOfAnchor(iv_xiaoxi_add, layoutGravity, 0, 0);
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 0.3f;
                getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                getActivity().getWindow().setAttributes(lp);
                break;
        }
    }


}