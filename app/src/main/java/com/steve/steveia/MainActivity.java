package com.steve.steveia;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private int white = Color.WHITE;
    private int red = Color.rgb(255, 55, 65);
    private EditText apiKey;

    private TextView text(String value, int size) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(white);
        t.setPadding(0, 10, 0, 10);
        return t;
    }

    private Button button(String value) {
        Button b = new Button(this);
        b.setText(value);
        b.setTextColor(white);
        b.setAllCaps(false);
        b.setBackgroundTintList(android.content.res.ColorStateList.valueOf(
                Color.rgb(65, 24, 32)));
        return b;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView scroll = new ScrollView(this);
        scroll.setBackgroundColor(Color.rgb(12, 12, 15));

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(24, 28, 24, 28);
        scroll.addView(root);

        TextView logo = text("😈", 52);
        logo.setGravity(Gravity.CENTER);
        root.addView(logo);

        TextView title = text("STEVE_IA", 30);
        title.setTypeface(null, Typeface.BOLD);
        title.setTextColor(red);
        title.setGravity(Gravity.CENTER);
        root.addView(title);

        TextView intro = text(
                "Ton assistant de conversation pour WhatsApp et WhatsApp Business.",
                16);
        intro.setGravity(Gravity.CENTER);
        root.addView(intro);

        root.addView(text("MODE DE FONCTIONNEMENT", 17));

        Button suggestions = button("Suggestions de réponses");
        root.addView(suggestions);
        suggestions.setOnClickListener(v ->
                Toast.makeText(this, "Mode suggestions sélectionné",
                        Toast.LENGTH_SHORT).show());

        Button automatic = button("Réponses automatiques");
        root.addView(automatic);
        automatic.setOnClickListener(v ->
                Toast.makeText(this,
                        "Le mode automatique sera activé après configuration.",
                        Toast.LENGTH_LONG).show());

        Button off = button("Désactiver les réponses IA");
        root.addView(off);
        off.setOnClickListener(v ->
                Toast.makeText(this, "Réponses IA désactivées",
                        Toast.LENGTH_SHORT).show());

        root.addView(text("CLÉ API GEMINI", 17));
        apiKey = new EditText(this);
        apiKey.setSingleLine(true);
        apiKey.setHint("Colle ta clé API Gemini ici");
        apiKey.setHintTextColor(Color.LTGRAY);
        apiKey.setTextColor(white);
        root.addView(apiKey);

        root.addView(text("BULLE FLOTTANTE", 17));
        Button floating = button("Activer la bulle démon 😈");
        root.addView(floating);
        floating.setOnClickListener(v -> {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                Toast.makeText(this,
                        "Autorise Steve_ia à s'afficher par-dessus les autres applications.",
                        Toast.LENGTH_LONG).show();
            } else {
                startService(new Intent(this, FloatingChatService.class));
                Toast.makeText(this, "Bulle flottante activée",
                        Toast.LENGTH_SHORT).show();
            }
        });

        Button contacts = button("Autoriser l'accès aux contacts");
        root.addView(contacts);
        contacts.setOnClickListener(v -> {
            if (android.os.Build.VERSION.SDK_INT >= 23 &&
                    checkSelfPermission(android.Manifest.permission.READ_CONTACTS)
                            != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[]{android.Manifest.permission.READ_CONTACTS}, 10);
            } else {
                Toast.makeText(this, "Accès aux contacts autorisé",
                        Toast.LENGTH_SHORT).show();
            }
        });

        Button notifications = button("Connecter les notifications WhatsApp");
        root.addView(notifications);
        notifications.setOnClickListener(v ->
                startActivity(new Intent(
                        "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")));

        root.addView(text(
                "Profil : Steve, entrepreneur indépendant à Yaoundé. "
                + "Échecs, calme, paix, humour, romantisme et conversations sincères. ❤️",
                15));

        setContentView(scroll);
    }
}
