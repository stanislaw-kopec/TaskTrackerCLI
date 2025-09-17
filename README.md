# Task Manager CLI

A simple Java command-line application for managing tasks (add, update, delete, change status).  
All tasks are stored in a `tasks.json` file to ensure persistence between program runs.

---

## Features
- Add new tasks  
- Update task descriptions  
- Delete tasks by ID  
- Change task status:  
  - `Todo`  
  - `In progress`  
  - `Done`  
- List tasks:  
  - all  
  - only `done`  
  - only `todo`  
  - only `in-progress`  
- Automatic save and load from a JSON file  

---

## Getting Started

### 1. Clone the repository
```bash
git clone https://github.com/stanislaw-kopec/TaskTrackerCLI.git
```
```bash
cd task-manager-cli
```
2. Compile the project
```bash
javac Main.java Service.java Task.java Status.java
```
3. Run the application
```bash
java Main <command> [arguments]
```
Available Commands
```pgsql
add <taskName>              - Add a new task
update <taskId> <newName>   - Update a task
delete <taskId>             - Delete a task
mark-in-progress <taskId>   - Mark a task as "In progress"
mark-done <taskId>          - Mark a task as "Done"
list [done|todo|in-progress]- List tasks (all or filtered)
help                        - Show this help message
```
Usage Examples
Add a new task:

```bash
java Main add "Buy groceries"
```
List all tasks:

```bash
java Main list
```
Mark a task with ID 1 as done:

```bash
java Main mark-done 1
```
Update a task description:

```bash
java Main update 2 "Learn Spring Boot"
```
Project Structure
```bash
├── Main.java       # CLI entry point with command handling
├── Service.java    # Business logic and JSON persistence
├── Task.java       # Task model
├── Status.java     # Enum with task statuses
└── tasks.json      # JSON file storing tasks (auto-generated)
```
Technologies
- Java 17+
- JSON (manual serialization/deserialization)

Future Improvements
- Replace manual JSON parsing with a library (e.g., Gson or Jackson)
- Add unit tests (JUnit)
- Support batch task updates
- Enhance CLI with additional features
