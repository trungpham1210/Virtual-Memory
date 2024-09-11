---

# Virtual Memory Management System

## Project Description
A Java-based application that simulates a virtual memory management system, incorporating key concepts such as page tables, frames, and Translation Lookaside Buffer (TLB). The system efficiently handles address translation and memory allocation, ensuring optimal performance and resource utilization.

## Features
- **Address Translation**: Implements methods to convert logical addresses to physical addresses, ensuring accurate memory access.
- **TLB Management**: Utilizes a Translation Lookaside Buffer (TLB) to speed up address translation by caching recent translations.
- **Frame Management**: Manages frames in main memory, including adding and clearing frames, and handling page replacement.
- **Comprehensive Testing**: Includes a suite of JUnit test cases to validate the functionality of the memory management system under various scenarios.
- **Utility Methods**: Provides utility methods for testing address lookups and defining constants related to memory sizes.

## Technologies Used
- **Java**
- **Memory Management**
- **Concurrency**
- **JUnit**

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Maven or Gradle for dependency management

### Installation
1. Clone the repository:
   ```sh
   git clone https://github.com/trungpham1210/Virtual-Memory.git
   ```
2. Navigate to the project directory:
   ```sh
   cd Virtual-Memory
   ```
3. Build the project using Maven or Gradle:
   ```sh
   mvn clean install
   ```
   or
   ```sh
   gradle build
   ```

### Running the Tests
To run the JUnit test cases, use the following command:
```sh
mvn test
```
or
```sh
gradle test
```

## Usage
1. Create instances of `Process` and `PageTableEntry` for each process's pages.
2. Use the `MainMemory` class to simulate the memory management by calling the `addPageToMemory` and `getPhysicalAddress` methods.
3. Verify the address translation and TLB functionality using the provided test cases.

## Example
```java
Process process = new Process(16);
MainMemory mainMemory = StudentSubmissionFactory.createMainMemory(TestUtil.SIXTY_FOUR_KB);
mainMemory.addPageToMemory(process.getEntryAt(1));

TestUtil.testLookup(4096, process, mainMemory, 0);
TestUtil.testLookup(4098, process, mainMemory, 2);
TestUtil.testLookup(0, process, mainMemory, -1);
```

## Contributing
Contributions are welcome! Please fork the repository and create a pull request with your changes.

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---
