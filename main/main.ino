//sets first ideal values
float idealHumidity = 50.00;

void setup()
{
  //initializes serial communication
  Serial.begin(115200);
  //sets power and gnd-pins
  setPinModes();
  //starts setup for DHT-Sensor
  setupDHT();
  //starts setup for light
  setupLight();
}

void loop()
{
  //regulateHumidity();
}

void emulateButton(int pin)
{
  //presses the button
  digitalWrite(pin, HIGH);
  //waits for 200 millis
  delay(200);

  //releases button
  digitalWrite(pin, LOW);
}

void emulateButtonInterval(int pin, double time)
{
  //presses a button
  emulateButton(pin);

  //waits for secified time
  delay(time);

  //presses button again
  emulateButton(pin);
}
