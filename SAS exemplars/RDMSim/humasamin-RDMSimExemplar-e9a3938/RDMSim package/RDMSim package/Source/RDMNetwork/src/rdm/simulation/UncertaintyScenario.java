package rdm.simulation;


/**
 * @author Huma Samin
 * @version 1.0
 * 
 * Class to define the uncertainty scenarios for the experiments
 */
public class UncertaintyScenario {
	
	private int current_scenario;
	private int min_deviation_mst_links;
	private int max_deviation_mst_links;	
	private int min_deviation_rt_links;	
	private int max_deviation_rt_links;
	
	private int min_deviation_mst_bandwidth;
	private int max_deviation_mst_bandwidth;	
	private int min_deviation_rt_bandwidth;
	private int max_deviation_rt_bandwidth;
	
	private int min_deviation_mst_writing_time;
	private int max_deviation_mst_writing_time;	
	private int min_deviation_rt_writing_time;
	private int max_deviation_rt_writing_time;
	
	
	/**
	 * Constructor to initialize the uncertainty scenario
	 */
	public UncertaintyScenario() {
		// TODO Auto-generated constructor stub
		current_scenario=0;
	}

	/**
	* This method returns the current uncertainty scenario
	* @return int value representing the current scenario
	* 
	*/	
	public int getCurrentScenario() {
		return current_scenario;
	}

	/**
	* This method sets the current uncertainty scenario
	* @param current_scenario int value representing the current scenario
	* 
	*/	
	public void setCurrentScenario(int current_scenario) {
		this.current_scenario = current_scenario;
	}
	
	/**
	* This method returns the mimimum percentage deviation for active links 
	* during MST topology
	* @return int value representing the minimum deviation
	* 
	*/	
	public int getMinDeviationMSTLinks() {
		return min_deviation_mst_links;
	}
	
	/**
	* This method sets the mimimum percentage deviation for active links 
	* during MST topology
	* @param min_deviation_mst_links int value to represent minimum deviation
	* 
	*/
	public void setMinDeviationMSTLinks(int min_deviation_mst_links) {
		this.min_deviation_mst_links = min_deviation_mst_links;
	}

	/**
	* This method returns the maximum percentage deviation for active links 
	* during MST topology
	* @return int value representing the maximum deviation
	* 
	*/	
	public int getMaxDeviationMSTLinks() {
		return max_deviation_mst_links;
	}

	/**
	* This method sets the maximum percentage deviation for active links 
	* during MST topology
	* @param max_deviation_mst_links int value to represent maximum deviation
	* 
	*/
	public void setMaxDeviationMSTLinks(int max_deviation_mst_links) {
		this.max_deviation_mst_links = max_deviation_mst_links;
	}
	
	/**
	* This method returns the mimimum percentage deviation for active links 
	* during RT topology
	* @return int value representing the minimum deviation
	* 
	*/	
	public int getMinDeviationRTLinks() {
		return min_deviation_rt_links;
	}

	/**
	* This method sets the mimimum percentage deviation for active links 
	* during RT topology
	* @param min_deviation_rt_links int value to represent minimum deviation
	* 
	*/
	public void setMinDeviationRTLinks(int min_deviation_rt_links) {
		this.min_deviation_rt_links = min_deviation_rt_links;
	}
	
	/**
	* This method returns the maximum percentage deviation for active links 
	* during RT topology
	* @return int value representing the maximum deviation
	* 
	*/	
	public int getMaxDeviationRTLinks() {
		return max_deviation_rt_links;
	}

	/**
	* This method sets the maximum percentage deviation for active links 
	* during RT topology
	* @param max_deviation_rt_links int value to represent maximum deviation
	* 
	*/
	public void setMaxDeviationRTLinks(int max_deviation_rt_links) {
		this.max_deviation_rt_links = max_deviation_rt_links;
	}

	/**
	* This method returns the mimimum percentage deviation for bandwidth consumption 
	* during MST topology
	* @return int value representing the minimum deviation
	* 
	*/	
	public int getMinDeviationMSTBandwidth() {
		return min_deviation_mst_bandwidth;
	}

	/**
	* This method sets the mimimum percentage deviation for bandwidth consumption 
	* during MST topology
	*@param min_deviation_mst_bandwidth int value to represent minimum deviation
	* 
	*/	
	public void setMinDeviationMSTBandwidth(int min_deviation_mst_bandwidth) {
		this.min_deviation_mst_bandwidth = min_deviation_mst_bandwidth;
	}

