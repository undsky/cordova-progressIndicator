package org.apache.cordova.progressindicator;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import android.app.ProgressDialog;

import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;

public class ProgressIndicator extends CordovaPlugin {

    private ProgressDialog progressIndicator = null;
    private static final String LOG_TAG = "ProgressIndicator";
    private KProgressHUD hud;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("show")) {
            show();
            callbackContext.success();
            return true;
        } else if (action.equals("showSimple")) {
            show();
            callbackContext.success();
            return true;
        } else if (action.equals("showSimpleWithLabel")) {
            String title = args.getString(1);
            show(title);
            callbackContext.success();
            return true;
        } else if (action.equals("showSimpleWithLabelDetail")) {
            String title = args.getString(1);
            String text = args.getString(2);
            show(title, text, true);
            callbackContext.success();
            return true;
        } else if (action.equals("showText")) {
            String title = args.getString(1);
            String text = args.getString(2);
            show(title, text, false);
            callbackContext.success();
            return true;
        } else if (action.equals("hide")) {
            hide();
            callbackContext.success();
            return true;
        } else if (action.equals("showDeterminate")) {
            Integer timeout = args.getInt(1);
            String title = args.getString(2);
            cordova.getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    hud = KProgressHUD.create(cordova.getActivity()).setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                            .setLabel(title);
                    hud.show();
                    simulateProgressUpdate(timeout);
                    callbackContext.success();
                }
            });
            // hud = KProgressHUD.create(cordova.getActivity()).setStyle(KProgressHUD.Style.PIE_DETERMINATE)
            //         .setLabel(title);
            // hud.show();
            // simulateProgressUpdate(timeout);
            // callbackContext.success();
            return true;
        } else if (action.equals("showDeterminateWithLabel")) {

        } else if (action.equals("showAnnular")) {

        } else if (action.equals("showAnnularWithLabel")) {

        } else if (action.equals("showBar")) {

        } else if (action.equals("showBarWithLabel")) {

        } else {
            callbackContext.error(
                    "Not supported call. On Android we only support show, showSimple, showSimpleWithLabel and showSimpleWithLabelDetail");
        }

        return false;
    }

    /**
     * This show the ProgressDialog
     *
     * @param text - Message to display in the Progress Dialog
     */
    public void show() {
        progressIndicator = new ProgressDialog(cordova.getActivity());
        progressIndicator.show();
    }

    /**
     * This show the ProgressDialog
     *
     * @param text - Message to display in the Progress Dialog
     */
    public void show(String text) {
        progressIndicator = new ProgressDialog(cordova.getActivity());
        progressIndicator.setTitle(text);
        progressIndicator.show();
    }

    /**
     * This show the ProgressDialog
     *
     * @param text - Message to display in the Progress Dialog
     */
    public void show(String title, String detail, Boolean withTitle) {
        progressIndicator = new ProgressDialog(cordova.getActivity());
        if (withTitle)
            progressIndicator.setTitle(title);
        progressIndicator.setMessage(detail);
        progressIndicator.show();
    }

    /**
     * This hide the ProgressDialog
     */
    public void hide() {
        if (progressIndicator != null) {
            progressIndicator.dismiss();
            progressIndicator = null;
        }
    }

    private void simulateProgressUpdate(Integer timeout) {
        hud.setMaxProgress(timeout);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            int currentProgress;

            @Override
            public void run() {
                currentProgress += 1;
                hud.setProgress(currentProgress);
                // if (currentProgress == 80) {
                //     hud.setLabel("Almost finish...");
                // }
                if (currentProgress < timeout) {
                    handler.postDelayed(this, 50);
                }
            }
        }, 100);
    }

    private void scheduleDismiss() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hud.dismiss();
            }
        }, 2000);
    }
}
