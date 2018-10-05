# Unit testing

Test coverage of application logic:

![alt text](https://raw.githubusercontent.com/stadibo/FlockSimulator/master/documentation/20181005_test_coverage.png "Test coverage")

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

*In my experience the performance is limited by the rotation operation, does not matter how fast the neighbor querying algorithm is.*

## Automatic benchmarking

Launching the application from the console with a command line argument "TEST_X" (X = number for test) will launch the benchmarking side of the program. The test consists of running the algorithm without the GUI trying various amounts of agents (25 - 1600) for 1000 position updates. The time for 1000 updates and the average time for each update is printed for each case (TODO: written to a file). Multiple command line argument can be written and these will all be run as benchmarks.

Add these as individual arguments when launching application:

Test label | Data structure / rotation(on/off) |
---------------- | --------- |
TEST_1 | Brute force |
TEST_2 | Bin-Lattice |

### Results

**BRUTE FORCE O(n^2)**

Amount of agents | Estimated FPS | Time (1000 updates) |
---------------- | ------------- | ------------------- |
25 | 27027 | 37 ms |
50 | 17857 | 56 ms |
100 | 4219 | 237 ms |
200 | 1166 | 857 ms | 
400 | 315 | 3171 ms |
800 | 91 | 10973 ms |
1600 | 20 | 49980 ms |

**BIN-LATTICE SPATIAL SUBDIVISION O(n*k)**

Amount of agents | Estimated FPS | Time (1000 updates) |
---------------- | ------------- | ------------------- |
25 | 33333 | 30 ms |
50 | 22222 | 45 ms |
100 | 13698 | 73 ms |
200 | 5882 | 170 ms | 
400 | 2427 | 412 ms |
800 | 1036 | 965 ms |
1600 | 407 | 2452 ms |

**NOTES**
The efficiency of the Bin-lattice is dependent on values (maxSpeed/Force, Cohesion, Separation) for the agents. If agents can get closer to each other than the cell size in the lattice they will crowd up and increase the amount of neighbor check and make the actual performance worse, closer to time complexity O(n^2). But when run with reasonable separation for agents, when agents are more spread out, it runs significantly faster than the __brute force__ method
