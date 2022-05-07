package rdm.management;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONObject;
import rdm.network.Monitorables;
import rdm.network.NetworkProperties;
import rdm.network.Topology;
import rdm.network.TopologyList;
import rdm.simulation.SimulationProperties;


/**
 * @author Huma Samin
 * @version 1.0
 * 
 * Class to define the Probe and Effector for the simulator. Provides an interface between the external 
 * decision-making agent and the RDM network.
 * 
 */
public class NetworkManagment implements Probe,Effector{
	
	public static NetworkProperties network_properties;
	public static SimulationProperties simulation_properties;
	private FileWriter file;
	private JSONObject mainObj; 
	
	
	/**
	* Constructor of the class to initialize the default network 
	* and simulation settings
	* 
	*/
	public NetworkManagment() 
	{		
		
		mainObj = new JSONObject();
		try
		{
		String path = new File("config_log_files/log.json").getAbsolutePath();
		file = new FileWriter(path);
		}catch(IOException ioex)
		{
			ioex.printStackTrace();
		}
		
		setNetworkProperties();
		setSimulationProperties();
		
		/*Topology current=new Topology();
		current.setTopologyName("rt");
		setCurrentTopology(current);*/
		
	}

	/**
	* Method to set the active links for the current simulation time step
	* @param  active_links  int value to represent the active links
	*/
	@Override
	public void setActiveLinks(int active_links) {
		// TODO Auto-generated method stub
		NetworkProperties.m.setActiveLinks(active_links);
	}
	

	/**
	* Method to set the bandwidth consumption for the current simulation time step
	* @param  bandwidth_consumption  double value to represent the bandwidth consumption
	*/

	@Override
	public void setBandwidthConsumption(double bandwidth_consumption) {
		// TODO Auto-generated method stub
		NetworkProperties.m.setBandwidthConsumption(bandwidth_consumption);
	}
	
	
	/**
	* Method to set the time to write data for the current simulation time step
	* @param  time_to_write  double value to represent the time to write data
	*/
	@Override
	public void setTimeToWrite(double time_to_write) {
		// TODO Auto-generated method stub
		NetworkProperties.m.setTimeToWrite(time_to_write);
	}
	
	/**
	* Method to set the topology for the current simulation time step
	* @param  current_topology  Topology object to represent the current topology
	*/
	@Override
	public void setCurrentTopology(Topology current_topology)
	{
		network_properties.setCurrentTopology(current_topology);
	}
	
	
	/**
	* Method to return the current topology for the network
	* @return Topology object representing the current topology
	* 
	*/	
	@Override
	public Topology getCurrentTopology() {
		// TODO Auto-generated method stub
		return network_properties.getCurrentTopology();
	}
	
	/**
	* Method to return the current bandwidth consumption for the network
	* @return int value representing bandwidth consumption
	* 
	*/	
	@Override
	public double getBandwidthConsumption() {
		// TODO Auto-generated method stub
		
		return network_properties.m.getBandwidthConsumption();
	}
	
	/**
	* Method to return the current active links for the network
	* @return int value representing active links
	* 
	*/
	@Override
	public int getActiveLinks() {
		// TODO Auto-generated method stub
		return network_properties.m.getActiveLinks();
	}

	/**
	* Method to return the current time to write data for the network
	* @return int value representing time to write data
	* 
	*/
	@Override
	public double getTimeToWrite() {
		// TODO Auto-generated method stub
		return network_properties.m.getTimeToWrite();
	}
	
	/**
	* Method to return the monitorable metrics values for current timestep 
	* @return Monitorable object representing monitorable metrics
	* 
	*/	
	public Monitorables getMonitorables() {
		
		
		String c_topology=getCurrentTopology().getTopologyName();
		Monitorables m=null;
		
		for(int i=0;i<TopologyList.topologies.size();i++)
		{
			if(TopologyList.topologies.get(i).getTopologyName().equals(c_topology))
			{
				m=NetworkProperties.m.generateMonitorables(TopologyList.topologies.get(i).getTopologyName(),simulation_properties.getUncertaintyScenario());
				
			}
		}
		
		return m;
		
		
	}
	
	
	/**
	* Method to return the Probe object
	* @return Probe object representing the Probe component of the simulator
	* 
	*/	
	public Probe getProbe()
	{
		return this;
	}
	
	/**
	* Method to return the Effector object
	* @return Effector object representing the Effector component of the simulator
	* 
	*/	
	public Effector getEffector()
	{
		return this;
	}

	/**
	* Method to set the network properties such as number of mirrors and links for the network
	* 
	*/		
	public void setNetworkProperties()
	{
		network_properties=new NetworkProperties();
		network_properties.loadNetworkSettings();
		

	}
	
	/**
	* Method to set the simulation properties such as simulation runs and uncertainty scenario
	*/		
	public void setSimulationProperties()
	{
		simulation_properties=new SimulationProperties();
		simulation_properties.loadSimulationSettings();
		
	}
	
	
	/**
	* Method to write the output log for the simulation runs	
	*/
	public void outputLog()
	{
				
		JSONObject obj =new JSONObject();
		
		
		obj.put("selected_topology",network_properties.getCurrentTopology().getTopologyName());//get current topology name method in Topology interface
		obj.put("active_links", (network_properties.m.getActiveLinks()));
		obj.put("badwidth_consumption",(network_properties.m.getBandwidthConsumption()));
		obj.put("time_to_write", (network_properties.m.getTimeToWrite()));
		
		mainObj.put(simulation_properties.getSimulationTimestep()+"", obj);
		
		
		
		
		
		
		if(simulation_properties.getSimulationTimestep()==simulation_properties.getSimulationRuns()-1)
		{
		try {
			 
			file.write(mainObj.toJSONString());
			file.flush();

		}
		catch (IOException e) {
			e.printStackTrace();
		}

		}
		
		
		
		
	}

	/**
	* Method to set the network topology at a particular simulation time step
	* @param  simulation_timestep  int value to represent the simulation time step
	* @param  selected_topology  String value to represent the topology name of the selected topology
	*/
	@Override
	public void setNetworkTopology(int simulation_timestep, String selected_topology) {
		
		
		simulation_properties.setSimulationTimestep(simulation_timestep);
		outputLog();
		for(int i=0;i<NetworkProperties.topologylist.topologies.size();i++)
		{
			if(NetworkProperties.topologylist.topologies.get(i).getTopologyName().equals(selected_topology))
			{
				network_properties.setCurrentTopology(NetworkProperties.topologylist.topologies.get(i));
			}
		}
		
		
		
		
	}

	
	
	

		
	
	
}
