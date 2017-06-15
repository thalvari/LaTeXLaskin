/*
 * To replace this license header, choose License Headers in Project Properties.
 * To replace this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin.gui;

import java.io.File;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import latexlaskin.calculator.Calculator;
import latexlaskin.io.IO;

/**
 *
 * @author thalvari
 */
public class AppIDScene extends MyScene {

    private PasswordField appIDPasswordField;
    private Button replaceButton;
    private Button clearButton;
    private Button returnButton;
    private Label errorLabel;

    public AppIDScene(Stage stage, Calculator calc) {
        super(stage, calc);
    }

    @Override
    protected void construct() {
        appIDPasswordField = new PasswordField();
        appIDPasswordField.setPromptText("AppID");
        replaceButton = new Button("Vaihda");
        replaceButton.setOnAction(this::replaceButtonHandle);
        clearButton = new Button("Tyhjennä");
        clearButton.setOnAction(this::appIDButtonHandle);
        returnButton = new Button("Palaa");
        returnButton.setOnAction(this::returnButtonHandle);
        errorLabel = new Label();
        gridPane.add(appIDPasswordField, 0, 0);
        gridPane.add(replaceButton, 1, 0);
        gridPane.add(clearButton, 2, 0);
        gridPane.add(returnButton, 3, 0);
        gridPane.add(errorLabel, 0, 1, 3, 1);
    }

    private void appIDButtonHandle(ActionEvent event) {
        appIDPasswordField.clear();
    }

    private void replaceButtonHandle(ActionEvent event) {
        String oldAppID = calc.getAppID();
        String newAppID = appIDPasswordField.getText();
        calc.setAppID(newAppID);
        calc.query("1 + 1");
        if (calc.getError() == null) {
            IO.writeAppID(new File("APPID"), newAppID);
            appIDPasswordField.clear();
            errorLabel.setText("");
            stage.setScene(otherScene.getScene());
        } else {
            calc.setAppID(oldAppID);
            errorLabel.setText(calc.getError());
        }
        stage.sizeToScene();
    }

    private void returnButtonHandle(ActionEvent event) {
        appIDPasswordField.clear();
        errorLabel.setText("");
        stage.setScene(otherScene.getScene());
    }
}
