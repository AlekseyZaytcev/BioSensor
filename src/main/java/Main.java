import jssc.SerialPortException;
import sensor_connect.BioSensorAPI;

import java.util.Date;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        try {
            BioSensorAPI sensor = new BioSensorAPI();
            sensor.initializePort();
            while (i < 100) {
                Date date = new Date();
                int val = sensor.levelBioActivity();
                System.out.println(date.toString() + "\t" + val);
                Thread.sleep(1000);
            }
            i++;
            sensor.closePort();

        } catch (SerialPortException e) {
            System.out.println("Ошибка: " + e);

        }
    }

}
