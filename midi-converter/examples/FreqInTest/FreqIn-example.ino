
// input pin can be any digital input
byte inputPin = A1;

boolean inputState = false;
boolean lastInputState = false;
double count = 0.00;
const long countMillis = 100L;
unsigned long previousCountMillis = millis();

//Bass is roughly 500/100
double highTreshold = 30;
double lowTreshold = 3;

long previousCounts = 0;
int cycles = 0;
int measurementCycles = 0;
double median = 0;

void setInputState() {
  int reading = analogRead(inputPin);
  if(reading >= highTreshold){
    inputState = true;
  }if(reading < lowTreshold){
    inputState = false;
  }

  // boolean reading = digitalRead(inputPin);
  // if (reading) {
  //   inputState = true;
  // } else {
  //   inputState = false;
  // }

  // Serial.print("Reading: ");
  // Serial.println(reading);

  measurementCycles++;
}

void setup() {
  pinMode(inputPin, INPUT);

  Serial.begin(250000);
}

void loop() {
  // runs every time thru the loop
  setInputState();

  // count every transision HIGH<->LOW
  if (inputState != lastInputState) {
    count++;
    lastInputState = inputState;
  }

  // ------- every half second, count is equal to Hz.---------------
  if (millis() - previousCountMillis >= countMillis) {
    previousCountMillis += countMillis;

    double multiplier = 500.0 / countMillis;
    count = count * multiplier;

    if (cycles % 5 == 0) {
      median = previousCounts / 4;
      previousCounts = 0;

      Serial.print("Median: ---->");
      Serial.println(median);
    } else {
      previousCounts += count;
    }

    // show Hz on Serial too if available
    Serial.print(count);
    Serial.println(" Hz");
    //Serial.println(measurementCycles);

    // reset to zero for the next half second's sample
    count = 0.00;
    cycles++;
    measurementCycles = 0;
  }
}
