import math

from matplotlib import pyplot as plt
from matplotlib import ticker
import numpy as np
params = {'legend.fontsize': 'x-large',
          'figure.figsize': (15, 5),
         'axes.labelsize': 'x-large',
         'axes.titlesize':'x-large',
         'xtick.labelsize':'x-large',
         'ytick.labelsize':'x-large'}
plt.rcParams.update(params)


def graph_concentration_of_impact_stats(data):
    pass


def graph_locality(data, exemplar_name):
    report = data["Adaptive Metrics Report"]
    ent_names = collect_dict_values_from_key(report, "Ent")
    locality = collect_dict_values_from_key(report, "Locality")
    impact_radius = collect_dict_values_from_key(locality, "ImpactRadius")

    fig, ax = plt.subplots(1, 1)
    cmap = plt.get_cmap("Paired")(np.arange(len(ent_names)))

    plot_double_bar_chart(ax, ent_names, impact_radius, "Local and Global Impact Radius of each adaptive strategy in " + exemplar_name,
                          "Number of reachable executable statements", True, cmap[0:2])

    plt.subplots_adjust(top=0.97, bottom=0.3)
    plt.show()


def graph_concentration_impact(data, exemplar_name):
    spread = data["ConcentrationOfImpact"]["Decentralization"]["Spread"]
    if len(spread) == 0:
        return

    ent_names = collect_dict_values_from_key(spread, "Ent")
    relative_values = collect_dict_values_from_key(spread, "Relative")
    absolute_values = collect_dict_values_from_key(spread, "Absolute")
    generate_coc_pie_chart_and_bar_chart(ent_names, relative_values, absolute_values, 100, 1, exemplar_name)


def generate_relative_coc_pie_chart(labels, relative_values, absolute_values, text_limit, autopct_limit, exemplar_name):
    new_labels = []
    for i in range(0, len(relative_values)):
        if relative_values[i] * 100 > text_limit or (
                relative_values[i] * 100 < autopct_limit and relative_values[i] > 1 / 1000):
            new_labels.append(str(round(relative_values[i] * 100, 3)))
        else:
            new_labels.append('')

    fig = plt.figure()
    cmap = plt.get_cmap("Dark2")(np.arange(len(labels)))

    fig.suptitle("Relative Global Impact Radius of each adaptive strategy in " + exemplar_name + " (%)")
    (ax1, ax2) = fig.subplots(1, 2)
    ax2.axis("equal")
    pie = ax2.pie(relative_values, labels=new_labels, startangle=0, autopct=generate_autopct(autopct_limit),
                  colors=cmap, wedgeprops={"edgecolor": "w", 'linewidth': 1})

    ax1.axis("off")
    ax1.legend(pie[0], labels, loc="center left")

    # sf2.suptitle("Absolute Global Impact Radius of each adaptive strategy")
    # ax3 = sf2.subplots(1, 1)
    # ax3.barh(labels, absolute_values, color=cmap)
    # ax3.yaxis.set_ticklabels([])
    # ax3.set_xlabel("Number of reachable executable statements across the entire system")

    plt.subplots_adjust(left=0.3, right=0.7, top=0.9, bottom=0.15)
    plt.show()


def generate_coc_pie_chart_and_bar_chart(labels, relative_values, absolute_values, text_limit, autopct_limit, exemplar_name):
    new_labels = []
    for i in range(0, len(relative_values)):
        if relative_values[i] * 100 > text_limit or (
                relative_values[i] * 100 < autopct_limit and relative_values[i] > 1 / 1000):
            new_labels.append(str(round(relative_values[i] * 100, 3)))
        else:
            new_labels.append('')

    fig = plt.figure()
    (sf1, sf2) = fig.subfigures(2, 1)
    cmap = plt.get_cmap("Dark2")(np.arange(len(labels)))

    sf1.suptitle("Relative Global Impact Radius of each adaptive strategy in " + exemplar_name + " (%)")
    (ax1, ax2) = sf1.subplots(1, 2)
    ax2.axis("equal")
    pie = ax2.pie(relative_values, labels=new_labels, startangle=0, autopct=generate_autopct(autopct_limit),
                  colors=cmap, wedgeprops={"edgecolor": "w", 'linewidth': 1})

    ax1.axis("off")
    ax1.legend(pie[0], labels, loc="center left")

    sf2.suptitle("Absolute Global Impact Radius of each adaptive strategy")
    ax3 = sf2.subplots(1, 1)
    ax3.barh(labels, absolute_values, color=cmap)
    ax3.yaxis.set_ticklabels([])
    ax3.set_xlabel("Number of reachable executable statements across the entire system")

    plt.subplots_adjust(left=0.3, right=0.7, top=0.9, bottom=0.15)
    plt.show()


