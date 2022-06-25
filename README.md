# Prism manual
PRISM Model Checker Manual - an extension to Prism manual https://www.prismmodelchecker.org/manual/


# Prism modifications
PRISM source code modified to avoid actions in Reward structures not specified in modules.

```
PYTHONCMD   PRISMIDE   PRISMAPI   FORMULA
 -           -         564ms       (to start PRISMAPI)
1.38s.       641ms     772ms       Pmax=?[F done]
1.114s       2ms       6ms         R{"travel"}min=?[F done]
1.096s       3ms       1ms         R{"idle"}min=?[F done]
Fails        Fails     Fails       Pmin=?[G fail]
```
