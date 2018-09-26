package com.nihalmistry.circularprogressview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class CircularProgressView extends View {

    public static final int START_ANGLE = -90;

    private int mTrackColor = Color.RED; // TODO: use a default from R.color...
    private int mTrackWidth = 10;

    private int mProgressWidth = 10;
    private int mProgressColor = Color.BLACK;

    private int mThumbWidth = 20;
    private int mThumbHeight = 20;

    int centerX;
    int centerY;

    int thumbCenterX, thumbCenterY;

    private Paint mPaint = new Paint();

    int paddingLeft;
    int paddingRight;
    int paddingTop;
    int paddingBottom;
    int contentWidth;
    int contentHeight;

    RectF mArcRect = new RectF();


    private float mMaxProgress = 100;
    private float mProgress = 50;

    private float sweepAngle;

    private Drawable mThumbDrawable;
    private int mThumbTint = Color.YELLOW;

    public CircularProgressView(Context context) {
        super(context);
        init(null, 0);
    }

    public CircularProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CircularProgressView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.CircularProgressView, defStyle, 0);


        mTrackColor = a.getColor(
                R.styleable.CircularProgressView_trackColor,
                mTrackColor);

        mTrackWidth = (int) a.getDimension(R.styleable.CircularProgressView_trackWidth,mTrackWidth);
        mProgressWidth = (int) a.getDimension(R.styleable.CircularProgressView_progressWidth,mProgressWidth);

        mProgressColor = a.getColor(
                R.styleable.CircularProgressView_progressColor,
                mProgressColor);

        mThumbDrawable = a.getDrawable(R.styleable.CircularProgressView_thumbDrawable);
        mThumbTint = a.getColor(R.styleable.CircularProgressView_thumbTint, mThumbTint);

        mThumbWidth = (int) a.getDimension(R.styleable.CircularProgressView_thumbWidth, mProgressWidth);
        mThumbHeight = (int) a.getDimension(R.styleable.CircularProgressView_thumbHeight, mThumbHeight);
        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        paddingLeft = getPaddingLeft();
        paddingTop = getPaddingTop();
        paddingRight = getPaddingRight();
        paddingBottom = getPaddingBottom();

        contentWidth = getWidth() - paddingLeft - paddingRight;
        contentHeight = getHeight() - paddingTop - paddingBottom;

        centerX = getWidth()/2;
        centerY = getHeight()/2;

        // Draw the track.

        if (getHeight() < getWidth()) {
            // Draw considering the width.
            mArcRect.set(centerX - contentHeight/2 - (mTrackWidth/2),
                    centerY - contentHeight/2 - (mTrackWidth/2),
                     centerX + contentHeight/2 - (mTrackWidth/2),
                    centerY + contentHeight/2 - (mTrackWidth/2));

            drawTrack(canvas);
            drawProgress(canvas);
            drawThumb(canvas);
        } else {
            // Draw considering the height

            mArcRect.set(centerX - contentWidth/2 + (mTrackWidth/2),
                    centerY - contentWidth/2 + (mTrackWidth/2),
                    centerX + contentWidth/2 - (mTrackWidth/2),
                    centerY + contentWidth/2 - (mTrackWidth/2));
            drawTrack(canvas);
            drawProgress(canvas);
            drawThumb(canvas);
        }
    }

    private void drawThumb(Canvas canvas) {
        if (mThumbDrawable != null) {
            mThumbDrawable.mutate().setColorFilter(mThumbTint, PorterDuff.Mode.SRC_IN);
            thumbCenterX = (int) ((contentWidth/2 - (mTrackWidth/2)) * Math.cos(Math.toRadians(sweepAngle+START_ANGLE))) + centerX;
            thumbCenterY = (int) ((contentWidth/2 - (mTrackWidth/2)) * Math.sin(Math.toRadians(sweepAngle+START_ANGLE))) + centerY;

            mThumbDrawable.setBounds(thumbCenterX - (mThumbWidth/2),
                    thumbCenterY - (mThumbWidth/2),
                    thumbCenterX + (mThumbWidth/2),
                    thumbCenterY + (mThumbWidth/2));

            mThumbDrawable.draw(canvas);
        }
    }

    private void drawProgress(Canvas canvas) {
        mPaint.setColor(mProgressColor);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mProgressWidth);
        canvas.drawArc(mArcRect,START_ANGLE, sweepAngle,false,mPaint);
    }


    private void drawTrack(Canvas canvas) {
        mPaint.setColor(mTrackColor);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mTrackWidth);
        canvas.drawCircle(getWidth()/2, getHeight()/2,(contentWidth/2) - mTrackWidth/2, mPaint);
    }


    public float getMaxProgress() {
        return mMaxProgress;
    }

    public void setMaxProgress(float maxProgress) {
        if (maxProgress > 0 && mMaxProgress != maxProgress ) {
            this.mMaxProgress = maxProgress;
            sweepAngle = (mProgress/mMaxProgress) * 360;
            invalidate();
        }
    }

    public float getProgress() {
        return mProgress;
    }

    public void setProgress(float progress) {
        if (progress > 0 && mProgress != progress) {
            this.mProgress = progress;
            sweepAngle = (mProgress/mMaxProgress) * 360;
            invalidate();
        }
    }
}
