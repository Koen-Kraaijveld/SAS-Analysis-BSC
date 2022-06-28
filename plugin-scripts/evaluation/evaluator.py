import json

from metrics_plotter import graph_concentration_impact, graph_locality, graph_dependency_degree, \
    graph_adaptive_complexity, graph_coupling, graph_lack_of_cohesion


def evaluate(data, exemplar_name, is_class_based):
    """
    Generates all the graphs showing the results from each metric.

    Args:
        data: the data containing the results from the metrics.
        exemplar_name: the name of the exemplar to be used in the graph's title and labels.
        is_class_based: true if the graphs will be generated for metrics that may only run on classes.

    Returns: the data containing the results from the metrics
    """

    graph_locality(data, exemplar_name)
    graph_concentration_impact(data, exemplar_name)
    graph_dependency_degree(data, exemplar_name)
    graph_adaptive_complexity(data, exemplar_name)

    if is_class_based:
        graph_coupling(data, exemplar_name)
        graph_lack_of_cohesion(data, exemplar_name)

    return data


def print_data(data):
    """
    Prints the data using JSON.

    Args:
        data: the data to be printed.
    """

    print(json.dumps(data, indent=3))


def save_data(data, file):
    """
    Saves the data using JSON.

    Args:
        data: the data to be saved.
        file: the file where the data will be saved to.
    """

    with open(file, 'w') as outfile:
        json.dump(data, outfile)


def read_data(file):
    """
    Reads data from a file using JSON.

    Args:
        file: the file to read from.

    Returns: the data from the file that has been read.
    """

    with open(file) as json_file:
        return json.load(json_file)


def generate_blank_data_template():
    """
    Generates an empty data template that will contain all the results from the metrics.

    Returns: an empty data template that will contain all the results from the metrics.
    """
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
