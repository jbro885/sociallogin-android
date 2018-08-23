# Social Login
Implementaion for social login on android through facebook, twitter and google
![demo](https://i.imgsafe.org/bf/bffa688c7c.png)

# License

`Social Login` is opensource, contribution and feedback are welcome!

[Apache Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)

Setup

**Enable Google Login**
```
  compile 'com.google.android.gms:play-services-auth:15.0.1'
```

1. Create app in Google Developer Console:

    https://developers.google.com/mobile/add?platform=android

2. Enable Google Signin API.

3. Get Web Client id in Oauth 2.0 client ids area:

    https://console.developers.google.com/apis/credentials

4. Set this Client id in helper class :

    ```
    GoogleHelper.setClient(<CLIENT ID>)
    ```

Note: If you want google+ information follows these steps:

  1. Make sure that Google+ Api is enabled in Google Developer Console.

  2. Include this dependency:

```
  compile 'com.google.android.gms:play-services-plus:15.0.1'
```

 Then, you can access all information through person object in GoogleHelper success callback.

**Enable Facebook Login**
```
compile 'com.facebook.android:facebook-android-sdk:4.5.0'
```

1. Create app in Facebook Developer Console.

2. Follow the steps mentioned here :

   https://developers.facebook.com/docs/android/getting-started/


**Enable Twitter Login**
```
compile('com.twitter.sdk.android:twitter:1.13.1@aar')
```

You need to approve your application for email access at:

   https://support.twitter.com/forms/platform

```
Copyright 2018 Supercharge

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
# Author

[Santosh](https://github.com/santoshhiremani)   
