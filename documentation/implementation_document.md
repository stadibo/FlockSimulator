# Implementation

## Function

The flock simulator is an interactive application for simulating coordinated animal motion such as flocks of birds or schools of fish in a 2D environment. By varying the strength of three key behaviors a complex set of motions is simulated. The algorithm achieves this by, for every agent in simulation, getting the nearest neighbors and aligning with their direction, and/or steering away from them, and/or seeking the neighborshoods midpoint.

## Structure

At the top level there is a GUI class that initializes the environment where representations of agents will be drawn. It also creates an _Generator_ implementation which is responsible for storing agents objects, creating new ones, and applying changed parameters to agents stored. There are two implementations of the abstract _Generator_ class: _AgentGenerator_, which uses a brute force approach to checking for neighbors, and _SpatialAgentGenerator_, which additionally stores the agents in a Bin-lattice data structure for faster checking of neighbors. The agents are represented by the abstract _Agent_ class, which stores all the required information about the agent, e.g. position, velocity, etc. and contains basic methods for calculating steering behaviors that are applied each update. The _Agent_ class can be implemented to create agents with different behaviors. The implementations utilize a _Vector_ class in the calculation of forces and representation of position, velocity, etc. Lastly, standard math operations throughout the application are handled by _java.lang.Math_ wrapped in _MathWrapper_ class.

*INSERT CLASS DIAGRAM HERE*

## Complexity

With a brute force approach the time complexity of the nearest neighbors algorithm is O(n), where n is the number of agents, and when comparing all agents to all other agents ends up being O(n^2). Due to the straightforward nature of the algorithm a only a simple ArrayList is needed which has a space complexity of O(n).

Using a bin-lattice spatial data structure (3.) for neighbor queries. Each bin stores the agents contained in a specific area/"cell". The time complexity can be reduced to O(n*k) where n is the number of agents and k is the number of bins to check. Space complexity of the bin-lattice is still O(n), since it the bin-lattice still only contains one instance of each agent.

## Optimizations
* Comparing distance between points without square-root, so instead of using euclidean distance just using squared euclidean distance.

Sources

1. ttp://www.red3d.com/cwr/steer/gdc99/
2. http://www.red3d.com/cwr/boids/
3. http://www.red3d.com/cwr/papers/2000/pip.pdf