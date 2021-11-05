# MDP Best Policy
Let's imagine the following MDP modelling a robot with two tasks in a file called "**robot2.pm**":

``` java
//There is a robot R2 with two tasks: t2 and t22.
//Task t2 is done in r2_0loc = 1
//Task t22 is done in r2_0loc = 2
//Robot start at location r2_0loc = 0
//The robot can travel between locations

mdp
formula done = (r2_t2=true &r2_t22=true);

module R2_0
r2_0loc : [0..2];
r2_t2 : bool; 
r2_t22 : bool; 


// r2_0 travel from 0 when not busy
[r2_0loc0_1] r2_0loc=0  -> (r2_0loc'=1);
[r2_0loc0_2] r2_0loc=0  -> (r2_0loc'=2);
// r2_0 travel from 1 when not busy
[r2_0loc1_2] r2_0loc=1 -> (r2_0loc'=2);
// r2_0 travel from 2 when not busy
[r2_0loc2_1] r2_0loc=2 -> (r2_0loc'=1);



// joint task 1
[t2] r2_0loc=1 & r2_t2=false -> (r2_t2'=true);
// joint task 2
[t22]  r2_0loc=2 & r2_t22=false -> (r2_t22'=true);
endmodule

rewards "travellingCost"
[r2_0loc0_1] true : 25;
[r2_0loc0_2] true : 25;
[r2_0loc1_2] true: 10;
[r2_0loc2_1] true: 50;
endrewards
```

The reward structure contains the travelling cost between locations. If we would like to reduce the travelling cost, 
for this simple example, we can infer that the 'best' way to complete both tasks is to first do
task 2 at location 1 (action: [r2_0loc0_1], reward: 25) and then task 22 at location 2 (action: [r2_0loc1_2], reward: 10) for a total cost of 35.
Note: if task 22 is done first, the total travelling cost to "done" would be 25+50=75.

The following cmd command gives us this information:
``` java
prism robot2.pm -pctl "Rmin=?[F done]" -exportadv best-policy -exportstates states
```

It generates two files: "best-policy" and "states" files. For now, we are interested in the best-policy[^1]. The first column shows the number of states and transitions.
From the second row, it shows the sequence of actions[^2] to "Rmin=?[F done]". The first column is the current row; the second, the next row (destination); the third the probability of the transition; and the last column the action label:
```java
9 7
0 1 1 r2_0loc0_1
1 3 1 t2
2 4 1 t2
3 7 1 r2_0loc1_2
5 6 1 t22
6 2 1 r2_0loc2_1
7 8 1 t22
```

Hence, for our example, this file can be interpretated as:
```java
0 to 1   - 1st travel to location 1, r2_0loc0_1
1 to 3   - 2nd do task t2
3 to 7   - 3rd travel to location 2, r2_0loc1_2
7 to 8   - 4rd do task t22
there is no 8, so we are done.
```

From this simple exercise we learned that:
- the information on what is happening is given by the action labels (e.g., we know that "t2" means robot r2 is doing t2)
- it may contain parts of the state space that are not actually reachable under that strategy (e.g., rows 5 and 6 are never reached)
- once we reach a non-existent state, we are done!


[^1]: It contains the Markov chain induced by the optimal policy, i.e., the series of deterministic actions that convert the MDP to a DTMC.
[^2]: https://www.prismmodelchecker.org/manual/Appendices/ExplicitModelFiles#tra. Notice that for some models this may not concide to the number of states and transitions shown when build the PRISM model in the graphical interface. This is because: 
"Since we are checking a (cosafe) LTL property, the model checking
process is based on the construction and analysis of a product of the
original model and an automaton. The .tra file you are seeing is a
fragment of that product, not the original model. Hence the mismatch in
indexing. There are actually switches -exportprodtrans and
-exportprodstates with export info about the product model, if that helps." Exchange with D.Parker
