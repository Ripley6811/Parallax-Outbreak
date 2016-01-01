# Parallax-Outbreak
Breakout-style game built with LibGDX


Photoshop: Used a 5x15 inch canvas with 128 pixels per inch for the nebula backdrop.
Nebulas painted in Photoshop using the "Scattered Flowers Mums" brush in the "Special Effect Brushes" set.


##References

- How to use logging - **Learning LibGDX Game Development** Chapters 2-3.
- How to use Pixmap - **Learning LibGDX Game Development** Ch. 3, Pg. 99.
- LibGDX Class Reference - **libgdx.badlogicgames.com**
- [How to display fonts - **www.gamefromscratch.com**](http://www.gamefromscratch.com/post/2013/09/26/LibGDX-Tutorial-2-Hello-World.aspx)
- [How to scale BitmapFont - **stackoverflow.com**](http://stackoverflow.com/questions/29814995/java-libgdx-bitmapfont-setscale-method-not-working)


Functionality
Build & Run
[ ] TODO: The desktop backend of the game can be built and run using Gradle without crashing, errors, or long load times.
Game Architecture
[x] TODO: The Game is structured using appropriate libGDX features including a subclass of `Game` that delegates to an implementation of `Screen`. The state of the game is regularly updated and rendered.
Gameplay Screens
TODO: The game opens with a difficulty select screen.  When a difficulty is selected, the game begins.  If the player runs out of lives, a Game Over screen is displayed, along with the score.
Rendering
TODO: Game uses ShapeRenderer or reasonable alternative to draw objects each frame.  Objects move in a smooth and continuous manner.

Gameplay
Controls
TODO: Game has user controls for desktop and mobile platforms. The app or README file contains instructions.
Object Interaction
TODO: The ball can collide with the paddle and with blocks, bouncing appropriately as described in the project description.  When hit by the ball, blocks break.
Object regeneration
TODO: Block regenerate using the rules described in the project description document.
Score & Lives
TODO: A score count is displayed that increases when a block is broken.  The number of remaining lives is displayed, which decreases every time the ball falls beyond the bottom of the screen.
Difficulty
TODO: There are multiple difficulty levels, which change the speed of the ball and the rate at which blocks regenerate.

Code Style
Object Oriented Java
TODO: Game code exhibits strong object oriented design principles, including proper use of inheritance, interfaces, and access modifiers.
Game Constants
TODO: Important values such as sprite names, dimensions, and gameplay constants are centrally organized. Hard-coded values are avoided.
Code Readability
TODO: Code is neatly formatted and structured in a way that is simple and easy to understand.