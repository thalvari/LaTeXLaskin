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
        if (results == null) {
            return null;
        }
        List<String> convertedResults = new ArrayList();
        for (String result : results) {
            convertedResults.add(convert(result));
        }
        return convertedResults;
    }

    private static String convert(String result) {
        result = replaceSlashes(result);
        return result;
    }

    private static String replaceSlashes(String result) {
        int idx = result.indexOf('/');
        while (idx != -1) {
            int idxStart = calcIdxStart(idx, result);
            int idxEnd = calcIdxEnd(idx, result);
            result = constructResult(idxStart, idx, idxEnd, result);
            idx = result.indexOf('/');
        }
        return result;
    }

    private static int calcIdxStart(int idx, String result) {
        int idxStart = idx;
        while (idxStart > 0 && result.charAt(idxStart - 1) != ' '
                && result.charAt(idxStart - 1) != '(') {
            idxStart--;
        }
        return idxStart;
    }

    private static int calcIdxEnd(int idx, String result) {
        int idxEnd = idx;
        while (idxEnd < result.length() - 1
                && result.charAt(idxEnd + 1) != ' '
                && result.charAt(idxEnd + 1) != ')') {
            idxEnd++;
        }
        return idxEnd;
    }

    private static String constructResult(int idxStart, int idx, int idxEnd,
            String result) {
        return result.substring(0, idxStart) + "\\frac{"
                + result.substring(idxStart, idx) + "}{"
                + result.substring(idx + 1, idxEnd + 1) + "}"
                + result.substring(idxEnd + 1, result.length());
    }
}
