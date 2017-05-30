/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin.latexconverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Muuttaa WACalculatorin vastaukset LaTeX-koodiksi.
 *
 * @author thalvari
 */
public class LaTeXConverter {

    public static List<String> convert(List<String> results) {
        List<String> convertedResults = new ArrayList();
        for (String result : results) {
            convertedResults.add(convert(result));
        }
        return convertedResults;
    }

    public static String convert(String result) {
        // TODO
        return result;
    }
}
