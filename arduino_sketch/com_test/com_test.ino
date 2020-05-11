
void setup() {
  // put your setup code here, to run once:
  pinMode(A1, OUTPUT);
  Serial.begin(9600);
}

int temp=1;
int humid=10;


void loop() {
  // put your main code here, to run repeatedly:
  Serial.print(temp);
  digitalWrite(A1, HIGH);
  temp++;
  Serial.print("\t");
  Serial.print(humid);
  humid++;
  Serial.println("\t");
  delay(1000);
  digitalWrite(A1, LOW);
  delay(10000);  
}
