import os

os.add_dll_directory("C:\\Program Files\\SciTools\\bin\\pc-win64")

import understand
import json

from metrics_plotter import graph_concentration_impact, graph_adaptive_metrics_report, graph_modifiability, \
    graph_concentration_of_impact_stats, graph_locality, graph_dependency_degree, graph_adaptive_testabiltiy, \
    graph_coupling, graph_lack_of_cohesion


def evaluate(data, exemplar_name, is_class_based):
    graph_locality(data, exemplar_name)
    graph_concentration_impact(data, exemplar_name)
    graph_dependency_degree(data, exemplar_name)
    graph_adaptive_testabiltiy(data, exemplar_name)

    if is_class_based:
        graph_coupling(data, exemplar_name)
        graph_lack_of_cohesion(data, exemplar_name)

    return data


def print_data(data):
    print(json.dumps(data, indent=3))


def save_data(data, file):
    with open(file, 'w') as outfile:
        json.dump(data, outfile)


def read_data(file):
    with open(file) as json_file:
        return json.load(json_file)


def generate_blank_data_template():
    return {
        "ConcentrationOfImpact": {
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
