/*
  Sensoren (readings): *_r
  Kontrollelemente (control): *_c
  
  Lufttemperatur
  
  licht dimmbar
  
*/


void setup() {
  // put your setup code here, to run once:
  setPinModes();
}

void loop() {
  // put your main code here, to run repeatedly:

}

void setPinModes() {
  pinMode(temp_r, INPUT);
  pinMode(temp_c, OUTPUT);

  pinMode(humid_r, INPUT);
  pinMode(humid_c, OUTPUT);

  //TODO bodenfeuchtigkeit

  pinMode(water_r, INPUT);
  pinMode(water_c, OUTPUT);

  pinMode(light_c, OUTPUT);
}
