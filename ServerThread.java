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
			byte[] resultBuff = new byte[0];
		    byte[] buff = new byte[1024];
		    int k = -1;


		    
		    while((k = client.getInputStream().read(buff, 0, buff.length)) > -1) {
		        byte[] tbuff = new byte[resultBuff.length + k]; // temp buffer size = bytes already read + bytes last read
		        System.arraycopy(resultBuff, 0, tbuff, 0, resultBuff.length); // copy previous bytes
		        System.arraycopy(buff, 0, tbuff, resultBuff.length, k);  // copy current lot
		        resultBuff = tbuff; // call the temp buffer as your result buff
		    }
		    System.out.println(resultBuff.length + " bytes read.");
		    System.out.println(resultBuff);
		    
		    InputStream is = client.getInputStream();
		    ByteArrayOutputStream buffer = new ByteArrayOutputStream();

			int nRead;
			byte[] data = new byte[16384];

			while ((nRead = is.read(data, 0, data.length)) != -1) {
			  buffer.write(data, 0, nRead);
			}

			buffer.flush();

			System.out.println(buffer.toByteArray());

		    // EnvironmentMonitoringProtos.EnvironmentUpdate.SensorRecord sensorData = EnvironmentMonitoringProtos.EnvironmentUpdate.SensorRecord.parseFrom(resultBuff.toByteArray());

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

