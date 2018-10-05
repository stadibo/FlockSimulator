# Week 5

## Happened this week

* Created Bin-Lattice data structure
* More unit tests
* Benchmarks for two implementations of Generator

Implemented goals for this week quite successfully.

## Problems

I found it difficult to implement the bin-lattice data structure and keep single responsibility for the Spatial generator implementation and the data structure itself, since they go almost hand in hand. Not entirely happy with the current operation, where the grid is emptied and filled on each update. It does not slow down the operation too much, but is certainly not optimal.

## Next week

Make code more readable and fix style errors, maybe change project structure a bit. Finish up documentation. If there is time optimize bin-lattice structure.

## Time spent

* 1h Writing unit tests
* 1h researching optimization of nearest neighbor queries
* 3h implementing Bin-lattice data structure
* 2h Cleaning up code
* 3h Writing documentation / benchmarking / fixing errors in benchmarks
* 2h Peer-review