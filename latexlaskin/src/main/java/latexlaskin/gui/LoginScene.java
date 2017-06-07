/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import latexlaskin.calculator.Calculator;

/**
 *
 * @author thalvari
 */
public class LoginScene extends MyScene {

    public LoginScene(Stage stage, Calculator calc) {
        super(stage, calc);
    }

    @Override
    protected void construct() {
        Label label = new Label("AppID: ");
        TextField textField = new TextField();
        Button button = new Button("Kirjaudu");
        Label errorLabel = new Label();
        gridPane.add(label, 0, 0);
        gridPane.add(textField, 1, 0);
        gridPane.add(button, 1, 1);
        gridPane.add(errorLabel, 1, 2);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        button.setOnAction((event) -> {
            calc.setAppID(textField.getText());
            calc.query("1 + 1");
            if (calc.getError() == null) {
                stage.setScene(otherScene.getScene());
            } else {
                errorLabel.setText(calc.getError());
            }
        });
    }
}
