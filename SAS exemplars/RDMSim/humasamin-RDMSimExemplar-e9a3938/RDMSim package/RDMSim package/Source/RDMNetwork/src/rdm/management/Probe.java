package rdm.management;

import rdm.network.Monitorables;
import rdm.network.Topology;

/**
 * @author Huma Samin
 * @version 1.0
 * 
 * Interface to define the Probe for the RDM simulator  
 * 
 */
public interface Probe {
	
	
	/**
	* Method to return the current topology for the network
	* @return Topology object representing the current topology
	* 
	*/	
	public Topology getCurrentTopology();
	
	/**
	* Method to return the current bandwidth consumption for the network
	* @return int value representing bandwidth consumption
	* 
	*/	
	public double getBandwidthConsumption();
	
	/**
	* Method to return the current active links for the network
	* @return int value representing active links
	* 
	*/	
	public int getActiveLinks();
	
	/**
	* Method to return the current time to write data for the network
	* @return int value representing time to write data
	* 
	*/	
	public double getTimeToWrite();
	
	/**
	* Method to return the monitorable metrics values for current timestep 
	* @return Monitorable object representing monitorable metrics
	* 
	*/	
	public Monitorables getMonitorables();
	
	

}
