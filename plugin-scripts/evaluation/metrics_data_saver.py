import json
from metrics_calculator import calculate_metrics
from evaluator import generate_blank_data_template
from metrics_calculator import get_metric_from_name


def get_ent_array_from_names(ents, ent_name_arr):
    target_ents = []
    for target_ent in ent_name_arr:
        for ent in ents:
            if target_ent == ent.simplename() and ent.simplename() not in target_ents:
                target_ents.append(ent)

    return target_ents


def calculate_and_save_metric_data(ents, ent_names, file):
    target_ents = get_ent_array_from_names(ents, ent_names)
    blank_data_template = generate_blank_data_template()
    calculate_metrics(blank_data_template, target_ents)
    print(blank_data_template)
    save_data(blank_data_template, file)


def save_data(data, file):
    with open(file, 'w') as outfile:
        json.dump(data, outfile)
