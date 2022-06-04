import os

os.add_dll_directory("C:\\Program Files\\SciTools\\bin\\pc-win64")

import understand
import json

from metrics_calculator import calculate_metrics
from metrics_plotter import graph_concentration_control, graph_adaptive_metrics_report, graph_modifiability, \
    graph_concentration_of_control_stats, graph_locality, graph_dependency_degree, graph_adaptive_testabiltiy, \
    graph_coupling, graph_lack_of_cohesion



def evaluate(target, exemplar_name, is_class_based):
    data = generate_blank_data_template()
    calculate_metrics(data, target)
    print_data(data)

    graph_locality(data, exemplar_name)
    graph_concentration_control(data, exemplar_name)
    graph_dependency_degree(data, exemplar_name)
    graph_adaptive_testabiltiy(data, exemplar_name)

    if is_class_based:
        graph_coupling(data, exemplar_name)
        graph_lack_of_cohesion(data, exemplar_name)


def print_data(data):
    print(json.dumps(data, indent=3))


def generate_blank_data_template():
    return {
        "ConcentrationOfControl": {
            "Decentralization": {
                "Spread": [],
                "Statistics": {}
            }
        },
        "Elementarity": {
            "Tree": {
                "Function": [],
                "Class": []
            }
        },
        "Adaptive Metrics Report": []
    }
