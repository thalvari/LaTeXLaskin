/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin.calculator.latexconverter;

/**
 * LaTeX-sanakirjan arvona oleva olio.
 *
 * @author thalvari
 */
public class LaTeXDictItem {

    /**
     * LaTeX-vastineen alkuosa.
     */
    private final String tag;

    /**
     * LaTeX-vastineen loppuosa.
     */
    private final String closingTag;

    /**
     * Konstruktori.
     *
     * @param tag LaTeX-vastine.
     * @param closingTag Loppuosa.
     */
    public LaTeXDictItem(String tag, String closingTag) {
        this.tag = tag;
        this.closingTag = closingTag;
    }

    /**
     * Toinen konstruktori.
     *
     * @param tag LaTeX-vastine.
     */
    public LaTeXDictItem(String tag) {
        this(tag, null);
    }

    /**
     * Palauttaa LaTeX-vastineen.
     *
     * @return LaTeX-vastine.
     */
    public String getTag() {
        return tag;
    }

    /**
     * Palauttaa loppuosan.
     *
     * @return Loppuosa.
     */
    public String getClosingTag() {
        return closingTag;
    }

    /**
     * Kertoo onko LaTeX-vastineella loppuosa.
     *
     * @return Totuusarvo.
     */
    public boolean hasClosingTag() {
        return closingTag != null;
    }
}
