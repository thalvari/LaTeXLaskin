/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin.gui;

import java.util.List;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
        errorLabel = new Label();
        vBox = new VBox();
        vBox.setSpacing(20);
        vBox.setPadding(new Insets(20, 20, 20, 20));
        gridPane.add(inputTextField, 0, 0);
        gridPane.add(calcButton, 1, 0);
        gridPane.add(clearButton, 2, 0);
        gridPane.add(errorLabel, 0, 1, 3, 1);
        gridPane.add(vBox, 0, 2, 3, 1);
        calcButton.setOnAction(this::calcButtonHandle);
        clearButton.setOnAction((event) -> {
            inputTextField.clear();
        });
    }

    private void calcButtonHandle(Event event) {
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

    private void processResult(String result) {
        TextField resultField = new TextField(result);
        resultField.setEditable(false);
        vBox.getChildren().add(resultField);
        Button copyButton = new Button("Kopioi");
        vBox.getChildren().add(copyButton);
        copyButton.setOnAction((event) -> {
            resultField.selectAll();
            resultField.copy();
        });
    }
}
