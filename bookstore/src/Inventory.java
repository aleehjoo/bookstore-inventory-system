import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Inventory {
    // Inventory uses a LinkedList of Book objects
    private static LinkedList<Book> inventory = new LinkedList<>();
    // Queue to store ordered book titles (FIFO)
    private static Queue<String> orderQueue = new LinkedList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Bookstore Inventory Management System!");

        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("Enter your choice: ");
            String input = scanner.nextLine().trim();

            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input — please enter a number between 1 and 7.");
                continue;
            }

            switch (choice) {
                case 1:
                    addBook(scanner);
                    break;
                case 2:
                    displayAllBooks();
                    break;
                case 3:
                    sortBooksByTitle();
                    break;
                case 4:
                    searchBookByTitle(scanner);
                    break;
                case 5:
                    addOrderToQueue(scanner);
                    break;
                case 6:
                    processNextOrder();
                    break;
                case 7:
                    running = false;
                    System.out.println("Thank you for using the Bookstore Inventory Management System!");
                    break;
                default:
                    System.out.println("Please choose a valid option (1-7).");
            }

            System.out.println(); // blank line for readability
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("Please choose an option:");
        System.out.println("1. Add a new book");
        System.out.println("2. Display all books");
        System.out.println("3. Sort books by title");
        System.out.println("4. Search for a book by title");
        System.out.println("5. Add a customer order to the queue");
        System.out.println("6. Process the next customer order");
        System.out.println("7. Exit");
    }

    // Step 3.1: Adding a new book
    private static void addBook(Scanner scanner) {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine().trim();

        System.out.print("Enter book author: ");
        String author = scanner.nextLine().trim();

        System.out.print("Enter book ISBN: ");
        String isbn = scanner.nextLine().trim();

        double price = 0.0;
        while (true) {
            System.out.print("Enter book price: ");
            String priceInput = scanner.nextLine().trim();
            try {
                price = Double.parseDouble(priceInput);
                if (price < 0) {
                    System.out.println("Price cannot be negative. Please enter a valid price.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid price format. Please enter a numeric value (e.g. 19.99).\n");
            }
        }

        Book newBook = new Book(title, author, isbn, price);
        inventory.add(newBook);
        System.out.println("Book added successfully!");
    }

    // Step 3.2: Display all books
    private static void displayAllBooks() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }

        System.out.println("--- All Books in Inventory ---");
        for (Book b : inventory) {
            System.out.println(b.toString());
        }
        System.out.println("-----------------------------");
    }

    // Step 4: Sorting using Bubble Sort by title (case-insensitive)
    private static void sortBooksByTitle() {
        if (inventory.size() <= 1) {
            System.out.println("Not enough books to sort.");
            return;
        }

        System.out.println("Sorting books by title...");
        int n = inventory.size();
        boolean swapped;
        // Classic bubble sort implementation operating directly on the LinkedList
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                String t1 = inventory.get(j).getTitle();
                String t2 = inventory.get(j + 1).getTitle();
                if (t1.compareToIgnoreCase(t2) > 0) {
                    Book temp = inventory.get(j);
                    inventory.set(j, inventory.get(j + 1));
                    inventory.set(j + 1, temp);
                    swapped = true;
                }
            }
            if (!swapped) break; // already sorted
        }

        System.out.println("Books sorted successfully!");
    }

    // Step 5: Linear search by title (case-insensitive exact match)
    private static void searchBookByTitle(Scanner scanner) {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty. No books to search.");
            return;
        }

        System.out.print("Enter the title of the book to search for: ");
        String query = scanner.nextLine().trim();

        for (Book b : inventory) {
            if (b.getTitle().equalsIgnoreCase(query)) {
                System.out.println("Book found:");
                System.out.println(b.toString());
                return;
            }
        }

        System.out.println("Book not found: " + query);
    }

    // Step 6.1: Add order to queue
    private static void addOrderToQueue(Scanner scanner) {
        System.out.print("Enter the title of the book to order: ");
        String title = scanner.nextLine().trim();

        orderQueue.add(title);
        System.out.println("Order for \"" + title + "\" has been added to the queue.");
    }

    // Step 6.2: Process next order
    private static void processNextOrder() {
        if (orderQueue.isEmpty()) {
            System.out.println("No orders to process. The queue is empty.");
            return;
        }

        System.out.println("Processing next order...");
        String title = orderQueue.remove();
        System.out.println("Processed order for: " + title);
    }
}
