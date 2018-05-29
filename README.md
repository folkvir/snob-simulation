# SNOB Simulation

Peer-sim simulation of the Snob model

# Usage

```
mvn install
mvn package
mvn exec:exec -Dexec.args="./configs/snob.txt" &>/dev/null # redirecting error output to /dev/null
# enable silent maven with -q option: mvn -q ...
```

Find results in the results folder. Under the name "./config/{config.name}-output.txt"