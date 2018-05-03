# Play REST API "Airport's statistics"

### Running

You need to download and install sbt for this application to run.

Once you have sbt installed, the following at the command prompt will start up Play in development mode:

```bash
sbt run
```

Play will start up on the HTTP port at <http://localhost:9000/>.   You don't need to deploy or reload anything -- changing any source code while the server is running will automatically recompile and hot-reload the application on the next HTTP request. 

### Usage
Query option
Type country name or code and you can see airports runways for each airport in selected country

```routes
GET /query HTTP/1.1
```

Reports option

Will print 10 countries with highest number of airports (with count) and countries with lowest number of airports.
Type of runways (as indicated in "surface" column) per country
