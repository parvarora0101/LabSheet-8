import java.util.*;

class Product {
    int id;
    String name;
    String category;
    float price;
    float rating;

    public Product(int id, String name, String category, float price, float rating) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Category: " + category 
                + ", Price: " + price + ", Rating: " + rating;
    }
}

public class ProductManagementSystem {

    // Display Products
    static void displayProducts(List<Product> products) {
        for (Product product : products) {
            System.out.println(product);
        }
    }

    // Insertion Sort
    static void insertionSort(List<Product> products, char criteria) {
        for (int i = 1; i < products.size(); i++) {
            Product key = products.get(i);
            int j = i - 1;

            while (j >= 0 && ((criteria == 'p' && products.get(j).price > key.price) ||
                              (criteria == 'r' && products.get(j).rating > key.rating) ||
                              (criteria == 'n' && products.get(j).name.compareTo(key.name) > 0))) {
                products.set(j + 1, products.get(j));
                j--;
            }
            products.set(j + 1, key);
        }
    }

    // Partition function for Quick Sort
    static int partition(List<Product> products, int low, int high, char criteria) {
        Product pivot = products.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if ((criteria == 'p' && products.get(j).price <= pivot.price) ||
                (criteria == 'r' && products.get(j).rating <= pivot.rating) ||
                (criteria == 'n' && products.get(j).name.compareTo(pivot.name) <= 0)) {
                i++;
                Collections.swap(products, i, j);
            }
        }
        Collections.swap(products, i + 1, high);
        return i + 1;
    }

    // Quick Sort
    static void quickSort(List<Product> products, int low, int high, char criteria) {
        if (low < high) {
            int pi = partition(products, low, high, criteria);
            quickSort(products, low, pi - 1, criteria);
            quickSort(products, pi + 1, high, criteria);
        }
    }

    // Binary Search by ID
    static int binarySearchById(List<Product> products, int id, int low, int high) {
        if (high >= low) {
            int mid = low + (high - low) / 2;

            if (products.get(mid).id == id)
                return mid;
            if (products.get(mid).id > id)
                return binarySearchById(products, id, low, mid - 1);

            return binarySearchById(products, id, mid + 1, high);
        }
        return -1;
    }

    // Sequential Search by Name
    static int sequentialSearchByName(List<Product> products, String name) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).name.equals(name)) {
                return i;
            }
        }
        return -1;
    }

    // Main Menu
    public static void menu() {
        Scanner scanner = new Scanner(System.in);
        List<Product> products = new ArrayList<>();
        int choice;

        do {
            System.out.println("\n1. Add Product\n2. Update Product\n3. Delete Product\n4. Display Products");
            System.out.println("5. Sort Products\n6. Search Product\n0. Exit");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1: {
                    System.out.print("Enter Product ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Clear buffer
                    System.out.print("Enter Product Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Category: ");
                    String category = scanner.nextLine();
                    System.out.print("Enter Price: ");
                    float price = scanner.nextFloat();
                    System.out.print("Enter Rating: ");
                    float rating = scanner.nextFloat();

                    products.add(new Product(id, name, category, price, rating));
                    break;
                }
                case 2: {
                    System.out.print("Enter Product ID to update: ");
                    int id = scanner.nextInt();
                    int index = binarySearchById(products, id, 0, products.size() - 1);
                    if (index != -1) {
                        scanner.nextLine(); // Clear buffer
                        System.out.print("Enter New Name: ");
                        products.get(index).name = scanner.nextLine();
                        System.out.print("Enter New Category: ");
                        products.get(index).category = scanner.nextLine();
                        System.out.print("Enter New Price: ");
                        products.get(index).price = scanner.nextFloat();
                        System.out.print("Enter New Rating: ");
                        products.get(index).rating = scanner.nextFloat();
                    } else {
                        System.out.println("Product not found.");
                    }
                    break;
                }
                case 3: {
                    System.out.print("Enter Product ID to delete: ");
                    int id = scanner.nextInt();
                    int index = binarySearchById(products, id, 0, products.size() - 1);
                    if (index != -1) {
                        products.remove(index);
                        System.out.println("Product deleted.");
                    } else {
                        System.out.println("Product not found.");
                    }
                    break;
                }
                case 4: {
                    displayProducts(products);
                    break;
                }
                case 5: {
                    System.out.print("Choose sort criteria (p: Price, r: Rating, n: Name): ");
                    char criteria = scanner.next().charAt(0);
                    System.out.print("Choose sort type (1: Insertion Sort, 2: Quick Sort): ");
                    int sortType = scanner.nextInt();

                    if (sortType == 1) {
                        insertionSort(products, criteria);
                    } else if (sortType == 2) {
                        quickSort(products, 0, products.size() - 1, criteria);
                    }
                    System.out.println("Products sorted.");
                    break;
                }
                case 6: {
                    System.out.print("Search by 1: ID, 2: Name: ");
                    int searchChoice = scanner.nextInt();

                    if (searchChoice == 1) {
                        System.out.print("Enter Product ID: ");
                        int id = scanner.nextInt();
                        int index = binarySearchById(products, id, 0, products.size() - 1);
                        if (index != -1) {
                            System.out.println("Product Found: " + products.get(index));
                        } else {
                            System.out.println("Product not found.");
                        }
                    } else if (searchChoice == 2) {
                        scanner.nextLine(); // Clear buffer
                        System.out.print("Enter Product Name: ");
                        String name = scanner.nextLine();
                        int index = sequentialSearchByName(products, name);
                        if (index != -1) {
                            System.out.println("Product Found: " + products.get(index));
                        } else {
                            System.out.println("Product not found.");
                        }
                    }
                    break;
                }
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 0);

        scanner.close();
    }

    public static void main(String[] args) {
        menu();
    }
}
