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
 *
 * @author thalvari
 */
public class Calculator {

    private static final boolean DEBUG = true;

    private WACalculator wACalc;
    private String error;
    private boolean debug;

    public Calculator(String appid) {
        wACalc = new WACalculator(appid, DEBUG);
        debug = DEBUG;
    }

    public List<String> query(String input) {
        List<String> results = wACalc.query(input);
        if (results == null) {
            error = wACalc.getError();
            return null;
        }
        results = process(results);
        if (debug) {
            System.out.println("Prosessoidut ratkaisut:");
            printResults(results);
        }
        results = convert(results);
        return results;
    }

    private List<String> process(List<String> results) {
        results = WAResultProcesser.removeBadResults(results);
        WAResultProcesser.trimResults(results);
        return results;
    }

    private List<String> convert(List<String> results) {
        results = LaTeXConverter.replaceSlashes(results);
        LaTeXConverter.replaceSymbols(results);
        return results;
    }

    private void printResults(List<String> results) {
        for (String result : results) {
            System.out.println(result);
        }
    }

    public String getError() {
        return error;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
        wACalc.setDebug(debug);
    }

    public WACalculator getWACalc() {
        return wACalc;
    }
}
