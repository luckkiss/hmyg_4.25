//package com.hldj.hmyg.widget;
//
//import android.content.Context;
//import android.content.res.TypedArray;
//import android.graphics.Canvas;
//import android.graphics.Paint;
//import android.graphics.Path;
//import android.graphics.RectF;
//import android.graphics.drawable.Drawable;
//import android.support.annotation.RequiresApi;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.MotionEvent;
//import android.widget.TextView;
//
///**
// * Created by Administrator on 2017/4/21.
// */
//
//public class SuperTextView extends TextView {
//
//    private static final float DEFAULT_CORNER = 0.0F;
//    private static final int DEFAULT_SOLID = -1;
//    private static final float DEFAULT_STROKE_WIDTH = 0.0F;
//    private static final int DEFAULT_STROKE_COLOR = -16777216;
//    private static final int DEFAULT_STATE_DRAWABLE_MODE = 4;
//    private static final int DEFAULT_TEXT_STROKE_COLOR = -16777216;
//    private static final int DEFAULT_TEXT_FILL_COLOR = -16777216;
//    private static final float DEFAULT_TEXT_STROKE_WIDTH = 0.0F;
//    private float corner;
//    private boolean leftTopCornerEnable;
//    private boolean rightTopCornerEnable;
//    private boolean leftBottomCornerEnable;
//    private boolean rightBottomCornerEnable;
//    private int solid;
//    private float strokeWidth;
//    private int strokeColor;
//    private int stateDrawableMode;
//    private boolean isShowState;
//    private Paint paint;
//    private int width;
//    private int height;
//    private Drawable drawable;
//    private float density;
//    private boolean autoAdjust;
//    private SuperTextView.Adjuster adjuster;
//    private boolean textStroke;
//    private int textStrokeColor;
//    private int textFillColor;
//    private float textStrokeWidth;
//    private boolean runnable = false;
//    private boolean needRun = false;
//    private Thread animThread;
//    private Path strokeWidthPath;
//    private Path solidPath;
//    private RectF strokeLineRectF;
//    private RectF solidRectF;
//    private float[] leftTopCorner = new float[2];
//    private float[] rightTopCorner = new float[2];
//    private float[] leftBottomCorner = new float[2];
//    private float[] rightBottomCorner = new float[2];
//    private float[] corners = new float[8];
//    private float[] drawableBounds = new float[4];
//    private float drawableWidth;
//    private float drawableHeight;
//    private float drawablePaddingLeft;
//    private float drawablePaddingTop;
//    private boolean cacheRunnableState;
//    private boolean cacheNeedRunState;
//
//    public SuperTextView(Context context) {
//        super(context);
//        this.init((AttributeSet) null);
//    }
//
//    public SuperTextView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        this.init(attrs);
//    }
//
//    public SuperTextView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        this.init(attrs);
//    }
//
//    @RequiresApi(
//            api = 21
//    )
//    public SuperTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        this.init(attrs);
//    }
//
//    private void init(AttributeSet attrs) {
//        this.density = this.getContext().getResources().getDisplayMetrics().density;
//        this.initAttrs(attrs);
//        this.paint = new Paint();
//        this.initPaint();
//    }
//
//    private void initAttrs(AttributeSet attrs) {
//        if (attrs != null) {
//            TypedArray typedArray = this.getContext().obtainStyledAttributes(attrs, styleable.SuperTextView);
//            this.corner = typedArray.getDimension(styleable.SuperTextView_corner, 0.0F);
//            this.leftTopCornerEnable = typedArray.getBoolean(styleable.SuperTextView_left_top_corner, false);
//            this.rightTopCornerEnable = typedArray.getBoolean(styleable.SuperTextView_right_top_corner, false);
//            this.leftBottomCornerEnable = typedArray.getBoolean(styleable.SuperTextView_left_bottom_corner, false);
//            this.rightBottomCornerEnable = typedArray.getBoolean(styleable.SuperTextView_right_bottom_corner, false);
//            this.solid = typedArray.getColor(styleable.SuperTextView_solid, -1);
//            this.strokeWidth = typedArray.getDimension(styleable.SuperTextView_stroke_width, 0.0F);
//            this.strokeColor = typedArray.getColor(styleable.SuperTextView_stroke_color, -16777216);
//            this.drawable = typedArray.getDrawable(styleable.SuperTextView_state_drawable);
//            this.drawableWidth = typedArray.getDimension(styleable.SuperTextView_state_drawable_width, 0.0F);
//            this.drawableHeight = typedArray.getDimension(styleable.SuperTextView_state_drawable_height, 0.0F);
//            this.drawablePaddingLeft = typedArray.getDimension(styleable.SuperTextView_state_drawable_padding_left, 0.0F);
//            this.drawablePaddingTop = typedArray.getDimension(styleable.SuperTextView_state_drawable_padding_top, 0.0F);
//            this.isShowState = typedArray.getBoolean(styleable.SuperTextView_isShowState, false);
//            this.stateDrawableMode = typedArray.getInteger(styleable.SuperTextView_state_drawable_mode, 4);
//            this.textStroke = typedArray.getBoolean(styleable.SuperTextView_text_stroke, false);
//            this.textStrokeColor = typedArray.getColor(styleable.SuperTextView_text_stroke_color, -16777216);
//            this.textFillColor = typedArray.getColor(styleable.SuperTextView_text_fill_color, -16777216);
//            this.textStrokeWidth = typedArray.getDimension(styleable.SuperTextView_text_stroke_width, 0.0F);
//            this.autoAdjust = typedArray.getBoolean(styleable.SuperTextView_autoAdjust, false);
//            typedArray.recycle();
//        }
//
//    }
//
//    private void initPaint() {
//        this.paint.reset();
//        this.paint.setAntiAlias(true);
//        this.paint.setDither(true);
//    }
//
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//    }
//
//    protected void onDraw(Canvas canvas) {
//        this.width = this.getWidth();
//        this.height = this.getHeight();
//        this.drawStrokeLine(canvas);
//        this.drawSolid(canvas);
//        this.isNeedToAdjust(canvas, SuperTextView.Adjuster.Opportunity.BEFORE_DRAWABLE);
//        this.drawStateDrawable(canvas);
//        this.isNeedToAdjust(canvas, SuperTextView.Adjuster.Opportunity.BEFORE_TEXT);
//        if (this.textStroke) {
//            this.getPaint().setStyle(Style.STROKE);
//            this.setTextColor(this.textStrokeColor);
//            this.getPaint().setFakeBoldText(true);
//            this.getPaint().setStrokeWidth(this.textStrokeWidth);
//            super.onDraw(canvas);
//            this.getPaint().setStyle(Style.FILL);
//            this.getPaint().setFakeBoldText(false);
//            this.setTextColor(this.textFillColor);
//        }
//
//        super.onDraw(canvas);
//        this.isNeedToAdjust(canvas, SuperTextView.Adjuster.Opportunity.AT_LAST);
//    }
//
//    private void drawStrokeLine(Canvas canvas) {
//        if (this.strokeWidth > 0.0F) {
//            if (this.strokeWidthPath == null) {
//                this.strokeWidthPath = new Path();
//            } else {
//                this.strokeWidthPath.reset();
//            }
//
//            if (this.strokeLineRectF == null) {
//                this.strokeLineRectF = new RectF();
//            } else {
//                this.strokeLineRectF.setEmpty();
//            }
//
//            this.strokeLineRectF.set(this.strokeWidth / 2.0F, this.strokeWidth / 2.0F, (float) this.width - this.strokeWidth / 2.0F, (float) this.height - this.strokeWidth / 2.0F);
//            this.getCorners(this.corner);
//            this.strokeWidthPath.addRoundRect(this.strokeLineRectF, this.corners, Path.Direction.CW);
//            this.initPaint();
//            this.paint.setStyle(Paint.Style.STROKE);
//            this.paint.setColor(this.strokeColor);
//            this.paint.setStrokeWidth(this.strokeWidth);
//            canvas.drawPath(this.strokeWidthPath, this.paint);
//        }
//
//    }
//
//    private void drawSolid(Canvas canvas) {
//        if (this.solidPath == null) {
//            this.solidPath = new Path();
//        } else {
//            this.solidPath.reset();
//        }
//
//        if (this.solidRectF == null) {
//            this.solidRectF = new RectF();
//        } else {
//            this.solidRectF.setEmpty();
//        }
//
//        this.solidRectF.set(this.strokeWidth, this.strokeWidth, (float) this.width - this.strokeWidth, (float) this.height - this.strokeWidth);
//        this.getCorners(this.corner - this.strokeWidth / 2.0F);
//        this.solidPath.addRoundRect(this.solidRectF, this.corners, Path.Direction.CW);
//        this.initPaint();
//        this.paint.setStyle(Paint.Style.FILL);
//        this.paint.setColor(this.solid);
//        canvas.drawPath(this.solidPath, this.paint);
//    }
//
//    private float[] getCorners(float corner) {
//        this.leftTopCorner[0] = 0.0F;
//        this.leftTopCorner[1] = 0.0F;
//        this.rightTopCorner[0] = 0.0F;
//        this.rightTopCorner[1] = 0.0F;
//        this.leftBottomCorner[0] = 0.0F;
//        this.leftBottomCorner[1] = 0.0F;
//        this.rightBottomCorner[0] = 0.0F;
//        this.rightBottomCorner[1] = 0.0F;
//        if (!this.leftTopCornerEnable && !this.rightTopCornerEnable && !this.leftBottomCornerEnable && !this.rightBottomCornerEnable) {
//            this.leftTopCorner[0] = corner;
//            this.leftTopCorner[1] = corner;
//            this.rightTopCorner[0] = corner;
//            this.rightTopCorner[1] = corner;
//            this.leftBottomCorner[0] = corner;
//            this.leftBottomCorner[1] = corner;
//            this.rightBottomCorner[0] = corner;
//            this.rightBottomCorner[1] = corner;
//        } else {
//            if (this.leftTopCornerEnable) {
//                this.leftTopCorner[0] = corner;
//                this.leftTopCorner[1] = corner;
//            }
//
//            if (this.rightTopCornerEnable) {
//                this.rightTopCorner[0] = corner;
//                this.rightTopCorner[1] = corner;
//            }
//
//            if (this.leftBottomCornerEnable) {
//                this.leftBottomCorner[0] = corner;
//                this.leftBottomCorner[1] = corner;
//            }
//
//            if (this.rightBottomCornerEnable) {
//                this.rightBottomCorner[0] = corner;
//                this.rightBottomCorner[1] = corner;
//            }
//        }
//
//        this.corners[0] = this.leftTopCorner[0];
//        this.corners[1] = this.leftTopCorner[1];
//        this.corners[2] = this.rightTopCorner[0];
//        this.corners[3] = this.rightTopCorner[1];
//        this.corners[4] = this.rightBottomCorner[0];
//        this.corners[5] = this.rightBottomCorner[1];
//        this.corners[6] = this.leftBottomCorner[0];
//        this.corners[7] = this.leftBottomCorner[1];
//        return this.corners;
//    }
//
//    private void drawStateDrawable(Canvas canvas) {
//        if (this.drawable != null && this.isShowState) {
//            this.getDrawableBounds();
//            this.drawable.setBounds((int) this.drawableBounds[0], (int) this.drawableBounds[1], (int) this.drawableBounds[2], (int) this.drawableBounds[3]);
//            this.drawable.draw(canvas);
//        }
//
//    }
//
//    private float[] getDrawableBounds() {
//        for (int i = 0; i < this.drawableBounds.length; ++i) {
//            this.drawableBounds[i] = 0.0F;
//        }
//
//        this.drawableWidth = this.drawableWidth == 0.0F ? (float) this.width / 2.0F : this.drawableWidth;
//        this.drawableHeight = this.drawableHeight == 0.0F ? (float) this.height / 2.0F : this.drawableHeight;
//        switch (null.$SwitchMap$com$coorchice$library$SuperTextView$DrawableMode[SuperTextView.DrawableMode.valueOf(this.stateDrawableMode).ordinal()]) {
//            case 1:
//                this.drawableBounds[0] = 0.0F + this.drawablePaddingLeft;
//                this.drawableBounds[1] = (float) this.height / 2.0F - this.drawableHeight / 2.0F + this.drawablePaddingTop;
//                this.drawableBounds[2] = this.drawableBounds[0] + this.drawableWidth;
//                this.drawableBounds[3] = this.drawableBounds[1] + this.drawableHeight;
//                break;
//            case 2:
//                this.drawableBounds[0] = (float) this.width / 2.0F - this.drawableWidth / 2.0F + this.drawablePaddingLeft;
//                this.drawableBounds[1] = 0.0F + this.drawablePaddingTop;
//                this.drawableBounds[2] = this.drawableBounds[0] + this.drawableWidth;
//                this.drawableBounds[3] = this.drawableBounds[1] + this.drawableHeight;
//                break;
//            case 3:
//                this.drawableBounds[0] = (float) this.width - this.drawableWidth + this.drawablePaddingLeft;
//                this.drawableBounds[1] = (float) (this.height / 2) - this.drawableHeight / 2.0F + this.drawablePaddingTop;
//                this.drawableBounds[2] = this.drawableBounds[0] + this.drawableWidth;
//                this.drawableBounds[3] = this.drawableBounds[1] + this.drawableHeight;
//                break;
//            case 4:
//                this.drawableBounds[0] = (float) this.width / 2.0F - this.drawableWidth / 2.0F + this.drawablePaddingLeft;
//                this.drawableBounds[1] = (float) this.height - this.drawableHeight + this.drawablePaddingTop;
//                this.drawableBounds[2] = this.drawableBounds[0] + this.drawableWidth;
//                this.drawableBounds[3] = this.drawableBounds[1] + this.drawableHeight;
//                break;
//            case 5:
//                this.drawableBounds[0] = (float) this.width / 2.0F - this.drawableWidth / 2.0F + this.drawablePaddingLeft;
//                this.drawableBounds[1] = (float) (this.height / 2) - this.drawableHeight / 2.0F + this.drawablePaddingTop;
//                this.drawableBounds[2] = this.drawableBounds[0] + this.drawableWidth;
//                this.drawableBounds[3] = this.drawableBounds[1] + this.drawableHeight;
//                break;
//            case 6:
//                this.drawableBounds[0] = 0.0F;
//                this.drawableBounds[1] = 0.0F;
//                this.drawableBounds[2] = (float) this.width;
//                this.drawableBounds[3] = (float) this.height;
//                break;
//            case 7:
//                this.drawableBounds[0] = 0.0F + this.drawablePaddingLeft;
//                this.drawableBounds[1] = 0.0F + this.drawablePaddingTop;
//                this.drawableBounds[2] = this.drawableBounds[0] + this.drawableWidth;
//                this.drawableBounds[3] = this.drawableBounds[1] + this.drawableHeight;
//                break;
//            case 8:
//                this.drawableBounds[0] = (float) this.width - this.drawableWidth + this.drawablePaddingLeft;
//                this.drawableBounds[1] = 0.0F + this.drawablePaddingTop;
//                this.drawableBounds[2] = this.drawableBounds[0] + this.drawableWidth;
//                this.drawableBounds[3] = this.drawableBounds[1] + this.drawableHeight;
//                break;
//            case 9:
//                this.drawableBounds[0] = 0.0F + this.drawablePaddingLeft;
//                this.drawableBounds[1] = (float) this.height - this.drawableHeight + this.drawablePaddingTop;
//                this.drawableBounds[2] = this.drawableBounds[0] + this.drawableWidth;
//                this.drawableBounds[3] = this.drawableBounds[1] + this.drawableHeight;
//                break;
//            case 10:
//                this.drawableBounds[0] = (float) this.width - this.drawableWidth + this.drawablePaddingLeft;
//                this.drawableBounds[1] = (float) this.height - this.drawableHeight + this.drawablePaddingTop;
//                this.drawableBounds[2] = this.drawableBounds[0] + this.drawableWidth;
//                this.drawableBounds[3] = this.drawableBounds[1] + this.drawableHeight;
//        }
//
//        return this.drawableBounds;
//    }
//
//    private void isNeedToAdjust(Canvas canvas, SuperTextView.Adjuster.Opportunity currentOpportunity) {
//        if (this.autoAdjust) {
//            if (this.adjuster == null) {
//                this.setAdjuster(new SuperTextView.DefaultAdjuster());
//            } else if (currentOpportunity == this.adjuster.getOpportunity()) {
//                this.adjuster.adjust(this, canvas);
//            }
//        }
//
//    }
//
//    public float getCorner() {
//        return this.corner;
//    }
//
//    public void setCorner(float corner) {
//        this.corner = corner;
//        this.postInvalidate();
//    }
//
//    public int getSolid() {
//        return this.solid;
//    }
//
//    public void setSolid(int solid) {
//        this.solid = solid;
//        this.postInvalidate();
//    }
//
//    public float getStrokeWidth() {
//        return this.strokeWidth;
//    }
//
//    public void setStrokeWidth(float strokeWidth) {
//        this.strokeWidth = strokeWidth;
//        this.postInvalidate();
//    }
//
//    public int getStrokeColor() {
//        return this.strokeColor;
//    }
//
//    public void setStrokeColor(int strokeColor) {
//        this.strokeColor = strokeColor;
//        this.postInvalidate();
//    }
//
//    public Drawable getDrawable() {
//        return this.drawable;
//    }
//
//    public void setDrawable(Drawable drawable) {
//        this.drawable = drawable;
//        this.postInvalidate();
//    }
//
//    public boolean isShowState() {
//        return this.isShowState;
//    }
//
//    public void setShowState(boolean showState) {
//        this.isShowState = showState;
//        this.postInvalidate();
//    }
//
//    public int getStateDrawableMode() {
//        return this.stateDrawableMode;
//    }
//
//    public void setStateDrawableMode(int stateDrawableMode) {
//        this.stateDrawableMode = stateDrawableMode;
//        this.postInvalidate();
//    }
//
//    public void setAdjuster(SuperTextView.Adjuster adjuster) {
//        this.adjuster = adjuster;
//        this.postInvalidate();
//    }
//
//    public SuperTextView.Adjuster getAdjuster() {
//        return this.adjuster;
//    }
//
//    public boolean isTextStroke() {
//        return this.textStroke;
//    }
//
//    public void setTextStroke(boolean textStroke) {
//        this.textStroke = textStroke;
//        this.postInvalidate();
//    }
//
//    public int getTextStrokeColor() {
//        return this.textStrokeColor;
//    }
//
//    public void setTextStrokeColor(int textStrokeColor) {
//        this.textStrokeColor = textStrokeColor;
//        this.postInvalidate();
//    }
//
//    public int getTextFillColor() {
//        return this.textFillColor;
//    }
//
//    public void setTextFillColor(int textFillColor) {
//        this.textFillColor = textFillColor;
//        this.postInvalidate();
//    }
//
//    public float getTextStrokeWidth() {
//        return this.textStrokeWidth;
//    }
//
//    public void setTextStrokeWidth(float textStrokeWidth) {
//        this.textStrokeWidth = textStrokeWidth;
//        this.postInvalidate();
//    }
//
//    public boolean isAutoAdjust() {
//        return this.autoAdjust;
//    }
//
//    public void setAutoAdjust(boolean autoAdjust) {
//        this.autoAdjust = autoAdjust;
//    }
//
//    public boolean isLeftTopCornerEnable() {
//        return this.leftTopCornerEnable;
//    }
//
//    public void setLeftTopCornerEnable(boolean leftTopCornerEnable) {
//        this.leftTopCornerEnable = leftTopCornerEnable;
//        this.postInvalidate();
//    }
//
//    public boolean isRightTopCornerEnable() {
//        return this.rightTopCornerEnable;
//    }
//
//    public void setRightTopCornerEnable(boolean rightTopCornerEnable) {
//        this.rightTopCornerEnable = rightTopCornerEnable;
//        this.postInvalidate();
//    }
//
//    public boolean isLeftBottomCornerEnable() {
//        return this.leftBottomCornerEnable;
//    }
//
//    public void setLeftBottomCornerEnable(boolean leftBottomCornerEnable) {
//        this.leftBottomCornerEnable = leftBottomCornerEnable;
//        this.postInvalidate();
//    }
//
//    public boolean isRightBottomCornerEnable() {
//        return this.rightBottomCornerEnable;
//    }
//
//    public void setRightBottomCornerEnable(boolean rightBottomCornerEnable) {
//        this.rightBottomCornerEnable = rightBottomCornerEnable;
//        this.postInvalidate();
//    }
//
//    public float getDrawableWidth() {
//        return this.drawableWidth;
//    }
//
//    public void setDrawableWidth(float drawableWidth) {
//        this.drawableWidth = drawableWidth;
//        this.postInvalidate();
//    }
//
//    public float getDrawableHeight() {
//        return this.drawableHeight;
//    }
//
//    public void setDrawableHeight(float drawableHeight) {
//        this.drawableHeight = drawableHeight;
//        this.postInvalidate();
//    }
//
//    public float getDrawablePaddingLeft() {
//        return this.drawablePaddingLeft;
//    }
//
//    public void setDrawablePaddingLeft(float drawablePaddingLeft) {
//        this.drawablePaddingLeft = drawablePaddingLeft;
//        this.postInvalidate();
//    }
//
//    public float getDrawablePaddingTop() {
//        return this.drawablePaddingTop;
//    }
//
//    public void setDrawablePaddingTop(float drawablePaddingTop) {
//        this.drawablePaddingTop = drawablePaddingTop;
//        this.postInvalidate();
//    }
//
//    public void startAnim() {
//        this.needRun = true;
//        this.runnable = false;
//        if (this.animThread == null) {
//            this.needRun = true;
//            this.runnable = true;
//            this.animThread = new Thread(new Runnable() {
//                public void run() {
//                    for (; SuperTextView.this.runnable; Log.e("SuperTextView", " -> startAnim: " + Thread.currentThread().getId() + "-> " + this.hashCode() + ": It\'s running!")) {
//                        SuperTextView.this.post(new Runnable() {
//                            public void run() {
//                                SuperTextView.this.postInvalidate();
//                            }
//                        });
//
//                        try {
//                            Thread.sleep(16L);
//                        } catch (InterruptedException var2) {
//                            var2.printStackTrace();
//                            SuperTextView.this.runnable = false;
//                        }
//                    }
//
//                    SuperTextView.this.animThread = null;
//                    if (SuperTextView.this.needRun) {
//                        SuperTextView.this.startAnim();
//                    }
//
//                }
//            });
//            this.animThread.start();
//        }
//
//    }
//
//    public void stopAnim() {
//        this.runnable = false;
//        this.needRun = false;
//    }
//
//    public boolean onTouchEvent(MotionEvent event) {
//        if (this.adjuster != null && this.adjuster.onTouch(this, event) && this.isAutoAdjust()) {
//            super.onTouchEvent(event);
//            return true;
//        } else {
//            return super.onTouchEvent(event);
//        }
//    }
//
//    protected void onWindowVisibilityChanged(int visibility) {
//        super.onWindowVisibilityChanged(visibility);
//        if (visibility != 0) {
//            this.cacheRunnableState = this.runnable;
//            this.cacheNeedRunState = this.needRun;
//            this.stopAnim();
//        } else if (this.cacheRunnableState && this.cacheNeedRunState) {
//            this.startAnim();
//        }
//
//    }
//
//    protected void onDetachedFromWindow() {
//        this.stopAnim();
//        super.onDetachedFromWindow();
//    }
//
//    protected void finalize() throws Throwable {
//        this.stopAnim();
//        super.finalize();
//    }
//
//    public static enum DrawableMode {
//        LEFT(0),
//        TOP(1),
//        RIGHT(2),
//        BOTTOM(3),
//        CENTER(4),
//        FILL(5),
//        LEFT_TOP(6),
//        RIGHT_TOP(7),
//        LEFT_BOTTOM(8),
//        RIGHT_BOTTOM(9);
//
//        public int code;
//
//        private DrawableMode(int code) {
//            this.code = code;
//        }
//
//        public static SuperTextView.DrawableMode valueOf(int code) {
//            SuperTextView.DrawableMode[] var1 = values();
//            int var2 = var1.length;
//
//            for (int var3 = 0; var3 < var2; ++var3) {
//                SuperTextView.DrawableMode mode = var1[var3];
//                if (mode.code == code) {
//                    return mode;
//                }
//            }
//
//            return CENTER;
//        }
//    }
//
//    public static class DefaultAdjuster extends SuperTextView.Adjuster {
//        public DefaultAdjuster() {
//        }
//
//        public void adjust(SuperTextView v, Canvas canvas) {
//            int length = v.length();
//            float scale = (float) v.getWidth() / (116.28F * v.getResources().getDisplayMetrics().density);
//            float[] textSizes = new float[]{37.21F, 37.21F, 24.81F, 27.9F, 24.81F, 22.36F, 18.6F, 18.6F};
//            switch (length) {
//                case 1:
//                    v.setTextSize(textSizes[0] * scale);
//                    break;
//                case 2:
//                    v.setTextSize(textSizes[1] * scale);
//                    break;
//                case 3:
//                    v.setTextSize(textSizes[2] * scale);
//                    break;
//                case 4:
//                    v.setTextSize(textSizes[3] * scale);
//                    break;
//                case 5:
//                case 6:
//                    v.setTextSize(textSizes[4] * scale);
//                    break;
//                case 7:
//                case 8:
//                case 9:
//                    v.setTextSize(textSizes[5] * scale);
//                    break;
//                case 10:
//                case 11:
//                case 12:
//                    v.setTextSize(textSizes[6] * scale);
//                    break;
//                case 13:
//                case 14:
//                case 15:
//                case 16:
//                    v.setTextSize(textSizes[7] * scale);
//            }
//
//        }
//    }
//
//    public abstract static class Adjuster {
//        private SuperTextView.Adjuster.Opportunity opportunity;
//
//        public Adjuster() {
//            this.opportunity = SuperTextView.Adjuster.Opportunity.BEFORE_TEXT;
//        }
//
//        protected abstract void adjust(SuperTextView var1, Canvas var2);
//
//        public boolean onTouch(SuperTextView v, MotionEvent event) {
//            return false;
//        }
//
//        public SuperTextView.Adjuster.Opportunity getOpportunity() {
//            return this.opportunity;
//        }
//
//        public void setOpportunity(SuperTextView.Adjuster.Opportunity opportunity) {
//            this.opportunity = opportunity;
//        }
//
//        public static enum Opportunity {
//            BEFORE_DRAWABLE,
//            BEFORE_TEXT,
//            AT_LAST;
//
//            private Opportunity() {
//            }
//        }
//    }
//}
