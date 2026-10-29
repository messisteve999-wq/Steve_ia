package com.steve.steveia;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.os.IBinder;
import android.provider.Settings;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FloatingChatService extends Service {
    private WindowManager manager;
    private TextView bubble;
    private LinearLayout panel;
    private boolean expanded = false;

    @Override
    public void onCreate() {
        super.onCreate();

        if (!Settings.canDrawOverlays(this)) {
            stopSelf();
            return;
        }

        manager = (WindowManager) getSystemService(WINDOW_SERVICE);

        int overlayType = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        WindowManager.LayoutParams bubbleParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                overlayType,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        bubbleParams.gravity = Gravity.TOP | Gravity.END;
        bubbleParams.x = 20;
        bubbleParams.y = 250;

        bubble = new TextView(this);
        bubble.setText("😈");
        bubble.setTextSize(30);
        bubble.setGravity(Gravity.CENTER);
        bubble.setBackgroundColor(Color.rgb(45, 5, 12));
        bubble.setPadding(14, 10, 14, 10);
        bubble.setOnClickListener(v -> togglePanel());

        manager.addView(bubble, bubbleParams);
    }

    private void togglePanel() {
        if (expanded) {
            if (panel != null) {
                manager.removeView(panel);
                panel = null;
            }
            expanded = false;
            return;
        }

        panel = new LinearLayout(this);
        panel.setOrientation(LinearLayout.VERTICAL);
        panel.setPadding(22, 18, 22, 18);
        panel.setBackgroundColor(Color.rgb(20, 15, 20));

        TextView heading = new TextView(this);
        heading.setText("STEVE_IA 😈");
        heading.setTextColor(Color.rgb(255, 70, 80));
        heading.setTextSize(20);
        heading.setTypeface(null, Typeface.BOLD);
        panel.addView(heading);

        TextView message = new TextView(this);
        message.setText(
                "Assistant flottant activé.\\n"
                + "Ouvre Steve_ia pour configurer Gemini et tes contacts.");
        message.setTextColor(Color.WHITE);
        message.setTextSize(15);
        message.setPadding(0, 14, 0, 14);
        panel.addView(message);

        TextView close = new TextView(this);
        close.setText("Fermer la fenêtre");
        close.setTextColor(Color.rgb(255, 100, 100));
        close.setPadding(0, 10, 0, 4);
        close.setOnClickListener(v -> togglePanel());
        panel.addView(close);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.TOP | Gravity.END;
        params.x = 20;
        params.y = 330;
        manager.addView(panel, params);
        expanded = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (manager != null) {
            if (panel != null) manager.removeView(panel);
            if (bubble != null) manager.removeView(bubble);
        }
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
