/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin;

import latexlaskin.wa.WACalculator;
import java.util.*;
import java.util.logging.*;
import latexlaskin.latexconverter.*;
import latexlaskin.wa.WAResultProcesser;

public class Main {

    private static final String APPID = "WJ628E-G3H5VTERP4";
    private static final String MODE = "plaintext";
    private static final boolean DEBUG = true;
    private static final String INPUT = "\\sum_{i=0}^n i";

    public static void main(String[] args) {
        if (args.length > 1) {
            System.out.println("Anna parametrina korkeintaan yksi merkkijono.");
            return;
        }
        String input = INPUT;
        if (args.length == 1) {
            input = args[0];
        }
        disableLogging();
        WACalculator calc = new WACalculator(APPID, MODE, DEBUG);
        List<String> results = calc.query(input);
        printAllResults(results, calc.getError());
    }

    private static void printAllResults(List<String> results, String error) {
        if (results == null) {
            System.out.println(error);
            return;
        }
        System.out.println("Tuetut ratkaisut:");
        printResults(results);
        results = WAResultProcesser.process(results);
        System.out.println("Prosessoituna:");
        printResults(results);
        results = LaTeXConverter.convert(results);
        System.out.println("LaTeX-koodina:");
        printResults(results);
    }

    private static void printResults(List<String> results) {
        for (String result : results) {
            System.out.println(result);
        }
    }

    private static void disableLogging() {
        Logger.getLogger("com.wolfram.alpha.net.URLFetcher")
                .setLevel(Level.OFF);
    }
}
