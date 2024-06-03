import org.junit.jupiter.api.Test;
import utilities.CSVreader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CSVReaderTest {
    //! Please change this line to your project directory path
    String userDir = "C:/Users/super/OneDrive/Cours/TUM BIE/Introduction to Software Engineering/Project/csv/";
    String bookCsv = userDir + "book_database_test.csv";
    String bookCopyCsv = userDir + "bookcopy_database_test.csv";
    String customerCsv = userDir + "customer_database_test.csv";

    @Test
    void makeBooksSuccessful() throws FileNotFoundException {
        //List<String[]> importBook = CSVreader.parseFile(bookCsv);
        //assertEquals("978-2-234-09252-5", .get(0).getIsbn());
    }

    @Test
    void makeBooksFileNotFound() throws FileNotFoundException {
        //assertThrows(FileNotFoundException.class, () -> CSVreader.makeBooks("wrong path").get(0).getIsbn());
    }
}
