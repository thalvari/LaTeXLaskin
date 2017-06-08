/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin.gui;

import javafx.application.Application;
import javafx.stage.Stage;
import latexlaskin.calculator.Calculator;

/**
 *
 * @author thalvari
 */
public class Main extends Application {

    private static final String APPID = "LE69JY-QUPK5KWVWV";
    private static final String FORMAT = "plaintext";
    private static boolean DEBUG = false;

    public static void main(String[] args) {
        if (args.length == 1 && args[0].equals("--debug")) {
            DEBUG = true;
        }
        launch(Main.class);
    }

    @Override
    public void start(Stage stage) {
        new GUI(stage, new Calculator(APPID, DEBUG, FORMAT)).start();
    }

    @Override
    public void stop() throws Exception {
    }
}
