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
            result = constructResultFrac(result,
                    idx,
                    calcStartIdx(result, 1, idx),
                    calcEndIdx(result, 1, idx));

            idx = result.indexOf('/');
        }

        return result;
    }

    private static int calcStartIdx(String result, int keyLen, int idx) {
        int revEndIdx = calcEndIdx(reverseResult(result),
                keyLen,
                result.length() - idx - 1);

        return result.length() - revEndIdx - 1;
    }

    private static String reverseResult(String result) {
        String revResult = "";
        for (int i = result.length() - 1; i >= 0; i--) {
            if (result.charAt(i) == '(') {
                revResult += ')';
            } else if (result.charAt(i) == ')') {
                revResult += '(';
            } else if (result.charAt(i) == '{') {
                revResult += '}';
            } else if (result.charAt(i) == '}') {
                revResult += '{';
            } else {
                revResult += result.charAt(i);
            }
        }

        return revResult;
    }

    private static int calcEndIdxPar(String result, int keyLen, int idx) {
        int endIdx = idx + keyLen;
        int level = 1;
        while (level > 0) {
            endIdx++;
            if (result.charAt(endIdx) == '(') {
                level++;
            } else if (result.charAt(endIdx) == ')') {
                level--;
            }
        }

        return endIdx;
    }

    private static int calcEndIdx(String result, int keyLen, int idx) {
        int endIdx = idx + keyLen - 1;
        boolean isExp = false;
        while (endIdx < result.length() - 1) {
            char curChar = result.charAt(endIdx);
            char nextChar = result.charAt(endIdx + 1);
            if (curChar == '^') {
                isExp = true;
            }

            if (isBreakChar(nextChar)
                    || (isExp && curChar != '^' && nextChar == '(')) {

                break;
            } else if (nextChar == '(') {
                endIdx = calcEndIdxPar(result, 1, endIdx);
                continue;
            }

            endIdx++;
        }

        return endIdx;
    }

    private static boolean isBreakChar(char c) {
        return c == ' ' || c == '-' || c == ')' || c == '}';
    }

    private static String constructResultFrac(String result, int idx,
            int startIdx, int endIdx) {

        return result.substring(0, startIdx)
                + "\\frac{"
                + result.substring(startIdx, idx)
                + "}{"
                + result.substring(idx + 1, endIdx + 1)
                + "}"
                + result.substring(endIdx + 1);
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
            int keyLen = LaTeXDict.getRealKeyLength(key);
            result = constructResult(result, keyLen, item, idx);
            idx = calcNextKeyIdx(result, key, item, idx);
        }

        return result;
    }

    private static String constructResult(String result, int keyLen,
            LaTeXDictItem item, int idx) {

        if (item.hasClosingTag()) {
            return constructResultClosing(result, keyLen, item, idx);
        } else {
            return constructResultNoClosing(result, keyLen, item, idx);
        }
    }

    private static String constructResultClosing(String result, int keyLen,
            LaTeXDictItem item, int idx) {

        int endIdx = calcEndIdx(result, keyLen, idx);
        return result.substring(0, idx)
                + item.getTag()
                + result.substring(idx + keyLen, endIdx + 1)
                + item.getClosingTag()
                + result.substring(endIdx + 1);
    }

    private static String constructResultNoClosing(String result, int keyLen,
            LaTeXDictItem item, int idx) {

        return result.substring(0, idx)
                + item.getTag()
                + result.substring(idx + keyLen);
    }

    private static int calcNextKeyIdx(String result, String key,
            LaTeXDictItem item, int idx) {

        return result.indexOf(key, idx + item.getTag().length());
    }

    /**
     * Poistaa ylimääräiset sulkeet.
     *
     * @param results Ratkaisut.
     */
    public static void removeExtraPars(List<String> results) {
        for (int i = 0; i < results.size(); i++) {
            results.set(i, removeExtraPars(results.get(i)));
        }
    }

    private static String removeExtraPars(String result) {
        int idx = result.indexOf("{(");
        while (idx != -1) {
            int endIdx = calcEndIdxPar(result, 1, idx);
            if (result.charAt(endIdx + 1) == '}') {
                result = constructResultNoExtraPars(result, idx, endIdx);
            }

            idx = result.indexOf("{(", idx + 1);
        }

        return result;
    }

    private static String constructResultNoExtraPars(String result, int idx,
            int endIdx) {

        return result.substring(0, idx + 1)
                + result.substring(idx + 2, endIdx)
                + result.substring(endIdx + 1);
    }
}
