/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin.latexconverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Kääntää prosessoituja ratkaisuja LaTeX-koodiksi.
 *
 * @author thalvari
 */
public class LaTeXConverter {

    /**
     * Tyhjä konstruktori.
     */
    public LaTeXConverter() {
    }

    /**
     * Muuttaa jakolaskujen esitystavan muotoon \frac{}{}.
     *
     * @param results Prosessoidut ratkaisut.
     * @return Ratkaisut LaTeX-koodina.
     */
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
            result = constructResult(idx, calcNumStart(idx, result),
                    calcDenomEnd(idx, result), result);
            idx = result.indexOf('/');
        }
        return result;
    }

    private static int calcNumStart(int idx, String result) {
        if (result.charAt(idx - 1) == ')') {
            return calcNumStartInPar(idx, result);
        } else {
            return calcNumStartNotInPar(idx, result);
        }
    }

    private static int calcNumStartInPar(int idx, String result) {
        int numStart = idx;
        int level = 1;
        while (level > 0) {
            numStart--;
            if (result.charAt(numStart - 1) == ')') {
                level++;
            } else if (result.charAt(numStart - 1) == '(') {
                level--;
            }
        }
        return numStart - 1;
    }

    private static int calcNumStartNotInPar(int idx, String result) {
        int numStart = idx;
        while (numStart > 0 && result.charAt(numStart - 1) != ' '
                && result.charAt(numStart - 1) != '(') {
            numStart--;
        }
        return numStart;
    }

    private static int calcDenomEnd(int idx, String result) {
        if (result.charAt(idx + 1) == '(') {
            return calcDenomEndInPar(idx, result);
        } else {
            return calcDenomEndNotInPar(idx, result);
        }
    }

    private static int calcDenomEndInPar(int idx, String result) {
        int denomEnd = idx;
        int level = 1;
        while (level > 0) {
            denomEnd++;
            if (result.charAt(denomEnd + 1) == '(') {
                level++;
            } else if (result.charAt(denomEnd + 1) == ')') {
                level--;
            }
        }
        return denomEnd + 1;
    }

    private static int calcDenomEndNotInPar(int idx, String result) {
        int denomEnd = idx;
        while (denomEnd < result.length() - 1
                && result.charAt(denomEnd + 1) != ' '
                && result.charAt(denomEnd + 1) != ')') {
            denomEnd++;
        }
        return denomEnd;
    }

    private static String constructResult(int idx, int numStart, int denomEnd,
            String result) {
        return result.substring(0, numStart) + "\\frac{"
                + result.substring(numStart, idx) + "}{"
                + result.substring(idx + 1, denomEnd + 1) + "}"
                + result.substring(denomEnd + 1, result.length());
    }
}
