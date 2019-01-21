game
====

This project implements the game of Breakout.

Name: Louis Jensen

### Timeline

Start Date: January 13, 2019

Finish Date: January 21, 2019

Hours Spent: ~24

### Resources Used
https://gamedevelopment.tutsplus.com/articles/gamedev-glossary-what-is-the-game-loop--gamedev-2469
https://www.tutorialspoint.com/javafx/index.htm                                                             
https://carlfx.wordpress.com/2012/03/29/javafx-2-gametutorial-part-1/ (Parts 1-4)


### Running the Program

Main class: RunGame.java

Data files needed: none

Key/Mouse inputs: 
* Left arrow key: moves paddle left
* Right arrow key: moves paddle right

Cheat keys:
* L button: adds a life to the current player
* S button: clears current level and skips to next level


Known Bugs:
* The ball bounces vertically off of paddle so if it intersects paddle in small gap on the side it will appear to bounce through paddle.
* Very rarely the ball will appear to destroy a two-hit or three-hit block in one hit. I believe this is because of the same problem described above.

### Notes
The game contains three levels. Each level gets progressively harder by adding more blocks, changing the ball configuration, and increasing the ball speed.
Each level ends when the player has destroyed all the blocks on the screen, or run out of lives. Upon finishing the level the player will be prompted to replay 
the level, continue to the next level, or play the game. This prompt will appear regardless of level outcome but the next level key will only work if the level
was completed succesfully.

Three different blocks appear in the game. They are different colors and the colors represent the number of hits left until they will be destroyed. 
Orange blocks require three more hits, blue blocks require two more, and purple blocks only one. Additionally, each block has a one in ten chance of
activating a power up. That power up will be generated randomly out of two options.

The three power up options are:
* Slow ball (reduces ball speed by 50%)
* Big paddle (increases paddle speed by 50%)

These power ups last until a life is lost. Slow ball is indicated by the ball turning gold and big paddle is indicated by the paddle turning gold.

A couple other things to note about the paddle are that if the ball lands in the middle third it will bounce off at the same angle that it came in. If it
hits the left third it will angle slightly more to the left and if it hits the right third it will angle slightly more to the right.


### Impressions
I really enjoyed this project and think it is a good introduction to the course. When I first started I did not realize the scope of this project
and I wish that I had more time to continue working on it so I could continue making improvements. However, I know that I learned a lot about
game design and how to use JavaFX.

