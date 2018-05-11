# Mancala

This repository contains the code for a very basic, 2 player turn-based version of the strategy game Mancala. Mancala is a very old game. The author's wife learned to play it in Malawi, Africa.

The current version only supports playing the game from the same computer. Future versions may allow remote play and playing against a computer. It was tested on the latest versions of Chrome and Safari. 

Warning: The UI looks ugly. No special attention was given here yet.

### Project overview

The project was developed as an [apache maven](http://maven.apache.org/) project compiling to a ***fat jar*** for Java i10. 

It uses a micro framework for web applications called [Spark](http://sparkjava.com/) to generate a single page web application based on HTML 5, CSS, Javascript and [jQuery](https://jquery.com). 



### Compilation


```
mvn package
```

This will compile the source code, run the unit tests and assemble a fat jar located in the target directory: `target/mancala-1.0-SNAPSHOT.jar`

### Running the game

To run the game you will need the fat jar and Java 10 installed on your system. Then:

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

The main class is `com.cloudinvoke.mancala.EntryPoint`. Web resources can be found under `src/main/resources`. The web layer is located in the main package `com.cloudinvoke.mancala` and business logic in `com.cloudinvoke.mancala.gamelogic`. Lastly `com.cloudinvoke.mancala.dto` contains the DTOs used to communicate with the web client.

#### Public fields in DTOs

Classes in the package `com.cloudinvoke.mancala.dto` have public fields. This may be frowned upon by some. I've chosen to deliberately do this after being inspired by Robert C. Martin's writeup on the difference between an object and a data structure in his book [Clean Code](https://www.amazon.com/Clean-Code-Handbook-Software-Craftsmanship/dp/0132350882).

The DTO classes in this package represent data structures and not OO objects i.e. we don't aim for encapsulation. They contain zero business logic. Getters/setters are omitted where possible 
to clarify intent (data structures not objects) and to make code more readable. Their use is not a violation of the [Law of Demeter](https://en.wikipedia.org/wiki/Law_of_Demeter).

#### On the use of REST vs MVC

I've opted to use REST for the server API instead of the usual Model View Controller (MVC) pattern. This is not the most all-browser friendly choice but it allowed me to expiriment with HTTP verbs like PATCH from client side Javascript and it also seemed simpler for this small project. I didn't want to add a big web framework. My fat jar clocks in under 5MB.

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

Note: Sonarqube complains about my public fields but as mentioned, their use is deliberate.
