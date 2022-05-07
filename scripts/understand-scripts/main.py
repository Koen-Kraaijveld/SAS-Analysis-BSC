import os

os.add_dll_directory("C:\\Program Files\\SciTools\\bin")

import understand

print(understand.version())

db = understand.open("C:\\Users\\koenk\\OneDrive\\Documents\\Vrije Universiteit\\Year 3\\Bachelor Project\\SAS "
                     "exemplars\\Dragonfly\\Dragonfly-master\\DragonFly-Project\\src\\src.und")

def sortKeyFunc(ent):
  return str.lower(ent.longname())

ents = db.ents("function,method,procedure")
for func in sorted(ents,key = sortKeyFunc):
    #print(func.simplename())
    if func.simplename() == "checkStatus":
        for lexeme in func.lexer():
            print(lexeme.text(), end="")
            if lexeme.ent():
                print("@", end="")
    # first = True
    # for param in func.ents("Define","Parameter"):
    #   if not first:
    #     print (", ",end="")
    #   print (param.type(),param,end="")
    #   first = False
    # print (")")