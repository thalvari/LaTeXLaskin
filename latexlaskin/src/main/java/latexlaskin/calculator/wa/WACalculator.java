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
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import latexlaskin.io.IO;

/**
 * API:a hyödyntävä laskin.
 *
 * @author thalvari
 */
public class WACalculator {

    private final WAEngine engine;
    private String error;
    private String resURL;
    private String resXMLURL;

    /**
     * Konstruktori.
     *
     */
    public WACalculator() {
        engine = new WAEngine();
        engine.setAppID(IO.readAppID(new File("APPID")));
        engine.addFormat("plaintext");
    }

    /**
     * Laskee syötteenä annetun laskun ja palauttaa tuetut vastaukset.
     *
     * @param input Syöte.
     * @return Ratkaisut.
     */
    public List<String> query(String input) {
        WAQuery query = createQuery(input);
        WAQueryResult queryResult;
        setURLs(query);
        if (input.isEmpty()) {
            error = "Tyhjä syöte.";
            return null;
        }

        try {
            queryResult = engine.performQuery(query);
        } catch (WAException e) {
            error = "Tapahtui verkkovirhe.";
            return null;
        }

        return processQueryResult(queryResult);
    }

    private WAQuery createQuery(String input) {
        WAQuery query = engine.createQuery();
        query.setInput(input);
        return query;
    }

    private void setURLs(WAQuery query) {
        resURL = query.toWebsiteURL();
        resXMLURL = engine.toURL(query);
    }

    private List<String> processQueryResult(WAQueryResult queryResult) {
        List<String> results = extractResults(queryResult);
        if (!checkResults(queryResult, results)) {
            return null;
        }

        return results;
    }

    private boolean checkResults(WAQueryResult queryResult,
            List<String> results) {

        if (queryResult.isError()) {
            error = "AppID virheellinen.";
            return false;
        } else if (!queryResult.isSuccess()
                || queryResult.getWarnings().length != 0) {
            error = "Syöte virheellinen.";
            return false;
        } else if (results.isEmpty()) {
            error = "Ei tuettuja ratkaisuja.";
            return false;
        } else {
            error = null;
            return true;
        }
    }

    private List<String> extractResults(WAQueryResult queryResult) {
        List<String> results = new ArrayList();
        for (WAPod pod : queryResult.getPods()) {
            if (WASupported.isExclusivelySupported(pod)) {
                results.clear();
                results.addAll(extractResults(pod));
                return results;
            } else if (WASupported.isSupported(pod)) {
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

    /**
     * Palauttaa edellisen virheviestin.
     *
     * @return Virheviesti.
     */
    public String getError() {
        return error;
    }

    /**
     * Palauttaa ratkaisujen osoitteen WA:n verkkosivuilla.
     *
     * @return Osoite.
     */
    public String getResURL() {
        return resURL;
    }

    /**
     * Palauttaa API:n tarjoaman XML-tiedoston osoitteen.
     *
     * @return Osoite.
     */
    public String getResXMLURL() {
        return resXMLURL;
    }

    /**
     * Asettaa AppID:n.
     *
     * @param appID AppID.
     */
    public void setAppID(String appID) {
        engine.setAppID(appID);
    }

    /**
     * Palauttaa AppID:n.
     *
     * @return ApID.
     */
    public String getAppID() {
        return engine.getAppID();
    }
}
