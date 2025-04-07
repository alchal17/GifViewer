# GifViewer

## Description

An Android application for browsing a list of popular GIFs with opportunity to download them with an
[external API](https://developers.giphy.com/docs/api/endpoint/#trending).

## Pre requirements:

Create **local.properties** file in root directory and add sdk.dir=<your-sdk-dir> path and API_KEY=<
your-api-key>

### Libraries

* **Ktor-client** - sending requests to API
* **Coil** - showing GIF based on it's URL
* **Navigation-Compose** - changing components bases on the current route and passing arguments from
  one route to another
* **Koin** - dependency injection