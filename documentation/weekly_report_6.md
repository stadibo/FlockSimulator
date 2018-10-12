# Week 6

## Happened this week

* Updated javadoc
* Made the project a bit more modular
* Fixed style errors and improved readability

This week I made many small changes and reflected on what could still be improved.

## Problems

The main improvement yet to be made before finishing touches is the usability and flexibility of the GUI. It works well, but is limited. There might be a need to create different kinds of agents for the simulation with different behaviors. It is possible to change the source code for testing such scenarios, but I feel it needs to able to be done from the GUI. I was trying to figure out a way to expand the existing classes without breaking the code, and te be able to have different agents I need some kind of class to manage the generators.

JavaFX rotation is still slow despite the underlying algorithm. It seems to be beacuse each time a polygon shape changes it needs to rasterized, which is intensive for the CPU. I will attempt to use images instead of polygons to represent agents on the screen. For now it is still quite interesting to see the simulation work without seeing the absolute direction of each agent.

## Next week

Making the application more interactive (possibility to add different agents). Adding benchmarks for comparing built in data structures versus mine, as well as, benchmarking the worst case scenarios for my implementations.

## Time spent

* 1h JavaDoc and other fixes
* 3h reworking structure and fixes when changes broke netbeans
* 2h Peer-review
* 1h reflection on the state of project