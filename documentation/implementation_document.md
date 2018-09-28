# Implementation

What does it do?
Why?

How is it structured?
Why?

## Complexity
With a brute force approach the time complexity of the nearest neighbors algorithm is O(n), and when done for all agents ends up being O(n^2). Due to the straightforward nature of the algorithm a only a simple ArrayList is needed which has a space requirement of O(n).

## Optimizations
* Comparing distance between points without square-root, so instead of using euclidean distance just using squared euclidean distance.
* 

Sources

* http://www.red3d.com/cwr/steer/gdc99/
* http://www.red3d.com/cwr/boids/
* http://www.red3d.com/cwr/papers/2000/pip.pdf