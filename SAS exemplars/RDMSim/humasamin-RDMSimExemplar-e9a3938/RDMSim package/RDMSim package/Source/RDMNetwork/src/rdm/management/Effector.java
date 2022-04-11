package rdm.management;

import rdm.network.Topology;

/**
 * @author Huma Samin
 * @version 1.0
 * 
 * Interface to define the Effector for the RDM simulator  
 * 
 */
public interface Effector {
	
	/**
	* Method to set the network topology at a particular simulation time step
	* @param  simulation_timestep  int value to represent the simulation time step
	* @param  selected_topology  String value to represent the topology name of the selected topology
	*/
	public void setNetworkTopology(int simulation_timestep,String selected_topology);
	
	/**
	* Method to set the active links for the current simulation time step
	* @param  active_links  int value to represent the active links
	*/
	public void setActiveLinks(int active_links);
	
	/**
	* Method to set the bandwidth consumption for the current simulation time step
	* @param  bandwidth_consumption  double value to represent the bandwidth consumption
	*/
	public void setBandwidthConsumption(double bandwidth_consumption);
	
	/**
	* Method to set the time to write data for the current simulation time step
	* @param  time_to_write  double value to represent the time to write data
	*/
	public void setTimeToWrite(double time_to_write);
	
	/**
	* Method to set the topology for the current simulation time step
	* @param  current_topology  Topology object to represent the current topology
	*/
	public void setCurrentTopology(Topology current_topology);
	

}
