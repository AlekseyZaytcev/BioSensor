package controller;

import Time.MyTime;
import dao.DaoManager;
import entitys.SensativityTableEntity;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import jssc.SerialPortException;
import sensor_connect.BioSensorAPI;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.*;

public class MainController {

    private BioSensorAPI sensorAPI = new BioSensorAPI();
    private DaoManager manager = new DaoManager();
    private MyTime myTime = new MyTime();

    private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
    private ScheduledFuture<?> task;
    private Boolean statusFlag = true;

    private CategoryAxis xAxis = new CategoryAxis();
    private CategoryAxis yAxis = new CategoryAxis();


    @FXML
    private LineChart<String, String> lineChart = new LineChart<String, String>(xAxis, yAxis);;
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;

    @FXML
    public void initialize() {
        startButton.setId("round-red");
        stopButton.setId("green");
        stopButton.setVisible(false);
        createChart();
    }

    private Parent createChart() {
        XYChart.Series<String, String> series = new XYChart.Series<String, String>();
        series.setName("Bioactivity");
        List<SensativityTableEntity> entity = manager.getBioEntity(myTime.weeks2AgoTime().toString(), myTime.currentTime().toString());
        for (int i = 0; i < entity.size(); i++) {
            Timestamp time = entity.get(i).getTime();
            Integer value = entity.get(i).getValue();
            series.getData().add(new XYChart.Data<String, String>(time.toString(), value.toString()));
        }
        lineChart.getData().add(series);

        new Thread(() -> {
            try {
                Thread.sleep(5000);

                for (int i = 0; i < 15; i++) {
                    int finalI = i;
//                    Platform.runLater(() -> series.getData().add(new XYChart.Data<>(1 + finalI, 1 + finalI)));
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        return lineChart;
    }

    @FXML
    public void startBioactivity() {
        Runnable dbPush = new Runnable() {
            @Override
            public void run() {
                try {
                    sensorAPI.initializePort();
                    Integer value = sensorAPI.levelBioActivity();
                    sensorAPI.closePort();
                    manager.setValue(value);
                } catch (SerialPortException e) {
                    e.printStackTrace();
                }
                if (statusFlag == false) {
                    task.cancel(false);
                }
            }
        };
        task = executor.scheduleWithFixedDelay(dbPush, 0, 1, TimeUnit.SECONDS);
        startButton.setVisible(false);
        stopButton.setVisible(true);
        statusFlag = true;

    }

    @FXML
    public void stopBioactivity() {
        startButton.setVisible(true);
        stopButton.setVisible(false);
        statusFlag = false;
    }
}