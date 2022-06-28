from matplotlib import pyplot as plt
import numpy as np

params = {'legend.fontsize': 'x-large',
          'figure.figsize': (15, 5),
          'axes.labelsize': 'x-large',
          'axes.titlesize': 'x-large',
          'xtick.labelsize': 'x-large',
          'ytick.labelsize': 'x-large'}
plt.rcParams.update(params)


def graph_locality(data, exemplar_name):
    """
    Generates a graph to plot the Impact Radius from an exemplar's JSON data.

    Args:
        data: the data to be plotted.
        exemplar_name: the name of the exemplar to be used in the graph's title and labels.
    """

    report = data["Adaptive Metrics Report"]
    ent_names = collect_dict_values_from_key(report, "Ent")
    locality = collect_dict_values_from_key(report, "Locality")
    impact_radius = collect_dict_values_from_key(locality, "ImpactRadius")

    fig, ax = plt.subplots(1, 1)
    cmap = plt.get_cmap("Paired")(np.arange(len(ent_names)))

    plot_double_bar_chart(ax, ent_names, impact_radius, "Local and Global Impact Radius of each adaptive strategy in " +
                          exemplar_name, "Number of reachable executable statements", True, cmap[0:2])

    plt.subplots_adjust(top=0.97, bottom=0.3)
    plt.show()


def graph_concentration_impact(data, exemplar_name):
    """
    Organizes the data to be plotted for the Concentration of Impact metric from an exemplar's JSON data.

    Args:
        data: the data to be plotted.
        exemplar_name: the name of the exemplar to be used in the graph's title and labels.
    """

    spread = data["ConcentrationOfImpact"]["Decentralization"]["Spread"]
    if len(spread) == 0:
        return

    ent_names = collect_dict_values_from_key(spread, "Ent")
    relative_values = collect_dict_values_from_key(spread, "Relative")
    generate_relative_coc_pie_chart(ent_names, relative_values, 100, 1, exemplar_name)


def generate_relative_coc_pie_chart(labels, relative_values, text_limit, autopct_limit, exemplar_name):
    """
    Generates a pie chart from the organized data for the Concentration of Impact metric.

    Args:
        labels: the text labels corresponding to the names of the adaptive strategies.
        relative_values: the Relative Global Impact Radius values to be plotted.
        text_limit: the text limit to determine when text is placed outside of its pie chart slice.
        autopct_limit: the percentage to determine when text is placed outside of its pie chart slice.
        exemplar_name: the name of the exemplar to be used in the graph's title and labels.
    """

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

    plt.subplots_adjust(left=0.3, right=0.7, top=0.9, bottom=0.15)
    plt.show()


def graph_dependency_degree(data, exemplar_name):
    """
       Generates a graph to plot the Dependency Degree from an exemplar's JSON data.

       Args:
           data: the data to be plotted.
           exemplar_name: the name of the exemplar to be used in the graph's title and labels.
   """

    report = data["Adaptive Metrics Report"]
    ent_names = collect_dict_values_from_key(report, "Ent")
    dependencies = collect_dict_values_from_key(report, "Dependencies")
    print(dependencies)

    fig, ax = plt.subplots(1, 1)
    fig.subplots_adjust(hspace=0.5)

    cmap = plt.get_cmap("Paired")(np.arange(len(ent_names)))

    plot_double_bar_chart(ax, ent_names, dependencies,
                          "Dependency Degree of each adaptive strategy in " + exemplar_name,
                          "Number of adaptive strategies that have a \n dependency relation with another adaptive "
                          "strategy",
                          True, cmap[2:4])

    plt.subplots_adjust(top=0.97, bottom=0.3)
    plt.show()


