import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class Library {
    //region Properties
    /**
     * Indicates fine's fee for each day exceeds the deadline, counted in euro.
     */
    private static final int FINE_FEE = 5;

    /**
     * Indicates unique and auto-incremental ID.
     */
    private int uniqueId = 0;

    /**
     * Indicates user's unique and auto-incremental ID, used for creating new user.
     */
    private int userUniqueId = 0;

    /**
     * Indicates book's unique and auto-incremental ID, used for creating new book.
     */
    private int bookUniqueId = 0;

    /**
     * Indicates list of library's users.
     */
    private List<User> users = new ArrayList<>();

    /**
     * Indicates list of library's books.
     */
    private List<Book> books = new ArrayList<>();
    //endregion

    // region Getter and Setter

    /**
     * Getter
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Getter
     */
    public List<Book> getBooks() {
        return books;
    }
    //endregion

    /**
     * Add new user to the library.
     *
     * @param name user's name.
     */
    public void addUser(String name) {
        // check if name is blank or empty.
        if (name.trim().isEmpty()) {
            System.out.println("User's name cannot be blank!");
            return;
        }

        // create brand-new user then add to the list.
        User newUser = new User(userUniqueId++, name, uniqueId);
        users.add(newUser);
        System.out.println("User was successfully added!");
    }

    /**
     * Add new book to the library.
     *
     * @param newBook new book to be added.
     */
    public void addBook(Book newBook) {
        // check if name is blank or empty.
        if (newBook.getName().trim().isEmpty()) {
            System.out.println("Book's name cannot be blank!");
            return;
        }

        // adding relationships
        newBook.setLibraryId(uniqueId);
        newBook.setUniqueId(bookUniqueId++);

        // add book to the list.
        books.add(newBook);
        System.out.println("Book was successfully added!");
    }

    /**
     * Check out book from the library by user.
     *
     * @param inputUserUniqueId user unique ID that borrows the book.
     * @param inputBookUniqueId book unique ID that is borrowed.
     */
    public void checkOutBook(int inputUserUniqueId, int inputBookUniqueId) {
        // check if user exists.
        User checkedUser = getUserById(inputUserUniqueId);
        if (checkedUser == null) return;

        // check if book exists.
        Book checkedBook = getBookById(inputBookUniqueId);
        if (checkedBook == null) return;

        // check if book is available to be borrowed.
        if (checkedBook.getToDate() != null) {
            System.out.println("This book is already borrowed!");
        }

        // book is no longer available since user has borrowed it.
        checkedBook.setDeadline(LocalDate.now());
        checkedBook.setUserId(inputUserUniqueId);
        checkedUser.addBookBorrowed(checkedBook);

        System.out.println("Book was successfully borrowed by User: " + checkedUser.getName());
    }

    /**
     * Return book back to the library by user.
     * Fine might apply if user returns the book exceeds the deadline.
     *
     * @param inputUserUniqueId user unique ID that returns the book.
     * @param inputBookUniqueId book unique ID that is returned.
     */
    public void returnBook(int inputUserUniqueId, int inputBookUniqueId) {
        // check if user exists.
        User checkedUser = getUserById(inputUserUniqueId);
        if (checkedUser == null) return;

        // check if book exists.
        Book checkedBook = getBookById(inputBookUniqueId);
        if (checkedBook == null) return;

        // calculate fine.
        calculateFine(checkedBook, checkedUser);

        // book is now available.
        checkedBook.setDeadline(null);
        checkedBook.setUserId(-1);
        checkedUser.removeBookBorrowed(checkedBook);

        System.out.println("Book was successfully returned by User: " + checkedUser.getName());
    }

    /**
     * Contact user and announce to them about books' status.
     *
     * @param inputUserUniqueId target user to be announced.
     */
    public void contactUser(int inputUserUniqueId) {
        // check if user exists.
        User checkedUser = getUserById(inputUserUniqueId);
        if (checkedUser == null) return;

        // classify books borrowed by user into 2 categories, valid borrowed books and late returned books.
        List<Book> lateReturnedBooks = new ArrayList<>();
        List<Book> validBorrowedBooks = new ArrayList<>();
        checkedUser.getBooksBorrowed().forEach(curBook -> {
            if (calculateDateExceedDeadline(curBook.getToDate()) > 0) {
                lateReturnedBooks.add(curBook);
            } else {
                validBorrowedBooks.add(curBook);
            }
        });

        // display message to user.
        displayMessage(checkedUser.getName(), validBorrowedBooks, lateReturnedBooks);
    }

    /**
     * Display message to target user about books' status.
     *
     * @param userName           target user's name.
     * @param validBorrowedBooks list of valid borrowed books.
     * @param lateReturnedBooks  list of late returned books.
     */
    private void displayMessage(String userName, List<Book> validBorrowedBooks, List<Book> lateReturnedBooks) {
        System.out.println("Dear user: " + userName + ",");
        System.out.printf("Today is: %tF%n\n", LocalDate.now());
        // message for valid books.
        System.out.println("You are currently borrowing " + validBorrowedBooks.size() + " valid book(s)");
        if (!validBorrowedBooks.isEmpty()) {
            validBorrowedBooks.forEach(validBook -> {
                System.out.printf("+ ID: %d, name: %s.  Deadline: %tF%n\n", validBook.getUniqueId(), validBook.getName(), validBook.getToDate());
            });
        }
        System.out.println("--------------------");

        // message for late returned books.
        System.out.println("You are currently having " + lateReturnedBooks.size() + " book(s) that exceeds the deadline, please return them to the library!");
        if (!lateReturnedBooks.isEmpty()) {
            lateReturnedBooks.forEach(lateBook -> {
                System.out.printf("+ ID: %d, name: %s.  Deadline: %tF%n\n", lateBook.getUniqueId(), lateBook.getName(), lateBook.getToDate());
            });
        }
        System.out.println("--------------------");
    }

    /**
     * Calculate date exceed the defined deadline.
     *
     * @param toDate defined deadline.
     * @return number of dates exceed.
     */
    private int calculateDateExceedDeadline(LocalDate toDate) {
        return Period.between(toDate, LocalDate.now()).getDays();
    }

    /**
     * Calculate fine.
     *
     * @param checkedBook book returned.
     * @param checkedUser target user.
     */
    private void calculateFine(Book checkedBook, User checkedUser) {
        //calculate fine.
        int dateExceedDeadline = calculateDateExceedDeadline(checkedBook.getToDate());
        if (dateExceedDeadline > 0) {
            int fineFee = FINE_FEE * dateExceedDeadline;
            checkedUser.setFineOwed(fineFee);
            System.out.println("Fine for exceeding deadline " + dateExceedDeadline + " days: " + fineFee + "€");
        }
    }

    /**
     * Get user by given ID.
     *
     * @param inputUserUniqueId user's unique ID.
     * @return User with input ID, null if not found.
     */
    private User getUserById(int inputUserUniqueId) {
        for (User curUser : users) {
            if (curUser.getUniqueId() == inputUserUniqueId) {
                return curUser;
            }
        }

        // case not found.
        System.out.println("User not found!");
        return null;
    }

    /**
     * Get book by given ID.
     *
     * @param inputBookUniqueId book's unique ID.
     * @return Book with input ID, null if not found.
     */
    private Book getBookById(int inputBookUniqueId) {
        for (Book curBook : books) {
            if (curBook.getUniqueId() == inputBookUniqueId) {
                return curBook;
            }
        }

        // case not found.
        System.out.println("Book not found!");
        return null;
    }
}
