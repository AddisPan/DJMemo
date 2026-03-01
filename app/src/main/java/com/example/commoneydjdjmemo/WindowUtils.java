package com.example.commoneydjdjmemo;

import android.app.Activity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

/**
 * 角色: 視窗 UI 工具類 (全螢幕/沉浸式模式控制)
 * 
 * 責任:
 * - 隱藏/顯示系統導覽列與狀態列，提供更廣闊的螢幕空間。
 * - 處理全螢幕 UI 的手勢行為，讓導覽列在滑動時暫時出現。
 * 
 * 需求對應:
 * - 提升應用程式的視覺專業感與沉浸式體驗。
 */
public class WindowUtils {

    /**
     * 開啟沉浸式模式：隱藏導覽列 (使用者滑動時暫時出現)
     */
    public static void enableImmersiveMode(Activity activity) {
        if (activity == null) return;

        WindowInsetsControllerCompat windowInsetsController =
                WindowCompat.getInsetsController(activity.getWindow(), activity.getWindow().getDecorView());

        if (windowInsetsController != null) {
            windowInsetsController.setSystemBarsBehavior(
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            );
            windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars());
        }
    }

    /**
     * 開啟全沉浸模式：隱藏導覽列與狀態列
     */
    public static void enableFullImmersiveMode(Activity activity) {
        if (activity == null) return;

        WindowInsetsControllerCompat windowInsetsController =
                WindowCompat.getInsetsController(activity.getWindow(), activity.getWindow().getDecorView());

        if (windowInsetsController != null) {
            windowInsetsController.setSystemBarsBehavior(
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            );
            windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());
        }
    }

    /**
     * 關閉沉浸式模式：顯示導覽列
     */
    public static void disableImmersiveMode(Activity activity) {
        if (activity == null) return;

        WindowInsetsControllerCompat windowInsetsController =
                WindowCompat.getInsetsController(activity.getWindow(), activity.getWindow().getDecorView());

        if (windowInsetsController != null) {
            windowInsetsController.show(WindowInsetsCompat.Type.navigationBars());
        }
    }
}
