/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin;

import com.wolfram.alpha.WAEngine;
import com.wolfram.alpha.WAException;
import com.wolfram.alpha.WAPlainText;
import com.wolfram.alpha.WAPod;
import com.wolfram.alpha.WAQuery;
import com.wolfram.alpha.WAQueryResult;
import java.util.ArrayList;
import java.util.List;

/**
 * Tekee laskutoimitukset hyödyntäen API:a.
 *
 * @author thalvari
 */
public class WACalculator {

    // Vastaavat xml-tiedoston rikkinäisiä symboleita.
    private static final char EQUALS = 63449;
    private static final char NEPER = 63309;
    private static final char IMAGINARY = 63310;

    private final WAEngine engine;
    private String error;
    private boolean debug;

    /**
     * Luokan konstruktori.
     *
     * @param appid Käyttäjän AppID.
     * @param format Mitä halutaan mukaan vastaukseen.
     * @param debug Käytetäänkö debuggaustilaa.
     */
    public WACalculator(String appid, String format, boolean debug) {
        engine = new WAEngine();
        engine.setAppID(appid);
        engine.addFormat(format);
        this.debug = debug;
    }

    /**
     * Palauttaa luodun laskumoottorin.
     *
     * @return laskumoottori
     */
    public WAEngine getEngine() {
        return engine;
    }

    /**
     * Palauttaa mahdollisen virheviestin.
     *
     * @return Virheviesti.
     */
    public String getError() {
        return error;
    }

    /**
     * Asettaa debug-tilan päälle tai pois.
     *
     * @param debug Uusi tila.
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    /**
     * Kysyy API:lta laskun tulosta.
     *
     * @param input Laskutoimitus tekstinä.
     * @return Lista vaihtoehtoisista ratkaisuista.
     */
    public List<String> query(String input) {
        if (input.isEmpty()) {
            error = "Syöte virheellinen.";
            return null;
        }
        WAQuery query = createQuery(input);
        WAQueryResult queryResult;
        try {
            queryResult = engine.performQuery(query);
        } catch (WAException e) {
            error = "Tapahtui verkkovirhe.";
            return null;
        }
        if (!checkResults(queryResult)) {
            return null;
        } else {
            return processResults(queryResult);
        }
    }

    private WAQuery createQuery(String input) {
        WAQuery query = engine.createQuery();
        query.setInput(input);
        if (debug) {
            printDebug(query);
        }
        return query;
    }

    private boolean checkResults(WAQueryResult queryResult) {
        if (queryResult.isError()) {
            error = "AppID virheellinen.";
            return false;
        } else if (!queryResult.isSuccess()) {
            error = "Syöte virheellinen.";
            return false;
        }
        return true;
    }

    private List<String> processResults(WAQueryResult queryResult) {
        List<String> results = extractResults(queryResult);
        trimResults(results);
        replaceSymbols(results);
        return results;
    }

    private List<String> extractResults(WAQueryResult queryResult) {
        List<String> results = new ArrayList();
        for (WAPod pod : queryResult.getPods()) {
            if (pod.getID().equals("Result")
                    || pod.getID().equals("AlternateForm")
                    || pod.getID().equals("ExpandedForm")
                    || pod.getID().equals("DecimalApproximation")
                    || pod.getID().equals("AlternateFormAssumingAllVariables"
                            + "AreReal")
                    || ((pod.getTitle().equals("Derivative")
                    || pod.getTitle().equals("Indefinite integral")
                    || pod.getTitle().equals("Definite integral"))
                    && pod.getPosition() == 100)) {
                results.add(getResult(pod));
            }
        }
        return results;
    }

    private String getResult(WAPod pod) {
        Object o = pod.getSubpods()[0].getContents()[0];
        String result = ((WAPlainText) o).getText();
        return result;
    }

    private void trimResults(List<String> results) {
        for (int i = 0; i < results.size(); i++) {
            results.set(i, trimResult(results.get(i)));
        }
    }

    private String trimResult(String result) {
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

    private void replaceSymbols(List<String> results) {
        for (int i = 0; i < results.size(); i++) {
            results.set(i, replaceSymbols(results.get(i)));
        }
    }

    private String replaceSymbols(String result) {
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

    private void printDebug(WAQuery query) {
        System.out.println("Ratkaisu WA:n sivuilla:");
        System.out.println(query.toWebsiteURL());
        System.out.println("Ratkaisu XML-tiedostona:");
        System.out.println(engine.toURL(query));
    }
}
