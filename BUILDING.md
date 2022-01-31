This project needs Java 16 or higher, because it uses Java records.

The standard Java version that we use for this project is `16.0.2-zulu`.

# Getting Started

If you're using sdkman, you can get started with these commands.

Here, `$PROJECT_ROOT` means the root directory of this project.

```bash
$ sdk install java 16.0.2-zulu
$ sdk use java 16.0.2-zulu
$ cd $PROJECT_ROOT
$ ./gradlew clean test
```

We have not (yet) configured this project to run as a Java application
using the Gradle application for Java applications, so `./gradlew run`
will not work. We are focusing on running the code with tests.

