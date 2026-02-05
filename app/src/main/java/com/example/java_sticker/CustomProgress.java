package com.example.java_sticker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import java.util.logging.LogRecord;

public class CustomProgress extends View {
    private int curValue;//현재값
    private int maxValue; //최대값
    private int lineColor; //진행바 색 저장
    private int backgroundColor; //진행바 뒷배경 색


    public CustomProgress(Context context) {
        super(context);
    }

    public CustomProgress(Context context, AttributeSet attrs){
        super(context,attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,R.styleable.MyProgressBar,0,0);

        try{
            this.curValue = a.getInteger(R.styleable.MyProgressBar_curValue,0);
            this.maxValue = a.getInteger(R.styleable.MyProgressBar_maxValue,5);
            this.lineColor = a.getColor(R.styleable.MyProgressBar_lineColor,0xff000000);
            this.backgroundColor = a.getColor(R.styleable.MyProgressBar_backgroundColor,0xff000000);
        }finally {

        }
    }

    public int getLineColor(){
        return lineColor;
    }

    public void setLineColor(int color) {

        this.lineColor = color;

        invalidate();

        requestLayout();

    }


    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        invalidate();
        requestLayout();
    }

    public int getCurValue() {

        return curValue;

    }



    public void setCurValue(int curValue) {

        this.curValue = curValue;

        invalidate();

        requestLayout();

    }



    public int getMaxValue() {

        return maxValue;

    }



    public void setMaxValue(int maxValue) {

        this.maxValue = maxValue;

        invalidate();

        requestLayout();

    }


    Handler mHandler = new Handler();

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        int width = this.getMeasuredWidth();
        int height = this.getMeasuredHeight();


        //진행바 그리기
        Paint circle = new Paint();
        circle.setColor(this.lineColor);
        circle.setStrokeWidth(10);
        circle.setAntiAlias(true);
        circle.setStyle(Paint.Style.STROKE);

        //백그라운드 진행바
        Paint backgroundCircle = new Paint();
        backgroundCircle.setColor(this.backgroundColor);
        backgroundCircle.setStrokeWidth(10);
        backgroundCircle.setAntiAlias(true);
        backgroundCircle.setStyle(Paint.Style.STROKE);


        canvas.drawArc(new RectF(10,10,width-10,height-10),0,360,false,backgroundCircle);

        canvas.drawArc(new RectF(10,10,width-10,height-10),-90,((float)this.curValue/(float) this.maxValue*360),false,circle);


        //텍스트 그리기
        Paint textp = new Paint();
        textp.setColor(Color.BLACK);
        textp.setTextSize(50);
        textp.setTextAlign(Paint.Align.CENTER);

        canvas.drawText(this.curValue+"/"+this.maxValue,width / 2, (float) (height/1.7),textp);
    }
}
