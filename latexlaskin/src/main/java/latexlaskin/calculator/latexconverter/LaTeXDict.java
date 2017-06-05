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
public class LaTeXDict {

    private static final char IMAGINARY = 63310;
    private static final char NEPER = 63309;
    private static final Map<String, LaTeXDictItem> DICTIONARY;

    static {
        DICTIONARY = new HashMap();
        DICTIONARY.put("" + IMAGINARY, new LaTeXDictItem("\\mathrm{i}"));
        DICTIONARY.put("" + NEPER, new LaTeXDictItem("\\mathrm{e}"));
        DICTIONARY.put("π", new LaTeXDictItem("\\pi"));
        DICTIONARY.put("cos", new LaTeXDictItem("\\cos"));
//        DICTIONARY.put("cosh", new LaTeXDictItem("\\cosh"));
        DICTIONARY.put("sin", new LaTeXDictItem("\\sin"));
//        DICTIONARY.put("sinh", new LaTeXDictItem("\\sinh"));
        DICTIONARY.put("tan", new LaTeXDictItem("\\tan"));
//        DICTIONARY.put("tanh", new LaTeXDictItem("\\tanh"));
        DICTIONARY.put("sqrt", new LaTeXDictItem("\\sqrt{", "}"));
        DICTIONARY.put("^", new LaTeXDictItem("^{", "}"));
        DICTIONARY.put("abs", new LaTeXDictItem("\\vert ", " \\vert"));
    }

    public LaTeXDict() {
    }

    public static Set<String> getKeys() {
        return DICTIONARY.keySet();
    }

    public static LaTeXDictItem getItem(String key) {
        return DICTIONARY.get(key);
    }
}
