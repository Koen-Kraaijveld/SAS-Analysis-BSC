package rdm.simulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import rdm.management.NetworkManagment;
import rdm.network.Topology;
import rdm.network.TopologyList;
import rdm.validation.RDMValidation;


/**
 * 
 * @author Huma Samin
 * @version 1.0
 * 
 * Class to set the properties for simulation like simulation runs, 
 * uncertainty scenario for simulation
 *
 */
public class SimulationProperties {
	
	private int simulation_runs;	
	public static UncertaintyScenario current_scenario;
	private int simulation_timestep;
	
	
	/**
	* Constructor to initialize the current uncertainty scenario
	* 
	*/
	public SimulationProperties()
	{
		current_scenario=new UncertaintyScenario();
	}
	
	/**
	* This method returns the number of simulation runs for the experiments
	* @return int value representing the simulation runs
	* 
	*/
	public int getSimulationRuns() {
		return simulation_runs;
	}

	/**
	* This method sets the number of simulation runs for the experiment
	* @param  simulation_runs int value representing the simulation runs
	*
	*/
	public void setSimulationRuns(int simulation_runs) {
		this.simulation_runs = simulation_runs;
	}

	/**
	* This method returns the uncertainty scenario for the experiment
	* @return UncertaintyScenario Object representing the current uncertainty scenario
	* 
	*/
	public UncertaintyScenario getUncertaintyScenario() {
		return current_scenario;
	}

	/**
	* This method sets the uncertainty scenario for the experiment
	* @param  uncertainty_scenario object representing the uncertainty scenario
	*
	*/
	public void setUncertaintyScenario(UncertaintyScenario uncertainty_scenario) {
		this.current_scenario = uncertainty_scenario;
	}
	
	/**
	* This method returns the current simulation time step value
	* @return int value representing the current simulation time step
	* 
	*/
	public int getSimulationTimestep() {
		return simulation_timestep;
	}

