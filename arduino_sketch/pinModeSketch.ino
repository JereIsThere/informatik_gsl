/*
   temp = temperature
   humid = humidity
   gndHumid = humidity of the ground
   water = water flow

   _c = control
   _r = reading
   _pp = power plus
   _gnd = ground
*/

byte temp_c = A0;
byte temp_r = A1;

byte humid_c = A2;
byte humid_pp[1] = {53};
byte humid_r = A3;

byte water_c = A4;

byte light_c = A6;

void setPinModes(){
  pinMode(temp_c, OUTPUT);
  pinMode(temp_r, INPUT);

  pinMode(humid_c, OUTPUT);
  pinMode(humid_r, INPUT);

  pinMode(water_c, OUTPUT);

  pinMode(light_c, OUTPUT);
}
