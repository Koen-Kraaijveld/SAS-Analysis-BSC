from evaluator import evaluate, save_data, read_data

atrp_raw_data = read_data("./data/atrp.json")
evaluated_data = evaluate(atrp_raw_data, "ATRP", True)
