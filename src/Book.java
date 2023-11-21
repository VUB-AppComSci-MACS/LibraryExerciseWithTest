import java.time.LocalDate;

public class Book {
    //region Properties
    /**
     * Indicates unique and auto-incremental Id.
     */
    private int uniqueId = 0;

    /**
     * Indicates unique ID of the library this book belongs to.
     */
    private int libraryId;

    /**
     * Indicates book's name.
     */
    private String name;

    /**
     * Indicates book's type, which below orders:
     * EDUCATION
     * IT
     * POLITICS
     */
    private BookType bookType;

    /**
     * Indicates book's description.
     */
    private String description;

    /**
     * Indicates time that user successfully borrows the book,
     * default is null if the book is available.
     */
    private LocalDate fromDate = null;

    /**
     * Indicates time that user has to return the book,
     * default is null if the book is available.
     */
    private LocalDate toDate = null;

    /**
     * Indicates if there's user borrowing the book,
     * Default is -1 if the book is available.
     */
    private int userId = -1;
    //endregion

    /**
     * Constructor
     *
     * @param uniqueId    book's Id.
     * @param name        book's name.
     * @param bookType    book's type.
     * @param description book's description.
     */
    public Book(int uniqueId, String name, BookType bookType, String description) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.bookType = bookType;
        this.description = description;
    }

    //region Getter and Setter

    /**
     * Getter
     */
    public String getName() {
        return name;
    }

    /**
     * Getter
     */
    public int getUniqueId() {
        return uniqueId;
    }

    /**
     * Getter
     */
    public LocalDate getToDate() {
        return toDate;
    }


    /**
     * Setter
     */
    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    /**
     * Setter
     */
    public void setLibraryId(int libraryId) {
        this.libraryId = libraryId;
    }

    /**
     * Setter
     */
    public void setDeadline(LocalDate fromDate) {
        this.fromDate = fromDate;

        // Calculate toDate only if fromDate is not null
        if (fromDate != null) {
            this.toDate = fromDate.plusDays(7);
        } else toDate = null;
    }

    /**
     * Setter
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
    //endregion
}