def graph_adaptive_complexity(data, exemplar_name):
    """
       Generates a graph to plot the Adaptive Complexity from an exemplar's JSON data.

       Args:
           data: the data to be plotted.
           exemplar_name: the name of the exemplar to be used in the graph's title and labels.
   """

    report = data["Adaptive Metrics Report"]
    ent_names = collect_dict_values_from_key(report, "Ent")

    maintainability = collect_dict_values_from_key(report, "Maintainability")
    complexity = collect_dict_values_from_key(maintainability, "Complexity")

    fig, ax = plt.subplots(1, 1)
    fig.subplots_adjust(hspace=0.5)

    cmap = plt.get_cmap("Paired")(np.arange(14))

    plot_double_bar_chart(ax, ent_names, complexity,
                          "Adaptive Complexity of each adaptive in " + exemplar_name,
                          "Number of linearly independent paths through \n an adaptive strategy's control flow graph",
                          True, cmap[4:6])

    plt.subplots_adjust(top=0.97, bottom=0.3)
    plt.show()


def graph_adaptive_metrics_report(data):
    """
       Generates a series of graphs for each adaptive strategy in the architecture from an exemplar's JSON data.

       Args:
           data: the data to be plotted.
    """

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
    """
    Generates a bar chart with two color-coded bars for each adaptive strategy.

    Args:
        ax: the subplot that the graph will be generated on.
        labels: the names of the adaptive strategies.
        values: the resulting values of a metric computed from the adaptive strategies
        title: the title of the generated graph.
        y_axis_label: the label used for the y-axis.
        has_xticks: true if the graph has ticks on the x-axis.
        cmap: the color map used by the generated graph.
    """

    first_key = [list(d.keys())[0] for d in values][0]

    second_key = [list(d.keys())[1] for d in values][0]

    first_values = [list(d.values())[0] for d in values]
    second_values = [list(d.values())[1] for d in values]

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
    """
       Generates a graph to plot the Adaptive Modifiability from an exemplar's JSON data.

       Args:
           data: the data to be plotted.
   """

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
    """
        Generates a graph to plot the Coupling of an architecture of adaptive strategies from an exemplar's
        JSON data.

       Args:
           data: the data to be plotted.
           exemplar_name: the name of the exemplar to be used in the graph's title and labels.
   """

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
    plot_single_bar_chart(ax, ent_names_with_modifiability, coupling,
                          "Coupling of each adaptive strategy in " + exemplar_name,
                          "Number classes coupled to", True, cmap[6])

    plt.subplots_adjust(top=0.97, bottom=0.3)
    plt.show()


def graph_lack_of_cohesion(data, exemplar_name):
    """
       Generates a graph to plot the Lack of Cohesion of an architecture of adaptive strategies from an exemplar's
       JSON data.

       Args:
           data: the data to be plotted.
           exemplar_name: the name of the exemplar to be used in the graph's title and labels.
   """

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
                          "Average number of class \n instance variables not \n used by its functions (%)", True,
                          cmap[6])

    plt.subplots_adjust(top=0.97, bottom=0.3)
    plt.show()


def plot_single_bar_chart(ax, labels, values, title, y_axis_label, has_xticks, cmap):
    """
        Generates a bar chart for the metrics results from each adaptive strategy.

        Args:
            ax: the subplot that the graph will be generated on.
            labels: the names of the adaptive strategies.
            values: the resulting values of a metric computed from the adaptive strategies
            title: the title of the generated graph.
            y_axis_label: the label used for the y-axis.
            has_xticks: true if the graph has ticks on the x-axis.
            cmap: the color map used by the generated graph.
        """

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
    """
    Collects values of a key from a list of dictionaries.
    Args:
        list: the list of dictionaries.
        key: the key corresponding to the value that will be collected from each dictionary in the list.

    Returns: a list containing the values from each key from each dictionary.
    """

    return [d[key] for d in list]


def generate_autopct(limit):
    """
    Wrapper function that is used to generate a limit that is used for the text and percentages of a pie chart.

    Args:
        limit: defines the limit on which text and percentages will be shown in the pie chart.

    Returns: the limit on which text and percentages will be shown in the pie chart.
    """

    def autopct(percentage):
        """
        Generates a string to define the limit for the text and percentages of a pie chart.

        Args:
            percentage: defines the limit on which text and percentages will be shown in the pie chart.

        Returns: the string defining the limit on which text and percentages will be shown in the pie chart.
        """

        return ('%.2f' % percentage) if percentage > limit else ''

    return autopct
