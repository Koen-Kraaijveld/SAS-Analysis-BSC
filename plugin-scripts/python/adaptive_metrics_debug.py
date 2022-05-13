import os

os.add_dll_directory("C:\\Program Files\\SciTools\\bin")

import understand
import json

print(understand.version())

db = understand.open(
    "D:\OneDrive\Documents\Vrije Universiteit\Bachelor "
    "Project\exemplars\Dragonfly\Dragonfly-master\DragonFly-Project\src\dragonfly.und")

ents = db.ents("function,method,procedure")


def get_func_from_name(func_name):
    for func in ents:
        if func.simplename() == func_name:
            return func


def get_class_from_name(class_name):
    for ent in ents:
        if ent.simplename() == class_name:
            return ent


def get_ent_array_from_names(ent_name_arr):
    target_ents = []
    for target_ent in ent_name_arr:
        for ent in ents:
            if target_ent == ent.simplename() and ent.simplename() not in target_ents:
                target_ents.append(ent)

    return target_ents


def get_metric_from_name(ent, name):
    metric = ent.metric((name,))
    return metric[name]


def sum_metrics_from_ent_array(ent_array, metric_name):
    total = 0
    for ent in ent_array:
        metric_val = get_metric_from_name(ent, metric_name)
        if metric_val is not None:
            total += metric_val

    return total


def get_ref_info(ref):
    return "{} ({}) {} -> ({})".format(ref.file().name(), ref.line(), ref.kind().longname(), ref.isforward())


def collect_defined_class_funcs(class_name):
    defined_class_funcs = []
    if refs := class_name.refs():
        for ref in refs:
            kind = ref.kind().longname()
            ref_kind_split = kind.split()
            if ("Define" in ref_kind_split) and (ref.ent() not in defined_class_funcs):
                print(ref.ent())
                defined_class_funcs.append(ref.ent())

    return defined_class_funcs


def collect_called_func_names(func, called_func_names_arr):
    if refs := func.refs():
        for ref in refs:
            kind = ref.kind().longname()
            ref_kind_split = kind.split()
            ent_kind_split = ref.ent().kind().longname().split()

            if ("Call" in ref_kind_split) and (
                    "Method" in ent_kind_split or "Function" in ent_kind_split) and ref.ent() not in called_func_names_arr:
                called_func_names_arr.append(ref.ent())

    return called_func_names_arr


def construct_elementarity_tree(target_ent_arr):
    direct_called_func = []
    for target_ent in target_ent_arr:
        sub_called_func = {
            "ent": target_ent,
            "called_funcs": collect_called_func_names(target_ent, []),
            "children": []
        }

        direct_called_func.append(sub_called_func)

    for direct_ent in direct_called_func:
        for target_ent in target_ent_arr:
            if target_ent in direct_ent["called_funcs"]:
                direct_ent["children"].append(target_ent)

    for direct_ent_1 in direct_called_func:
        for direct_ent_2 in direct_called_func:
            for i in range(0, len(direct_ent_2["children"])):
                if direct_ent_1["ent"] == direct_ent_2["children"][i]:
                    # for direct_ent_temp in direct_called_func:
                    #     if direct_ent_1 == direct_ent_temp:
                    #         print(True)
                    direct_ent_2["children"][i] = direct_ent_1

    return direct_called_func


def get_multiplied_spaces(amount):
    total = ""
    for i in range(0, amount):
        total += " "

    return total


def print_elementarity_tree(elementarity_tree, depth):
    if elementarity_tree["ent"] is not None:
        print((get_multiplied_spaces(depth * 3)) + "ENTITY: " + elementarity_tree["ent"].name())

    for ent_child in elementarity_tree["children"]:
        print_elementarity_tree(ent_child, depth + 1)









target_ents = get_ent_array_from_names([
    "flying",
    "returnToHome",
    "landing",
    "checkStatus",
    "flyingRight",
    "flyingLeft",
    "getCurrentPositionI",
    "takeOff"
])

elementarity_tree = construct_elementarity_tree(target_ents)

print_elementarity_tree(elementarity_tree[4], 0)


# summed = sum_metrics_from_ent_array(collected_names, "CountLineCode")
#
# func = get_func_from_name("checkStatus")
# print(func.ib())

# initial_func = get_func_from_name("checkStatus")
# print("INITIAL FUNCTION: " + initial_func.simplename())
#
# collected_names = collect_called_func_names(initial_func, [])


# initial_class = get_class_from_name("DroneBusinessObject")
# print("CLASS: " + initial_class.name())
#
# class_funcs = collect_defined_class_funcs(initial_class)
#
# class_func_called_arr = []
# for class_func in class_funcs:
#     class_func_called_arr.append(class_func)
#     called_funcs = collect_called_func_names(class_func, class_func_called_arr)
#     for called_func in called_funcs:
#         collect_called_func_names(called_func, class_func_called_arr)
#
# for class_func in class_func_called_arr:
#     print(class_func.name() + " -> " + str(class_func.metric(("CountLineCode",))["CountLineCode"]))
#
# print(len(class_func_called_arr))


# for ent in collected_names:
#     collect_called_func_names(ent, collected_names)
# for name in collected_names:
#     print(name.longname() + " " + str(name.metric(("CountLineCode",))["CountLineCode"]))
