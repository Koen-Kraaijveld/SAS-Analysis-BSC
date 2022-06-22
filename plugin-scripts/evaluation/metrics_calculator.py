import os

os.add_dll_directory("C:\\Program Files\\SciTools\\bin\\pc-win64")

import understand
import statistics


def calculate_metrics(data_template, ents):
    if isinstance(ents, list) and len(ents) > 1:
        calculate_decentralization(data_template, ents)
        calculate_elementarity_dependencies(data_template, ents)
        for ent in ents:
            generate_adaptive_metrics_report(data_template, ent)
    else:
        generate_adaptive_metrics_report(data_template, ents)



def generate_adaptive_metrics_report(data_template, ents):
    ent_info = ents.name()
    locality = calculate_locality(ents)
    maintainability = calculate_maintainability(ents)
    dependencies = {
        "Dependencies": calculate_dependencies(data_template, ents)
    }

    report = {
        "Ent": ent_info
    }

    report.update(locality)
    report.update(maintainability)
    report.update(dependencies)

    data_template["Adaptive Metrics Report"].append(
        report
    )


def calculate_locality(ent):
    return {
        "Locality": {
            "ControlRadius": {
                "Local": calculate_local_control_radius(ent),
                "Global": calculate_global_control_radius(ent)
            }
        }
    }


def calculate_maintainability(ent):
    testability = calculate_testability(ent)
    modifiability = calculate_modifiability(ent)

    if modifiability is not None:
        return {
            "Maintainability": testability | modifiability
        }
    else:
        return {
            "Maintainability": testability
        }


def calculate_testability(ent):
    if is_method(ent):
        return {
            "Complexity": {
                "Cyclomatic": get_metric_from_name(ent, "Cyclomatic"),
                "Strict Cyclomatic": get_metric_from_name(ent, "CyclomaticStrict")
            }
        }
    elif is_class(ent):
        return {
            "Complexity": {
                "Cyclomatic": get_metric_from_name(ent, "SumCyclomatic"),
                "Strict Cyclomatic": get_metric_from_name(ent, "SumCyclomaticStrict")
            }
        }

    return None


def calculate_modifiability(ent):
    if is_class(ent):
        return {
            "Modifiability": {
                "Coupling": get_metric_from_name(ent, "CountClassCoupled"),
                "LackOfCohesion": get_metric_from_name(ent, "PercentLackOfCohesion")
            }
        }

    return None


def calculate_dependencies(data_template, ent):
    elementarity_tree_func = data_template["Elementarity"]["Tree"]["Function"]
    elementarity_tree_class = data_template["Elementarity"]["Tree"]["Class"]

    for func_tree in elementarity_tree_func:
        for func in func_tree:
            if ent in func["Ent"]:
                print(func)
                return {
                    "Depends on": func["DependsOn"],
                    "Depended on by": func["DependedOnBy"]
                }

    for class_tree in elementarity_tree_class:
        for class_ in class_tree:
            if str(ent).split(".")[0] == str(class_["Ent"]).split(".")[0]:
                print(class_)
                return {
                    "Depends on": class_["DependsOn"],
                    "Depended on by": class_["DependedOnBy"]
                }

    return {
        "Depends on": 0,
        "Depended on by": 0
    }


def calculate_decentralization(data_template, ents):
    ent_control_arr = []
    ent_global_control_arr = []

    for ent in ents:
        global_control = calculate_global_control_radius(ent)
        ent_control_arr.append({
            "ent": ent,
            "control": global_control
        })
        ent_global_control_arr.append(global_control)

    ent_control_arr = sorted(ent_control_arr, key=lambda d: d['control'], reverse=True)
    summed_ent_global_control = sum(ent_global_control_arr)

    for ent in ent_control_arr:
        data_template["ConcentrationOfControl"]["Decentralization"]["Spread"].append({
            "Ent": ent["ent"].name(),
            "Relative": ent["control"] / summed_ent_global_control,
            "Absolute": ent["control"]
        })

    calculate_decentralization_statistics(data_template, ent_control_arr, ent_global_control_arr)


def calculate_decentralization_statistics(data_template, ent_control_arr, ent_global_control_arr):
    statistics_arr = data_template["ConcentrationOfControl"]["Decentralization"]["Statistics"]
    spread_arr = data_template["ConcentrationOfControl"]["Decentralization"]["Spread"]
    statistics_arr["Maximum"] = spread_arr[0]
    statistics_arr["Minimum"] = spread_arr[len(ent_control_arr) - 1]
    statistics_arr["Variance"] = round(statistics.variance(ent_global_control_arr), 2)
    statistics_arr["StandardDeviation"] = round(statistics.stdev(ent_global_control_arr), 2)


