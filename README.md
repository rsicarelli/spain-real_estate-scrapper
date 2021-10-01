## Spain Real Estate Scrapper
Kotlin based scrapper that scraps real estate properties and export it to Firebase Cloud Firestore.

## Motivation
Real estate experiences lacks many features for home hunting, making it really difficult to navigate, apply filters etc. The scrap is used for personal usage only.

### Supported portals:
- [AProperties](https://www.aproperties.es/)
- [EngelVoelkers](https://www.engelvoelkers.com/)

## How to use it
1. Clone the project and open in your favourite IDE or Code Editor;
2. Follow [Firebase: create and enable service accounts](https://cloud.google.com/compute/docs/access/create-enable-service-accounts-for-instances) to create and export a new service accounts.
3. Follow [Firebase: get started with Cloud Firestore](https://firebase.google.com/docs/firestore/quickstart) to set up the database. 
4. Open `RentalPropertiesService.kt` and update default URLs. Note that the default URLs are only scrapping properties from Valencia.
5. Open `HomeHuntApplication.kt` and executes the `main()` function.
6. Grab a coffee and check the progress on console until it ends.

## Built with
- [Kotlin](https://kotlinlang.org/) :rocket:
- [Skrape{it}](https://github.com/skrapeit/skrape.it)
- [Firebase Firestore/Admin](https://firebase.google.com/docs/firestore)
- [Kotlin Logging](https://github.com/MicroUtils/kotlin-logging)
- [Koin](https://github.com/InsertKoinIO/koin)
- [JUnit5](https://junit.org/junit5/docs/current/user-guide/)
- [MockK](https://mockk.io/)

## Contributing
Any contributions you make are greatly appreciated.

1. Fork the Project
2. Create your Feature Branch (git checkout -b feature/MyFeature)
3. Commit your Changes (git commit -m 'Add some features')
4. Push to the Branch (git push origin feature/MyFeature)
5. Open a Pull Request
