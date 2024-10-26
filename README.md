This app makes use of the MBTA's public API to fetch upcoming departures from the station of the user's choosing.

If you want to run the app yourself, you'll first need to get an API key from the MBTA website: https://api-v3.mbta.com/portal
Then, add your key to your local.properties file like this `mbta_api_key=key_goes_here`

This is very much still a work in progress. Here are some things that I'd like to add as I continue to work on this:

- Use the user's location to find the nearest stops
- Store data in a db since most of this data doesn't update frequently
- Integrate with some mapping sdk to display the routes and live vehicle positions