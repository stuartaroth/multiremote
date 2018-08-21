# multiremote

[Screenshot](https://i.imgur.com/BGR5w6l.png)

`multiremote` is a project that started because I lost the remote to my Roku and my phone was always dead.

The application is a runnable jar that is a webserver that allows you to control multiple Rokus at a time.

To run the application simply download the jar `multiremote.jar` and run the following command:
  - `java -jar multiremote.jar`

After it has fully started simply navigate to http://localhost:8834

It was written with extendability in mind that will allow future releases to add different types of remotes,
as long as they fulfill the `Remote` interface found in the project.

Unfortunately it appears that Roku is the only manufacturer that has openly exposed its remote control API, but if you have something else you'd like to control using this interface, feel free to make a pull request.

To run the project locally you will need:
  - `gradle`
  - `npm`

The client project can be found in the `src/main/resources/client` folder in the project.
To compile the Typescript you will need install `typescript` globally. Then run the command:
  - `npm run compile`

Once the typescript has been compiled you can run the project locally using gradle in the root directory of the project:
  - `./gradlew run`

You can build the project into a fat jar using the following command:
  - `./gradlew jar`
