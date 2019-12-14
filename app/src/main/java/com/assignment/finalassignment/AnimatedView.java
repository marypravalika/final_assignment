package com.assignment.finalassignment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.SensorEvent;
import android.view.View;

public class AnimatedView extends View {
    private static final int CIRCLE_RADIUS = 50; //pixels

    private Paint mPaint;
    public int x;
    public int y;
    private int viewWidth;
    private int viewHeight;

    public AnimatedView(Context context) {
        super(context);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;
    }

    public void onSensorEvent (SensorEvent event) {
        x = x - (int) event.values[0];
        y = y + (int) event.values[1];
        //Make sure we do not draw outside the bounds of the view.
        //So the max values we can draw to are the bounds + the size of the circle
        if (x <= 0 + CIRCLE_RADIUS) {
            x = 0 + CIRCLE_RADIUS;
        }
        if (x >= viewWidth - CIRCLE_RADIUS) {
            x = viewWidth - CIRCLE_RADIUS;
        }
        if (y <= 0 + CIRCLE_RADIUS) {
            y = 0 + CIRCLE_RADIUS;
        }
        if (y >= viewHeight - CIRCLE_RADIUS) {
            y = viewHeight - CIRCLE_RADIUS;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(x, y, CIRCLE_RADIUS, mPaint);
        //We need to call invalidate each time, so that the view continuously draws
        invalidate();
    }
}

