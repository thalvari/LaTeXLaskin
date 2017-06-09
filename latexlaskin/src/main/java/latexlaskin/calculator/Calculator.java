/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin.calculator;

import java.util.List;
import latexlaskin.calculator.latexconverter.LaTeXConverter;
import latexlaskin.calculator.wa.WACalculator;
import latexlaskin.calculator.wa.WAResultProcesser;

/**
 * LaTeX-laskin.
 *
 * @author thalvari
 */
public class Calculator {

    private final WACalculator waCalc;
    private boolean debug;

    /**
     * Konstruktori.
     *
     * @param appid Käyttäjän AppID.
     * @param debug Debug-tila.
     * @param format API:n palauttamat formaatit.
     */
    public Calculator(String appid, boolean debug, String format) {
        waCalc = new WACalculator(appid, format);
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
        List<String> results = waCalc.query(input);
        if (debug) {
            printURLs();
        }

        if (results == null) {
            return null;
        }

        results = process(results);
        if (debug) {
            printResults(results);
        }

        results = convert(results);
        if (debug) {
            printResults(results);
        }

        return results;
    }

    private List<String> process(List<String> results) {
        results = WAResultProcesser.removeBadResults(results);
        WAResultProcesser.trimResults(results);
        return results;
    }

    private List<String> convert(List<String> results) {
        LaTeXConverter.replaceSlashes(results);
        if (debug) {
            printResults(results);
        }

        LaTeXConverter.replace(results);
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
