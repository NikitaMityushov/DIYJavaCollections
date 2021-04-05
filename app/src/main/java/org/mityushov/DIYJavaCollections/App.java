package org.mityushov.DIYJavaCollections;

public class App {
    public static void main(String... args) {
    
        try (var arr = new FastIntArray()) {
// 1 

            for (int i = 100; i > 0; i--) {
                boolean flag = true;
                flag = arr.add(i);
                if (!flag) {
                    break;
                }
            }
            /*
            for (long i = 0; i < 10; i++) {
                System.out.println(arr.get(i));
            }
            */
/*
            long n11 = System.currentTimeMillis();

            for (int i = -1; i < 12; i++) {
                arr.contains(i);
                // System.out.println(i + " is " + arr.contains(i));
            }

            long n12 = System.currentTimeMillis();
            System.out.println("Linear search time is " + (n12 - n11));
//2
            System.out.println("Quick sort started...");
            arr.sort();
            System.out.println("Quick sort stopped...");

            long n21 = System.currentTimeMillis();
            
            for (int i = -1; i < 12; i++) {
                arr.contains(i);
                // System.out.println(i + " is " + arr.contains(i));
            }

            long n22 = System.currentTimeMillis();
            System.out.println("Binary search time is " + (n22 - n21));

            for (long i = arr.size(); i > arr.size() - 10; i--) {
                System.out.println(arr.get(i));
            }
*/

            System.out.println(arr);


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Все пропало, допрыгался с Unsafe");
        }
    
    }
}
