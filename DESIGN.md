###High Level Design Goals

The goal of this project was to create a breakout game in which a
ball would bounce around the screen and break blocks and the player
would control a paddle to keep the ball from falling off the 
bottom of the screen.

###How to add New Features

There are several ways to add new features to the game and the 
programmer would have to decide the best way to do that depending
on the feature. Small additions to the physics of the game could be
changed in the GameDriver class. This class contains the majority
of the code that makes the game run. If the designer wanted to
add a feature that was an object that appeared on the screen in the
game the most effective and clear way to do that would probably 
be to create a new class. This is what I have done with the paddle, 
ball, and blocks. Then in the GameDriver class you can create a 
new instance of that class and add it to the root so that it will
appear in the scene.

###Major Design Choices

I wrote most of the code to play the game in a single class, GameDriver.java, 
and used other classes to create items that would appear in the screen of the game.
In hindsight, I wish that I had created more classes and split up
most of the work but allowed the classes to interact more. When I was
refactoring I created a new class that would generate the block layout
and screen for each level, rather than having that done in the GameDriver
class. Another choice I made was to have all the items in the game 
(ball, paddle, blocks) be shapes created using the shape package 
rather than to load image files from the resources database. The reason
I chose to do this is because I thought it would be easier to monitor
collisions between the ball and items with the Shape.intersect method than
to use ImageView objects. I chose to implement two significant methods
that controlled what was displayed on the screen. One of them made the menu
screen that appeared between levels and the other created the
level screen that displayed the blocks and paddle and allowed
the user to play the game.

###Assumptions
In this game I assume that the player will only press keys that
are displayed on the screen as options to press. The code also
assumes that the ball will only collide with the top or bottom of
blocks or paddle. Unfortunately this does not happen every collision
and occasionally this causes a multi hit block to be destoryed in
what appears to be one hit.