	/**
	* This method returns the maximum percentage deviation for bandwidth consumption 
	* during MST topology
	* @return int value representing the maximum deviation
	* 
	*/	
	public int getMaxDeviationMSTBandwidth() {
		return max_deviation_mst_bandwidth;
	}
	
	/**
	* This method sets the maximum percentage deviation for bandwidth consumption 
	* during MST topology
	*@param max_deviation_mst_bandwidth int value to represent maximum deviation
	* 
	*/	
	public void setMaxDeviationMSTBandwidth(int max_deviation_mst_bandwidth) {
		this.max_deviation_mst_bandwidth = max_deviation_mst_bandwidth;
	}

	/**
	* This method returns the mimimum percentage deviation for bandwidth consumption 
	* during RT topology
	* @return int value representing the minimum deviation
	* 
	*/	
	public int getMinDeviationRTBandwidth() {
		return min_deviation_rt_bandwidth;
	}

	/**
	* This method sets the mimimum percentage deviation for bandwidth consumption 
	* during RT topology
	*@param min_deviation_rt_bandwidth int value to represent minimum deviation
	* 
	*/
	public void setMinDeviationRTBandwidth(int min_deviation_rt_bandwidth) {
		this.min_deviation_rt_bandwidth = min_deviation_rt_bandwidth;
	}

	/**
	* This method returns the maximum percentage deviation for bandwidth consumption 
	* during RT topology
	* @return int value representing the maximum deviation
	* 
	*/	
	public int getMaxDeviationRTBandwidth() {
		return max_deviation_rt_bandwidth;
	}

	
	/**
	* This method sets the maximum percentage deviation for bandwidth consumption 
	* during RT topology
	*@param max_deviation_rt_bandwidth int value to represent maximum deviation
	* 
	*/	
	public void setMaxDeviationRTBandwidth(int max_deviation_rt_bandwidth) {
		this.max_deviation_rt_bandwidth = max_deviation_rt_bandwidth;
	}

	/**
	* This method returns the mimimum percentage deviation for time to write data 
	* during MST topology
	* @return int value representing the minimum deviation
	* 
	*/	
	public int getMinDeviationMSTWritingTime() {
		return min_deviation_mst_writing_time;
	}

	/**
	* This method sets the mimimum percentage deviation for time to write data 
	* during MST topology
	* @param min_deviation_mst_writing_time int value to represent minimum deviation
	*/	
	public void setMinDeviationMSTWritingTime(int min_deviation_mst_writing_time) {
		this.min_deviation_mst_writing_time = min_deviation_mst_writing_time;
	}

	/**
	* This method returns the maximum percentage deviation for time to write data 
	* during MST topology
	* @return int value representing the maximum deviation
	* 
	*/	
	public int getMaxDeviationMSTWritingTime() {
		return max_deviation_mst_writing_time;
	}

	/**
	* This method sets the maximum percentage deviation for time to write 
	* data during MST topology
	* @param max_deviation_mst_writing_time int value to represent maximum deviation
	*/	
	public void setMaxDeviationMSTWritingTime(int max_deviation_mst_writing_time) {
		this.max_deviation_mst_writing_time = max_deviation_mst_writing_time;
	}

	/**
	* This method returns the mimimum percentage deviation for time to write data 
	* during RT topology
	* @return int value representing the minimum deviation
	* 
	*/	
	public int getMinDeviationRTWritingTime() {
		return min_deviation_rt_writing_time;
	}

	
	/**
	* This method sets the mimimum percentage deviation for time to write data
	* during RT topology
	* @param min_deviation_rt_writing_time int value to represent minimum deviation
	*/	
	public void setMinDeviationRTWritingTime(int min_deviation_rt_writing_time) {
		this.min_deviation_rt_writing_time = min_deviation_rt_writing_time;
	}

	/**
	* This method returns the maximum percentage deviation for time to write data during RT topology
	* @return int value representing the maximum deviation
	* 
	*/	
	public int getMaxDeviationRTWritingTime() {
		return max_deviation_rt_writing_time;
	}

	/**
	* This method sets the maximum percentage deviation for time to write 
	* data during RT topology
	* @param max_deviation_rt_writing_time int value to represent maximum deviation
	*/	
	public void setMaxDeviationRTWritingTime(int max_deviation_rt_writing_time) {
		this.max_deviation_rt_writing_time = max_deviation_rt_writing_time;
	}

	
	
}
