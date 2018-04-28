# Mancala

This repository contains the code for a very basic, 2 player turn-based version of the strategy game Mancala. Mancala is a very old game. The author's wife learned to play it in Malawi, Africa.

The current version only supports playing the game from the same computer. Future versions may allow remote play and playing against a computer. It was tested on the latest versions of Chrome and Safari. 

Warning: The UI looks ugly. No special attention was given here yet.

### Project overview

The project was developed as an [apache maven](http://maven.apache.org/) project compiling to a ***fat jar*** for Java 8. 

It uses a micro framework for web applications called [Spark](http://sparkjava.com/) to generate a single page web application based on HTML 5, CSS, Javascript and JQuery. 



### Compilation


```
mvn package
```

This will compile the source code, run the unit tests and assemble a fat jar located in the target directory: `target/mancala-1.0-SNAPSHOT.jar`

### Running the game

To run the game you will need the fat jar and Java 8 installed on your system. Then:

```
java -jar target/mancala-1.0-SNAPSHOT.jar
```

Access the game through your browser at address [http://localhost:8080/]().

### Docker integration

A *Dockerfile* exists in the project root. To build an image that can run the game in a container:

```
docker build -t mancala .
```

and to run it:

```
docker run -ti -p 8080:8080 mancala
```

### Source code overview

The main class is `com.cloudinvoke.mancala.EntryPoint`. Web resources can be found under `src/main/resources`.

#### On the use of REST vs MVC

I've opted to use REST for the server API instead of the usual Model View Controller (MVC) pattern. This is not the most all-browser friendly choice but it allowed me to expiriment with HTTP verbs like PATCH from client side Javascript.

Only one REST resource exist: A 'Game' resource on the root path (*../*).


### Code quality inspection

To generate a **unit test coverage report**:

```
mvn cobertura:cobertura
```

Then open `${prjectdir}/target/site/cobertura/index.html` in your browser.

**SonarQube analysis**:

Run a docker instance of Sonarqube:

```
docker run -d --name sonarqube -p 9000:9000 -p 9092:9092 sonarqube:7.1-alpine
```

And then scan the project against it:

```
mvn sonar:sonar
```

Access Sonarqube on [http://localhost:9000]()