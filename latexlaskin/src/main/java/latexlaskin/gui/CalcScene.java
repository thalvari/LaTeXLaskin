/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latexlaskin.gui;

import java.util.List;
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
public class CalcScene extends MyScene {

    public CalcScene(Stage stage, Calculator calc) {
        super(stage, calc);
    }

    @Override
    protected void construct() {
        Label inputLabel = new Label("Syöte: ");
        TextField inputField = new TextField();
        Label resultLabel = new Label();
        Button calcButton = new Button("Laske");
        gridPane.add(inputLabel, 0, 1);
        gridPane.add(inputField, 1, 1);
        gridPane.add(resultLabel, 1, 3);
        gridPane.add(calcButton, 1, 2);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(15, 15, 15, 15));
        calcButton.setOnAction((event) -> {
            List<String> results = calc.query(inputField.getText());
            if (results == null) {
                resultLabel.setText(calc.getError());
                stage.sizeToScene();
                return;
            }

            resultLabel.setText("Ratkaisut:");
            int i = 4;
            for (String result : results) {
                TextField textField = new TextField(result);
                textField.setEditable(false);
                gridPane.add(textField, 1, i);
                i++;
            }
            stage.sizeToScene();
        });
    }
}
