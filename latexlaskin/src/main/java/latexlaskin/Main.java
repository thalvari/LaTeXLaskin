/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import latexlaskin.wacalculator.WACalculator;
import latexlaskin.latexconverter.LaTeXConverter;

/**
 * Ohjelman suoritus alkaa pääluokasta.
 *
 * @author thalvari
 */
public class Main {

    private static final String APPID = "WJ628E-G3H5VTERP4";
    private static final String MODE = "plaintext";
    private static final boolean DEBUG = true;

    private static final String INPUT = "|2x-2|";

    private static WACalculator calc;

    /**
     * Ohjelman suoritus alkaa pääluokan main-metodista.
     *
     * @param args komentoriviparametrit
     */
    public static void main(String[] args) {
        disableAPILogging();
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
//        printResults(results);
        printResults(LaTeXConverter.convert(results));
    }

    private static void printResults(List<String> results) {
        if (results == null) {
            System.out.println(calc.getError());
        } else {
            if (results.isEmpty()) {
                System.out.println("Ei tuettu.");
            } else if (results.size() == 1) {
                System.out.println("Ratkaisu:");
            } else {
                System.out.println("Vaihtoehtoiset ratkaisut:");
            }
            for (String result : results) {
                System.out.println(result);
            }
        }
    }

    private static void disableAPILogging() {
        Logger.getLogger("com.wolfram.alpha.net.URLFetcher")
                .setLevel(Level.OFF);
    }
}
