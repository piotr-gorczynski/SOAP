Title: Short describtion how to generate SOAP client based on calc WSDL using Android Studio 3.0.1
Author: Piotr Gorczynski 2018

1. Create New project for example calcClient with empty MainActivity.java and activity_main.xml layout
2. Android Studion 3.0.1 does not contain SOAP library, therefore we need to use 3rd party library for example ksoap2-android (http://simpligility.github.io/ksoap2-android/index.html). Download the latest bundle, in this exmaple it is https://oss.sonatype.org/content/repositories/ksoap2-android-releases/com/google/code/ksoap2-android/ksoap2-android-assembly/3.6.2/ksoap2-android-assembly-3.6.2-jar-with-dependencies.jar.
3. Add to app/build.gradle in dependencies reference "compile files('libs/ksoap2-android-assembly-3.6.2-jar-with-dependencies.jar')"
4. Create simple interface capturing SOAP server URI, value a,  value b, operation and button invoking call to the SOAP server.
5. The call to the SOAP server must be implemented as a AsyncTask class extension. 