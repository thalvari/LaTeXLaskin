/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin.gui;

import javafx.event.Event;
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

    private TextField appIDTextField;
    private Button loginButton;
    private Button clearButton;
    private Label errorLabel;

    public LoginScene(Stage stage, Calculator calc) {
        super(stage, calc);
    }

    @Override
    protected void construct() {
        appIDTextField = new TextField();
        appIDTextField.setPromptText("AppID");
        loginButton = new Button("Kirjaudu");
        clearButton = new Button("Tyhjennä");
        errorLabel = new Label();
        gridPane.add(appIDTextField, 0, 0);
        gridPane.add(loginButton, 1, 0);
        gridPane.add(clearButton, 2, 0);
        gridPane.add(errorLabel, 0, 1, 3, 1);
        loginButton.setOnAction(this::loginButtonHandle);
        clearButton.setOnAction((event) -> {
            appIDTextField.clear();
        });
    }

    private void loginButtonHandle(Event event) {
        calc.setAppID(appIDTextField.getText());
        calc.query("1 + 1");
        if (calc.getError() == null) {
            stage.setScene(otherScene.getScene());
        } else {
            errorLabel.setText(calc.getError());
        }
        stage.sizeToScene();
    }
}
