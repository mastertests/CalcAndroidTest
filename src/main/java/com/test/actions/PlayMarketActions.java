package com.test.actions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PlayMarketActions {

    public void uninstallApp(String appPackage) throws IOException, InterruptedException {

        final Process process = Runtime.getRuntime().exec("adb uninstall " + appPackage);

        new Thread(() -> {
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            try {
                while ((line = input.readLine()) != null)
                    System.out.println(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        process.waitFor();
    }
}
