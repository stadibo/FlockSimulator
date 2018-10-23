# Unit testing

Test coverage of application logic:

![alt text](https://raw.githubusercontent.com/stadibo/FlockSimulator/master/documentation/img/20181023_test_coverage.png "Test coverage")

# Performance testing

## Manual testing

Current visual performance can be measured by running the program, manually adding agents to the screen by holding down the mouse button, and observing the framerate counter. This is not the optimal way to gauge the performance of the algorithm itself, but gives a good look at the performance of the software as a whole. (Still working on simply getting the performance of the algorithm itself, without the visual component.)

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

Launching the application from the console with a command line argument "TEST_X" (X = label for test) will launch the benchmarking side of the program. The test consists of running the algorithm without the GUI trying various amounts of agents (25 - 3200) for 1000 position updates. The time for 1000 updates and the average time for each update is printed for each case. Multiple command line argument can be written and these will all be run as benchmarks.

Add these as individual arguments when launching application:

Test label | Data structure |
---------------- | --------- |
BRUTE | Brute force using own list|
BIN | Bin-Lattice using own list |
JAVABRUTE | Brute force using java ArrayList|
JAVABIN | Bin-Lattice using java ArrayList |

### Results

**BRUTE FORCE O(n^2)**

The brute force implementation slows down significantly with larger amounts of agents. The java implementation is slightly slower, which might be caused by more complex functions and 

![alt text](https://raw.githubusercontent.com/stadibo/FlockSimulator/master/documentation/img/1000%20updates%20using%20brute%20force%20approach%20(FlockList).png "BF FlockList")

![alt text](https://raw.githubusercontent.com/stadibo/FlockSimulator/master/documentation/img/1000%20updates%20using%20brute%20force%20approach%20(ArrayList).png "BF ArrayList")

![alt text](https://raw.githubusercontent.com/stadibo/FlockSimulator/master/documentation/img/1000%20updates%20using%20brute%20force%20approach%20(FlockList%20vs%20ArrayList).png "BF FlockList vs ArrayList")

**BIN-LATTICE SPATIAL SUBDIVISION O(n*k)**

The algorithm using the java list implementation is slightly faster and will continue to be faster beacause of the more efficient way of removing agents from the list, in particular clearing entire lists.

![alt text](https://raw.githubusercontent.com/stadibo/FlockSimulator/master/documentation/img/1000%20updates%20using%20bin-lattice%20(FlockList).png "BL FlockList")

![alt text](https://raw.githubusercontent.com/stadibo/FlockSimulator/master/documentation/img/1000%20updates%20using%20bin-lattice%20(ArrayList).png "BL ArrayList")

![alt text](https://raw.githubusercontent.com/stadibo/FlockSimulator/master/documentation/img/1000%20updates%20using%20Bin-lattice%20(FlockList%20vs%20ArrayList).png "BL FlockList vs ArrayList")

**BRUTE FORCE VS BIN-LATTICE RELATIVE TIME REQUIRED**

In a sensible use case, around 1600 agents on screen the algorithm using the spatial data structure is 24 times faster than the brute force approach. At this point the amount of comparisons is getting detrimental to the performance of the brute force algorithm as it can no longer maintain 60 frames per second while rendering the simulation.

![alt text](https://raw.githubusercontent.com/stadibo/FlockSimulator/master/documentation/img/1000%20updates_%20Brute%20force%20(FlockList)%20vs%20Bin-lattice%20(FlockList).png "BF vs BL")

**NOTES**
The efficiency of the Bin-lattice is dependent on values (maxSpeed/Force, Cohesion, Separation) for the agents. If agents can get closer to each other than the cell size in the lattice they will crowd up and increase the amount of neighbor checks and make the actual performance worse, closer to time complexity O(n^2). But when run with reasonable separation for agents, when agents are more spread out, it runs significantly faster than the __brute force__ method.
