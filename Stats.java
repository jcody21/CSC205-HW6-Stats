import java.util.*;

/**
 * This Java application calculates some statistics
 * given an array of integers.
 *
 * @creator John Cody
 * @created 02019.02.04
 **/

interface StatsConstants {
   // used by print() to format output...
   String PRINT_BEGIN = "[";
   String PRINT_END = "]";
   String PRINT_SEPARATOR = ",";

   // checkIfSorted() return values...
   int UNSORTED = 0;
   int ASCENDING = 1;
   int DESCENDING = 2;
   int SKIP = 3;
}

public class Stats {

   public static void main(String[] argv) {

      int[][] data = { 
         null,
         { },
         { 0 },
         { 1, 2 },
         { 1, 1 },
         { 1, 3, 2 },
         { 5, 5, 5, 5, 5 },
         { 2, 0, 5, 4, 8, 5 },
         { 1, 5, 6, 6, 6, 7, 9 },
         { -7, 0, 0, 3, 3, 3, 4, 4 },
         { -2, -2, 0, 1, 1, 2, 2, 2 },
         { -4, -2, 42, 12, 12, 3, -2 },
      };
      
      for (int i = 0; i < data.length; i++) {
         switch (checkIfSorted(data[i])) {
            case StatsConstants.SKIP:
               if (data[i] == null)
                  System.out.println("null element\n");
               else if (data[i].length == 0) 
                  System.out.println("empty array\n");
               else
                  System.out.println("???\n");
               continue;
            case StatsConstants.UNSORTED:
               Arrays.sort(data[i]);
               break;
            case StatsConstants.DESCENDING:
               reverseArray(data[i]);
               break;
         }
         printResults(stats(data[i]));
      }
   }

   private static void reverseArray(int[] x) {
      for (int i = 0, j = x.length - 1 ; i < j; i++, j--) {
         int k = x[i];
         x[i] = x[j];
         x[j] = k;
      }
   }

   private static void printArray(int[] x, boolean nl) {
      System.out.print(StatsConstants.PRINT_BEGIN);
      for (int i = 0, j = x.length - 1; i < j; i++)
         System.out.print(x[i] + StatsConstants.PRINT_SEPARATOR);
      System.out.print(x[x.length - 1] + StatsConstants.PRINT_END);
      if (nl) System.out.println();
   }

   private static void printResults(Results r) {
      printArray(r.data, true);
      StringBuffer sb = new StringBuffer("...mean: ");
      sb.append(r.mean).append("; median: ").append(r.median).
         append("; mode: "). append(r.nomode ? "modeless" : r.mode).
         append("; cardinality: ").append(r.cardinality).
         append("; range: ").append(r.range);
      System.out.println(sb);
      System.out.println();
   }

   static class Results {
      public int[] data; 
      public int cardinality;
      public int range;
      public double mean;
      public double median;
      public int mode;
      public boolean nomode;
   }

   private static Results stats(int[] data) {
      Results x = new Results(); 
      x.nomode = true;
      
      int tempRecord = 0, record = 1, mode = 0, sum = 0, min = data[0], max = data[0], len = data.length;
      double median;
      
      
      if(len % 2 == 1) 
          if((len/2) + 1 < len)
              median = data[len/2];
          else
              median = 0;
      else 
          median = (data[(len-1)/2] + data[(len-1)/2 + 1])/2.0;
      
      for(int i = 0; i < len; i++) {
          tempRecord = 0;
          if(data[i] > max) 
              max = data[i];
          if(data[i] < min) 
              min = data[i];
          sum += data[i];
          
          int k = i + 1;
          while(k < len && data[i] == data[k]) {
              tempRecord = k - i + 2;
              k++;
          }
          if(len == 1) {
              tempRecord = 2;
          }
          if(tempRecord > record) {
              record = tempRecord;
              mode = data[i];
              x.nomode = false;
          }
          else if(tempRecord == record)
              x.nomode = true;
      }
      
      x.data = data;
      x.cardinality = len;
      x.range = max - min;
      x.mean = (sum*1.0)/len;
      x.median = median;
      x.mode = mode;
      return x;
   }

   private static int checkIfSorted(int[] data) {
       boolean ascend = true;
       boolean descend = true;
       
       if(data == null || data.length == 0) 
           return StatsConstants.SKIP;
       for(int i = 0; i < data.length; i++) {
           if(i == data.length - 1) {
              if(ascend)
                   return StatsConstants.ASCENDING;
              if(descend)
                   return StatsConstants.DESCENDING; 
              else 
                  break;
           } 
           if(data[i] > data[i + 1]) 
               ascend = false;
           if(data[i] < data[i + 1]) 
               descend = false;
       }

       return StatsConstants.UNSORTED;
   }

}