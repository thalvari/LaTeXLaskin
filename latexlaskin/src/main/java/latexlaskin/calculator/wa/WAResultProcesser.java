/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin.calculator.wa;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Metodeja API:lta saatujen ratkaisujen prosessoimiseksi.
 *
 * @author thalvari
 */
public class WAResultProcesser {

    /**
     * Joukko merkeistä, joita edeltävä osa leikataan ratkaisusta.
     */
    private static final Set<String> TRIM_START;

    /**
     * Joukko merkeistä, joita seuraava osa leikataan ratkaisusta.
     */
    private static final Set<String> TRIM_END;

    static {
        TRIM_START = new HashSet();
        TRIM_START.add("");

        TRIM_END = new HashSet();
        TRIM_END.add(",");
        TRIM_END.add("≈");
        TRIM_END.add("onstant");
    }

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
     * Poistaa ratkaisuista turhat osat.
     *
     * @param results Ratkaisut.
     */
    public static void trimResults(List<String> results) {
        for (int i = 0; i < results.size(); i++) {
            results.set(i, trimResult(results.get(i)));
        }
    }

    private static String trimResult(String result) {
        result = trimStart(result);
        result = trimEnd(result);
        return result;
    }

    private static String trimStart(String result) {
        for (String s : TRIM_START) {
            int idx = result.indexOf(s);
            if (idx != -1) {
                result = result.substring(idx + 1);
            }
        }

        return result;
    }

    private static String trimEnd(String result) {
        for (String s : TRIM_END) {
            int idx = result.indexOf(s);
            if (idx != -1) {
                result = result.substring(0, idx);
            }
        }

        return result;
    }
}
