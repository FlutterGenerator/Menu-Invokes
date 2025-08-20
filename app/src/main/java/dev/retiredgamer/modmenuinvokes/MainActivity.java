package dev.retiredgamer.modmenuinvokes;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    Button above, below, button_Imgui, button_Lgl, generate_imgui;
    ImageButton button_copy_activity, button_copy_alert, button_copy_imgui, button_copy_service;
    TextView activity_code, alert_code, service_code, imgui_code;
    EditText libname;
    RelativeLayout controlbuttonsid, imgui_layout, text1, text2, text3;
    LinearLayout libnamelayout, modtypeid, versionType;
    String activity, alert, service;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Настройка кастомного ActionBar
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setHomeButtonEnabled(false);

            TextView textView = new TextView(this);
            textView.setText("Retired Gamer");
            textView.setTextSize(20);
            textView.setTextColor(0xFFFFFFFF);
            textView.setGravity(Gravity.CENTER);

            ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                    ActionBar.LayoutParams.WRAP_CONTENT,
                    ActionBar.LayoutParams.WRAP_CONTENT,
                    Gravity.CENTER
            );

            actionBar.setCustomView(textView, params);
        }

        // Установка layout
        setContentView(R.layout.activity_main);

        // Инициализация элементов
        button_Lgl = findViewById(R.id.button_Lgl);
        button_Imgui = findViewById(R.id.button_Imgui);
        above = findViewById(R.id.above);
        below = findViewById(R.id.below);
        modtypeid = findViewById(R.id.modtypeid);
        controlbuttonsid = findViewById(R.id.controlbuttonsid);
        button_copy_imgui = findViewById(R.id.button_copy_imgui);
        button_copy_activity = findViewById(R.id.button_copy_activity);
        button_copy_service = findViewById(R.id.button_copy_service);
        button_copy_alert = findViewById(R.id.button_copy_alert);
        libname = findViewById(R.id.libname);
        libnamelayout = findViewById(R.id.libnamelayout);
        alert_code = findViewById(R.id.alert_code);
        activity_code = findViewById(R.id.activity_code);
        service_code = findViewById(R.id.service_code);
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
        versionType = findViewById(R.id.versionType);
        generate_imgui = findViewById(R.id.generate_imgui);
        imgui_code = findViewById(R.id.imgui_code);

        // Обработчики кнопок
        button_Imgui.setOnClickListener(v -> {
            button_Lgl.setVisibility(View.VISIBLE);
            button_Imgui.setVisibility(View.VISIBLE);
            handleImgui();
        });

        above.setOnClickListener(v -> generateAbove());

        below.setOnClickListener(v -> generateBelow());

        button_Lgl.setOnClickListener(v -> {
            button_Lgl.setVisibility(View.VISIBLE);
            button_Imgui.setVisibility(View.VISIBLE);
            handleLgl();
        });

        button_copy_imgui.setOnClickListener(v -> copyToClipboard(imgui_code));

        button_copy_activity.setOnClickListener(v -> copyToClipboard(activity_code));

        button_copy_alert.setOnClickListener(v -> copyToClipboard(alert_code));

        button_copy_service.setOnClickListener(v -> copyToClipboard(service_code));

        generate_imgui.setOnClickListener(v -> {
            String editable = libname.getText().toString();
            if (editable.isEmpty()) {
                editable = "Retiredgamer";
            }
            if (!editable.startsWith("lib")) {
                editable = "lib" + editable;
            }
            if (editable.indexOf("lib", 1) != -1) {
                editable = editable.replace("lib", "");
            }

            imgui_code.setText("const-string v0, \"" + editable + "\"\n\n" +
                    "invoke-static {v0}, Ljava/lang/System;->loadLibrary(Ljava/lang/String;)V");
        });
    }

    // Метод копирования текста в буфер обмена
    private void copyToClipboard(TextView textView) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        if (clipboard != null) {
            clipboard.setPrimaryClip(ClipData.newPlainText("copied text", textView.getText().toString()));
            Toast.makeText(this, "Text copied", Toast.LENGTH_SHORT).show();
        }
    }

    // Показать layout для ImGui
    private void handleImgui() {
        libnamelayout.setVisibility(View.VISIBLE);
        generate_imgui.setVisibility(View.VISIBLE);
    }

    // Показать layout для Lgl
    private void handleLgl() {
        text1.setVisibility(View.VISIBLE);
        text2.setVisibility(View.VISIBLE);
        text3.setVisibility(View.VISIBLE);
        versionType.setVisibility(View.VISIBLE);
    }

    // Генерация кода для "above"
    private void generateAbove() {
        alert = "<uses-permission android:name=\"android.permission.SYSTEM_ALERT_WINDOW\" />";
        activity = "invoke-static {p0}, Lcom/android/support/Main;->Start(Landroid/content/Context;)V";
        service = "<service\n android:name=\"com.android.support.Launcher\"\n" +
                "android:enabled=\"true\"\n" +
                "android:exported=\"false\"\n" +
                "android:stopWithTask=\"true\"/>";
        alert_code.setText(alert);
        activity_code.setText(activity);
        service_code.setText(service);
    }

    // Генерация кода для "below"
    private void generateBelow() {
        alert = "<uses-permission android:name=\"android.permission.SYSTEM_ALERT_WINDOW\" />";
        activity = "invoke-static {p0}, Luk/lgl/MainActivity;->Start(Landroid/content/Context;)V";
        service = "<service\n" +
                "android:name=\"uk.lgl.modmenu.FloatingModMenuService\"\n" +
                "android:enabled=\"true\"\n" +
                "android:exported=\"false\"\n" +
                "android:stopWithTask=\"true\" />";
        alert_code.setText(alert);
        activity_code.setText(activity);
        service_code.setText(service);
    }
}