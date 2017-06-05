/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin.calculator.latexconverter;

/**
 *
 * @author thalvari
 */
public class LaTeXDictItem {

    private final String tag;
    private final String closingTag;

    public LaTeXDictItem(String tag, String closingTag) {
        this.tag = tag;
        this.closingTag = closingTag;
    }

    public LaTeXDictItem(String tag) {
        this(tag, null);
    }

    public String getTag() {
        return tag;
    }

    public String getClosingTag() {
        return closingTag;
    }

    public boolean hasClosingTag() {
        return closingTag != null;
    }
}
