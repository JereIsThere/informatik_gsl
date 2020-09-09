int highestReading = -2;
byte inputPin = A1;

int cycles = 0;

void setup()
{
    Serial.begin(250000);
}

void loop()
{
    int reading = analogRead(inputPin);

    if (reading >= highestReading)
    {
        highestReading = reading;
    }

    Serial.print(highestReading);
    Serial.print("      is highest, current:        ");
    Serial.print(reading);

    if(digitalRead(inputPin)){
        Serial.println(", digitalRead returns true");
    }else{
        Serial.println("");
    }
}