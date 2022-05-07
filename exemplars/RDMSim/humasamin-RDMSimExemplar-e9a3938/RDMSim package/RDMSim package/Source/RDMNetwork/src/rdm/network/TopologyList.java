package rdm.network;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import rdm.validation.RDMValidation;

/**
 * @author Huma Samin
 * @version 1.0
 * 
 * Class to define the list of topologies for the RDM network and 
 * loading topology impacts on the monitorable metrics
 * 
 */
public class TopologyList {
	
	public static ArrayList<Topology> topologies;
	
	
	/**
	* Constructor of the  to initialize the topologies list
	* 
	*/
	public TopologyList()
	{
		topologies=new ArrayList<Topology>();
	}
	
	/**
	* This method adds a topology to the topology list
	* @param  topology  Topology object to be added to the topology list
	*
	*/
	public void addTopology(Topology topology)
	{
		topologies.add(topology);
	}
	
	
	/**
	* This method removes a specific topology from the topology list
	* @param  topology  Topology object to be removed from the topology list
	*
	*/
	public void removeTopology(Topology topology)
	{
		for(int i=0;i<topologies.size();i++)
		{
			if(topologies.get(i).equals(topology))
			{
				topologies.remove(i);
			}
		}
	}
	
	
	/**
	* This method searches and returns a specific topology from the list
	* @param  topology  Topology object to be removed from the topology list
	* @return Topology object
	* 
	*/	
	public Topology getTopology(Topology topology)
	{
		for(int i=0;i<topologies.size();i++)
		{
			if(topologies.get(i).equals(topology))
			{
				return topologies.get(i);
			}
		}
		return null;
		
	}
	
	/**
	* This method loads the topolgies defined in the configuartion settings 
	* into the topology list 
	*/	
	
	public void loadTopologies()
	{
		JSONParser parser = new JSONParser();
		
		try {
			String path = new File("config_log_files/configuration.json").getAbsolutePath();
			
			Object obj1 = parser.parse(new FileReader(path));//parsing the JSON string inside the file that we created earlier.

			JSONObject jsonObject = (JSONObject) obj1;
			
			
			JSONArray topologylist = (JSONArray) jsonObject.get("topologies");
			
			for(int i=0;i<topologylist.size();i++)
			{
				Topology t=new Topology();
				t.setTopologyName(topologylist.get(i).toString());
				topologies.add(t);
				
			}
			
				
			
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
	
	/**
	* This method loads the topology impacts defined in the configuration settings 
	* for the topologies loaded in the topology list
	*/	
	public void loadTopologyImpacts()
	{
		JSONParser parser = new JSONParser();
		
		try {
			String path = new File("config_log_files/configuration.json").getAbsolutePath();
			
			Object obj1 = parser.parse(new FileReader(path));//parsing the JSON string inside the file that we created earlier.

			for(int i=0;i<topologies.size();i++)
			{
				JSONObject jsonObject = (JSONObject) obj1;
			
			
				JSONArray t_active_links = (JSONArray) jsonObject.get(topologies.get(i).getTopologyName()+"_active_links");
			
				int min=((int) Integer.parseInt(t_active_links.get(0).toString()));
				int max=((int) Integer.parseInt(t_active_links.get(1).toString()));
				
				
				try
				{
					if(min<0||min>100||min>=max)
					{
						throw new RDMValidation("invalid_range_links");
					}
					else if(max<0||max>100||min>=max)
					{
						throw new RDMValidation("invalid_range_links");
					}
					
				
				}
				catch(RDMValidation va)
				{
					va.validateConfigurationSetup();
				}
				
				
				topologies.get(i).setMinActiveLinks((NetworkProperties.number_of_links*(int) Integer.parseInt(t_active_links.get(0).toString())/100));
				topologies.get(i).setMaxActiveLinks((NetworkProperties.number_of_links*(int) Integer.parseInt(t_active_links.get(1).toString())/100));
				
				int link_bandwidth=(int)ThreadLocalRandom.current().nextDouble(20,30+1);
				topologies.get(i).setMinBandwidthConsumption(topologies.get(i).getMinActiveLinks()*link_bandwidth);
				topologies.get(i).setMaxBandwidthConsumption(topologies.get(i).getMaxActiveLinks()*link_bandwidth);
				
				
				int link_time_to_write=(int)ThreadLocalRandom.current().nextDouble(10,20+1);
				
				topologies.get(i).setMinTimeToWrite(topologies.get(i).getMinActiveLinks()*link_time_to_write);
				topologies.get(i).setMaxTimeToWrite(topologies.get(i).getMaxActiveLinks()*link_time_to_write);
				
				
			}	
			
			
			
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
