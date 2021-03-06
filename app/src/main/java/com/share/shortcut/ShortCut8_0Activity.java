package com.share.shortcut;


import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;

/**
 * Created by tzw on 2018/7/19.
 * 8.0系统创建Activity快捷方式
 */

public class ShortCut8_0Activity extends BaseActivity {

    static final String TAG = "app";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_8);

        findViewById(R.id.create_8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testShortCut(ShortCut8_0Activity.this);
            }
        });


        findViewById(R.id.goback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackMainActivity(ShortCut8_0Activity.this);
            }
        });

    }
    class CallBackReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "onReceive: 固定快捷方式的回调");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void testShortCut(Context context) {
        ShortcutManager shortcutManager = (ShortcutManager) context.getSystemService(Context.SHORTCUT_SERVICE);
        boolean requestPinShortcutSupported = shortcutManager.isRequestPinShortcutSupported();
        Log.i(TAG, "启动器是否支持固定快捷方式: "+requestPinShortcutSupported);

        if (requestPinShortcutSupported) {

            Intent shortcutInfoIntent = new Intent(context, TargetActivity.class);

            shortcutInfoIntent.setAction(Intent.ACTION_VIEW);

            ShortcutInfo info = new ShortcutInfo.Builder(context, "tzw")
                    .setIcon(Icon.createWithResource(context, R.drawable.yuanbao))
                    .setShortLabel("O系统短")
                    .setLongLabel("O系统长")
                    .setIntent(shortcutInfoIntent)
                    .build();

            //当添加快捷方式的确认弹框弹出来时，将被回调CallBackReceiver里面的onReceive方法
            PendingIntent shortcutCallbackIntent = PendingIntent.getBroadcast(context, 0, new Intent(context, CallBackReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);

            shortcutManager.requestPinShortcut(info, shortcutCallbackIntent.getIntentSender());

        }
    }

}
