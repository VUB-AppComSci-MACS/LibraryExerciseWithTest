import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class LibraryTest {
    private int Id = 0;
    private int userId = 0;
    private int bookId = 0;

    private Library library;
    private final int NUMBER_OF_USERS = 100;
    private final int NUMBER_OF_BOOKS = 200;
    private List<User> users;
    private List<Book> books;
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(output));
    }

    @Before
    public void generateUsers() {
        this.users = new ArrayList<>();
        for (int i = 0; i < this.NUMBER_OF_USERS; i++) {
            String name = "UserNo " + i;
            users.add(new User(userId++, name, Id));
        }
    }

    @Before
    public void generateBooks() {
        this.books = new ArrayList<>();
        for (int i = 0; i < this.NUMBER_OF_BOOKS; i++) {
            String name = "BookNo " + i;
            String description = "This is the description for book" + i;
            books.add(new Book(bookId++, name, getRandomBookType(), description));
        }
    }

    private BookType getRandomBookType() {
        BookType[] bookTypes = BookType.class.getEnumConstants();
        int index = (int) (Math.random() * bookTypes.length);
        return bookTypes[index];
    }

    @Test
    public void testAdditionUser_CaseNameEmpty_PrintoutWarningMessage() {
        // Arrange
        library = new Library();

        // Act
        int listUserBefore = library.getUsers().size();
        library.addUser("");
        int listUserAfter = library.getUsers().size();

        // Assert
        Assert.assertTrue(library.getUsers().isEmpty());
        Assert.assertEquals("User's name cannot be blank!", listUserBefore, listUserAfter);
    }

    @Test
    public void testAdditionUser_ValidName_PrintoutSuccessfulMessage() {
        // Arrange
        library = new Library();

        // Act
        int listUserBefore = library.getUsers().size();
        library.addUser("TannyLe");
        int listUserAfter = library.getUsers().size();

        // Assert
        Assert.assertFalse(library.getUsers().isEmpty());
        Assert.assertTrue("User was successfully added!", listUserBefore < listUserAfter);
    }

    @Test
    public void testAdditionBook_CaseNameEmpty_PrintoutWarningMessage() {
        // Arrange
        library = new Library();
        Book newBook = new Book(bookId++, "", getRandomBookType(), "Just a description");

        // Act
        int listBookBefore = library.getBooks().size();
        library.addBook(newBook);
        int listBookAfter = library.getBooks().size();

        // Assert
        Assert.assertTrue(library.getBooks().isEmpty());
        Assert.assertEquals("Book's name cannot be blank!", listBookBefore, listBookAfter);
    }

    @Test
    public void testAdditionBook_ValidName_PrintoutSuccessfulMessage() {
        // Arrange
        library = new Library();
        Book newBook = new Book(bookId++, "Just a book name", getRandomBookType(), "Just a description");

        // Act
        int listBookBefore = library.getBooks().size();
        library.addBook(newBook);
        int listBookAfter = library.getBooks().size();

        // Assert
        Assert.assertFalse(library.getBooks().isEmpty());
        Assert.assertTrue("Book was successfully added!", listBookBefore < listBookAfter);
    }

    @Test
    public void testCheckOutBook_InvalidUserId_PrintoutWarningMessage() {
        // Arrange
        library = new Library();
        userId = 1000;
        Book newBook = new Book(bookId++, "Just a book name", getRandomBookType(), "Just a description");
        library.addBook(newBook);

        // Act
        library.checkOutBook(userId, newBook.getUniqueId());

        // Assert
        Assert.assertNull(newBook.getToDate());
        Assert.assertTrue("User not found!", library.getUsers().isEmpty());
    }

    @Test
    public void testCheckOutBook_InvalidBookId_PrintoutWarningMessage() {
        // Arrange
        library = new Library();
        bookId = 1000;
        library.addUser("TannyLe");
        int addedUserId = library.getUsers().get(0).getUniqueId();

        // Act
        library.checkOutBook(addedUserId, bookId);

        // Assert
        Assert.assertFalse(library.getUsers().isEmpty());
        Assert.assertTrue("Book not found!", library.getBooks().isEmpty());
    }

    @Test
    public void testCheckOutBook_ValidBookAndUserId_PrintoutSuccessfulMessage() {
        // Arrange
        library = new Library();
        String userName = "TannyLe";
        library.addUser(userName);
        int addedUserId = library.getUsers().get(0).getUniqueId();
        Book newBook = new Book(bookId++, "Just a book name", getRandomBookType(), "Just a description");
        library.addBook(newBook);

        // Act
        int booksBorrowedBefore = library.getUsers().get(0).getBooksBorrowed().size();
        library.checkOutBook(addedUserId, newBook.getUniqueId());
        int booksBorrowedAfter = library.getUsers().get(0).getBooksBorrowed().size();

        // Assert
        Assert.assertFalse(library.getUsers().isEmpty());
        Assert.assertFalse(library.getBooks().isEmpty());
        Assert.assertNotNull(library.getUsers().get(0));
        Assert.assertNotNull(newBook.getToDate());
        Assert.assertTrue("Book was successfully borrowed by User: " + userName, booksBorrowedBefore < booksBorrowedAfter);
    }

    @Test
    public void testReturnBook_InvalidUserId_PrintoutWarningMessage() {
        // Arrange
        library = new Library();
        userId = 1000;
        Book newBook = new Book(bookId++, "Just a book name", getRandomBookType(), "Just a description");
        library.addBook(newBook);

        // Act
        library.returnBook(userId, newBook.getUniqueId());

        // Assert
        Assert.assertNull(newBook.getToDate());
        Assert.assertTrue("User not found!", library.getUsers().isEmpty());
    }

    @Test
    public void testReturnBook_InvalidBookId_PrintoutWarningMessage() {
        // Arrange
        library = new Library();
        bookId = 1000;
        library.addUser("TannyLe");
        int addedUserId = library.getUsers().get(0).getUniqueId();

        // Act
        library.returnBook(addedUserId, bookId);

        // Assert
        Assert.assertFalse(library.getUsers().isEmpty());
        Assert.assertTrue("Book not found!", library.getBooks().isEmpty());
    }

    @Test
    public void testReturnBook_ValidBookAndUserId_PrintoutSuccessfulMessage() {
        // Arrange
        library = new Library();
        String userName = "TannyLe";
        library.addUser(userName);
        int addedUserId = library.getUsers().get(0).getUniqueId();
        Book newBook = new Book(bookId++, "Just a book name", getRandomBookType(), "Just a description");
        library.addBook(newBook);

        // Act
        library.checkOutBook(addedUserId, newBook.getUniqueId());
        int booksBorrowedBefore = library.getUsers().get(0).getBooksBorrowed().size();
        library.returnBook(addedUserId, newBook.getUniqueId());
        int booksBorrowedAfter = library.getUsers().get(0).getBooksBorrowed().size();

        // Assert
        Assert.assertFalse(library.getUsers().isEmpty());
        Assert.assertFalse(library.getBooks().isEmpty());
        Assert.assertNotNull(library.getUsers().get(0));
        Assert.assertNull(newBook.getToDate());
        Assert.assertTrue("Book was successfully returned by User: " + userName, booksBorrowedBefore > booksBorrowedAfter);
    }

    @Test
    public void testContactUser_InvalidUserId_PrintoutWarningMessage() {
        // Arrange
        library = new Library();
        int userId = 1000;

        // Act
        library.contactUser(userId);

        // Assert
        Assert.assertTrue("User not found!", true);
    }

    @Test
    public void testContactUser_ValidUserId_PrintoutSuccessfulMessage() {
        // Arrange
        library = new Library();
        String userName = "TannyLe";
        library.addUser(userName);
        int addedUserId = library.getUsers().get(0).getUniqueId();
        books.forEach(book -> {
            library.addBook(book);
            library.checkOutBook(addedUserId, book.getUniqueId());
        });

        // Act
        library.contactUser(addedUserId);

        // Assert
        Assert.assertTrue("User not found!", true);
        Assert.assertEquals(library.getBooks().size(), books.size());
        Assert.assertEquals(library.getUsers().get(0).getBooksBorrowed().size(), books.size());
    }
}
