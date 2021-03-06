# User Guide

* [About](#about)
* [Getting Started](#getting-started)
    * [Download](#download)
    * [Launch](#launch)
    * [Visual Walkthrough](#visual-walkthrough)
    * [Functional Walkthrough](#functional-walkthrough)
* [Commands Summary](#commands-summary)
* [Supported Time Fomats](#supported-time-formats)
* [Commands](#commands)
    * [Adding Tasks and Reminders](#adding-tasks-and-reminders)
    * [Editing Tasks and Reminders](#editing-tasks-and-reminders)
    * [Renaming Tags](#renaming-tags)
    * [Marking Tasks as Done](#marking-tasks-as-done)
    * [Deleting Tasks and Reminders](#deleting-tasks-and-reminders)
    * [Listing Tasks and Reminders](#listing-tasks-and-reminders)
    * [Viewing a Task](#viewing-a-task)
    * [Finding Freetime](#finding-freetime)
    * [Undoing Previous Command](#undoing-previous-command)
    * [Making Aliases for Commands](#making-aliases-for-commands)
    * [Saving and Loading Storage File](#saving-and-loading-storage-file)
    * [Getting Program Information](#getting-program-information)
    * [Seeking Help](#seeking-help)
    * [Exiting Linenux](#exiting-linenux)
* [Shortcut Keys](#shortcut-keys)
* [Conclusion](#conclusion)

//@@author A0127694U
## About

Everyone of us will face, at some point in our lives, the feeling of being overwhelmed by the amount of things to do: meeting project deadlines, attending a friend's wedding ceremony, paying the bills and the list goes on. This hectic pace of life is the new normal, but it doesn't mean we have to suffer in silence. We believe that everyone should have control over their own lives and Linenux is the key to achieving just that.

Linenux is essentially a task organiser. It helps you to prioritise your tasks by urgency and importance, so that you can better make use of your time by focussing on things that matter. What sets Linenux apart from other task managers is its simplicity and efficiency. It requires only a single line of command. No mouse clicks needed and no gimmicks. Linenux works only as fast as you can type, making it a perfect fit for those who prefer typing.

Without further ado, let's get started!

//@@author A0140702X
## Getting Started

#### Download

1. Ensure that you have Java 8 installed on your computer.
2. Download the latest linenux.jar from the [releases](https://github.com/CS2103AUG2016-W11-C1/main/releases) tab on our github page.
    
    <img src="images/userGuide/release.png">
    
3. Copy the file to the folder that you want to use as the home folder for Linenux.

#### Launch

Double-click on the file to launch Linenux. Wait for a few seconds for the main window to appear.

//@@author A0144915A
#### Visual Walkthrough

<img src="images/userGuide/mainWindow.png">

1. **To-do Panel** - shows to-dos.
2. **Deadline Panel** - shows deadlines.
3. **Event Panel** - shows events.
4. **Result Panel** - shows reminders, help page and search results.
5. **Command Box** - where you enter the command.

*The list of deadlines, events and reminders are always ordered according to their urgency.*

//@@author A0135788M
#### Functional Walkthrough

*This section is a scenario driven, step-by-step introduction on how to use Linenux. For a more in-depth coverage of the commands, refer to our [Commands](#commands) section in the user guide.*

*Step 1: Adding a task.*

You have to buy groceries from the local supermarket today as your relatives are coming to visit for the weekend. You decide to remind youself with the aid of Linenux. Type the following line in the command box:

`> add buy groceries et/today 7.00pm`

*Step 2: Setting a reminder.*

You worry that you might not have enough cash to buy groceries and decide to withdraw money from a nearby ATM machine at your workplace. Type the following line in the command box:

`> remind buy groceries n/withdraw money et/today 6.00pm`

*Step 3: Making spelling mistakes.*

Linenux provides a quick way to fix typing errors. Type the following line in the command box:

`> addd play the piano`

Typing the above line with the spelling mistake will cause a prompt to appear on the bottom-right corner of the window.

`> yes `

Replying yes to the prompt will add the task 'play the piano' without having to type everything again. How convenient!

*Step 4: Marking a task as done.*

It is 8.00pm already and you have just finished buying the groceries. Type the following line in the command box:

`> done buy groceries`

This command will prevent the task from showing up again in subsequent `list` or `today` commands.

*Step 5: Becoming proficient*

You are very impressed with what you have seen thus far and want to learn more about the amazing things you can do with Linenux. Type the following line in the command box:

`> help`

This brings up the list of Linenux commands with their description and format. It is for a quick in-app look-up. Continue to the next section if you want a complete rundown of the details of commands.

## Commands Summary

*Notes:*

1. *The `command` word must be the first word in the sentence.*
2. *Optional fields are enclosed in square brackets `[]`.*
3. *All commands and their respective fields are case-insensitive.*
4. *The order of the fields do not matter.*
5. *The notation `...` means that you can have more than one instance of that field.*

| Command                 		| Description                               | Format                                                        	 |
|-------------------------------|-------------------------------------------|--------------------------------------------------------------------|
| [`add`](#add) 		  		| Adding a task.	   	     	  		    | `add` TASK [st/START_TIME] [et/END_TIME] [#/TAG]...   	 		 |
| [`remind`](#remind) 	  		| Setting a reminder for a task.  	      	| `remind` KEYWORDS t/TIME n/NOTE                       	 		 |
| [`edit`](#edit) 		  		| Editing a task.   	  		            | `edit` KEYWORDS [n/TASK] [st/START_TIME] [et/END_TIME] [#/TAG]...  |
| [`editr`](#editr)       		| Editing a reminder.                       | `editr` KEYWORDS [t/TIME] [n/NOTE]                      	 	 	 |
| [`rename`](#rename)	  		| Renaming a tag.						    | `rename` OLD_TAG #/NEW_TAG										 |
| [`done`](#done) 	      		| Marking a task as done.       	  	 	| `done` KEYWORDS										             |
| [`undone`](#undone)     		| Marking a task as undone.                 | `undone` KEYWORDS                                                  |
| [`delete`](#delete) 	  		| Deleting a task. 	                        | `delete` KEYWORDS 										      	 |
| [`deleter`](#deleter)   		| Deleting a reminder.                      | `deleter` KEYWORDS                                                 |
| [`clear`](#clear)       		| Clearing a set of tasks.		            | `clear` [#/TAG]											       	 |
| [`list`](#list) 		  		| Listing tasks and reminders.              | `list` [KEYWORDS] [st/START_TIME] [et/END_TIME] [#/TAG] [d/DONE] 	 |
| [`today`](#today)       		| Listing tasks and reminders for today.    | `today` 													         |
| [`tomorrow`](#tomorrow) 		| Listing tasks and reminders for tomorrow. | `tomorrow` 													 	 |
| [`view`](#view)         		| Viewing details around a task.            | `view` KEYWORDS                                                    |
| [`freetime`](#freetime) 		| Finding a free timeslot.   	  	 	    | `freetime` [st/START_TIME] et/END_TIME 				             |
| [`undo`](#undo) 		  		| Undoing the previous command.          	| `undo` 				   									     	 |
| [`alias`](#alias)       		| Making aliases for the commands.          | `alias` COMMMAND_NAME NEW_NAME                               		 |
| [`unalias`](#unalias)   		| Removing aliases for the commands.        | `unalias` ALIAS                                                    |
| [`save`](#save)         		| Saving the schedule to another folder.    | `save` NEW_PATH                                                    |
| [`load`](#load)               | Loading a schedule from another folder.   | `load` PATH                                                        |
| [`information`](#information) | Getting information about the program.    | `information`                                                      |
| [`help`](#help) 		  		| Seeking help.			                    | `help` [COMMMAND_NAME]    									 	 |
| [`exit`](#exit) 	   	        | Exiting Linenux. 			                | `exit` 			       									     	 |


//@@author A0144915A
## Supported Time Formats

| Format               | Example                |
|----------------------|------------------------|
| dd MMMM yyyy hh.mma  | 01 Jan 2016 5.50pm     |
| yyyy-MM-dd hh.mma    | 2016-10-16 5.50pm      |
| today 5.50pm         | today 5.50pm           |
| tomorrow 5.50pm      | tommorrow 5.50pm       |


//@@author A0135788M
## Commands

*Notes:*

1. *The `command` word must be the first word in the sentence.*
2. *All commands and their respective fields are case-insensitive.*
3. *The order of the fields do not matter.*
4. *Optional fields are enclosed in square brackets `[]`.*
5. *The notation `...` means that you can have more than one instance of that field.*

#### Adding Tasks and Reminders

##### *Add*

Linenux supports 3 kinds of tasks:

1. **To-dos**    (Tasks without start and end times)
2. **Deadlines** (Tasks with end times only)
3. **Events**    (Tasks with start and end times)

Adding a task has never been this easy. Just indicate the appropriate fields and we will automatically assign the newly created task to one of the three categories above.

*Format:*

`add TASK [st/START_TIME] [et/END_TIME] [#/TAG]...`

*Examples:*

```
Explanation: Adding a to-do with tag 'trump'.
> add watch videos on presidential election #/trump

Explanation: Adding a deadline with tags 'household' and 'bills and money'.
> add pay utility bills et/tomorrow 5.00pm #/household #/bills and money

Explanation: Adding an event with tag 'household'.
> add house warming st/tomorrow 7.00pm et/tomorrow 9.00pm #/household
```

<img src="images/userGuide/add.png">

##### *Remind*

Setting reminders is as simple as adding a task. Just specify the appropriate fields and we will assign the reminder to the task that you want. Reminders will show up on the display panel upon launch or when the commands `list`, `today` and `tomorrow` are entered.

*Format:*

`remind KEYWORDS t/TIME n/NOTES`

*Examples:*

```
Explanation: Setting a reminder for the event 'house warming' with the note 'buy groceries'.
> remind house warming t/tomorrow 7.00am n/buy groceries
```

<img src="images/userGuide/remind.png">

*Did you know?*

Have you wondered why we use the word `KEYWORDS` rather than `TASK`? This is because the `remind` command actually searches for task names containing those keywords! For example, consider the scenario below :

```
> add cs2101 assignment et/tomorrow 11.59pm
> add cs2103 assignment et/tomorrow 11.59pm
> remind assignment n/finish assignment t/tomorrow 10.00pm
```

<img src="images/userGuide/multipleResult.png">

In this scenario, since there are multiple task names with the keyword `assignment`, both results are returned and you get to choose, via specifying an index, which task is the reminder for. This searching mechanism is implemented for all commands with the field `KEYWORDS`. How cool is that?

//@@author A0140702X
#### Editing Tasks and Reminders

##### *Edit*

In a perfect world, everything will go according to plan. Unfortunately, things change all the time and we need to be able to respond accordingly. Thus, to make any changes to your tasks, we have the `edit` command.

It uses the same searching mechanism as `remind` and it will update the respective fields of the task to their new values. However, do take note that any new tags you add to a task with replace all existing tags.

Also, to remove fields, we will use a dash as shown in the examples below. You can remove all fields except for`n/TASK_NAME`.

*Format:*

` edit KEYWORDS [n/TASK] [st/START_TIME] [et/END_TIME] [#/TAG]...`

*Examples:*

```
Explanation: Adding an event with tags 'hobby' and 'rest day'.
> add play golf st/tomorrow 7.00am et/tomorrow 9.00am #/hobby #/rest day

Explanation: Changing the name of the event from 'play golf' to 'play chess'.
> edit golf n/play chess

Explanation: Changing the starting and ending time of the event 'play chess'.
> edit chess st/tomorrow 7.00pm et/tomorrow 9.00pm

Explanation: Replacing both tags 'hobby' and 'rest day' with the tag 'fun'.
> edit chess #/fun

Explanation: Changing 'play chess' from an event to a to-do.
> edit chess st/- et/-

Explanation: Removing all tags associated with the to-do 'play chess'.
> edit chess #/-
```

##### *Editr*

`editr` is the `edit` command for reminders. It works similar to the `edit` command, with the same earching mechanism which searches for a reminder by its note. However, since both the time and note fields are compulsory, you will not be able to remove any of the fields.

*Format:*

`editr KEYWORDS [t/TIME] [n/NOTE]`

*Examples:*

```
Explanation: Setting a reminder for the event 'house warming' with the note 'buy groceries'.
> remind house warming t/tomorrow 7.00am n/buy groceries

Explanation: Changing the time of a reminder with note.
> editr buy groceries t/tomorrow 6:00am
```

//@@author A0127694U
#### Renaming Tags

##### *Rename*

Renaming a tag will change all tasks with that tag to the new tag.

*Format:*

`rename OLD_TAG #/NEW_TAG`

*Examples:*

```
Explanation: Rename tag 'assignments' to 'nus assignments'.
> rename assignments #/nus assignments
```

//@@author A0127694U
#### Marking Tasks as Done

##### *Done*

Yes! You’ve just completed a task. Since it’s completed, you wouldn’t want to see it popping up as a task that is incomplete. Thus, type `done`, along with the task name to mark it as completed.

*Format:*

`done KEYWORDS`

*Examples:*

```
Explanation: Mark to-do 'write user guide' as done.
> done write user guide.
```

##### *Undone*

In cases where we accidentally mark an incomplete task as done, we can use the `undone` command to mark the task as incomplete.

*Format:*

`undone KEYWORDS`

*Examples:*

```
Explanation: Mark to-do `write user guide` as undone.
> undone write user guide
```

#### Deleting Tasks and Reminders

##### *Delete*

At times, tasks or reminders might become redundant. For example, a scheduled meeting might be cancelled due to unforeseen circumstances. Deleting a task will remove all its reminders.

*Format:*

```
delete KEYWORDS
```

*Example:*

```
Explanation: Delete an event 'cs2103 meeting'.
> delete cs2103 meeting
```

##### *Deleter*

`deleter` is the delete command for reminders. When you delete a reminder will, you will not delete the task that it is associated with.

*Format:*

```
deleter KEYWORDS
```

*Example:*

```
Explanation: Delete a reminder 'buy groceries'.
> delete buy groceries
```

##### *Clear*

To delete multiple tasks in the same category, the `clear` command can be used. When used on its own, it will delete all tasks marked as done. You can also delete a set of tasks with the same tag by specifying the tag name.

*Format:*

`clear [#/TAG]`

*Example:*

```
Explanation: Clears all tasks marked as done.
> clear

Explanation: Clears all tasks with tag 'assignment'.
> clear #/assignment
```

//@@author A0135788M
#### Listing Tasks and Reminders

##### *List*

The default behaviour of the `list` command returns the list of all incomplete tasks and reminders. This may not be particularly helpful as you may have a lot of outstanding tasks. Luckily, you are able to search for tasks by specifying the various fields. For example, you can list tasks that have the same tag.

Also, if you need to see the tasks that you have completed, simply add the `d/DONE` field which works as follows:

1. d/yes - Shows only completed tasks.
2. d/all - Shows completed tasks along with incompleted tasks.

*Format:*

`list [KEYWORDS] [st/START_TIME] [et/END_TIME] [#/TAG] [d/DONE]`

*Examples:*

```
Explanation: Lists all incomplete tasks and reminders.
> list

Explanation: Lists all incomplete tasks and reminders containing the word `assignment`.
> list assignment

Explanation: List all incomplete tasks and reminders from now to 25 December 2016, 11.59pm.
  (Assuming now is some time before 25 Decemeber 2016.)
> list et/2016-12-25 11.59pm

Explanation: List all incomplete tasks and reminders from 16 October 2016, 12.00am to 25 December 2016, 11.59pm.
> list st/2016-10-16 12.00am et/2016-12-25 11.59pm

Explanation: List all completed tasks.
> list d/yes

Explanation: List both completed and incompleted tasks.
> list d/all

Explanation: List all incomplete tasks with tag 'nus'.
> list #/nus

Explanation: List all incomplete tasks from 16 October 2016, 12.00am to 25 December 2016, 11.59pm and with tags 'nus'.
> list st/2016-10-16 12.00am et/2016-12-25 11.59pm #/nus
```

<img src="images/userGuide/listAll.png">

<img src="images/userGuide/listTag.png">

//@@author A0140702X
##### *Today*

Seeing as how you might want to know today's tasks frequently, instead of typing `list st/today 12.00am et/today 11.59pm`, we made it into a command itself! Your events that occur today will be shown as well.

*Format:*

`today`

*Example:*

```
Explanation: List all tasks today.
> today
```

##### *Tomorrow*

Similar as the command `today`, this is a syntactic sugar which is equivalent to `list st/tomorrow 12.00am et/tomorrow 11:59pm`. Your events that occur tomorrow will be shown as well.

*Format:*

`tomorrow`

*Example:*

```
Explanation: List all tasks the next day.
> tomorrow
```
#### Viewing a Task

##### *View*

The command `list` only provides the general details surrounding a task. To see all the reminders associated with a task, use `view`.

*Format:*

`view KEYWORDS`

*Example:*

```
Explanation: View details regarding the deadline.
> view CS2101 assignment
```

<img src="images/userGuide/view.png">

//@@author A0144915A
#### Finding Freetime

##### *Freetime*

Sometimes we might need to know when we are free. The `freetime` command shows you all the time slots that are not occupied by events.

*Format:*

`freetime [st/START_TIME] et/END_TIME`

*Examples:*

```
Explanation: Finding all free time slots from tomorrow 3.00pm to tomorrow 10.00pm.
> freetime st/tomorrow 3.00pm et/tomorrow 10.00pm

Explanation: Finding all free time slots from now to tomorrow 9.00pm.
> freetime et/tomorrow 9.00pm
```

<img src="images/userGuide/freetime.png">

//@@author A0135788M
#### Undoing Previous Command

##### *Undo*

We all make mistakes in life, but we believe in second chances. After all, we are mere mortals. With the `undo` command, we will turn a blind eye to your previous commands. However, do note that not all mistakes can be forgiven. Only the following commands can be undone:

*Undo-able Commands:*

1. `add`
2. `remind`
3. `edit`
4. `editr`
5. `rename`
6. `done`
7. `undone`
8. `delete`
9. `deleter`
10. `clear`

You can undo up to ten such commands.

*Format:*

`undo`

*Example:*

```
Explanation: Undo previous undo-able commands, if any.
> undo
```

//@@author A0144915A
#### Making Aliases for Commands

##### *Alias*

Ever felt that the command `tomorrow` is too long to type? Or if you have a better name for a command? Well, the `alias` command allows you to create aliases for commands. A command can have multiple aliases, but no two commands can have the same alias. Furthermore, an alias has to be a single word consisting of only letters and numbers.

*Format:*

`alias COMMAND_NAME NEW_NAME`

*Example:*

```
Explanation: Adding an alias for tomorrow.
> alias tomorrow tmr

Explanation: Listing all incomplete tasks and reminders for the next day.
> tmr

Explanation: Original command tomorrow will still work.
> tomorrow
```

##### *Unalias*

After setting an alias for a command, you can remove it with the `unalias` command. Note that you cannot remove the original command word.

*Format:*

`unalias ALIAS`

```
Explanation: Removing alias tmr from tomorrow
> unalias tmr
```

#### Saving and Loading Storage File

##### *Save*

You might want to store the schedule file in a separate folder. You can do so by specifying the full path location.

*Format:*

`save NEW_PATH`

*Example*

```
Explanation: Saving the schedule file to the Downloads folder.
> save C:\Users\Jim\Downloads\MySchedule.xml
```

##### *Load*

Similarly, you can load a schedule file from another folder by specifying the full path location.

*Format:*

`load PATH`

*Example*

```
Explanation: Loading a schedule file from the Documents folder.
> load C:\Users\Jim\Documents\.Schedule.xml
```

//@@author A0127694U
#### Getting Program Information

##### *Information*

Provides program information.

*Format:*

`information`

*Example*

```
Explanation: Gets program information.
> information
```

<img src="images/userGuide/information.png">

//@@author A0140702X
#### Seeking Help

##### *Help*

We all need a helping hand every once in a while. Lucky for you, we are with you every step of the way. If you have forgotten how to use a command, or want to know more about what Linenux can do, just type `help` and we will tell you all that you need to know. Help also displays the current aliases for the command.

*Format:*

`help [COMMMAND_NAME]`

*Example:*

```
Explanation: Help for all commands.
> help

Explanation: Help for information command.
> help information
```

<img src="images/userGuide/help.png">

#### Exiting Linenux

##### *Exit*

When you are done for the day, to exit the program, you can just type `exit`!

*Format:*

`exit`

*Example:*

```
Explanation: Exits the program.
> exit
```

//@@author A0127694U
## Shortcut Keys

*These keyboard shortcuts are not commands, but they help speed up navigation in Linenux. We hope that you find these shortcuts helpful.*

| Key                         | Function                                                                                   |
|-----------------------------|--------------------------------------------------------------------------------------------|
| <kbd>↑</kbd> / <kbd>↓</kbd> | Cycles through your most recently used commands without having to type everything again.   |
| <kbd>Tab</kbd>              | Autocompletes the command word for you. 												   |

//@@author A0140702X
## Conclusion

Thank you for taking the time to read our user guide. Please feel free to post in our [issue tracker](https://github.com/CS2103AUG2016-W11-C1/linenux/issues) if you notice any bugs or have any suggestions on how to improve. We will be extremely happy to hear from you so we can make Linenux even better. With that, have fun organising your tasks!
