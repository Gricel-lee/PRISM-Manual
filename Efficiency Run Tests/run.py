from pymoo.optimize import minimize

from pymoo.core.problem import Problem
#from pymoo.model.problem import Problem

import numpy as np
import math
from functools import reduce# multiply elements in a list
import operator
import random
import itertools
import subprocess
import re
from pymoo.visualization.scatter import Scatter

from pymoo.algorithms.moo.nsga2 import NSGA2

from pymoo.factory import get_sampling, get_crossover, get_mutation

from pymoo.operators.mixed_variable_operator import MixedVariableSampling, MixedVariableMutation, MixedVariableCrossover

import datetime

import datetime

'''-------------------------------------------------------'''

idle = []
travel = []
prob_succ = []



pmFile = "/Users/gris/eclipse-workspace/execPrismAPI/models/1_0PM[2, 4, 0, 1].mdp"
prism = "/Users/gris/Documents/prism-4.7-osx64/bin/prism"
# Formulae
f1Feas = "/Users/gris/eclipse-workspace/execPrismAPI/models/1_0PM(2, 4, 0, 1).propFeas"
f2P = "/Users/gris/eclipse-workspace/execPrismAPI/models/1_0PM(2, 4, 0, 1).propP"
f3I = "/Users/gris/eclipse-workspace/execPrismAPI/models/1_0PM(2, 4, 0, 1).propI"
f4T = "/Users/gris/eclipse-workspace/execPrismAPI/models/1_0PM(2, 4, 0, 1).propT"
    
# check if schedulable
a = datetime.datetime.now()
outputFeas = subprocess.Popen([prism, pmFile,f1Feas], stdout=subprocess.PIPE).stdout.read().strip().decode("utf-8")
isSchedulable = float(re.search('Result: (\-?\d*\.?\d*)', outputFeas).group(1))
print(isSchedulable)   
b = datetime.datetime.now() - a
print("\n\n===========TIME schedulable",b.total_seconds())      
# idle
a = datetime.datetime.now()
output = subprocess.Popen([prism, pmFile,f4T], stdout=subprocess.PIPE).stdout.read().strip().decode("utf-8")
print(output)
b = datetime.datetime.now() - a
print("\n\n===========TIME schedulable",b.total_seconds())  

# travel
a = datetime.datetime.now()
output = subprocess.Popen([prism, pmFile,f3I], stdout=subprocess.PIPE).stdout.read().strip().decode("utf-8")
print(output)
b = datetime.datetime.now() - a
print("\n\n===========TIME schedulable",b.total_seconds())  

# prob
a = datetime.datetime.now()
output = subprocess.Popen([prism, pmFile,f2P], stdout=subprocess.PIPE).stdout.read().strip().decode("utf-8")
print(output)
b = datetime.datetime.now() - a
print("\n\n===========TIME schedulable",b.total_seconds())  
