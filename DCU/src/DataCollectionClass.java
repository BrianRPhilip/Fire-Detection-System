import java.util.Scanner;
import com.fazecast.jSerialComm.SerialPort;

public class DataCollectionClass{
	private boolean openPort;
	SerialPort ArPort = SerialPort.getCommPort("ACM1");
	//Initialises a SerialPort named "ArPort" that will capture Data being sent on the specified port Name
	
	protected String Data;
	protected String Temp;
	protected String Flame;
	protected String Smoke;
	protected Scanner data;
	protected boolean status;
	
	protected float temp;
	protected float flame;
	protected float smoke;
	
	public boolean createPort() {		
		openPort = ArPort.openPort(); 
		ArPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);;
		data = new Scanner(ArPort.getInputStream());
		return openPort;
	}
	
	public void getValue(){
		status = data.hasNext();
		while(status) {
			Data = data.nextLine();
			break;
		}
	}
	
	public String getData() {
		return Data;
	}
	
	public void SplitData() {
		
		String[] split = Data.split(":", 3);   //":" is the character at which the string will split. 
		Temp = split[0];  //So, there must be 2 full stops to split the string into 3 parts.
		Flame = split[1];
		Smoke = split[2];
	}
	
	public float ReturnTemp() {
		float temp = Float.parseFloat(Temp);
		return temp;
	}
	
	public boolean ReturnFlame() {
		boolean flame = "1".equals(Flame);
		return flame;
	}
	
	public boolean ReturnSmoke() {
		
		float smokeValue = Float.parseFloat(Smoke);  //convert string smoke data to a float
		double gasRs = (5.0-smokeValue)/smokeValue;   //Rs is resistance of sensor when exposed to gasses
		double Ro = 1.74;  //Ro is resistance of sensor in clean air
		double ratio = gasRs / Ro;
		return (ratio<6.3);
	}
	
	
	public boolean CheckData() {
		if(Data.length() > 10) {
			return true;
		}
		return false;
		
	}
	/**
	public float GetTempVal() {
		temp = Float.parseFloat(Temp);
		temp = (float) ((5.0 * temp * 100.0)/1024.0); 
		return temp;
	}

	public float GetSmokeVal() {
		smoke = Float.parseFloat(Smoke);
		smoke = (float) ((5.0 * temp * 100.0)/1024.0); //change
		return smoke;
		
	}
	*/
	
}
