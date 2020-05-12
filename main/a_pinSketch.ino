//declaring power pins
int powerPinMin = 22;
int powerPinMax = 40;
//declaring gnd pins
int gndPinMin = 41;
int gndPinMax = 53;

void setPinModes()
{
  //cycling through power pins
  for(int i = powerPinMin; i<= powerPinMax; i++){
    //setting pinModes for powerPins
    pinMode(i, OUTPUT);
    //setting the 5V output for the power pins
    digitalWrite(i, HIGH);
  }

  for(int i = gndPinMin; i<= gndPinMax; i++){
    //setting pinModes for gndPins
    pinMode(i, OUTPUT);
    //setting gnd output for gndPins
    digitalWrite(i, LOW);
  }
}
