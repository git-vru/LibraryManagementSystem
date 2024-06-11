### Note on the assignment 5
Because no feedback for assignment 4 was provided on time, we decide to focus on the tasks 2 & 3 .
A full code review, doesn't make much sense considering the amount of code that has been changed/added.
It's preferable for us to wait for the feedback and review the code in consequences, before deeply refactoring the project.

# How to install the prototype

1. Download the source code or clone the project via ssh or https.
2. Open IntelliJ (because it's the only IDE we are working with, and we are sure it works with it).
3. Open the prototype folder in IntelliJ. ⚠️ THE WHOLE REPOSITORY FOLDER
4. Wait for Gradle to install properly.

## Run the prototype
1. Go in the main file (```src/main/java/Main.java```) and click on the first green play button, then the first option ```Run 'Main.main()'```.

## Execute the test
1. Go in test file (```src/test/java/controller/ControllerTest.java```) and click on the first green play button, then ```Run ControllerTest```.

## Build the project (using Gradle)
1. Open a terminal
2. Go to the project folder and build the using the gradle command (i.e. ```./gradlew build``` on Linux/MacOS/WSL)
3. Go to the build folder (```PROJECT_FOLDER/build/classes/java/main```)
4. Run the program with ```java Main```

## How to calculate the Coverage test
1. Go in test file (```src/test/java/controller/ControllerTest.java```) and click on the ```Run``` button.
2. choose ```Edit Configurations```.
3. click on ```Modify options```.
4. click on ```Specify alternative coverage runner```.
5. choose ```JaCoCo``` in ```Choose coverage runner```.
6. go to the src>test>java>run 'Tests in Prototype.test' with coverage

## Usage of the Core Processes

### Book
Navigate to the book menu `0`

#### 1. Search for a book:
In the book menu, enter `0`. Then enter `0` to search via ISBN, `1` to search via Title or `2` to search via Author.

#### 2. Add a book:
In the book menu, enter `1` and then enter `0` to add a single book in the command line or `1` to import a csv file.
The csv file must respect the following format: `<isbn>,<title>,<author>,<publication date (YYYY-MM-DD)>`

#### 3. Modify a book:
Search for a book `0`.
To modify the title, enter `3`.
To modify the author, enter `4`.
To modify the publication test, enter `5`.
To modify the classification number/shelf location, enter `6`.

The data entered cannot be empty.

#### 4. Delete a book:
Search for a book `0`. Select delete book `0`.

### Book Copy
Navigate to the book menu `0`

#### 1. Search for a book copy:
In the book menu, enter `0`. Then enter `1` and then enter a book copy id.

#### 2. Add a book copy:
To add a book copy for a spesific book:
In the book menu, search for any book `0` and enter `1` to add a book copy.

To add a book copy from a csv file:
In the book menu, enter `3` and enter the path to the csv file.
The csv file must respect one of the following format:
- New NOT borrowed book copy: `<isbn>`
- New borrowed book copy: `<isbn>,<customer_id>,<borrowed_date>,<return_date>,<fee>`
- Pre-existing NOT borrowed book copy: `<bookcopy_id>,<isbn>`
- Pre-existing borrowed book copy: `<bookcopy_id>,<isbn>,<customer_id>,<borrowed_date>,<return_date>,<fee>`

The uniqueness of the `bookcopy_id` is not check!
If the customer id is null or a customer can't be found, no book copy will be created.

#### 3. Delete a book copy:
Navigate to the book menu `0`, then search for a book `0`.
Enter `2` to delete a copy of this book. Then enter a valid book copy ID to delete the book copy.

### Customer
Navigate to the customer menu `1`

#### 1. Search for a customer:
In the customer menu, enter `0`. Then enter `0` and then enter a customer id.

#### 2. Add a customer:
In the customer menu, enter `1` and then enter `0` to add a single customer in the command line or `1` to import a csv file.
The csv file must respect the following format: `<first name>,<last name>,<date of birth (YYYY-MM-DD)>`

#### 3. Modify a customer:
Search for a customer `0`.
To modify the first name, enter `3`.
To modify the last name, enter `4`.
To modify the birth date, enter `5`.

The data entered cannot be empty.

#### 4. Delete a customer:
Search for a customer `0`. Select delete book `0`. 

### Borrowing

#### 1. Borrow a book
Open borrowing menu `2`, then create a new borrowing `0`, then enter a `customer id` and a `book copy id`.

#### 2. Return a book
Open borrowing menu `2`, then return a book `1`, then pass the `book copy id`.

#### 3. Borrow a book (via customer)
Open the customer menu `1`, search for the customer `0` and select Borrow a book for customer: (customer) `1` Then enter the `book copy id`.

#### 4. Return a book (via customer)
Open customer menu `1`, search for the customer `0` and select Return a book for customer: (customer) `2`. Then enter the `book copy id`.

## Objects Example

To properly test our prototype, we are creating some objects statically in the Main class and the Test class.
You can also look at the following examples to see how the different objects are implemented.

### Book
| Title             | Author             | Isbn (unique)     | Publication Date | Classification Number |
|-------------------|--------------------|-------------------|------------------|-----------------------|
| Les Fleurs du Mal | Charles Baudelaire | 978-2-290-11507-7 | 21/06/1857       | BAU01                 |
| Candide           | Voltaire           | isbn02            | 01/01/1759       | VOL01                 |


### Customer
| Id | First Name | Last Name  | Date of Birth | Subscription Date | Borrowing List                  |
|----|------------|------------|---------------|-------------------|---------------------------------|
| 1  | Max        | Mustermann | 01/11/2024    | Date of today     | Empty ArrayList                 |
| 2  | Vrushabh   | Jain       | 30/10/2004    | 01/05/2024        | ArrayList<BookCopy1, BookCopy2> |


### Book Copy
| Id       | Book                    | Borrower Id | Borrowed Date | Returned Date | Fee  |
|----------|-------------------------|-------------|---------------|---------------|------|
| VOL01_01 | Book                    | 1           | 05/05/2024    | 20/05/2024    | 0.00 |
| BAU01_01 | Book<978-2-290-11507-7> | null        | null          | null          | 0.00 |

## Troubleshooting

- If the green button doesn't appear or you can't run the program, please make sure that you open the right folder.
- If the problem persists, please check your JDK version and your Gradle version.



