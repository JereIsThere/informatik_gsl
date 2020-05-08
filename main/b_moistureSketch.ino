int DHT_pin = 2; //pin for the DHT-sensor, so for humidity and temperature
int higherPin = 3;
int lowerPin = 4;
int currentHumidity;

#include "DHT.h" //init DHT sensor (moisture and temp)

#define DHTPIN DHT_pin //connection for sensor is pin 2

#define DHTTYPE DHT11 // DHT sensor type is DHT11

DHT dht(DHTPIN, DHTTYPE); //defines variable "dht" as a shortcut

void setupDHT()
{
  pinMode(DHT_pin, INPUT);
  dht.begin();
}

void updateHumudity()
{

  float humidity = dht.readHumidity();

  currentHumidity = humidity;
}

void regulateHumidity(float targetHumidity, float currentHumidity)
{

  delay(2000);

  updateHumudity();

  Serial.print("humidity (");
  Serial.print(currentHumidity);
  Serial.print(") needs to be ");

  if (targetHumidity > currentHumidity)
  {
    raiseHumidity();
  }
  else
  {
    lowerHumidity();
  }

  Serial.print(" (");
  Serial.print(targetHumidity);
  Serial.println(")");
}

void raiseHumidity()
{
  Serial.print("higher");
  digitalWrite(higherPin, HIGH);
  digitalWrite(lowerPin, LOW);
}

void lowerHumidity()
{
  Serial.print("lower");
  digitalWrite(lowerPin, HIGH);
  digitalWrite(higherPin, LOW);
}