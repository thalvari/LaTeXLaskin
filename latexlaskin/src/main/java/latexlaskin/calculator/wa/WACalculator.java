/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin.calculator.wa;

import com.wolfram.alpha.WAEngine;
import com.wolfram.alpha.WAException;
import com.wolfram.alpha.WAPlainText;
import com.wolfram.alpha.WAPod;
import com.wolfram.alpha.WAQuery;
import com.wolfram.alpha.WAQueryResult;
import com.wolfram.alpha.WASubpod;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Ratkaisee laskuja API:a hyödyntäen.
 *
 * @author thalvari
 */
public class WACalculator {

    private final WAEngine engine;
    private String error;
    private String waUrl;
    private String waXmlUrl;

    /**
     * Konstruktori.
     *
     * @param appid Käyttäjän AppID.
     * @param format
     */
    public WACalculator(String appid, String format) {
        engine = new WAEngine();
        engine.setAppID(appid);
        engine.addFormat(format);
        disableLogging();
    }

    private static void disableLogging() {
        Logger.getLogger("com.wolfram.alpha.net.URLFetcher").setLevel(
                Level.OFF);
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
        }
        waUrl = query.toWebsiteURL();
        waXmlUrl = engine.toURL(query);
        return extractResults(queryResult);
    }

    private WAQuery createQuery(String input) {
        WAQuery query = engine.createQuery();
        query.setInput(input);
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
            if (WASupported.isSupported(pod)) {
                results.addAll(extractResults(pod));
            }
        }
        return results;
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

    public String getError() {
        return error;
    }

    public String getWaUrl() {
        return waUrl;
    }

    public String getWaXmlUrl() {
        return waXmlUrl;
    }
}
