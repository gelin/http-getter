Summary
=======

Android application with one big button to open an URL on demand.

Story
-----

The need came. The need to open one CGI script URL to mark messages as read. To do it simply, quickly and frequently from an Android phone. To do it only after explicit pressing the button (not silently in background, not to refresh the page). This is why a browser bookmark doesn't fit here. A browser is slow to start. And a browser can suddenly requery the URL when you can back to it from another application. Also it's necessary to ignore SSL errors.

Features
--------

* a big button to open the URL
* you can define any URL
* the URL is opened only after explicit button press
* the URL is not reloaded when you come back to the application
* the URL content (HTML page) is displayed in WebView
* SSL errors are ignored
* Basic HTTP auth can be used
* scan QR code for long URLs

Download
--------

[Google Play](https://play.google.com/store/apps/details?id=ru.gelin.android.getter)