def graph_dependency_degree(data, exemplar_name):
    report = data["Adaptive Metrics Report"]
    ent_names = collect_dict_values_from_key(report, "Ent")
    dependencies = collect_dict_values_from_key(report, "Dependencies")
    print(dependencies)

    fig, ax = plt.subplots(1, 1)
    fig.subplots_adjust(hspace=0.5)

    cmap = plt.get_cmap("Paired")(np.arange(len(ent_names)))

    plot_double_bar_chart(ax, ent_names, dependencies, "Dependency Degree of each adaptive strategy in " + exemplar_name,
                          "Number of adaptive strategies that have a \n dependency relation with another adaptive strategy",
                          True, cmap[2:4])

    plt.subplots_adjust(top=0.97, bottom=0.3)
    plt.show()


def graph_adaptive_testabiltiy(data, exemplar_name):
    report = data["Adaptive Metrics Report"]
    ent_names = collect_dict_values_from_key(report, "Ent")

    maintainability = collect_dict_values_from_key(report, "Maintainability")
    complexity = collect_dict_values_from_key(maintainability, "Complexity")

    fig, ax = plt.subplots(1, 1)
    fig.subplots_adjust(hspace=0.5)

    cmap = plt.get_cmap("Paired")(np.arange(14))

    plot_double_bar_chart(ax, ent_names, complexity,
                          "Adaptive Complexity of each adaptive in " + exemplar_name,
                          "Number of linearly independent paths through \n an adaptive strategy's control flow graph", True, cmap[4:6])

    plt.subplots_adjust(top=0.97, bottom=0.3)
    plt.show()


def graph_adaptive_metrics_report(data):
    report = data["Adaptive Metrics Report"]
    ent_names = collect_dict_values_from_key(report, "Ent")
    locality = collect_dict_values_from_key(report, "Locality")
    impact_radius = collect_dict_values_from_key(locality, "ImpactRadius")

    maintainability = collect_dict_values_from_key(report, "Maintainability")
    complexity = collect_dict_values_from_key(maintainability, "Complexity")

    dependencies = collect_dict_values_from_key(report, "Dependencies")

    fig, ax = plt.subplots(3, 1)
    fig.subplots_adjust(hspace=0.5)

    cmap = plt.get_cmap("Paired")(np.arange(len(ent_names)))

    plot_double_bar_chart(ax[0], ent_names, impact_radius, "Local and Global Impact Radius of each adaptive strategy",
                          "Number of reachable \n executable statements", False, cmap[0:2])

    plot_double_bar_chart(ax[1], ent_names, dependencies, "Dependencies of each adaptive strategy",
                          "Number of adaptive strategies \n that have a dependency \n on another adaptive strategy",
                          False, cmap[2:4])

    plot_double_bar_chart(ax[2], ent_names, complexity,
                          "Cyclomatic and Strict Cyclomatic complexity of each adaptive strategy",
                          "Complexity", True, cmap[4:6])

    plt.subplots_adjust(left=0.25, right=0.75, top=0.97, bottom=0.3)
    plt.show()


def plot_double_bar_chart(ax, labels, values, title, y_axis_label, has_xticks, cmap):
    first_key = [list(d.keys())[0] for d in values][0]
    # print(first_key)

    second_key = [list(d.keys())[1] for d in values][0]
    # print(second_key)

    first_values = [list(d.values())[0] for d in values]
    second_values = [list(d.values())[1] for d in values]

    # print(labels)
    # print(first_values)
    # print(second_values)

    width = 0.35
    x = np.arange(len(labels))
    bar1 = ax.bar(x - width / 2, first_values, width, label=first_key, color=cmap[0])
    bar2 = ax.bar(x + width / 2, second_values, width, label=second_key, color=cmap[1])

    ax.set_ylabel(y_axis_label)
    ax.set_title(title)
    if has_xticks:
        plt.xticks(x, labels, rotation=45, ha='right')
    else:
        ax.set_xticks(x, [''] * len(labels))
    ax.set_axisbelow(True)
    ax.grid(color='gray', linestyle='dashed')
    ax.legend()
    plt.rc('axes', labelsize=12)

    if all(v == 0 for v in first_values) and all(v == 0 for v in second_values):
        ax.set_ylim([0, 1])
        ax.set_yticks(np.arange(min(first_values), max(first_values) + 2, 2.0))


