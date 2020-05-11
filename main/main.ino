/*
  Sensoren (readings): *_r
  Kontrollelemente (control): *_c

  Lufttemperatur = temp
  Luftfeuchtigkeit = air_humid
  Bodenfeuchtigkeit = gnd_humid
  Licht = light
*/

byte temp_c = 0;

byte air_humid_c = A2;

byte gnd_humid_r = A3;
byte gnd_humid_c = 2;

byte light_c = A4;

byte fans[] = {3, 4};

float idealHumidity = 50.00;

void setup()
{
  Serial.begin(115200);
  setupDHT();
  setupLight();
}

void loop()
{
  //regulateHumidity();
  delay(3000);

  pinMode(13, INPUT);
  pinMode(12, INPUT);

  bool thirteen = digitalRead(13);
  bool twelve = digitalRead(12);

  Serial.print(thirteen);
  Serial.print(" = 13, 12 = ");
  Serial.println(twelve);

  if (digitalRead(13))
  {
    Serial.println("toggleLight");
    toggleLight();
  }
  else if (digitalRead(12))
  {
    Serial.println("debugging light");
    setLightIntensity(0.5);
  }
}

void emulateButton(int pin)
{
  digitalWrite(pin, HIGH);

  delay(200);

  digitalWrite(pin, LOW);
}

void emulateButtonInterval(int pin, double time)
{
  emulateButton(pin);

  delay(time);

  emulateButton(pin);
}