void setup() {
  setPinModes();
  Serial.begin(115200);
}

void loop() {
  byte groundVal = temp_r;
  Serial.print(groundVal);
}
