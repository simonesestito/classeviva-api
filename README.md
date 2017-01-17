<h1 align="center">ClasseViva API</h1>

<h2 align="center">
<a href="https://android-arsenal.com/api?level=8"><img src="https://img.shields.io/badge/API-8%2B-blue.svg?style=flat" border="0" alt="API"></a>
<img src="https://jitpack.io/v/simonesestito/classeviva-api.svg"/>
<a href="https://www.paypal.me/simonesestito" title="Donate using PayPal"><img src="https://img.shields.io/badge/paypal-donate-orange.svg"/></a>
</h2>

<h3 align="center">Android Library</h3> 
<h4 align="center"><i>Based on <a href="http://docs.classevivapi.apiary.io">Michelangelo Morrillo's API</a></i></h2>

## How to include
Add the repository to your **project** _build.gradle_:
 ```Javascript
repositories {
 ...
 maven {
 url "https://jitpack.io"
 } 
} 
``` 

Add the Library to your **module** _build.gradle_:
```Javascript
dependencies {
<<<<<<< HEAD
 compile 'com.github.simonesestito:classeviva-api:1.0.1'
=======
 compile 'com.github.simonesestito:classeviva-api:1.0'
>>>>>>> origin/master
 .....
}
```

##How to use
####1) Create a new ClassevivaSession,
passing your secret API key (if you don't have one, go to <a href="https://github.com/simonesestito/classeviva-api#api-key"><i><b>API Key</b></i></a> paragraph) and Context
```Java
ClassevivaSession session = new ClassevivaSession(API_KEY, getApplicationContext());
```
####2) Login
```Java
OnResultsAvailable<String> listener = new OnResultsAvailable<>(){
 @Override
 public void onResultsAvailable(String result, ClassevivaSession instance){
  //"result" is the unique session key
  //"instance" is your ClassevivaSession object
 }

 @Override
 public void onError(Exception e){
  //This method will be called if an exception has thrown during the login process
  //"e" is the caught Exception
 }
};
session.login(username, password, listener);
```

####3a) Get Grades List
```Java
OnResultsAvailable<List<Mark>> listener = new OnResultsAvailable<>(){
 @Override
 public void onResultsAvailable(List<Mark> result, ClassevivaSession instance){
  //"result" is the list of grades
  //"instance" is your ClassevivaSession object
 }

 @Override
 public void onError(Exception e){
  //This method will be called if an exception has thrown during the login process
  //"e" is the caught Exception
 }
};

instance.getMarksList(listener);
```

####3b) Get Agenda
```Java
OnResultsAvailable<List<AgendaItem>> listener = new OnResultsAvailable<>(){
 @Override
 public void onResultsAvailable(List<AgendaItem> result, ClassevivaSession instance){
  //"result" is the full agenda
  //"instance" is your ClassevivaSession object
 }

 @Override
 public void onError(Exception e){
  //This method will be called if an exception has thrown during the login process
  //"e" is the caught Exception
 }
};

instance.getAgenda(listener);
```
####WARNING:
if you call _getAgenda()_ or _getMarksList()_ **before** _login()_, you will throw an IllegalStateException!


##Api Key
If you haven't got any API Key, you have to ask it to <a href="https://github.com/Sismaa">@Sismaa</a>


##License

Copyright 2017 Simone Sestito

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
