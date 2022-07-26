/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zereb.doomlauncher;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * @author Plett Oleg
 */
public class DoomLauncher extends Application {

    public static final String DOOM_LAUNCHER_FXML = "/DoomLauncher.fxml";
    public static final String TITLE_STRING = "Doom Launcher 2.0";
    public static final int WINDOW_WIDTH = 840;
    public static final int WINDOW_HEIGHT = 580;

    private static Stage pStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
    }

    @Override
    public void start(Stage stageDoomLauncher) throws IOException {
        var fxml = getClass().getResource(DOOM_LAUNCHER_FXML);
        Parent rootNode = FXMLLoader.load(fxml);
        stageDoomLauncher.setTitle(TITLE_STRING);
        stageDoomLauncher.setMinHeight(WINDOW_HEIGHT);
        stageDoomLauncher.setMinWidth(WINDOW_WIDTH);
        setPrimaryStage(stageDoomLauncher);

        Scene sceneDoomLauncher = new Scene(rootNode, WINDOW_WIDTH, WINDOW_HEIGHT);
        sceneDoomLauncher.getStylesheets().add("/DoomLauncher.css");
        stageDoomLauncher.setScene(sceneDoomLauncher);
        stageDoomLauncher.show();
    }

    @Override
    public void stop() {

    }

    private void setPrimaryStage(Stage pStage) {
        DoomLauncher.pStage = pStage;
    }

    public static Stage getPrimaryStage() {
        return pStage;
    }


}
