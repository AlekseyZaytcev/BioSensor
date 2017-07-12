import jssc.SerialPortException;
import sensor_connect.BioSensorAPI;

import static dao.DaoManager.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        try {
            BioSensorAPI sensor = new BioSensorAPI();
            sensor.initializePort();
            while (i < 100) {
                int val = sensor.levelBioActivity();
                setValue(val);
                Thread.sleep(1000);
                i++;
            }
            sensor.closePort();
            System.out.println(getDate());
            System.out.println(getValue());

        } catch (SerialPortException e) {
            System.out.println("Ошибка: " + e);

        }
    }

}
