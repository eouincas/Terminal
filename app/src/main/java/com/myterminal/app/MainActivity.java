package com.myterminal.app;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView output;
    private EditText input;
    private ShellManager shellManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        output = findViewById(R.id.output);
        input = findViewById(R.id.input);

        shellManager = new ShellManager(this);

        showModeDialog();
    }

    private void showModeDialog() {
        boolean hasRoot = shellManager.hasRoot();
        boolean hasAdb = shellManager.hasShizuku();

        String[] options;

        if (hasRoot && hasAdb) {
            options = new String[]{
                    "Использовать ADB + Root",
                    "Только Root",
                    "Только ADB",
                    "Обычный Shell"
            };
        } else if (hasRoot) {
            options = new String[]{
                    "Только Root",
                    "Обычный Shell"
            };
        } else if (hasAdb) {
            options = new String[]{
                    "Только ADB",
                    "Обычный Shell"
            };
        } else {
            options = new String[]{
                    "Обычный Shell"
            };
        }

        new AlertDialog.Builder(this)
                .setTitle("Выберите режим")
                .setItems(options, (dialog, which) -> {
                    String selected = options[which];
                    output.setText("Режим: " + selected);
                    shellManager.setMode(selected);
                })
                .show();
    }
    }
