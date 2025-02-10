# Github
This repository is for an android application that shows list of Github users fetch from https://api.github.com and also shows selected user details.

## Following libraries are being used:
#### UI - Jetpack Compose  
#### Theming - Material3  
#### Database - Room  
#### Remote Calls - Retrofit2,Okhttp3  
#### Json Parsin - Gson  
#### Dependency Injection - Hilt  
#### Paging - Paging3  
#### Image Caching - Coil  

## Improvements for future:  
### Architectural Improvements :  
  #### Although I started this project with AIM to implement CLEAN architecture but there is still scope for improvement.  
  #### Add UseCases instead of directly using data sources directly in ViewModels.  
  #### Add base class for Exceptions related to Network calls for better error handling.  
  #### Segregate data models to a different module.  

### UI Improvements :  
  #### Implement UI specific for large screens.   
  #### Implement common TopApp bar for the application screen.  
  #### Minimise usage of activities and replace with fragments where possible.  
  #### Improve UserDetail screen by adding animation for resizing profile image as user scrolls.  
  #### Also show partial list of followers and following on detail screen along with button show list of followers,following,public repos and Gists for user.  
  #### Improve error UI by using Snackbar or dialog.  

### API call:
  #### Increase rate limit by using API Token or authentcating users.  


### Testing:
 #### Currently unit tests are very limited. Increase code coverage by writing unit tests.  
 #### Also add instrumentation tests.  

  
