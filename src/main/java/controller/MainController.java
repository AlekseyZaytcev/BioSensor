package controller;

import Time.MyTime;
import dao.DaoManager;
import entitys.SensativityTableEntity;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import jssc.SerialPortException;
import sensor_connect.BioSensorAPI;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.*;

public class MainController {
    BioSensorAPI sensorAPI = new BioSensorAPI();
    DaoManager manager = new DaoManager();
    MyTime myTime = new MyTime();
     ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
     ScheduledFuture<?> task;
     private Boolean statusFlag = true;

    @FXML
    LineChart<String, Integer> lineChart;
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;

    @FXML
    public void initialize() {
        XYChart.Series<String, Integer> series = new XYChart.Series<String, Integer>();
        List<SensativityTableEntity> entity = manager.getBioEntity(myTime.weeks2AgoTime().toString(), myTime.currentTime().toString());
        for (int i = 0; i < entity.size(); i++) {
            Timestamp time = entity.get(i).getTime();
            Integer value = entity.get(i).getValue();
            series.getData().add(new XYChart.Data<String, Integer>(time.toString(), value));
        }
        series.setName("Bioactivity");
        lineChart.getData().add(series);
        startButton.setId("round-red");
        stopButton.setId("green");
        stopButton.setVisible(false);
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
                if (statusFlag == false){
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
    public void stopBioactivity(){
        startButton.setVisible(true);
        stopButton.setVisible(false);
        statusFlag = false;
    }
}