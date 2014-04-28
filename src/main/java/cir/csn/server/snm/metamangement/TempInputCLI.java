package cir.csn.server.snm.metamangement;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import cir.csn.server.snm.db.DAOFactory;

public class TempInputCLI {
	private static SensorMetaDAO dao;
	private static Scanner sc;


	public static void main(String[] args) {
		sc = new Scanner(System.in);
		dao = new DAOFactory().sensorMetaDAO();

		int commandType = 0;
		boolean continue_flag = true;

		do {
			System.out.println("----Command Choose----");
			System.out.println("0. Log out");
			System.out.println("1. Create Sensor Meta");
			System.out.println("2. Update Sensor Meta");
			System.out.println("3. Delete Sensor Meta");
			System.out.println("4. Create Sensor Tags");
			System.out.println("5. Update Sensor Tags");
			System.out.println("6. Delete Sensor Tags");
			System.out.println("7. Create Sensor Network");
			System.out.println("8. Update Sensor Network");
			System.out.println("9. Delete Sensor Network");
			System.out.print("Choose Command: ");
			commandType = sc.nextInt();
			sc.nextLine();

			switch(commandType){
			case 0:
				continue_flag = false;
				break;
			case 1:
				createSensorMeta();
				break;
			case 2:
				updateSensorMeta(); 
				break;
			case 3:
				deleteSensorMeta();
				break;
			case 4:
				createSensorTag();
				break;
			case 5:
				updateSensorTag();
				break;
			case 6:
				deleteSensorTag();
				break;
			case 7:
				createSensorNetwork();
				break;
			case 8:
				updateSensorNetwork();
				break;
			case 9:
				deleteSensorNetwork();
				break;
			default:
				System.out.println("Wrong Command");
				break;
			}
		}while(continue_flag);
		System.out.println("Bye");
	}

	private static void createSensorMeta() {
		String ip = null;
		String id = null;
		String uri = null;

		System.out.println("Create Sensor Meta Mode");
		System.out.print("Sensor IP: ");
		ip = sc.nextLine();
		System.out.println(ip);
		System.out.print("Sensor ID: ");
		id = sc.nextLine();
		System.out.println(id);
		uri = "http://" + ip + "/" + id;
		System.out.println("Your URI: " + uri);

		try {
			dao.addSensor(uri, ip, id);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Finish DB Input");
	}

	private static void updateSensorMeta() {
		String ip = null;
		String id = null;
		String uri = null;

		System.out.println("Update Sensor Meta Mode");

		Map<String, String> sensorMetaMap = null;

		System.out.print("Input Sensor ID: ");
		id = sc.nextLine();

		try {
			sensorMetaMap = dao.getSensorMeta(id);


			ip = sensorMetaMap.get("ip");
			System.out.println("Current Sensor IP: " + ip);

			String old_id = id;

			System.out.println("Input Updated Sensor Meta ");
			System.out.print("Sensor IP: ");
			ip = sc.nextLine();
			System.out.println(ip);
			System.out.print("Sensor ID: ");
			id = sc.nextLine();
			System.out.println(id);
			uri = "http://" + ip + "/" + id;
			System.out.println("Your New URI: " + uri);

			dao.updateSensorMeta(uri, ip, id, old_id);
			System.out.println("Finish DB Update");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static void deleteSensorMeta() {
		String sensor_id = null;
		System.out.println("Delete Sensor Meta Mode");

		System.out.print("Input Sensor ID: ");
		sensor_id = sc.nextLine();

		try {
			dao.deleteSensorMeta(sensor_id);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("Finish Sensor Meta Delete");
	}

	private static void createSensorTag() {
		String id = null;
		String uri = null;
		String tag = null;
		Map<String, String> sensorMetaMap = null;

		System.out.println("Create Sensor Tags Mode");
		System.out.print("Sensor ID: ");
		id = sc.nextLine();

		try {
			sensorMetaMap = dao.getSensorMeta(id);
			uri = sensorMetaMap.get("uri");

			System.out.print("Input Tag: ");
			tag = sc.nextLine();

			dao.addTagInfo(uri, tag);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void updateSensorTag() {
		String uri = null;
		String id = null;
		String tag = null;
		String new_tag = null;
		Map<String, String> sensorMetaMap = null;

		System.out.println("Update Sensor Tags Mode");
		System.out.print("Sensor ID: ");
		id = sc.nextLine();

		System.out.print("Tag: ");
		tag = sc.nextLine();

		System.out.print("New Tag: ");
		new_tag = sc.nextLine();

		try {
			sensorMetaMap = dao.getSensorMeta(id);
			uri = sensorMetaMap.get("uri");
			
			dao.updateTagInfo(uri, tag, new_tag);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static void deleteSensorTag() {
		String uri = null;
		String id = null;
		String tag = null;
		Map<String, String> sensorMetaMap = null;

		System.out.println("Delete Sensor Tags Mode");
		System.out.print("Sensor ID: ");
		id = sc.nextLine();

		System.out.print("Tag: ");
		tag = sc.nextLine();

		try {
			sensorMetaMap = dao.getSensorMeta(id);
			uri = sensorMetaMap.get("uri");
			
			dao.deleteTagInfo(uri, tag);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void createSensorNetwork() {
		String name = null;
		String create_date = null;
		int sn_id = 0;
		String temp_sensor = null;
		Set<String> sensors = new HashSet<String>();
		System.out.println("Create Sensor Network Mode");
		System.out.print("Sensor Network Name: ");
		name = sc.nextLine();
		
		Calendar calendar = Calendar.getInstance();
        java.util.Date date = calendar.getTime();
        create_date = (new SimpleDateFormat("yyyyMMddHHmmss").format(date));
        
        try {
			dao.addSensorNetwork(name, create_date);
			System.out.println("Finish Insert Sensor Network in DB");
			System.out.println("Add Sensors");
			
			for(;;){
				System.out.print("Input Sensor: ");
				temp_sensor = sc.nextLine();
				if( temp_sensor.compareTo("end") == 0 )
					break;
				else
					sensors.add(temp_sensor);
			}
			
			sn_id = dao.getSensorNetworkID(name);
			dao.addSensorNetworkMembers(sn_id, sensors);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
	}

	private static void updateSensorNetwork() {
		String name = null;
		String new_name = null;
		
		System.out.println("Update Sensor Network Mode");
		System.out.print("Sensor Network Name: ");
		name = sc.nextLine();
		
		System.out.print("Sensor Network New Name: ");
		new_name = sc.nextLine();
        
        try {
			dao.updateSensorNetwork(name, new_name);
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        System.out.println("Finish Update Sensor Network in DB");
	}
	
	private static void deleteSensorNetwork() {
		String name = null;
		String delete_date = null;
		System.out.println("Delete Sensor Network Mode");

		System.out.print("Sensor Network Name: ");
		name = sc.nextLine();
		
		Calendar calendar = Calendar.getInstance();
        java.util.Date date = calendar.getTime();
        delete_date = (new SimpleDateFormat("yyyyMMddHHmmss").format(date));
        
        try {
			dao.deleteSensorNetwork(name, delete_date);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        System.out.println("Finish Delet Sensor Network in DB");
		
	}
}