def calculate_elementarity_dependencies(data_template, target_ent_arr):
    grouped_ents = group_together_classes_and_methods(target_ent_arr)
    elementarity_tree_method = construct_elementarity_tree(grouped_ents["methods"])
    elementarity_tree_class = construct_elementarity_tree_class(grouped_ents["classes"])

    if len(elementarity_tree_method["final"]) > 0:
        elementarity_tree_method_arr = []
        for tree in elementarity_tree_method["final"]:
            elementarity_info = calculate_elementarity_info(tree)
            for tree_info in elementarity_info:
                elementarity_tree_method_arr.append({
                    "Ent": tree_info["ent"].name(),
                    "DependsOn": tree_info["Depends on"],
                    "DependedOnBy": tree_info["Depended by"]
                })
            data_template["Elementarity"]["Tree"]["Function"].append(elementarity_tree_method_arr)

    if len(elementarity_tree_class["final"]) > 0:
        for tree in elementarity_tree_class["final"]:
            elementarity_tree_class_arr = []
            elementarity_info = calculate_elementarity_info(tree)
            for tree_info in elementarity_info:
                elementarity_tree_class_arr.append({
                    "Ent": tree_info["ent"].name(),
                    "DependsOn": tree_info["Depends on"],
                    "DependedOnBy": tree_info["Depended by"]
                })
            data_template["Elementarity"]["Tree"]["Class"].append(elementarity_tree_class_arr)


def calculate_elementarity_info(elementarity_tree):
    elementarity_tree_info = count_dependencies(elementarity_tree, 0, [])
    return sorted(elementarity_tree_info, key=lambda d: d['Depends on'])


