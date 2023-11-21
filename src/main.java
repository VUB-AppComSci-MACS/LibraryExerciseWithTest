import java.time.LocalDate;

public class main {
    public static void main(String[] args) {
        int bookId = 0;

        final int NUMBER_OF_USERS = 10;
        final int NUMBER_OF_BOOKS = 20;

        System.out.println("-----Library Test-----");
        System.out.println("I implemented unit test for this assignment as well, " +
                "since I didn't read the requirement clearly...");
        System.out.println("\n");
        Library library = new Library();
        System.out.printf("-----Test create %d new Users -----\n", NUMBER_OF_USERS);
        for (int i = 0; i < NUMBER_OF_USERS; i++) {
            String name = "UserNo " + i;
            library.addUser(name);
            System.out.printf("User: %s added!\n", name);
        }
        System.out.println("Current library's user: " + library.getUsers().size());
        System.out.println("-----*-----");

        System.out.printf("-----Test create %d new Books -----\n", NUMBER_OF_BOOKS);
        for (int i = 0; i < NUMBER_OF_BOOKS; i++) {
            String name = "BookNo " + i;
            String description = "This is the description for book" + i;
            library.addBook(new Book(bookId++, name, getRandomBookType(), description));
            System.out.printf("Book: %s added!", name);
        }
        System.out.println("Current library's book: " + library.getBooks().size());
        System.out.println("-----*-----");

        System.out.println("-----Test check out book -----");
        library.checkOutBook(
                library.getUsers().get(0).getUniqueId(),
                library.getBooks().get(0).getUniqueId()
        );
        library.checkOutBook(
                library.getUsers().get(0).getUniqueId(),
                library.getBooks().get(1).getUniqueId()
        );
        library.getBooks().get(1).setDeadline(LocalDate.now().minusDays(10));
        System.out.println("-----*-----");

        System.out.println("-----Test return book -----");
        library.returnBook(
                library.getUsers().get(0).getUniqueId(),
                library.getBooks().get(0).getUniqueId()
        );
        library.returnBook(
                library.getUsers().get(0).getUniqueId(),
                library.getBooks().get(1).getUniqueId()
        );
        System.out.println("-----*-----");

        System.out.println("-----Test contact user -----");
        library.checkOutBook(
                library.getUsers().get(0).getUniqueId(),
                library.getBooks().get(0).getUniqueId()
        );
        library.checkOutBook(
                library.getUsers().get(0).getUniqueId(),
                library.getBooks().get(1).getUniqueId()
        );
        library.checkOutBook(
                library.getUsers().get(0).getUniqueId(),
                library.getBooks().get(2).getUniqueId()
        );
        library.checkOutBook(
                library.getUsers().get(0).getUniqueId(),
                library.getBooks().get(3).getUniqueId()
        );
        library.getBooks().get(2).setDeadline(LocalDate.now().minusDays(10));

        library.contactUser(library.getUsers().get(0).getUniqueId());
        System.out.println("-----*-----");

    }

    /**
     * Get random book type.
     * @return 1 random book type.
     */
    private static BookType getRandomBookType() {
        BookType[] bookTypes = BookType.class.getEnumConstants();
        int index = (int) (Math.random() * bookTypes.length);
        return bookTypes[index];
    }
}
