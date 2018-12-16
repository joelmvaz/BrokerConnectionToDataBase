import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.TooManyListenersException;

/**
 * Class declaration
 *
 *
 * @author
 * @version 1.8, 08/03/00
 */
public class ComControl implements Runnable, SerialPortEventListener
{
    static CommPortIdentifier portId1;

    InputStream inputStream;

    byte[] totalReadBuffer = null;

    SerialPort serialPort1;

    Thread readThread;

    protected String divertCode = "10";

    static String TimeStamp;

    String scannedInput = "";

    String tempInput = "";

    /**
     * Method declaration
     *
     *
     * @param args
     *
     * @see
     */
    public static void main(String[] args)
    {

        try
        {
            Enumeration ports = CommPortIdentifier.getPortIdentifiers();
            while(ports.hasMoreElements())
            {
                CommPortIdentifier port = (CommPortIdentifier) ports.nextElement();
                String type;
                switch(port.getPortType())
                {
                    case CommPortIdentifier.PORT_PARALLEL:
                        type = "Parallel";
                        break;
                    case CommPortIdentifier.PORT_SERIAL:
                        type = "Serial";
                        break;
                    default: // / Shouldn't happen
                        type = "Unknown";
                        break;
                }
                System.out.println(port.getName() + ": " + type);
            }

            portId1 = CommPortIdentifier.getPortIdentifier("/dev/ttyACM0");
            ComControl reader = new ComControl();
        }

        catch(Exception e)
        {
            TimeStamp = new java.util.Date().toString();
            System.out.println(TimeStamp + ": /dev/ttyACM0 " + portId1);
            System.out.println(TimeStamp + ": msg1 - " + e);
        }
    };

    /**
     * Constructor declaration
     *
     *
     * @see
     */
    public ComControl()
    {

        try
        {
            TimeStamp = new java.util.Date().toString();
            serialPort1 = (SerialPort) portId1.open("ComControl", 2000);
            System.out.println(TimeStamp
                    + ": "
                    + portId1.getName()
                    + " opened for scanner input");
        }
        catch(PortInUseException e)
        {
        }
        try
        {
            inputStream = serialPort1.getInputStream();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        try
        {
            serialPort1.addEventListener(this);
        }
        catch(TooManyListenersException e)
        {
        }
        serialPort1.notifyOnDataAvailable(true);
        try
        {

            serialPort1.setSerialPortParams(9600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            serialPort1.setDTR(false);
            serialPort1.setRTS(false);

        }
        catch(UnsupportedCommOperationException e)
        {
            e.printStackTrace();
        }

        readThread = new Thread(this);
        readThread.start();
    }

    /**
     * Method declaration
     *
     *
     * @see
     */
    public void run()
    {
        try
        {
            Thread.sleep(30000);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Method declaration
     *
     *
     * @param event
     *
     * @see
     */
    public void serialEvent(SerialPortEvent event)
    {
        switch(event.getEventType())
        {
            case SerialPortEvent.BI:
            case SerialPortEvent.OE:
            case SerialPortEvent.FE:
            case SerialPortEvent.PE:
            case SerialPortEvent.CD:
            case SerialPortEvent.CTS:
            case SerialPortEvent.DSR:
                System.out.println("data set ready");
            case SerialPortEvent.RI:
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                System.out.println("empty");
                break;
            case SerialPortEvent.DATA_AVAILABLE:
                TimeStamp = new java.util.Date().toString();
                System.out.println("*DATA avail: " + TimeStamp);

                try
                {
                    System.out.println("count: " + inputStream.available());
                    byte[] readBuffer = new byte[inputStream.available()];
                    while(inputStream.available() > 0)
                    {
                        int numBytes = inputStream.read(readBuffer);
                    }
                    totalReadBuffer = readBuffer;
                    System.out.print(new String(readBuffer));
                }
                catch(IOException e)
                {
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
                break;
        }
        System.out.println("end of event");
    }
}