# How to install the prototype

1. Download the source code or clone the project via ssh or https.
2. Open IntelliJ (because it's the only IDE we are working with, and we are sure it works with it).
3. Open the prototype folder in IntelliJ. ⚠️ THE WHOLE REPOSITORY FOLDER
4. Wait for Gradle to install properly.

## Run the prototype
1. Go in the main file (```src/main/java/Main.java```) and click on the first green play button, then the first option ```Run 'Main.main()'```.

## Execute the test
1. Go in test file (```src/test/java/controller/ControllerTest.java```) and click on the first green play button, then ```Run ControllerTest```.

## How to calculate the Coverage test
1. Go in test file (```src/test/java/controller/ControllerTest.java```) and click on the ```Run``` button.
2. choose ```Edit Configurations```.
3. click on ```Modify options```.
4. click on ```Specify alternative coverage runner```.
5. choose ```JaCoCo``` in ```Choose coverage runner```.
6. go to the src>test>java>run 'Tests in Prototype.test' with coverage

## Usage of the Core Processes
#### 1. Delete a book (via ISBN):
Navigate to the book menu `0`, then search for a book `0`. After entering the correct ISBN, select delete book `0`.
#### 2. Delete a book copy (via ID)
Navigate to the book menu `0`, then search for a book `0`. After entering the correct ISBN, select delete a copy of this book `1`. Then enter a valid ID to delete the book copy.
#### 3. Delete a customer (via ID)
Navigate to the customer menu `1`, then search for a customer `0`. After entering the correct ID, select delete the customer `0`. 

## Objects Example

To properly test our prototype, we are creating some objects statically in the Main class and the Test class.
You can also look at the following examples to see how the different objects are implemented.

### Book
| Title             | Author             | Isbn              | Publication Date | Classification Number |
|-------------------|--------------------|-------------------|------------------|-----------------------|
| Les Fleurs du Mal | Charles Baudelaire | 978-2-290-11507-7 | 21/06/1857       | BAU01                 |
| Candide           | Voltaire           | isbn02            | 01/01/1759       | VOL01                 |


### Customer
| Id | First Name | Last Name  | Date of Birth | Subscription Date | Borrowing List                  |
|----|------------|------------|---------------|-------------------|---------------------------------|
| 1  | Max        | Mustermann | 01/11/2024    | Date of today     | Empty ArrayList                 |
| 2  | Vrushabh   | Jain       | 30/10/2004    | 01/05/2024        | ArrayList<BookCopy1, BookCopy2> |


### Book Copy
| Id       | Book                    | Borrower    | Borrowed Date | Returned Date | Fee  |
|----------|-------------------------|-------------|---------------|---------------|------|
| VOL01_01 | Book                    | Customer<1> | 05/05/2024    | 20/05/2024    | 0.00 |
| BAU01_01 | Book<978-2-290-11507-7> | null        | null          | null          | 0.00 |

## Troubleshooting

- If the green button doesn't appear or you can't run the program, please make sure that you open the right folder.
- If the problem persists, please check your JDK version and your Gradle version.



