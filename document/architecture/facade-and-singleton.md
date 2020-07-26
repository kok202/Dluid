---
title: Facade & Singleton
parent: Architecture
has_children: false
nav_order: 2
---

## Facade and Singleton
Every dluid module has `Facade` and `Singleton` class.
`~Facade` class in Dluid is interface for each module.
In brief, The `~Facade` is responsible for communication between the module.
Basically upper module can access to any class of under modules.
Therefore, upper modules may communicate directly with singleton in under modules.
But, access using the `Facade` is recommended.

By comparison with Spring Framework, `Facade` in dluid is controller and `singleton` is being used as `ApplicationContext`.
