import java.util.ArrayList;
import java.util.List;

public class User {
    //region Properties
    /**
     * Indicates unique and auto-incremental ID.
     */
    private int uniqueId;

    /**
     * Indicates user's name.
     */
    private String name;

    /**
     * Indicates book IDs and names that user's borrowing.
     */
    private List<Book> booksBorrowed = new ArrayList<>();

    /**
     * Indicates unique ID of the library this user belongs to.
     */
    private int libraryId;

    /**
     * Indicates fine that user owes, counted in euro.
     */
    private int fineOwed = 0;
    //endregion

    /**
     * Constructor
     *
     * @param uniqueId  user's unique ID.
     * @param name      user's name.
     * @param libraryId library's ID.
     */
    public User(int uniqueId, String name, int libraryId) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.libraryId = libraryId;
    }

    // region Getter and Setter

    /**
     * Getter
     */
    public int getUniqueId() {
        return uniqueId;
    }

    /**
     * Getter
     */
    public String getName() {
        return name;
    }

    /**
     * Getter
     */
    public List<Book> getBooksBorrowed() {
        return booksBorrowed;
    }

    /**
     * Getter
     */
    public int getFineOwed() {
        return fineOwed;
    }

    /**
     * Setter
     */
    public void setFineOwed(int fineOwed) {
        this.fineOwed += fineOwed;
    }

    //endregion

    /**
     * Add book borrowed to the list.
     *
     * @param bookBorrowed book borrowed.
     */
    public void addBookBorrowed(Book bookBorrowed) {
        this.booksBorrowed.add(bookBorrowed);
    }

    /**
     * Remove book borrowed from the list.
     *
     * @param bookBorrowed book borrowed.
     */
    public void removeBookBorrowed(Book bookBorrowed) {
        this.booksBorrowed.remove(bookBorrowed);
    }
}
