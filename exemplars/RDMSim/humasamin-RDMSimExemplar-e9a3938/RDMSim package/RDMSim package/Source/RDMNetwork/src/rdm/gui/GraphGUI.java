package rdm.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import rdm.management.NetworkManagment;
import rdm.simulation.SimulationProperties;


/**
 * 
 * @author Huma Samin
 * @version 1.0
 * 
 * Class to design Graphical User Interface for the simulation results
 *
 */
public class GraphGUI extends AnchorPane{
	
	  public static XYChart.Series<String, Number> series_bc,threshold_bc;
	  private XYChart.Series<String, Number> series_al,threshold_al;
	  private XYChart.Series<String, Number> series_ttw,threshold_ttw;
	  
	  final NumberAxis xAxis = new NumberAxis();
	  final NumberAxis yAxis = new NumberAxis();
	  
	 
	  private String results_log;
	  
	 public static int timestep;
	  //new code
	  final int WINDOW_SIZE = 15;
	 public static ScheduledExecutorService scheduledExecutorService;
	 
     public static XYChart.Series<String, Number> series; 
	 public static XYChart.Series<String, Number> al_series; 
	 public static XYChart.Series<String, Number> ttw_series; 
	 double datapoint[];
	 double th_ttw; 
	 
     /**
      * Constructor of the class to initialize Graphical User Interface 
      */
	public GraphGUI() {
		
		// TODO Auto-generated constructor stub
		timestep=0;
		datapoint=new double[3];
		th_ttw=0;
			
		
		series_bc = new XYChart.Series();
		series_al = new XYChart.Series();
		series_ttw = new XYChart.Series();
		threshold_bc = new XYChart.Series<>();
		threshold_al = new XYChart.Series<>();
		threshold_ttw = new XYChart.Series<>();
		
		results_log="";
		
		

		loadGUI();
		
		
		
		
		
		
	}
	
