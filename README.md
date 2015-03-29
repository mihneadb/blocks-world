# Blocks World

School assignment. Defines [Blocks World](http://en.wikipedia.org/wiki/Blocks_world) entities (blocks, actions
and predicates) and uses A* to find the needed actions in order to get from the starting world to the target world.

Check out the [main](https://github.com/mihneadb/blocks-world/blob/master/src/Main.java) file to get an idea.


Example run:

```
From [on(B, A), ontable(A), ontable(C), ontable(D), clear(B), clear(C), clear(D), armempty()]
To [on(C, A), on(B, D), ontable(A), ontable(D), clear(C), clear(B), armempty()]

Applying unstack(B, A)
To [on(B, A), ontable(A), ontable(C), ontable(D), clear(B), clear(C), clear(D), armempty()]
Got [ontable(A), ontable(C), ontable(D), clear(C), clear(D), hold(B), clear(A)]

Applying stack(B, D)
To [ontable(A), ontable(C), ontable(D), clear(C), clear(D), hold(B), clear(A)]
Got [ontable(A), ontable(C), ontable(D), clear(C), clear(A), on(B, D), clear(B), armempty()]

Applying pickup(C)
To [ontable(A), ontable(C), ontable(D), clear(C), clear(A), on(B, D), clear(B), armempty()]
Got [ontable(A), ontable(D), clear(A), on(B, D), clear(B), hold(C)]

Applying stack(C, A)
To [ontable(A), ontable(D), clear(A), on(B, D), clear(B), hold(C)]
Got [ontable(A), ontable(D), on(B, D), clear(B), on(C, A), clear(C), armempty()]

Took 4 steps.
Took 2 ms.
```
