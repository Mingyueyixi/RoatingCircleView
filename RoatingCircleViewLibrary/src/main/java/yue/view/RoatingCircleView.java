package yue.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class RoatingCircleView extends View{

    private Paint mPaint;
    private int off;
    private Paint rPaint;
    private Paint bPaint;
    private Paint gPaint;
    /**默认值：红色*/
    private int lColor = 0xffff2222;
    /**默认值：绿色*/
    private int cColor = 0xff22ff22;
    /**默认值：蓝色*/
    private int rColor = 0xff2222ff;

    public RoatingCircleView(Context context) {
        super(context);
        init();
    }

    public RoatingCircleView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RoatingCircleView(Context context, AttributeSet attrs,
                             int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        rPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        gPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        initDot();
    }
    public void setDotColor(int lColor,int cColor,int rColor){
        this.lColor = lColor;
        this.cColor = cColor;
        this.rColor = rColor;
        updateDot();
    }
    private void initDot() {
        //		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        rPaint.setColor(lColor);
        gPaint.setColor(cColor);
        bPaint.setColor(rColor);
    }
    public void updateDot(){
        initDot();
        invalidate();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int width = getWidth();

        int w = (int) (width/3.0);
        int h = (int) (height/2.0);

        int l = w>h?h:w;

        int r = (int) (l/2.0);
        int cy = (int) (height/2.0);


        canvas.drawCircle(r, cy, (int)(radd*r), rPaint);

        canvas.drawCircle(width/2, cy,(int) (rmin*r), gPaint);

        canvas.drawCircle(width-r, cy, (int)(radd*r), bPaint);

        flush();
    }
    int count;
    private boolean flag = true;

    private double radd = 0.8;
    private double rmin = 0.8;

    /**
     * 刷新。当count==10时，表示刷新了一遍。flag = false,count执行自减，两边小圆的绘制转入缩小。
     * 当count==0时，count执行自增，两边小圆的绘制转入放大。
     */
    private void flush(){
        postInvalidateDelayed(30);
        if (count==10) {
            flag = false;
        }
        if(count == 0){
            flag = true;
        }

        rmin = 1.0 - 0.02*count;
        radd = 0.8+0.02*count;

        if (flag) {
            count++;
            return;
        }
        count--;
    }

}