	/**
	 * Method used to load the results Graphs
	 */
	public void loadGUI()
	{
		//readResultsLog();
		
		//defining the axes
        final CategoryAxis xAxis = new CategoryAxis(); 
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time Steps");
        xAxis.setAnimated(false); 
        yAxis.setLabel("Bandwidth Consumption ");
        yAxis.setAnimated(false); 

        
        final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Minimization of Cost");
        lineChart.setAnimated(false); // disable animations
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		lineChart.setLayoutX((screenSize.getWidth()*2)/100);
		lineChart.setLayoutY(((screenSize.getHeight()-50)*3)/100);
		
        lineChart.setPrefHeight(((screenSize.getHeight()-50)*53)/100);
		lineChart.setPrefWidth(((screenSize.getWidth())*32)/100);
       
        lineChart.setCreateSymbols(false);
        

        //defining a series to display data
        series = new XYChart.Series<>();
        series.setName("Bandwidth");
        threshold_bc.setName("Threshold");
        threshold_al.setName("Threshold");
        threshold_ttw.setName("Threshold");

        // add series to chart
        lineChart.getData().add(series);
        lineChart.getData().add(threshold_bc);
        
        ////////////////////////////////////Second Line Chart
      //defining the axes
        final CategoryAxis xAxis1 = new CategoryAxis(); // we are gonna plot against time
        final NumberAxis yAxis1 = new NumberAxis();
        xAxis1.setLabel("Time Steps");
        xAxis1.setAnimated(false); // axis animations are removed
        yAxis1.setLabel("Active Network Links ");
        yAxis1.setAnimated(false); // axis animations are removed

        //creating the line chart with two axis created above
        final LineChart<String, Number> lineChart1 = new LineChart<>(xAxis1, yAxis1);
        lineChart1.setTitle("Maximization of Reliability");
        lineChart1.setAnimated(false); // disable animations
       
        lineChart1.setLayoutX((screenSize.getWidth()*33)/100);
		lineChart1.setLayoutY(((screenSize.getHeight()-50)*3)/100);
		
        lineChart1.setPrefHeight(((screenSize.getHeight()-50)*53)/100);
		lineChart1.setPrefWidth(((screenSize.getWidth())*32)/100);
        
        
      

        //defining a series to display data
        al_series = new XYChart.Series<>();
        al_series.setName("Active Links");

        // add series to chart
        lineChart1.getData().add(al_series);
        lineChart1.getData().add(threshold_al);
        
        lineChart1.setCreateSymbols(false);

        //Adding Chart Time to Write
         final CategoryAxis xAxis2 = new CategoryAxis(); // we are gonna plot against time
         final NumberAxis yAxis2 = new NumberAxis();
        
        xAxis2.setLabel("Time Steps");
        xAxis2.setAnimated(false); // axis animations are removed
        yAxis2.setLabel("Time to Write Data");
        yAxis2.setAnimated(false); // axis animations are removed

        //creating the line chart with two axis created above
        final LineChart<String, Number> lineChart2 = new LineChart<>(xAxis2, yAxis2);
        lineChart2.setTitle("Maximization of Performance");
        lineChart2.setAnimated(false); // disable animations
       
        lineChart2.setLayoutX((screenSize.getWidth()*63)/100);
		lineChart2.setLayoutY(((screenSize.getHeight()-50)*3)/100);
		
        lineChart2.setPrefHeight(((screenSize.getHeight()-50)*53)/100);
		lineChart2.setPrefWidth(((screenSize.getWidth())*32)/100);
        
        
        
        
      
        lineChart2.setCreateSymbols(false);

        //defining a series to display data
        ttw_series = new XYChart.Series<>();
        ttw_series.setName("Time to Write Data");
        
        // add series to chart
        lineChart2.getData().add(ttw_series);
        lineChart2.getData().add(threshold_ttw);
       
        
/////////////////////////////////////////////////////////////////////////////////
        Label lblresults=new Label("Results:");
        lblresults.setLayoutX(80);
        lblresults.setLayoutY(400);
        this.getChildren().addAll(lblresults);
        TextArea textArea = new TextArea("");
        textArea.setLayoutX(80);
        textArea.setLayoutY(420);
        textArea.setText(results_log);
        textArea.setEditable(false);
        this.getChildren().addAll(textArea);
        textArea.setPrefSize(1000, 200);


        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
       
       
        scheduledExecutorService.scheduleAtFixedRate(() -> {
           
    	 
    	  int simulation_run=getSimulationRun();
    	  double dp[]= getSeriesDataPoint(simulation_run);
    	//  double al=200;
    	   
    	  // System.out.println(bc+"  "+al);

            // Update the chart
          Platform.runLater(() -> {
              
              
                series.getData().add(new XYChart.Data<>(timestep+"", dp[0]));
                al_series.getData().add(new XYChart.Data<>(timestep+"", dp[1]));
                ttw_series.getData().add(new XYChart.Data<>(timestep+"", dp[2]));
               
                 
                threshold_ttw.getData().add(new XYChart.Data<>(timestep+"", NetworkManagment.network_properties.m.getThresholdTimeToWrite())); 
                threshold_bc.getData().add(new XYChart.Data<>(timestep+"", NetworkManagment.network_properties.m.getThresholdBandwidthConsumption()));    
                threshold_al.getData().add(new XYChart.Data<>(timestep+"", NetworkManagment.network_properties.m.getThresholdActiveLinks()));    

                textArea.setText(results_log);
                
                if (series.getData().size()>WINDOW_SIZE)
                {
                    series.getData().remove(0);
                    al_series.getData().remove(0);
                    ttw_series.getData().remove(0);
                   
                    threshold_ttw.getData().remove(0);
                    	
                    
                    threshold_bc.getData().remove(0);
                    threshold_al.getData().remove(0);
                   // threshold_al.getData().remove(threshold_al.getData().size()-1);
                    
                    
                }
               
            });
          
        }, 0, 1, TimeUnit.SECONDS);
       
       
      		
		
		this.getChildren().add(lineChart);
		this.getChildren().add(lineChart1);
		this.getChildren().add(lineChart2);
		
		
     
			     
		      	
	}
	
	/**
	 * Method to get the simulation time step value for the results
	 * @return int value representing the simulation time step
	 */
	public int getSimulationRun()
	{
		return timestep++;
	}
	
