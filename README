# Process instance prototype

This little Play application is a simple prototype how one could define instances of BPMN models. A process instance is an execution path within a BPMN model. For example, `XOR` gateways are replaced with the pair of executed paths. 

```
+------------+         +------------+          +------------+
|            |         |            |          |            |
|   Task A   +--------->     XOR    +---------->   Task B   |
|            |         |            |          |            |
+------------+         +------+-----+          +------------+
                              |
                              |                +------------+
                              |                |            |
                              +---------------->   Task C   |
                                               |            |
                                               +------------+
```

yields the following instance, if Task B is invoked during process execution.

```
+------------+         +------------+
|            |         |            | 
|   Task A   +--------->   Task B   +
|            |         |            |
+------------+         +------------+
```

Run `./activator run` to run the Play! application. On `http://localhost:9000/docs/swagger-ui/index.html?url=/docs/swagger.json` you will find a little documentation of the API. From start, there is a process with id `scanMail` available. Ontologies can be found in `conf/resources`.
