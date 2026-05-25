package com.myterminal.app;

import android.content.Context;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ShellManager {

    private String mode = "Обычный Shell";
    private Context context;

    public ShellManager(Context context) {
        this.context = context;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public boolean hasRoot() {
        try {
            Process process = Runtime.getRuntime().exec("su -c id");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );
            String result = reader.readLine();
            return result != null && result.contains("uid=0");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean hasShizuku() {
        try {
            Class.forName("rikka.shizuku.Shizuku");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String exec(String cmd) {
        try {
            Process process;

            if (mode.contains("Root")) {
                process = Runtime.getRuntime().exec(new String[]{"su", "-c", cmd});
            } else {
                process = Runtime.getRuntime().exec(cmd);
            }

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );

            StringBuilder output = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            return output.toString();

        } catch (Exception e) {
            return e.toString();
        }
    }
  }
