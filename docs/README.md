# Robert User Guide

![Screenshot of Robert Chatbot](/docs/Ui.png)

Robert is a polite butler-themed chatbot that helps you manage various tasks (e.g., todos, deadlines, events). He responds with polite phrases—like “Certainly, sir”—and keeps track of your tasks in a local storage file.

Below are instructions on some of Robert’s key features and how to use them, along with examples of the expected outcomes.

---

## Adding Todos

Use the `todo` command to add a basic task without any specific date or time.

**Usage**:
```
todo <description>
```

**Example**:
```
todo read book
```

**Expected Outcome**:
```
Certainly, sir. I have added this task:
   [T][ ] read book
You now have 1 tasks in your list, sir.
```

---

## Adding Deadlines

Use the `deadline` command to add a task that must be done before a specific date. Robert will then store the new deadline and update you with the total number of tasks in your list.

**Usage**:
```
deadline <description> /by <YYYY-MM-DD>
```

**Example**:
```
deadline submit report /by 2025-01-31
```

**Expected Outcome**:
```
Certainly, sir. I have added this task:
   [D][ ] submit report (by: Jan 31 2025)
You now have 3 tasks in your list, sir.
```

---

## Adding Events

Events let you specify a start time and end time for a task. Robert will keep track of these and display them appropriately when you list your tasks.

**Usage**:
```
event <description> /from <start> /to <end>
```

**Example**:
```
event project meeting /from 2025-02-01 /to 2025-02-02
```

**Expected Outcome**:
```
Certainly, sir. I have added this task:
   [E][ ] project meeting (from: 2025-02-01 to: 2025-02-02)
You now have 4 tasks in your list, sir.
```

---

## Listing Tasks

Use the `list` command to view all tasks currently stored. Robert will enumerate them in the order they were added (unless you sorted deadlines).

**Usage**:
```
list
```

**Expected Outcome**:
```
Certainly, sir. Here are the tasks in your list:
 1.[T][ ] read book
 2.[D][ ] submit report (by: Jan 31 2025)
 3.[E][ ] project meeting (from: 2025-02-01 to: 2025-02-02)
 ...
```

---

## Marking a Task

When a task is done, you can mark it with an `X` to indicate completion.

**Usage**:
```
mark <task_number>
```

**Example**:
```
mark 1
```

**Expected Outcome**:
```
Certainly, sir. I've marked this task as done:
   [T][X] read book
```

---

## Unmarking a Task

If you marked a task by mistake or want to revert it to undone status:

**Usage**:
```
unmark <task_number>
```

**Example**:
```
unmark 1
```

**Expected Outcome**:
```
Certainly, sir. I've marked this task as not done yet:
   [T][ ] read book
```

---

## Deleting Tasks

Use the `delete` command to remove a specific task from your list.

**Usage**:
```
delete <task_number>
```

**Example**:
```
delete 2
```

**Expected Outcome**:
```
Certainly, sir. I've removed this task:
   [D][ ] submit report (by: Jan 31 2025)
You now have 2 tasks in the list, sir.
```

---

## Finding Tasks

If you want to locate tasks by a keyword in their description:

**Usage**:
```
find <keyword>
```

**Example**:
```
find report
```

**Expected Outcome**:
```
Certainly, sir. Here are the matching tasks:
 1.[D][ ] submit report (by: Jan 31 2025)
```

---

## Sorting Deadlines

Sort all existing deadlines by ascending date, placing them first in the list (followed by other tasks).

**Usage**:
```
sort
```

**Expected Outcome**:
```
Certainly, sir. Deadlines have now been sorted by date.
Here is your newly arranged list:
 1.[D][ ] submit report (by: Jan 25 2025)
 2.[D][ ] renew passport (by: Jan 31 2025)
 3.[T][ ] read book
 ...
```

---

> **Note**:
> - Robert informs you of any errors (e.g., invalid date formats, out-of-range task indices) with a polite, butler-style apology or explanation.
> - All data is stored locally in a `data` folder by default.
> - If you need to **save** or **load** tasks from a different location, modify the relevant file path in `Robert.java`.

Enjoy your conversation with **Robert**, your personal butler chatbot!