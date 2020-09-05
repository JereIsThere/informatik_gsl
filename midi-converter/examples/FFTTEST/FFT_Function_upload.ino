//---------------------------------------------------------------------------//
byte sine_data[91] =
    {
        0,
        4, 9, 13, 18, 22, 27, 31, 35, 40, 44,
        49, 53, 57, 62, 66, 70, 75, 79, 83, 87,
        91, 96, 100, 104, 108, 112, 116, 120, 124, 127,
        131, 135, 139, 143, 146, 150, 153, 157, 160, 164,
        167, 171, 174, 177, 180, 183, 186, 189, 192, 195, //Paste this at top of program
        198, 201, 204, 206, 209, 211, 214, 216, 219, 221,
        223, 225, 227, 229, 231, 233, 235, 236, 238, 240,
        241, 243, 244, 245, 246, 247, 248, 249, 250, 251,
        252, 253, 253, 254, 254, 254, 255, 255, 255, 255};
float f_peaks[5]; // top 5 frequencies peaks in descending order

byte inputPin = A7;

const int sampleSize = 64;
int data[sampleSize], collectingData[sampleSize];

int measureCounter = 0;
long lastMeasureTime = 0;

float fftDuration = 60.0 * 1000.0;
//---------------------------------------------------------------------------//

void setup()
{
  Serial.begin(250000);
}

void measure()
{
  float measureDelay = fftDuration / sampleSize;
  measureDelay *= 0.90;
  // for (int i = 0; i < sampleSize; i++)
  // {
  //   data[i] = analogRead(inputPin) - 512;
  // }
  if (micros() - lastMeasureTime >= measureDelay && measureCounter < sampleSize) {
    collectingData[measureCounter] = analogRead(inputPin) - 512;
    measureCounter++;
    lastMeasureTime = micros();
  }
}

void forceMeasure() {
  collectingData[measureCounter] = analogRead(inputPin) - 512;
  measureCounter++;
}

void copy(int* src, int* dst, int len) {
    memcpy(dst, src, sizeof(src[0])*len);
}

void loop()
{
  long time = micros();

  // measure();

  // long deltaT = millis() - time;

  // float freq = sampleSize / (deltaT / 1000.0);

  // long fftStart = millis();
  copy(collectingData, data, sampleSize);

  measureCounter = 0;
  float temp = sampleSize / fftDuration * 1000.0 * 1000.0;
  FFT(data, sampleSize, temp);

  fftDuration = micros() - time;

  Serial.print(f_peaks[0]);
  Serial.print(" = Hz = ");
  Serial.println(temp);
  /*
//example
FFT(data,64,100);        //to get top five value of frequencies of X having 64 sample at 100Hz sampling
Serial.println(f_peaks[0]);
Serial.println(f_peaks[1]);
delay(99999);
*/

  /* 
after ruing above FFT(), frequencies available at f_peaks[0],f_peaks[1],f_peaks[2],f_peaks[3],f_peaks[4],
*/
}

//-----------------------------FFT Function----------------------------------------------//

