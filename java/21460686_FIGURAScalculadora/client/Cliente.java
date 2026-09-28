package client;

import shared.ListaFigurasInterface;
import shared.FiguraInterface;
import java.rmi.Naming;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        try {
            // Conectarse al servidor RMI
            ListaFigurasInterface figuras = (ListaFigurasInterface) Naming.lookup("rmi://localhost/figuras");

            Scanner scanner = new Scanner(System.in);
            int opcion;
            System.out.println("-----CALCULADORA DE FIGURAS-----\n");
            menu:do {
                System.out.println("\n----Menú:----");
                System.out.println("[1] Agregar Rombo");
                System.out.println("[2] Agregar Rectángulo");
                System.out.println("[3] Agregar Triángulo");
                System.out.println("[4] Agregar Círculo");
                System.out.println("[5] Calcular Totales");
                System.out.println("[0] Salir");
                System.out.print("Elige una opción: ");
                opcion = scanner.nextInt();

                System.out.println("\n");
                switch (opcion) {
                    case 0:
                        System.out.println("GRACIAS POR USAR EL PROGRAMA");
                        break menu;
                    case 1: 
                        System.out.print("Ingresa la diagonal mayor: ");
                        double diagonalMayor = scanner.nextDouble();
                        System.out.print("Ingresa la diagonal menor: ");
                        double diagonalMenor = scanner.nextDouble();
                        FiguraInterface rombo = new Rombo(diagonalMayor, diagonalMenor);
                        figuras.agregar(rombo);
                        
                        // Mostrar resultados de la figura agregada
                        System.out.println("Figura agregada: Rombo");
                        System.out.println("Área: " + rombo.calcularArea());
                        System.out.println("Perímetro: " + rombo.calcularPerimetro());
                        break;
                    
                    case 2: 
                        System.out.print("Ingresa la base: ");
                        double base = scanner.nextDouble();
                        System.out.print("Ingresa la altura: ");
                        double altura = scanner.nextDouble();
                        FiguraInterface rectangulo = new Rectangulo(base, altura);
                        figuras.agregar(rectangulo);
                        
                        // Mostrar resultados de la figura agregada
                        System.out.println("Figura agregada: Rectángulo");
                        System.out.println("Área: " + rectangulo.calcularArea());
                        System.out.println("Perímetro: " + rectangulo.calcularPerimetro());
                        break;
                    
                    case 3: 
                        System.out.print("Ingresa el lado A: ");
                        double ladoA = scanner.nextDouble();
                        System.out.print("Ingresa el lado B: ");
                        double ladoB = scanner.nextDouble();
                        System.out.print("Ingresa el lado C: ");
                        double ladoC = scanner.nextDouble();
                        FiguraInterface triangulo = new Triangulo(ladoA, ladoB, ladoC);
                        figuras.agregar(triangulo);
                        
                        // Mostrar resultados de la figura agregada
                        System.out.println("Figura agregada: Triángulo");
                        System.out.println("Área: " + triangulo.calcularArea());
                        System.out.println("Perímetro: " + triangulo.calcularPerimetro());
                        break;
                    
                    case 4: 
                        System.out.print("Ingresa el radio: ");
                        double radio = scanner.nextDouble();
                        FiguraInterface circulo = new Circulo(radio);
                        figuras.agregar(circulo);
                        
                        // Mostrar resultados de la figura agregada
                        System.out.println("Figura agregada: Círculo");
                        System.out.println("Área: " + circulo.calcularArea());
                        System.out.println("Perímetro: " + circulo.calcularPerimetro());
                        break;
                    
                    case 5: 
                        // Obtener y mostrar los totales de área y perímetro
                        double areaTotal = figuras.getAreaTotal();
                        double perimetroTotal = figuras.getPerimetroTotal();
                        System.out.println("Área total: " + areaTotal);
                        System.out.println("Perímetro total: " + perimetroTotal);
                        break;
                    
                    
                    default: 
                        System.out.println("Opción no válida.");
                        break;
                }
            } while (opcion != 6);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
