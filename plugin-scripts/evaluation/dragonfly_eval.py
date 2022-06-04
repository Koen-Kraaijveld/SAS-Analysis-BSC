from evaluator import evaluate, save_data, read_data

dragonfly_raw_data = read_data("./data/dragonfly.json")
evaluated_data = evaluate(dragonfly_raw_data, "Dragonfly", False)
