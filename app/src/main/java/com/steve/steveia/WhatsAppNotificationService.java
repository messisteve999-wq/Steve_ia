package com.steve.steveia;

import android.app.Notification;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.media.AudioManager;
import android.media.ToneGenerator;

public class WhatsAppNotificationService extends NotificationListenerService {
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        String packageName = sbn.getPackageName();
        if (!packageName.equals("com.whatsapp")
                && !packageName.equals("com.whatsapp.w4b")) {
            return;
        }

        Notification notification = sbn.getNotification();
        if (notification == null || notification.extras == null) return;

        CharSequence title = notification.extras.getCharSequence(
                Notification.EXTRA_TITLE);
        if (title == null) return;

        // Notification access is used only to detect WhatsApp messages.
        // Contact filtering and reply handling will be added in a later version.
        ToneGenerator tone = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 70);
        tone.startTone(ToneGenerator.TONE_PROP_BEEP, 180);
        tone.release();
    }
}
