import os

os.add_dll_directory("C:\\Program Files\\SciTools\\bin\\pc-win64")

import understand
import json

print(understand.version())

db = understand.open(
    r"C:\Users\koenk\OneDrive\Documents\Vrije Universiteit\Year "
    r"3\SAS-Analysis-BSC\exemplars\ATRP\adasim-master\src\atrp.und")

ents = db.ents("class,interface")


def is_class(ent):
    type_split = ent.kind().name().split()
    return "Class" in type_split or "Interface" in type_split


def is_method(ent):
    type_split = ent.kind().name().split()
    return "Method" in type_split or "Function" in type_split


def get_func_from_name(func_name):
    for func in ents:
        if func.simplename() == func_name:
            return func


def get_class_from_name(class_name):
    for ent in ents:
        if ent.simplename() == class_name:
            ent_kind_split = ent.kind().name().split(" ")
            if "Class" in ent_kind_split:
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
            "children": [],
            "parent": None
        }

        direct_called_func.append(sub_called_func)

    for direct_ent in direct_called_func:
        for target_ent in target_ent_arr:
            if target_ent in direct_ent["called_funcs"]:
                direct_ent["children"].append(target_ent)

    redundant_trees_tracker = []
    for direct_ent_1 in direct_called_func:
        redundant_trees_tracker.append(direct_ent_1)
        for direct_ent_2 in direct_called_func:
            for i in range(0, len(direct_ent_2["children"])):
                if direct_ent_1["ent"] == direct_ent_2["children"][i]:
                    direct_ent_2["children"][i] = direct_ent_1
                    direct_ent_2["children"][i]["parent"] = direct_ent_2["ent"]

    final_tree_arr = []
    for dir_ent in direct_called_func:
        if dir_ent["parent"] is None and len(dir_ent["children"]) != 0:
            # print(dir_ent["parent"])
            # print(dir_ent["children"])
            # print(dir_ent["ent"].name())
            final_tree_arr.append(dir_ent)

    # flattened = []
    # for tree in final_tree_arr:
    #     flattened = flatten_tree(tree, flattened)
    #
    # for redundant in redundant_trees_tracker:
    #     print(redundant["ent"].name())
    #     if redundant not in flattened:
    #         final_tree_arr.append(redundant)

    # for redundant in redundant_trees_tracker:
    #     print(redundant["ent"].name())

    return final_tree_arr


def get_multiplied_spaces(amount):
    total = ""
    for i in range(0, amount):
        total += " "

    return total


def flatten_tree(tree, flattened_arr):
    flattened_arr.append(tree)
    for ent_child in tree["children"]:
        # print(ent_child["ent"].name())
        flatten_tree(ent_child, flattened_arr)

    return flattened_arr


def print_elementarity_tree(elementarity_tree, depth):
    if elementarity_tree["ent"] is not None:
        print((get_multiplied_spaces(depth * 3)) + "ENTITY: " + elementarity_tree["ent"].name())

    for ent_child in elementarity_tree["children"]:
        print_elementarity_tree(ent_child, depth + 1)


def print_cfg_node(node):
    if node.line_begin() is not None:
        print(f"{node.kind()}({node.line_begin()})")
    else:
        print(node.kind())


def find_closing_conditional_in_cfg(cfg, node_open_idx):
    total_lines = 0
    max_final_line = -1
    counter = 1
    nodes = cfg.nodes()
    node_start = nodes[node_open_idx]
    node_end = node_start.end_node()

    if node_end is None:
        return None

    node_close_idx = node_open_idx + 1
    while counter > 0:
        node = nodes[node_close_idx]
        if node.kind() == node_start.kind():
            counter += 1
        elif node.kind() == node_end.kind():
            counter -= 1
        elif node.line_begin() is not None:
            total_lines += abs(node.line_begin() - node.line_end()) + 1
            if node.line_end() > max_final_line:
                max_final_line = node.line_end()

        node_close_idx += 1

    # print(total_lines)
    # print(f"({node_start.line_begin()} - {max_final_line})")
    return {
        "open_idx": node_open_idx,
        "close_idx": node_close_idx,
        "children": []
    }


def collect_all_conditionals(cfg):
    conditionals = []
    for i in range(0, len(cfg.nodes())):
        closed_conditional = find_closing_conditional_in_cfg(cfg, i)
        if closed_conditional is not None:
            conditionals.append(closed_conditional)

    return conditionals


