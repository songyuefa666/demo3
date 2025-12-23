Fixes and improvements implemented:

1. **Resolved** **`BorrowRecordDao.java`** **Compilation Error**: Removed the misplaced method definition inside `findExpiringOrOverdue`'s loop and ensured correct brace structure. The file is now syntactically correct.

2. **Navigation & Accessibility**:

   * Updated `nav.jsp` to include links for:

     * **Query**: Ranking (`/query/ranking`), Expiry (`/query/expiry`), History (`/query/history`).

     * **Management**: Bookshelf (`/book/bookshelf`).

     * **System (Admin only)**: Library Info (`/system/library`), System Params (`/system/params`).

3. **Data Consistency**:

   * Updated `QueryServlet` to call `borrowRecordDao.updateOverdueStatus()` before processing any query. This ensures that whenever a user views lists (Expiry, History, Ranking), the overdue status is up-to-date. Combined with `BorrowServlet`'s existing call, this covers the "closed loop" requirement.

4. **Library Info Management**:

   * Created `LibraryInfoDao` (get/update).

   * Created `LibraryInfoServlet` (index/update).

   * Created `library_info.jsp` (form).

5. **Previous Fixes Confirmed**:

   * `Ranking` logic implemented.

   * `SysParam` update implemented.

   * `Bookshelf` CRUD implemented.

   * `BookDao` atomic update implemented.

**Verification Steps:**

1. **Compile & Restart**: Ensure the project compiles without errors (especially `BorrowRecordDao`).
2. **Navigation**: Log in and check the sidebar. All new links should be visible and functional.
3. **Library Info**: Go to `/system/library`, edit info, save, and refresh to verify persistence.
4. **Overdue Status**:

   * Manually set a borrow record's `due_date` to yesterday in DB.

   * Visit `/query/expiry` or `/borrow`.

   * Check DB: `status` should update from 1 to 3 automatically.
5. **Ranking**: Visit `/query/ranking`, select a date range, and verify results.

