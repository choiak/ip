# Inu-chan User Guide

Inu-chan is a command-line task manager that helps you manage your tasks.

## Adding tasks

There are three types of tasks:

* To-Do (labelled as T)
* Deadline (labelled as D)
* Event (labelled as E)

### Adding To-Do

Add a to-do task to the list.

Command: `todo <task name>`

Example: `todo read book`

Example output
```
       __    __
       \/----\/
        \^  ^/    inuChan.tasks.Task added, WOOF!
        _\  /_
      _|  \/  |_
     | | |  | | |
    _| | |  | | |_
   "---|_|--|_|---"

Added the following task as task 1, ruff!
	[T][ ]: read book
```

### Adding deadline

Add a to-do with deadline to the list.

Command: `deadline <task name> /by <deadline>`

Example: `deadline assignment 1 /by tomorrow`

Example output
```
       __    __
       \/----\/
        \^  ^/    inuChan.tasks.Task added, WOOF!
        _\  /_
      _|  \/  |_
     | | |  | | |
    _| | |  | | |_
   "---|_|--|_|---"

Added the following task as task 2, ruff!
	[D][ ]: assignment 1 (by tomorrow)
```
### Adding event

Add an event to the list.

Command: `event <task name> /from <starting time> /to <ending time>`

Examples: 
 - `event dinner with friends! /from 18:00 /to 20:00`
 - `event dinner with friends! /to 20:00 /from 18:00`


Example output
```
       __    __
       \/----\/
        \^  ^/    inuChan.tasks.Task added, WOOF!
        _\  /_
      _|  \/  |_
     | | |  | | |
    _| | |  | | |_
   "---|_|--|_|---"

Added the following task as task 3, ruff!
    [E][ ]: dinner with friends! (18:00 -> 20:00)
```

## Listing tasks

List all tasks in the list. The number at the beginning of each line is the index of the task, the first bracket is the type of the task, and the second bracket indicate the completion of the task (X for completed, blank for not completed).

Command: `list`

Example output
```
       __    __
       \/----\/
        \^  ^/    The list, WOOF!
        _\  /_
      _|  \/  |_
     | | |  | | |
    _| | |  | | |_
   "---|_|--|_|---"

Here's the list, ruff!
1. [T][ ]: read book
2. [D][X]: assignment 1 (by tomorrow)
3. [E][ ]: dinner with friends! (18:00 -> 20:00)
```

## Marking tasks

Mark a task as completed.

Command: `mark <task index>`

Example: `mark 1`

Example output
```
       __    __
       \/----\/
        \^  ^/    Marked, WOOF!
        _\  /_
      _|  \/  |_
     | | |  | | |
    _| | |  | | |_
   "---|_|--|_|---"

Marked the following item, ruff!
    [T][X]: read book
```

## Unmarking tasks

Mark a task as not completed.

Command: `unmark <task index>`

Example: `unmark 1`

Example output
```
       __    __
       \/----\/
        \^  ^/    Unmarked, WOOF!
        _\  /_
      _|  \/  |_
     | | |  | | |
    _| | |  | | |_
   "---|_|--|_|---"

Unmarked the following item, ruff!
    [T][ ]: read book
```

## Finding tasks

Find a task by keywords.

Command: `find <keywords>`

Example: `find book`

Example output
```
       __    __
       \/----\/
        \^  ^/    I found these, WOOF!
        _\  /_
      _|  \/  |_
     | | |  | | |
    _| | |  | | |_
   "---|_|--|_|---"

Here's the matching tasks, ruff!
1. [T][ ]: read book
```

## Exit the program

Command: `bye` or  `quit` or `exit` or `goodbye`

