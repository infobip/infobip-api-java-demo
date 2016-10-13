# Infobip API Java Demo Application
Simple desktop application written in Java, illustrating integration with Infobip's SMS API. All of the documentation of the API methods used in this app can be found at [dev.infobip.com](https://dev.infobip.com/).

## Requirements
* Java version 8 or later. You can find JDK at [Oracle downloads pages](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* Maven version 3. You can find download instructions at [Maven Project downloads page](http://maven.apache.org/download.cgi) and for installation instructions take a look at [installation guide](http://maven.apache.org/install.html)
* Git, which if you do not already have you can find ad [git downloads page](https://git-scm.com/downloads)

## Building the app
To clone this repository using git choose a directory on your computer, point your favorite command language interpreter to it and execute:

	git clone https://github.com/infobip/infobip-api-java-demo.git
	
After that you should be left with a local copy of this repository. To install the demo application execute:

	mvn install
	
That should have pulled application's dependencies (it depends only on [Infobip API client](https://github.com/infobip/infobip-api-java-client)) and built the application. As a result there should now be a `target` directory with, among other things, `DemoApp.jar` file in it. 

## Running the app
To run the app position yourself in the `target` directory and than execute:

	java -jar DemoApp.jar
	
That should start the app and present you with the log in screen. There you can enter your Infobip account credentials and start playing around with sending smses and reviewing logs and delivery reports.