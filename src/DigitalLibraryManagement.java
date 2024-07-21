import java.util.*;
import java.time.LocalDate;

class Book {
    private String id;
    private String title;
    private String author;
    private String category;
    private boolean isAvailable;

    public Book(String id, String title, String author, String category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.isAvailable = true;
    }

    // Getters and setters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }
}

class User {
    private String id;
    private String name;
    private String email;
    private List<Book> borrowedBooks;

    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.borrowedBooks = new ArrayList<>();
    }

    // Getters and setters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public List<Book> getBorrowedBooks() { return borrowedBooks; }
}

class Library {
    private List<Book> books;
    private List<User> users;

    public Library() {
        this.books = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(String bookId) {
        books.removeIf(book -> book.getId().equals(bookId));
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(String userId) {
        users.removeIf(user -> user.getId().equals(userId));
    }

    public List<Book> searchBooks(String query) {
        List<Book> results = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    book.getAuthor().toLowerCase().contains(query.toLowerCase()) ||
                    book.getCategory().toLowerCase().contains(query.toLowerCase())) {
                results.add(book);
            }
        }
        return results;
    }

    public boolean issueBook(String bookId, String userId) {
        Book book = findBook(bookId);
        User user = findUser(userId);
        if (book != null && user != null && book.isAvailable()) {
            book.setAvailable(false);
            user.getBorrowedBooks().add(book);
            return true;
        }
        return false;
    }

    public boolean returnBook(String bookId, String userId) {
        Book book = findBook(bookId);
        User user = findUser(userId);
        if (book != null && user != null && user.getBorrowedBooks().remove(book)) {
            book.setAvailable(true);
            return true;
        }
        return false;
    }

    private Book findBook(String bookId) {
        return books.stream().filter(b -> b.getId().equals(bookId)).findFirst().orElse(null);
    }

    private User findUser(String userId) {
        return users.stream().filter(u -> u.getId().equals(userId)).findFirst().orElse(null);
    }
}

class Admin {
    private Library library;

    public Admin(Library library) {
        this.library = library;
    }

    public void addBook(Book book) {
        library.addBook(book);
    }

    public void removeBook(String bookId) {
        library.removeBook(bookId);
    }

    public void addUser(User user) {
        library.addUser(user);
    }

    public void removeUser(String userId) {
        library.removeUser(userId);
    }

    // Other admin functionalities like report generation can be added here
}

public class DigitalLibraryManagement {
    public static void main(String[] args) {
        Library library = new Library();
        Admin admin = new Admin(library);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Admin Login");
            System.out.println("2. User Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    adminMenu(admin, scanner);
                    break;
                case 2:
                    userMenu(library, scanner);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    private static void adminMenu(Admin admin, Scanner scanner) {
        while (true) {
            System.out.println("\n1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. Add User");
            System.out.println("4. Remove User");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter book ID: ");
                    String bookId = scanner.nextLine();
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter book author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter book category: ");
                    String category = scanner.nextLine();
                    admin.addBook(new Book(bookId, title, author, category));
                    System.out.println("Book added successfully");
                    break;
                case 2:
                    System.out.print("Enter book ID to remove: ");
                    bookId = scanner.nextLine();
                    admin.removeBook(bookId);
                    System.out.println("Book removed successfully");
                    break;
                case 3:
                    System.out.print("Enter user ID: ");
                    String userId = scanner.nextLine();
                    System.out.print("Enter user name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter user email: ");
                    String email = scanner.nextLine();
                    admin.addUser(new User(userId, name, email));
                    System.out.println("User added successfully");
                    break;
                case 4:
                    System.out.print("Enter user ID to remove: ");
                    userId = scanner.nextLine();
                    admin.removeUser(userId);
                    System.out.println("User removed successfully");
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    private static void userMenu(Library library, Scanner scanner) {
        while (true) {
            System.out.println("\n1. Search Books");
            System.out.println("2. Issue Book");
            System.out.println("3. Return Book");
            System.out.println("4. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter search query: ");
                    String query = scanner.nextLine();
                    List<Book> results = library.searchBooks(query);
                    for (Book book : results) {
                        System.out.println(book.getTitle() + " by " + book.getAuthor() + " (" + book.getCategory() + ")");
                    }
                    break;
                case 2:
                    System.out.print("Enter book ID: ");
                    String bookId = scanner.nextLine();
                    System.out.print("Enter your user ID: ");
                    String userId = scanner.nextLine();
                    if (library.issueBook(bookId, userId)) {
                        System.out.println("Book issued successfully");
                    } else {
                        System.out.println("Failed to issue book");
                    }
                    break;
                case 3:
                    System.out.print("Enter book ID: ");
                    bookId = scanner.nextLine();
                    System.out.print("Enter your user ID: ");
                    userId = scanner.nextLine();
                    if (library.returnBook(bookId, userId)) {
                        System.out.println("Book returned successfully");
                    } else {
                        System.out.println("Failed to return book");
                    }
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid option");
            }
        }
    }
}