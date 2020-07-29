---
title: Observer pattern
parent: Architecture
has_children: false
nav_order: 3
---

## Observer pattern
When communicating between modules, there are some cases which are lower modules deliver message to higher modules.
For example, when user acts to connect blocks.
`Canvas` module, which is a lower module of the `GUI` module, needs to send message to `GUI` module.
At this moment, the module that the user actually interacts with is `Canvas`.
But an arrow, which is drawn for showing how the blocks will be connected, is on the `GUI` module.
So although user interacts with `Canvas`, `Canvas` must request message to the `GUI` module to generate an arrow.
Sadly, however, `Canvas` is a lower module of the `GUI` module and does not know the interface of the `GUI`.
To resolve this issue, Dluid use Observer pattern for reverse communication between modules.

So shortly from the above example
1. `GUI` module registers an observer for drawing an arrow to observable of `Canvas` module.
2. if the `Canvas` module wants to deliver the message to the Gui module, the `Canvas` module calls `setChanged()` and `notifyObserver()` to Observable.

### Custom usage
As mentioned earlier, each module has one singleton and one facade.
And there is one state machine in a singleton.

#### State machine
The `state machine` is the only one `observable` that exists in each module.
The reason why Dluid limits availability of `observable` to one per module is simply because of ease of management.
When notification occurs, observers registered in the `state machine` decide whether they should act or not by looking at the `action type`.
At this point, some concepts like `action`, `payload`, `dispatch`, `reducer` are used in the Dluid.
These concepts were inspired from the web front-end library, Redux.

#### Action
Action is a parameter passed by `notifyObserver` method. 
There are two types of information, `type` and `payload`.
Observers check the action type for determining whether message came to me or not.
`Action type` is a predefined enumerator.
```java
public class Action {
    private ActionType type;
    private Object payload;
}
```

#### payload
`Payload` is additional data that you want to send to the Observer through action.

#### dispatch
It is the act of making an action through a `state machine`.

#### reducer
Abstract class that inherits the observer .
This class has some method like `support` and `update`.
`support` method is method that determines what type of action it is and an update method that determines how it works.
Observers using `state machine` must inherit Reducer.

```java
public abstract class Reducer implements Observer {
    // ...
    public abstract boolean support(Action action);
    public abstract void update(Observable o, Action action);
}
```
