# TS-ACO-TSP
Implementation of Tabu Search and ACO for TSP problem (java)
Assignment KU Leuven KULAK.

# Tabu Search
For using the Tabu Search algorithm we first create a graph:
```
Graph graphName = new Graph("src\\TS_ACO_TSP\\Dataset\\nameOfProblem.tsp");
```
After that, make an new instance of TabuSearch:
```
TabuSearch searchName = new TabuSearch(graphName, numberOfIterationsWanted);
```
Get the solution tour:
```
Tour solutionTour = searchName.getSolutionTour();
```


# Ant Colony Optimization
For using the Ant Colony Optimization algorithm we first create a graph:
```
Graph graphName = new Graph("src\\TS_ACO_TSP\\Dataset\\nameOfProblem.tsp");
```
After that, make a new instance of ACO:
```
ACO searchName = new ACO(graphName, numberOfIterationsWanted);
```
Get the solution tour:
```
Tour solutionTour = searchName.getSolutionTour();
```

# Tour

We can do a couple of things with the tour:
```
int length = solutionTour.getLength();
List<Integer> listOfElements = solutionTour.getElements();
solutionTour.printTour();
```

# General

For some problems, the optimal tour with optimal tour length can be found in Dataset under the form "nameOfProblem.opt.tour".
