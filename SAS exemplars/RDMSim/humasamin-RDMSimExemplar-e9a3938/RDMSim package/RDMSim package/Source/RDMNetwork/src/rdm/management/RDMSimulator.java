package rdm.management;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.concurrent.ThreadLocalRandom;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rdm.gui.GraphGUI;
import rdm.network.Monitorables;
import rdm.network.TopologyList;

/**
 * 
 * @author Huma Samin
 * @version 1.0
 * 
 * Main class to execute the RDM Simulator
 *
 */
public class RDMSimulator extends Application{
	
	/**
	 * main method to start the simulation execution
	 * @param args to hold VM arguments
	 */
	public static void main(String args[])
	{
		//Step1:Create an object of NetworkManagement to load the simulation and network properties from configuration file
		NetworkManagment nm=new NetworkManagment();
		
		//Step: 2 Instantiate the probe and effector
		Probe probe=nm.getProbe();
		Effector effector=nm.getEffector();
		
		
	     for(int timestep=0;timestep<nm.simulation_properties.getSimulationRuns();timestep++) {
			
			//Monitor
			Monitorables m=probe.getMonitorables();
			
			//Analyse and Plan						
			if (probe.getActiveLinks()<m.getThresholdActiveLinks()&&probe.getBandwidthConsumption()>m.getThresholdBandwidthConsumption()||probe.getTimeToWrite()>m.getThresholdTimeToWrite())
			{
				int random_topology=(int)ThreadLocalRandom.current().nextDouble(0,TopologyList.topologies.size());
				effector.setNetworkTopology(timestep,TopologyList.topologies.get(random_topology).getTopologyName());

				
				
			}
			else if(probe.getActiveLinks()>m.getThresholdActiveLinks()&&probe.getBandwidthConsumption()<m.getThresholdBandwidthConsumption()||probe.getTimeToWrite()<m.getThresholdTimeToWrite())
			{
				int random_topology=(int)ThreadLocalRandom.current().nextDouble(0,TopologyList.topologies.size());
				//execute
				effector.setNetworkTopology(timestep,TopologyList.topologies.get(random_topology).getTopologyName());

				
				
			}
			else
			{
				int random_topology=(int)ThreadLocalRandom.current().nextDouble(0,TopologyList.topologies.size());

				effector.setNetworkTopology(timestep,TopologyList.topologies.get(random_topology).getTopologyName());
				
			}
				
		
	}
	
	     displayResults(args);
	     System.exit(0);
		

	}

	/**
	 * Method to launch the Graphical User Interface for the simulator
	 * @param args to hold VM arguments
	 */
	public static void displayResults(String args[])
	{
		launch(args);
	}
	
	
	/**
	 * Method to display the Graphical User Interface
	 */
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		
		
		
		GraphGUI g=new GraphGUI();
		Scene scene=new Scene(g,1000,500);
		stage.setScene(scene);	
		stage.setTitle("RDMSim");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		stage.setHeight(screenSize.getHeight()-50);
		stage.setWidth(screenSize.getWidth());
		//stage.setMaximized(true);
		stage.show();
	}
	
	
	/**
	 * Method to stop the Executor service
	 */
	@Override
    public void stop() throws Exception {
        super.stop();
        GraphGUI.scheduledExecutorService.shutdownNow();
    }
	
}
