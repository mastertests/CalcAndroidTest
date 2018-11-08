package com.test.actions;

import com.test.base.BaseTest;
import com.test.util.reporter.Reporter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class AdbActions {

    private final String device = BaseTest.udid;

    public void installApp(String pathToApk) throws Exception {

        Reporter.log("Installing application");

        File file = new File(pathToApk);

        Reporter.log(executeCommand(String.format(
                "adb -s %s install \"%s\"", device, file.getAbsolutePath())));
    }

    public boolean isApkInstalled(String appPackage) throws IOException, InterruptedException {

        return executeCommand(String.format(
                "adb -s %s shell pm list packages %s", device, appPackage))
                .equals("package:" + appPackage);
    }

    public void uninstallApp(String appPackage) throws IOException, InterruptedException {

        Reporter.log("Uninstalling application");

        Reporter.log(executeCommand(String.format(
                "adb -s %s uninstall %s", device, appPackage)));
    }

    private String executeCommand(String command) throws InterruptedException, IOException {

        Process process = Runtime.getRuntime().exec(command);

        StringBuilder result = new StringBuilder();

        new Thread(() -> {
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            try {
                while ((line = input.readLine()) != null)
                    result.append(line).append("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        process.waitFor();

        return result.toString().trim();
    }
}
