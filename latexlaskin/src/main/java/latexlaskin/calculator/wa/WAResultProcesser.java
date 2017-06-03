/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin.calculator.wa;

import java.util.ArrayList;
import java.util.List;

/**
 * Metodeja API:lta saatujen ratkaisujen prosessoimiseksi.
 *
 * @author thalvari
 */
public class WAResultProcesser {

    private static final char EQUALS = 63449;

    /**
     * Tyhjä konstruktori.
     */
    public WAResultProcesser() {
    }

    /**
     * Poistaa huonot ratkaisut.
     *
     * @param results Ratkaisut.
     * @return Prosessoidut ratkaisut.
     */
    public static List<String> removeBadResults(List<String> results) {
        List<String> goodResults = new ArrayList();
        for (String result : results) {
            if (!result.contains("(for")) {
                goodResults.add(result);
            }
        }
        return goodResults;
    }

    /**
     * Poistaa ratkaisuista turhan tekstin.
     *
     * @param results Ratkaisut.
     */
    public static void trimResults(List<String> results) {
        for (int i = 0; i < results.size(); i++) {
            results.set(i, trimResult(results.get(i)));
        }
    }

    private static String trimResult(String result) {
        int idx = result.indexOf(EQUALS);
        if (idx != -1) {
            result = result.substring(idx + 1);
        }
        idx = result.indexOf('≈');
        if (idx != -1) {
            result = result.substring(0, idx);
        }
        return result;
    }
}
