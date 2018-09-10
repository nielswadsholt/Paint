package io.brothersinarms.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CanvasView extends View {

    private Path path;
    private Paint paint;
    private float X, Y;
    private static final float TOLERANCE = 5;
    Context context;

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        path = new Path();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(12f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(path, paint);
    }

    private  void startTouch(float x, float y) {
        path.moveTo(x, y);
        X = x;
        Y = y;
    }

    private void moveTouch(float x, float y) {
        float dx = Math.abs(x - X);
        float dy = Math.abs(y - Y);

        if (dx >= TOLERANCE || dy >= TOLERANCE) {
            path.quadTo(X, Y, (x + X) / 2, (y + Y) / 2);
            X = x;
            Y = y;
        }
    }

    private void upTouch() {
        path.lineTo(X, Y);
    }

    public void clearCanvas(){
        path.reset();
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                moveTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                upTouch();
                performClick();
                invalidate();
                break;
        }

        return  true;
    }

    @Override
    public boolean performClick() {
        super.performClick();

        return true;
    }
}
