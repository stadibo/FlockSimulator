# Implementation

## Function

The flock simulator is an interactive application for simulating coordinated animal motion such as flocks of birds or schools of fish in a 2D environment. By varying the strength of three key behaviors a complex set of motions is simulated. The algorithm achieves this by, for every agent in simulation, getting the nearest neighbors and aligning with their direction, and/or steering away from them, and/or seeking the neighborshoods midpoint.

## Structure

At the top level there is a GUI class that initializes the environment where representations of agents will be drawn. It also creates an _AgentGenerator_ class which is responsible for storing agents objects, creating new ones, and applying changed parameters to agents stored. The agents are represented by the _Agent_ class, which stores all the required information about the agent, like position, velocity, etc. and contains methods for calculating steering behaviors that are applied each update. The class utilizes a _Vector_ class in the calculation of forces and representation of position, velocity, etc. Lastly, standard math operations throughout the application are handled by _java.lang.Math_ wrapped in _MathWrapper_ class.

*INSERT CLASS DIAGRAM HERE*

## Complexity
With a brute force approach the time complexity of the nearest neighbors algorithm is O(n), and when done for all agents ends up being O(n^2). Due to the straightforward nature of the algorithm a only a simple ArrayList is needed which has a space requirement of O(n).

## Optimizations
* Comparing distance between points without square-root, so instead of using euclidean distance just using squared euclidean distance.
* Implementing Bin-lattice datastructure (TODO)

Sources

* http://www.red3d.com/cwr/steer/gdc99/
* http://www.red3d.com/cwr/boids/
* http://www.red3d.com/cwr/papers/2000/pip.pdf