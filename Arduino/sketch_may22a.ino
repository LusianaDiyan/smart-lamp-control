#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <ESP8266WebServer.h>

// Ganti dengan password Jaringan WiFi yang user miliki
const char* ssid = "LDN";
const char* password = "25132018";
ESP8266WebServer server(80); //Inisialisasi server pada port 80

String webpage;
String state1 = " ";
String state2 = " ";
int LED1 = D1;
int LED2 = D3;

void setup(void){
  //Inisialisasi pin padam ketika NodeMCU pertama kali menyala
  pinMode(LED1, OUTPUT);
  pinMode(LED2, OUTPUT);
  digitalWrite(LED1, LOW);
  digitalWrite(LED2, LOW);
  
  delay(1000);
  Serial.begin(115200);
  WiFi.begin(ssid, password); //begin WiFi connection
  Serial.println("");
  
  // Wait for connection
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.print("Connected to ");
  Serial.println(ssid);
  Serial.print("IP address: ");

// Isi dari Webpage -------------------------------------------------------
  webpage += "<!DOCTYPE HTML>";
  webpage += "<html>";
  webpage += "<head>";
  webpage += "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">";
  webpage += "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css\">";
  webpage += "</head><div class=\"container\">";
  webpage+= "<h1> LED Web Control ESP8266</h1>";
  webpage+= "<h3>LED Biru : " + state1 + "\n";
  webpage += "<div class=\"row\">";
  //webpage += "<div class=\"col-md-2\"><a href=\"LED1On\" class=\"btn btn-block btn-lg btn-primary\" role=\"button\">ON</a></div>";
  //webpage += "<div class=\"col-md-2\"><a href=\"LED1Off\" class=\"btn btn-block btn-lg btn-info\" role=\"button\">OFF</a></div>";
  webpage+= "<div class=\"col-md-2\"><a href=\"LED1ON\"\"><button class=\"btn btn-block btn-lg btn-primary\">ON</button></a>&nbsp;<a href=\"LED1OFF\"\"><button class=\"btn btn-block btn-lg btn-danger\">OFF</button></a></p><br></div>";
  webpage += "</div>";
  webpage+= "<h3>LED Merah : " + state2 + "\n";
  webpage += "<div class=\"row\">";
  webpage+= "<div class=\"col-md-2\"><a href=\"LED2ON\"\"><button class=\"btn btn-block btn-lg btn-primary\">ON</button></a>&nbsp;<a href=\"LED2OFF\"\"><button class=\"btn btn-block btn-lg btn-danger\">OFF</button></a></p><br></div>";
  //webpage += "<div class=\"col-md-2\"><a href=\"LED2On\" class=\"btn btn-block btn-lg btn-primary\" role=\"button\">ON</a></div>";
  //webpage += "<div class=\"col-md-2\"><a href=\"LED2Off\" class=\"btn btn-block btn-lg btn-info\" role=\"button\">OFF</a></div>";
  webpage += "</div>";

//membuat tampilan webpage
  Serial.println(WiFi.localIP());
  server.on("/", [](){
    server.send(200, "text/html", webpage);
  });

//untuk merespon perintah yang masuk --------------------------
  server.on("/LED1ON", []() {
    server.send(200, "text/html", webpage);
    digitalWrite(LED1,HIGH);
    state1 = "On";
    delay(1000);
  });
  server.on("/LED2ON", []() {
    server.send(200, "text/html", webpage);
    digitalWrite(LED2,HIGH);
    state2 = "On";
    delay(1000);
  });
  
  server.on("/LED1OFF", []() {
    server.send(200, "text/html", webpage);
    digitalWrite(LED1,LOW);
    state1 = "Off";
    delay(1000);
  });
  server.on("/LED2OFF", []() {
    server.send(200, "text/html", webpage);
    digitalWrite(LED2,LOW);
    state2 = "Off";
    delay(1000);
  });
  
  server.begin();
  Serial.println("Web server started!");
}

void loop() {
  server.handleClient();
}
