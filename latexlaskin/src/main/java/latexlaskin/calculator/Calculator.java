/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin.calculator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import latexlaskin.calculator.latexconverter.LaTeXConverter;
import latexlaskin.calculator.wa.WACalculator;
import latexlaskin.calculator.wa.WAResultProcesser;

/**
 * LaTeX-laskin.
 *
 * @author thalvari
 */
public class Calculator {

    /**
     * Varsinainen laskin-olio.
     */
    private final WACalculator waCalc;

    /**
     * Kertoo onko debug-tila täytössä.
     */
    private boolean debug;

    /**
     * Korvattavat symbolit syötteessä.
     */
    private static final Map<String, String> INPUT_SYMBOLS;

    static {
        INPUT_SYMBOLS = new HashMap();
        INPUT_SYMBOLS.put("\\mathrm{d}", "d");
        INPUT_SYMBOLS.put("\\mathrm{e}", "e");
        INPUT_SYMBOLS.put("\\mathrm{i}", "i");
    }

    /**
     * Konstruktori.
     *
     * @param debug Debug-tila.
     */
    public Calculator(boolean debug) {
        waCalc = new WACalculator();
        this.debug = debug;
    }

    /**
     * Laskee syötteenä annetun laskun ja palauttaa tuetut ratkaisut
     * LaTeX-koodina.
     *
     * @param input Syöte.
     * @return Ratkaisut.
     */
    public List<String> query(String input) {
        input = processInput(input);
        List<String> results = waCalc.query(input);
        if (debug) {
            printURLs();
        }

        if (results == null) {
            return null;
        }

        results = processResults(results);
        results = convertResults(results);
        return results;
    }

    private String processInput(String input) {
        for (String key : INPUT_SYMBOLS.keySet()) {
            input = replaceInputSymbol(input, key);
        }

        return input;
    }

    private String replaceInputSymbol(String input, String key) {
        int idx = input.indexOf(key);
        while (idx != -1) {
            input = input.substring(0, idx)
                    + INPUT_SYMBOLS.get(key)
                    + input.substring(idx + key.length());

            idx = input.indexOf(key);
        }

        return input;
    }

    private List<String> processResults(List<String> results) {
        results = WAResultProcesser.removeBadResults(results);
        WAResultProcesser.trimResults(results);
        return results;
    }

    private List<String> convertResults(List<String> results) {
        if (debug) {
            printResults(results);
        }

        LaTeXConverter.replaceSlashes(results);
        if (debug) {
            printResults(results);
        }

        LaTeXConverter.replace(results);
        if (debug) {
            printResults(results);
        }

        LaTeXConverter.removeExtraPars(results);
        if (debug) {
            printResults(results);
        }

        return results;
    }

    private void printURLs() {
        System.out.println("---");
        System.out.println(waCalc.getResURL());
        System.out.println(waCalc.getResXMLURL());
    }

    private void printResults(List<String> results) {
        for (String result : results) {
            System.out.println(result);
        }
    }

    /**
     * Palauttaa edellisen virheviestin.
     *
     * @return Virheviesti.
     */
    public String getError() {
        return waCalc.getError();
    }

    /**
     * Asettaa debug-tilan.
     *
     * @param debug Uusi tila.
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    /**
     * Asettaa AppID:n.
     *
     * @param appID AppID.
     */
    public void setAppID(String appID) {
        waCalc.setAppID(appID);
    }

    /**
     * Palauttaa AppID:n.
     *
     * @return ApID.
     */
    public String getAppID() {
        return waCalc.getAppID();
    }
}
