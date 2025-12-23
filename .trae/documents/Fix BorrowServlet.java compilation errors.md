I will fix the compilation errors in `BorrowServlet.java` caused by incorrect placement of import statements and malformed class structure.

**Plan:**
1.  **Read `BorrowServlet.java`**: Examine the current file content to confirm the syntax errors (likely imports placed after annotations or inside the class, and potentially duplicate class definitions).
2.  **Rewrite `BorrowServlet.java`**:  Re-upload the *entire* correct content of `BorrowServlet.java`. I will ensure:
    *   All imports are at the very top.
    *   The `@WebServlet` annotation is immediately before the class definition.
    *   There is only one `public class BorrowServlet` definition.
    *   The `ReaderTypeDao` and `ReaderType` logic is correctly integrated.
3.  **Compile**: Run `mvn clean compile` to verify the fix.
