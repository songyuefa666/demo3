// Apple-style UI interactions

const UI = {
    // Toast Notification
    showToast: function(message, type = 'success') {
        const container = document.getElementById('toast-container');
        if (!container) return;

        const toast = document.createElement('div');
        toast.className = `toast toast-${type}`;
        
        const icon = type === 'success' ? '✓' : (type === 'warning' ? '!' : '✕');
        
        toast.innerHTML = `
            <div class="toast-icon">${icon}</div>
            <div class="toast-message">${message}</div>
        `;

        container.appendChild(toast);

        // Trigger animation
        requestAnimationFrame(() => {
            toast.classList.add('show');
        });

        // Remove after 3s
        setTimeout(() => {
            toast.classList.remove('show');
            setTimeout(() => toast.remove(), 300);
        }, 3000);
    },

    // Confirm Modal
    confirm: function(message, onConfirm) {
        // Create modal DOM if not exists
        let modal = document.getElementById('ui-modal');
        if (!modal) {
            modal = document.createElement('div');
            modal.id = 'ui-modal';
            modal.className = 'modal-overlay';
            modal.innerHTML = `
                <div class="modal">
                    <div class="modal-header">
                        <div class="modal-title">确认操作</div>
                    </div>
                    <div class="modal-body" id="ui-modal-message"></div>
                    <div class="modal-footer">
                        <button class="btn btn-secondary" id="ui-modal-cancel">取消</button>
                        <button class="btn btn-danger" id="ui-modal-confirm">确认</button>
                    </div>
                </div>
            `;
            document.body.appendChild(modal);
            
            // Bind events
            document.getElementById('ui-modal-cancel').onclick = UI.closeModal;
            modal.onclick = (e) => {
                if (e.target === modal) UI.closeModal();
            };
        }

        document.getElementById('ui-modal-message').textContent = message;
        
        const confirmBtn = document.getElementById('ui-modal-confirm');
        // Remove old listener to prevent stack
        const newConfirmBtn = confirmBtn.cloneNode(true);
        confirmBtn.parentNode.replaceChild(newConfirmBtn, confirmBtn);
        
        newConfirmBtn.onclick = () => {
            UI.closeModal();
            if (onConfirm) onConfirm();
        };

        modal.style.display = 'flex';
        requestAnimationFrame(() => modal.classList.add('show'));
    },

    closeModal: function() {
        const modal = document.getElementById('ui-modal');
        if (modal) {
            modal.classList.remove('show');
            setTimeout(() => modal.style.display = 'none', 200);
        }
    },

    // Init Page
    init: function() {
        // Add toast container
        if (!document.getElementById('toast-container')) {
            const tc = document.createElement('div');
            tc.id = 'toast-container';
            tc.className = 'toast-container';
            document.body.appendChild(tc);
        }

        // Auto-handle server-side alerts
        const alerts = document.querySelectorAll('.alert-server-msg');
        alerts.forEach(alert => {
            const msg = alert.getAttribute('data-msg');
            const type = alert.getAttribute('data-type');
            if (msg) {
                UI.showToast(msg, type);
                alert.style.display = 'none'; // Hide the static alert if we use toast
            }
        });
        
        // Button loading state
        document.querySelectorAll('form').forEach(form => {
            form.addEventListener('submit', function() {
                const btn = this.querySelector('button[type="submit"]');
                if (btn) {
                    btn.classList.add('disabled');
                    btn.style.opacity = '0.7';
                    const originalText = btn.innerText;
                    btn.innerText = '处理中...';
                    // Allow submitting but prevent double clicks
                    setTimeout(() => {
                        btn.classList.remove('disabled');
                        btn.style.opacity = '1';
                        btn.innerText = originalText;
                    }, 5000); // Reset after 5s timeout safety
                }
            });
        });
    }
};

document.addEventListener('DOMContentLoaded', UI.init);

// Global shortcut for delete links
window.confirmDelete = function(url) {
    UI.confirm('确定要删除这条记录吗？此操作不可恢复。', () => {
        window.location.href = url;
    });
    return false;
};
