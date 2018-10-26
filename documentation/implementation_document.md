# Implementation

## Function

The flock simulator is an interactive application for simulating coordinated animal motion such as flocks of birds or schools of fish in a 2D environment. By varying the strength of three key behaviors a complex set of motions is simulated. The algorithm achieves this by, for every agent in simulation, getting the nearest neighbors and aligning with their direction, and/or steering away from them, and/or seeking the neighborhoods’ midpoint.

## Structure

At the top level there is a GUI class that initializes the environment where representations of agents will be drawn. It also creates an __Generator__ implementation which is responsible for storing agents objects, creating new ones, and applying changed parameters to agents stored. There are two implementations of the abstract __Generator__ class: __AgentGenerator__, which uses a brute force approach to checking for neighbors, and __SpatialAgentGenerator__, which additionally stores the agents in a Bin-lattice data structure for faster lookup of neighbors. 

The agents are represented by the abstract __Agent__ class, which stores all the required information about the agent, e.g. position, velocity, etc. and contains basic methods for calculating steering behaviors that are applied each update. The __Agent__ class can be implemented to create agents with different behaviors. The implementations utilize a __Vector__ class in the calculation of forces and representation of position, velocity, etc. The __Flocker__ class implements __Agent__ and adds functions for more complex rules/behaviors. Lastly, standard math operations throughout the application are handled by __java.lang.Math__ wrapped in __MathWrapper__ class. JavaDoc can be found [here](https://github.com/stadibo/FlockSimulator/tree/master/documentation/JavaDoc/apidocs).

![alt text](https://raw.githubusercontent.com/stadibo/FlockSimulator/master/documentation/img/FlockSimulatorStructure.png "Structure diagram")

## Time Complexity
### Brute force approach
With a brute force approach the time complexity of the nearest neighbors algorithm is _O(n)_, where _n_ is the number of agents, and when comparing all agents to all other agents ends up being _O(n^2)_. This is the result of looping through the list of all _n_ agents for each agent in its flocking functions: alignment, cohesion and separation. Each function loops through the n agents and executes constant time operations on them. The time for this is _O( 3n^2 )_, and since we can ignore constant time operations and _O( 3n^2 )_ is equivalent to _O(n^2)_, we can state that the brute force approach has a time complexity _O(n^2)_.


### Bin-lattice spatial subdivision
Using a bin-lattice spatial data structure (3.) for neighbor queries, each bin stores the agents contained in a specific bin/"cell area" based on the coordinates of the agents. In practice the data structure represents a grid dividing the rendered scene into cells of a certain size. The time complexity can be reduced to _O(n*k)_ where _n_ is the number of agents and _k_ is maximum density (amount of agents) within a given radius, since agents maintain a minimum separation distance.

## Space Complexity
In both cases each agent requires constant space. The brute force approach only uses a list for storage and space complexity is therefore _O(n)_. The approach using spatial subdivision also uses a list to store the agents. In addition the bin-lattice has a 2D grid which stores lists with pointers to agents. The grid size is relative to the rendered scene resolution and cell size, and ends up being constant. The lists within the grid combined require _O(n)_ space. The space complexity ends up being _O(2n)_, and is equivalent to _O(n)_.

## Optimizations
* combining looping through all agents in the flocking functions, instead of looping through in each function separately, which was done for readability.
* checking if any agent has moved beyond its cell boundary and moving it to the correct cell, instead of reinserting every agent on each update.

Sources
1. http://www.red3d.com/cwr/steer/gdc99/
2. http://www.red3d.com/cwr/boids/
3. http://www.red3d.com/cwr/papers/2000/pip.pdf
