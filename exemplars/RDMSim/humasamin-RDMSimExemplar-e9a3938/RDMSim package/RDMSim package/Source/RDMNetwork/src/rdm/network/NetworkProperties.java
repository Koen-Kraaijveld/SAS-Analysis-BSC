package rdm.network;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import rdm.validation.RDMValidation;

/**
 * 
 * @author Huma Samin
 * @version 1.0
 * Class to set basic properties for RDM Network such as number of mirrors, number of links
 * and current topology.
 *
 */
public class NetworkProperties {
	
	public static int number_of_mirrors;
	public static int number_of_links;
	public static Monitorables m;
	
	public static TopologyList topologylist;
	public static Topology current_topology;
	
	
	/**
	* Constructor of the class to initialize the network properties such as
	* Monitorable metrics and Topologies for the network.
	* 
	*/
	public NetworkProperties() {
		super();
		
		m=new Monitorables();		
		topologylist=new TopologyList();
	}
	
	
	/**
	* This method sets the current topology.
	* @param  currenttopology  Topology object to be set as current topology for the network
	*
	*/
	public void setCurrentTopology(Topology currenttopology)
	{
		current_topology=currenttopology;
				
	}
	
	
	/**
	* This method returns the currently selected topology.
	* @return Object of Topology class to represent the current topology
	* 
	*/	
	
	public Topology getCurrentTopology()
	{
		return current_topology;
	}
	
	
	/**
	 * Method to load the network properties from the configuration file
	 */
	public void loadNetworkSettings() 
	{
		JSONParser parser = new JSONParser();
		
		try {
			String path = new File("config_log_files/configuration.json").getAbsolutePath();
			
			Object obj1 = parser.parse(new FileReader(path));//parsing the JSON string inside the file that we created earlier.

			JSONObject jsonObject = (JSONObject) obj1;
			
			//Step 1: Load the number of mirrors from configuration file
			int mirror_number = Integer.parseInt(jsonObject.get("mirror_number").toString());
			number_of_mirrors=mirror_number;
			//set the number of links on the basis of number of mirrors as a fully connected RDM network
			number_of_links = (number_of_mirrors*(number_of_mirrors-1)/2);
			
			//Step 2: load Topologies			
			topologylist.loadTopologies();
			topologylist.loadTopologyImpacts();
			
			//Step 3: load the threshold values for monitorables
			double active_links_threshold = Double.parseDouble(jsonObject.get("link_threshold").toString());
			
			double bandwidth_threshold = Double.parseDouble(jsonObject.get("bandwidth_threshold").toString());
			
			double writing_time_threshold = Double.parseDouble(jsonObject.get("writing_times_threshold").toString());
			
			
			//Validation of threshold values
			try
			{
				
				if(mirror_number<5||mirror_number>30000)
					
				{
					
					throw new RDMValidation("invalid_mirror_number");
				}	
				
			if(active_links_threshold<0.0||active_links_threshold>100.0)
						
			{
				
				throw new RDMValidation("invalid_link_threshold");
			}
			if(bandwidth_threshold<0||bandwidth_threshold>100)
			{
				throw new RDMValidation("invalid_bandwidth_threshold");
			}
			if(writing_time_threshold<0||writing_time_threshold>100)
			{
				throw new RDMValidation("invalid_writing_time_threshold");
			}
		}catch(RDMValidation va)
		{
			va.validateConfigurationSetup();
		}
			m.setThresholdActiveLinks(((active_links_threshold*number_of_links)/100));
			m.setThresholdBandwidthConsumption((bandwidth_threshold*(number_of_links*30))/100);
			m.setThresholdTimeToWrite((writing_time_threshold*(number_of_links*20))/100);
			
		}
		catch(FileNotFoundException fe)
		{
			fe.printStackTrace();
		}
		catch(IOException ioex)
		{
			ioex.printStackTrace();
		}
		catch(ParseException pe)
		{
			pe.printStackTrace();
		}

	}

}