# ent = get_func_from_name("checkStatus")
# ent_cfg = ent.control_flow_graph()


# for i in range(0, len(ent_cfg.nodes())):
#     print(f"{i} {ent_cfg.nodes()[i].kind()}")

# print()
# print(find_closing_conditional_in_cfg(ent_cfg, 56)["line_interval"])

def construct_conditional_tree(conditionals):
    for i in range(0, len(conditionals) - 1):
        conditional_1 = conditionals[i]

        for conditional_2 in conditionals[i:]:
            open_1 = conditional_1["open_idx"]
            close_1 = conditional_1["close_idx"]

            open_2 = conditional_2["open_idx"]
            close_2 = conditional_2["close_idx"]

            if open_1 < open_2 and close_1 > close_2:
                conditional_1["children"].append(conditional_2)

    return conditionals


def print_conditional_tree(cfg):
    for node in cfg:
        print("abc")
        if len(node["children"]) > 0:
            for node_child in node["children"]:
                print_conditional_tree(node_child)

    # for conditional_1 in conditionals:
    #     for conditional_2 in conditionals:
    #         open_1 = conditional_1["open_idx"]
    #         close_1 = conditional_1["close_idx"]
    #
    #         open_2 = conditional_2["open_idx"]
    #         close_2 = conditional_2["close_idx"]
    #
    #         if open_1 < open_2 and close_1 < close_2:
    #             conditional_2["children"].append(conditional_1)
    #         elif open_2 < open_1 and close_2 < close_1:
    #             conditional_1["children"].append(conditional_2)

    # return conditionals


target_ents = get_ent_array_from_names([
    "checkStatus",
    "flying",
    "returnToHome",
    "landing",
    "landed",
    "safeLanding",
    "shutDown",
    "takeOff"
])


def parse_elementarity_tree(cfg):
    parsed_tree = []
    # for node in cfg.nodes():
    #     parsed = {
    #         "node": node,
    #         "children_info": {
    #             "children": [],
    #             "parent": node
    #         }
    #     }
    #
    #     for node_child in node.children():
    #         parsed["children"].append(node_child)
    #         parsed["children"]
    #
    #     parsed_tree.append(parsed)
    #
    # for parsed in parsed_tree:
    #     print(parsed["node"].kind())


# parse_elementarity_tree(ent_cfg)


def collect_all_cfg_adaptive_strategies(cfg, parent_ent):
    for node in cfg.nodes():
        if node.line_begin() is not None:
            lexer = db.lookup("DroneBusinessObject.java")[0].lexer()
            lexemes = lexer.lexemes(node.line_begin(), node.line_end())
            for lexeme in lexemes:
                print(lexeme.ref())
                if lexeme.ent() in target_ents:
                    lexeme_temp = lexeme
                    while lexeme_temp.previous() is not None:
                        lexeme_temp = lexeme_temp.previous()
                        if lexeme_temp.ent() in target_ents:
                            print(lexeme.ent().name() + " -> " + lexeme_temp.ent().name() + " " + str(
                                lexeme.line_begin()))
                            break

                    print()


# collect_all_cfg_adaptive_strategies(ent_cfg, target_ents[0])

# conditionals = collect_all_conditionals(ent_cfg)
# conditonal_tree = construct_conditional_tree(conditionals)
# print(conditonal_tree)
#
# print_conditional_tree(conditonal_tree)
#
#
# def print_conditional_content(cfg, node_idx):
#     node = cfg.nodes()[node_idx]
#     lexer = db.lookup("DroneBusinessObject.java")[0].lexer()
#     # lexeme = lexer.lexeme(node.line_begin(), node.column_begin())
#     # print(lexeme.text())
#
#     lexemes = lexer.lexemes(node.line_begin(), node.line_end())
#     for lexeme in lexemes:
#         print(lexeme.text(), end="")

# print()
# print_conditional_content(ent_cfg, 1)


# def print_control_constructs(nodes):
#     file = db.lookup("DroneBusinessObject.java")[0]
#
#     for node in nodes:
#         if node.end_node() is not None:
#             line_begin = node.line_begin()
#             line_end = node.end_node().children()[0].line_begin()
#             if line_end is None:
#                 lexeme = file.lexer().lexeme(node.line_begin(), node.column_begin()).previous()
#                 for i in range(0, 10):
#                     print(lexeme.text())
#                     lexeme = lexeme.next()
#             # print(f"{node.kind()}({line_begin}-{line_end})")