	/**
	 * Method to set the simulation time step for the results log
	 * @param step int value representing the simulation time step
	 */
	public static void setTimestep(int step)
	{
		timestep=step;
	}
	
	
	/**
	 * Method to define a series data point for a particular time step for the Graph loaded with 
	 * the monitorable metrics' values from the results log
	 * @param timestep int value representing the simulation time step
	 * @return double array represent the data points for the monitorables
	 */
	public double[] getSeriesDataPoint(int timestep) {
		
			JSONParser parser1 = new JSONParser();
			double bc=0;
			
		
		try {
				String path =new File("config_log_files/log.json").getAbsolutePath();
			
				FileReader fr=new FileReader(path);
				Object obj1 = parser1.parse(fr);

				JSONObject jsonObject = (JSONObject) obj1;
				Set m=jsonObject.keySet();
				
			
				JSONObject obj=(JSONObject) jsonObject.get(timestep+"");
				
				
				results_log=results_log+"Timestep: "+(timestep+1)+", Selected_Topology: "+obj.get("selected_topology")+", Active_Links: "+obj.get("active_links")+", Bandwidth_Consumption: "+obj.get("badwidth_consumption")+", Time_to_Write_Data: "+obj.get("time_to_write")+"\n";
				
				
				//populating series with data
				datapoint[0]=(double)obj.get("badwidth_consumption");
				String x=(String)obj.get("active_links").toString();
				datapoint[1]=Integer.parseInt(x);				
				datapoint[2]=(double)obj.get("time_to_write");
				fr.close();
			
	}
	catch(FileNotFoundException ex)
	{
		ex.printStackTrace();
	}
	catch(IOException ioex)
	{
		ioex.printStackTrace();
	}
	catch(ParseException ex)
	{
		ex.printStackTrace();
	}

		return datapoint;
		
	}
	
	
	
	
	/**
	 * Method to read the results log from log.json file
	 */
	public void readResultsLog()
	{
		
		JSONParser parser1 = new JSONParser();
		
		try {
			String path =new File("config_log_files/log.json").getAbsolutePath();
			
			FileReader fr=new FileReader(path);
			Object obj1 = parser1.parse(fr);

			JSONObject jsonObject = (JSONObject) obj1;
			Set m=jsonObject.keySet();
			System.out.println(m.size());
			
			for(int i=0;i<m.size();i++)
			{
				JSONObject obj=(JSONObject) jsonObject.get(i+"");
				System.out.println("timestep: "+i);
				System.out.println("Selected Topology: "+obj.get("selected_topology"));
				System.out.println("Active Links: "+obj.get("active_links"));
				System.out.println("Bandwidth Consumption: "+obj.get("badwidth_consumption"));
				System.out.println("Time to Write Data"+obj.get("time_to_write")+"\n");
				
				results_log=results_log+"Timestep: "+i+", Selected_Topology: "+obj.get("selected_topology")+", Active_Links: "+obj.get("active_links")+", Bandwidth_Consumption: "+obj.get("badwidth_consumption")+", Time_to_Write_Data: "+obj.get("time_to_write")+"\n";
				
				
				//populating series with data
				 series_bc.getData().add(new XYChart.Data(i,obj.get("badwidth_consumption")));
				 series_al.getData().add(new XYChart.Data(i,obj.get("active_links")));
				 series_ttw.getData().add(new XYChart.Data(i,obj.get("time_to_write")));
				
				 if(threshold_ttw.getData().size()<=20)
				 {
				 threshold_bc.getData().add(new XYChart.Data(i+"",NetworkManagment.network_properties.m.getThresholdBandwidthConsumption()));
				 threshold_al.getData().add(new XYChart.Data(i+"",NetworkManagment.network_properties.m.getThresholdActiveLinks()));
				 threshold_ttw.getData().add(new XYChart.Data(i+"",NetworkManagment.network_properties.m.getThresholdTimeToWrite()));
				 }
			}
			
	}
	catch(FileNotFoundException ex)
	{
		ex.printStackTrace();
	}
	catch(IOException ioex)
	{
		ioex.printStackTrace();
	}
	catch(ParseException ex)
	{
		ex.printStackTrace();
	}

}
	
	

	

}
