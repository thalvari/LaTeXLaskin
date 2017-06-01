/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin.wa;

import com.wolfram.alpha.WAEngine;
import com.wolfram.alpha.WAException;
import com.wolfram.alpha.WAPlainText;
import com.wolfram.alpha.WAPod;
import com.wolfram.alpha.WAQuery;
import com.wolfram.alpha.WAQueryResult;
import com.wolfram.alpha.WASubpod;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Ratkaisee laskuja API:a hyödyntäen.
 *
 * @author thalvari
 */
public class WACalculator {

    private static final String[] SUPPORTED_POD_IDS = {
        "Result", "AlternateForm", "ExpandedForm", "DecimalApproximation",
        "AlternateFormAssumingAllVariablesAreReal"
    };
    private static final String[] SUPPORTED_FIRST_POD_TITLES = {
        "Derivative", "Indefinite integral", "Definite integral"
    };

    private final WAEngine engine;
    private String error;
    private boolean debug;

    /**
     * Konstruktori.
     *
     * @param appid Käyttäjän AppID.
     * @param format Tuetut formaatit ratkaisuille.
     * @param debug Käytetäänkö debuggaustilaa.
     */
    public WACalculator(String appid, String format, boolean debug) {
        engine = new WAEngine();
        engine.setAppID(appid);
        engine.addFormat(format);
        this.debug = debug;
    }

    /**
     * Palauttaa laskumoottorin.
     *
     * @return Laskumoottori.
     */
    public WAEngine getEngine() {
        return engine;
    }

    /**
     * Palauttaa edellisen virheviestin.
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
     * Kysyy API:lta laskun tulosta, palauttaen kaikki ratkaisut tuetuista
     * podeista.
     *
     * @param input Syöte.
     * @return Ratkaisut.
     */
    public List<String> query(String input) {
        if (input.isEmpty()) {
            error = "Tyhjä syöte.";
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
            return extractResults(queryResult);
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

    private List<String> extractResults(WAQueryResult queryResult) {
        List<String> results = new ArrayList();
        for (WAPod pod : queryResult.getPods()) {
            if (isSupported(pod)) {
                results.addAll(extractResults(pod));
            }
        }
        return results;
    }

    private boolean isSupported(WAPod pod) {
        return Arrays.asList(SUPPORTED_POD_IDS).contains(pod.getID())
                || (Arrays.asList(SUPPORTED_FIRST_POD_TITLES).contains(
                        pod.getTitle()) && pod.getPosition() == 100);
    }

    private List<String> extractResults(WAPod pod) {
        List<String> results = new ArrayList();
        for (WASubpod subpod : pod.getSubpods()) {
            for (Object o : subpod.getContents()) {
                results.add(((WAPlainText) o).getText());
            }
        }
        return results;
    }

    private void printDebug(WAQuery query) {
        System.out.println("Ratkaisut WA:n sivuilla:");
        System.out.println(query.toWebsiteURL());
        System.out.println("Ratkaisut XML-tiedostona:");
        System.out.println(engine.toURL(query));
    }
}
