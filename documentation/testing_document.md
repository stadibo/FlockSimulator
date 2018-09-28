# Testing

## Unit testing

Test coverage of application logic can be found [here](link to jacoco report)

## Performance testing

### Manual testing

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

### Automatic benchmarking (Not yet working)

Launching the application from the console with a command line argument "test" will launch the benchmarking side of the program. The test consists of, first, only running the algorithm without the GUI with various amounts of agents (10 - 100_000) for 600 position updates (10 seconds of rendering at 60fps), and second, the same run will be made with GUI + algorithm to measure the rendered fps. The time for 600 updates and the average time for each update, will be stored for each case and later printed to console or written to a file. Another command line argument would decide which algorithm would be used in test. (For now only brute force exist with either java ArrayList or my own implementation FlockList).