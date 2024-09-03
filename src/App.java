import java.util.*;
import java.io.FileWriter;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        boolean execute = true;
        
        while (execute) {
            boolean elegirMetodo = true;
            while (elegirMetodo) {      
                System.out.println("---------------Elige El Metodo Que Quieres Usar---------------");
                System.out.println("1. Congruencial Mixto.");
                System.out.println("2. Congruencial Multiplicativo.");
                System.out.println("3. Cuadros medios.");
                System.out.println("4. Lagged Fibonacci.");
                System.out.println("5. BBS.");
                System.out.print("Escribe El Numero Del Metodo: ");
                int metodo = scanner.nextInt();
                System.out.println("===============================================================");

                switch (metodo) {
                    case 1:
                        metodoCongruencialMixto();
                        elegirMetodo = false;
                        break;
                    case 2:
                        metodoCongruencialMultiplicativo();
                        elegirMetodo = false;
                        break;
                    case 3:
                        metodoCuadrosMedios();
                        elegirMetodo = false;
                        break;
                    case 4:
                        metodoLaggedFibonacci();
                        elegirMetodo = false;
                        break;
                    case 5:
                        metodoBBS();
                        elegirMetodo = false;
                        break;
                    default:
                        System.out.println("Ingresa Un Valor Correcto");
                        break;
                }
            }
            boolean continuar = true;
            while (continuar) {
                System.out.println("---------------¿Desea generar otra secuencia?---------------");
                System.out.println("1. SI.");
                System.out.println("2. NO.");
                System.out.print("Ingrese Su Respuesta: ");
                int respuestaFinal = scanner.nextInt();
                if (respuestaFinal == 1) {
                    continuar = false;
                }
                else if (respuestaFinal == 2) {
                    while (continuar) {
                        System.out.println("---------------¿Desea Hacer Las Pruebas?---------------");
                        System.out.println("1. SI.");
                        System.out.println("2. NO.");
                        System.out.print("Ingrese Su Respuesta: ");
                        respuestaFinal = scanner.nextInt();
                        switch (respuestaFinal) {
                            case 1:
                                RandomNumberTests randomNumberTests = new RandomNumberTests();
                                randomNumberTests.pruebas();
                                continuar = false;
                                execute = false;
                                break;

                            case 2:
                                continuar = false;
                                execute = false;
                                break;
                        
                            default:
                                break;
                        }
                    }
                    
                }
                else {
                    System.out.println("Elige una opcion valida");
                } 
                System.out.println("===============================================================");
            }
        }
        scanner.close();
    }

    public static void metodoCongruencialMixto() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--------------Metodo Congruencial Mixto--------------");
        System.out.print("Ingrese La Semilla (X0): ");
        int X0 = scanner.nextInt();
        int Xn = X0;
        System.out.print("Ingrese El Multiplicador (a): ");
        int a = scanner.nextInt();
        System.out.print("Ingrese La Constante Aditiva (c): ");
        int c = scanner.nextInt();
        System.out.print("Ingrese El Modulo (m): ");
        int m = scanner.nextInt();

        System.out.println("===============================================================");

        if (!(0 < Xn && Xn < m && 0 < a && a < m && 0 <= c && c < m && 0 < m)) {
            System.out.println("Error: Por favor ingresa valores adecuados para los parámetros.");
            scanner.close();
            return;
        }

        validarMixto(a, c, m);

        List<Integer> generados = new ArrayList<>();
        List<Double> normalizados = new ArrayList<>();
        System.out.println("\nNúmeros generados: ");

        while (true) {
            Xn = (a * Xn + c) % m;
            if (generados.contains(Xn)) {
                break;
            }
            generados.add(Xn);
            normalizados.add(Xn / (double) m);
            System.out.println(Xn / (double) m);
        }

        escribirEnCSV(normalizados, "generados.csv");
        System.out.println("Periodo: " + normalizados.size());
        System.out.println("Archivo 'generados.csv' creado satisfactoriamente con los números generados.");

        System.out.println("===============================================================");

    }
    
    public static void metodoCongruencialMultiplicativo() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--------------Metodo Congruencial Multiplicativo--------------");
        System.out.print("Ingrese La Semilla (X0): ");
        int X0 = scanner.nextInt();
        int Xn = X0;
        System.out.print("Ingrese El Multiplicador (a): ");
        int a = scanner.nextInt();
        System.out.print("Ingrese El Modulo (m): ");
        int m = scanner.nextInt();

        System.out.println("===============================================================");

        if (!(0 < Xn && Xn < m && 0 < a && a < m && 0 < m)) {
            System.out.println("Error: Por favor ingresa valores adecuados para los parámetros.");
            return;
        }

        if (!esPrimo(m)) {
            System.out.println("Warning: El valor de 'm' no es primo.");
        }

        List<Integer> generados = new ArrayList<>();
        List<Double> normalizados = new ArrayList<>();
        System.out.println("\nNúmeros generados: ");

        while (true) {
            Xn = (a * Xn) % m;
            if (generados.contains(Xn)) {
                break;
            }
            generados.add(Xn);
            normalizados.add(Xn / (double) m);
            System.out.println(Xn / (double) m);
        }

        escribirEnCSV(normalizados, "generados.csv");
        System.out.println("Periodo: " + normalizados.size());
        System.out.println("Archivo 'generados.csv' creado satisfactoriamente con los números generados.");

        System.out.println("===============================================================");
    }
    
    public static void metodoCuadrosMedios() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--------------Metodo Cuadros Medios--------------");
        System.out.println("Ingrese La Semilla (Valor Inicial De 4 Dígitos): ");
        int semilla = scanner.nextInt();

        if (String.valueOf(semilla).length() != 4) {
            System.out.println("Error: La semilla debe ser un número de 4 dígitos.");
            return;
        }

        List<Double> generados = new ArrayList<>();
        Set<Integer> semillasGeneradas = new HashSet<>();

        System.out.println("Números pseudoaleatorios generados:");
        while (true) {
            semilla = Integer.parseInt(String.format("%08d", semilla * semilla).substring(2, 6));
            double generado = semilla / 10000.0;

            if (semillasGeneradas.contains(semilla)) {
                break;
            }

            generados.add(generado);
            semillasGeneradas.add(semilla);
            System.out.println(generado);
        }

        escribirEnCSV(generados, "generados.csv");
        System.out.println("Periodo: " + generados.size());
        System.out.println("Archivo 'generados.csv' creado satisfactoriamente con los números generados.");

        System.out.println("===============================================================");
    }
    
    public static void metodoLaggedFibonacci() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--------------Metodo Lagged Fibonacci--------------");
        System.out.print("Ingrese El Valor Inicial (X0): ");
        int x0 = scanner.nextInt();
        System.out.print("Ingrese El Segundo Valor (X1): ");
        int x1 = scanner.nextInt();
        System.out.print("Ingrese El Lag: ");
        int lag = scanner.nextInt();
        System.out.print("Ingrese El Modulo (m): ");
        int mod = scanner.nextInt();
        System.out.print("Ingrese La Cantidad De Numeros Que Quiere Generar: ");
        int n = scanner.nextInt();

        System.out.println("===============================================================");

        List<Integer> generados = new ArrayList<>();
        List<Double> normalizados = new ArrayList<>();
        generados.add(x0);
        generados.add(x1);
        normalizados.add(x0 / (double) mod);
        normalizados.add(x1 / (double) mod);

        System.out.println("Números pseudoaleatorios generados:");
        for (int i = 0; i < n; i++) {
            int Xn = (generados.get(i - lag) + generados.get(i - lag + 1)) % mod;
            generados.add(Xn);
            normalizados.add(Xn / (double) mod);
            System.out.println(Xn / (double) mod);
        }

        escribirEnCSV(normalizados, "generados.csv");
        System.out.println("Archivo 'generados.csv' creado satisfactoriamente con los números generados.");

        System.out.println("===============================================================");
    }
    
    public static void metodoBBS() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--------------Metodo BBS--------------");
        System.out.print("Ingrese El Valor P: ");
        int p = scanner.nextInt();
        System.out.print("Ingrese El Valor Q: ");
        int q = scanner.nextInt();
        System.out.print("Ingrese La Semilla: ");
        int semilla = scanner.nextInt();
        System.out.print("Ingrese La Cantidad De Numeros Que Quiere Generar: ");
        int n = scanner.nextInt();

        System.out.println("===============================================================");

        int modulus = p * q;
        List<Double> generados = new ArrayList<>();

        System.out.println("Números pseudoaleatorios generados:");
        for (int i = 0; i < n; i++) {
            semilla = (semilla * semilla) % modulus;
            double generado = Math.round(semilla / (double) modulus * 10000) / 10000.0;
            generados.add(generado);
            System.out.println(generado);
        }

        escribirEnCSV(generados, "generados.csv");
        System.out.println("Archivo 'generados.csv' creado satisfactoriamente con los números generados.");

        System.out.println("===============================================================");
    }

    public static void validarMixto(int a, int c, int m) {
        if (a % 2 == 0 || a % 3 == 0 || a % 5 == 0) {
            System.out.println("Warning: El multiplicador 'a' debe ser un entero impar no divisible por 3 o 5.");
        }

        if (c % 2 == 0) {
            System.out.println("Warning: La constante aditiva 'c' debe ser un entero impar.");
        }

        if (gcd(c, m) != 1) {
            System.out.println("Warning: 'c' debe ser relativamente primo a 'm'.");
        }
    }

    public static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public static boolean esPrimo(int m) {
        if (m <= 1) return false;
        if (m <= 3) return true;
        if (m % 2 == 0 || m % 3 == 0) return false;
        for (int i = 5; i * i <= m; i += 6) {
            if (m % i == 0 || m % (i + 2) == 0) return false;
        }
        return true;
    }

    public static void escribirEnCSV(List<Double> numeros, String nombreArchivo) {
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            for (double numero : numeros) {
                writer.write(numero + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }
}