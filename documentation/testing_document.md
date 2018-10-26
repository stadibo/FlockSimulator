# Unit testing

Test coverage of application logic:

![alt text](https://raw.githubusercontent.com/stadibo/FlockSimulator/master/documentation/img/20181023_test_coverage.png "Test coverage")

# Performance testing

## Manual benchmarking

Current visual performance can be measured by running the program, manually adding agents to the screen by holding down the mouse button, and observing the framerate counter. This is not the optimal way to gauge the performance of the algorithm itself, but gives a good look at the performance of the software as a whole.

Numbers obtained by running the program with brute force version of checking nearest neighbors and rotating agents:

Amount of agents | Framerate (avg) |
---------------- | --------- |
100 | 60 |
200 | 59 |
300 | 40 |
400 | 31 |
500 | 24 |
600 | 19 |
700 | 17 |
800 | 14 |
900 | 12 |
1000 | 11 |

*In my experience the performance is limited by the rotation operation and re-rendering in JavaFX, no matter how fast the neighbor querying algorithm is.*

## Automatic benchmarking
Benchmarking for the flocking algorithm itself compares the two main implementations: Brute force approach and the approach using a spatial data structure. These implementations can be more efficient by not using the square root to get the actual distance, but intead foregoing using the square root in calculating the distance and using the squared values as they are when comparing distance.

Guide for running the benchmarks from the command line can be found in the [user guide](https://github.com/stadibo/FlockSimulator/blob/master/documentation/user_guide.md)

### Results

**BRUTE FORCE O(n^2)**

The brute force implementation slows down significantly with larger amounts of agents. Not having to calculate the square root in the distance function saves a fair bit of time.

![alt text](https://raw.githubusercontent.com/stadibo/FlockSimulator/master/documentation/img/1000%20updates%20using%20brute%20force%20approach%20and%20distance%20with%20square%20root.png "BF with sqrt")

![alt text](https://raw.githubusercontent.com/stadibo/FlockSimulator/master/documentation/img/1000%20updates%20using%20brute%20force%20approach%20and%20distance%20without%20square%20root.png "BF without sqrt")

![alt text](https://raw.githubusercontent.com/stadibo/FlockSimulator/master/documentation/img/1000%20updates%20using%20brute%20force%20approach%20(square%20root%20vs%20no%20square%20root).png "BF Distance calculation efficiency comparison")

**BIN-LATTICE SPATIAL SUBDIVISION O(n*k)**

The performance degrades more steadily as expected from the time complexity ananlysis and not calculating the square root still gives a notable improvement in performance. 

![alt text](https://raw.githubusercontent.com/stadibo/FlockSimulator/master/documentation/img/1000%20updates%20using%20bin-lattice%20and%20distance%20with%20square%20root.png "BL with sqrt")

![alt text](https://raw.githubusercontent.com/stadibo/FlockSimulator/master/documentation/img/1000%20updates%20using%20bin-lattice%20and%20distance%20without%20square%20root.png "BL without sqrt")

![alt text](https://raw.githubusercontent.com/stadibo/FlockSimulator/master/documentation/img/1000%20updates%20using%20Bin-lattice%20(square%20root%20vs%20no%20square%20root).png "BL Distance calculation efficiency comparison")

**BRUTE FORCE VS BIN-LATTICE RELATIVE TIME REQUIRED**

In a sensible use case, around 1600 agents on screen the algorithm using the spatial data structure is 24 times faster than the brute force approach. At this point the amount of comparisons is getting detrimental to the performance of the brute force algorithm as it can no longer maintain 60 frames per second while rendering the simulation.

![alt text](https://raw.githubusercontent.com/stadibo/FlockSimulator/master/documentation/img/1000%20updates_%20Brute%20force%20vs%20Bin-lattice%20Relative%20Performance.png "BF vs BL")

**NOTES**
There is a slight bit of randomness in the benchmarking, resulting from placing the agents randomly on the "screen" for a more real scenario, therefore, small variations in the results will occur, but not so large as to affect performance in relation to the other implementations.

The efficiency of the Bin-lattice is dependent on values (maxSpeed/Force, Cohesion, Separation) for the agents. If agents can get closer to each other than the cell size in the lattice they will crowd up and increase the amount of neighbor checks and make the actual performance worse, closer to time complexity O(n^2). But when run with reasonable separation for agents, when agents are more spread out, it runs significantly faster than the __brute force__ method.