def graph_modifiability(data):
    report = data["Adaptive Metrics Report"]
    modifiability = []
    ent_names_with_modifiability = []
    for ent in report:
        if "Modifiability" in ent["Maintainability"]:
            modifiability.append(ent["Maintainability"]["Modifiability"])
            ent_names_with_modifiability.append(ent["Ent"])

    cmap = plt.get_cmap("Paired")(np.arange(16))

    fig, ax = plt.subplots(2, 1)
    fig.subplots_adjust(hspace=0.5)

    coupling = [list(d.values())[0] for d in modifiability]
    plot_single_bar_chart(ax[0], ent_names_with_modifiability, coupling, "Coupling of each adaptive strategy",
                          "Number classes coupled to", False, cmap[6])

    lack_of_cohesion = [list(d.values())[1] for d in modifiability]
    print(lack_of_cohesion)
    plot_single_bar_chart(ax[1], ent_names_with_modifiability, lack_of_cohesion,
                          "Lack of Cohesion of each adaptive strategy",
                          "Class methods that do not  use a\ngiven class instance variable (%)", True, cmap[7])

    plt.subplots_adjust(left=0.25, right=0.75, top=0.97, bottom=0.3)
    plt.show()


def graph_coupling(data, exemplar_name):
    report = data["Adaptive Metrics Report"]
    modifiability = []
    ent_names_with_modifiability = []
    for ent in report:
        if "Modifiability" in ent["Maintainability"]:
            modifiability.append(ent["Maintainability"]["Modifiability"])
            ent_names_with_modifiability.append(ent["Ent"])

    cmap = plt.get_cmap("Paired")(np.arange(10))
    print(len(cmap))
    fig, ax = plt.subplots(1, 1)
    fig.subplots_adjust(hspace=0.5)

    coupling = [list(d.values())[0] for d in modifiability]
    plot_single_bar_chart(ax, ent_names_with_modifiability, coupling, "Coupling of each adaptive strategy in " + exemplar_name,
                          "Number classes coupled to", True, cmap[6])

    plt.subplots_adjust(top=0.97, bottom=0.3)
    plt.show()


def graph_lack_of_cohesion(data, exemplar_name):
    report = data["Adaptive Metrics Report"]
    modifiability = []
    ent_names_with_modifiability = []
    for ent in report:
        if "Modifiability" in ent["Maintainability"]:
            modifiability.append(ent["Maintainability"]["Modifiability"])
            ent_names_with_modifiability.append(ent["Ent"])

    cmap = plt.get_cmap("Paired")(np.arange(10))

    fig, ax = plt.subplots(1, 1)
    fig.subplots_adjust(hspace=0.5)

    lack_of_cohesion = [list(d.values())[1] for d in modifiability]
    plot_single_bar_chart(ax, ent_names_with_modifiability, lack_of_cohesion,
                          "Lack of Cohesion of each adaptive strategy in " + exemplar_name,
                          "Average number of class \n instance variables not \n used by its functions (%)", True, cmap[6])

    plt.subplots_adjust(top=0.97, bottom=0.3)
    plt.show()


def plot_single_bar_chart(ax, labels, values, title, y_axis_label, has_xticks, cmap):
    width = 0.35
    x = np.arange(len(labels))
    bar = ax.bar(x, values, width, color=cmap)

    ax.set_ylabel(y_axis_label)
    ax.set_title(title)
    if has_xticks:
        plt.xticks(x, labels, rotation=45, ha='right')
    else:
        ax.set_xticks(x, [''] * len(labels))
    ax.set_axisbelow(True)
    ax.set_ylim(bottom=0)
    ax.grid(color='gray', linestyle='dashed')


def collect_dict_values_from_key(list, key):
    return [d[key] for d in list]


def generate_autopct(limit):
    def autopct(percentage):
        return ('%.2f' % percentage) if percentage > limit else ''

    return autopct
