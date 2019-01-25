package per.goweii.anywindow;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

/**
 * 描述：
 *
 * @author Cuizhen
 * @date 2019/1/23
 */
public class AnyWindow {

    private final Context mContext;
    private final AnyWindowManager mManager;
    private final WindowParams mParams;

    private View mView;

    public static AnyWindow create(Context context) {
        return new AnyWindow(context);
    }

    private AnyWindow(Context context) {
        mContext = context;
        mManager = AnyWindowManager.newAttachToActivity(mContext);
        mParams = WindowParams.createSystemFloatWindow();
    }

    public AnyWindow setView(@NonNull View view) {
        mView = view;
        return this;
    }

    public AnyWindow setView(@LayoutRes int layoutRes) {
        mView = LayoutInflater.from(mContext).inflate(layoutRes, null);
        return this;
    }

    public AnyWindow setInsetScreen(){
        mParams.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR);
        return this;
    }

    public AnyWindow setViewSize(int width, int height){
        mParams.setSize(width, height);
        return this;
    }

    public AnyWindow setViewLocation(int x, int y){
        mParams.setLocation(x, y);
        return this;
    }

    public void show() {
        onAttach();
    }

    public void dismiss() {
        onRemove();
    }

    public void update() {
        mManager.updateView(mView, mParams.get());
    }

    protected void onAttach() {
        mView.post(new Runnable() {
            @Override
            public void run() {
                mView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onShow();
                    }
                }, onAnimIn());
            }
        });
        mManager.addView(mView, mParams.get());
    }

    protected long onAnimIn() {
        return 0;
    }

    protected void onShow() {
    }

    protected void onRemove() {
        mView.postDelayed(new Runnable() {
            @Override
            public void run() {
                onDetach();
            }
        }, onAnimOut());
    }

    protected long onAnimOut() {
        return 0;
    }

    protected void onDetach() {
        mManager.removeView(mView);
    }
}
