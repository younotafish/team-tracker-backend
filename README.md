# Project Name： Team Tracker

## frontend repo： https://github.com/MoonJiao123/team-tracker-frontend.git
## backendrepo： https://github.com/younotafish/team-tracker-backend.git

## Introduction ：
A teamwork collaboration mini app which help you and your team manage projects efficiently.
[youtube demo viedo](https://www.youtube.com/watch?v=LiQBcPudNH4)
## 项目背景：
项目管理、任务规划是每个人、每个团队都会遇到的问题。在校园中，不论是社团的任务规划，还是课堂大作业的协作，大部分都是基于TIM甚至微信的管理，我们常常会陷入时间线混乱，文件杂乱不堪，责任分工不明确，进度难以掌控的局面。为了解决这个痛点，我们决定开发Team Tracker
## 项目框架：
在我们的初步设想中，项目主要包括一下几个功能。
## 任务分配：
项目的发起人可以设置不同的任务块，任务块可以继续划分子任务块。
## 权限管理：
不同的人对不同的任务块有不同的管理权限。项目发起人是超级用户，对所有的项目有所有的权限，具体负责人对自己负责的项目有所有权限，其他人员对不属于自己的任务块只有查看权（类似于操作系统的权限管理777）。
## 进度掌控：
任务分成：待进行，进行中，已完成三个状态，长按删除，左滑把任务移到下一个状态


## 前端说明
* git clone https://github.com/MoonJiao123/team-tracker-frontend.git
* cd client
* npm i
* npm install wepy-cli -g
* npm run dev:weapp  

## 前端项目结构
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
## 后端说明
* git clone https://github.com/younotafish/team-tracker-backend.git
* 参考heroku运行spring boot项目
  
## 后端项目结构

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



