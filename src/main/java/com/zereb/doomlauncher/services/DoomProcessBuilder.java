/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.zereb.doomlauncher.services;

import java.io.IOException;
import java.util.List;

public class DoomProcessBuilder {
    private static DoomProcessBuilder instance;

    public static DoomProcessBuilder getInstance() {
        if (instance == null)
            instance = new DoomProcessBuilder();
        return instance;
    }

    private DoomProcessBuilder() {
    }

    public void startDoom(List<String> cmd) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(cmd);
            processBuilder.start();
        } catch (IOException e) {
            UiLogger.println(e.toString());
        }
    }

}
