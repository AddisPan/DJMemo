package com.example.commoneydjdjmemo;

import android.app.Activity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

/**
 * 沉浸式全螢幕工具類
 *
 * 用途：隱藏系統導覽列，實現沉浸式 UI 體驗
 *
 * 使用方式：
 * 在任何 Activity 的 onCreate 方法中，setContentView 之後呼叫：
 * WindowUtils.enableImmersiveMode(this);
 */
public class WindowUtils {

    /**
     * 啟用沉浸式模式（隱藏系統導覽列）
     *
     * 效果：
     * - 隱藏底部導覽列，讓內容佔據整個螢幕
     * - 當使用者從螢幕邊緣向上滑動時，導覽列會暫時浮現
     * - 幾秒後自動隱藏，提供更沉浸的視覺體驗
     *
     * @param activity 要設定的 Activity 物件
     */
    public static void enableImmersiveMode(Activity activity) {
        if (activity == null) {
            return; // 防呆：如果 Activity 為 null，直接返回
        }

        // 獲取 WindowInsetsController，用來控制系統視窗邊框（如導覽列、狀態列）
        WindowInsetsControllerCompat windowInsetsController =
                WindowCompat.getInsetsController(activity.getWindow(), activity.getWindow().getDecorView());

        if (windowInsetsController != null) {
            // 設定行為：當使用者從螢幕邊緣滑動時，導覽列會「暫時」浮現
            // 過幾秒自動隱藏（比 BEHAVIOR_SHOW_PERMANENT 更沉浸）
            windowInsetsController.setSystemBarsBehavior(
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            );

            // 隱藏導覽列
            // Type.navigationBars() = 只隱藏底部導覽列
            // Type.systemBars() = 同時隱藏頂部狀態列和底部導覽列
            windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars());
        }
    }

    /**
     * 啟用完全沉浸模式（隱藏導覽列和狀態列）
     *
     * 適用場景：全屏應用、遊戲、視頻播放
     *
     * @param activity 要設定的 Activity 物件
     */
    public static void enableFullImmersiveMode(Activity activity) {
        if (activity == null) {
            return;
        }

        WindowInsetsControllerCompat windowInsetsController =
                WindowCompat.getInsetsController(activity.getWindow(), activity.getWindow().getDecorView());

        if (windowInsetsController != null) {
            windowInsetsController.setSystemBarsBehavior(
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            );
            // 隱藏所有系統欄（包含狀態列和導覽列）
            windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());
        }
    }

    /**
     * 恢復正常模式（顯示導覽列）
     *
     * @param activity 要設定的 Activity 物件
     */
    public static void disableImmersiveMode(Activity activity) {
        if (activity == null) {
            return;
        }

        WindowInsetsControllerCompat windowInsetsController =
                WindowCompat.getInsetsController(activity.getWindow(), activity.getWindow().getDecorView());

        if (windowInsetsController != null) {
            // 顯示導覽列
            windowInsetsController.show(WindowInsetsCompat.Type.navigationBars());
        }
    }
}

