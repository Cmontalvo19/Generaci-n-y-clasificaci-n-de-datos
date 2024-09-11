import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GenerateInfoFiles {

    public static void main(String[] args) {
        try {
            createSalesMenFile(10, "Juan Perez", 123456789);
            createProductsFile(10);
            createSalesManInfoFile(10);
            System.out.println("Archivos generados exitosamente.");
        } catch (IOException e) {
            System.err.println("Error al generar los archivos: " + e.getMessage());
        }
    }

    public static void createSalesMenFile(int randomSalesCount, String name, long id) throws IOException {
        String fileName = "sales_" + id + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("CC;" + id + "\n");
            Random random = new Random();
            for (int i = 0; i < randomSalesCount; i++) {
                int productId = random.nextInt(100) + 1;
                int quantitySold = random.nextInt(50) + 1;
                writer.write(productId + ";" + quantitySold + ";\n");
            }
        }
    }

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
