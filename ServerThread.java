import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;
import java.io.ByteArrayOutputStream;

/*
 * Individual ServerThread listens for the client to tell it what command to run, then
 * runs that command and sends the output of that command to the client
 *
 */
public class ServerThread extends Thread {
	Socket client = null;
	PrintWriter output;
	BufferedReader input;
	
	public ServerThread(Socket client) {
		this.client = client;
	}
	
	public void run() {
		System.out.println("Accepted connection. ");

		try {
			InputStream input = client.getInputStream();

			EnvironmentMonitoringProtos.EnvironmentUpdate sensorData = EnvironmentMonitoringProtos.EnvironmentUpdate.parseFrom(input);
			
			System.out.println("SerializedSize: " + sensorData.getSerializedSize());
			System.out.println("Total Sensor record: " + sensorData.getSensorRecordCount());


			// for (EnvironmentMonitoringProtos.EnvironmentUpdate.SensorRecord data: sensorData.getSensorRecordList()) {
		 //      // print some record;
		 //      System.out.println("Timestamp: " + data.getTimestamp());
		 //      System.out.println("Temp: " + data.getTemperature());
		 //      System.out.println("Humd: " + data.getRelativeHumidity());
		 //      System.out.println("CO: " + data.getCarbonMonoxide());
		 //      System.out.println("PM: " + data.getParticulateMatter());
		 //      System.out.println("UV: " + data.getUltraviolet());
		 //      }
		}
		catch (IOException e) {
			e.printStackTrace();
		} 
		finally {
			// close the connection to the client
			try {
				client.close();
			}
			catch (IOException e) {
				e.printStackTrace();	
			}			
			System.out.println("Output closed.");
		}

	}
}

