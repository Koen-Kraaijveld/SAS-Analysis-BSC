import os

os.add_dll_directory("C:\\Program Files\\SciTools\\bin\\pc-win64")

# import understand
#
# from evaluator import evaluate, save_data, read_data
# from metrics_calculator import calculate_metrics
# from metrics_data_saver import calculate_and_save_metric_data
#
# db = understand.open(r"C:\Users\koenk\OneDrive\Documents\Vrije Universiteit\Year "
#                      r"3\SAS-Analysis-BSC\exemplars\TRAPP\TRAPP-master\app\trapp.und")
#
# ents = db.ents("class")
# ent_names = ["Strategy", "AvoidOverLoadedStreets", "LoadBalancing", "TunePlanningResolution"]
#
# trapp_data = calculate_and_save_metric_data(ents, ent_names, "./data/trapp.json")

from evaluator import read_data, evaluate

trapp_raw_data = read_data("./data/trapp.json")
evaluated_data = evaluate(trapp_raw_data, "TRAPP", True)
