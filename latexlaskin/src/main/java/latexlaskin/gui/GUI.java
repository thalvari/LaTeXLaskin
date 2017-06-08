/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin.gui;

import javafx.stage.Stage;
import latexlaskin.calculator.Calculator;

/**
 *
 * @author thalvari
 */
public class GUI {

    private final Stage stage;
    private LoginScene loginScene;
    private CalcScene calcScene;

    public GUI(Stage stage, Calculator calc) {
        this.stage = stage;
        this.stage.setTitle("LaTeXLaskin");
        calcScene = new CalcScene(stage, calc);
        loginScene = new LoginScene(stage, calc);
        calcScene.setOtherScene(loginScene);
        loginScene.setOtherScene(calcScene);
    }

    public void start() {
        stage.setScene(loginScene.getScene());
        stage.show();
    }
}
