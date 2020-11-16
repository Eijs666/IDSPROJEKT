#include <Arduino.h>
#include "WiFi.h"
#include "AsyncUDP.h"

const char * ssid = "AndroidAP";
const char * password = "zczf2021";

//Jostick værdier
int x = 34;
int y = 33;
int joysw = 32;

boolean stopInit = false;

AsyncUDP udp;

boolean buttonIsPressed = false;

void setup() {
  // initialize the joystick as an input:
  pinMode(x, INPUT);
  pinMode(y, INPUT);
  pinMode(joysw, INPUT);

  Serial.begin(9600);
  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);
  if (WiFi.waitForConnectResult() != WL_CONNECTED) {
    Serial.println("WiFi Failed");
    while (1) {
      delay(1000);
    }
  }
  if (udp.listen(4000)) {
    Serial.print("UDP Listening on IP: ");
    Serial.println(WiFi.localIP());
    udp.onPacket([](AsyncUDPPacket packet) {
      Serial.print("UDP Packet Type: ");
      Serial.print(packet.isBroadcast()
                       ? "Broadcast"
                       : packet.isMulticast() ? "Multicast" : "Unicast");
      Serial.print(", From: ");
      Serial.print(packet.remoteIP());
      Serial.print(":");
      Serial.print(packet.remotePort());
      Serial.print(", To: ");
      Serial.print(packet.localIP());
      Serial.print(":");
      Serial.print(packet.localPort());
      Serial.print(", Length: ");
      Serial.print(packet.length());
      Serial.print(", Data: ");
      Serial.write(packet.data(), packet.length());
      Serial.println();

      // reply to the client/sender
      packet.printf("Got %u bytes of data", packet.length());
    });
  }

  // Send unicast
  // udp.print("Hello Server!");
  // udp.
}

void sendMessage(String msg){
  udp.writeTo((const uint8_t *)msg.c_str(), msg.length(),
              IPAddress(192, 168, 43, 219), 7007);
}

void loop() {
  delay(200);

  Serial.println("---------");
  
  Serial.print("Y-axis: ");
  Serial.println(analogRead(y));
  Serial.print("X-axis: ");
  Serial.println(analogRead(x));
  Serial.print("sw-axis: ");
  Serial.println(analogRead(joysw));

  //FLY DOWN
  if(analogRead(y) > 3000 ){
  sendMessage("DOWN");
    udp.broadcastTo("DOWN", 4000);
  }
  
  //FLY UP
  if(analogRead(y) < 1000){
  sendMessage("UP");
  udp.broadcastTo("UP", 4000);

  }

  //FLY LEFT
  if(analogRead(x) < 1000 ){
  sendMessage("LEFT");
  udp.broadcastTo("LEFT", 4000);
  }

  //FLY RIGHT
  if(analogRead(x) > 3000 ){
  sendMessage("RIGHT");
  udp.broadcastTo("RIGHT", 4000);
  }

  //INSTANTIATE DRONE
  if(analogRead(joysw) > 3000 ){
  sendMessage("BUTTON");
  udp.broadcastTo("BUTTON", 4000);
  }


}