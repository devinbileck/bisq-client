# Bisq Client
A client for the [Bisq network](https://github.com/bisq-network/bisq) supporting **Android**, **iOS**, **Desktop**, and **Web** platforms.

# Running the Application

# Android
Open the project in Intellij IDEA or Android Studio and run the "androidApp" configuration.

# iOS
Open and build the Xcode project located in the iosApp folder.

## Desktop
```
./gradlew :desktopApp:run
```

To build a native desktop distribution:
```
./gradlew :desktopApp:package
# output is written to desktopApp/build/compose/binaries
```

# Web
```
./gradlew :webApp:jsBrowserDevelopmentRun
```

# Contributing
Refer to the wiki for project information and take a look at open issues and milestones to see what needs to be done. Though do keep in mind that as per the project plan we have defined milestones with incremental functionality to be completed sequentially.
If you are interested in contributing, please review [CONTRIBUTING.md](CONTRIBUTING.md).
