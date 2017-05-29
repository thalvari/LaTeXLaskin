/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Ohjelman suoritus alkaa pääluokasta.
 *
 * @author thalvari
 */
public class Main {

    private static final String APPID = "WJ628E-G3H5VTERP4";
    private static final String MODE = "plaintext";
    private static final boolean DEBUG = true;

    private static final String INPUT = "\\int_0^1 2x";

    /**
     * Ohjelman suoritus alkaa pääluokan main-metodista.
     *
     * @param args komentoriviparametrit
     */
    public static void main(String[] args) {
        Logger.getLogger("com.wolfram.alpha.net.URLFetcher")
                .setLevel(Level.OFF);

        WACalculator calc = new WACalculator(APPID, MODE, DEBUG);
        List<String> results = calc.query(INPUT);
        if (calc.getError() != null) {
            System.out.println(calc.getError());
        } else {
            System.out.println("Tulostetaan vaihtoehtoiset ratkaisut:");
            for (String result : results) {
                System.out.println(result);
            }
        }
    }
}