# print_control_constructs(ent_cfg.control_flow_graph().nodes())


# def print_conditionals(nodes):
#     depth = 0
#
#     for node in nodes:
#         if node.kind() == "if":
#             print((" " * depth * 3) + node.kind() + "(" + str(node.line_begin()) + ")")
#             depth += 1
#         elif node.kind() == "end-if":
#             depth -= 1
#             print((" " * depth * 3) + node.kind())
#         else:
#             print((" " * depth * 3) + node.kind())

# if node.kind() == "end":
#     return
#
# for node_child in node.children():
#     print_conditionals(node_child, depth)

# if node.kind() == "if":
#     end_node_line = node.end_node().children()[0].line_begin()
#     print((" " * depth * 4) + str(node.line_begin()) + " - " + str(end_node_line))

# if end_node_line is None:
#     print_conditional(node.end_node().children()[0], depth + 1)

# ent_cfg = get_func_from_name("checkStatus").control_flow_graph()
# print_conditionals(ent_cfg.nodes())

# elementarity_tree = construct_elementarity_tree(target_ents)
#
# for ent in elementarity_tree:
#     print_elementarity_tree(ent, 0)
#     print()

# summed = sum_metrics_from_ent_array(collected_names, "CountLineCode")
#


# initial_func = get_func_from_name("checkStatus")
# print("INITIAL FUNCTION: " + initial_func.simplename())
#
# collected_names = collect_called_func_names(initial_func, [])

target_classes = get_ent_array_from_names([
    "AbstractRoutingAlgorithm",
    "AdaptiveRoutingAlgorithm",
    "QLearningRoutingAlgorithm",
    "TrafficLookaheadRoutingAlgorithm",
    "LookaheadShortestPathRoutingAlgorithm",
    "RoutingAlgorithm",
    "TrafficMain"
])


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


# grouped = group_together_classes_and_methods(target_classes)
# for class_ in grouped["classes"]:
#     print(class_.name())
#
# print()
#
# for method_ in grouped["methods"]:
#     print(method_.name())


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


def print_elementarity_tree_class(tree, depth):
    if tree["ent"] is not None:
        print((get_multiplied_spaces(depth * 3)) + "ENTITY: " + tree["ent"].name())

    if tree["children"] is not None:
        for ent_child in tree["children"]:
            print_elementarity_tree_class(ent_child, depth + 1)


# def print_elementarity_tree(elementarity_tree, depth):
#     if elementarity_tree["ent"] is not None:
#         print((get_multiplied_spaces(depth * 3)) + "ENTITY: " + elementarity_tree["ent"].name())
#
#     for ent_child in elementarity_tree["children"]:
#         print_elementarity_tree(ent_child, depth + 1)


# for direct_ent_2 in direct_called_func:
#     for i in range(0, len(direct_ent_2["children"])):
#         if direct_ent_1["ent"] == direct_ent_2["children"][i]:
#             direct_ent_2["children"][i] = direct_ent_1
#             direct_ent_2["children"][i]["parent"] = direct_ent_2["ent"]

elementarity_tree_class = construct_elementarity_tree_class(target_classes)


def find_parent_class(child_class):
    dependencies = child_class.depends()
    for target in sorted(dependencies.keys(), key=lambda target: target.longname()):
        for ref in dependencies[target]:
            ref_kind_split = ref.kind().longname().split(" ")
            if "Extend" in ref_kind_split and not ref.file().library():
                return ref


initial_class = get_class_from_name("TrafficLookaheadRoutingAlgorithm")


def calculate_class_control_radius(initial_class):
    class_funcs = collect_defined_class_funcs(initial_class)

    parent_class = find_parent_class(initial_class)
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

# class_func_called_arr = calculate_class_control_radius(initial_class)
# print(sum_metrics_from_ent_array(class_func_called_arr, "CountStmtExe"))

# for ent in collected_names:
#     collect_called_func_names(ent, collected_names)
# for name in collected_names:
#     print(name.longname() + " " + str(name.metric(("CountLineCode",))["CountLineCode"]))
