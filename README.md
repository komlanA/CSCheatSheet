Original App Design Project - README Template
===

# CS Cheat Sheet

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
This application is to help Computer Science students by providing them with a single resource for them to study and learn about important concepts, common languages, algorithms, data structures, etc.

### App Evaluation
- **Category:** Educational
- **Mobile:** A website is view only, app will allow users to use camera for posting pictures of notes or important information.
- **Story:** Allows students to work together and share helpful information which will make learning a more communal experience. 
- **Market:** Anyone who is studying computer science or a related field, or just somebody who is interested in the subject. 
- **Habit:** Users can post any new information, but there will be a vote and discussion system on new posts and how reliable or correct the information is. The app will also be populated by posts from the developer.
- **Scope:** The CS Cheat Sheet app will start out as just an app to help study Computer Science along with a forum for discussion. It could later grow to include other areas of study, and maybe live chat rooms to discuss topics and learn from eachother.

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

1 - User can log in and user stays logged in even on app close, until they log out

2 - User can upload information for other students to see

3 - User can add and remove flashcards

**Optional Nice-to-have Stories**

4 - Forum option

5 - Up / Down vote system on posts to filter out any unnecessary or repeat posts

### 2. Screen Archetypes
* Signup Page
   * 1
* Login Page
   * 1
* Home Page
   * 2
* Flash Cards Page
   * 2
   * 3
* Feed with other students posts
   * 2
   * 4
   * 5
### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Home Page
* Flash Cards
* Feed

**Flow Navigation** (Screen to Screen)

* Login Page
   => Home Page
* Signup Page
   => Home Page
* Home Page
   => Login Page
* Feed
   => In depth Post Page (expands the user post)
   => Open page for user to write a post (photo optional)

## Wireframes
<img src="https://i.imgur.com/u1TQkGS.png" width=600>

## Schema 
[This section will be completed in Unit 9]
### Models
[Add table of models]
### Networking
- [Add list of network requests by screen ]
- [Create basic snippets for each Parse network request]
- [OPTIONAL: List endpoints if using existing API such as Yelp]
