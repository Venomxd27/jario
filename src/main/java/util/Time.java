package util;

public class Time {
   public static float timestarted = System.nanoTime();

   public static float get_time() {return (float)((System.nanoTime() - timestarted)* 1E-9);}
}
