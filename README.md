# Human Resources System

Used technologies:

    1. Java 8
    2. Maven
    3. Spring Framework
        - Spring Boot
        - Spring Web MVC for REST API
    4. MySQL DB
	
---

## 1. Features:

With this app as an admin you can add new user. Logged user can add new employee to the system and search employees with criteria.
Employee has title, salary and is assigned to department. Every employee can be assigned to many departments. User can read employees
history of salaries, titles and departments with dates. If title or salary is current, "to date" is 9999-01-01. Date before today means 
title(department or salary) has been changed or employee doesn't work here anymore. 
There's event sending email to employee after changing his salary.

REST endopints:
* register new user
* update user profile
* user login
* add employee
* add department
* change employee profile
* change employee title
* change employee salary
* assign employee to department
* unassign employee from department
* fire employee



    


