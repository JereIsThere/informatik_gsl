byte inputPin = 7;
byte outputPin = A3;

void setup()
{
  // put your setup code here, to run once
  pinMode(inputPin, INPUT);
  pinMode(outputPin, OUTPUT);

  Serial.begin(115200);
}

void loop()
{
  // put your main code here, to run repeatedly:

  if (digitalRead(inputPin))
  {
    Serial.println("Signal!");
  }
}
