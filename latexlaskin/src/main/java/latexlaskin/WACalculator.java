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

    private WAEngine engine;
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
     * Kysyy API:lta laskun tulosta.
     *
     * @param input Laskutoimitus tekstinä.
     * @return Lista vaihtoehtoisista ratkaisuista.
     */
    public List<String> query(String input) {
        error = null;
        WAQuery query = engine.createQuery();
        query.setInput(input);

        if (debug) {
            printDebug(query);
        }

        WAQueryResult queryResult;
        try {
            queryResult = engine.performQuery(query);
        } catch (WAException e) {
            error = "Tapahtui verkkovirhe.";
            return null;
        }
        return processResults(queryResult);
    }

    private List<String> processResults(WAQueryResult queryResult) {
        if (queryResult.isError()) {
            error = "Osoite virheellinen.";
            return null;
        }
        if (!queryResult.isSuccess()) {
            error = "Syöte virheellinen.";
            return null;
        }
        List<String> results = new ArrayList();
        for (WAPod pod : queryResult.getPods()) {
            if (pod.getID().equals("Result")
                    || pod.getID().equals("AlternateForm")
                    || pod.getID().equals("ExpandedForm")
                    || pod.getID().equals("DecimalApproximation")
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
        int idx = result.indexOf('');
        if (idx != -1) {
            result = result.substring(idx + 1);
        }
        return result;
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

    private void printDebug(WAQuery query) {
        System.out.println("Ratkaisu WA:n sivuilla:");
        System.out.println(query.toWebsiteURL());
        System.out.println("Ratkaisu XML-tiedostona:");
        System.out.println(engine.toURL(query) + "\n");
    }
}
