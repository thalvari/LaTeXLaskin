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

    private WACalculator calc;

    public Calculator(String appid) {
        calc = new WACalculator(appid);
    }

    public List<String> query(String input) {
        List<String> results = calc.query(input);
        results = process(results);
        return results;
    }

    private List<String> process(List<String> results) {
        results = WAResultProcesser.removeBadResults(results);
        WAResultProcesser.trimResults(results);
        return results;
    }

    private List<String> convert(List<String> results) {
//        results = LaTeXConverter.replaceSlashes(results);
        return results;
    }
}
