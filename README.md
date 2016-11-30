# AndroidAsyncTask-to-fetch-PHP-file-without-using-any-library

Rather than using any Library to fetch data from PHP or JSON file,you can use standard
Methods of Android AsyncTask and HTTPURLConnection class.

Advantages
- One Advantage of using Android AsyncTask over any Library is file size,using any library in your code
puts extra burde on your application in terms of application size.

the best time to use Android AsyncTask is when there are fewer requests made to the server
and in case of multiple request,use volley or any other libraries.
