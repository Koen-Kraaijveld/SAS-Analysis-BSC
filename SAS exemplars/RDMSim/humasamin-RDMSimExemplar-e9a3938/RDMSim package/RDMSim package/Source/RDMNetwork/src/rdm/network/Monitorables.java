package rdm.network;
import java.util.concurrent.ThreadLocalRandom;
import rdm.simulation.UncertaintyScenario;

/**
 * @author Huma Samin
 * @version 1.0
 * Class to define Monitorable Metrics for the RDM network and randomly generate their values
 * 
 */

public class Monitorables {
	
	private int active_links;
	private double time_to_write;
	private double bandwidth_consumption;
	private double threshold_active_links;	
	private double threshold_bandwidth_consumption;
	private double threshold_time_to_write;
	private double alpha;
	
	/**
	* This method returns the fraction of active links to represent a communication path
	* between mirrors
	* @return alpha
	* 
	*/	
	public double getAlpha() {
		return alpha;
	}
	
	/**
	* This method sets the fraction for active links for the communication path between
	* mirrors
	* @param  alpha  Fraction of active links
	*
	*/
	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}
	
	
	
	
	
	/**
	* This method returns the number of active links thats have been set
	* for the currently selected topology.
	* @return the number of active links
	* 
	*/	
	public int getActiveLinks() {
		return active_links;
	}
	
	/**
	* This method sets the number of active links for the currently 
	* selected topology.
	* @param  active_links  value for number of active links
	*
	*/
	public void setActiveLinks(int active_links) {
		this.active_links = active_links;
	}
	
	/**
	* This method returns the time to write data for the currently selected topology.
	* @return time to write data
	* 
	*/
	public double getTimeToWrite() {
		return time_to_write;
	}
	
	
	/**
	* This method sets time to write data for the currently 
	* selected topology.
	* @param  writing_time  value for time to write data
	*
	*/
	
	public void setTimeToWrite(double writing_time) {
		this.time_to_write = writing_time;
	}
	
	/**
	* This method returns the bandwidth consumption
	* for the currently selected topology.
	* @return bandwidth consumption
	* 
	*/
	public double getBandwidthConsumption() {
		return bandwidth_consumption;
	}
	
	/**
	* This method sets the bandwidth consumption for the currently 
	* selected topology.
	* @param  bandwidth_consumption value for bandwidth consumption
	*
	*/
	public void setBandwidthConsumption(double bandwidth_consumption) {
		this.bandwidth_consumption = bandwidth_consumption;
	}
	
	
	/**
	* This method returns the satisfaction threshold for active links
	* @return threshold value for active links
	* 
	*/
	
	public double getThresholdActiveLinks() {
		return threshold_active_links;
	}
	
	/**
	* This method sets the satisfaction threshold for the active links
	* @param threshold_active_links threshold value for active links
	*
	*/
	public void setThresholdActiveLinks(double threshold_active_links) {
		this.threshold_active_links = threshold_active_links;
	}
	
	/**
	* This method returns satisfaction threshold for bandwidth consumption
	* @return threshold value for bandwidth consumption
	* 
	*/
	public double getThresholdBandwidthConsumption() {
		return threshold_bandwidth_consumption;
	}
	
	/**
	* This method sets the satisfaction threshold for bandwidth consumption
	* @param threshold_bandwidth_consumption threshold value for bandwidth consumption
	*
	*/
	public void setThresholdBandwidthConsumption(double threshold_bandwidth_consumption) {
		this.threshold_bandwidth_consumption = threshold_bandwidth_consumption;
	}
	
	/**
	* This method returns the satisfaction threshold for time to write data.
	* @return threshold value for time to write
	* 
	*/
	public double getThresholdTimeToWrite() {
		return threshold_time_to_write;
	}
	
	/**
	* This method sets the satisfaction threshold for time to write data
	* @param threshold_time_to_write threshold value for time to write data
	*
	*/
	public void setThresholdTimeToWrite(double threshold_time_to_write) {
		this.threshold_time_to_write = threshold_time_to_write;
	}

	
	
	/**
	* This method generate monitorable metric values for a single run according to the selected topology
	* under the selected uncertainty scenario.
	* @return Monitorable values for a single run
	* 
	*/	
	public Monitorables generateMonitorables(String selected_topology,UncertaintyScenario current_scenario)
	{
			alpha=1.0;
		for(int i=0;i<TopologyList.topologies.size();i++)
		{
			if(TopologyList.topologies.get(i).getTopologyName().equals(selected_topology)&& current_scenario.getCurrentScenario()==0)
			{
				
				double link_dev=0;
				double bandwidth_dev=0;
				double writing_time_dev=0;
				
				double al=ThreadLocalRandom.current().nextDouble(TopologyList.topologies.get(i).getMinActiveLinks(),TopologyList.topologies.get(i).getMaxActiveLinks()+1);
				active_links=(int)(al-((link_dev/100)*al));
				
				int link_bandwidth=(int)ThreadLocalRandom.current().nextDouble(20,30+1);
				double bc=(alpha*active_links)*link_bandwidth;
				bandwidth_consumption=(bc-((bandwidth_dev/100)*bc));
				int link_time_to_write=(int)ThreadLocalRandom.current().nextDouble(10,20+1);
				double ttw=(alpha*active_links)*link_time_to_write;
				time_to_write=(ttw-((writing_time_dev/100)*ttw));
				
				
			}
			else
			{
				if(selected_topology.equals("rt")&&current_scenario.getCurrentScenario()!=0)
				{
					
					
					
					double link_dev=ThreadLocalRandom.current().nextDouble(current_scenario.getMinDeviationRTLinks(),current_scenario.getMaxDeviationRTLinks()+1);
					double bandwidth_dev=ThreadLocalRandom.current().nextDouble(current_scenario.getMinDeviationRTBandwidth(),current_scenario.getMaxDeviationRTBandwidth()+1);
					double writing_time_dev=ThreadLocalRandom.current().nextDouble(current_scenario.getMinDeviationRTWritingTime(),current_scenario.getMaxDeviationRTWritingTime()+1);
					
					if(link_dev>0&&link_dev<1)
					{
						link_dev=0;
					}
					if(bandwidth_dev>0&&bandwidth_dev<1)
					{
						bandwidth_dev=0;
					}
					if(writing_time_dev>0&&writing_time_dev<1)
					{
						writing_time_dev=0;
					}
					
					
					double al=ThreadLocalRandom.current().nextDouble(TopologyList.topologies.get(1).getMinActiveLinks(),TopologyList.topologies.get(1).getMaxActiveLinks()+1);
					active_links=(int)(al-((link_dev/100)*al));
					
					int link_bandwidth=(int)ThreadLocalRandom.current().nextDouble(20,30+1);
					
				
					double bc=(alpha*active_links)*link_bandwidth;
					bandwidth_consumption=(bc+((bandwidth_dev/100)*bc));
					int link_time_to_write=(int)ThreadLocalRandom.current().nextDouble(10,20+1);
					double ttw=(alpha*active_links)*link_time_to_write;
					time_to_write=(ttw+((writing_time_dev/100)*ttw));
					
					
				}
				else if(selected_topology.equals("mst")&&current_scenario.getCurrentScenario()!=0) {
				
				   double link_dev=ThreadLocalRandom.current().nextDouble(current_scenario.getMinDeviationMSTLinks(),current_scenario.getMaxDeviationMSTLinks()+1);
				   double bandwidth_dev=ThreadLocalRandom.current().nextDouble(current_scenario.getMinDeviationMSTBandwidth(),current_scenario.getMaxDeviationMSTBandwidth()+1);
				   double writing_time_dev=ThreadLocalRandom.current().nextDouble(current_scenario.getMinDeviationMSTWritingTime(),current_scenario.getMaxDeviationMSTWritingTime()+1);
				  
				   if(link_dev>0&&link_dev<1)
					{
						link_dev=0;
					}
					if(bandwidth_dev>0&&bandwidth_dev<1)
					{
						bandwidth_dev=0;
					}
					if(writing_time_dev>0&&writing_time_dev<1)
					{
						writing_time_dev=0;
					}
				   
				  
				   
				   double al=ThreadLocalRandom.current().nextDouble(TopologyList.topologies.get(0).getMinActiveLinks(),TopologyList.topologies.get(0).getMaxActiveLinks()+1);
				   active_links=(int)(al-((link_dev/100)*al));
				   
				   
				   int link_bandwidth=(int)ThreadLocalRandom.current().nextDouble(20,30+1);
					double bc=(alpha*active_links)*link_bandwidth;
				   bandwidth_consumption=(bc+((bandwidth_dev/100)*bc));
				   int link_time_to_write=(int)ThreadLocalRandom.current().nextDouble(10,20+1);
					double ttw=(alpha*active_links)*link_time_to_write;
				   time_to_write=(ttw+((writing_time_dev/100)*ttw));
				   

				  				
				}
				
			}
		}
		
		return this;
		
		
	}
	
	

}
