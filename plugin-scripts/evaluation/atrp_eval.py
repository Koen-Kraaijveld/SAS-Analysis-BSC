import os

os.add_dll_directory("C:\\Program Files\\SciTools\\bin\\pc-win64")

import understand
import json

from evaluator import evaluate, save_data

db = understand.open(
    r"C:\Users\koenk\OneDrive\Documents\Vrije Universiteit\Year "
    r"3\SAS-Analysis-BSC\exemplars\ATRP\adasim-master\src\atrp.und")

class_ents = db.ents("class,interface")
func_ents = db.ents("function,method,procedure")
combined_ents = db.ents("class,interface,function,method,procedure")


def get_ent_from_name(ent_pool, ent_name):
    for ent in ent_pool:
        if ent.simplename() == ent_name:
            return ent


def get_ent_array_from_names(ent_pool, ent_name_arr):
    target_ents = []
    for target_ent in ent_name_arr:
        for ent in ent_pool:
            if target_ent == ent.simplename() and ent.simplename() not in target_ents:
                target_ents.append(ent)

    return target_ents





adaptive_strategies = get_ent_array_from_names(class_ents, [
    "AbstractRoutingAlgorithm",
    "AdaptiveRoutingAlgorithm",
    "LookaheadShortestPathRoutingAlgorithm",
    "QLearningRoutingAlgorithm",
    "TrafficLookaheadRoutingAlgorithm",
    "AlwaysRecomputeRoutingAlgorithm"
])

data = evaluate(adaptive_strategies, "ATRP", True)
save_data(data, "./data/atrp.json")
