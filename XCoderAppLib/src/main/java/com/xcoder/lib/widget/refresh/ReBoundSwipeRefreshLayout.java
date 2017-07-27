//package com.xcoder.lib.widget.refresh;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.res.TypedArray;
//import android.support.annotation.ColorInt;
//import android.support.annotation.ColorRes;
//import android.support.annotation.Nullable;
//import android.support.annotation.VisibleForTesting;
//import android.support.v4.content.ContextCompat;
//import android.support.v4.view.MotionEventCompat;
//import android.support.v4.view.NestedScrollingChild;
//import android.support.v4.view.NestedScrollingChildHelper;
//import android.support.v4.view.NestedScrollingParent;
//import android.support.v4.view.NestedScrollingParentHelper;
//import android.support.v4.view.ViewCompat;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.util.AttributeSet;
//import android.util.DisplayMetrics;
//import android.util.Log;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewConfiguration;
//import android.view.ViewGroup;
//import android.view.animation.Animation;
//import android.view.animation.Animation.AnimationListener;
//import android.view.animation.DecelerateInterpolator;
//import android.view.animation.Transformation;
//import android.widget.AbsListView;
//
//
///**
// * Created by xz on 2017/7/1 0001.
// * 把官方的SwipeRefreshLayout弄下来了
// */
//
//public class ReBoundSwipeRefreshLayout extends ViewGroup implements NestedScrollingParent,
//        NestedScrollingChild {
//
//    //这两个变量决定下拉刷新的刷新图标的大小，一个标准图，一个大图
//    // Maps to ProgressBar.Large style
//    //指定界面上 ProgressBar 的样式
//    public static final int LARGE = RefreshDrawable.LARGE;
//    // Maps to ProgressBar default style
//    //指定界面上 ProgressBar 的样式
//    public static final int DEFAULT = RefreshDrawable.DEFAULT;
//
//    //圆的直径
//    @VisibleForTesting
//    static final int CIRCLE_DIAMETER = 40;
//
//    //大圆的直径
//    @VisibleForTesting
//    static final int CIRCLE_DIAMETER_LARGE = 56;
//
//    //日志标签
//    private static final String LOG_TAG = ReBoundSwipeRefreshLayout.class.getSimpleName();
//
//    //最大 透明度
//    private static final int MAX_ALPHA = 255;
//
//    //透明起始值
//    private static final int STARTING_PROGRESS_ALPHA = (int) (.3f * MAX_ALPHA);
//
//    //progressBar旋转的速度
//    private static final float DECELERATE_INTERPOLATION_FACTOR = 2f;
//
//    //无效的指针（???）
//    private static final int INVALID_POINTER = -1;
//
//    //阻率(阻尼效果)
//    private static final float DRAG_RATE = .5f;
//
//    // Max amount of circle that can be filled by progress during swipe gesture,
//    // where 1.0 is a full circle
//    //动作过程中可以通过进度来填充的圆圈量
//    //1是一个完整的圆
//    private static final float MAX_PROGRESS_ANGLE = .8f;
//
//    //最大的进步的角度(???)
//    private static final int SCALE_DOWN_DURATION = 150;
//
//    //透明动画时间()
//    private static final int ALPHA_ANIMATION_DURATION = 300;
//
//    //激活触发时间
//    private static final int ANIMATE_TO_TRIGGER_DURATION = 200;
//
//    //动画开始时间
//    private static final int ANIMATE_TO_START_DURATION = 200;
//
//    // Default background for the progress spinner
//    //进度微调器的默认后台
//    private static final int CIRCLE_BG_LIGHT = 0xFFFAFAFA;
//
//    // Default offset in dips from the top of the view to where the progress spinner should stop
//    //从视图顶部到进度旋转器应该停止的位置的默认偏移量
//    private static final int DEFAULT_CIRCLE_TARGET = 64;
//
//    //目标View（???）
//    private View mTarget; // the target of the gesture
//
//    //刷新监听
//    OnRefreshListener mListener;
//
//    //是否刷新
//    boolean mRefreshing = false;
//
//    //触摸(??)
//    private int mTouchSlop;
//
//    //总阻力距离
//    private float mTotalDragDistance = -1;
//
//    // If nested scrolling is enabled, the total amount that needed to be
//    // consumed by this as the nested scrolling parent is used in place of the
//    // overscroll determined by MOVE events in the onTouch handler
//    //如果嵌套滚动功能，需要消耗这个嵌套滚动母用于在触摸事件处理程序将确定OverScroll地方总量
//    private float mTotalUnconsumed;
//
//    //嵌套滚动父助手
//    private final NestedScrollingParentHelper mNestedScrollingParentHelper;
//
//    //嵌套滚动子助手
//    private final NestedScrollingChildHelper mNestedScrollingChildHelper;
//
//    //父滚动消耗(???)
//    private final int[] mParentScrollConsumed = new int[2];
//
//    //窗口偏移
//    private final int[] mParentOffsetInWindow = new int[2];
//
//    //嵌套滚动正在进行中
//    private boolean mNestedScrollInProgress;
//
//    //媒体动画持续时间
//    private int mMediumAnimationDuration;
//
//    //当前目标偏移顶
//    int mCurrentTargetOffsetTop;
//
//    //拖动时初始值Y
//    private float mInitialMotionY;
//
//    //下降多少
//    private float mInitialDownY;
//
//    //正在拖动
//    private boolean mIsBeingDragged;
//
//    //活动指针标识
//    private int mActivePointerId = INVALID_POINTER;
//
//    // Whether this item is scaled up rather than clipped
//    //这个项目是否扩大而不是裁剪
//    boolean mScale;
//
//    // Target is returning to its start offset because it was cancelled or a
//    // refresh was triggered.
//    //目标将返回到起始偏移量，因为它被取消或刷新被触发。
//    private boolean mReturningToStart;
//
//    //减速插补器
//    private final DecelerateInterpolator mDecelerateInterpolator;
//
//    //系统设置
//    private static final int[] LAYOUT_ATTRS = new int[]{
//            android.R.attr.enabled
//    };
//
//    //官方的圆View
//    RefreshCircleImageView mCircleView;
//
//    //圆视图索引
//    private int mCircleViewIndex = -1;
//
//    //来自
//    protected int mFrom;
//
//    //开始缩减(动画效果)
//    float mStartingScale;
//
//    //原始偏移顶
//    protected int mOriginalOffsetTop;
//
//    //微调偏移端
//    int mSpinnerOffsetEnd;
//
//    //旋转View
//    RefreshDrawable mProgress;
//
//    //偏移动画
//    private Animation mScaleAnimation;
//
//    //向下偏移动画
//    private Animation mScaleDownAnimation;
//
//    //透明开始动画
//    private Animation mAlphaStartAnimation;
//
//    //最大透明动画
//    private Animation mAlphaMaxAnimation;
//
//    //偏移向下开始动画
//    private Animation mScaleDownToStartAnimation;
//
//    //通知
//    boolean mNotify;
//
//    //圆的直径
//    private int mCircleDiameter;
//
//    // Whether the client has set a custom starting position;
//    //是否设置了自定义起始位置；
//    boolean mUsingCustomStart;
//
//    //滚动向上回调
//    private OnChildScrollUpCallback mChildScrollUpCallback;
//
//    //动画监听
//    private Animation.AnimationListener mRefreshListener = new Animation.AnimationListener() {
//        @Override
//        public void onAnimationStart(Animation animation) {
//        }
//
//        @Override
//        public void onAnimationRepeat(Animation animation) {
//        }
//
//        @SuppressLint("NewApi")
//        @Override
//        public void onAnimationEnd(Animation animation) {
//            //动画效果结束，开始调起刷新监听
//            if (mRefreshing) {
//                // Make sure the progress view is fully visible
//                //确保进度视图是完全可见的。
//                mProgress.setAlpha(MAX_ALPHA);
//                mProgress.start();
//                if (mNotify) {
//                    if (mListener != null) {
//                        mListener.onRefresh();
//                    }
//                }
//                //设置当前progress的偏移量
//                mCurrentTargetOffsetTop = mCircleView.getTop();
//            } else {
//                reset();
//            }
//        }
//    };
//
//    //重置
//    void reset() {
//        mCircleView.clearAnimation();
//        mProgress.stop();
//        mCircleView.setVisibility(View.GONE);
//        setColorViewAlpha(MAX_ALPHA);
//        // Return the circle to its start position
//        if (mScale) {
//            setAnimationProgress(0 /* animation complete and view is hidden */);
//        } else {
//            setTargetOffsetTopAndBottom(mOriginalOffsetTop - mCurrentTargetOffsetTop,
//                    true /* requires update */);
//        }
//        mCurrentTargetOffsetTop = mCircleView.getTop();
//    }
//
//    //设置是否被禁用  ？？？
//    @Override
//    public void setEnabled(boolean enabled) {
//        super.setEnabled(enabled);
//        if (!enabled) {
//            reset();
//        }
//    }
//
//    //当界面被隐藏时，需要重置刷新控件的动画效果
//    @Override
//    protected void onDetachedFromWindow() {
//        super.onDetachedFromWindow();
//        reset();
//    }
//
//    //设置颜色视图透明度
//    @SuppressLint("NewApi")
//    private void setColorViewAlpha(int targetAlpha) {
//        mCircleView.getBackground().setAlpha(targetAlpha);
//        mProgress.setAlpha(targetAlpha);
//    }
//
//    /**
//     * The refresh indicator starting and resting position is always positioned
//     * near the top of the refreshing content. This position is a consistent
//     * location, but can be adjusted in either direction based on whether or not
//     * there is a toolbar or actionbar present.
//     * 刷新指示器开始和静止位置总是处于位置。
//     * 在刷新内容的顶部。这个职位是一贯的。
//     * 位置，但可以根据是否有方向调整。
//     * 有一个工具栏或ActionBar目前。
//     * <p>
//     * <strong>Note:</strong> Calling this will reset the position of the refresh indicator to
//     * <code>start</code>.
//     * </p>
//     *
//     * @param scale Set to true if there is no view at a higher z-order than where the progress
//     *              spinner is set to appear. Setting it to true will cause indicator to be scaled
//     *              up rather than clipped.
//     *              设置为true，如果有更高的次序没有观点比在进步
//     *              旋转器设置为显示。将其设置为true将导致指示符为
//     *              上而不是剪下。
//     * @param start The offset in pixels from the top of this view at which the
//     *              progress spinner should appear.
//     *              此视图顶部的像素的偏移量，其中
//     *              进度微调器应该出现。
//     * @param end   The offset in pixels from the top of this view at which the
//     *              progress spinner should come to rest after a successful swipe
//     *              gesture.
//     *              此视图顶部的像素的偏移量，其中
//     *              进展顺利后，应该休息一下。
//     *              手势。
//     */
//    //设置进度视图偏移
//    public void setProgressViewOffset(boolean scale, int start, int end) {
//        mScale = scale;
//        mOriginalOffsetTop = start;
//        mSpinnerOffsetEnd = end;
//        mUsingCustomStart = true;
//        reset();
//        mRefreshing = false;
//    }
//
//    /**
//     * @return The offset in pixels from the top of this view at which the progress spinner should
//     * appear.
//     * 此视图顶部的像素偏移量，在该视图中进度微调器应该
//     * 出现。
//     */
//    //获取进度视图开始偏移
//    public int getProgressViewStartOffset() {
//        return mOriginalOffsetTop;
//    }
//
//    /**
//     * @return The offset in pixels from the top of this view at which the progress spinner should
//     * come to rest after a successful swipe gesture.
//     * 此视图顶部的像素偏移量，在该视图中进度微调器应该
//     * 一个成功的打击手势后休息。
//     */
//    //获取进度视图结束偏移
//    public int getProgressViewEndOffset() {
//        return mSpinnerOffsetEnd;
//    }
//
//    /**
//     * The refresh indicator resting position is always positioned near the top
//     * of the refreshing content. This position is a consistent location, but
//     * can be adjusted in either direction based on whether or not there is a
//     * toolbar or actionbar present.
//     *
//     * @param scale Set to true if there is no view at a higher z-order than where the progress
//     *              spinner is set to appear. Setting it to true will cause indicator to be scaled
//     *              up rather than clipped.
//     * @param end   The offset in pixels from the top of this view at which the
//     *              progress spinner should come to rest after a successful swipe
//     *              gesture.
//     */
//    public void setProgressViewEndTarget(boolean scale, int end) {
//        mSpinnerOffsetEnd = end;
//        mScale = scale;
//        mCircleView.invalidate();
//    }
//
//    /**
//     * One of DEFAULT, or LARGE.
//     */
//    public void setSize(int size) {
//        if (size != RefreshDrawable.LARGE && size != RefreshDrawable.DEFAULT) {
//            return;
//        }
//        final DisplayMetrics metrics = getResources().getDisplayMetrics();
//        if (size == RefreshDrawable.LARGE) {
//            mCircleDiameter = (int) (CIRCLE_DIAMETER_LARGE * metrics.density);
//        } else {
//            mCircleDiameter = (int) (CIRCLE_DIAMETER * metrics.density);
//        }
//        // force the bounds of the progress circle inside the circle view to
//        // update by setting it to null before updating its size and then
//        // re-setting it
//        mCircleView.setImageDrawable(null);
//        mProgress.updateSizes(size);
//        mCircleView.setImageDrawable(mProgress);
//    }
//
//    /**
//     * Simple constructor to use when creating a SwipeRefreshLayout from code.
//     *
//     * @param context
//     */
//    public ReBoundSwipeRefreshLayout(Context context) {
//        this(context, null);
//    }
//
//    /**
//     * Constructor that is called when inflating SwipeRefreshLayout from XML.
//     *
//     * @param context
//     * @param attrs
//     */
//    public ReBoundSwipeRefreshLayout(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        // 系统默认的最小滚动距离
//        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
//        // 系统默认的动画时长
//        mMediumAnimationDuration = getResources().getInteger(
//                android.R.integer.config_mediumAnimTime);
//
//        setWillNotDraw(false);
//        mDecelerateInterpolator = new DecelerateInterpolator(DECELERATE_INTERPOLATION_FACTOR);
//
//        // 刷新的圆圈的大小，单位转换成 sp
//        final DisplayMetrics metrics = getResources().getDisplayMetrics();
//        mCircleDiameter = (int) (CIRCLE_DIAMETER * metrics.density);
//        // 创建刷新动画的圆圈
//        createProgressView();
//        ViewCompat.setChildrenDrawingOrderEnabled(this, true);
//        // the absolute offset has to take into account that the circle starts at an offset
//        mSpinnerOffsetEnd = (int) (DEFAULT_CIRCLE_TARGET * metrics.density);
//
//        // 刷新动画的临界距离值
//        mTotalDragDistance = mSpinnerOffsetEnd;
//
//        // 通过 NestedScrolling 机制来处理嵌套滚动
//        mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);
//
//        mNestedScrollingChildHelper = new NestedScrollingChildHelper(this);
//        setNestedScrollingEnabled(true);
//
//        mOriginalOffsetTop = mCurrentTargetOffsetTop = -mCircleDiameter;
//        moveToStart(1.0f);
//
//        // 获取 xml 中定义的属性
//        final TypedArray a = context.obtainStyledAttributes(attrs, LAYOUT_ATTRS);
//        setEnabled(a.getBoolean(0, true));
//        a.recycle();
//    }
//
//    @Override
//    protected int getChildDrawingOrder(int childCount, int i) {
//        if (mCircleViewIndex < 0) {
//            return i;
//        } else if (i == childCount - 1) {
//            // Draw the selected child last
//            return mCircleViewIndex;
//        } else if (i >= mCircleViewIndex) {
//            // Move the children after the selected child earlier one
//            return i + 1;
//        } else {
//            // Keep the children before the selected child the same
//            return i;
//        }
//    }
//
//    private void createProgressView() {
//        mCircleView = new RefreshCircleImageView(getContext(), CIRCLE_BG_LIGHT);
//        mProgress = new RefreshDrawable(getContext(), this);
//        mProgress.setBackgroundColor(CIRCLE_BG_LIGHT);
//        mCircleView.setImageDrawable(mProgress);
//        mCircleView.setVisibility(View.GONE);
//        addView(mCircleView);
//    }
//
//    /**
//     * Set the listener to be notified when a refresh is triggered via the swipe
//     * gesture.
//     */
//    public void setOnRefreshListener(OnRefreshListener listener) {
//        mListener = listener;
//    }
//
//    /**
//     * Pre API 11, alpha is used to make the progress circle appear instead of scale.
//     */
//    private boolean isAlphaUsedForScale() {
//        return android.os.Build.VERSION.SDK_INT < 11;
//    }
//
//    /**
//     * Notify the widget that refresh state has changed. Do not call this when
//     * refresh is triggered by a swipe gesture.
//     *
//     * @param refreshing Whether or not the view should show refresh progress.
//     */
//    public void setRefreshing(boolean refreshing) {
//        if (refreshing && mRefreshing != refreshing) {
//            // scale and show
//            mRefreshing = refreshing;
//            int endTarget = 0;
//            if (!mUsingCustomStart) {
//                endTarget = mSpinnerOffsetEnd + mOriginalOffsetTop;
//            } else {
//                endTarget = mSpinnerOffsetEnd;
//            }
//            setTargetOffsetTopAndBottom(endTarget - mCurrentTargetOffsetTop,
//                    true /* requires update */);
//            mNotify = false;
//            startScaleUpAnimation(mRefreshListener);
//        } else {
//            setRefreshing(refreshing, false /* notify */);
//        }
//    }
//
//    @SuppressLint("NewApi")
//    private void startScaleUpAnimation(AnimationListener listener) {
//        mCircleView.setVisibility(View.VISIBLE);
//        if (android.os.Build.VERSION.SDK_INT >= 11) {
//            // Pre API 11, alpha is used in place of scale up to show the
//            // progress circle appearing.
//            // Don't adjust the alpha during appearance otherwise.
//            mProgress.setAlpha(MAX_ALPHA);
//        }
//        mScaleAnimation = new Animation() {
//            @Override
//            public void applyTransformation(float interpolatedTime, Transformation t) {
//                setAnimationProgress(interpolatedTime);
//            }
//        };
//        mScaleAnimation.setDuration(mMediumAnimationDuration);
//        if (listener != null) {
//            mCircleView.setAnimationListener(listener);
//        }
//        mCircleView.clearAnimation();
//        mCircleView.startAnimation(mScaleAnimation);
//    }
//
//    /**
//     * Pre API 11, this does an alpha animation.
//     *
//     * @param progress
//     */
//    void setAnimationProgress(float progress) {
//        if (isAlphaUsedForScale()) {
//            setColorViewAlpha((int) (progress * MAX_ALPHA));
//        } else {
//            ViewCompat.setScaleX(mCircleView, progress);
//            ViewCompat.setScaleY(mCircleView, progress);
//        }
//    }
//
//    private void setRefreshing(boolean refreshing, final boolean notify) {
//        if (mRefreshing != refreshing) {
//            mNotify = notify;
//            ensureTarget();
//            mRefreshing = refreshing;
//            if (mRefreshing) {
//                animateOffsetToCorrectPosition(mCurrentTargetOffsetTop, mRefreshListener);
//            } else {
//                startScaleDownAnimation(mRefreshListener);
//            }
//        }
//    }
//
//    void startScaleDownAnimation(Animation.AnimationListener listener) {
//        mScaleDownAnimation = new Animation() {
//            @Override
//            public void applyTransformation(float interpolatedTime, Transformation t) {
//                setAnimationProgress(1 - interpolatedTime);
//            }
//        };
//        mScaleDownAnimation.setDuration(SCALE_DOWN_DURATION);
//        mCircleView.setAnimationListener(listener);
//        mCircleView.clearAnimation();
//        mCircleView.startAnimation(mScaleDownAnimation);
//    }
//
//    @SuppressLint("NewApi")
//    private void startProgressAlphaStartAnimation() {
//        mAlphaStartAnimation = startAlphaAnimation(mProgress.getAlpha(), STARTING_PROGRESS_ALPHA);
//    }
//
//    @SuppressLint("NewApi")
//    private void startProgressAlphaMaxAnimation() {
//        mAlphaMaxAnimation = startAlphaAnimation(mProgress.getAlpha(), MAX_ALPHA);
//    }
//
//    @SuppressLint("NewApi")
//    private Animation startAlphaAnimation(final int startingAlpha, final int endingAlpha) {
//        // Pre API 11, alpha is used in place of scale. Don't also use it to
//        // show the trigger point.
//        if (mScale && isAlphaUsedForScale()) {
//            return null;
//        }
//        Animation alpha = new Animation() {
//            @Override
//            public void applyTransformation(float interpolatedTime, Transformation t) {
//                mProgress.setAlpha(
//                        (int) (startingAlpha + ((endingAlpha - startingAlpha) * interpolatedTime)));
//            }
//        };
//        alpha.setDuration(ALPHA_ANIMATION_DURATION);
//        // Clear out the previous animation listeners.
//        mCircleView.setAnimationListener(null);
//        mCircleView.clearAnimation();
//        mCircleView.startAnimation(alpha);
//        return alpha;
//    }
//
//    /**
//     * @deprecated Use {@link #setProgressBackgroundColorSchemeResource(int)}
//     */
//    @Deprecated
//    public void setProgressBackgroundColor(int colorRes) {
//        setProgressBackgroundColorSchemeResource(colorRes);
//    }
//
//    /**
//     * Set the background color of the progress spinner disc.
//     *
//     * @param colorRes Resource id of the color.
//     */
//    public void setProgressBackgroundColorSchemeResource(@ColorRes int colorRes) {
//        setProgressBackgroundColorSchemeColor(ContextCompat.getColor(getContext(), colorRes));
//    }
//
//    /**
//     * Set the background color of the progress spinner disc.
//     *
//     * @param color
//     */
//    public void setProgressBackgroundColorSchemeColor(@ColorInt int color) {
//        mCircleView.setBackgroundColor(color);
//        mProgress.setBackgroundColor(color);
//    }
//
//    /**
//     * @deprecated Use {@link #setColorSchemeResources(int...)}
//     */
//    @Deprecated
//    public void setColorScheme(@ColorRes int... colors) {
//        setColorSchemeResources(colors);
//    }
//
//    /**
//     * Set the color resources used in the progress animation from color resources.
//     * The first color will also be the color of the bar that grows in response
//     * to a user swipe gesture.
//     *
//     * @param colorResIds
//     */
//    public void setColorSchemeResources(@ColorRes int... colorResIds) {
//        final Context context = getContext();
//        int[] colorRes = new int[colorResIds.length];
//        for (int i = 0; i < colorResIds.length; i++) {
//            colorRes[i] = ContextCompat.getColor(context, colorResIds[i]);
//        }
//        setColorSchemeColors(colorRes);
//    }
//
//    /**
//     * Set the colors used in the progress animation. The first
//     * color will also be the color of the bar that grows in response to a user
//     * swipe gesture.
//     *
//     * @param colors
//     */
//    public void setColorSchemeColors(@ColorInt int... colors) {
//        ensureTarget();
//        mProgress.setColorSchemeColors(colors);
//    }
//
//    /**
//     * @return Whether the SwipeRefreshWidget is actively showing refresh
//     * progress.
//     */
//    public boolean isRefreshing() {
//        return mRefreshing;
//    }
//
//    private void ensureTarget() {
//        // Don't bother getting the parent height if the parent hasn't been laid
//        // out yet.
//        if (mTarget == null) {
//            for (int i = 0; i < getChildCount(); i++) {
//                View child = getChildAt(i);
//                if (!child.equals(mCircleView)) {
//                    mTarget = child;
//                    break;
//                }
//            }
//        }
//    }
//
//    /**
//     * Set the distance to trigger a sync in dips
//     *
//     * @param distance
//     */
//    public void setDistanceToTriggerSync(int distance) {
//        mTotalDragDistance = distance;
//    }
//
//    //onLayout 主要负责确定各个子视图的位置。
//    @Override
//    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        // 获取 SwipeRefreshLayout 的宽高
//        final int width = getMeasuredWidth();
//        final int height = getMeasuredHeight();
//        if (getChildCount() == 0) {
//            return;
//        }
//        if (mTarget == null) {
//            ensureTarget();
//        }
//        if (mTarget == null) {
//            return;
//        }
//        // 考虑到给控件设置 padding，去除 padding 的距离
//        final View child = mTarget;
//        final int childLeft = getPaddingLeft();
//        final int childTop = getPaddingTop();
//        final int childWidth = width - getPaddingLeft() - getPaddingRight();
//        final int childHeight = height - getPaddingTop() - getPaddingBottom();
//        // 设置 mTarget 的位置
//        child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
//        int circleWidth = mCircleView.getMeasuredWidth();
//        int circleHeight = mCircleView.getMeasuredHeight();
//        // 根据 mCurrentTargetOffsetTop 变量的值来设置 mCircleView 的位置
//        mCircleView.layout((width / 2 - circleWidth / 2), mCurrentTargetOffsetTop,
//                (width / 2 + circleWidth / 2), mCurrentTargetOffsetTop + circleHeight);
//    }
//
//    //确定子视图的大小。
//    @Override
//    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        if (mTarget == null) {
//            // 确定内部要滚动的View，如 RecycleView
//            ensureTarget();
//        }
//        if (mTarget == null) {
//            return;
//        }
//        // 测量子 View （mTarget）
//        mTarget.measure(MeasureSpec.makeMeasureSpec(
//                getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
//                MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(
//                getMeasuredHeight() - getPaddingTop() - getPaddingBottom(), MeasureSpec.EXACTLY));
//        // 测量刷新的圆圈 mCircleView
//        mCircleView.measure(MeasureSpec.makeMeasureSpec(mCircleDiameter, MeasureSpec.EXACTLY),
//                MeasureSpec.makeMeasureSpec(mCircleDiameter, MeasureSpec.EXACTLY));
//
//        // 计算 mCircleView 在 ViewGroup 中的索引
//        mCircleViewIndex = -1;
//        // Get the index of the circleview.
//        for (int index = 0; index < getChildCount(); index++) {
//            if (getChildAt(index) == mCircleView) {
//                mCircleViewIndex = index;
//                break;
//            }
//        }
//    }
//
//    /**
//     * Get the diameter of the progress circle that is displayed as part of the
//     * swipe to refresh layout.
//     *
//     * @return Diameter in pixels of the progress circle view.
//     */
//    public int getProgressCircleDiameter() {
//        return mCircleDiameter;
//    }
//
//    /**
//     * @return Whether it is possible for the child view of this layout to
//     * scroll up. Override this if the child view is a custom view.
//     */
//    public boolean canChildScrollUp() {
//        if (mChildScrollUpCallback != null) {
//            return mChildScrollUpCallback.canChildScrollUp(this, mTarget);
//        }
//        if (android.os.Build.VERSION.SDK_INT < 14) {
//            if (mTarget instanceof AbsListView) {
//                final AbsListView absListView = (AbsListView) mTarget;
//                return absListView.getChildCount() > 0
//                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
//                        .getTop() < absListView.getPaddingTop());
//            } else {
//                return ViewCompat.canScrollVertically(mTarget, -1) || mTarget.getScrollY() > 0;
//            }
//        } else {
//            return ViewCompat.canScrollVertically(mTarget, -1);
//        }
//    }
//
//    /**
//     * Set a callback to override {@link SwipeRefreshLayout#canChildScrollUp()} method. Non-null
//     * callback will return the value provided by the callback and ignore all internal logic.
//     *
//     * @param callback Callback that should be called when canChildScrollUp() is called.
//     */
//    public void setOnChildScrollUpCallback(@Nullable OnChildScrollUpCallback callback) {
//        mChildScrollUpCallback = callback;
//    }
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        ensureTarget();
//
//        final int action = MotionEventCompat.getActionMasked(ev);
//        int pointerIndex;
//
//        if (mReturningToStart && action == MotionEvent.ACTION_DOWN) {
//            mReturningToStart = false;
//        }
//
//        if (!isEnabled() || mReturningToStart || canChildScrollUp()
//                || mRefreshing || mNestedScrollInProgress) {
//            // Fail fast if we're not in a state where a swipe is possible
//            return false;
//        }
//
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                setTargetOffsetTopAndBottom(mOriginalOffsetTop - mCircleView.getTop(), true);
//                mActivePointerId = ev.getPointerId(0);
//                mIsBeingDragged = false;
//
//                pointerIndex = ev.findPointerIndex(mActivePointerId);
//                if (pointerIndex < 0) {
//                    return false;
//                }
//                mInitialDownY = ev.getY(pointerIndex);
//                break;
//
//            case MotionEvent.ACTION_MOVE:
//                if (mActivePointerId == INVALID_POINTER) {
//                    Log.e(LOG_TAG, "Got ACTION_MOVE event but don't have an active pointer id.");
//                    return false;
//                }
//
//                pointerIndex = ev.findPointerIndex(mActivePointerId);
//                if (pointerIndex < 0) {
//                    return false;
//                }
//                final float y = ev.getY(pointerIndex);
//                startDragging(y);
//                break;
//
//            case MotionEventCompat.ACTION_POINTER_UP:
//                onSecondaryPointerUp(ev);
//                break;
//
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//                mIsBeingDragged = false;
//                mActivePointerId = INVALID_POINTER;
//                break;
//        }
//
//        return mIsBeingDragged;
//    }
//
//    @Override
//    public void requestDisallowInterceptTouchEvent(boolean b) {
//        // if this is a List < L or another view that doesn't support nested
//        // scrolling, ignore this request so that the vertical scroll event
//        // isn't stolen
//        if ((android.os.Build.VERSION.SDK_INT < 21 && mTarget instanceof AbsListView)
//                || (mTarget != null && !ViewCompat.isNestedScrollingEnabled(mTarget))) {
//            // Nope.
//        } else {
//            super.requestDisallowInterceptTouchEvent(b);
//        }
//    }
//
//    // NestedScrollingParent
//
//    @Override
//    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
//        return isEnabled() && !mReturningToStart && !mRefreshing
//                && (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
//    }
//
//    @Override
//    public void onNestedScrollAccepted(View child, View target, int axes) {
//        // Reset the counter of how much leftover scroll needs to be consumed.
//        mNestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes);
//        // Dispatch up to the nested parent
//        startNestedScroll(axes & ViewCompat.SCROLL_AXIS_VERTICAL);
//        mTotalUnconsumed = 0;
//        mNestedScrollInProgress = true;
//    }
//
//    @Override
//    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
//        // If we are in the middle of consuming, a scroll, then we want to move the spinner back up
//        // before allowing the list to scroll
//        if (dy > 0 && mTotalUnconsumed > 0) {
//            if (dy > mTotalUnconsumed) {
//                consumed[1] = dy - (int) mTotalUnconsumed;
//                mTotalUnconsumed = 0;
//            } else {
//                mTotalUnconsumed -= dy;
//                consumed[1] = dy;
//            }
//            moveSpinner(mTotalUnconsumed);
//        }
//
//        // If a client layout is using a custom start position for the circle
//        // view, they mean to hide it again before scrolling the child view
//        // If we get back to mTotalUnconsumed == 0 and there is more to go, hide
//        // the circle so it isn't exposed if its blocking content is moved
//        if (mUsingCustomStart && dy > 0 && mTotalUnconsumed == 0
//                && Math.abs(dy - consumed[1]) > 0) {
//            mCircleView.setVisibility(View.GONE);
//        }
//
//        // Now let our nested parent consume the leftovers
//        final int[] parentConsumed = mParentScrollConsumed;
//        if (dispatchNestedPreScroll(dx - consumed[0], dy - consumed[1], parentConsumed, null)) {
//            consumed[0] += parentConsumed[0];
//            consumed[1] += parentConsumed[1];
//        }
//    }
//
//    @Override
//    public int getNestedScrollAxes() {
//        return mNestedScrollingParentHelper.getNestedScrollAxes();
//    }
//
//    @Override
//    public void onStopNestedScroll(View target) {
//        mNestedScrollingParentHelper.onStopNestedScroll(target);
//        mNestedScrollInProgress = false;
//        // Finish the spinner for nested scrolling if we ever consumed any
//        // unconsumed nested scroll
//        if (mTotalUnconsumed > 0) {
//            finishSpinner(mTotalUnconsumed);
//            mTotalUnconsumed = 0;
//        }
//        // Dispatch up our nested parent
//        stopNestedScroll();
//    }
//
//    @Override
//    public void onNestedScroll(final View target, final int dxConsumed, final int dyConsumed,
//                               final int dxUnconsumed, final int dyUnconsumed) {
//        // Dispatch up to the nested parent first
//        dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed,
//                mParentOffsetInWindow);
//
//        // This is a bit of a hack. Nested scrolling works from the bottom up, and as we are
//        // sometimes between two nested scrolling views, we need a way to be able to know when any
//        // nested scrolling parent has stopped handling events. We do that by using the
//        // 'offset in window 'functionality to see if we have been moved from the event.
//        // This is a decent indication of whether we should take over the event stream or not.
//        final int dy = dyUnconsumed + mParentOffsetInWindow[1];
//        if (dy < 0 && !canChildScrollUp()) {
//            mTotalUnconsumed += Math.abs(dy);
//            moveSpinner(mTotalUnconsumed);
//        }
//    }
//
//    // NestedScrollingChild
//
//    @Override
//    public void setNestedScrollingEnabled(boolean enabled) {
//        mNestedScrollingChildHelper.setNestedScrollingEnabled(enabled);
//    }
//
//    @Override
//    public boolean isNestedScrollingEnabled() {
//        return mNestedScrollingChildHelper.isNestedScrollingEnabled();
//    }
//
//    @Override
//    public boolean startNestedScroll(int axes) {
//        return mNestedScrollingChildHelper.startNestedScroll(axes);
//    }
//
//    @Override
//    public void stopNestedScroll() {
//        mNestedScrollingChildHelper.stopNestedScroll();
//    }
//
//    @Override
//    public boolean hasNestedScrollingParent() {
//        return mNestedScrollingChildHelper.hasNestedScrollingParent();
//    }
//
//    @Override
//    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed,
//                                        int dyUnconsumed, int[] offsetInWindow) {
//        return mNestedScrollingChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed,
//                dxUnconsumed, dyUnconsumed, offsetInWindow);
//    }
//
//    @Override
//    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
//        return mNestedScrollingChildHelper.dispatchNestedPreScroll(
//                dx, dy, consumed, offsetInWindow);
//    }
//
//    @Override
//    public boolean onNestedPreFling(View target, float velocityX,
//                                    float velocityY) {
//        return dispatchNestedPreFling(velocityX, velocityY);
//    }
//
//    @Override
//    public boolean onNestedFling(View target, float velocityX, float velocityY,
//                                 boolean consumed) {
//        return dispatchNestedFling(velocityX, velocityY, consumed);
//    }
//
//    @Override
//    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
//        return mNestedScrollingChildHelper.dispatchNestedFling(velocityX, velocityY, consumed);
//    }
//
//    @Override
//    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
//        return mNestedScrollingChildHelper.dispatchNestedPreFling(velocityX, velocityY);
//    }
//
//    private boolean isAnimationRunning(Animation animation) {
//        return animation != null && animation.hasStarted() && !animation.hasEnded();
//    }
//
//    @SuppressLint("NewApi")
//    private void moveSpinner(float overscrollTop) {
//        mProgress.showArrow(true);
//        float originalDragPercent = overscrollTop / mTotalDragDistance;
//
//        float dragPercent = Math.min(1f, Math.abs(originalDragPercent));
//        float adjustedPercent = (float) Math.max(dragPercent - .4, 0) * 5 / 3;
//        float extraOS = Math.abs(overscrollTop) - mTotalDragDistance;
//        float slingshotDist = mUsingCustomStart ? mSpinnerOffsetEnd - mOriginalOffsetTop
//                : mSpinnerOffsetEnd;
//        float tensionSlingshotPercent = Math.max(0, Math.min(extraOS, slingshotDist * 2)
//                / slingshotDist);
//        float tensionPercent = (float) ((tensionSlingshotPercent / 4) - Math.pow(
//                (tensionSlingshotPercent / 4), 2)) * 2f;
//        float extraMove = (slingshotDist) * tensionPercent * 2;
//
//        int targetY = mOriginalOffsetTop + (int) ((slingshotDist * dragPercent) + extraMove);
//        // where 1.0f is a full circle
//        if (mCircleView.getVisibility() != View.VISIBLE) {
//            mCircleView.setVisibility(View.VISIBLE);
//        }
//        if (!mScale) {
//            ViewCompat.setScaleX(mCircleView, 1f);
//            ViewCompat.setScaleY(mCircleView, 1f);
//        }
//
//        if (mScale) {
//            setAnimationProgress(Math.min(1f, overscrollTop / mTotalDragDistance));
//        }
//        if (overscrollTop < mTotalDragDistance) {
//            if (mProgress.getAlpha() > STARTING_PROGRESS_ALPHA
//                    && !isAnimationRunning(mAlphaStartAnimation)) {
//                // Animate the alpha
//                startProgressAlphaStartAnimation();
//            }
//        } else {
//            if (mProgress.getAlpha() < MAX_ALPHA && !isAnimationRunning(mAlphaMaxAnimation)) {
//                // Animate the alpha
//                startProgressAlphaMaxAnimation();
//            }
//        }
//        float strokeStart = adjustedPercent * .8f;
//        mProgress.setStartEndTrim(0f, Math.min(MAX_PROGRESS_ANGLE, strokeStart));
//        mProgress.setArrowScale(Math.min(1f, adjustedPercent));
//
//        float rotation = (-0.25f + .4f * adjustedPercent + tensionPercent * 2) * .5f;
//        mProgress.setProgressRotation(rotation);
//        setTargetOffsetTopAndBottom(targetY - mCurrentTargetOffsetTop, true /* requires update */);
//    }
//
//    private void finishSpinner(float overscrollTop) {
//        if (overscrollTop > mTotalDragDistance) {
//            setRefreshing(true, true /* notify */);
//        } else {
//            // cancel refresh
//            mRefreshing = false;
//            mProgress.setStartEndTrim(0f, 0f);
//            Animation.AnimationListener listener = null;
//            if (!mScale) {
//                listener = new Animation.AnimationListener() {
//
//                    @Override
//                    public void onAnimationStart(Animation animation) {
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animation animation) {
//                        if (!mScale) {
//                            startScaleDownAnimation(null);
//                        }
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animation animation) {
//                    }
//
//                };
//            }
//            animateOffsetToStartPosition(mCurrentTargetOffsetTop, listener);
//            mProgress.showArrow(false);
//        }
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        final int action = MotionEventCompat.getActionMasked(ev);
//        int pointerIndex = -1;
//
//        if (mReturningToStart && action == MotionEvent.ACTION_DOWN) {
//            mReturningToStart = false;
//        }
//
//        if (!isEnabled() || mReturningToStart || canChildScrollUp()
//                || mRefreshing || mNestedScrollInProgress) {
//            // Fail fast if we're not in a state where a swipe is possible
//            return false;
//        }
//
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                mActivePointerId = ev.getPointerId(0);
//                mIsBeingDragged = false;
//                break;
//
//            case MotionEvent.ACTION_MOVE: {
//                pointerIndex = ev.findPointerIndex(mActivePointerId);
//                if (pointerIndex < 0) {
//                    Log.e(LOG_TAG, "Got ACTION_MOVE event but have an invalid active pointer id.");
//                    return false;
//                }
//
//                final float y = ev.getY(pointerIndex);
//                startDragging(y);
//
//                if (mIsBeingDragged) {
//                    final float overscrollTop = (y - mInitialMotionY) * DRAG_RATE;
//                    if (overscrollTop > 0) {
//                        moveSpinner(overscrollTop);
//                    } else {
//                        return false;
//                    }
//                }
//                break;
//            }
//            case MotionEventCompat.ACTION_POINTER_DOWN: {
//                pointerIndex = MotionEventCompat.getActionIndex(ev);
//                if (pointerIndex < 0) {
//                    Log.e(LOG_TAG,
//                            "Got ACTION_POINTER_DOWN event but have an invalid action index.");
//                    return false;
//                }
//                mActivePointerId = ev.getPointerId(pointerIndex);
//                break;
//            }
//
//            case MotionEventCompat.ACTION_POINTER_UP:
//                onSecondaryPointerUp(ev);
//                break;
//
//            case MotionEvent.ACTION_UP: {
//                pointerIndex = ev.findPointerIndex(mActivePointerId);
//                if (pointerIndex < 0) {
//                    Log.e(LOG_TAG, "Got ACTION_UP event but don't have an active pointer id.");
//                    return false;
//                }
//
//                if (mIsBeingDragged) {
//                    final float y = ev.getY(pointerIndex);
//                    final float overscrollTop = (y - mInitialMotionY) * DRAG_RATE;
//                    mIsBeingDragged = false;
//                    finishSpinner(overscrollTop);
//                }
//                mActivePointerId = INVALID_POINTER;
//                return false;
//            }
//            case MotionEvent.ACTION_CANCEL:
//                return false;
//        }
//
//        return true;
//    }
//
//    @SuppressLint("NewApi")
//    private void startDragging(float y) {
//        final float yDiff = y - mInitialDownY;
//        if (yDiff > mTouchSlop && !mIsBeingDragged) {
//            mInitialMotionY = mInitialDownY + mTouchSlop;
//            mIsBeingDragged = true;
//            mProgress.setAlpha(STARTING_PROGRESS_ALPHA);
//        }
//    }
//
//    private void animateOffsetToCorrectPosition(int from, Animation.AnimationListener listener) {
//        mFrom = from;
//        mAnimateToCorrectPosition.reset();
//        mAnimateToCorrectPosition.setDuration(ANIMATE_TO_TRIGGER_DURATION);
//        mAnimateToCorrectPosition.setInterpolator(mDecelerateInterpolator);
//        if (listener != null) {
//            mCircleView.setAnimationListener(listener);
//        }
//        mCircleView.clearAnimation();
//        mCircleView.startAnimation(mAnimateToCorrectPosition);
//    }
//
//    private void animateOffsetToStartPosition(int from, AnimationListener listener) {
//        if (mScale) {
//            // Scale the item back down
//            startScaleDownReturnToStartAnimation(from, listener);
//        } else {
//            mFrom = from;
//            mAnimateToStartPosition.reset();
//            mAnimateToStartPosition.setDuration(ANIMATE_TO_START_DURATION);
//            mAnimateToStartPosition.setInterpolator(mDecelerateInterpolator);
//            if (listener != null) {
//                mCircleView.setAnimationListener(listener);
//            }
//            mCircleView.clearAnimation();
//            mCircleView.startAnimation(mAnimateToStartPosition);
//        }
//    }
//
//    private final Animation mAnimateToCorrectPosition = new Animation() {
//        @Override
//        public void applyTransformation(float interpolatedTime, Transformation t) {
//            int targetTop = 0;
//            int endTarget = 0;
//            if (!mUsingCustomStart) {
//                endTarget = mSpinnerOffsetEnd - Math.abs(mOriginalOffsetTop);
//            } else {
//                endTarget = mSpinnerOffsetEnd;
//            }
//            targetTop = (mFrom + (int) ((endTarget - mFrom) * interpolatedTime));
//            int offset = targetTop - mCircleView.getTop();
//            setTargetOffsetTopAndBottom(offset, false /* requires update */);
//            mProgress.setArrowScale(1 - interpolatedTime);
//        }
//    };
//
//    void moveToStart(float interpolatedTime) {
//        int targetTop = 0;
//        targetTop = (mFrom + (int) ((mOriginalOffsetTop - mFrom) * interpolatedTime));
//        int offset = targetTop - mCircleView.getTop();
//        setTargetOffsetTopAndBottom(offset, false /* requires update */);
//    }
//
//    private final Animation mAnimateToStartPosition = new Animation() {
//        @Override
//        public void applyTransformation(float interpolatedTime, Transformation t) {
//            moveToStart(interpolatedTime);
//        }
//    };
//
//    @SuppressLint("NewApi")
//    private void startScaleDownReturnToStartAnimation(int from, AnimationListener listener) {
//        mFrom = from;
//        if (isAlphaUsedForScale()) {
//            mStartingScale = mProgress.getAlpha();
//        } else {
//            mStartingScale = ViewCompat.getScaleX(mCircleView);
//        }
//        mScaleDownToStartAnimation = new Animation() {
//            @Override
//            public void applyTransformation(float interpolatedTime, Transformation t) {
//                float targetScale = (mStartingScale + (-mStartingScale * interpolatedTime));
//                setAnimationProgress(targetScale);
//                moveToStart(interpolatedTime);
//            }
//        };
//        mScaleDownToStartAnimation.setDuration(SCALE_DOWN_DURATION);
//        if (listener != null) {
//            mCircleView.setAnimationListener(listener);
//        }
//        mCircleView.clearAnimation();
//        mCircleView.startAnimation(mScaleDownToStartAnimation);
//    }
//
//    void setTargetOffsetTopAndBottom(int offset, boolean requiresUpdate) {
//        mCircleView.bringToFront();
//        ViewCompat.offsetTopAndBottom(mCircleView, offset);
//        mCurrentTargetOffsetTop = mCircleView.getTop();
//        if (requiresUpdate && android.os.Build.VERSION.SDK_INT < 11) {
//            invalidate();
//        }
//    }
//
//    private void onSecondaryPointerUp(MotionEvent ev) {
//        final int pointerIndex = MotionEventCompat.getActionIndex(ev);
//        final int pointerId = ev.getPointerId(pointerIndex);
//        if (pointerId == mActivePointerId) {
//            // This was our active pointer going up. Choose a new
//            // active pointer and adjust accordingly.
//            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
//            mActivePointerId = ev.getPointerId(newPointerIndex);
//        }
//    }
//
//    /**
//     * Classes that wish to be notified when the swipe gesture correctly
//     * triggers a refresh should implement this interface.
//     */
//    public interface OnRefreshListener {
//        /**
//         * Called when a swipe gesture triggers a refresh.
//         */
//        void onRefresh();
//    }
//
//    /**
//     * Classes that wish to override {@link SwipeRefreshLayout#canChildScrollUp()} method
//     * behavior should implement this interface.
//     */
//    public interface OnChildScrollUpCallback {
//        /**
//         * Callback that will be called when {@link SwipeRefreshLayout#canChildScrollUp()} method
//         * is called to allow the implementer to override its behavior.
//         *
//         * @param parent SwipeRefreshLayout that this callback is overriding.
//         * @param child  The child view of SwipeRefreshLayout.
//         * @return Whether it is possible for the child view of parent layout to scroll up.
//         */
//        boolean canChildScrollUp(ReBoundSwipeRefreshLayout parent, @Nullable View child);
//    }
//}
