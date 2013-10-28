Android Hybrid allow you to build native mobile applications using HTML, CSS and JavaScript
======

###Objective
Android Hybrid demonstrate the way of accessing native functions from javascript and vice-versa.

###Structure

* Web project should be inside the assets folder `WebPlayer/src/main/assets/wwww`
* Native code should be inside src folder `WebPlayer/src/main/java`

###The Mechanism
######MyActivity.java
```java
  @Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
  
      WebView webView = (WebView) findViewById(R.id.webView);
      webView.addJavascriptInterface(new JavaScriptAccessor(), "AndroidFunction");
      webView.getSettings().setJavaScriptEnabled(true);
      webView.loadUrl("file:///android_asset/www/index.html");
  }
  
  public class JavaScriptAccessor {
  
      public void doSomethingNative() {
          runOnUiThread(new Runnable() {
              public void run() {
                 //TODO: Do something here
              }
          });
      }
  }  
```

####Comunication: Web -> Native
######Index.html
```javascript
  <script type="text/javascript">
  
      function setName(name) {
          AndroidFunction.setName(name);
      }
  </script>
```
```html
  <body>
    <input type="button" class="button" value="Hello Telefonica" onClick="setName('Hello Android!')" />
  </body>
```
######MyActivity.java
```java
  public class JavaScriptAccessor {
  
      public void setName(final String name) {
          runOnUiThread(new Runnable() {
              public void run() {
                  nativeComponent.setText(name);
              }
          });
      }
  }
```

####Comunication: Native -> Web
######Index.html
```javascript
  <script type="text/javascript">
  
    function callFromActivity(msg){
        var element = document.getElementById("result");
        element.innerHTML = msg;
    }
  </script>
```
```html
  <body>
    <div id="result"></div>
  </body>
```
######MyActivity.java
```java
  runOnUiThread(new Runnable() {
      public void run() {
          webView.loadUrl("javascript:callFromActivity(\""+pausedTime+"\")");
      }
  });
```
