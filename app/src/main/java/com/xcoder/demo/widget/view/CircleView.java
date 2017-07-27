package com.xcoder.demo.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.xcoder.demo.R;

/**
 * Created by Administrator on 2017/7/5 0005.
 */

public class CircleView extends View{

    private String mXText;
    private int mXTextColor;
    private int mXTextSize;
    private Paint mPaint;
    private Rect mRect;

    public CircleView(Context context) {
        this(context,null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        TypedArray a=context.getTheme().obtainStyledAttributes(attrs, R.styleable.XView,defStyleAttr,0);
        int n=a.getIndexCount();
        for (int i=0;i<n;i++){
            int attr=a.getIndex(i);
            switch (attr){
                case R.styleable.XView_XText:
                    mXText=a.getString(attr);
                    break;
                case R.styleable.XView_XTextColor:
                    mXTextColor=a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.XView_XTextSize:
                    // 默认设置为16sp，TypeValue也可以把sp转化为px
                    mXTextSize=a.getDimensionPixelSize(attr,(int)
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,16,getResources().getDisplayMetrics()));
                    break;
            }
        }
        a.recycle();

        /**
         * 获得绘制文本的宽和高
         */
        mPaint = new Paint();
        mPaint.setTextSize(mXTextSize);
        mRect = new Rect();
        mPaint.getTextBounds(mXText,0,mXText.length(), mRect);
    }


    //自定义View时View的大小
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        //size  大小
//        //mode  模式
//        int width=MeasureSpec.getMode(widthMeasureSpec);
//        int height=MeasureSpec.getMode(heightMeasureSpec);
//
//        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
//        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
//
////        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
////                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
//
//
//        setMeasuredDimension(50,50);
    }

    //自定义View时View在ViewGroup的哪个位置
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

//        int measuredHeight = getMeasuredHeight();
//
//        int measuredWidth = getMeasuredWidth();
//
//
//        int height = getHeight();
//
//        int width = getWidth();

//        layout(50,50,50,50);
    }

    //自定义View时， 绘制自定义View
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        mPaint.setColor(mXTextColor);
        canvas.drawText(mXText, getWidth() / 2 - mRect.width() / 2, getHeight() / 2 + mRect.height() / 2, mPaint);
    }
}
