package util;

public class Time {
   public static float timeStarted = System.nanoTime();

   public static float get_time() {return (float)((System.nanoTime() - timeStarted)* 1E-9);}
}
