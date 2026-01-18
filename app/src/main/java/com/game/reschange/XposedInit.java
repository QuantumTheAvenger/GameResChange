package com.game.reschange;

import android.util.DisplayMetrics;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Xposedinit implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        
        // SADECE HEDEF OYUN İÇİN ÇALIŞ (Örn: Payback 2 veya Free Fire)
        // Eğer Payback 2 ise paket adı: net.apex_designs.payback2
        if (!lpparam.packageName.equals("net.apex_designs.payback2")) {
            return;
        }

        XposedHelpers.findAndHookMethod("android.view.Display", lpparam.classLoader, "getRealMetrics", DisplayMetrics.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                DisplayMetrics metrics = (DisplayMetrics) param.args[0];
                
                // J6+ (720x1480) için %75 oranında 540p (Hızlı ve akıcı)
                metrics.widthPixels = 540;
                metrics.heightPixels = 1110;
                metrics.densityDpi = 240; // DPI'ı 240 yapmak arayüzü rahatlatır
            }
        });
    }
}
