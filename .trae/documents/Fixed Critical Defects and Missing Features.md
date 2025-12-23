Fixes and improvements implemented:

1. **Ranking Functionality (P0/P1)**:

   * Updated `BorrowRecordDao.java` to add `findRanking(startDate, endDate)`.

   * Updated `QueryServlet.java` to implement `ranking` action.

   * Created `src/main/webapp/WEB-INF/views/query/ranking_list.jsp` for display.

2. **System Parameters Management (P1)**:

   * Updated `SysParamDao.java` to add `updateValue`.

   * Created `SysParamServlet.java` (mapped to `/system/params`) to handle updates.

   * Created `src/main/webapp/WEB-INF/views/system/params.jsp` for the UI.

   * *Note*: This is protected by `RoleFilter` (path `/system/*` requires `SYS_ADMIN`).

3. **Bookshelf Management (P1)**:

   * Rewrote `BookshelfDao.java` to implement full CRUD (insert, update, delete, findById).

   * Created `BookshelfServlet.java` (mapped to `/book/bookshelf`).

   * Created `src/main/webapp/WEB-INF/views/book/bookshelf_list.jsp` and `bookshelf_form.jsp`.

4. **Transaction & Concurrency Safety (P0)**:

   * Modified `BookDao.updateStock` to use atomic SQL update with a condition (`stock + ? >= 0` for borrowing).

   * It now throws `RuntimeException` if stock is insufficient, triggering `BorrowService`'s transaction rollback.

**Manual Testing Guide:**

1. **Ranking**:

   * Go to `/demo3/query/ranking`.

   * Select dates (e.g., this month) and click "Statistics".

   * Verify top borrowed books appear.

2. **System Params**:

   * Login as `SYS_ADMIN`.

   * Go to `/demo3/system/params`.

   * Modify `max_borrow_num` and save.

   * Verify the change persists.

3. **Bookshelf**:

   * Go to `/demo3/book/bookshelf`.

   * Add a new bookshelf.

   * Edit it.

   * Delete it.

4. **Concurrency/Stock**:

   * Set a book's stock to 1.

   * Borrow it (Success).

   * Try to borrow it again (Should fail with "Stock update failed" or similar error).

