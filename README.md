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
### Models
#### Post
   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | String   | unique id for the user post (default field) |
   | author        | Pointer to User| image author |
   | image         | File     | image that user posts for notes/questions |
   | caption       | String   | caption about the notes or it can be a question from the user |
   | commentsCount | Number   | number of comments that has been posted to an image |
   | upVotes    | Number   | number of up votes for the post |
   | downVotes    | Number   | number of down votes for the post |
   | createdAt     | DateTime | date when post is created (default field) |
   | updatedAt     | DateTime | date when post is last updated (default field) |

### Networking
#### List of network requests by screen
   - Home Page Feed
      - (Read/GET) Query all posts where user is author
         ```swift
        // Specify which class to query
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                for (Post post: posts) {
                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
                }
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
            }
         ```
   - Feed
      - (Create/POST) Create a new post object
        ```swift
        // Specify which class to query
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                for (Post post: posts) {
                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
                }
                adapter.clear();
                adapter.addAll(posts);
                // Now we call setRefreshing(false) to signal refresh has finished
                swipeContainer.setRefreshing(false);
            }
         ```
      - (POST) Cast up vote
        ```swift
        // Get the post, then update the up vote counter for the post. Once the up vote counter is updated, do a refresh
        // of the page to show that there was a change in the number of votes
        ```
      - (POST) Cast down vote
        ```swift
        // Get the post, then update the down vote counter for the post. Once the up vote counter is updated, do a refresh
        // of the page to show that there was a change in the number of votes
        ```
