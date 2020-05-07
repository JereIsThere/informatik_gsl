/*
  Sensoren (readings): *_r
  Kontrollelemente (control): *_c

  Lufttemperatur = temp
  Luftfeuchtigkeit = air_humid
  Bodenfeuchtigkeit = gnd_humid
  Licht = light
*/

int DHT_pin = 2; //pin for the DHT-sensor, so for humidity and temperature

byte temp_c = 0;

byte air_humid_c = A2;

byte gnd_humid_r = A3;
byte gnd_humid_c = 2;

byte light_c = A4;

byte fans[] = {3, 4};

void setup() {
  //Serial.begin(115200);
}

void loop() {
  //byte groundVal = temp_r;
  //Serial.print(groundVal);
}
