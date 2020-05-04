/*
  Sensoren (readings): *_r
  Kontrollelemente (control): *_c

  Lufttemperatur = temp
  Luftfeuchtigkeit = air_humid
  Bodenfeuchtigkeit = gnd_humid
  Licht = light

  licht dimmbar

*/

byte temp_r = A0;
byte temp_c = 0;

byte air_humid_r = A1;
byte air_humid_c = A2;

byte gnd_humid_r = A3;
byte gnd_humid_c = 2;

byte light_c = A4;

byte fans[] = {3, 4};

void setPinModes() {
  pinMode(temp_r, INPUT);
  pinMode(temp_c, OUTPUT);

  pinMode(air_humid_r, INPUT);
  pinMode(air_humid_c, OUTPUT);

  pinMode(gnd_humid_r, INPUT);
  pinMode(gnd_humid_c, INPUT);
  
  pinMode(light_c, OUTPUT);

  for(int i =0;i<sizeof(fans);i++){
    pinMode(fans[i], OUTPUT);
  }
}
