import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GenerateInfoFiles {

    public static void main(String[] args) {
        try {
            // Generar archivos de prueba
            createProductsFile(100); // Aseguramos que haya suficientes productos
            createSalesManInfoFile(10);
            createSalesMenFiles(10, 10); // Generamos archivos de ventas para 10 vendedores con 10 ventas cada uno
            System.out.println("Archivos generados exitosamente.");
        } catch (IOException e) {
            System.err.println("Error al generar los archivos: " + e.getMessage());
        }
    }

    // Método para generar un archivo de ventas de un vendedor
    public static void createSalesMenFiles(int salesmanCount, int randomSalesCount) throws IOException {
        Random random = new Random();
        for (int i = 0; i < salesmanCount; i++) {
            long id = 1000000000L + i;
            String fileName = "sales_" + id + ".txt";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                writer.write("CC;" + id + "\n");
                for (int j = 0; j < randomSalesCount; j++) {
                    int productId = random.nextInt(100) + 1; // IDs de productos entre 1 y 100
                    int quantitySold = random.nextInt(50) + 1;
                    writer.write(productId + ";" + quantitySold + ";\n");
                }
            }
        }
    }

    // Método para generar un archivo con información de productos
    public static void createProductsFile(int productsCount) throws IOException {
        String fileName = "products.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            Random random = new Random();
            for (int i = 0; i < productsCount; i++) {
                int productId = i + 1;
                String productName = "Producto" + productId;
                double productPrice = random.nextDouble() * 100;
                writer.write(productId + ";" + productName + ";" + productPrice + "\n");
            }
        }
    }

    // Método para generar un archivo con información de vendedores
    public static void createSalesManInfoFile(int salesmanCount) throws IOException {
        String fileName = "salesmen_info.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < salesmanCount; i++) {
                String type = "CC";
                long id = 1000000000L + i;
                String firstName = "Nombre" + i;
                String lastName = "Apellido" + i;
                writer.write(type + ";" + id + ";" + firstName + ";" + lastName + "\n");
            }
        }
    }
}
