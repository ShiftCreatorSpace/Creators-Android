Creators Co-Op: Android
===========================

Coming soon: an easy way to keep up with all of our projects, events, and creators at the Creators Co-Op! We're not yet ready for a 1.0 release (despite the default version number) but come back as soon as September hits. Better yet, feel free to contribute!


Building
-----

The project has been built with Android Studio Beta, and consequently uses the Gradle build system.
If you have used Gradle before, great! If you haven't, you might want to take a look at some guides online.

Before you get started, you'll need your Parse App ID and Client Key. Add these to the java properties file located in `~/.gradle/gradle.properties`. Until you add these, Gradle will complain that you haven't set the values.

To build the project for debug, you should only need to import it into Android studio. You may also build it from the command line, using:

`$ ./gradlew clean assembleDebug`

If you plan on building this project for release, you'll need to add a `signingConfig` block to app/build.gradle with your keys. This will depend on how you plan on signing it.


Credits
-----

As of this writing, this project shares much of its code base with the [MHacks Android App](https://github.com/mhacks/MHacks-Android "MHacks-Android"), since they require many of the same core features.

We would also like to thank the maintainers of the following:

- [Square's Picasso Image Library](http://square.github.io/picasso/ "Picasso")
- [Guava Google Core Libraries](https://code.google.com/p/guava-libraries/ "guava-libraries")
- [Parse Android Library](https://parse.com/docs/android_guide "Parse Android Developer Guide")


License
-----

Stay tuned.