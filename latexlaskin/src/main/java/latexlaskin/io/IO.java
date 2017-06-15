/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin.io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Metodeja APPID-tiedoston käsittelyyn.
 *
 * @author thalvari
 */
public class IO {

    /**
     * Tyhjä konstruktori.
     */
    public IO() {
    }

    /**
     * Lukee AppID:n tiedostosta.
     *
     * @param file Tiedosto.
     * @return AppID.
     */
    public static String readAppID(File file) {
        List<String> lines = new ArrayList();
        try {
            Files.lines(Paths.get(file.getName())).forEach(line
                    -> lines.add(line));
        } catch (IOException e) {
        }

        if (lines.isEmpty()) {
            return null;
        } else {
            return lines.get(0);
        }
    }

    /**
     * Kirjoittaa uuden AppID:n tiedostoon.
     *
     * @param file Tiedosto.
     * @param appID AppID.
     */
    public static void writeAppID(File file, String appID) {
        List<String> lines = new ArrayList();
        lines.add(appID);
        try {
            Files.write(Paths.get(file.getName()), lines,
                    StandardCharsets.UTF_8);
        } catch (IOException e) {
        }
    }
}
