# Spotify2
Segunda Práctica DASM

REQUISITOS

-Tener cuenta en Spotify
-Tener listas de reproducción pública

INSTALACIÓN

Para esta práctica es necesario descargarse el SDK de Android para Spotify https://github.com/spotify/android-sdk/releases (SpotifySdkAndroid-1.0.0-beta11.zip)
Y las librerías

  Picasso
  Spotify-web-android-api (https://github.com/kaaes/spotify-web-api-android)
  
Para su uso es necesario añadir las siguiente dependencias al build.gradle de la aplicación

repositories { mavenCentral() flatDir {
  dirs 'libs' }
}

dependencies {
  compile fileTree(dir: 'libs', include: ['*.jar']) 
  testCompile 'junit:junit:4.12'
  
  compile(name: 'spotify-web-api-android-0.1.1', ext: 'aar') 
  compile 'com.android.support:appcompat-v7:23.1.0' 
  compile 'com.spotify.sdk:spotify-auth:1.0.0-beta11@aar' 
  compile 'com.spotify.sdk:spotify-player:1.0.0-beta11@aar' 
  compile 'com.squareup.retrofit:retrofit:1.9.0'
  compile 'com.squareup.okhttp:okhttp:2.2.0' 
  compile 'com.android.support:design:23.1.0' 
  compile 'com.squareup.picasso:picasso:2.5.2'
}
