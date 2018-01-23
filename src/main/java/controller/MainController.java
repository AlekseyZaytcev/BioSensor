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
import javafx.scene.control.Label;
import jssc.SerialPortException;
import sensor_connect.BioSensorAPI;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MainController {

    private BioSensorAPI sensorAPI = new BioSensorAPI();
    private DaoManager manager = new DaoManager();
    private MyTime myTime = new MyTime();

    private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
    private ScheduledFuture<?> task1;
    private ScheduledFuture<?> task2;
    private Boolean statusFlag = false;

    private XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private Date syncDate;
    private Integer syncValue;

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
    private Label valueLabel;

    @FXML
    public void initialize() {
        startButton.setId("round-red");
        stopButton.setId("green");
        stopButton.setVisible(false);
        series.setName("Bioactivity, Simens");

        lineChart.setCreateSymbols(false);
        lineChart.verticalGridLinesVisibleProperty().setValue(false);
        lineChart.horizontalGridLinesVisibleProperty().setValue(false);

        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(200);
        yAxis.setUpperBound(800);
        yAxis.setTickUnit(100);

        createChart();
    }

    private Parent createChart() {
        List<SensativityTableEntity> entity = manager.getBioEntity(myTime.weeks2AgoTime(), myTime.currentTime());
        for (int i = 0; i < entity.size(); i++) {
            Date date = entity.get(i).getDate();
            Integer value = entity.get(i).getValue();
            series.getData().add(new XYChart.Data<String, Number>(date.toString(), value));
        }
        syncValue = entity.get(entity.size() - 1).getValue();
        syncDate = entity.get(entity.size() - 1).getDate();
        lineChart.getData().add(series);
        Runnable drowChartTask = new Runnable() {
            @Override
            public void run() {
                if (statusFlag == true) {

                    try {
                        lock.readLock().lock();
                        Date date = syncDate;
                        Integer value = syncValue;
                        lock.readLock().unlock();
                        Platform.runLater(() -> series.getData().add(new XYChart.Data<>(date.toString(), value)));
                        Platform.runLater(() -> series.getData().remove(0, 1));
                        Platform.runLater(() -> valueLabel.setText(value.toString()));
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
        };
        task1 = executor.scheduleWithFixedDelay(drowChartTask, 1, 1, TimeUnit.SECONDS);

        return lineChart;
    }

    @FXML
    public void startBioactivity() {
        Runnable dbPushTask = new Runnable() {
            @Override
            public void run() {
                if (statusFlag == true) {

                    try {
                        lock.writeLock().lock();
                        sensorAPI.initializePort();
                        Integer value = sensorAPI.levelBioActivity();
                        Date date = new Date();
                        sensorAPI.closePort();
                        syncDate = date;
                        syncValue = value;
                        lock.writeLock().unlock();
                        manager.setValue(value, date);
                    } catch (SerialPortException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        task2 = executor.scheduleWithFixedDelay(dbPushTask, 0, 1, TimeUnit.SECONDS);
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