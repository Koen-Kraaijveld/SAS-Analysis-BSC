package rdm.validation;

import javax.swing.JOptionPane;
import rdm.network.NetworkProperties;

/**
 * 
 * @author Huma Samin
 * @version 1.0
 * 
 * Class to define Validation Checks for the simulator
 *
 */
public class RDMValidation extends Exception{
	
	private String exception_type;
	
	/**
	 * Constructor to initialize the exception
	 * @param exception_type String value to represent the exception type
	 */
	
	public RDMValidation(String exception_type)
	{
		this.exception_type=exception_type;
		validateConfigurationSetup();
		
	}
	
	/**
	* This method applies validation checks for the configuration parameters
	* 
	*/
	public void validateConfigurationSetup()
	{
		
		if(exception_type.equals("invalid_mirror_number"))
		{
			validateMirrorNumber();
		}
		if(exception_type.equals("invalid_link_threshold"))
		{
			validateThresholdActiveLinks();
		}
		else if(exception_type.equals("invalid_bandwidth_threshold"))
		{
			validateThresholdBandwidthConsumption();
			
		}
		else if(exception_type.equals("invalid_writing_time_threshold"))
		{
			validateThresholdTimeToWrite();
		}
		else if(exception_type.equals("mst_active_links"))
		{
			validateMSTActiveLinks();
		}
		else if(exception_type.equals("rt_active_links"))
		{
			validateRTActiveLinks();
		}
		else if(exception_type.equals("invalid_scenario"))
		{
			validateScenarioNumber();
		}
		else if(exception_type.equals("invalid_dev_range_links_mst"))
		{
			validateDeviationRangeLinksMST();
		}
		else if(exception_type.equals("invalid_dev_range_bandwidth_mst"))
		{
			validateDeviationRangeBandwidthMST();
		}
		else if(exception_type.equals("invalid_dev_range_ttw_mst"))
		{
			validateDeviationRangeTTWMST();
		}
		else if(exception_type.equals("invalid_dev_range_links_rt"))
		{
			validateDeviationRangeLinksRT();
		}
		else if(exception_type.equals("invalid_dev_range_bandwidth_rt"))
		{
			validateDeviationRangeBandwidthRT();
		}
		else if(exception_type.equals("invalid_dev_range_ttw_rt"))
		{
			validateDeviationRangeTTWRT();
		}
		else if(exception_type.equals("invalid_range_links"))
		{
			validateActiveLinks();
		}
	}
	
	/**
	* Method to validate the number of mirrors 
	*/
	public void validateMirrorNumber()
	{
			
		//JOptionPane.showMessageDialog(null, "The active links threshold should be between 0 and "+NetworkProperties.number_of_links);
		JOptionPane.showMessageDialog(null, "The number of mirrors should be between 5 to 30000.");
		System.exit(0);	
	}
	
	/**
	* Method to validate the threshold value for active links
	*/	
	public void validateThresholdActiveLinks()
	{
			
		//JOptionPane.showMessageDialog(null, "The active links threshold should be between 0 and "+NetworkProperties.number_of_links);
		JOptionPane.showMessageDialog(null, "The active links threshold should be between 0 and 100 percent of total links");
		System.exit(0);	
	}
	
	/**
	* Method to validate the threshold value bandwidth consumption
	*/	
	public void validateThresholdBandwidthConsumption()
	{
		JOptionPane.showMessageDialog(null, "The bandwidth consumption threshold should be between 0 and 100 percent of total bandwidth");
		System.exit(0);	
		
	}
	
	/**
	* Method to validate the threshold value for time to write data
	*/	
	public void validateThresholdTimeToWrite()
	{
		JOptionPane.showMessageDialog(null, "The Time to Write threshold should be between 0 and 100 perecnt of total time to write data");
		System.exit(0);	
		
	}
	
	/**
	* Method to validate the active links range for MST topology
	*/	
	public void validateMSTActiveLinks()
	{
		JOptionPane.showMessageDialog(null, "The range for active links for MST topology should be between 0 and "+NetworkProperties.number_of_links);
		System.exit(0);	
	}
	
	/**
	* Method to validate the active links range for RT topology
	*/	
	public void validateRTActiveLinks()
	{
		JOptionPane.showMessageDialog(null, "The range for active links for RT topology should be between 0 and "+NetworkProperties.number_of_links);
		System.exit(0);	
	}
	
	/**
	* Method to validate the active links range
	*/	
	public void validateActiveLinks()
	{
		JOptionPane.showMessageDialog(null, "The range for Active Links should be between 0 to 100 percent and minimum Active Links should be less than maximum Active Links");
		System.exit(0);
	}
	
	
	/**
	* Method to validate the uncertainty scenario selection
	*/	
	public void validateScenarioNumber()
	{

		JOptionPane.showMessageDialog(null, "The current_scenario should be between 0 and 6");
		System.exit(0);	
		
	}
	
	/**
	* Method to validate the deviation range for active links during MST topology
	*/	
	public void validateDeviationRangeLinksMST() {
		JOptionPane.showMessageDialog(null, "The deviation range for Active Links should be between 0 to 100 percent and minimum deviation should be less than maximum deviation");
		System.exit(0);
	}
	
	/**
	* Method to validate the deviation range for bandwidth consumption during MST topology
	*/	
	public void validateDeviationRangeBandwidthMST() {
		JOptionPane.showMessageDialog(null, "The deviation range for Bandwidth should be between 0 to 100 percent and minimum deviation should be less than maximum deviation");
		System.exit(0);
	}
	
	/**
	* Method to validate the deviation range for time to write data during MST topology
	*/	
	public void validateDeviationRangeTTWMST() {
		JOptionPane.showMessageDialog(null, "The deviation range for Writing Time should be between 0 to 100 percent and minimum deviation should be less than maximum deviation");
		System.exit(0);
	}
	
	/**
	* Method to validate the deviation range for active links during RT topology
	*/	
	public void validateDeviationRangeLinksRT() {
		JOptionPane.showMessageDialog(null, "The deviation range for Active Links should be between 0 to 100 percent and minimum deviation should be less than maximum deviation");
		System.exit(0);
	}
	
	
	/**
	* Method to validate the deviation range for bandwidth consumption during RT topology
	*/	
	public void validateDeviationRangeBandwidthRT() {
		JOptionPane.showMessageDialog(null, "The deviation range for Bandwidth should be between 0 to 100 percent and minimum deviation should be less than maximum deviation");
		System.exit(0);
	}
	
	/**
	* Method to validate the deviation range for time to write data during RT topology
	*/	
	public void validateDeviationRangeTTWRT() {
		JOptionPane.showMessageDialog(null, "The deviation range for Writing Time should be between 0 to 100 percent and minimum deviation should be less than maximum deviation");
		System.exit(0);	
	}


}
