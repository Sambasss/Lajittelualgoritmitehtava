import java.util.*;
import java.util.function.Consumer;

public class SortTest {

    // ALGORITMIT

    public static void insertionSort(int[] data) {
        int n = data.length;
        for (int i = 1; i < n; ++i) {
            int key = data[i];
            int j = i - 1;
            while (j >= 0 && data[j] > key) {
                data[j + 1] = data[j];
                j = j - 1;
            }
            data[j + 1] = key;
        }
    }

    public static void bubbleSort(int[] data) {
        int n = data.length;
        int i, j, temp;
        boolean swapped;
        for (i = 0; i < n - 1; i++) {
            swapped = false;
            for (j = 0; j < n - i - 1; j++) {
                if (data[j] > data[j + 1]) {
                    temp = data[j];
                    data[j] = data[j + 1];
                    data[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped)
                break;
        }
    }

    public static void shellSort(int[] data) {
        int n = data.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int temp = data[i];
                int j;
                for (j = i; j >= gap && data[j - gap] > temp; j -= gap) {
                    data[j] = data[j - gap];
                }
                data[j] = temp;
            }
        }
    }

    public static void quickSort(int[] data) {
        quickSort(data, 0, data.length - 1);
    }

    private static void quickSort(int[] data, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(data, low, high);
            quickSort(data, low, pivotIndex);
            quickSort(data, pivotIndex + 1, high);
        }
    }

    private static int partition(int[] data, int low, int high) {
        int pivot = data[low + (high - low) / 2];
        int i = low - 1;
        int j = high + 1;

        while (true) {
            do {
                i++;
            } while (data[i] < pivot);

            do {
                j--;
            } while (data[j] > pivot);

            if (i >= j) {
                return j;
            }

            int temp = data[i];
            data[i] = data[j];
            data[j] = temp;
        }
    }

    public static void mergeSort(int[] data) {
        if (data.length < 2) {
            return;
        }
        int mid = data.length / 2;
        int[] left = Arrays.copyOfRange(data, 0, mid);
        int[] right = Arrays.copyOfRange(data, mid, data.length);

        mergeSort(left);
        mergeSort(right);

        merge(data, left, right);
    }

    private static void merge(int[] data, int[] left, int[] right) {
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                data[k++] = left[i++];
            } else {
                data[k++] = right[j++];
            }
        }
        while (i < left.length) {
            data[k++] = left[i++];
        }
        while (j < right.length) {
            data[k++] = right[j++];
        }
    }

    // DATA

    public static int[] createAscendingData(int n) {
        int[] data = new int[n];
        for (int i = 0; i < data.length; i++) {
            data[i] = i;
        }
        return data;
    }

    public static int[] createDescendingData(int n) {
        int[] data = new int[n];
        for (int i = 0; i < data.length; i++) {
            data[i] = data.length - 1 - i;
        }
        return data;
    }

    public static int[] createRandomData(int n) {
        int[] data = new int[n];
        Random gen = new Random();
        for (int i = 0; i < data.length; i++) {
            data[i] = gen.nextInt(n);
        }
        return data;
    }

    // funktioref
    public static void testSorter(String name, Consumer<int[]> sorter, int[] originalData) {
        // Kopioidaan data, jotta jokainen algoritmi saa saman alkuperäisen datan
        int[] dataCopy = Arrays.copyOf(originalData, originalData.length);
        
        long start = System.nanoTime();
        sorter.accept(dataCopy);
        long stop = System.nanoTime();
        
        double duration = (stop - start) / 1000000000.0;
        System.out.printf("%-30s: %.6f s\n", name, duration);
    }

    public static void main(String[] args) {
        int size = 100000;

        int[] randomData = createRandomData(size);
        int[] ascendingData = createAscendingData(size);
        int[] descendingData = createDescendingData(size);

        // SATUNNAISELLA DATALLA
        System.out.println("--- SATUNNAISELLA DATALLA ---");
        testSorter("Insertion sort", SortTest::insertionSort, randomData);
        testSorter("Bubble sort", SortTest::bubbleSort, randomData);
        testSorter("Arrays.sort", Arrays::sort, randomData);
        testSorter("Shellsort", SortTest::shellSort, randomData);
        testSorter("Quicksort", SortTest::quickSort, randomData);
        testSorter("Mergesort", SortTest::mergeSort, randomData);
        System.out.println();

        // NOUSEVALLA DATALLA
        System.out.println("--- TESTATAAN NOUSEVALLA DATALLA ---");
        testSorter("Insertion sort", SortTest::insertionSort, ascendingData);
        testSorter("Bubble sort", SortTest::bubbleSort, ascendingData);
        testSorter("Arrays.sort", Arrays::sort, ascendingData);
        testSorter("Shellsort", SortTest::shellSort, ascendingData);
        testSorter("Quicksort", SortTest::quickSort, ascendingData);
        testSorter("Mergesort", SortTest::mergeSort, ascendingData);
        System.out.println();

        // LASKEVALLA DATALLA
        System.out.println("--- TESTATAAN LASKEVALLA DATALLA ---");
        testSorter("Insertion sort", SortTest::insertionSort, descendingData);
        testSorter("Bubble sort", SortTest::bubbleSort, descendingData);
        testSorter("Arrays.sort", Arrays::sort, descendingData);
        testSorter("Shellsort", SortTest::shellSort, descendingData);
        testSorter("Quicksort", SortTest::quickSort, descendingData);
        testSorter("Mergesort", SortTest::mergeSort, descendingData);
    }
}