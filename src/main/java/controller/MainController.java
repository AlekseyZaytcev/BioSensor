package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;

public class MainController {
    private Boolean startFlag = false;
    @FXML
    LineChart<String, Number> lineChart;
    @FXML
    private Button startButton;

    @FXML
    public void initialize() {
        lineChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
        series.getData().add(new XYChart.Data<String, Number>("Jan", 200));
        series.getData().add(new XYChart.Data<String, Number>("Feb", 500));
        series.getData().add(new XYChart.Data<String, Number>("Mar", 300));
        series.getData().add(new XYChart.Data<String, Number>("Apr", 600));
        series.setName("Bioactivity");
        lineChart.getData().add(series);
    }

    @FXML
    public void startBioactivity() {
        if (startFlag == false) {
            startFlag = true;
            startButton.setText("Started");
            startButton.setId("green");
        } else {
            startFlag = false;
            startButton.setText("Stopped");
            startButton.setId("red");
            startButton.setId("round-red");
        }
    }
}