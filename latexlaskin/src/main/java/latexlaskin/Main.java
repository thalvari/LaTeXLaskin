/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin;

import java.util.List;
import latexlaskin.calculator.Calculator;

public class Main {

    private static final String APPID = "WJ628E-G3H5VTERP4";
    private static final boolean DEBUG = true;
    private static final String FORMAT = "plaintext";
    private static final String INPUT = "(1-e^2x)/(1+e^{2x})";

    private static Calculator calc;

    public static void main(String[] args) {
        if (args.length > 1) {
            System.out.println("Anna parametrina korkeintaan yksi merkkijono.");
            return;
        }

        String input = INPUT;
        if (args.length == 1) {
            input = args[0];
        }

        calc = new Calculator(APPID, DEBUG, FORMAT);
        List<String> results = calc.query(input);
        printResults(results);
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
