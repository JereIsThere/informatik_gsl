int heatOnPin = 6;
int heatOffPin = 7;
//value for temperature readings
float currentTemp = 0;

void setupTemp()
{
    //sets pin modes
    pinMode(heatOnPin, OUTPUT);
    pinMode(heatOffPin, OUTPUT);
}

void updateTemp()
{
    //reads current temperature from DHT-sensor (temporary temperature (reading))
    float tempTemp = dht.readTemperature();
    //updates global value for temp
    currentTemp = tempTemp;
}

void raiseTemp()
{
    //activates heat measures
    emulateButton(heatOnPin);
}

void lowerTemp()
{
    //deactivate heat measures
    emulateButton(heatOffPin);
}

void regulateTemp()
{
    //updates global value
    updateTemp();
    //checks if temperature is within range of ideal temperature and tolerance
    if (currentTemp < (idealTemperature - temperatureTolerance))
    {
        raiseTemp();
    }
    else if (currentTemp > (idealTemperature + temperatureTolerance))
    {
        lowerTemp();
    }
}