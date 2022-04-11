package rdm.network;

/**
 * @author Huma Samin
 * @version 1.0
 * 
 * Class to define Topology for the RDM network and setting the ranges for monitorable metrics
 * for the topology
 * 
 */

public class Topology {
	
	private String topology_name;
	private double min_active_links;
	private double max_active_links;
	private double min_bandwidth_consumption;
	private double max_bandwidth_consumption;
	private double min_time_to_write;
	private double max_time_to_write;

	
	/**
	* Constructor of the class to initialize the default topology as
	* the Redundant Topology
	* 
	*/
	public Topology()
	{
		topology_name="rt";
	}
	

	/**
	* This method sets the name of the topology
	* @param  topologyname  String value to be set as topology name
	*
	*/
	public void setTopologyName(String topologyname)
	{
		this.topology_name=topologyname;
	}
	
	/**
	* This method returns the topology name
	* @return topology name
	* 
	*/		
	public String getTopologyName()
	{
		return this.topology_name;
	}
	
	
	/**
	* This method returns the minimum number of possible active links for the topology
	* @return value representing the minimum number of active links
	* 
	*/		
	public double getMinActiveLinks() {
		return min_active_links;
	}

	/**
	* This method sets the minimum number of possible active links for the topology
	* @param  min_active_links  double value to be set as the minimum active links
	*
	*/

	public void setMinActiveLinks(double min_active_links) {
		this.min_active_links = min_active_links;
	}

	/**
	* This method returns the maximum number of possible active links for the topology
	* @return value representing the maximum number of active links
	* 
	*/	
	public double getMaxActiveLinks() {
		return max_active_links;
	}


	/**
	* This method sets the maximum number of possible active links for the topology
	* @param  max_active_links  double value to be set as the maximum active links
	*
	*/
	public void setMaxActiveLinks(double max_active_links) {
		this.max_active_links = max_active_links;
	}

	/**
	* This method returns the minimum possible bandwidth consumption for the topology
	* @return value representing the minimum bandwidth consumption
	* 
	*/	
	public double getMinBandwidthConsumption() {
		return min_bandwidth_consumption;
	}

	/**
	* This method sets the minimum bandwidth consumption for the topology
	* @param  min_bandwidth_consumption  double value to be set as the minimum bandwidth consumption
	*
	*/
	public void setMinBandwidthConsumption(double min_bandwidth_consumption) {
		this.min_bandwidth_consumption = min_bandwidth_consumption;
	}

	/**
	* This method returns the maximum possible bandwidth consumption for the topology
	* @return value representing the maximum bandwidth consumption
	* 
	*/	
	public double getMaxBandwidthConsumption() {
		return max_bandwidth_consumption;
	}

	/**
	* This method sets the maximum bandwidth consumption for the topology
	* @param  max_bandwidth_consumption  double value to be set as the maximum bandwidth consumption
	*
	*/
	public void setMaxBandwidthConsumption(double max_bandwidth_consumption) {
		this.max_bandwidth_consumption = max_bandwidth_consumption;
	}

	/**
	* This method returns the minimum possible time to write data for the topology
	* @return value representing the minimum time to write data
	* 
	*/	
	public double getMinTimeToWrite() {
		return min_time_to_write;
	}

	/**
	* This method sets the minimum time to write data for the topology
	* @param  min_time_to_write  double value to be set as the minimum time to write data
	*
	*/
	public void setMinTimeToWrite(double min_time_to_write) {
		this.min_time_to_write = min_time_to_write;
	}

	/**
	* This method returns the maximum possible time to write data for the topology
	* @return value representing the maximum time to write data
	* 
	*/	
	public double getMaxTimeToWrite() {
		return max_time_to_write;
	}

	/**
	* This method sets the maximum time to write data for the topology
	* @param  max_time_to_write  double value to be set as the maximum time to write data
	*
	*/
	public void setMaxTimeToWrite(double max_time_to_write) {
		this.max_time_to_write = max_time_to_write;
	}

	
	

}
