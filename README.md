== ABOUT ==

!!!! IMPORTANT !!!! This is version 1 of the toolkit which is only receives maintenance updates.

Java service that exposes the a MEA node database through OData service.

== BUILDING ==

In order to build the project from sources you will need to have installed:
	- Java JDK 1.6+ (http://www.oracle.com/technetwork/java/javase/downloads/index.html)
	- Apache Ant (http://ant.apache.org/)

1. Checkout the project from SVN repository
	- use any type Subversion client (svn). For Linux is svn command line, for Windows you can use TortoiseSVN.
	- project's SVN URL is: http://svn.eaudeweb.ro/informea/trunk/odata/providers/java
				shell:$ svn checkout http://svn.eaudeweb.ro/informea/trunk/odata/providers/java informea-odata
2. Run the build
	- Using ant run the following command
				shell:$ ant
	This will build the source code and the output will be in folder bin/odata.war


== INSTALLATION ==

	1. Install servlet container (server)
		The WAR must be run inside a servlet (2.5) compatible container such as Apache Tomcat 6 (http://tomcat.apache.org/)
		Download & install a Tomcat distribution. For Linux you can install using system package manager (apt, yum etc.)
		To run the Tomcat, you will also need to install the JDK 1.6 from Sun (Oracle)

	2.
		a) Go to ${TOMCAT_HOME}/webapps and create a new directory for the web application (for example "odata")
		b) Copy the WAR archive into the in ${TOMCAT_HOME}/webapps/ozone and unpack it using zip utility. This will expand the application files and directories.
			unzip ozone-producer-1.0.war
		c) Delete the former WAR archive
			rm ozone-producer-1.0.war

		Note: If Tomcat is installed on using system package manager, the 'webapps' can be located in a different place (ex. /var/lib/tomcat6/webapps on Linux).
		Please reffer to your OS system documentation to find out where to find the webapps directory.

	3. Configuring the application
		3.1 Configuring the database connection
			Open the context configuration file located in META-INF/context.xml file.
			Here is located the JDBCP resource (<Resource>) to the database for each provider. Edit the username/password and url to point to your MySQL server.

			NON-MYSQL installations: If you are running other database server than MySQL, you will have to put its corresponding JDBC driver into WEB-INF/lib
	 		directory and configure the url in context.xml accordingly (see http://www.connectionstrings.com/ for reference).

		3.2 Securing the web application
			You can secure your web application in Tomcat. This process is described in InforMEA API. You will need to loom into web.xml and set-up the resources.
			Shortly: Enable <login-config> and <security-constraint> then edit ${TOMCAT_HOME}/conf/tomcat-users.xml to add username/password to with role "odata" that can access the application.

	4. Running the application.
		Start the Apache Tomcat service.
			Note: Depending upon the type of installation, starting the service may differ.
			a) If installed via Linux package manager, this will be installed as a service and you can use the system utilities such as
					shell:$ sudo service tomcat6 start
					or
					shell:$ sudo /etc/init.d/tomcat6 start
			b) If installed standalone, go to Tomcat 'bin' directory and run from there
					shell:$ catalina.sh run
					or, for Windows users
					cmd: catalina.bat run
		The service should start without any errors. If it does not start look into the console or logs (logs/catalina.out) for any stack trace that may hint the issues.
		If you have issues please refer to FAQ or contact support :)


	5. Browsing the application

		Open your browser and point it to http://localhost:8080/odata/
		This will open the index file that shows a summary of the running services.
		From there you can see the exposed services with links to their appropriate URL.

