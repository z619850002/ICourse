package com.example.dell.coursetable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 田雍恺 on 2018/2/3.
 */

public class RadarView extends View {
    private int count; //数据个数
    private float angle;
    private float radius; //网格最大半径
    private int centerX;  //中心X
    private int centerY;  //中心Y
    private String[] titles = {"考评", "负担", "实用", "趣味", "教师"};
    private double[] data = {0, 0, 0, 0, 0}; //各个维度分值
    private float maxValue = 5;  //数据最大值
    private Paint mainPaint;    //雷达区画笔
    private Paint valuePaint;   //数据区画笔
    private Paint textPaint;    //文本画笔

    public RadarView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        init();
    }

    public RadarView(Context context, AttributeSet attrs){
        super(context, attrs);
        init();
    }

    public RadarView(Context context){
        super(context);
        init();
    }

    private void init(){
        count = Math.min(titles.length, data.length);
        angle = (float)(Math.PI*2/count);

        mainPaint = new Paint();
        mainPaint.setAntiAlias(true);
        mainPaint.setColor(Color.GRAY);
        mainPaint.setStyle(Paint.Style.STROKE);

        valuePaint = new Paint();
        valuePaint.setAntiAlias(true);
        valuePaint.setColor(Color.BLUE);
        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        textPaint = new Paint();
        textPaint.setTextSize(20);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        radius = Math.min(h,w)/2*0.9f;
        centerX = w/2;
        centerY = h/2;
        postInvalidate();
        super.onSizeChanged(w,h,oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas){
        drawPolygon(canvas);
        drawLines(canvas);
        drawText(canvas);
        drawRegion(canvas);
    }

    /**
     * 绘制五边形
     * @param canvas
     */
    private void drawPolygon(Canvas canvas){
        Path path = new Path();
        float r = radius/(count-1); //r是蛛丝间距
        for(int i = 1; i<count; i++){
            float curR = r*i;  //当前半径
            path.reset();
            for(int j = 0; j<count; j++){
                float angle1 = (float)(Math.PI*0.3);
                float x,y;
                if(j==0){
                    x = (float)(centerX+curR*Math.cos(angle1));
                    y = (float)(centerY+curR*Math.sin(angle1));
                    path.moveTo(x,y);
                }else {
                    //计算出蛛丝上每个点的坐标
                    x = (float)(centerX+curR*Math.cos(angle1+angle*j));
                    y = (float)(centerX+curR*Math.sin(angle1+angle*j));
                    path.lineTo(x,y);
                }
            }
            path.close();
            canvas.drawPath(path, mainPaint);
        }
    }

    /**
     * 绘制直线
     * @param canvas
     */
    private void drawLines(Canvas canvas){
        Path path = new Path();
        float anglle1 = (float) (Math.PI*0.3);
        for(int i = 0; i < count; i++){
            path.reset();
            path.moveTo(centerX, centerY);
            float x = (float) (centerX + radius*Math.cos(anglle1+angle*i));
            float y = (float) (centerY + radius*Math.sin(anglle1+angle*i));
            path.lineTo(x,y);
            canvas.drawPath(path, mainPaint);
        }
    }

    /**
     * 绘制文字
     * @param canvas
     */
    private void drawText(Canvas canvas){
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float fontHeight = fontMetrics.descent - fontMetrics.ascent;
        float angle1 = (float)(Math.PI*0.3);
        for(int i=0; i<count; i++){
            float x = (float) (centerX+(radius+fontHeight/2)*Math.cos(angle1+angle*i));
            float y = (float) (centerY+(radius+fontHeight/2)*Math.sin(angle1+angle*i));
            if((angle1+angle*i)>=0&&(angle1+angle*i)<=Math.PI/2){//第4象限
                canvas.drawText(titles[i], x,y,textPaint);
            }else if((angle1+angle*i)>=3*Math.PI/2&&(angle1+angle*i)<=Math.PI*2){//第3象限
                canvas.drawText(titles[i], x,y,textPaint);
            }else if((angle1+angle*i)>Math.PI/2&&(angle1+angle*i)<=Math.PI){//第2象限
                float dis = textPaint.measureText(titles[i]);//文本长度
                canvas.drawText(titles[i], x-dis,y,textPaint);
            }else if((angle1+angle*i)>=Math.PI&&(angle1+angle*i)<3*Math.PI/2){//第1象限
                float dis = textPaint.measureText(titles[i]);//文本长度
                canvas.drawText(titles[i], x-dis,y,textPaint);
            }
        }
    }

    /**
     * 绘制覆盖区域
     * @param canvas
     */
    private void drawRegion(Canvas canvas){
        Path path = new Path();
        valuePaint.setAlpha(255);
        float angle1 = (float)(Math.PI*0.3);
        for(int i=0;i<count; i++){
            double percent = data[i]/maxValue;
            float x = (float)(centerX + radius*Math.cos(angle1+angle*i)*percent);
            float y = (float)(centerY + radius*Math.sin(angle1+angle*i)*percent);
            if(i==0){
                path.moveTo(x,y);
            }else {
                path.lineTo(x,y);
            }
            //绘制圆点
            canvas.drawCircle(x,y,10,valuePaint);
        }
        valuePaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path,valuePaint);
        valuePaint.setAlpha(127);
        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawPath(path, valuePaint);
    }


    //设置标题
    public void setTitles(String[] titles){
        this.titles = titles;
    }

    //设置数值
    public void setData(double[] data){
        this.data = data;
    }

    public float getMaxValue(){
        return maxValue;
    }
    //设置最大数值
    public void setMaxValue(float maxValue){
        this.maxValue = maxValue;
    }

    //设置蛛网颜色
    public void setMainPaintColor(int color){
        mainPaint.setColor(color);
    }

    //设置标题颜色
    public void setTextPaintColor(int color){
        textPaint.setColor(color);
    }

    //设置覆盖区域颜色
    public void setValuePaintColor(int color){
        valuePaint.setColor(color);
    }
}
