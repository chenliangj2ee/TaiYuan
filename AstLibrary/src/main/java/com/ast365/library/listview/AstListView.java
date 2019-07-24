/**
 * @file XListView.java
 * @package me.maxwin.view
 * @create Mar 18, 2012 6:28:41 PM
 * @author Maxwin
 * @description An ListView support (a) Pull down to refresh, (b) Pull up to load more.
 * Implement RefreshListener, and see stopRefresh() / stopLoadMore().
 */
package com.ast365.library.listview;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.ast365.library.R;

import java.text.SimpleDateFormat;
import java.util.Date;


public class AstListView extends ListView implements OnScrollListener {

    private final static int SCROLLBACK_HEADER = 0;
    private final static int SCROLLBACK_FOOTER = 1;
    private final static int SCROLL_DURATION = 400; // scroll ast_back duration
    private final static int PULL_LOAD_MORE_DELTA = 50; // when pull up >= 50px
    // at bottom, trigger
    // load more.
    private final static float OFFSET_RADIO = 1.8f; // support iOS like pull
    public int currentPage = 0;
    View emptyView;
    private float mLastY = -1; // save event y
    private Scroller mScroller; // used for scroll ast_back
    private OnScrollListener mScrollListener; // user's scroll listener
    // the interface to trigger refresh and load more.
    private RefreshListener mListViewListener;
    // -- header view
    private AstHeader mHeaderView;
    // header view content, use it to calculate the Header's height. And hide it
    // when disable pull refresh.
    private RelativeLayout mHeaderViewContent;
    private TextView mHeaderTimeView;
    private int mHeaderViewHeight; // header view's height
    private boolean mEnablePullRefresh = true;
    private boolean mPullRefreshing = false; // is refreashing.
    // -- footer view
    private AstFooter mFooterView;
    private boolean mEnablePullLoad = true;
    private boolean mPullLoading;
    private boolean mIsFooterReady = false;
    // total list items, used to detect is at the bottom of listview.
    private int mTotalItemCount;
    // for mScroller, scroll ast_back from header or footer.
    private int mScrollBack;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    AstListView.this.stopRefresh();
                    break;
                case 1:
                    AstListView.this.stopLoadMore();
                    break;
            }

            super.handleMessage(msg);
        }

    };

    /**
     * @param context
     */
    public AstListView(Context context) {
        super(context);
        initWithContext(context);
    }
    // feature.

    public AstListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWithContext(context);
    }

    public AstListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initWithContext(context);
    }

    public void stop(){
         stopLoadMore();
         stopRefresh();
    }
    public int getCurrentPage() {
        Log.i("currentPage", "currentPage:" + currentPage);
        return currentPage;
    }

    public void setCurrentPage(int page) {
        Log.i("currentPage", "currentPage:" + currentPage);
        this.currentPage = page;
    }

    public void nextPage() {
        this.currentPage = currentPage + 1;
        Log.i("currentPage", "currentPage:" + currentPage);

    }

    public void reset() {
        this.currentPage = 0;
        final float deltaY = mHeaderViewContent.getHeight();
        this.setSelection(0);
        mHeaderView.setVisiableHeight((int) deltaY);
        if (getFirstVisiblePosition() == 0
                && (mHeaderView.getVisiableHeight() > 0 || deltaY > 0)) {
            updateHeaderHeight(deltaY);
            invokeOnScrolling();
        }
        if (getFirstVisiblePosition() == 0) {

            mPullRefreshing = true;
            mHeaderView.setState(AstHeader.STATE_REFRESHING);
            if (mListViewListener != null) {
                setRefreshTime();
                handler.sendEmptyMessageDelayed(0, 10000);
            }
            resetHeaderHeight();
        }


    }

    private void initWithContext(Context context) {
        this.setCacheColorHint(Color.TRANSPARENT);
        mScroller = new Scroller(context, new DecelerateInterpolator());
        // XListView need the scroll event, and it will dispatch the event to
        // user's listener (as a proxy).
        super.setOnScrollListener(this);

        // init header view
        mHeaderView = new AstHeader(context);
        mHeaderViewContent = (RelativeLayout) mHeaderView
                .findViewById(R.id.xlistview_header_content);
        mHeaderTimeView = (TextView) mHeaderView
                .findViewById(R.id.xlistview_header_time);
        addHeaderView(mHeaderView);
        // init footer view
        mFooterView = new AstFooter(context);

        // init header height
        mHeaderView.getViewTreeObserver().addOnGlobalLayoutListener(
                new OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mHeaderViewHeight = mHeaderViewContent.getHeight();
                        getViewTreeObserver()
                                .removeGlobalOnLayoutListener(this);
                    }
                });
        setRefreshTime();
        setPullLoadEnable(true);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        // make sure XListViewFooter is the last footer view, and only add once.
        if (mIsFooterReady == false) {
            mIsFooterReady = true;
            addFooterView(mFooterView);
        }
        super.setAdapter(adapter);

    }

    /**
     * enable or disable pull down refresh feature.
     *
     * @param enable
     */
    public void setPullRefreshEnable(boolean enable) {
        mEnablePullRefresh = enable;
        if (!mEnablePullRefresh) { // disable, hide the content
            mHeaderViewContent.setVisibility(View.INVISIBLE);
        } else {
            mHeaderViewContent.setVisibility(View.VISIBLE);
        }
    }

    /**
     * enable or disable pull up load more feature.
     *
     * @param enable
     */
    public void setPullLoadEnable(boolean enable) {
        mEnablePullLoad = enable;
        if (!mEnablePullLoad) {
            mFooterView.hide();
            mFooterView.setOnClickListener(null);
        } else {
            mPullLoading = false;
            mFooterView.show();
            mFooterView.setState(AstFooter.STATE_NORMAL);
            // both "pull up" and "click" will invoke load more.
            mFooterView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    startLoadMore();
                }
            });
        }
    }

    /**
     * stop refresh, reset header view.
     */
    public void stopRefresh() {
//        if (mPullRefreshing == true) {
//            mPullRefreshing = false;
//            resetHeaderHeight();
//        }
        resetHeaderHeight();
    }

    /**
     * stop load more, reset footer view.
     */
    public void stopLoadMore() {
//        if (mPullLoading == true) {
//            mPullLoading = false;
//            mFooterView.setState(AstFooter.STATE_NORMAL);
//        }
        mFooterView.setState(AstFooter.STATE_NORMAL);
    }

    /**
     * set last refresh time
     *
     * @param time
     */
    public void setRefreshTime(String time) {
        mHeaderTimeView.setText(time);
    }

    /**
     * set last refresh time
     *
     * @param time
     */
    private void setRefreshTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日  HH:mm");
        String date = format.format(new Date());
        mHeaderTimeView.setText(date);
    }

    private void invokeOnScrolling() {
        if (mScrollListener instanceof OnAstScrollListener) {
            OnAstScrollListener l = (OnAstScrollListener) mScrollListener;
            l.onAstScrolling(this);
        }
    }

    private void updateHeaderHeight(float delta) {
        mHeaderView.setVisiableHeight((int) delta
                + mHeaderView.getVisiableHeight());
        if (mEnablePullRefresh && !mPullRefreshing) { // 鏈浜庡埛鏂扮姸鎬侊紝鏇存柊绠ご
            if (mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
                mHeaderView.setState(AstHeader.STATE_READY);
            } else {
                mHeaderView.setState(AstHeader.STATE_NORMAL);
            }
        }
        if (emptyView != null)
            emptyView.setY(getY() + (int) delta
                    + mHeaderView.getVisiableHeight());
        setSelection(0); // scroll to top each time
    }

    public void setEmptyView(Activity act, final View emptyView) {
        this.emptyView = emptyView;
        this.emptyView.setBackgroundColor(Color.WHITE);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        act.addContentView(emptyView, params);
    }

    @Override
    public void addHeaderView(View v) {
        super.addHeaderView(v);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        initEmptyView();

    }

    private void initEmptyView() {
        ListAdapter adapter = getAdapter();
        boolean empty = ((adapter == null) || adapter.isEmpty());
//		if(super.getChildCount()<=getHeaderViewsCount()+getFooterViewsCount()){
        if (empty) {
            if (emptyView != null) {
                this.emptyView.setVisibility(View.VISIBLE);
                emptyView.setMinimumWidth(getWidth());
                emptyView.setMinimumHeight(getHeight());
                emptyView.setX(getX());
                emptyView.setY(getY() + mHeaderView.getVisiableHeight());
            }
//            setPullLoadEnable(false);
        } else {
            if (emptyView != null) {
                this.emptyView.setVisibility(View.GONE);
            }
//            setPullLoadEnable(true);
        }
    }

    /**
     * reset header view's height.
     */
    private void resetHeaderHeight() {
        int height = mHeaderView.getVisiableHeight();
        if (height == 0) // not visible.
            return;
        // refreshing and header isn't shown fully. do nothing.
        if (mPullRefreshing && height <= mHeaderViewHeight) {
            return;
        }
        int finalHeight = 0; // default: scroll ast_back to dismiss header.
        // is refreshing, just scroll ast_back to show all the header.
        if (mPullRefreshing && height > mHeaderViewHeight) {
            finalHeight = mHeaderViewHeight;
        }
        mScrollBack = SCROLLBACK_HEADER;
        mScroller.startScroll(0, height, 0, finalHeight - height,
                SCROLL_DURATION);
        // trigger computeScroll
        invalidate();
        postInvalidate();
    }

    private void updateFooterHeight(float delta) {
        int height = mFooterView.getBottomMargin() + (int) delta;
        if (mEnablePullLoad && !mPullLoading) {
            if (height > PULL_LOAD_MORE_DELTA) { // height enough to invoke load
                // more.
                mFooterView.setState(AstFooter.STATE_READY);
            } else {
                mFooterView.setState(AstFooter.STATE_NORMAL);
            }
        }
        mFooterView.setBottomMargin(height);

    }

    private void resetFooterHeight() {
        int bottomMargin = mFooterView.getBottomMargin();
        if (bottomMargin > 0) {
            mScrollBack = SCROLLBACK_FOOTER;
            mScroller.startScroll(0, bottomMargin, 0, -bottomMargin,
                    SCROLL_DURATION);
            invalidate();
        }
    }

    private void startLoadMore() {
        mPullLoading = true;
        mFooterView.setState(AstFooter.STATE_LOADING);
        if (mListViewListener != null) {
            mListViewListener.onLoadMore();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mListViewListener == null) {
            return super.onTouchEvent(ev);
        }
        if (mLastY == -1) {
            mLastY = ev.getRawY();
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaY = ev.getRawY() - mLastY;
                mLastY = ev.getRawY();
                if (getFirstVisiblePosition() == 0
                        && (mHeaderView.getVisiableHeight() > 0 || deltaY > 0)) {
                    // the first item is showing, header has shown or pull down.
                    updateHeaderHeight(deltaY / OFFSET_RADIO);
                    invokeOnScrolling();
                } else if (getLastVisiblePosition() == mTotalItemCount - 1
                        && (mFooterView.getBottomMargin() > 0 || deltaY < 0) && mEnablePullLoad) {
                    if (emptyView == null) {
                        updateFooterHeight(-deltaY / OFFSET_RADIO);
                    } else if (emptyView.getVisibility() == View.GONE) {
                        updateFooterHeight(-deltaY / OFFSET_RADIO);
                    } else {

                    }

                }
                break;
            default:
                mLastY = -1; // reset
                if (getFirstVisiblePosition() == 0) {
                    // invoke refresh
                    if (mEnablePullRefresh
                            && mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
                        mPullRefreshing = true;
                        mHeaderView.setState(AstHeader.STATE_REFRESHING);
                        if (mListViewListener != null) {
                            reset();
                            mListViewListener.onRefresh();
                            setRefreshTime();
                            handler.sendEmptyMessageDelayed(0, 10000);
                        }
                    }
                    resetHeaderHeight();
                } else if (getLastVisiblePosition() == mTotalItemCount - 1) {
                    // invoke load more.
                    if (mEnablePullLoad
                            && mFooterView.getBottomMargin() > PULL_LOAD_MORE_DELTA) {
                        startLoadMore();
                        handler.sendEmptyMessageDelayed(1, 10000);
                    }
                    resetFooterHeight();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            if (mScrollBack == SCROLLBACK_HEADER) {
                mHeaderView.setVisiableHeight(mScroller.getCurrY());
            } else {
                mFooterView.setBottomMargin(mScroller.getCurrY());
            }
            postInvalidate();
            invokeOnScrolling();
        }
        super.computeScroll();
    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        mScrollListener = l;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mScrollListener != null) {
            mScrollListener.onScrollStateChanged(view, scrollState);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        // send to user's listener
        mTotalItemCount = totalItemCount;
        if (mScrollListener != null) {
            mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount,
                    totalItemCount);
        }
    }

    public void setOnRefreshListener(RefreshListener l) {
        mListViewListener = l;
        if (mListViewListener != null) {
            mListViewListener.onRefresh();
        }
    }

    /**
     * you can listen ListView.OnScrollListener or this one. it will invoke
     * onAstScrolling when header/footer scroll ast_back.
     */
    public interface OnAstScrollListener extends OnScrollListener {
        public void onAstScrolling(View view);
    }

    /**
     * implements this interface to get refresh/load more event.
     */
    public interface RefreshListener {
        public void onRefresh();

        public void onLoadMore();
    }


}