== FAQ ==
	Q: When I restart my service on my *NIX box, the Tomcat service is not running. Why?
	A: If you installed Tomcat by hand, you will have to configure an script in /etc/init.d/ that will start the Tomcat.

	Q: Whom may I contact if I'm in trouble?
	A: Drop an e-mail to Cristian Romanescu (cristian.romanescu _at_ eaudeweb _dot_ ro)

	Q: Where is the InforMEA API document to reffer to?
	A: Please visit http://www.informea.org/api

	Q: Are there other ways to rowse my new service?
	A: The service can be explored the web browser or using dedicated clients such as OData browser. There are other tools too:
		* http://www.silverlight.net/content/samples/odataexplorer/ (that needs Microsoft Silverlight 4 and Internet Explorer).
		* http://metasapiens.com/sesame/data-browser/preview/ (also needs Silverlight and IE)
		* Atom news readers

	Q: The service cannot connect to the database, why? (I'm getting in tomcat logs (Access denied for user 'mysql'@'localhost' to database 'DATABASE'))
	A: Please make sure your database server, for example MySQL listens on network interfaces that are accessible from the machine where Tomcat is running. Please make sure that username/passwords are correct (you can test with command mysql -h 182.33.34.1 -u username -p username DATABASE). Also make sure that your JDBC URL is correct.

	Q: Some characters appear wrong on the output.
	A: Check that MySQL and JDBC connection are configured to work in UTF-8 encoding, data from DB may come clobbered if connection is using latin1 and data is in UTF-8 or vice-versa.

	Q: My tomcat is accessible on port 8080 but is used by other applications. I want to change that, how?
	A: Go to ${TOMCAT_HOME}/conf/server.xml and look for 8080 port, change to other available value.

	Q: My firewall blocks the port 8080 and service is not visible from outside world. How do I fix this?
	A: You will have to have apache httpd server in front of the service with mod_rewrite module enabled.
	   This way, you can create a mapping between http://my.cool.website.org:8080/ozone/services/service.svc to http://my.cool.website.org/ozone.svc
           or any other mappings you can think of. Apache httpd will redirect output to port 80 which is accessible from outside.

	Q: Can I contribute with feedback?
	A: Sure! We always welcome feedback so we can improve things. Look above where to send it!

	Q: I don't like Java and I will not install JDK and Tomcat because Java is a resource hog. What can I do?
	A: We've created the provider in Java just to cover as many platforms as possible (Linux, Solaris, Windows servers etc.).
	   If you are running on Windows you are welcome to implement your service using the .NET WCF framework. As long as the service adheres to the InforMEA API specifications,
	   the internal implementation is opaque and doesn't matter.

	Q: I have some advice on my own regarding the setup for others, can I share it?
	A: Yes, please! See the feedback above!

	Q: What hardware is needed to run this?
	A: Minimum requirements for running this service is something like Pentium 3 processor and minimum 512MB or RAM (keeping in mind other services that you will probably run such as MySQL/Apache httpd), but it will swap.
	   Ideally you should have 1024 MB or more. Always fine-tune the Apache http server that is memory intensive by default.

	Q: Where are the connection details and all application settings stored?
	A: The toolkit is using Java Preferences API to store the configuration locally.
            The API is platform dependent and may choose to store the settings on registry (Windows platforms) or in XML file for non-registry implementations (Linux, BSD, Mac OSX).
            Testing shown that on CentOS Linux platform preferences are stored in home directory of the process that runs the tomcat servlet container (usually apache or root).
            For instance might be: /home/root/.java/.userPrefs/prefs.xml.
            If user does not have a home directory or JVM is not being able to write the preferences, you may get an exception in the logs such as:

                java.lang.SecurityException: Could not lock User prefs.Lock file access denied.
                    java.util.prefs.FileSystemPreferences.checkLockFile0ErrorCode(FileSystemPreferences.java:919)
                    ...
            This means that the JVM user.home directory is not correctly set, it does not exists or the process does not have write permission to the preferences file.


            The solutions are:
                1. Find out the location where the JVM is trying to write and create this file with appropriate privileges. There is no exact way to achieve this:
                    You might find out the user.home setting by compiling & running the following small java program with same user:

                        import java.util.Properties;
                        import java.util.Enumeration;

                        class test {
                        public static void main(String args[]) {
                                Properties props = System.getProperties();
                                Enumeration names = props.propertyNames();
                                while(names.hasMoreElements()) {
                                        String name = names.nextElement().toString();
                                        String value = props.getProperty(name).toString();
                                        System.out.println(name+"="+value);
                                }
                        }
                        }

                2. Change the "user.home" when the JVM starts out, for example the command line might look like java ... -Duser.home="/home/test/writable.xml" ...

            References:
                http://docs.oracle.com/javase/1.4.2/docs/guide/lang/preferences.html
                http://stackoverflow.com/questions/675864/where-are-java-preferences-stored-on-mac-os-x
