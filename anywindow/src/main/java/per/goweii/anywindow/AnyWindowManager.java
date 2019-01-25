package per.goweii.anywindow;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;

/**
 * 描述：
 *
 * @author Cuizhen
 * @date 2019/1/2
 */
public class AnyWindowManager {

    private static AnyWindowManager mInstance;

    private WindowManager mWindowManager;

    public static AnyWindowManager getAttachToSystem(@NonNull Context context) {
        if (mInstance == null) {
            mInstance = new AnyWindowManager(getSystemWindowManager(context));
        }
        return mInstance;
    }

    public static AnyWindowManager newAttachToActivity(@NonNull Context context) {
        return new AnyWindowManager(getActivityWindowManager(context));
    }

    private static WindowManager getSystemWindowManager(@NonNull Context context) {
        return (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    private static WindowManager getActivityWindowManager(@NonNull Context context) {
        Activity activity = null;
        if (context instanceof Activity) {
            activity = (Activity) context;
        } else if (context instanceof ContextWrapper) {
            Context baseContext = ((ContextWrapper) context).getBaseContext();
            if (baseContext instanceof Activity) {
                activity = (Activity) baseContext;
            }
        }
        if (activity == null) {
            return null;
        }
        return activity.getWindowManager();
    }

    public AnyWindowManager(WindowManager windowManager) {
        if (windowManager == null) {
            throw new NullPointerException("WindowManager mast not be null");
        }
        mWindowManager = windowManager;
    }

    /**
     * 添加悬浮窗
     */
    public void addView(View view, WindowManager.LayoutParams params) {
        if (view.isAttachedToWindow()) {
            return;
        }
        try {
            mWindowManager.addView(view, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 移除悬浮窗
     */
    public void removeView(View view) {
        if (!view.isAttachedToWindow()) {
            return;
        }
        try {
            mWindowManager.removeView(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新悬浮窗
     */
    public void updateView(View view, WindowManager.LayoutParams params) {
        if (!view.isAttachedToWindow()) {
            return;
        }
        try {
            mWindowManager.updateViewLayout(view, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从当前上下文获取Activity
     */
    @Nullable
    private static Activity getActivity(Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        }
        if (context instanceof ContextWrapper) {
            Context baseContext = ((ContextWrapper) context).getBaseContext();
            if (baseContext instanceof Activity) {
                return (Activity) baseContext;
            }
        }
        return null;
    }

}
