# Tests
I tested running PRISM in three different ways:
1) Run it as a command line sentence called from python.
2) Run the PRISM API from a java project
3) Run it in the PRISM UI for comparison performance.

The findings and files used as provided here.

# Results
```
PYTHONCMD   PRISMIDE   PRISMAPI   FORMULA
 -           -         564ms       (to start PRISMAPI)
1.38s.       641ms     772ms       Pmax=?[F done]
1.114s       2ms       6ms         R{"travel"}min=?[F done]
1.096s       3ms       1ms         R{"idle"}min=?[F done]
Fails        Fails     Fails       Pmin=?[G fail]
```
