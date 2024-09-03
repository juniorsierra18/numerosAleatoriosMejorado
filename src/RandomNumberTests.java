import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class RandomNumberTests {

    // Read CSV file to get generated numbers
    public static List<Double> leerCsv(String archivo) {
        List<Double> numeros = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] valores = linea.split(",");
                numeros.add(Double.parseDouble(valores[0]));  // Assuming the number is in the first column
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numeros;
    }

    // Calculate Chi-square critical value (approximation)
    public static double chiSquareCriticalValue(int degreesOfFreedom, double alpha) {
        // Using an approximation formula for critical value
        // Note: This is a rough approximation. For more accurate results, use a statistical table or library.
        return degreesOfFreedom * (1 + alpha * Math.sqrt(degreesOfFreedom) / 2);
    }

    // Calculate Kolmogorov-Smirnov critical value (approximation)
    public static double kolmogorovSmirnovCriticalValue(int n, double alpha) {
        // Using an approximation formula for critical value
        // Note: This is a rough approximation. For more accurate results, use a statistical table or library.
        return Math.sqrt(-0.5 * Math.log(alpha / 2)) / Math.sqrt(n);
    }

    // Test for Means
    public static void promedios(List<Double> numeros) {
        int N = numeros.size();
        double aritmetica = 0;
        double media = 0.5;
        double varianza = Math.sqrt(1.0 / 12);
        double alfa = 1.96;

        for (double n : numeros) {
            aritmetica += n;
        }
        aritmetica /= N;

        double Z_0 = ((aritmetica - media) * Math.sqrt(N)) / varianza;
        System.out.println("Estadistico: " + Z_0);

        if (Math.abs(Z_0) < alfa) {
            System.out.println("Los numeros pseudoaleatorios provienen de una distribución uniforme");
        } else {
            System.out.println("Los numeros pseudoaleatorios NO provienen de una distribución uniforme");
        }
    }

    // Frequency Test
    public static void frecuencias(List<Double> numeros, int n, double alfa) {
        int N = numeros.size();
        List<Double> subIntervalos = new ArrayList<>();
        double FE = (double) N / n;
        int[] FO = new int[n];
        double X2_0 = 0;
        double X2_an = chiSquareCriticalValue(n - 1, alfa);

        for (int i = 0; i < n; i++) {
            subIntervalos.add((double) i / n);
            FO[i] = 0;
        }

        for (double numero : numeros) {
            for (int j = 0; j < n - 1; j++) {
                if (numero >= subIntervalos.get(j) && numero < subIntervalos.get(j + 1)) {
                    FO[j]++;
                } else if (j == n - 2 && numero >= subIntervalos.get(j + 1) && numero <= 1) {
                    FO[j + 1]++;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            X2_0 += Math.pow(FO[i] - FE, 2) / FE;
        }

        System.out.println("Valor del estadistico: " + X2_0);
        System.out.println("Valor de chi cuadrado: " + X2_an);

        if (X2_0 < X2_an) {
            System.out.println("Los numeros pseudoaleatorios provienen de una distribución uniforme");
        } else {
            System.out.println("Los numeros pseudoaleatorios NO provienen de una distribución uniforme");
        }
    }

    // Series Test
    public static void series(List<Double> numeros, int n, double alfa) {
        int N = numeros.size();
        List<Double> subIntervalos = new ArrayList<>();
        double FE = (double) (N - 1) / (n * n);
        int[][] FO = new int[n][n];
        double X2_0 = 0;
        double X2_a = chiSquareCriticalValue(n * n - 1, alfa);

        for (int i = 0; i < n; i++) {
            subIntervalos.add((double) i / n);
        }

        for (int i = 0; i < N - 1; i++) {
            double Ui = numeros.get(i);
            double Uj = numeros.get(i + 1);
            int x = 0, y = 0;
            for (int j = 0; j < n - 1; j++) {
                if (Ui >= subIntervalos.get(j) && Ui < subIntervalos.get(j + 1)) {
                    x = j;
                } else if (j == n - 2 && Ui >= subIntervalos.get(j + 1) && Ui <= 1) {
                    x = j + 1;
                }
                if (Uj >= subIntervalos.get(j) && Uj < subIntervalos.get(j + 1)) {
                    y = j;
                } else if (j == n - 2 && Uj >= subIntervalos.get(j + 1) && Uj <= 1) {
                    y = j + 1;
                }
            }
            FO[x][y]++;
        }

        for (int[] fila : FO) {
            for (int frecuencia : fila) {
                X2_0 += Math.pow(frecuencia - FE, 2);
            }
        }
        X2_0 = ((n * n) / (double) (N - 1)) * X2_0;

        System.out.println("Valor del estadistico: " + X2_0);
        System.out.println("Valor de chi cuadrado: " + X2_a);

        if (X2_0 < X2_a) {
            System.out.println("Los numeros pseudoaleatorios provienen de una distribución uniforme");
        } else {
            System.out.println("Los numeros pseudoaleatorios NO provienen de una distribución uniforme");
        }
    }

    // Kolmogorov-Smirnov Test
    public static void kolmogorovSmirnov(List<Double> numeros, double alfa) {
        int n = numeros.size();
        Collections.sort(numeros);
        List<Double> F_n = new ArrayList<>();
        double D = 0;

        double d_an = kolmogorovSmirnovCriticalValue(n, alfa);

        for (int i = 1; i <= n; i++) {
            F_n.add((double) i / n);
        }

        for (int i = 0; i < n; i++) {
            double x = Math.abs(F_n.get(i) - numeros.get(i));
            if (x > D) {
                D = x;
            }
        }

        System.out.println("Valor del estadístico: " + D);
        System.out.println("Valor crítico: " + d_an);

        if (D < d_an) {
            System.out.println("Los numeros pseudoaleatorios provienen de una distribución uniforme");
        } else {
            System.out.println("Los numeros pseudoaleatorios NO provienen de una distribución uniforme");
        }
    }

    public static void pruebas() {
        Scanner scanner = new Scanner(System.in);
        List<Double> numerosGenerados = leerCsv("generados.csv");
        System.out.println("Lista de números: " + numerosGenerados);

        System.out.println("Realizar pruebas");
        System.out.println("1. Promedios");
        System.out.println("2. Frecuencias");
        System.out.println("3. Series");
        System.out.println("4. Kolmogorov-Smirnov");
        System.out.println("Ingrese El Numero De La Prueba");
        int opc = scanner.nextInt();

        System.out.println("===============================================================");

        switch (opc) {
            case 1:
                promedios(numerosGenerados);
                break;
            case 2:
                System.out.print("Numero de subintervalos: ");
                int nFrecuencia = scanner.nextInt();
                System.out.print("Valor de alfa: ");
                double alfaFrecuencia = scanner.nextDouble();
                frecuencias(numerosGenerados, nFrecuencia, alfaFrecuencia);
                break;
            case 3:
                System.out.print("Valor de n: ");
                int nSeries = scanner.nextInt();
                System.out.print("Valor de alfa: ");
                double alfaSeries = scanner.nextDouble();
                series(numerosGenerados, nSeries, alfaSeries);
                break;
            case 4:
                System.out.print("Valor de Alfa: ");
                double alfaKS = scanner.nextDouble();
                kolmogorovSmirnov(numerosGenerados, alfaKS);
                break;
            default:
                System.out.println("Opción inválida");
        }
        boolean continuar = true;
        while (continuar) {
            System.out.println("---------------¿Desea Hacer Otra Prueba?---------------");
            System.out.println("1. SI.");
            System.out.println("2. NO.");
            System.out.print("Ingrese Su Respuesta: ");
            int respuestaFinal = scanner.nextInt();
            switch (respuestaFinal) {
                case 1:
                    pruebas();
                    break;

                case 2:
                    continuar = false;
                    break;
            
                default:
                    break;
            }
            System.out.println("==============================================================");
        }

    }
}
