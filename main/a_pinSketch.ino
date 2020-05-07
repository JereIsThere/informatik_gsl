void setPinModes() {
  //prepare pins
  pinMode(DHT_pin, INPUT);
  
  pinMode(temp_c, OUTPUT);

  pinMode(air_humid_c, OUTPUT);

  pinMode(gnd_humid_r, INPUT);
  pinMode(gnd_humid_c, INPUT);
  
  pinMode(light_c, OUTPUT);

  for(int i =0;i<sizeof(fans);i++){
    pinMode(fans[i], OUTPUT);
  }
}
