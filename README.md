Introduction
------------
The Adaptive Strategies Metric Suite (ASMS) is a plugin 
implemented for the static code analysis tool Understand, 
developed by Scientific Toolworks. It realizes a 
metric suite designed to measure the adaptive strategies of self-adaptive systems. Running the plugin 
from Understand will generate the corresponding results 
computed by these metrics in an Interactive Report (IReport), 
which is a feature supported by Understand to display the 
results. 

Requirements
------------
The plugin must be run through Scientific Toolwork’s static 
code analysis tool [Understand](https://licensing.scitools.com/download), which requires a valid license 
code in order to access. Alternatively, a 30-day trial 
version can also be downloaded.

Installation
------------
The following steps can be followed to ensure that the 
plugin will be installed and recognized by Understand:

1. Clone this repository by running the following command:
   1. ```git clone https://github.com/Koen-Kraaijveld/ASMS.git```
2. Navigate to /plugin-scripts.
3. Copy/cut the ```adaptive_strategies_metric_suite.upy``` file.
4. Navigate to the directory where Understand is installed.
   1. The default directory location is C:/Program Files/SciTools.
5. From there, navigate to /conf/plugin/User/IReport.
6. Paste the file in this directory.

The plugin should now be correctly installed and accessible 
to Understand. To verify, open Understand and open a project. 
Right-click on any architecture or entity and hover the 
cursor over the Interactive Reports option. A new option 
titled ‘Adaptive Strategies Metric Suite’ should appear and 
clicking on it will execute the plugin.

In order to demonstrate the full capabilities of the plugin, 
it is best to run it in its optimal environment. This can be 
accomplished by reproducing the results from the thesis, 
specifically by running the plugin on the ATRP and TRAPP 
exemplars. Follow the steps below to see how this is done.

Reproducing the Results
------------
After successfully installing the plugin, carry out the 
following steps in order to reproduce the results from 
when the plugin was run on ATRP and TRAPP.

### ATRP ###
1. Open Understand.
2. Go to File → Open → Project.
3. Navigate to the directory that was cloned from this repository.
4. From there, navigate to ```/exemplars/ATRP/adasim-master/src```.
5. Open ```atrp.und``` by clicking on it.
6. Follow these steps to run the plugin on the adaptive strategies architecture.
   1. Open the Architecture Browser window by going to Architectures → Browse Architectures. There should already be an architecture there named ‘ATRP Adaptive Strategies’.
   2. Right click on it and hover the cursor over the Interactive Reports option.
   3. The ‘Adaptive Strategies Metric Suite’ option should appear. Click on it and the plugin will execute. See the screenshot below for further clarity.
    <br /> &nbsp;    
      ![atrp_arch_execute_instructions](/img/atrp-arch.png) <br />
    <br />
   4. A UI box should appear in the lower right (by default) titled ‘IReport - Adaptive Strategies Metric Suite’. This displays all the results from the execution of the plugin.

    
7. Follow these steps to run the plugin on an arbitrary entity.
   1. Navigate to the Project Browser by going to View → Project Browser.
   2. Click on a file of your choice.
   3. Right click on a class or function, hover the cursor over the Interactive Reports option.
   4. Click on the ‘Adaptive Strategies Metric Suite’ option and the plugin should execute.


### TRAPP ###
1. Open Understand.
2. Go to File → Open → Project.
3. Navigate to the directory that was cloned from this repository.
4. From there, navigate to ```/exemplars/TRAPP/TRAPP-master/app```.
5. Open ```trapp.und``` by clicking on it.
6. Follow these steps to run the plugin on the adaptive strategies architecture.
   1. Open the Architecture Browser window by going to Architectures → Browse Architectures. There should already be an architecture there named 'TRAPP Adaptive Strategies’.
   2. Right click on it and hover the cursor over the Interactive Reports option.
   3. The ‘Adaptive Strategies Metric Suite’ option should appear. Click on it and the plugin will execute. The screenshot belows provides additional clarity.
   <br /> &nbsp; 
      ![trapp-arch](/img/trapp-arch.png) <br />
   <br />
   
   5. A UI box should appear in the lower right (by default) titled ‘IReport - Adaptive Strategies Metric Suite’. This displays all the results from the execution of the plugin.

Running the Evaluation Scripts
------------
In order to the run the evaluation and see the graphs 
presented in the thesis, following the steps below:
1. Navigate to the directory that was cloned from this repository.
2. From there, navigate to /plugin-scripts/evaluation.
3. Run ```atrp_eval.py``` and ```trapp_eval.py``` to see the results for ATRP and TRAPP, respectively.
4. The graphs should render in order of appearance.


Exemplars
------------
1. [Dragonfly](https://github.com/DragonflyDrone/Dragonfly)
2. [ATRP](https://github.com/brunyuriy/adasim)
3. [UNDERSEA](https://drops.dagstuhl.de/opus/volltexte/2017/7141/)
4. [Platooning LEGOs](https://zenodo.org/record/4604167)
5. [TRAPP](https://github.com/iliasger/TRAPP)