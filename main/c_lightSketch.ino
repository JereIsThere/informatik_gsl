int lightTogglePin = 4;
int lightIntensityPin = 5;
bool lightIsOn = false;
//the time needed by the dimmer to complete a full cycle
double fullCycleTime = 13000.00;
//the value for the current light intensity
double currentIntensity = 0.00;

void setupLight()
{
    //sets pin modes for both light outputs
    pinMode(lightTogglePin, OUTPUT);
    pinMode(lightIntensityPin, OUTPUT);
}

void toggleLight()
{
    //"presses a button"
    emulateButton(lightTogglePin);

    //invert boolean for state of light
    lightIsOn != lightIsOn;
}

void resetLightIntensity(){
    //calculates the delay time that was used last time
    double lastDelay = (fullCycleTime / 2) * currentIntensity;

    //calculates the time needed for the light to complete a cycle
    double timeToReset = fullCycleTime - lastDelay;

    //presses the button for the time calculated beforehand, therefor setting the intensity to zero
    emulateButtonInterval(lightIntensityPin, timeToReset);

    currentIntensity = 0;
}

void setLightIntensity(double intensity)
{
    //sets the light intensity to zero
    resetLightIntensity();

    //calculates delay time to reach specified intensity
    double delayTime = (fullCycleTime / 2) * intensity;

    //"presses button" with calculated delay
    emulateButtonInterval(lightIntensityPin, delayTime);

    //updates the current intensity value
    currentIntensity = intensity;
}