float FFT(int in[], int N, float Frequency)
{
  /*
Code to perform FFT on arduino,
setup:
paste sine_data [91] at top of program [global variable], paste FFT function at end of program
Term:
1. in[]     : Data array, 
2. N        : Number of sample (recommended sample size 2,4,8,16,32,64,128...)
3. Frequency: sampling frequency required as input (Hz)

If sample size is not in power of 2 it will be clipped to lower side of number. 
i.e, for 150 number of samples, code will consider first 128 sample, remaining sample  will be omitted.
For Arduino nano, FFT of more than 128 sample not possible due to mamory limitation (64 recomended)
For higher Number of sample may arise Mamory related issue,
Code by ABHILASH
Contact: abhilashpatel121@gmail.com 
Documentation:https://www.instructables.com/member/abhilash_patel/instructables/
*/

  // o will be the index of a power of two (from the data array)
  unsigned int t_data[13] = {1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048};
  int a, c1, f, o, x;
  a = N;

  for (int i = 0; i < 12; i++) //calculating the levels
  {
    if (t_data[i] <= a)
    {
      o = i;
    }
    measure();
  }

  // Actual start of the algorithm

  int in_ps[t_data[o]] = {};    //input for sequencing
  float out_r[t_data[o]] = {};  //real part of transform
  float out_im[t_data[o]] = {}; //imaginory part of transform

  x = 0;
  for (int b = 0; b < o; b++) // bit reversal
  {
    c1 = t_data[b];
    f = t_data[o] / (c1 + c1);
    for (int j = 0; j < c1; j++)
    {
      x = x + 1;
      in_ps[x] = in_ps[j] + f;
      measure();
    }
  }

  for (int i = 0; i < t_data[o]; i++) // update input array as per bit reverse order
  {
    if (in_ps[i] < a)
    {
      out_r[i] = in[in_ps[i]];
    }
    if (in_ps[i] > a)
    {
      out_r[i] = in[in_ps[i] - a];
    }
    measure();
  }

  int i10, i11, n1;
  float e, c, s, tr, ti;

  for (int i = 0; i < o; i++) //fft
  {
    i10 = t_data[i];               // overall values of sine/cosine  :
    i11 = t_data[o] / t_data[i + 1]; // loop with similar sine cosine:
    e = 360 / t_data[i + 1];
    e = 0 - e;
    n1 = 0;

    for (int j = 0; j < i10; j++)
    {
      c = cosine(e * j);
      s = sine(e * j);
      n1 = j;

      for (int k = 0; k < i11; k++)
      {
        measure();
        tr = c * out_r[i10 + n1] - s * out_im[i10 + n1];
        ti = s * out_r[i10 + n1] + c * out_im[i10 + n1];

        measure();

        out_r[n1 + i10] = out_r[n1] - tr;
        out_r[n1] = out_r[n1] + tr;

        measure();

        out_im[n1 + i10] = out_im[n1] - ti;
        out_im[n1] = out_im[n1] + ti;

        n1 = n1 + i10 + i10;
        measure();
      }
    }
  }

  /*
for(int i=0;i<t_data[o];i++)
{
Serial.print(out_r[i]);
Serial.print("\t");                                     // un comment to print RAW o/p    
Serial.print(out_im[i]); Serial.println("i");      
}
*/

  //---> here onward out_r contains amplitude and our_in conntains frequency (Hz)
  for (int i = 0; i < t_data[o - 1]; i++) // getting amplitude from compex number
  {
    measure();
    out_r[i] = sqrt(out_r[i] * out_r[i] + out_im[i] * out_im[i]); // to  increase the speed delete sqrt
    measure();
    out_im[i] = i * Frequency / N;
    /*
         Serial.print(out_im[i]); Serial.print("Hz");
         Serial.print("\t");                            // un comment to print freuency bin    
         Serial.println(out_r[i]); 
         */
  }

  x = 0; // peak detection
  for (int i = 1; i < t_data[o - 1] - 1; i++)
  {
    if (out_r[i] > out_r[i - 1] && out_r[i] > out_r[i + 1])
    {
      in_ps[x] = i; //in_ps array used for storage of peak number
      x = x + 1;
    }
    measure();
  }

  s = 0;
  c = 0;
  for (int i = 0; i < x; i++) // re arraange as per magnitude
  {
    for (int j = c; j < x; j++)
    {
      if (out_r[in_ps[i]] < out_r[in_ps[j]])
      {
        s = in_ps[i];
        in_ps[i] = in_ps[j];
        in_ps[j] = s;
      }
      measure();
    }
    c = c + 1;
  }

  for (int i = 0; i < 5; i++) // updating f_peak array (global variable)with descending order
  {
    f_peaks[i] = out_im[in_ps[i]];
  }

  while (measureCounter < sampleSize) {
    forceMeasure();
  }

}

float sine(int i)
{
  int j = i;
  float out;
  while (j < 0)
  {
    j = j + 360;
  }
  while (j > 360)
  {
    j = j - 360;
  }
  if (j > -1 && j < 91)
  {
    out = sine_data[j];
  }
  else if (j > 90 && j < 181)
  {
    out = sine_data[180 - j];
  }
  else if (j > 180 && j < 271)
  {
    out = -sine_data[j - 180];
  }
  else if (j > 270 && j < 361)
  {
    out = -sine_data[360 - j];
  }
  return (out / 255);
}

float cosine(int i)
{
  int j = i;
  float out;
  while (j < 0)
  {
    j = j + 360;
  }
  while (j > 360)
  {
    j = j - 360;
  }
  if (j > -1 && j < 91)
  {
    out = sine_data[90 - j];
  }
  else if (j > 90 && j < 181)
  {
    out = -sine_data[j - 90];
  }
  else if (j > 180 && j < 271)
  {
    out = -sine_data[270 - j];
  }
  else if (j > 270 && j < 361)
  {
    out = sine_data[j - 270];
  }
  return (out / 255);
}

//------------------------------------------------------------------------------------//
