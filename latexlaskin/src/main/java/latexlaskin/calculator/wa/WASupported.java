/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin.calculator.wa;

import com.wolfram.alpha.WAPod;
import java.util.HashSet;
import java.util.Set;

/**
 * Määrittelee mitkä API:lta saadut ratkaisut ovat tuettuja.
 *
 * @author thalvari
 */
public class WASupported {

    private static final Set<String> SUPPORTED_POD_IDS;
    private static final Set<String> SUPPORTED_FIRST_POD_TITLES;

    static {
        SUPPORTED_POD_IDS = new HashSet();
        SUPPORTED_POD_IDS.add("Result");
        SUPPORTED_POD_IDS.add("AlternateForm");
        SUPPORTED_POD_IDS.add("ExpandedForm");
        SUPPORTED_POD_IDS.add("DecimalApproximation");
        SUPPORTED_POD_IDS.add("AlternateFormAssumingAllVariablesAreReal");

        SUPPORTED_FIRST_POD_TITLES = new HashSet();
        SUPPORTED_FIRST_POD_TITLES.add("Derivative");
        SUPPORTED_FIRST_POD_TITLES.add("Indefinite integral");
        SUPPORTED_FIRST_POD_TITLES.add("Definite integral");
    }

    /**
     * Tyhjä konstruktori.
     */
    public WASupported() {
    }

    /**
     * Kertoo ovatko podin ratkaisut tuettuja.
     *
     * @param pod Podi.
     * @return Totuusarvo.
     */
    public static boolean isSupported(WAPod pod) {
        return isSupportedById(pod) || isSupportedByTitle(pod);
    }

    private static boolean isSupportedById(WAPod pod) {
        return SUPPORTED_POD_IDS.contains(pod.getID());
    }

    private static boolean isSupportedByTitle(WAPod pod) {
        return SUPPORTED_FIRST_POD_TITLES.contains(pod.getTitle())
                && pod.getPosition() == 100;
    }
}
