/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin.gui;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import latexlaskin.calculator.Calculator;

/**
 *
 * @author thalvari
 */
public abstract class MyScene {

    protected final Stage stage;
    protected final Calculator calc;
    protected final GridPane gridPane;
    protected MyScene otherScene;

    public MyScene(Stage stage, Calculator calc) {
        this.stage = stage;
        this.calc = calc;
        gridPane = new GridPane();
        construct();
    }

    public void setOtherScene(MyScene otherScene) {
        this.otherScene = otherScene;
    }

    public Scene getScene() {
        return new Scene(gridPane);
    }

    protected abstract void construct();
}
