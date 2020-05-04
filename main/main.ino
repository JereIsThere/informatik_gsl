byte temp_r = A0;
byte temp_c = 0;

byte air_humid_r = A1;
byte air_humid_c = A2;

byte gnd_humid_r = A3;
byte gnd_humid_c = 2;

byte light_c = A4;

byte fans[] = {3, 4};

void setup() {
  setPinModes();
  Serial.begin(115200);
}

void loop() {
  byte groundVal = temp_r;
  Serial.print(groundVal);
}
