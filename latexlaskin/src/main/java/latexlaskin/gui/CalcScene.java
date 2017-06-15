/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin.gui;

import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import latexlaskin.calculator.Calculator;

/**
 *
 * @author thalvari
 */
public class CalcScene extends MyScene {

    private TextField inputTextField;
    private Button calcButton;
    private Button clearButton;
    private Button appIDButton;
    private Label errorLabel;
    private VBox vBox;

    public CalcScene(Stage stage, Calculator calc) {
        super(stage, calc);
    }

    @Override
    protected void construct() {
        inputTextField = new TextField();
        inputTextField.setPromptText("Syöte");
        calcButton = new Button("Laske");
        clearButton = new Button("Tyhjennä");
        appIDButton = new Button("Vaihda AppID");
        errorLabel = new Label();
        vBox = new VBox();
        vBox.setSpacing(20);
        gridPane.add(inputTextField, 0, 0);
        gridPane.add(calcButton, 1, 0);
        gridPane.add(clearButton, 2, 0);
        gridPane.add(appIDButton, 3, 0);
        gridPane.add(errorLabel, 0, 1, 4, 1);
        gridPane.add(vBox, 0, 2, 4, 2);
        calcButton.setOnAction(this::calcButtonHandle);
        clearButton.setOnAction(this::clearButtonHandle);
        appIDButton.setOnAction(this::appIDButtonHandle);
    }

    private void calcButtonHandle(ActionEvent event) {
        vBox.getChildren().clear();
        List<String> results = calc.query(inputTextField.getText());
        if (results == null) {
            errorLabel.setText(calc.getError());
            stage.sizeToScene();
            return;
        }

        errorLabel.setText("Ratkaisut:");
        for (String result : results) {
            processResult(result);
        }

        stage.sizeToScene();
    }

    private void clearButtonHandle(ActionEvent event) {
        inputTextField.clear();
    }

    private void appIDButtonHandle(ActionEvent event) {
        stage.setScene(otherScene.getScene());
    }

    private void processResult(String result) {
        HBox hBox = new HBox();
        hBox.setSpacing(20);
        TextArea resultArea = new TextArea(result);
        resultArea.setEditable(false);
        resultArea.setWrapText(true);
        resultArea.setPrefRowCount(3);
        Button copyButton = new Button("Kopioi");
        copyButton.setOnAction((event) -> {
            resultArea.selectAll();
            resultArea.copy();
        });
        hBox.getChildren().add(resultArea);
        hBox.getChildren().add(copyButton);
        vBox.getChildren().add(hBox);
    }
}
