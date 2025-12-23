UI Refactoring Complete.

**Summary of changes:**

1.  **Created Assets**:
    *   `src/main/webapp/assets/css/app.css`: Custom CSS for admin layout, cards, tables, forms, etc.
    *   `src/main/webapp/assets/js/app.js`: Helper for alerts and delete confirmation.

2.  **Created Common JSPs**:
    *   `WEB-INF/views/common/head.jsp`: Includes CSS/JS.
    *   `WEB-INF/views/common/top.jsp`: Top navigation bar.
    *   `WEB-INF/views/common/nav.jsp`: Sidebar navigation with active state.
    *   `WEB-INF/views/common/footer.jsp`: Simple footer.
    *   `WEB-INF/views/common/message.jsp`: Standardized alert component.

3.  **Refactored Pages**:
    *   `login.jsp`: Centered login box layout.
    *   `home.jsp`: Dashboard layout with shortcut buttons.
    *   `book_list.jsp`: Search bar, styled table, pagination.
    *   `book_form.jsp`: Grid layout form.
    *   `reader_list.jsp`: Styled table, status badges.
    *   `reader_form.jsp`: Standard form.
    *   `borrow_form.jsp`: Split layout (Reader Info + Book Borrow + List).
    *   `system/admin_list.jsp` & `admin_form.jsp`: Standard CRUD styles.
    *   `error/403.jsp`, `404.jsp`, `500.jsp`: Centered error messages with stack trace for 500.

**Before vs After Examples:**

*   **Login Page**:
    *   *Before*: Plain HTML form, top-left aligned.
    *   *After*: Centered white card on gray background, styled inputs, large blue button.

*   **Book List**:
    *   *Before*: Raw HTML table (`border="1"`), plain links.
    *   *After*: Clean white card, zebra-striped table, hover effects, styled "Edit/Delete" buttons, proper pagination UI.

*   **Borrow Page**:
    *   *Before*: Vertical list of inputs and raw text.
    *   *After*: Integrated dashboard feel. Top search bar. When reader found: beautiful info card with limits/fines, clear input for book barcode, and a color-coded table for current borrowings (red for overdue).
