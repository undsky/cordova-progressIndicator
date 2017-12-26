package org.apache.cordova.progressindicator;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import android.os.Handler;

import com.kaopiz.kprogresshud.KProgressHUD;

public class ProgressIndicator extends CordovaPlugin {

    private KProgressHUD hud;
    final Handler handler = new Handler();

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("show")) {
            boolean dim = args.getBoolean(0);
            initHud(dim, KProgressHUD.Style.SPIN_INDETERMINATE);
            hud.show();
            callbackContext.success();
            return true;
        } else if (action.equals("showSimple")) {
            boolean dim = args.getBoolean(0);
            initHud(dim, KProgressHUD.Style.SPIN_INDETERMINATE);
            hud.show();
            callbackContext.success();
            return true;
        } else if (action.equals("showSimpleWithLabel")) {
            boolean dim = args.getBoolean(0);
            String title = args.getString(1);
            initHud(dim, KProgressHUD.Style.SPIN_INDETERMINATE);
            hud.setLabel(title);
            hud.show();
            callbackContext.success();
            return true;
        } else if (action.equals("showSimpleWithLabelDetail")) {
            boolean dim = args.getBoolean(0);
            String title = args.getString(1);
            String detail = args.getString(2);
            initHud(dim, KProgressHUD.Style.SPIN_INDETERMINATE);
            hud.setLabel(title).setDetailsLabel(detail);
            hud.show();
            return true;
        } else if (action.equals("showDeterminate")) {
            boolean dim = args.getBoolean(0);
            int timeout = args.getInt(1);
            initHud(dim, KProgressHUD.Style.PIE_DETERMINATE);
            hud.setMaxProgress(timeout);
            hud.show();
            simulateProgressUpdate(timeout);
            callbackContext.success();
            return true;
        } else if (action.equals("showDeterminateWithLabel")) {
            boolean dim = args.getBoolean(0);
            int timeout = args.getInt(1);
            String title = args.getString(2);
            initHud(dim, KProgressHUD.Style.PIE_DETERMINATE);
            hud.setMaxProgress(timeout).setLabel(title);
            hud.show();
            simulateProgressUpdate(timeout);
            callbackContext.success();
            return true;
        } else if (action.equals("showDeterminateAnnular")) {
            boolean dim = args.getBoolean(0);
            int timeout = args.getInt(1);
            initHud(dim, KProgressHUD.Style.ANNULAR_DETERMINATE);
            hud.setMaxProgress(timeout);
            hud.show();
            simulateProgressUpdate(timeout);
            callbackContext.success();
            return true;
        } else if (action.equals("showDeterminateAnnularWithLabel")) {
            boolean dim = args.getBoolean(0);
            int timeout = args.getInt(1);
            String title = args.getString(2);
            initHud(dim, KProgressHUD.Style.ANNULAR_DETERMINATE);
            hud.setMaxProgress(timeout).setLabel(title);
            hud.show();
            simulateProgressUpdate(timeout);
            callbackContext.success();
            return true;
        } else if (action.equals("showDeterminateBar")) {
            boolean dim = args.getBoolean(0);
            int timeout = args.getInt(1);
            initHud(dim, KProgressHUD.Style.BAR_DETERMINATE);
            hud.setMaxProgress(timeout);
            hud.show();
            simulateProgressUpdate(timeout);
            callbackContext.success();
            return true;
        } else if (action.equals("showDeterminateBarWithLabel")) {
            boolean dim = args.getBoolean(0);
            int timeout = args.getInt(1);
            String title = args.getString(2);
            initHud(dim, KProgressHUD.Style.BAR_DETERMINATE);
            hud.setMaxProgress(timeout).setLabel(title);
            hud.show();
            simulateProgressUpdate(timeout);
            callbackContext.success();
            return true;
        } else if (action.equals("hide")) {
            hide();
            callbackContext.success();
            return true;
            // } else if (action.equals("showText")) {
            //     boolean dim = args.getBoolean(0);
            //     String title = args.getString(1);
            //     // setCustomView
            //     callbackContext.success();
            //     return true;
            // }else if (action.equals("showSuccess")) {
            //     boolean dim = args.getBoolean(0);
            //     String title = args.getString(1);
            //     // setCustomView
            //     callbackContext.success();
            //     return true;
        } else {
            callbackContext.error(
                    "Not supported call. On Android we only support show, showSimple, showSimpleWithLabel and showSimpleWithLabelDetail");
        }

        return false;
    }

    public void hide() {
        if (hud != null) {
            handler.removeCallbacksAndMessages(null);
            hud.dismiss();
            hud = null;
        }
    }

    private void initHud(boolean dim, KProgressHUD.Style style) {
        hide();
        hud = KProgressHUD.create(cordova.getActivity()).setStyle(style).setCancellable(false);
        if (dim)
            hud.setDimAmount(0.5f);
    }

    private void simulateProgressUpdate(final int timeout) {
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(new Runnable() {
            int currentProgress;

            @Override
            public void run() {
                currentProgress += 1;
                hud.setProgress(currentProgress);
                if (currentProgress < timeout) {
                    handler.postDelayed(this, 50);
                }
            }
        }, 100);
    }
}
