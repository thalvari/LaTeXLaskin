/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin.calculator.latexconverter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * LaTeX-sanakirja.
 *
 * @author thalvari
 */
public class LaTeXDict {

    /**
     * Sanakirjan avaimet ja niitä vastaavat arvot.
     */
    private static final Map<String, LaTeXDictItem> DICTIONARY;

    static {
        DICTIONARY = new HashMap();
        DICTIONARY.put("≥", new LaTeXDictItem(" \\geq "));
        DICTIONARY.put("≤", new LaTeXDictItem(" \\leq "));
        DICTIONARY.put("", new LaTeXDictItem("\\mathrm{i}"));
        DICTIONARY.put("", new LaTeXDictItem("\\mathrm{e}"));
        DICTIONARY.put("π", new LaTeXDictItem("\\pi"));
        DICTIONARY.put("μ", new LaTeXDictItem("\\mu"));
        DICTIONARY.put("σ", new LaTeXDictItem("\\sigma"));
        DICTIONARY.put("α", new LaTeXDictItem("\\alpha"));
        DICTIONARY.put("β", new LaTeXDictItem("\\beta"));
        DICTIONARY.put("", new LaTeXDictItem("\\gamma"));
        DICTIONARY.put("θ", new LaTeXDictItem("\\theta"));
        DICTIONARY.put("λ", new LaTeXDictItem("\\lambda"));
        DICTIONARY.put("ω", new LaTeXDictItem("\\omega"));
        DICTIONARY.put("log(", new LaTeXDictItem("\\log"));
        DICTIONARY.put("cos(", new LaTeXDictItem("\\cos"));
        DICTIONARY.put("cos^", new LaTeXDictItem("\\cos"));
        DICTIONARY.put("cosh(", new LaTeXDictItem("\\cosh"));
        DICTIONARY.put("cosh^", new LaTeXDictItem("\\cosh"));
        DICTIONARY.put("sin(", new LaTeXDictItem("\\sin"));
        DICTIONARY.put("sin^", new LaTeXDictItem("\\sin"));
        DICTIONARY.put("sinh(", new LaTeXDictItem("\\sinh"));
        DICTIONARY.put("sinh^", new LaTeXDictItem("\\sinh"));
        DICTIONARY.put("tan(", new LaTeXDictItem("\\tan"));
        DICTIONARY.put("tan^", new LaTeXDictItem("\\tan"));
        DICTIONARY.put("tanh(", new LaTeXDictItem("\\tanh"));
        DICTIONARY.put("tanh^", new LaTeXDictItem("\\tanh"));
        DICTIONARY.put("cot(", new LaTeXDictItem("\\cot"));
        DICTIONARY.put("cot^", new LaTeXDictItem("\\cot"));
        DICTIONARY.put("csc(", new LaTeXDictItem("\\csc"));
        DICTIONARY.put("csc^", new LaTeXDictItem("\\csc"));
        DICTIONARY.put("sec(", new LaTeXDictItem("\\sec"));
        DICTIONARY.put("sec^", new LaTeXDictItem("\\sec"));
        DICTIONARY.put("sqrt(", new LaTeXDictItem("\\sqrt{", "}"));
        DICTIONARY.put("^", new LaTeXDictItem("^{", "}"));
        DICTIONARY.put("_", new LaTeXDictItem("_{", "}"));
        DICTIONARY.put("abs(", new LaTeXDictItem("\\vert ", " \\vert"));
    }

    /**
     * Tyhjä konstruktori.
     */
    public LaTeXDict() {
    }

    /**
     * Palauttaa kaikki sanakirjan avaimet.
     *
     * @return Avaimet.
     */
    public static Set<String> getKeys() {
        return DICTIONARY.keySet();
    }

    /**
     * Palauttaa avaimen todellisen pituuden.
     *
     * @param key Avain.
     * @return Pituus.
     */
    public static int getRealKeyLength(String key) {
        char lastChar = key.charAt(key.length() - 1);
        if (!key.equals("^") && (lastChar == '(' || lastChar == '^')) {
            return key.length() - 1;
        } else {
            return key.length();
        }
    }

    /**
     * Palauttaa avainta vastaavan olion.
     *
     * @param key Avain.
     * @return Olio.
     */
    public static LaTeXDictItem getItem(String key) {
        return DICTIONARY.get(key);
    }
}
