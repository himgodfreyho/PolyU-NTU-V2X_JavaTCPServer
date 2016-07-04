import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;
import java.io.ByteArrayOutputStream;
import java.util.List;

/*
 * Individual ServerThread listens for the client to tell it what command to run, then
 * runs that command and sends the output of that command to the client
 *
 */
public class ServerThread extends Thread {
	Socket client = null;
	PrintWriter output;
	BufferedReader input;
	int sn = 0;
	
	public ServerThread(Socket client) {
		this.client = client;
	}
	
	public void run() {
		System.out.println("Accepted connection. ");

		try {
			InputStream input = client.getInputStream();

			EnvironmentMonitoringProtos.EnvironmentUpdate sensorData = EnvironmentMonitoringProtos.EnvironmentUpdate.parseFrom(input);
			
			System.out.println("SerializedSize: " + sensorData.getSerializedSize());
			System.out.println("Total Sensor record recevied: " + sensorData.getSensorRecordCount());



			for (EnvironmentMonitoringProtos.EnvironmentUpdate.SensorRecord data: sensorData.getSensorRecordList()) {
		      List<EnvironmentMonitoringProtos.EnvironmentUpdate.SensorData> list = data.getSensorDataList();
		      // print some record;
		      System.out.println("SN: " + data.getSequenceNumber());
		      // sn = data.getSequenceNumber();
		      
		      // System.out.println("Timestamp: " + data.getTimestamp());
		      // System.out.println("List: " + list);
		      // System.out.println("Key: " + list.get(1));

		      
		      }
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

