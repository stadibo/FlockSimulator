# Week 2

## Happened this week

* Implemented behaviors for independent agent
* Made simulation runnable
* Made unit tests
* Cleaned up code

Most of the used time this week was used setting up structure of agent and its behavior with vector class.

## Problems

Trigonometric math operations seem to be a bit slow, might need to optimize. Also, rotation of all polygons each update is very processing intesive. Might need to manually rotate.

## Next week

Adding options to change parameters of behavior in gui. Implementing flocking behavior and figuring out how to optimize finding nearest neighbors within radius of agent. Making proper tests that don't brake when paremeters that are now hard coded in agent class are changed.

## Questions

* Is it enough to wrap the "java.lang.Math" class to use functions like sin, cos, atan2, etc, or should I implement them myself? 
* Would it be better (for performance) create manage polygon rotation manually?

## Time spent

* 4h researching Craig Reynolds paper on boids and other papers for how to optimize finding nearest neighbors
* 2h writing tests
* 2h figuring out UI and later fixing UI
* 6h total implementing agent behaviors and creating vector class
