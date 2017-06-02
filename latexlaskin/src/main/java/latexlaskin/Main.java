/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import latexlaskin.calculator.latexconverter.LaTeXConverter;
import latexlaskin.calculator.wa.WACalculator;
import latexlaskin.calculator.wa.WAResultProcesser;

public class Main {

    private static final String APPID = "WJ628E-G3H5VTERP4";
    private static final String INPUT = "e^{ix}";

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
        WACalculator calc = new WACalculator(APPID);
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
