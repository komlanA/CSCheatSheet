package com.example.cscheatsheet;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);

        //ParseObject.registerSubclass(Post.class);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("9IwP3XxP0mpXccjyLXjfx17F2KlABZi1IjwCyT04")
                .clientKey("KN3leXEZ6FP8OiFHOwWi34bbiPWOg6T4fTt67fjz")
                .server("https://parseapi.back4app.com")
                .build()

        );
    }
}