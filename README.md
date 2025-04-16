# Red, Green, VACAY! 
#### A chore-centric budgeting app for families

LaunchCode Liftoff Project -- budget-breakers (group 8)
- --
## Table of Contents
- [Introduction](#introduction)
- [Tech Stack](#tech-stack)
- [Setup](#setup)
- [Project Status](#project-status)
- [Contributors](#contributors)
- --
## Introduction
**Red, Green, VACAY!** is a chore-centric budgeting application targeted towards family units. It allows family members
to create groups and within those groups, create events to budget for as a family, allows adult users to create chores,
child users to gain earnings through the completion of chores, and then contribute those earnings to the family events.
The aim for the application is to provide a fun way to teach children to budget, while being able to contribute to, 
and even plan, a fun family outing.
- --
## Tech Stack
This project is created with:
* Java 17
* Springboot 3.4.3
* MySQL 8.0.32
* Javascript
* React 19.0.0
* Axios 1.8.4
* Bootstrap 5.3.3
* [MailHog](https://github.com/mailhog/MailHog/releases)
* [Weather Forecast API](https://open-meteo.com/en/docs?temperature_unit=fahrenheit&current=temperature_2m,is_day,rain,wind_speed_10m,wind_direction_10m&wind_speed_unit=mph&precipitation_unit=inch&forecast_days=3)
* [US Holiday API](https://date.nager.at/api/v3/NextPublicHolidays/US)
- --
## Setup
To run this project:
1. Install it locally by forking the repo and cloning it to your personal machine.
2. For the back-end (BE): Open the back-end package, labeled 'budget-planning-backend' in the IDE.
3. (BE) Set up environment variables equal to "USERNAME" and "PASSWORD" to establish a database connection on the java side.
4. (BE) Set up SQL database (this project used MySQL Workbench) with a schema named "budget-breakers" and a user with a username and password to match the environment variables in the IDE.
5. (BE) In the IDE, run the back-end application to confirm the connection between the IDE and the database. 
6. For the front-end (FE): Change directories to the frontend package, labeled as "budget-planning-frontend" in the IDE.
7. (FE) Run ```npm install``` to establish frontend dependencies.
8. (FE) Run ```npm run dev``` to start application on the front-end.
9. In the browser, navigate to [localhost:5173/Home](http://localhost:5173/Home); this is the login page of the application. Register a user, login, and start exploring!
8. This project uses MailHog to send and accept email invitations for users to join groups within the local machine; to set that up:
   9. Download [MailHog](https://github.com/mailhog/MailHog/releases)
   10. Open the downloaded file and allow the window to run in the background while application is running.
   11. In the browser, open a new tab and navigate to [localhost:8025](http://localhost:8025/); this is the inbox for receiving emails.
   12. In the application tab, navigate to the [Groups](http://localhost:5173/Groups) page, create a new group, and add an email in the "Enter emails to invite others to group" section.
   13. In the MailHog tab, there should now be a new email in the inbox with an invitation and a link. For the link to work, the person receiving the email **must be a registered user and must be logged into the application**. The link will not allow a non-registered or non-logged in user to accept the invitation. This is an intentional measure.
   14. If no email has come through, check the name of your device. The email that MailHog sends the invitation from comes from the device it runs on in the format of "deviceUserName@deviceName.com". If the device name has an unaccepted email domain character within it, i.e. an underscore or hashtag, the email format is invalid and cannot send emails.
   15. Click [here](https://kinsta.com/blog/mailhog/) for more information about MailHog.
- --
## Project Status
This project is currently still in development. It has reached the stage of a minimum viable product, but has definite room to grow. 

Some potential future developments for this project: 

* A user account page that has history of all completed chores and contributions they have made
* An option to "save up" earnings from chores rather than immediately contribute chore earnings to an event
* An option to "pay out" earnings from chores rather than immediately contribute chore earnings to an event
- --
## Contributors
* [Kayalvizhi Rajendran](https://github.com/KayalvizhiRajendran)
* [Minnie Poole](https://github.com/marionpoole)
* [Olga Karzhova](https://github.com/VolhaKarzhova)
* [Catherine Adams](https://github.com/catcadams)