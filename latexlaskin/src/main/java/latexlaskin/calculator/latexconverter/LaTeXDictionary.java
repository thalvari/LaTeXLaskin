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
 *
 * @author thalvari
 */
public class LaTeXDictionary {

    private static final char IMAGINARY = 63310;
    private static final char NEPER = 63309;
    private static final Map<String, String> DICTIONARY;

    static {
        DICTIONARY = new HashMap();
        DICTIONARY.put("" + IMAGINARY, "\\mathrm{i}");
        DICTIONARY.put("" + NEPER, "\\mathrm{e}");
        DICTIONARY.put("π", "\\pi");
        DICTIONARY.put("cos", "\\cos");
        DICTIONARY.put("sin", "\\sin");
        DICTIONARY.put("tan", "\\tan");
    }

    public LaTeXDictionary() {
    }

    public static Set<String> getKeys() {
        return DICTIONARY.keySet();
    }

    public static String getValue(String key) {
        return DICTIONARY.get(key);
    }
}
