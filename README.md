# Bisq Client
A client for the [Bisq network](https://github.com/bisq-network/bisq) supporting **Android** and **iOS** platforms.

# Running the Application

## Android
Open the project in Intellij IDEA or Android Studio and run the "androidApp" configuration.

## iOS
1. Install the [CocoaPods dependency manager](https://cocoapods.org):
`gem install cocoapods`
2. Install the cocoapods-generate plugin:
`gem install cocoapods-generate`
3. Within the project root, run the following command:
`./gradlew build`
4. Within the `iosApp` folder, run the following command to install dependencies:
`pod install`
5. Open the `iosApp.xcworkspace` file in Xcode.
6. Build and run the project.

# Contributing
Refer to the wiki for project information and take a look at open issues and milestones to see what needs to be done. Though do keep in mind that as per the project plan we have defined milestones with incremental functionality to be completed sequentially.
If you are interested in contributing, please review [CONTRIBUTING.md](CONTRIBUTING.md).
