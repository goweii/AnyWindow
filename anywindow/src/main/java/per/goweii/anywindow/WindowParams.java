package per.goweii.anywindow;

import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.FloatRange;
import android.view.Gravity;
import android.view.WindowManager;

/**
 * 描述：
 *
 * @author Cuizhen
 * @date 2019/1/23
 */
final class WindowParams {

    private final WindowManager.LayoutParams mLayoutParams;

    public static WindowParams with(WindowManager.LayoutParams params) {
        return new WindowParams(params);
    }

    public static WindowParams create() {
        return new WindowParams(null);
    }

    public static WindowParams createSystemFloatWindow() {
        int type;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
        return create().setFormat(PixelFormat.RGBA_8888)
                .setGravity(Gravity.TOP | Gravity.LEFT)
                .setType(type)
                .setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
                .addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL)
                .addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
                .addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR)
                .addFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
    }

    public static WindowParams createAppFloatWindow() {
        int type;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
        return create().setFormat(PixelFormat.RGBA_8888)
                .setGravity(Gravity.TOP | Gravity.LEFT)
                .setType(type)
                .setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
                .addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL)
                .addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
                .addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR)
                .addFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
    }

    private WindowParams(WindowManager.LayoutParams params) {
        if (params != null) {
            mLayoutParams = params;
        } else {
            mLayoutParams = new WindowManager.LayoutParams();
        }
    }

    public WindowParams setSize(int width, int height) {
        mLayoutParams.width = width;
        mLayoutParams.height = height;
        return this;
    }

    public WindowParams setFormat(int format) {
        mLayoutParams.format = format;
        return this;
    }

    /**
     * 如果忽略gravity属性，那么它表示窗口的绝对x/y位置。
     * 当设置了 Gravity.LEFT 或 Gravity.RIGHT 之后，就表示到特定边的距离。
     *
     * @param x x
     * @param y y
     * @return WindowParams
     */
    public WindowParams setLocation(int x, int y) {
        mLayoutParams.x = x;
        mLayoutParams.y = y;
        return this;
    }

    /**
     * 窗口x/y位置的参考位置
     *
     * @param gravity 参考位置
     * @return WindowParams
     */
    public WindowParams setGravity(int gravity) {
        mLayoutParams.gravity = gravity;
        return this;
    }

    /**
     * 在纵/横向上，为关联的view预留了多少扩展空间（像素）。如果是0，那么此view不能被拉伸。
     * 其他情况下，扩展空间（像素）将被widget所均分。
     *
     * @param horizontalWeight horizontalWeight
     * @return WindowParams
     */
    public WindowParams setHorizontalWeight(int horizontalWeight) {
        mLayoutParams.horizontalWeight = horizontalWeight;
        return this;
    }

    /**
     * 在纵/横向上，为关联的view预留了多少扩展空间（像素）。如果是0，那么此view不能被拉伸。
     * 其他情况下，扩展空间（像素）将被widget所均分。
     *
     * @param verticalWeight verticalWeight
     * @return WindowParams
     */
    public WindowParams setVerticalWeight(int verticalWeight) {
        mLayoutParams.verticalWeight = verticalWeight;
        return this;
    }

    /**
     * 水平边距，容器与widget之间的距离，占容器宽度的百分率。
     *
     * @param horizontalMargin 水平边距
     * @return WindowParams
     */
    public WindowParams setHorizontalMargin(int horizontalMargin) {
        mLayoutParams.horizontalMargin = horizontalMargin;
        return this;
    }

    /**
     * 纵向边距，容器与widget之间的距离，占容器高度的百分率。
     *
     * @param verticalMargin 纵向边距
     * @return WindowParams
     */
    public WindowParams setVerticalMargin(int verticalMargin) {
        mLayoutParams.verticalMargin = verticalMargin;
        return this;
    }

    /**
     * 窗口所使用的动画设置
     * 它必须是一个系统资源而不是应用程序资源，因为窗口管理器不能访问应用程序。
     *
     * @param windowAnimations 窗口所使用的动画设置
     * @return WindowParams
     */
    public WindowParams setWindowAnimations(int windowAnimations) {
        mLayoutParams.windowAnimations = windowAnimations;
        return this;
    }

    /**
     * 整个窗口的半透明值
     * 1.0表示不透明，0.0表示全透明
     *
     * @param alpha 半透明值
     * @return WindowParams
     */
    public WindowParams setAlpha(@FloatRange(from = 0, to = 1) float alpha) {
        mLayoutParams.alpha = alpha;
        return this;
    }

    /**
     * 后面的窗口变暗的程度
     * 1.0表示完全不透明，0.0表示没有变暗
     *
     * @param dimAmount 变暗的程度
     * @return WindowParams
     */
    public WindowParams setDimAmount(@FloatRange(from = 0, to = 1) float dimAmount) {
        mLayoutParams.dimAmount = dimAmount;
        return this;
    }

    /**
     * 用来覆盖用户设置的屏幕亮度。表示应用用户设置的屏幕亮度。
     * 从0到1调整亮度从暗到最亮发生变化
     *
     * @param screenBrightness 屏幕亮度
     * @return WindowParams
     */
    public WindowParams setScreenBrightness(@FloatRange(from = 0, to = 1) float screenBrightness) {
        mLayoutParams.screenBrightness = screenBrightness;
        return this;
    }

    /**
     * 窗口的标示符
     *
     * @param token 窗口的标示符
     * @return WindowParams
     */
    public WindowParams setToken(IBinder token) {
        mLayoutParams.token = token;
        return this;
    }

    /**
     * 此窗口所在的包名
     *
     * @param packageName 此窗口所在的包名
     * @return WindowParams
     */
    public WindowParams setPackageName(String packageName) {
        mLayoutParams.packageName = packageName;
        return this;
    }

    /**
     * 屏幕方向
     * {@link android.content.pm.ActivityInfo#screenOrientation}
     *
     * @param screenOrientation 屏幕方向
     * @return WindowParams
     */
    public WindowParams setScreenOrientation(int screenOrientation) {
        mLayoutParams.screenOrientation = screenOrientation;
        return this;
    }

    /**
     * 窗口类型。
     * 有3种主要类型：
     * <p>
     * Applicationwindows：
     * 取值在 FIRST_APPLICATION_WINDOW 和 LAST_APPLICATION_WINDOW 之间。
     * 是通常的、顶层的应用程序窗口。必须将 token 设置成 activity 的 token 。
     * Sub_windows：
     * 取值在 FIRST_SUB_WINDOW 和 LAST_SUB_WINDOW 之间。
     * 与顶层窗口相关联，token 必须设置为它所附着的宿主窗口的 token。
     * Systemwindows：
     * 取值在 FIRST_SYSTEM_WINDOW 和 LAST_SYSTEM_WINDOW 之间。
     * 用于特定的系统功能。它不能用于应用程序，使用时需要特殊权限。
     * <p>
     * 应用程序窗口。
     * {@link WindowManager.LayoutParams#FIRST_APPLICATION_WINDOW} = 1;
     * <p>
     * 所有程序窗口的“基地”窗口，其他应用程序窗口都显示在它上面。
     * TYPE_BASE_APPLICATION =1;
     * <p>
     * 普通应用功能程序窗口。token必须设置为Activity的token，以指出该窗口属谁。
     * TYPE_APPLICATION = 2;
     * <p>
     * 用于应用程序启动时所显示的窗口。应用本身不要使用这种类型。
     * 它用于让系统显示些信息，直到应用程序可以开启自己的窗口。
     * TYPE_APPLICATION_STARTING = 3;
     * <p>
     * 应用程序窗口结束。
     * LAST_APPLICATION_WINDOW = 99;
     * <p>
     * 子窗口。子窗口的Z序和坐标空间都依赖于他们的宿主窗口。
     * FIRST_SUB_WINDOW = 1000;
     * <p>
     * 面板窗口，显示于宿主窗口上层。
     * TYPE_APPLICATION_PANEL = FIRST_SUB_WINDOW;
     * <p>
     * 媒体窗口，例如视频。显示于宿主窗口下层。
     * TYPE_APPLICATION_MEDIA = FIRST_SUB_WINDOW+1;
     * <p>
     * 应用程序窗口的子面板。显示于所有面板窗口的上层。（GUI的一般规律，越“子”越靠上）
     * TYPE_APPLICATION_SUB_PANEL = FIRST_SUB_WINDOW +2;
     * <p>
     * 对话框。类似于面板窗口，绘制类似于顶层窗口，而不是宿主的子窗口。
     * TYPE_APPLICATION_ATTACHED_DIALOG = FIRST_SUB_WINDOW +3;
     * <p>
     * 媒体信息。显示在媒体层和程序窗口之间，需要实现透明（半透明）效果。（例如显示字幕）
     * TYPE_APPLICATION_MEDIA_OVERLAY = FIRST_SUB_WINDOW +4;
     * <p>
     * 子窗口结束。（ End of types of sub-windows ）
     * LAST_SUB_WINDOW = 1999;
     * <p>
     * 系统窗口。非应用程序创建。
     * FIRST_SYSTEM_WINDOW = 2000;
     * <p>
     * 状态栏。只能有一个状态栏；它位于屏幕顶端，其他窗口都位于它下方。
     * TYPE_STATUS_BAR = FIRST_SYSTEM_WINDOW;
     * <p>
     * 搜索栏。只能有一个搜索栏；它位于屏幕上方。
     * TYPE_SEARCH_BAR = FIRST_SYSTEM_WINDOW+1;
     * <p>
     * 电话窗口。它用于电话交互（特别是呼入）。它置于所有应用程序之上，状态栏之下。
     * TYPE_PHONE = FIRST_SYSTEM_WINDOW+2;
     * <p>
     * 系统提示。它总是出现在应用程序窗口之上。
     * TYPE_SYSTEM_ALERT = FIRST_SYSTEM_WINDOW +3;
     * <p>
     * 锁屏窗口。
     * TYPE_KEYGUARD = FIRST_SYSTEM_WINDOW +4;
     * <p>
     * 信息窗口。用于显示toast。
     * TYPE_TOAST = FIRST_SYSTEM_WINDOW +5;
     * <p>
     * 系统顶层窗口。显示在其他一切内容之上。此窗口不能获得输入焦点，否则影响锁屏。
     * TYPE_SYSTEM_OVERLAY = FIRST_SYSTEM_WINDOW +6;
     * <p>
     * 电话优先，当锁屏时显示。此窗口不能获得输入焦点，否则影响锁屏。
     * TYPE_PRIORITY_PHONE = FIRST_SYSTEM_WINDOW +7;
     * <p>
     * 系统对话框。（例如音量调节框）。
     * TYPE_SYSTEM_DIALOG = FIRST_SYSTEM_WINDOW +8;
     * <p>
     * 锁屏时显示的对话框。
     * TYPE_KEYGUARD_DIALOG = FIRST_SYSTEM_WINDOW +9;
     * <p>
     * 系统内部错误提示，显示于所有内容之上。
     * TYPE_SYSTEM_ERROR = FIRST_SYSTEM_WINDOW +10;
     * <p>
     * 内部输入法窗口，显示于普通UI之上。应用程序可重新布局以免被此窗口覆盖。
     * TYPE_INPUT_METHOD = FIRST_SYSTEM_WINDOW +11;
     * <p>
     * 内部输入法对话框，显示于当前输入法窗口之上。
     * TYPE_INPUT_METHOD_DIALOG= FIRST_SYSTEM_WINDOW +12;
     * <p>
     * 墙纸窗口。
     * TYPE_WALLPAPER = FIRST_SYSTEM_WINDOW +13;
     * <p>
     * 状态栏的滑动面板。
     * TYPE_STATUS_BAR_PANEL = FIRST_SYSTEM_WINDOW +14;
     * <p>
     * 系统窗口结束。
     * LAST_SYSTEM_WINDOW = 2999;
     *
     * @param type 窗口类型
     * @return WindowParams
     */
    public WindowParams setType(int type) {
        mLayoutParams.type = type;
        return this;
    }

    /**
     * 行为选项/旗标
     * 默认为 none
     * 下面定义了 flags 的取值：
     * <p>
     * 窗口之后的内容变暗。
     * FLAG_DIM_BEHIND = 0x00000002;
     * <p>
     * 窗口之后的内容变模糊。
     * FLAG_BLUR_BEHIND = 0x00000004;
     * <p>
     * 不许获得焦点。
     * 不能获得按键输入焦点，所以不能向它发送按键或按钮事件。那些时间将发送给它后面的可以获得焦点的窗口。此选项还会设置FLAG_NOT_TOUCH_MODAL选项。设置此选项，意味着窗口不能与软输入法进行交互，所以它的Z序独立于任何活动的输入法（换句话说，它可以全屏显示，如果需要的话，可覆盖输入法窗口）。要修改这一行为，可参考FLAG_ALT_FOCUSALBE_IM选项。
     * FLAG_NOT_FOCUSABLE = 0x00000008;
     * <p>
     * 不接受触摸屏事件。
     * FLAG_NOT_TOUCHABLE = 0x00000010;
     * <p>
     * 当窗口可以获得焦点（没有设置 FLAG_NOT_FOCUSALBE 选项）时，仍然将窗口范围之外的点设备事件（鼠标、触摸屏）发送给后面的窗口处理。否则它将独占所有的点设备事件，而不管它们是不是发生在窗口范围内。
     * FLAG_NOT_TOUCH_MODAL = 0x00000020;
     * <p>
     * 如果设置了这个标志，当设备休眠时，点击触摸屏，设备将收到这个第一触摸事件。
     * 通常第一触摸事件被系统所消耗，用户不会看到他们点击屏幕有什么反应。
     * FLAG_TOUCHABLE_WHEN_WAKING = 0x00000040;
     * <p>
     * 当此窗口为用户可见时，保持设备常开，并保持亮度不变。
     * FLAG_KEEP_SCREEN_ON = 0x00000080;
     * <p>
     * 窗口占满整个屏幕，忽略周围的装饰边框（例如状态栏）。此窗口需考虑到装饰边框的内容。
     * FLAG_LAYOUT_IN_SCREEN =0x00000100;
     * <p>
     * 允许窗口扩展到屏幕之外。
     * FLAG_LAYOUT_NO_LIMITS =0x00000200;
     * <p>
     * 窗口显示时，隐藏所有的屏幕装饰（例如状态条）。使窗口占用整个显示区域。
     * FLAG_FULLSCREEN = 0x00000400;
     * <p>
     * 此选项将覆盖FLAG_FULLSCREEN选项，并强制屏幕装饰（如状态条）弹出。
     * FLAG_FORCE_NOT_FULLSCREEN =0x00000800;
     * <p>
     * 抖动。指 对半透明的显示方法。又称“点透”。图形处理较差的设备往往用“点透”替代Alpha混合。
     * FLAG_DITHER = 0x00001000;
     * <p>
     * 不允许屏幕截图。
     * FLAG_SECURE = 0x00002000;
     * <p>
     * 一种特殊模式，布局参数用于指示显示比例。
     * FLAG_SCALED = 0x00004000;
     * <p>
     * 当屏幕有可能贴着脸时，这一选项可防止面颊对屏幕造成误操作。
     * FLAG_IGNORE_CHEEK_PRESSES = 0x00008000;
     * <p>
     * 当请求布局时，你的窗口可能出现在状态栏的上面或下面，从而造成遮挡。当设置这一选项后，窗口管理器将确保窗口内容不会被装饰条（状态栏）盖住。
     * FLAG_LAYOUT_INSET_DECOR = 0x00010000;
     * <p>
     * 反转FLAG_NOT_FOCUSABLE选项。
     * 如果同时设置了FLAG_NOT_FOCUSABLE选项和本选项，窗口将能够与输入法交互，允许输入法窗口覆盖；
     * 如果FLAG_NOT_FOCUSABLE没有设置而设置了本选项，窗口不能与输入法交互，可以覆盖输入法窗口。
     * FLAG_ALT_FOCUSABLE_IM = 0x00020000;
     * <p>
     * 如果你设置了FLAG_NOT_TOUCH_MODAL，那么当触屏事件发生在窗口之外事，可以通过设置此标志接收到一个MotionEvent.ACTION_OUTSIDE事件。注意，你不会收到完整的down/move/up事件，只有第一次down事件时可以收到ACTION_OUTSIDE。
     * FLAG_WATCH_OUTSIDE_TOUCH = 0x00040000;
     * <p>
     * 当屏幕锁定时，窗口可以被看到。这使得应用程序窗口优先于锁屏界面。可配合FLAG_KEEP_SCREEN_ON选项点亮屏幕并直接显示在锁屏界面之前。可使用FLAG_DISMISS_KEYGUARD选项直接解除非加锁的锁屏状态。此选项只用于最顶层的全屏幕窗口。
     * FLAG_SHOW_WHEN_LOCKED = 0x00080000;
     * <p>
     * 请求系统墙纸显示在你的窗口后面。窗口必须是半透明的。
     * FLAG_SHOW_WALLPAPER = 0x00100000;
     * <p>
     * 窗口一旦显示出来，系统将点亮屏幕，正如用户唤醒设备那样。
     * FLAG_TURN_SCREEN_ON = 0x00200000;
     * <p>
     * 解除锁屏。只有锁屏界面不是加密的才能解锁。如果锁屏界面是加密的，那么用户解锁之后才能看到此窗口，除非设置了FLAG_SHOW_WHEN_LOCKED选项。
     * FLAG_DISMISS_KEYGUARD = 0x00400000;
     * <p>
     * 锁屏界面淡出时，继续运行它的动画。
     * FLAG_KEEP_SURFACE_WHILE_ANIMATING =0x10000000;
     * <p>
     * 以原始尺寸显示窗口。用于在兼容模式下运行程序。
     * FLAG_COMPATIBLE_WINDOW = 0x20000000;
     * <p>
     * 用于系统对话框。设置此选项的窗口将无条件获得焦点。
     * FLAG_SYSTEM_ERROR = 0x40000000;
     *
     * @param flags 行为选项/旗标
     * @return WindowParams
     */
    public WindowParams setFlags(int flags) {
        mLayoutParams.flags = flags;
        return this;
    }

    public WindowParams addFlags(int flags) {
        mLayoutParams.flags |= flags;
        return this;
    }

    public WindowParams removeFlags(int flags) {
        mLayoutParams.flags &= ~flags;
        return this;
    }

    /**
     * 软输入法模式选项
     * <p>
     * 软输入区域是否可见。
     * SOFT_INPUT_MASK_STATE = 0x0f;
     * <p>
     * 未指定状态。
     * SOFT_INPUT_STATE_UNSPECIFIED = 0;
     * <p>
     * 不要修改软输入法区域的状态。
     * SOFT_INPUT_STATE_UNCHANGED = 1;
     * <p>
     * 隐藏输入法区域（当用户进入窗口时）。
     * SOFT_INPUT_STATE_HIDDEN = 2;
     * <p>
     * 当窗口获得焦点时，隐藏输入法区域。
     * SOFT_INPUT_STATE_ALWAYS_HIDDEN = 3;
     * <p>
     * 显示输入法区域（当用户进入窗口时）。
     * SOFT_INPUT_STATE_VISIBLE = 4;
     * <p>
     * 当窗口获得焦点时，显示输入法区域。
     * SOFT_INPUT_STATE_ALWAYS_VISIBLE = 5;
     * <p>
     * 窗口应当主动调整，以适应软输入窗口。
     * SOFT_INPUT_MASK_ADJUST = 0xf0;
     * <p>
     * 未指定状态，系统将根据窗口内容尝试选择一个输入法样式。
     * SOFT_INPUT_ADJUST_UNSPECIFIED = 0x00;
     * <p>
     * 当输入法显示时，允许窗口重新计算尺寸，使内容不被输入法所覆盖。
     * 不可与SOFT_INPUT_ADJUSP_PAN混合使用,如果两个都没有设置，系统将根据窗口内容自动设置一个选项。
     * SOFT_INPUT_ADJUST_RESIZE = 0x10;
     * <p>
     * 输入法显示时平移窗口。它不需要处理尺寸变化，框架能够移动窗口以确保输入焦点可见。
     * 不可与SOFT_INPUT_ADJUST_RESIZE混合使用;如果两个都没设置,系统将根据窗口内容自动设置一个选项。
     * SOFT_INPUT_ADJUST_PAN = 0x20;
     * <p>
     * 当用户转至此窗口时，由系统自动设置，所以你不要设置它。
     * 当窗口显示之后该标志自动清除。
     * SOFT_INPUT_IS_FORWARD_NAVIGATION = 0x100;
     *
     * @param softInputMode 软输入法模式选项
     * @return WindowParams
     */
    public WindowParams softInputMode(int softInputMode) {
        mLayoutParams.softInputMode = softInputMode;
        return this;
    }

    public WindowManager.LayoutParams get() {
        return mLayoutParams;
    }

}
