/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin.wa;

import java.util.ArrayList;
import java.util.List;

/**
 * Prosessoi API:lta saatuja ratkaisuja.
 *
 * @author thalvari
 */
public class WAResultProcesser {

    // Vastaavat xml-tiedoston rikkinäisiä symboleita.
    private static final char EQUALS = 63449;
    private static final char NEPER = 63309;
    private static final char IMAGINARY = 63310;

    /**
     * Tyhjä konstruktori.
     */
    public WAResultProcesser() {
    }

    /**
     * Poistaa ratkaisuista turhan tekstin ja korvaa rikkinäiset symbolit.
     *
     * @param results Ratkaisut.
     * @return Prosessoidut ratkaisut.
     */
    public static List<String> process(List<String> results) {
        results = removeBadResults(results);
        trimResults(results);
        replaceSymbols(results);
        return results;
    }

    private static List<String> removeBadResults(List<String> results) {
        List<String> goodResults = new ArrayList();
        for (String result : results) {
            if (!result.contains("(for")) {
                goodResults.add(result);
            }
        }
        return goodResults;
    }

    private static void trimResults(List<String> results) {
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

    private static void replaceSymbols(List<String> results) {
        for (int i = 0; i < results.size(); i++) {
            results.set(i, replaceSymbols(results.get(i)));
        }
    }

    private static String replaceSymbols(String result) {
        char[] chars = result.toCharArray();
        for (int i = 0; i < result.length(); i++) {
            if (chars[i] == NEPER) {
                chars[i] = 'e';
            } else if (chars[i] == IMAGINARY) {
                chars[i] = 'i';
            }
        }
        return new String(chars);
    }
}