def construct_elementarity_tree(target_ent_arr):
    direct_called_func = []
    for target_ent in target_ent_arr:
        sub_called_func = {
            "ent": target_ent,
            "called_funcs": collect_called_func_names(target_ent, []),
            "children": [],
            "parent": None,
            "is_in_final_tree": True
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
                    direct_ent_1["is_in_final_tree"] = False
                    direct_ent_2["children"][i] = direct_ent_1
                    direct_ent_2["children"][i]["parent"] = direct_ent_2["ent"]

    final_tree_arr = {
        "final": [],
        "misc": []
    }
    for tree in direct_called_func:
        if tree["is_in_final_tree"]:
            if len(tree["children"]) <= 0:
                final_tree_arr["misc"].append(tree)
            else:
                final_tree_arr["final"].append(tree)

    return final_tree_arr


def construct_elementarity_tree_class(ents):
    classes = []
    for ent in ents:

        temp_ent_array = []
        for temp_ent in ents:
            temp_ent_array.append(temp_ent.ref().ent())

        ent_dependsby_arr = []
        for dependency in ent.dependsby().keys():
            if dependency in ents:
                ent_dependsby_arr.append({
                    "ent": dependency.ref().ent(),
                    "children": None,
                    "parent": None,
                    "is_in_final_tree": True,
                })

        classes.append({
            "ent": ent.ref().ent(),
            "children": ent_dependsby_arr,
            "parent": None,
            "is_in_final_tree": True,
        })

    for class_1 in classes:
        for class_2 in classes:
            for i in range(0, len(class_2["children"])):
                if class_1["ent"] == class_2["children"][i]["ent"]:
                    class_1["is_in_final_tree"] = False
                    class_2["children"][i] = class_1
                    class_2["children"][i]["parent"] = class_2["ent"]

    final_tree_arr = {
        "final": [],
        "misc": []
    }
    for tree in classes:
        if tree["is_in_final_tree"]:
            if len(tree["children"]) <= 0:
                final_tree_arr["misc"].append(tree)
            else:
                final_tree_arr["final"].append(tree)

    return final_tree_arr


def count_dependencies(tree, depends_on_count, dependencies_info):
    if len(tree["children"]) > 0:
        depended_by_count = calculate_total_nodes(tree, 0)
    else:
        depended_by_count = 0

    dependency_info = {
        "ent": tree["ent"],
        "Depends on": depends_on_count,
        "Depended by": depended_by_count
    }

    depends_on_count += 1

    for ent_child in tree["children"]:
        count_dependencies(ent_child, depends_on_count, dependencies_info)

    dependencies_info.append(dependency_info)

    return dependencies_info


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


def calculate_local_control_radius(ent):
    starting_local_control = get_metric_from_name(ent, "CountStmtExe")

    if not is_class(ent):
        first_level_called_ent_names = collect_called_func_names(ent, [])
        return starting_local_control + sum_metrics_from_ent_array(first_level_called_ent_names, "CountStmtExe")
    else:
        class_funcs = collect_defined_class_funcs(ent)

        parent_class = find_parent_class(ent)
        if parent_class is not None:
            parent_class_funcs = collect_defined_class_funcs(parent_class.ent())
            for func in parent_class_funcs:
                if "Private" not in func.kindname().split(" "):
                    class_funcs.append(func)

        class_func_called_arr = []
        for class_func in class_funcs:
            class_func_called_arr.append(class_func)
            collect_called_func_names(class_func, class_func_called_arr)

        return starting_local_control + sum_metrics_from_ent_array(class_func_called_arr, "CountStmtExe")


def calculate_global_control_radius(ent):
    metric = ent.metric(("CountStmtExe",))
    print(ent.name() + " " + ent.kindname() + " " + str(metric))
    starting_global_control = get_metric_from_name(ent, "CountStmtExe")
    total_called_ent_names = collect_total_called_ent_names(ent)
    return starting_global_control + sum_metrics_from_ent_array(total_called_ent_names, "CountStmtExe")


def sum(array):
    total = 0
    for n in array:
        total += n

    return total


def collect_defined_class_funcs(class_name):
    defined_class_funcs = []

    if refs := class_name.refs():
        for ref in refs:
            kind = ref.kind().longname()
            ref_kind_split = kind.split()
            if ("Define" in ref_kind_split) and (ref.ent() not in defined_class_funcs):
                defined_class_funcs.append(ref.ent())

    return defined_class_funcs


def collect_called_func_names(ent, called_func_names_arr):
    if refs := ent.refs():
        for ref in refs:
            kind = ref.kind().longname()
            ref_kind_split = kind.split()
            ent_kind_split = ref.ent().kind().longname().split()

            if ("Call" in ref_kind_split) and (
                    "Method" in ent_kind_split or "Function" in ent_kind_split) and ref.ent() not in called_func_names_arr:
                called_func_names_arr.append(ref.ent())

    return called_func_names_arr


def collect_total_called_ent_names(ent):
    if not is_class(ent):

        total_called_ent_names = collect_called_func_names(ent, [])
        for ent in total_called_ent_names:
            collect_called_func_names(ent, total_called_ent_names)

        return total_called_ent_names

    else:
        class_funcs = collect_defined_class_funcs(ent)

        parent_class = find_parent_class(ent)
        if parent_class is not None:
            parent_class_funcs = collect_defined_class_funcs(parent_class.ent())
            for func in parent_class_funcs:
                if "Private" not in func.kindname().split(" "):
                    class_funcs.append(func)

        class_func_called_arr = []
        for class_func in class_funcs:
            class_func_called_arr.append(class_func)
            called_funcs = collect_called_func_names(class_func, class_func_called_arr)
            for called_func in called_funcs:
                collect_called_func_names(called_func, class_func_called_arr)

        return class_func_called_arr


def count_dependencies(tree, depends_on_count, dependencies_info):
    if len(tree["children"]) > 0:
        depended_by_count = calculate_total_nodes(tree, 0)
    else:
        depended_by_count = 0

    dependency_info = {
        "ent": tree["ent"],
        "Depends on": depends_on_count,
        "Depended by": depended_by_count
    }

    depends_on_count += 1

    for ent_child in tree["children"]:
        count_dependencies(ent_child, depends_on_count, dependencies_info)

    dependencies_info.append(dependency_info)

    return dependencies_info


def find_parent_class(child_class):
    dependencies = child_class.depends()
    for target in sorted(dependencies.keys(), key=lambda target: target.longname()):
        for ref in dependencies[target]:
            ref_kind_split = ref.kind().longname().split(" ")
            if "Extend" in ref_kind_split and not ref.file().library():
                return ref


def calculate_total_nodes(tree, count):
    count = len(tree["children"])
    for child in tree["children"]:
        count += calculate_total_nodes(child, count)

    return count


def group_together_classes_and_methods(ents):
    classes = []
    methods = []

    for ent in ents:
        if is_class(ent):
            classes.append(ent)
        elif is_method(ent):

            methods.append(ent)

    return {
        "classes": classes,
        "methods": methods
    }


def is_class(ent):
    type_split = ent.kind().name().split()
    return "Class" in type_split or "Interface" in type_split


def is_method(ent):
    type_split = ent.kind().name().split()
    return "Method" in type_split or "Function" in type_split
