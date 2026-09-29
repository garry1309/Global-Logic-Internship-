Employee Management System 
_____________________________________

What this project does
------------------------
It's a simple console app where you can:
- Add an employee (Full-Time, Part-Time, or Intern)
- Look up an employee's details
- Calculate their salary
- See all employees at once
- Send them a notification message

How the code is organized
---------------------------
model         -> the Employee classes (Employee, FullTimeEmployee, PartTimeEmployee, Intern)
salary        -> the classes that actually calculate salary for each employee type
repository    -> where employees get saved/stored (in memory, like a temporary database)
notification  -> handles sending a message to an employee
service       -> ties everything together (the "manager" class)
app           -> Main.java, the actual menu you interact with

Why I built it this way (SOLID principles, explained simply)
---------------------------------------------------------------

1. Single Responsibility
   Each class does ONE thing only. The Employee class just holds a person's
   info. It doesn't calculate salary itself - it asks a separate calculator
   class to do that job. Same with storage and notifications - each has its
   own class.

2. Open/Closed
   If I want to add a new employee type later (like "Contractor"), I don't
   have to touch any of the existing code. I just add a new class for it.
   Nothing that already works gets edited or risked breaking.

3. Liskov Substitution
   FullTimeEmployee, PartTimeEmployee, and Intern can all be used anywhere
   the code expects a plain "Employee" - the program doesn't care which one
   it actually is, they all behave properly.

4. Interface Segregation
   The interfaces (SalaryCalculator, EmployeeRepository, NotificationService)
   are small and only have the methods they actually need. Nothing bloated
   or unused.

5. Dependency Inversion
   The main "manager" class (EmployeeService) doesn't depend on specific
   classes like InMemoryEmployeeRepository directly - it depends on the
   general interface instead. This means I could swap out storage for a
   real database later without rewriting the manager class.

Why I used interfaces
------------------------
Interfaces basically say "any class that implements me MUST have these
methods" - without caring how they're actually implemented. This makes it
easy to swap pieces in and out (like switching from in-memory storage to a
real database) without breaking the rest of the app.

How to add a new employee type (example: Contractor)
---------------------------------------------------------
1. Make a new class ContractorSalaryCalculator that implements SalaryCalculator
   and figures out how a contractor gets paid.
2. Make a new class ContractorEmployee that extends Employee and uses that
   calculator.
3. That's it - nothing else in the project needs to change.

