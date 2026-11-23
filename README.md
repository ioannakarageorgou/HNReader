# HNReader

A small Hacker News client for Android.

I've been doing iOS for years and wanted to finally learn Android properly, so I'm building one app from scratch and adding to it bit by bit. Kotlin, Jetpack Compose, and the public [Hacker News API](https://github.com/HackerNews/API).

## Plan

- [x] Project setup
- [x] Top stories list
- [x] Loading real data from the API
- [x] Story details
- [x] Unit tests
- [ ] Comments
- [ ] Save stories for later
- [ ] Offline support
- [ ] UI tests

## Running

Open in Android Studio, let Gradle sync, hit Run.

Unit tests run on the JVM, no emulator needed: `./gradlew testDebugUnitTest`, or right-click the `test` folder in Android Studio and pick Run.
