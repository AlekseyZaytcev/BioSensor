/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensor_connect;

import jssc.SerialPort;
import jssc.SerialPortException;

/**
 * @author root
 */
public class BioSensorAPI {

    private String port = "COM4";
    private final byte request[] = {(byte) 0x01, (byte) 0x04, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x09,
            (byte) 0x61, (byte) 0xCC};

    SerialPort serialPort = new SerialPort(port);

    public void initializePort() throws SerialPortException {
        serialPort.openPort();
        serialPort.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);
    }

    public int levelBioActivity() throws SerialPortException {
        serialPort.writeBytes(request);
        int[] response = serialPort.readIntArray(23);
        int low = response[11] << 8;
        int high = response[12];
        int levelBioActivity = low + high;
        return levelBioActivity;
    }

    public void closePort() throws SerialPortException {
        serialPort.closePort();
    }

    public void sensitivity_5() throws SerialPortException {
        byte bytes[] = {(byte) 0x01, (byte) 0x06, (byte) 0x00, (byte) 0x02, (byte) 0x00, (byte) 0x05, (byte) 0xE8,
                (byte) 0x09};
        initializePort();
        serialPort.writeBytes(bytes);
        closePort();
    }

    public void sensitivity_60() throws SerialPortException {
        byte bytes[] = {(byte) 0x01, (byte) 0x06, (byte) 0x00, (byte) 0x02, (byte) 0x00, (byte) 0x3C, (byte) 0x28,
                (byte) 0x1B};
        initializePort();
        serialPort.writeBytes(bytes);
        closePort();
    }

    public void sensitivity_120() throws SerialPortException {
        byte bytes[] = {(byte) 0x01, (byte) 0x06, (byte) 0x00, (byte) 0x02, (byte) 0x00, (byte) 0x78, (byte) 0x28,
                (byte) 0x28};
        initializePort();
        serialPort.writeBytes(bytes);
        closePort();
    }

    public void sensitivity_180() throws SerialPortException {
        byte bytes[] = {(byte) 0x01, (byte) 0x06, (byte) 0x00, (byte) 0x02, (byte) 0x00, (byte) 0xB4, (byte) 0x28,
                (byte) 0x7D};
        initializePort();
        serialPort.writeBytes(bytes);
        closePort();
    }

    public void sensitivity_255() throws SerialPortException {
        byte bytes[] = {(byte) 0x01, (byte) 0x06, (byte) 0x00, (byte) 0x02, (byte) 0x00, (byte) 0xFF, (byte) 0x68,
                (byte) 0x4A};
        initializePort();
        serialPort.writeBytes(bytes);
        closePort();
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

}
