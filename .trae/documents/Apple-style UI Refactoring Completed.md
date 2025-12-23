I have completed the Apple-style UI refactoring for the entire project.

**Implementation Details:**

1.  **New Asset System (`/assets`)**:
    *   `css/apple.css`: Core design system defining variables for System Blue (`#0071e3`), SF fonts, layout grid, and spacing.
    *   `css/components.css`: Reusable component library including `.card`, `.btn-primary/secondary`, `.table`, `.form-control`, `.badge`, `.modal`, and `.toast`.
    *   `js/ui.js`: Lightweight Vanilla JS library handling:
        *   **Toast Notifications**: Replaces default alerts with floating, auto-dismissing toasts.
        *   **Confirm Modal**: Custom iOS-style confirmation dialogs for destructive actions (replacing `window.confirm`).
        *   **Loading States**: Auto-disables submit buttons to prevent double-posting.

2.  **Unified Layout Architecture**:
    *   Updated `nav.jsp` to a translucent sidebar with active state highlighting.
    *   Updated `top.jsp` to a minimalist top bar showing user profile.
    *   Introduced `.app-layout`, `.app-sidebar`, `.app-main`, `.app-content` structure across all pages.

3.  **Page Transformations**:
    *   **Login**: Centered, shadow-elevated card design with focused inputs.
    *   **Dashboard**: Welcoming "Hello" message with quick-action cards.
    *   **Lists (Books/Readers/Admins)**: Clean tables with hover effects, badge indicators for status, and sticky headers.
    *   **Forms**: Grid-based layout (2-column or 3-column) for better readability, with clear labels and input focus states.
    *   **Borrow Dashboard**: A specialized layout with a "Search Card", "Reader Profile Card", "Action Card", and "History Table" all in one view.

4.  **Interaction Enhancements**:
    *   **Delete Actions**: Now trigger a custom modal instead of a browser alert.
    *   **Feedback**: Success/Error messages from the server are automatically converted to Toasts.

**Verification Guide:**
1.  **Login**: `http://localhost:8080/demo3/` - Check the new centered card layout.
2.  **Dashboard**: Login and see the welcome screen.
3.  **Books**: Go to "图书档案", try adding a book (grid form) and deleting one (custom modal).
4.  **Borrow**: Go to "图书借阅", search for a reader (e.g., `R001` or `12345678901`). You should see the new split-view dashboard.

All resources are local, no internet connection required.
