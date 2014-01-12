RsdSimpleMonitor
=======
Version 0.0.1 Created by Samad Atoro and the Reliable Soft Dev team

Introduction
------------

RsdSimpleMonitor is a simple java program that I wrote to "monitor"  web applications and websites. The application will send an email notification to a list of predefined recipients if any of the specified websites is down. The program can be used as a monitor if run as cron job or as a scheduled task on Windows.


### Running the program

1. Download the latest [RsdSimpleMonitor Jar](https://github.com/samadatoro/RsdSimpleMonitor/tree/master/target/rsd-simple-monitor-0.0.1-SNAPSHOT-shaded.jar) and rename it as simplemonitor.jar
   
2. Create a config.properties file as follow in the same directory where the jar is located.
```php
#configuration file
# urls to monitor
monitor.urls=www.yahoo.com,www.google.com
# email address to notify. leave empty if you don't want to send email notifications
monitor.notify=email@example.com
#smtp server
monitor.smtpServer=mail.example.com
# sender email
monitor.fromEmail=email@example.com
#email subject
monitor.emailSubject=Monitor Error Notification
```
3. Run the program

```bash
java -jar simplemonitor.jar
```

A log directory will be created in your current directory with two files

1. monitorOut.log contains the Log4J trace logs

2. monitorErr.log contains the error logs

### Build from the source

The project is built with Eclipse as a Maven project.  You will need to install [Maven] (http://maven.apache.org).
Confirm that maven is installed by executing the following command

```bash
mvn -v
```

Once maven is step, you can follow these steps to install and run the program. Feel free to make changes

1. Clone  [RsdSimpleMonitor] (https://github.com/samadatoro/RsdSimpleMonitor)

```bash
git clone https://github.com/samadatoro/RsdSimpleMonitor.git
```

2. Execute the following command to compile, run the junit test and package the program as a jar.

```bash
mvn package
```
The Maven pom.xml is using the [Maven Shade plugin] (http://maven.apache.org/plugins/maven-shade-plugin/) to include the dependencies in the jar and make it executable.

3.  Create a config.properties file as shown above in the project folder

4. Run the program

```bash
java -jar target/rsd-simple-monitor-0.0.1-SNAPSHOT-shaded.jar
```
