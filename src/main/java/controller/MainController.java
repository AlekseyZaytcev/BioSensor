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
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainController {

    private BioSensorAPI sensorAPI = new BioSensorAPI();
    private DaoManager manager = new DaoManager();
    private MyTime myTime = new MyTime();

    private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
    private ScheduledFuture<?> task;
    private Boolean statusFlag = true;

    private XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();

    @FXML
    NumberAxis yAxis;
    @FXML
    CategoryAxis xAxis;
    @FXML
    private LineChart<String, Number> lineChart;
    ;
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;

    @FXML
    public void initialize() {
        startButton.setId("round-red");
        stopButton.setId("green");
        stopButton.setVisible(false);
        series.setName("Bioactivity");
        lineChart.setCreateSymbols(false);

        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(300);
        yAxis.setUpperBound(1000);
        yAxis.setTickUnit(100);
        
        createChart();
    }

    private Parent createChart() {
        List<SensativityTableEntity> entity = manager.getBioEntity(myTime.weeks2AgoTime().toString(), myTime.currentTime().toString());
        for (int i = 0; i < entity.size(); i++) {
            Timestamp time = entity.get(i).getTime();
            Integer value = entity.get(i).getValue();
            series.getData().add(new XYChart.Data<String, Number>(time.toString(), value));
        }
        lineChart.getData().add(series);

        new Thread(() -> {
            try {
                while (true) {
                    List<SensativityTableEntity> entity2 = manager.getBioEntity(myTime.secondsAgoTime(5).toString(), myTime.currentTime().toString());
                    if (statusFlag == true) {
                        for (int i = 0; i < entity2.size(); i++) {
                            Timestamp time = entity2.get(i).getTime();
                            Integer value = entity2.get(i).getValue();
                            Platform.runLater(() -> series.getData().add(new XYChart.Data<>(time.toString(), value)));
                            Thread.sleep(1000);
                        }
                    }
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