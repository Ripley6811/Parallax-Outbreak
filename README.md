# Parallax-Outbreak
Breakout-style game built with LibGDX

##Introduction

I wanted to incorporate a space scene with parallax effects in a game. Stars and
nebulae flow across the background as the player paddle moves. The nebulae background
(with dark spots to make the stars twinkle) was made in Photoshop on a 5x15 inch
canvas (128 ppi) using the "Scattered Flowers Mums" brush in the "Special Effect
Brushes" set. Stars and other assets were made programmatically with LibGDX's ShapeRenderer
or Pixmap classes.

##Gameplay

There are three modes of gameplay; "Easy", "Hard" and "Insane!". Ball max speed
increases with each difficulty level and the number of hits required before balls double
also increases with difficulty. "Insane!" also has blocks regenerating every few seconds.

**Desktop**: Use arrow keys to move the paddle and launch the ball.

**Android**: Touch the screen to launch the ball and angle the device downward
in the direction you want the paddle to move.

When the game ends, tap or click on screen to return to the options menu.

##References

- How to use logging - **Learning LibGDX Game Development** Chapters 2-3.
- How to use Pixmap - **Learning LibGDX Game Development** Ch. 3, Pg. 99.
- LibGDX Class Reference - **libgdx.badlogicgames.com**
- [How to display fonts - **www.gamefromscratch.com**](http://www.gamefromscratch.com/post/2013/09/26/LibGDX-Tutorial-2-Hello-World.aspx)
- [How to scale BitmapFont - **stackoverflow.com**](http://stackoverflow.com/questions/29814995/java-libgdx-bitmapfont-setscale-method-not-working)
- [How to compare strings in Java - **stackoverflow.com**](http://stackoverflow.com/questions/513832/how-do-i-compare-strings-in-java)
- [How to use Android back button - **stackoverflow.com**](http://stackoverflow.com/questions/7223723/in-libgdx-how-do-i-get-input-from-the-back-button)
