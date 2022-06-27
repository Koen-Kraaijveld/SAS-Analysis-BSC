from evaluator import read_data, evaluate

trapp_raw_data = read_data("./data/trapp.json")
evaluated_data = evaluate(trapp_raw_data, "TRAPP", True)
