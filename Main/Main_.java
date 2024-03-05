package Main;

import scr.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class Main_ {
   public static QUASO train = null;
   public static QUASO target = null;
   public static void main(String args[]) throws Exception {
      Scanner input = new Scanner(System.in);
      rTarget();
      rTrain();

      Relu relu = new Relu();
      long startTime = System.nanoTime();
      Neural number = new Neural(train.transpose(), target.transpose(), new int[] {3, 10, 7, 1});
      number.setEpoch(10000);
      number.setLearningRate(0.0000001);
      number.train(relu);

      long endTime = System.nanoTime();
      long timeElapsed = endTime - startTime;
      
      System.out.println("เวลาที่ใช้ในการรัน: " + timeElapsed + " นาโนวินาที");

      while (true) {
         Double [][] test = {{input.nextDouble()},
                             {input.nextDouble()},
                             {input.nextDouble()}};
         QUASO testQuaso = new QUASO(0, 0, false);
         number.predict(testQuaso.fromArray(test), relu);
      }
   
   }

   private static void rTrain()throws Exception{
      Scanner sc = new Scanner(new BufferedReader(new FileReader("Main/plus_60/data_train.txt")));
      QUASO Quaso = new QUASO(0, 0, false);
      int rows = 400;
      int columns = 3;

      Double[][] data_train = new Double[rows][columns];
      while (sc.hasNextLine()) {
         for (int i = 0; i < data_train.length; i++) {
            String[] line = sc.nextLine().trim().split("\\s+");
            for (int j = 0; j < line.length; j++) {
               data_train[i][j] = Double.parseDouble(line[j]); 
            }
         }
      }
      sc.close();
      train = Quaso.fromArray(data_train);
   }

   private static void rTarget() throws Exception{
      Scanner sc = new Scanner(new BufferedReader(new FileReader("Main/plus_60/data_target.txt")));
      QUASO Quaso = new QUASO(0, 0, false);
      int rows = 400;
      int columns = 1;

      Double[][] data_target = new Double[rows][columns];
      while (sc.hasNextLine()) {
         for (int i = 0; i < data_target.length; i++) {
            String[] line = sc.nextLine().trim().split("\\s+");
            for (int j = 0; j < line.length; j++) {
               data_target[i][j] = Double.parseDouble(line[j]); 
            }
         }
      }
      sc.close();
      target = Quaso.fromArray(data_target);
   }
}

class Relu extends activation{
   public double forward(double x){
       return Math.max(0, x);
   }

   public double backward(double x){
       return x > 0 ? 1 : 0;
   }
}