	/**
	* This method sets the value of current simulation time step
	* @param  simulation_timestep int value representing the simulation time step
	*
	*/
	public void setSimulationTimestep(int simulation_timestep) {
		this.simulation_timestep = simulation_timestep;
	}
	
	
	/**
	* This method loads the simulation settings for the experiment from the configuration file
	*
	*/	
	public void loadSimulationSettings() 
	{
		
		JSONParser parser = new JSONParser();
		
		try {
			 String path = new File("config_log_files/configuration.json").getAbsolutePath();
			
			Object obj1 = parser.parse(new FileReader(path));//parsing the JSON string inside the file that we created earlier.

			JSONObject jsonObject = (JSONObject) obj1;
			
			int timesteps = Integer.parseInt(jsonObject.get("time_steps").toString());
			simulation_runs=timesteps;
			
			//load the execution scenario
			int curr_scenario = Integer.parseInt(jsonObject.get("current_scenario").toString());
			current_scenario.setCurrentScenario(curr_scenario);
			
			//Scenario Validation
			try
			{
				if(current_scenario.getCurrentScenario()<0||current_scenario.getCurrentScenario()>6)
				{
					throw new RDMValidation("invalid scenario");
				}
				
			}catch(RDMValidation va)
			{
				va.validateScenarioNumber();
			}
			
			if(curr_scenario==0)
			{
				Topology current=new Topology();
				current.setTopologyName("mst");
				NetworkManagment.network_properties.setCurrentTopology(current);
			}
			
			if(curr_scenario==1)
			{
				Topology current=new Topology();
				current.setTopologyName("mst");
				NetworkManagment.network_properties.setCurrentTopology(current);
			}
			
			if(curr_scenario==2)
			{
				Topology current=new Topology();
				current.setTopologyName("rt");
				NetworkManagment.network_properties.setCurrentTopology(current);
			}
			
			if(curr_scenario==3)
			{
				int random_topology=(int)ThreadLocalRandom.current().nextDouble(0,TopologyList.topologies.size());
				
				Topology current=new Topology();
				current.setTopologyName(TopologyList.topologies.get(random_topology).getTopologyName());
				NetworkManagment.network_properties.setCurrentTopology(current);
			}
			
			if(curr_scenario==4)
			{
				Topology current=new Topology();
				current.setTopologyName("mst");
				NetworkManagment.network_properties.setCurrentTopology(current);
			}
			
			if(curr_scenario==5)
			{
				Topology current=new Topology();
				current.setTopologyName("rt");
				NetworkManagment.network_properties.setCurrentTopology(current);
			}
			
			if(curr_scenario==6)
			{
				int random_topology=(int)ThreadLocalRandom.current().nextDouble(0,TopologyList.topologies.size());
				
				Topology current=new Topology();
				current.setTopologyName(TopologyList.topologies.get(random_topology).getTopologyName());
				NetworkManagment.network_properties.setCurrentTopology(current);
			}
			
			
			
			//MST
			JSONArray deviationmstlinks = (JSONArray) jsonObject.get("deviation_scenario_"+current_scenario.getCurrentScenario()+"_mst_links");
			
			int mindevmstlinks=(int) Integer.parseInt(deviationmstlinks.get(0).toString());
			int maxdevmstlinks=(int) Integer.parseInt(deviationmstlinks.get(1).toString());
			current_scenario.setMinDeviationMSTLinks(mindevmstlinks);
			current_scenario.setMaxDeviationMSTLinks(maxdevmstlinks);
			
			JSONArray deviationmstbandwidth = (JSONArray) jsonObject.get("deviation_scenario_"+current_scenario.getCurrentScenario()+"_mst_bandwidth_consumption");
			int mindevmstbandwidth=(int) Integer.parseInt(deviationmstbandwidth.get(0).toString());
			int maxdevmstbandwidth=(int) Integer.parseInt(deviationmstbandwidth.get(1).toString());
			current_scenario.setMinDeviationMSTBandwidth(mindevmstbandwidth);
			current_scenario.setMaxDeviationMSTBandwidth(maxdevmstbandwidth);
			
			JSONArray deviationmstttw = (JSONArray) jsonObject.get("deviation_scenario_"+current_scenario.getCurrentScenario()+"_mst_writing_time");
			int mindevmstttw=(int) Integer.parseInt(deviationmstttw.get(0).toString());
			int maxdevmstttw=(int) Integer.parseInt(deviationmstttw.get(1).toString());
			current_scenario.setMinDeviationMSTWritingTime(mindevmstttw);
			current_scenario.setMaxDeviationMSTWritingTime(maxdevmstttw);
			
			//RT
			JSONArray deviationrtlinks = (JSONArray) jsonObject.get("deviation_scenario_"+current_scenario.getCurrentScenario()+"_rt_links");
			
			int mindevrtlinks=(int) Integer.parseInt(deviationrtlinks.get(0).toString());
			int maxdevrtlinks=(int) Integer.parseInt(deviationrtlinks.get(1).toString());
			current_scenario.setMinDeviationRTLinks(mindevrtlinks);
			current_scenario.setMaxDeviationRTLinks(maxdevrtlinks);
			
			JSONArray deviationrtbandwidth = (JSONArray) jsonObject.get("deviation_scenario_"+current_scenario.getCurrentScenario()+"_rt_bandwidth_consumption");
			int mindevrtbandwidth=(int) Integer.parseInt(deviationrtbandwidth.get(0).toString());
			int maxdevrtbandwidth=(int) Integer.parseInt(deviationrtbandwidth.get(1).toString());
			current_scenario.setMinDeviationRTBandwidth(mindevrtbandwidth);
			current_scenario.setMaxDeviationRTBandwidth(maxdevrtbandwidth);
			
			JSONArray deviationrtttw = (JSONArray) jsonObject.get("deviation_scenario_"+current_scenario.getCurrentScenario()+"_rt_writing_time");
			int mindevrtttw=(int) Integer.parseInt(deviationrtttw.get(0).toString());
			int maxdevrtttw=(int) Integer.parseInt(deviationrtttw.get(1).toString());
			current_scenario.setMinDeviationRTWritingTime(mindevrtttw);
			current_scenario.setMaxDeviationRTWritingTime(maxdevrtttw);
			
			
			//validate deviation ranges
			try
			{
				if(mindevmstlinks<0||mindevmstlinks>100||mindevmstlinks>maxdevmstlinks)
				{
					throw new RDMValidation("invalid_dev_range_links_mst");
				}
				else if(maxdevmstlinks<0||maxdevmstlinks>100||mindevmstlinks>maxdevmstlinks)
				{
					throw new RDMValidation("invalid_dev_range_links_mst");
				}
				if(mindevmstbandwidth<0||mindevmstbandwidth>100||mindevmstbandwidth>maxdevmstbandwidth)
				{
					throw new RDMValidation("invalid_dev_range_bandwidth_mst");
				}
				else if(maxdevmstbandwidth<0||maxdevmstbandwidth>100||mindevmstbandwidth>maxdevmstbandwidth)
				{
					throw new RDMValidation("invalid_dev_range_bandwidth_mst");
				}
				if(mindevmstttw<0||mindevmstttw>100||mindevmstttw>maxdevmstttw)
				{
					throw new RDMValidation("invalid_dev_range_ttw_mst");
				}
				else if(maxdevmstttw<0||maxdevmstttw>100||mindevmstttw>maxdevmstttw)
				{
					throw new RDMValidation("invalid_dev_range_ttw_mst");
				}
				
				if(mindevrtlinks<0||mindevrtlinks>100||mindevrtlinks>maxdevrtlinks)
				{
					throw new RDMValidation("invalid_dev_range_links_rt");
				}
				else if(maxdevrtlinks<0||maxdevrtlinks>100||mindevrtlinks>maxdevrtlinks)
				{
					throw new RDMValidation("invalid_dev_range_links_rt");
				}
				if(mindevrtbandwidth<0||mindevrtbandwidth>100||mindevrtbandwidth>maxdevrtbandwidth)
				{
					throw new RDMValidation("invalid_dev_range_bandwidth_rt");
				}
				else if(maxdevrtbandwidth<0||maxdevrtbandwidth>100||mindevrtbandwidth>maxdevrtbandwidth)
				{
					throw new RDMValidation("invalid_dev_range_bandwidth_rt");
				}
				if(mindevrtttw<0||mindevrtttw>100||mindevrtttw>maxdevrtttw)
				{
					throw new RDMValidation("invalid_dev_range_ttw_rt");
				}
				else if(maxdevrtttw<0||maxdevrtttw>100||mindevrtttw>maxdevrtttw)
				{
					throw new RDMValidation("invalid_dev_range_ttw_rt");
				}
			}
			catch(RDMValidation va)
			{
				va.validateConfigurationSetup();
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
