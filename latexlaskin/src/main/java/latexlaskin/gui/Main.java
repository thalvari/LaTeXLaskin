/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin.gui;

import java.util.List;
import javafx.application.Application;
import javafx.stage.Stage;
import latexlaskin.calculator.Calculator;

/**
 *
 * @author thalvari
 */
public class Main extends Application {

    private static final String APPID = "WJ628E-G3H5VTERP4";
    private static final boolean DEBUG = true;
    private static final String FORMAT = "plaintext";
    private static final String INPUT = "|2 x - 2|";

    private static Calculator calc;

    public static void main(String[] args) {
        if (args.length == 0) {
            launch(Main.class);
        } else if (args.length > 1) {
            System.out.println("Anna parametrina korkeintaan yksi merkkijono.");
            return;
        }

        calc = new Calculator(APPID, DEBUG, FORMAT);
        List<String> results = calc.query(args[0]);
        printResults(results);
    }

    @Override
    public void start(Stage stage) {
        new GUI(stage, new Calculator(APPID, DEBUG, FORMAT)).start();
    }

    private static void printResults(List<String> results) {
        if (results == null) {
            System.out.println(calc.getError());
            return;
        }

        System.out.println("Ratkaisut LaTeX-koodina:");
        for (String result : results) {
            System.out.println(result);
        }
    }
}
