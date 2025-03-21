import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class BubbleMergeGenerics {

    public static int[] createRandomArray(int arrayLength){
        Random random = new Random();
        int[] array = new int[arrayLength];
        for (int i = 0; i<arrayLength; i++){
            array[i] = random.nextInt(101);
        }
        return array;
    }

    public static void writeArrayToFile(int[] array, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int num : array) {
                writer.write(String.valueOf(num));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("An error occurred when writing to the file: " + e.getMessage());
        }
    }
    public static int[] readFileToArray(String filename) {
        try(BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String lines; 
            int[] array = new int[100];
            int index = 0;
            while ((lines = reader.readLine()) != null){
                array[index++] = Integer.parseInt(lines);
            }
            int[] result = new int[index];
            System.arraycopy(array, 0, result, 0, index);
            return result;

        } catch (IOException e) {
            System.err.println("An error occurred when reading the file: " + e.getMessage());
            return new int[0];
        }
    }

    public static <T extends Comparable<T>> void bubbleSort(T[] array) {
        boolean swap = true;
        while(swap){

            swap = false;
            for(int i = 0; i <array.length-1; i++){
                if(array[i].compareTo(array[i+1]) > 0) {
                    swap = true;
                    T temp = array[i];
                    array[i] = array[i+1];
                    array[i+1] = temp;
                }
            }
        }
    }
    public static <T extends Comparable<T>> void mergeSort(List<T> list) {
        if (list.size() < 2) {
            return;
        }
        int middle = list.size() / 2;
        List<T> oneHalf = new ArrayList<>(list.subList(0, middle));
        List<T> twoHalf = new ArrayList<>(list.subList(middle, list.size()));

        mergeSort(oneHalf);
        mergeSort(twoHalf);

        merge(list, oneHalf, twoHalf);
    }
    public static <T extends Comparable<T>> void merge(List<T> list, List<T> oneHalf, List<T> twoHalf){
        int o = 0;
        int t = 0;
        int m = 0;
        while (o < oneHalf.size() && t < twoHalf.size()){
            if (oneHalf.get(o).compareTo(twoHalf.get(t)) <= 0) {
                list.set(m++, oneHalf.get(o++));
            } else {
                list.set(m++, twoHalf.get(t++));
            }
        }
        while (o < oneHalf.size()) {
            list.set(m++, oneHalf.get(o++));
        }
        while (t < twoHalf.size()) {
            list.set(m++, twoHalf.get(t++));
        }
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a number between 1 and 100 to be randomly generated: ");
        int runs = scanner.nextInt();
        int[] randomArray = createRandomArray(runs);

        String filename = "randomNums.txt";
        writeArrayToFile(randomArray, filename);

        int[] arrayFromFile = readFileToArray(filename);
        
        Integer[] arrayForBubble = new Integer[arrayFromFile.length];
        for ( int i = 0; i < arrayFromFile.length; i++) {
            arrayForBubble[i] = arrayFromFile[i];
        }
        bubbleSort(arrayForBubble);

        String filename2 = "bubbleSortedArrayGenerics.txt";
        int[] bubbleSortedArray = new int[arrayForBubble.length];
        for (int i = 0; i < arrayForBubble.length; i++) {
            bubbleSortedArray[i] = arrayForBubble[i];
        }
        writeArrayToFile(bubbleSortedArray, filename2);

        List<Integer> listFromFile = new ArrayList<>();
        for (int num : arrayFromFile) {
            listFromFile.add(num);
        }
        mergeSort(listFromFile);

        String sortedFileName = "mergeSortedArrayGenerics.txt";
        int[] sortedArray = new int[listFromFile.size()];
        for ( int i = 0; i < listFromFile.size(); i++) {
            sortedArray[i] = listFromFile.get(i);
        }
        writeArrayToFile(sortedArray, sortedFileName);
        scanner.close();


        System.out.println("Bubble sorted results: ");
        for (int num : bubbleSortedArray) {
            System.out.println(num);
        }
        System.out.println(" Merge sorted results: " );
        for (int num : sortedArray) {
            System.out.println(num);
        }
    }


}

