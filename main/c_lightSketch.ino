int lightTogglePin = 4;
int lightIntensityPin = 5;
bool lightIsOn = false;
//the time needed by the dimmer to complete a full cycle
double fullCycleTime = 13000.00;
double currentIntensity = 0.00;

void setupLight()
{
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
    double lastDelay = (fullCycleTime / 2) * currentIntensity;

    double timeToReset = fullCycleTime - lastDelay;

    emulateButtonInterval(lightIntensityPin, timeToReset);

    currentIntensity = 0;
}

void setLightIntensity(double intensity)
{
    resetLightIntensity();

    double delayTime = (fullCycleTime / 2) * intensity;

    emulateButtonInterval(lightIntensityPin, delayTime);

    //updates the current intensity
    currentIntensity = intensity;
}
