# Project Name： Team Tracker(backend part)

## frontend repo： https://github.com/MoonJiao123/team-tracker-frontend.git
## backendrepo： https://github.com/younotafish/team-tracker-backend.git

## Introduction ：
A teamwork collaboration mini app which help you and your team manage projects efficiently.
To have a quick view, click the picture below which will route you to the YouTube page
[![youtube demo video](Readme/demo.png)](https://www.youtube.com/watch?v=LiQBcPudNH4)
## Project Background：
Every time when we need to cooperate within a big team, we will always fall into the inefficiency trap. We always fail to handle the progress, the file is always messy and the responsibility is always unclear. It's all because we don't have a suitable tool. For this reason, we developped this app: Team Tracker 
## Project main functionality：
1. start new project
2. join a new project
3. invite teammate to join a project
4. update project description
5. create tasks within a project
6. update tasks
7. assign priority to tasks
8.  assign tasks to group member
9.  Pin task to the top
## Authorization Management：
1. group member will have different role and the project owner can manage the authorization in a project.
## Progress Management：
1. tasks are classified to different status: Todos, Doings, Dones. And the project owner can change the status so that each member can know how much work has been done and how much hasn't.


## How to run frontend
* git clone https://github.com/MoonJiao123/team-tracker-frontend.git
* cd client
* npm i
* npm install wepy-cli -g
* npm run dev:weapp  

## frontend structure
```
├── app.config.js
├── app.js
├── app.scss
├── components                          所有的组件
│   ├── AddProjectButton.js
│   ├── AddTask.js
│   ├── Board.js
│   ├── CurrentMembers.js
│   ├── DeleteProjectButton.js
│   ├── InviteMembters.js
│   ├── Logo.js
│   ├── Navbar.js
│   ├── ProjectForm.js
│   ├── ProjectInfo.js
│   ├── SearchTask.js
│   ├── Searchbar.js
│   ├── Task.js
│   ├── Taskitem.js
│   ├── images
│   │   ├── background.png
│   │   ├── delete.png
│   │   ├── home.png
│   │   ├── logo.png
│   │   ├── manager.png
│   │   ├── minus.png
│   │   ├── plus.png
│   │   ├── speech.png
│   │   └── team.png
│   ├── index.jsx
│   └── login.js
├── custom-variables.scss
├── index.html
├── pages
│   ├── index
│   │   ├── index.config.js             首页
│   │   ├── index.jsx
│   │   └── index.scss
│   └── project
│       ├── project.config.js           每个项目的页面
│       ├── project.jsx
│       └── project.scss
└── util
    └── v-request.js
```
## how to run backend
* git clone https://github.com/younotafish/team-tracker-backend.git
* click [Deploying / Hosting Spring Boot applications on Heroku](https://www.callicoder.com/deploy-host-spring-boot-apps-on-heroku/)
  
## backend structure

```
├── mvnw
├── mvnw.cmd
├── pom.xml
├── ppmtool.iml
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── teamtracker
│   │   │           └── backend
│   │   │               ├── BackendApplication.java
│   │   │               ├── controller
│   │   │               │   ├── ProjectController.java
│   │   │               │   └── TaskController.java
│   │   │               ├── domain
│   │   │               │   ├── Project.java
│   │   │               │   ├── ProjectNameAndStatus.java
│   │   │               │   ├── ProjectTask.java
│   │   │               │   └── User.java
│   │   │               ├── exceptions
│   │   │               │   ├── CustomResponseEntityExceptionHandler.java
│   │   │               │   ├── ProjectNameException.java
│   │   │               │   ├── ProjectNameExceptionResponse.java
│   │   │               │   ├── ProjectNotFoundException.java
│   │   │               │   ├── ProjectNotFoundExceptionResponse.java
│   │   │               │   ├── UserNameException.java
│   │   │               │   └── UserNameExceptionResponse.java
│   │   │               ├── repository
│   │   │               │   ├── ProjectRepository.java
│   │   │               │   ├── ProjectTaskRepository.java
│   │   │               │   └── UserRepository.java
│   │   │               └── service
│   │   │                   ├── MapValidationErrorService.java
│   │   │                   ├── ProjectService.java
│   │   │                   ├── TaskService.java
│   │   │                   └── UserService.java
│   │   └── resources
│   │       └── application.properties
│   └── test
│       └── java
│           └── com
│               └── teamtracker
│                   └── backend
│                       └── BackendApplicationTests.java
├── target
│   ├── classes
│   │   ├── META-INF
│   │   │   └── ppmtool.kotlin_module
│   │   ├── application\ 2.properties
│   │   ├── application.properties
│   │   └── com
│   │       └── teamtracker
│   │           └── backend
│   │               ├── BackendApplication\ 2.class
│   │               ├── BackendApplication.class
│   │               ├── controller
│   │               │   ├── ProjectController\ 2.class
│   │               │   ├── ProjectController.class
│   │               │   ├── TaskController\ 2.class
│   │               │   └── TaskController.class
│   │               ├── domain
│   │               │   ├── Project\ 2.class
│   │               │   ├── Project.class
│   │               │   ├── ProjectNameAndStatus.class
│   │               │   ├── ProjectTask\ 2.class
│   │               │   ├── ProjectTask.class
│   │               │   ├── User\ 2.class
│   │               │   └── User.class
│   │               ├── exceptions
│   │               │   ├── CustomResponseEntityExceptionHandler\ 2.class
│   │               │   ├── CustomResponseEntityExceptionHandler.class
│   │               │   ├── ProjectNameException\ 2.class
│   │               │   ├── ProjectNameException.class
│   │               │   ├── ProjectNameExceptionResponse\ 2.class
│   │               │   ├── ProjectNameExceptionResponse.class
│   │               │   ├── ProjectNotFoundException\ 2.class
│   │               │   ├── ProjectNotFoundException.class
│   │               │   ├── ProjectNotFoundExceptionResponse\ 2.class
│   │               │   ├── ProjectNotFoundExceptionResponse.class
│   │               │   ├── UserNameException\ 2.class
│   │               │   ├── UserNameException.class
│   │               │   ├── UserNameExceptionResponse\ 2.class
│   │               │   └── UserNameExceptionResponse.class
│   │               ├── repository
│   │               │   ├── ProjectRepository\ 2.class
│   │               │   ├── ProjectRepository.class
│   │               │   ├── ProjectTaskRepository.class
│   │               │   ├── UserRepository\ 2.class
│   │               │   └── UserRepository.class
│   │               └── service
│   │                   ├── MapValidationErrorService\ 2.class
│   │                   ├── MapValidationErrorService.class
│   │                   ├── ProjectService\ 2.class
│   │                   ├── ProjectService.class
│   │                   ├── TaskService\ 2.class
│   │                   ├── TaskService.class
│   │                   ├── UserService\ 2.class
│   │                   └── UserService.class
│   ├── generated-sources
│   │   └── annotations
│   ├── generated-test-sources
│   │   └── test-annotations
│   ├── maven-archiver
│   │   └── pom.properties
│   ├── maven-status
│   │   └── maven-compiler-plugin
│   │       ├── compile
│   │       │   └── default-compile
│   │       │       ├── createdFiles.lst
│   │       │       └── inputFiles.lst
│   │       └── testCompile
│   │           └── default-testCompile
│   │               ├── createdFiles.lst
│   │               └── inputFiles.lst
│   ├── surefire-reports
│   │   ├── TEST-com.teamtracker.backend.BackendApplicationTests.xml
│   │   └── com.teamtracker.backend.BackendApplicationTests.txt
│   ├── teamtracker-0.0.1-SNAPSHOT.jar
│   ├── teamtracker-0.0.1-SNAPSHOT.jar.original
│   └── test-classes
│       └── com
│           └── teamtracker
│               └── backend
│                   ├── BackendApplicationTests\ 2.class
│                   └── BackendApplicationTests.class
└── teamtracker.iml
```



