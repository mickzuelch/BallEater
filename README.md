# BallEater

BallEater is a small arcade-style Android game written in Java. You control a red ball that must eat green food to stay alive. If the ball shrinks too much or collides with hostile food, the game ends.

An additional **Food Escape** mode spawns food at the screen edge for a short moment before it rushes along a remembered path toward the player. If it touches the player, the ball grows; once the ball grows large enough to fill the screen the game is over. Your goal is therefore to dodge the food and keep the ball small for as long as possible.

## Building

This project uses Gradle. To build the debug APK run:

```sh
./gradlew assembleDebug
```

## Running

Install the generated APK on any device or emulator running **Android API 28** or higher. The main screen lets you start the classic "BallEater" mode or the "Food Escape" mode.

