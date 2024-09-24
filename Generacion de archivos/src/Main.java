import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        try {
            // Leer archivos de entrada
            Map<String, Vendedor> vendedores = leerArchivoVendedores("salesmen_info.txt");
            Map<Integer, Producto> productos = leerArchivoProductos("products.txt");
            Map<String, Double> ventasPorVendedor = new HashMap<>();
            Map<Integer, Integer> cantidadPorProducto = new HashMap<>();

            // Procesar archivos de ventas
            File folder = new File(".");
            for (File file : folder.listFiles()) {
                if (file.getName().startsWith("sales_")) {
                    procesarArchivoVentas(file, ventasPorVendedor, cantidadPorProducto, productos);
                }
            }

            // Generar archivos de salida
            generarArchivoVentasPorVendedor(vendedores, ventasPorVendedor);
            generarArchivoProductosVendidos(productos, cantidadPorProducto);

            System.out.println("Archivos de salida generados exitosamente.");
        } catch (IOException e) {
            System.err.println("Error al generar los archivos de salida: " + e.getMessage());
        }
    }

    // Leer archivo de información de vendedores
    public static Map<String, Vendedor> leerArchivoVendedores(String fileName) throws IOException {
        Map<String, Vendedor> vendedores = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                String id = parts[1];
                String nombre = parts[2] + " " + parts[3];
                vendedores.put(id, new Vendedor(id, nombre));
            }
        }
        return vendedores;
    }

    // Leer archivo de información de productos
    public static Map<Integer, Producto> leerArchivoProductos(String fileName) throws IOException {
        Map<Integer, Producto> productos = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                int id = Integer.parseInt(parts[0]);
                String nombre = parts[1];
                double precio = Double.parseDouble(parts[2]);
                productos.put(id, new Producto(id, nombre, precio));
            }
        }
        return productos;
    }

    // Procesar archivo de ventas de un vendedor
    public static void procesarArchivoVentas(File file, Map<String, Double> ventasPorVendedor, Map<Integer, Integer> cantidadPorProducto, Map<Integer, Producto> productos) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String vendedorId = reader.readLine().split(";")[1];
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                int productoId = Integer.parseInt(parts[0]);
                int cantidadVendida = Integer.parseInt(parts[1]);
                Producto producto = productos.get(productoId);

                // Verificar que el producto no sea null
                if (producto != null) {
                    double totalVenta = producto.getPrecio() * cantidadVendida;
                    ventasPorVendedor.put(vendedorId, ventasPorVendedor.getOrDefault(vendedorId, 0.0) + totalVenta);
                    cantidadPorProducto.put(productoId, cantidadPorProducto.getOrDefault(productoId, 0) + cantidadVendida);
                } else {
                    System.err.println("Producto con ID " + productoId + " no encontrado en el archivo de productos.");
                }
            }
        }
    }

    // Generar archivo de ventas por vendedor
    public static void generarArchivoVentasPorVendedor(Map<String, Vendedor> vendedores, Map<String, Double> ventasPorVendedor) throws IOException {
        List<Map.Entry<String, Double>> listaVentas = new ArrayList<>(ventasPorVendedor.entrySet());
        listaVentas.sort((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("ventas_por_vendedor.csv"))) {
            for (Map.Entry<String, Double> entry : listaVentas) {
                String vendedorId = entry.getKey();
                double totalVenta = entry.getValue();
                Vendedor vendedor = vendedores.get(vendedorId);

                // Verificar que el vendedor no sea null
                if (vendedor != null) {
                    writer.write(vendedor.getNombre() + ";" + totalVenta + "\n");
                } else {
                    System.err.println("Vendedor con ID " + vendedorId + " no encontrado en el archivo de vendedores.");
                }
            }
        }
    }

    // Generar archivo de productos vendidos
    public static void generarArchivoProductosVendidos(Map<Integer, Producto> productos, Map<Integer, Integer> cantidadPorProducto) throws IOException {
        List<Map.Entry<Integer, Integer>> listaProductos = new ArrayList<>(cantidadPorProducto.entrySet());
        listaProductos.sort((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("productos_vendidos.csv"))) {
            for (Map.Entry<Integer, Integer> entry : listaProductos) {
                int productoId = entry.getKey();
                int cantidadVendida = entry.getValue();
                Producto producto = productos.get(productoId);
                writer.write(producto.getNombre() + ";" + producto.getPrecio() + ";" + cantidadVendida + "\n");
            }
        }
    }

    // Clase para representar un vendedor
    static class Vendedor {
        private String id;
        private String nombre;

        public Vendedor(String id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }

        public String getId() {
            return id;
        }

        public String getNombre() {
            return nombre;
        }
    }

    // Clase para representar un producto
    static class Producto {
        private int id;
        private String nombre;
        private double precio;

        public Producto(int id, String nombre, double precio) {
            this.id = id;
            this.nombre = nombre;
            this.precio = precio;
        }

        public int getId() {
            return id;
        }

        public String getNombre() {
            return nombre;
        }

        public double getPrecio() {
            return precio;
        }
    }
}
