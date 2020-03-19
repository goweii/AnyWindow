package per.goweii.anywindow;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

/**
 * 描述：
 *
 * @author Cuizhen
 * @date 2019/1/23
 */
public class AnyWindow implements View.OnTouchListener, GestureDetector.OnGestureListener {

    private final Context mContext;
    private final AnyWindowManager mManager;
    private final WindowParams mParams;
    private final GestureDetector mGestureDetector;
    private final Scroller mScroller;
    private VelocityTracker mVelocityTracker = null;
    private final Rect mFenceRect;
    private final Path mDragPath;

    private View mView;

    private State dragState = State.IDLE;
    private float dragStartX = 0F;
    private float dragStartY = 0F;
    private float dragStartEventX = 0F;
    private float dragStartEventY = 0F;

    private enum State {
        IDLE, DRAGGING, FLING
    }

    public static AnyWindow create(Context context) {
        return new AnyWindow(context);
    }

    private AnyWindow(Context context) {
        mContext = context;
        mManager = AnyWindowManager.newAttachToActivity(mContext);
        mParams = WindowParams.createAppFloatWindow();
        mGestureDetector = new GestureDetector(mContext, this);
        mScroller = new Scroller(mContext, new DecelerateInterpolator());
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        mFenceRect = new Rect(0, 0, dm.widthPixels, dm.heightPixels);
        mDragPath = new Path();
    }

    public AnyWindow setView(@LayoutRes int layoutRes) {
        View view = LayoutInflater.from(mContext).inflate(layoutRes, null);
        return setView(view);
    }

    public AnyWindow setView(@NonNull View view) {
        mView = view;
        mView.setOnTouchListener(this);
        mView.getViewTreeObserver().addOnDrawListener(new ViewTreeObserver.OnDrawListener() {
            @Override
            public void onDraw() {
                computeScroll();
            }
        });
        return this;
    }

    public AnyWindow setInsetScreen() {
        mParams.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR);
        return this;
    }

    public AnyWindow setViewSize(int width, int height) {
        mParams.setSize(width, height);
        return this;
    }

    public AnyWindow setViewLocation(int x, int y) {
        boolean inx = x >= mFenceRect.left && x <= mFenceRect.right;
        boolean iny = y >= mFenceRect.top && x <= mFenceRect.bottom;
        if (inx || iny) {
            int realx;
            if (x < mFenceRect.left) {
                realx = mFenceRect.left;
            } else if (x > mFenceRect.right) {
                realx = mFenceRect.right;
            } else {
                realx = x;
            }
            int realy;
            if (y < mFenceRect.top) {
                realy = mFenceRect.top;
            } else if (y > mFenceRect.bottom) {
                realy = mFenceRect.bottom;
            } else {
                realy = y;
            }
            mParams.setLocation(realx, realy);
        } else {
            mScroller.abortAnimation();
        }
        return this;
    }

    public void show() {
        setViewLocation(mFenceRect.right, (int) (mFenceRect.height() * 0.6F));
        mManager.addView(mView, mParams.get());
    }

    public void dismiss() {
        mManager.removeView(mView);
    }

    public void update() {
        mManager.updateView(mView, mParams.get());
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        boolean consumed = mGestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            default:
                break;
            case MotionEvent.ACTION_UP:
                onUp(event);
                break;
        }
        return consumed;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Log.d("AnyWindow", "onDown");
        dragState = State.IDLE;
        return true;
    }

    public void onUp(MotionEvent e) {
        Log.d("AnyWindow", "onUp");
        if (dragState != State.DRAGGING) {
            return;
        }
        dragState = State.FLING;
        float vx = 0F;
        float vy = 0F;
        if (mVelocityTracker != null) {
            mVelocityTracker.computeCurrentVelocity(1000, Math.max(mFenceRect.width(), mFenceRect.height()));
            vx = mVelocityTracker.getXVelocity();
            vy = mVelocityTracker.getYVelocity();
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
        onDragEnd(vx, vy);
        return;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.d("AnyWindow", "onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d("AnyWindow", "onSingleTapUp");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.d("AnyWindow", "onScroll[" + distanceX + "," + distanceY + "]");
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        e2.setLocation(e2.getRawX(), e2.getRawY());
        mVelocityTracker.addMovement(e2);
        float touchX = e2.getRawX();
        float touchY = e2.getRawY();
        if (dragState == State.IDLE) {
            dragState = State.DRAGGING;
            dragStartEventX = touchX;
            dragStartEventY = touchY;
            onDragStart();
        } else {
            float dargX = touchX - dragStartEventX;
            float dargY = touchY - dragStartEventY;
            onDragging(dargX, dargY);
        }
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.d("AnyWindow", "onLongPress");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d("AnyWindow", "onFling[" + velocityX + "," + velocityY + "]");
        return false;
    }

    private void onDragStart() {
        Log.d("AnyWindow", "onDragStart");
        dragStartX = mParams.get().x;
        dragStartY = mParams.get().y;
        mDragPath.reset();
        mDragPath.rewind();
        mDragPath.moveTo(mParams.get().x, mParams.get().y);
    }

    private void onDragging(float moveX, float moveY) {
        Log.d("AnyWindow", "onDragging[" + moveX + "," + moveY + "]");
        float x = dragStartX + moveX;
        float y = dragStartY + moveY;
        float cX = (x + mParams.get().x) / 2F;
        float cY = (y + mParams.get().y) / 2F;
        mDragPath.quadTo(dragStartX, dragStartY, cX, cY);
        setViewLocation((int) x, (int) y);
        update();
    }

    private void onDragEnd(float velocityX, float velocityY) {
        Log.d("AnyWindow", "onDragEnd[" + velocityX + "," + velocityY + "]");
        float startX = mParams.get().x;
        float startY = mParams.get().y;
        float startCenterX = startX + mView.getWidth() / 2F;
        float startCenterY = startY + mView.getHeight() / 2F;
        float endX;
        if (startCenterX < mFenceRect.width() / 2F) {
            endX = 0;
        } else {
            endX = mFenceRect.width() - mView.getWidth();
        }
        float endY;
        if (velocityX == 0F && velocityY == 8F) {
            endY = startY;
        } else {
            float dx = endX - startX;
            float dy = Math.abs(dx) * (velocityY / Math.abs(velocityX));
            endY = startY + dy;
        }
        PathMeasure pm = new PathMeasure(mDragPath, false);
        float[] pos = new float[2];
        float[] tan = new float[2];
        pm.getPosTan(pm.getLength(), pos, tan);
        float degrees = (float) (Math.atan2(tan[1], tan[0]) * 180F / Math.PI);
        Log.d("AnyWindow", "onDragEnd degrees=" + degrees);
        Log.d("AnyWindow", "onDragEnd end[" + endX + "," + endY + "]");
        mScroller.startScroll((int) startX, (int) startY, (int) (endX - startX), (int) (endY - startY));
        computeScroll();
    }

    private void computeScroll() {
        if (dragState != State.FLING) {
            return;
        }
        if (mScroller.computeScrollOffset()) {
            float x = mScroller.getCurrX();
            float y = mScroller.getCurrY();
            setViewLocation((int) x, (int) y);
            update();
        } else {
            dragState = State.IDLE;
        }
    }

}
