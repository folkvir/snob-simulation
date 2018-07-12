# SNOB Simulation

Peer-sim simulation of the Snob model

# Usage

```bash
# INSTALL
sh install.sh

# for just running a particular experiment
mvn exec:java@snob-50 #for the snob simulation with 50 queries

# or run the big experiment
nohup sh xp.sh > xp.log &
tail -f xp.log
```
* Config files must be placed in "./configs" folder.
* Find results in the results folder. Under the name "./config/{config.name}-output.txt"
