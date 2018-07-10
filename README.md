# SNOB Simulation

Peer-sim simulation of the Snob model

# Usage

```
mvn install
mvn package
mvn exec:java@snob #for the snob simulation
# enable silent maven with -q option: mvn -q ...
```
* Config files must be placed in "./configs" folder.
* Find results in the results folder. Under the name "./config/{config.name}-output.txt"
