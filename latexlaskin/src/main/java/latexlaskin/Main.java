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

    private static final String INPUT = "\\sum_{i=0}^{\\infty} 1/i!";

    /**
     * Ohjelman suoritus alkaa pääluokan main-metodista.
     *
     * @param args komentoriviparametrit
     */
    public static void main(String[] args) {
        Logger.getLogger("com.wolfram.alpha.net.URLFetcher")
                .setLevel(Level.OFF);
        WACalculator calc = new WACalculator(APPID, MODE, DEBUG);
        String input = INPUT;
        switch (args.length) {
            case 0:
                break;
            case 1:
                input = args[0];
                break;
            default:
                System.out.println("Anna parametrina korkeintaan yksi"
                        + " merkkijono.");
                return;
        }
        List<String> results = calc.query(input);
        if (calc.getError() != null) {
            System.out.println(calc.getError());
        } else {
            if (results.size() == 1) {
                System.out.println("Ratkaisu:");
            } else {
                System.out.println("Vaihtoehtoiset ratkaisut:");
            }
            for (String result : results) {
                System.out.println(result);
            }
        }
    }
}
