#Design document

The purpose of the flock simulator is to be an interactive application for simulating coordinated animal motion such as flocks of birds or schools of fish in a 2D environment, as well as, benchmarking the performance of the simulation when using various combinations of strength in the behaviors and using flocks of various size.

##Algorithm design and Time Complexity

Behavior information for the simulation is taken from [Craig Reynolds](http://www.red3d.com/cwr/boids/). Initial behaviors are Separation, Alignment and Cohesion.

The idea is to create an algorithm that is able to calculate the next direction and speed of each individual in the flock based on the neighbors around them in asymptotic O(n^2) time. The algorithm will be very naive but should be fast enough for the simulator to animate the “behavior” in real time. Some matrix calculation will be needed for determining the direction of the individuals. The aim is to make the algorithm and underlying operations as efficient as possible so that many individuals can be displayed and animated on modern hardware. By using a spatial data structure, according to Craig Reynolds, the algorithm could be improved to have time complexity close to O(n).

##Data structures and Space complexity

Initially the algorithm will not need very advanced data structures due to the naive implementation. An array-list will be used for storing data of the individuals. For faster lookup of neighbors a more advanced data structure, like R-trees or quadtrees, could be implemented.

Space requirement are initially O(n), but might require multiples of n for more advanced data structures.

##I/O

###Input

User input in the form of sliders that change the weight of a behavior speed of individuals and the amount of individuals to simulate.

###Output

* Animated simulation 
* Performance stats
* Behavior visualization
* Benchmark results

##Sources
[http://www.red3d.com/cwr/boids/]
[https://en.wikipedia.org/wiki/Quadtree]
[https://en.wikipedia.org/wiki/R-tree]