int DHT_pin = 2; //pin for the DHT-sensor, so for humidity and temperature
int humidControlPin = 3;
int lowerPin = 4;
int currentHumidity;

#include "DHT.h" //init DHT sensor (moisture and temp)

#define DHTPIN DHT_pin //connection for sensor is pin 2

#define DHTTYPE DHT11 // DHT sensor type is DHT11

DHT dht(DHTPIN, DHTTYPE); //defines variable "dht" as a shortcut


void setupDHT()
{
  //sets pin mode for the pin where the dht is connected
  pinMode(DHT_pin, INPUT);
  //initializes the dht-sensor
  dht.begin();
}

void updateHumudity()
{
  //reads the current humidity with the DHT-sensor and stores it in a local value
  float humidity = dht.readHumidity();
  //updates the glbal value with the one read
  currentHumidity = humidity;
}

void regulateHumidity(float targetHumidity, float currentHumidity)
{
  //waits for the sensor to finish (it's slow)
  delay(2000);
  //updates the current humidity reading
  updateHumudity();

  //raises or lowers the humidity according to the target and the current humidity
  if (targetHumidity > currentHumidity)
  {
    raiseHumidity();
  }
  else
  {
    lowerHumidity();
  }
}

void raiseHumidity()
{
  //powers USB-Air moisturizer
  digitalWrite(humidControlPin, HIGH);
}

void lowerHumidity()
{
  //cuts power to USB-Air moisturizer
  digitalWrite(humidControlPin, LOW);
}