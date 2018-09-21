# Week 3

## Happened this week

* Implemented flocking behaviors
* Made simulation interactive
* Made more unit tests (90% coverage)
* JavaDoc comments

Most of the used time this week was used making the classes modular and having as little hard coded parameters as possible. Functionality is mostly finished.

## Problems

Struggled a bit with flow of parameters from GUI to agent (needs more work), as well as, responsibility of agent class; how many methods need to be public for the class to be able to be unit tested, etc. Performance of rotation is still an issue, and will try to change polygon points manually, but I suspect the existing java implementation is already fairly fast.

## Next week

Start working on own ArrayList and optimized spatial data structure / algorithm for finding neighbors for agents. Make more unit tests. Clean up functionality between classes; Make public only methods necessary for class to function.

## Questions

* Later when I start testing performance, can I, for example, use fast implementation of trig or sqrt functions found on the internet to compare if it makes the algorithm more performant? Or are we to mostly compare between our own solutions?

## Time spent

* 3h implementing flocking behaviors
* 3h writing tests and changing application as needed
* 2h fixing GUI to work with changes in the application
* 3h figuring out new structure and agentGenerator implementation