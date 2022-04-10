# Advanced Android Weather App using MVVM Architecture Sample (ViewModel + LiveData + Kotlin + volley) = Weather App

## Introduction
> - MVVM Architecture is one of the most popular and latest architecture to develop a maintanable and managable codebase.
> - Advanced WyyK Weather App is an Android App build with MVVM Architecture using Kotlin language and Volley network calling library.
> - The app shows your current location, and a list of cities showing current temperature of the city. (I used a total of 20 cities to display on the recycler view).
> - When a city is clicked, it will direct user to deatailed fragment whereby user will be able to see the current weather state 
> (Shown in the screenshots below) of the city and forecast of the next 6 days. User can add city to his/her favorite lists.
> - To add a city to favorite list, User will have to create an account so that data will not be lost if app is uninstalled or data is cleared.


[![forthebadge](https://forthebadge.com/images/badges/built-with-love.svg)](https://forthebadge.com)

[![forthebadge](https://forthebadge.com/images/badges/built-with-swag.svg)](https://forthebadge.com)

<a href='https://ko-fi.com/wycliffn2291' target='_blank'><img height='36' style='border:0px;height:36px;' src='https://az743702.vo.msecnd.net/cdn/kofi2.png?v=0' border='0' alt='Buy Me a Coffee at ko-fi.com' /></a>

Still in Active Development.


## Screenshots for Iphone

<img src="https://user-images.githubusercontent.com/46722362/162608718-55030008-573f-4ce8-91b7-5bbe1336c3a7.png"
      data-canonical-src="https://user-images.githubusercontent.com/46722362/162608718-55030008-573f-4ce8-91b7-5bbe1336c3a7.png"
       width="220" height="450" />
       <img src="https://user-images.githubusercontent.com/46722362/162608725-7612cc37-4fe1-4503-bd31-315e7d756733.png"
      data-canonical-src="https://user-images.githubusercontent.com/46722362/162608725-7612cc37-4fe1-4503-bd31-315e7d756733.png"
       width="220" height="450" />
       <img src="https://user-images.githubusercontent.com/46722362/162608732-b6086ea1-b672-43b9-887f-5fe661f595d6.png"
      data-canonical-src="https://user-images.githubusercontent.com/46722362/162608732-b6086ea1-b672-43b9-887f-5fe661f595d6.png"
       width="220" height="450" />
       <img src="https://user-images.githubusercontent.com/46722362/162608727-c0cf1552-0112-41d3-a6c6-9df9dce46858.png"
      data-canonical-src="https://user-images.githubusercontent.com/46722362/162608727-c0cf1552-0112-41d3-a6c6-9df9dce46858.png"
       width="220" height="450" />
       <img src="https://user-images.githubusercontent.com/46722362/162608729-3fc47637-0983-4310-8a5c-6196d9d05efa.png"
      data-canonical-src="https://user-images.githubusercontent.com/46722362/162608729-3fc47637-0983-4310-8a5c-6196d9d05efa.png"
       width="220" height="450" />
       <img src="https://user-images.githubusercontent.com/46722362/162608730-64f2a187-f065-42fc-be66-ddbeb1a594be.png"
      data-canonical-src="https://user-images.githubusercontent.com/46722362/162608730-64f2a187-f065-42fc-be66-ddbeb1a594be.png"
       width="220" height="450" />
       <img src="https://user-images.githubusercontent.com/46722362/162608733-e3e4af2b-e7b1-4502-b024-3ee515a7b63b.png"
      data-canonical-src="https://user-images.githubusercontent.com/46722362/162608733-e3e4af2b-e7b1-4502-b024-3ee515a7b63b.png"
       width="220" height="450" />
       
       
## Run the project
> - Sync the Gradle and run the project. Install APK on your emulator or real device.
> - Turn on the internet of your testing device. For better understanding, please read the comments of every methods.
> - Hope, these comments will help you to feel the MVVM Architecture.

## Disclaimer
> - There are some other ways of implementation of MVVM.
> - We find most of the MVVM tutorials are covered with Rx and Dagger. But it's not mandatory to use Rx or Dagger in MVVM. 
> - The main difference between MVP and MVVM is: Presenter is not Life Cycle aware. On the otherhand ViewModel is Life Cycle aware.
