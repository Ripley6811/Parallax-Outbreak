# Parallax-Outbreak
Breakout-style game built with LibGDX

##Introduction

I wanted to incorporate a space scene with parallax and wrap-around effects in a game. Stars and
nebulae flow across the background as the player paddle moves.

The nebulae background
(with dark spots to make the stars twinkle) was made in Adobe Photoshop CC on a 5x15 inch
canvas (128 ppi) using the "Scattered Flowers Mums" brush in the "Special Effect
Brushes" set. Stars and other assets were made programmatically with LibGDX's ShapeRenderer
or Pixmap classes.

Audio was created with [CheeseCutter SID Music Editor 2.7](http://theyamo.kapsi.fi/ccutter/about.html)
and edited with [Audacity 2.1.1](http://audacityteam.org/).

![alt tag](/screenshots/sample1.png)
![alt tag](/screenshots/11.png)

##Gameplay

There are three modes of game-play; "Easy", "Hard" and "Insane!". Ball max speed
increases with each difficulty level and the number of hits required before balls double
also increases with difficulty. "Insane!" also has *blocks regenerating* every few seconds.

**Desktop**: Use arrow keys to move the paddle and "UP" to launch the ball.

**Android**: Touch the screen to launch the ball and angle the device downward
in the direction you want the paddle to move.

When the "Ball-Split" counter reaches zero, all balls on screen will split in two,
up to a maximum of 10 balls in play.

2 points for every block hit and negative 1 point for each paddle hit.

When the game ends, tap or click on screen to return to the options menu.

*Tip:* For maximum damage, try to get a ball in a good position above a set of
blocks before a ball-split event.

*Tip:* No need to chase balls, wait for them to wrap around and enter the screen
from the other side.

##References

- Udacity's *2D Game Development with LibGDX* course - **www.udacity.com**
- How to use logging - **Learning LibGDX Game Development** Chapters 2-3.
- How to use Pixmap - **Learning LibGDX Game Development** Ch. 3, Pg. 99.
- LibGDX Class Reference - **libgdx.badlogicgames.com**
- [How to display fonts - **www.gamefromscratch.com**](http://www.gamefromscratch.com/post/2013/09/26/LibGDX-Tutorial-2-Hello-World.aspx)
- [How to scale BitmapFont - **stackoverflow.com**](http://stackoverflow.com/questions/29814995/java-libgdx-bitmapfont-setscale-method-not-working)
- [How to compare strings in Java - **stackoverflow.com**](http://stackoverflow.com/questions/513832/how-do-i-compare-strings-in-java)
- [How to use Android back button - **stackoverflow.com**](http://stackoverflow.com/questions/7223723/in-libgdx-how-do-i-get-input-from-the-back-button)
- [How to add sound effects - **github.com**](https://github.com/libgdx/libgdx/wiki/Sound-effects)
