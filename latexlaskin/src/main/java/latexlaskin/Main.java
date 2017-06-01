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

/**
 * Ohjelman suoritus alkaa pääluokasta.
 *
 * @author thalvari
 */
public class Main {

    private static final String APPID = "WJ628E-G3H5VTERP4";
    private static final String MODE = "plaintext";
    private static final boolean DEBUG = true;

    private static final String INPUT = "\\sum_{i=0}^n i";

    private static WACalculator calc;

    /**
     * Ohjelman suoritus alkaa pääluokan main-metodista.
     *
     * @param args komentoriviparametrit
     */
    public static void main(String[] args) {
        disableLogging();
        calc = new WACalculator(APPID, MODE, DEBUG);

        List<String> results;
        switch (args.length) {
            case 0:
                results = calc.query(INPUT);
                break;
            case 1:
                results = calc.query(args[0]);
                break;
            default:
                System.out.println("Anna parametrina korkeintaan yksi"
                        + " merkkijono.");
                return;
        }
        printAllResults(results);
    }

    private static void printAllResults(List<String> results) {
        if (results == null) {
            System.out.println(calc.getError());
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
