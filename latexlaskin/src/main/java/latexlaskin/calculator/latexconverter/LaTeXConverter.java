/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin.calculator.latexconverter;

import java.util.List;

/**
 * Metodeja ratkaisujen kääntämiseen LaTeX-koodiksi.
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
     * Muokkaa jakolaskujen esitystavan muotoon \frac{}{}.
     *
     * @param results Ratkaisut.
     */
    public static void replaceSlashes(List<String> results) {
        for (int i = 0; i < results.size(); i++) {
            results.set(i, replaceSlashes(results.get(i)));
        }
    }

    private static String replaceSlashes(String result) {
        int idx = result.indexOf('/');
        while (idx != -1) {
            result = constructResultFrac(result, idx,
                    calcStartIdx(result, "/", idx),
                    calcEndIdx(result, "/", idx));

            idx = result.indexOf('/');
        }

        return result;
    }

    private static int calcStartIdx(String result, String key, int idx) {
        String revResult = reverseResult(result);
        int revIdx = result.length() - idx - 1;
        return result.length() - calcEndIdx(revResult, key, revIdx) - 1;
    }

    private static String reverseResult(String result) {
        String revResult = new StringBuilder(result).reverse().toString();
        revResult = revResult.replace('(', 'ä');
        revResult = revResult.replace(')', '(');
        revResult = revResult.replace('ä', ')');
        return revResult;
    }

    private static int calcEndIdx(String result, String key, int idx) {
        if (result.charAt(idx + key.length()) == '(') {
            return calcEndIdxPar(result, key, idx);
        } else {
            return calcEndIdxNoPar(result, key, idx);
        }
    }

    private static int calcEndIdxPar(String result, String key, int idx) {
        int endIdx = idx + key.length() - 1;
        int level = 1;
        while (level > 0) {
            endIdx++;
            if (result.charAt(endIdx + 1) == '(') {
                level++;
            } else if (result.charAt(endIdx + 1) == ')') {
                level--;
            }
        }

        return endIdx + 1;
    }

    private static int calcEndIdxNoPar(String result, String key, int idx) {
        int endIdx = idx + key.length() - 1;
        while (endIdx < result.length() - 1
                && result.charAt(endIdx + 1) != ' '
                && result.charAt(endIdx + 1) != ')'
                && result.charAt(endIdx + 1) != '}') {
            endIdx++;
        }

        return endIdx;
    }

    private static String constructResultFrac(String result, int idx,
            int startIdx, int endIdx) {
        return result.substring(0, startIdx)
                + "\\frac{"
                + result.substring(startIdx, idx)
                + "}{"
                + result.substring(idx + 1, endIdx + 1)
                + "}"
                + result.substring(endIdx + 1, result.length());
    }

    /**
     * Korvaa symboleja ja funktioita niiden LaTeX-vastineilla.
     *
     * @param results Ratkaisut.
     */
    public static void replace(List<String> results) {
        for (int i = 0; i < results.size(); i++) {
            results.set(i, replace(results.get(i)));
        }
    }

    private static String replace(String result) {
        for (String key : LaTeXDict.getKeys()) {
            result = replaceKey(result, key);
        }

        return result;
    }

    private static String replaceKey(String result, String key) {
        LaTeXDictItem item = LaTeXDict.getItem(key);
        int idx = result.indexOf(key);
        while (idx != -1) {
            result = constructResult(result, key, item, idx);
            idx = calcNextKeyIdx(result, key, item, idx);
        }

        return result;
    }

    private static String constructResult(String result, String key,
            LaTeXDictItem item, int idx) {
        if (item.hasClosingTag()) {
            return constructResultClosing(result, key, item, idx);
        } else {
            return constructResultNoClosing(result, key, item, idx);
        }
    }

    private static String constructResultClosing(String result, String key,
            LaTeXDictItem item, int idx) {
        int endIdx = calcEndIdx(result, key, idx);
        return result.substring(0, idx)
                + item.getTag()
                + result.substring(idx + key.length(), endIdx + 1)
                + item.getClosingTag()
                + result.substring(endIdx + 1, result.length());
    }

    private static String constructResultNoClosing(String result, String key,
            LaTeXDictItem item, int idx) {
        return result.substring(0, idx)
                + item.getTag()
                + result.substring(idx + key.length(), result.length());
    }

    private static int calcNextKeyIdx(String result, String key,
            LaTeXDictItem item, int idx) {
        return result.indexOf(key, idx + item.getTag().length());
    }
}
