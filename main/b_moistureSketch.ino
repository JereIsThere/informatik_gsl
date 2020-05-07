#include "DHT.h" //init DHT sensor (moisture and temp)

#define DHTPIN DHT_pin //connection for sensor is pin 2 

#define DHTTYPE DHT11    // DHT sensor type is DHT11

DHT dht(DHTPIN, DHTTYPE); //defines variable "dht" as a shortcut
