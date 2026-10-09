// Accountant Dashboard JavaScript
class AccountantDashboard {
    constructor() {
        this.currentUser = null;
        this.invoices = [];
        this.currentInvoiceId = null;
        this.currentTab = 'profile';
        
        this.createModal = document.getElementById('createModal');
        this.editModal = document.getElementById('editModal');
        this.deleteModal = document.getElementById('deleteModal');
        this.notificationContainer = document.getElementById('notificationContainer');
        
        this.initializeEventListeners();
        this.loadInitialData();
    }
    
    initializeEventListeners() {
        // Tab Navigation
        document.getElementById('profileTab').addEventListener('click', () => this.switchTab('profile'));
        document.getElementById('invoicesTab').addEventListener('click', () => this.switchTab('invoices'));
        
        // Create Invoice
        document.getElementById('addInvoiceBtn').addEventListener('click', () => this.openCreateModal());
        document.getElementById('createFirstInvoiceBtn').addEventListener('click', () => this.openCreateModal());
        document.getElementById('closeCreateModal').addEventListener('click', () => this.closeCreateModal());
        document.getElementById('cancelCreate').addEventListener('click', () => this.closeCreateModal());
        document.getElementById('createInvoiceForm').addEventListener('submit', (e) => this.handleCreateInvoice(e));
        
        // Edit Invoice
        document.getElementById('closeEditModal').addEventListener('click', () => this.closeEditModal());
        document.getElementById('cancelEdit').addEventListener('click', () => this.closeEditModal());
        document.getElementById('editInvoiceForm').addEventListener('submit', (e) => this.handleUpdateInvoice(e));
        
        // Delete Invoice
        document.getElementById('cancelDelete').addEventListener('click', () => this.closeDeleteModal());
        document.getElementById('confirmDelete').addEventListener('click', () => this.handleDeleteInvoice());
        
        // Search and Filter
        document.getElementById('searchInput').addEventListener('input', () => this.filterInvoices());
        document.getElementById('typeFilter').addEventListener('change', () => this.filterInvoices());
        document.getElementById('dateFilter').addEventListener('change', () => this.filterInvoices());
        
        // Export PDF
        document.getElementById('exportPdfBtn').addEventListener('click', () => this.exportToPDF());
        
        // Character counters
        document.getElementById('createInvoiceNumber').addEventListener('input', () => this.updateCharCounter('createInvoiceNumberCounter', 'createInvoiceNumber', 20));
        document.getElementById('createTitle').addEventListener('input', () => this.updateCharCounter('createTitleCounter', 'createTitle', 100));
        document.getElementById('createDescription').addEventListener('input', () => this.updateCharCounter('createDescriptionCounter', 'createDescription', 500));
        document.getElementById('editInvoiceNumber').addEventListener('input', () => this.updateCharCounter('editInvoiceNumberCounter', 'editInvoiceNumber', 20));
        document.getElementById('editTitle').addEventListener('input', () => this.updateCharCounter('editTitleCounter', 'editTitle', 100));
        document.getElementById('editDescription').addEventListener('input', () => this.updateCharCounter('editDescriptionCounter', 'editDescription', 500));
        
        // Clear errors on input
        document.getElementById('createInvoiceNumber').addEventListener('input', () => this.clearError('createInvoiceNumberError'));
        document.getElementById('createInvoiceDate').addEventListener('change', () => this.clearError('createInvoiceDateError'));
        document.getElementById('createAmount').addEventListener('input', () => this.clearError('createAmountError'));
        document.getElementById('createInvoiceType').addEventListener('change', () => this.clearError('createInvoiceTypeError'));
        document.getElementById('createTitle').addEventListener('input', () => this.clearError('createTitleError'));
        document.getElementById('editInvoiceNumber').addEventListener('input', () => this.clearError('editInvoiceNumberError'));
        document.getElementById('editInvoiceDate').addEventListener('change', () => this.clearError('editInvoiceDateError'));
        document.getElementById('editAmount').addEventListener('input', () => this.clearError('editAmountError'));
        document.getElementById('editInvoiceType').addEventListener('change', () => this.clearError('editInvoiceTypeError'));
        document.getElementById('editTitle').addEventListener('input', () => this.clearError('editTitleError'));
        
        // Close modals on overlay click
        this.createModal.addEventListener('click', (e) => {
            if (e.target === this.createModal) this.closeCreateModal();
        });
        
        this.editModal.addEventListener('click', (e) => {
            if (e.target === this.editModal) this.closeEditModal();
        });
        
        this.deleteModal.addEventListener('click', (e) => {
            if (e.target === this.deleteModal) this.closeDeleteModal();
        });
    }
    
    async loadInitialData() {
        try {
            // Get current user ID from session storage
            const userId = this.getCurrentUserId();
            if (!userId) {
                this.showNotification('User session not found. Please login again.', 'error');
                setTimeout(() => {
                    window.location.href = '/login';
                }, 2000);
                return;
            }
            
            // Load user info
            await this.loadUserInfo(userId);
            
            // Load invoices
            await this.loadInvoices();
            
        } catch (error) {
            console.error('Error loading initial data:', error);
            this.showNotification('Failed to load data. Please refresh the page.', 'error');
        }
    }
    
    getCurrentUserId() {
        // Try to get from session storage first
        const sessionUserId = sessionStorage.getItem('userId');
        if (sessionUserId) return sessionUserId;
        
        // Try to get from localStorage as fallback
        const localUserId = localStorage.getItem('userId');
        if (localUserId) return localUserId;
        
        return null;
    }
    
    async loadUserInfo(userId) {
        try {
            const response = await fetch(`/api/users/${userId}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                }
            });
            
            if (response.ok) {
                this.currentUser = await response.json();
                document.getElementById('userName').textContent = `${this.currentUser.firstName} ${this.currentUser.lastName}`;
                this.displayUserProfile();
            }
        } catch (error) {
            console.error('Error loading user info:', error);
        }
    }
    
    displayUserProfile() {
        if (!this.currentUser) return;
        
        // Update profile header
        document.getElementById('profileName').textContent = `${this.currentUser.firstName} ${this.currentUser.lastName}`;
        document.getElementById('profileEmail').textContent = this.currentUser.email;
        document.getElementById('profileId').textContent = this.currentUser.userId;
        
        // Update detail items
        document.getElementById('detailUserId').textContent = this.currentUser.userId;
        document.getElementById('detailFirstName').textContent = this.currentUser.firstName;
        document.getElementById('detailLastName').textContent = this.currentUser.lastName;
        document.getElementById('detailEmail').textContent = this.currentUser.email;
        document.getElementById('detailContactNumber').textContent = this.currentUser.contactNumber;
        document.getElementById('detailUserType').textContent = this.currentUser.userType;
    }
    
    async loadInvoices() {
        try {
            const response = await fetch('/api/invoices', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                }
            });
            
            if (response.ok) {
                this.invoices = await response.json();
                console.table(this.invoices);
                this.displayInvoices();
            } else {
                this.showNotification('Failed to load invoices.', 'error');
            }
        } catch (error) {
            console.error('Error loading invoices:', error);
            this.showNotification('Network error. Please check your connection.', 'error');
        }
    }
    
    displayInvoices() {
        const tbody = document.getElementById('invoiceTableBody');
        const noDataMessage = document.getElementById('noDataMessage');
        
        if (this.invoices.length === 0) {
            tbody.innerHTML = '';
            noDataMessage.style.display = 'block';
            return;
        }
        
        noDataMessage.style.display = 'none';
        
        tbody.innerHTML = this.invoices.map(invoice => `
            <tr>
                <td>${invoice.invoiceNumber || 'N/A'}</td>
                <td>${invoice.title}</td>
                <td><span class="type-badge type-${invoice.invoiceType.toLowerCase()}">${this.formatInvoiceType(invoice.invoiceType)}</span></td>
                <td>$${parseFloat(invoice.amount).toFixed(2)}</td>
                <td>${this.formatDate(invoice.invoiceDate)}</td>
                <td>${invoice.dueDate ? this.formatDate(invoice.dueDate) : 'N/A'}</td>
                <td>
                    <div class="action-buttons">
                        <button class="btn-small btn-edit" onclick="accountantDashboard.openEditModal('${invoice.invoiceId}')" title="Edit Invoice">
                            <i class="fas fa-edit"></i>
                        </button>
                        <button class="btn-small btn-delete" onclick="accountantDashboard.confirmDeleteInvoice('${invoice.invoiceId}')" title="Delete Invoice">
                            <i class="fas fa-trash"></i>
                        </button>
                    </div>
                </td>
            </tr>
        `).join('');
        
        // Add fade-in animation
        document.querySelector('.table-section').classList.add('fade-in');
    }
    
    filterInvoices() {
        const searchTerm = document.getElementById('searchInput').value.toLowerCase();
        const typeFilter = document.getElementById('typeFilter').value;
        const dateFilter = document.getElementById('dateFilter').value;
        
        const filteredInvoices = this.invoices.filter(invoice => {
            const matchesSearch = !searchTerm || 
                invoice.invoiceNumber?.toLowerCase().includes(searchTerm) ||
                invoice.title?.toLowerCase().includes(searchTerm) ||
                invoice.description?.toLowerCase().includes(searchTerm);
            
            const matchesType = !typeFilter || invoice.invoiceType === typeFilter;
            const matchesDate = !dateFilter || invoice.invoiceDate === dateFilter;
            
            return matchesSearch && matchesType && matchesDate;
        });
        
        this.displayFilteredInvoices(filteredInvoices);
    }
    
    displayFilteredInvoices(filteredInvoices) {
        const tbody = document.getElementById('invoiceTableBody');
        const noDataMessage = document.getElementById('noDataMessage');
        
        if (filteredInvoices.length === 0) {
            tbody.innerHTML = `
                <tr>
                    <td colspan="7" class="loading-cell">
                        <div class="loading-spinner">
                            <i class="fas fa-search"></i>
                            <span>No invoices match your search criteria</span>
                        </div>
                    </td>
                </tr>
            `;
            noDataMessage.style.display = 'none';
            return;
        }
        
        noDataMessage.style.display = 'none';
        
        tbody.innerHTML = filteredInvoices.map(invoice => `
            <tr>
                <td>${invoice.invoiceNumber || 'N/A'}</td>
                <td>${invoice.title}</td>
                <td><span class="type-badge type-${invoice.invoiceType.toLowerCase()}">${this.formatInvoiceType(invoice.invoiceType)}</span></td>
                <td>$${parseFloat(invoice.amount).toFixed(2)}</td>
                <td>${this.formatDate(invoice.invoiceDate)}</td>
                <td>${invoice.dueDate ? this.formatDate(invoice.dueDate) : 'N/A'}</td>
                <td>
                    <div class="action-buttons">
                        <button class="btn-small btn-edit" onclick="accountantDashboard.openEditModal('${invoice.invoiceId}')" title="Edit Invoice">
                            <i class="fas fa-edit"></i>
                        </button>
                        <button class="btn-small btn-delete" onclick="accountantDashboard.confirmDeleteInvoice('${invoice.invoiceId}')" title="Delete Invoice">
                            <i class="fas fa-trash"></i>
                        </button>
                    </div>
                </td>
            </tr>
        `).join('');
    }
    
    switchTab(tabName) {
        // Update tab buttons
        document.querySelectorAll('.tab-button').forEach(btn => btn.classList.remove('active'));
        document.getElementById(`${tabName}Tab`).classList.add('active');
        
        // Update tab content
        document.querySelectorAll('.tab-content').forEach(content => content.classList.remove('active'));
        document.getElementById(`${tabName}Content`).classList.add('active');
        
        this.currentTab = tabName;
    }
    
    openCreateModal() {
        this.clearAllErrors();
        this.createModal.classList.add('show');
        document.getElementById('createInvoiceNumber').focus();
    }
    
    closeCreateModal() {
        this.createModal.classList.remove('show');
        document.getElementById('createInvoiceForm').reset();
        this.clearAllErrors();
        this.updateCharCounter('createInvoiceNumberCounter', 'createInvoiceNumber', 20);
        this.updateCharCounter('createTitleCounter', 'createTitle', 100);
        this.updateCharCounter('createDescriptionCounter', 'createDescription', 500);
    }
    
    async handleCreateInvoice(event) {
        event.preventDefault();
        
        if (!this.validateCreateForm()) {
            return;
        }
        
        const saveButton = document.getElementById('saveInvoice');
        this.setLoadingState(saveButton, true);
        
        try {
            const formData = new FormData(event.target);
            const invoiceData = {
                invoiceNumber: formData.get('invoiceNumber').trim(),
                invoiceDate: formData.get('invoiceDate'),
                amount: parseFloat(formData.get('amount')),
                invoiceType: formData.get('invoiceType'),
                title: formData.get('title').trim(),
                description: formData.get('description')?.trim() || null,
                dueDate: formData.get('dueDate') || null,
                taxAmount: formData.get('taxAmount') ? parseFloat(formData.get('taxAmount')) : null,
                discountAmount: formData.get('discountAmount') ? parseFloat(formData.get('discountAmount')) : null
            };
            
            const response = await fetch('/api/invoices', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(invoiceData)
            });
            
            const result = await response.json();
            
            if (response.ok) {
                this.showNotification('Invoice created successfully!', 'success');
                this.closeCreateModal();
                await this.loadInvoices();
            } else {
                this.showNotification(result.message || 'Failed to create invoice', 'error');
            }
        } catch (error) {
            console.error('Create invoice error:', error);
            this.showNotification('Network error. Please check your connection.', 'error');
        } finally {
            this.setLoadingState(saveButton, false);
        }
    }
    
    validateCreateForm() {
        let isValid = true;
        
        if (!this.validateInvoiceNumber('create')) isValid = false;
        if (!this.validateInvoiceDate('create')) isValid = false;
        if (!this.validateAmount('create')) isValid = false;
        if (!this.validateInvoiceType('create')) isValid = false;
        if (!this.validateTitle('create')) isValid = false;
        
        return isValid;
    }
    
    validateInvoiceNumber(formType) {
        const invoiceNumber = document.getElementById(`${formType}InvoiceNumber`).value.trim();
        const invoiceNumberRegex = /^.{5,20}$/;
        
        if (!invoiceNumber) {
            this.showFieldError(`${formType}InvoiceNumberError`, 'Invoice number is required');
            return false;
        }
        
        if (!invoiceNumberRegex.test(invoiceNumber)) {
            this.showFieldError(`${formType}InvoiceNumberError`, 'Invoice number must be between 5 and 20 characters');
            return false;
        }
        
        this.clearError(`${formType}InvoiceNumberError`);
        return true;
    }
    
    validateInvoiceDate(formType) {
        const invoiceDate = document.getElementById(`${formType}InvoiceDate`).value;
        
        if (!invoiceDate) {
            this.showFieldError(`${formType}InvoiceDateError`, 'Invoice date is required');
            return false;
        }
        
        this.clearError(`${formType}InvoiceDateError`);
        return true;
    }
    
    validateAmount(formType) {
        const amount = document.getElementById(`${formType}Amount`).value;
        
        if (!amount) {
            this.showFieldError(`${formType}AmountError`, 'Amount is required');
            return false;
        }
        
        if (parseFloat(amount) <= 0) {
            this.showFieldError(`${formType}AmountError`, 'Amount must be greater than 0');
            return false;
        }
        
        this.clearError(`${formType}AmountError`);
        return true;
    }
    
    validateInvoiceType(formType) {
        const invoiceType = document.getElementById(`${formType}InvoiceType`).value;
        
        if (!invoiceType) {
            this.showFieldError(`${formType}InvoiceTypeError`, 'Please select an invoice type');
            return false;
        }
        
        this.clearError(`${formType}InvoiceTypeError`);
        return true;
    }
    
    validateTitle(formType) {
        const title = document.getElementById(`${formType}Title`).value.trim();
        const titleRegex = /^.{5,100}$/;
        
        if (!title) {
            this.showFieldError(`${formType}TitleError`, 'Title is required');
            return false;
        }
        
        if (!titleRegex.test(title)) {
            this.showFieldError(`${formType}TitleError`, 'Title must be between 5 and 100 characters');
            return false;
        }
        
        this.clearError(`${formType}TitleError`);
        return true;
    }
    
    openEditModal(invoiceId) {
        const invoice = this.invoices.find(i => i.invoiceId == invoiceId);
        if (!invoice) {
            this.showNotification('Invoice not found.', 'error');
            return;
        }
        
        // Populate form with invoice data
        document.getElementById('editInvoiceId').value = invoice.invoiceId;
        document.getElementById('editInvoiceNumber').value = invoice.invoiceNumber || '';
        document.getElementById('editInvoiceDate').value = invoice.invoiceDate || '';
        document.getElementById('editAmount').value = invoice.amount || '';
        document.getElementById('editInvoiceType').value = invoice.invoiceType || '';
        document.getElementById('editDueDate').value = invoice.dueDate || '';
        document.getElementById('editTaxAmount').value = invoice.taxAmount || '';
        document.getElementById('editDiscountAmount').value = invoice.discountAmount || '';
        document.getElementById('editTitle').value = invoice.title || '';
        document.getElementById('editDescription').value = invoice.description || '';
        
        this.clearAllErrors();
        this.updateCharCounter('editInvoiceNumberCounter', 'editInvoiceNumber', 20);
        this.updateCharCounter('editTitleCounter', 'editTitle', 100);
        this.updateCharCounter('editDescriptionCounter', 'editDescription', 500);
        this.editModal.classList.add('show');
    }
    
    closeEditModal() {
        this.editModal.classList.remove('show');
        this.clearAllErrors();
    }
    
    async handleUpdateInvoice(event) {
        event.preventDefault();
        
        if (!this.validateEditForm()) {
            return;
        }
        
        const saveButton = document.getElementById('updateInvoice');
        this.setLoadingState(saveButton, true);
        
        try {
            const formData = new FormData(event.target);
            const invoiceId = document.getElementById('editInvoiceId').value;
            
            const updateData = {
                invoiceNumber: formData.get('invoiceNumber').trim(),
                invoiceDate: formData.get('invoiceDate'),
                amount: parseFloat(formData.get('amount')),
                invoiceType: formData.get('invoiceType'),
                title: formData.get('title').trim(),
                description: formData.get('description')?.trim() || null,
                dueDate: formData.get('dueDate') || null,
                taxAmount: formData.get('taxAmount') ? parseFloat(formData.get('taxAmount')) : null,
                discountAmount: formData.get('discountAmount') ? parseFloat(formData.get('discountAmount')) : null
            };
            
            const response = await fetch(`/api/invoices/${invoiceId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(updateData)
            });
            
            const result = await response.json();
            
            if (response.ok) {
                this.showNotification('Invoice updated successfully!', 'success');
                this.closeEditModal();
                await this.loadInvoices();
            } else {
                this.showNotification(result.message || 'Failed to update invoice', 'error');
            }
        } catch (error) {
            console.error('Update invoice error:', error);
            this.showNotification('Network error. Please check your connection.', 'error');
        } finally {
            this.setLoadingState(saveButton, false);
        }
    }
    
    validateEditForm() {
        let isValid = true;
        
        if (!this.validateInvoiceNumber('edit')) isValid = false;
        if (!this.validateInvoiceDate('edit')) isValid = false;
        if (!this.validateAmount('edit')) isValid = false;
        if (!this.validateInvoiceType('edit')) isValid = false;
        if (!this.validateTitle('edit')) isValid = false;
        
        return isValid;
    }
    
    confirmDeleteInvoice(invoiceId) {
        this.currentInvoiceId = invoiceId;
        this.deleteModal.classList.add('show');
    }
    
    closeDeleteModal() {
        this.deleteModal.classList.remove('show');
        this.currentInvoiceId = null;
    }
    
    async handleDeleteInvoice() {
        if (!this.currentInvoiceId) return;
        
        const deleteButton = document.getElementById('confirmDelete');
        this.setLoadingState(deleteButton, true);
        
        try {
            const response = await fetch(`/api/invoices/${this.currentInvoiceId}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                }
            });
            
            if (response.ok) {
                this.showNotification('Invoice deleted successfully!', 'success');
                this.closeDeleteModal();
                await this.loadInvoices();
            } else {
                const result = await response.json();
                this.showNotification(result.message || 'Failed to delete invoice', 'error');
            }
        } catch (error) {
            console.error('Delete invoice error:', error);
            this.showNotification('Network error. Please check your connection.', 'error');
        } finally {
            this.setLoadingState(deleteButton, false);
        }
    }
    
    updateCharCounter(counterId, textareaId, maxLength) {
        const textarea = document.getElementById(textareaId);
        const counter = document.getElementById(counterId);
        const length = textarea.value.length;
        
        counter.textContent = length;
        
        if (length > maxLength * 0.9) {
            counter.className = 'char-counter error';
        } else if (length > maxLength * 0.8) {
            counter.className = 'char-counter warning';
        } else {
            counter.className = 'char-counter';
        }
    }
    
    exportToPDF() {
        if (this.invoices.length === 0) {
            this.showNotification('No invoices to export.', 'warning');
            return;
        }
        
        try {
            const { jsPDF } = window.jspdf;
            const doc = new jsPDF();
            
            // Add title
            doc.setFontSize(20);
            doc.text('Invoice Management Report', 20, 20);
            
            // Add user info
            doc.setFontSize(12);
            doc.text(`Generated for: ${this.currentUser.firstName} ${this.currentUser.lastName}`, 20, 35);
            doc.text(`Generated on: ${new Date().toLocaleDateString()}`, 20, 45);
            
            // Prepare table data
            const tableData = this.invoices.map(invoice => [
                invoice.invoiceNumber || 'N/A',
                invoice.title,
                this.formatInvoiceType(invoice.invoiceType),
                `$${parseFloat(invoice.amount).toFixed(2)}`,
                this.formatDate(invoice.invoiceDate),
                invoice.dueDate ? this.formatDate(invoice.dueDate) : 'N/A'
            ]);
            
            // Add table
            doc.autoTable({
                head: [['Invoice #', 'Title', 'Type', 'Amount', 'Date', 'Due Date']],
                body: tableData,
                startY: 60,
                styles: {
                    fontSize: 8,
                    cellPadding: 3
                },
                headStyles: {
                    fillColor: [234, 179, 8],
                    textColor: [0, 0, 0]
                }
            });
            
            // Save the PDF
            doc.save(`invoices-${this.currentUser.userId}-${new Date().toISOString().split('T')[0]}.pdf`);
            
            this.showNotification('PDF exported successfully!', 'success');
        } catch (error) {
            console.error('PDF export error:', error);
            this.showNotification('Failed to export PDF. Please try again.', 'error');
        }
    }
    
    formatInvoiceType(type) {
        return type.replace(/_/g, ' ').replace(/\b\w/g, l => l.toUpperCase());
    }
    
    formatDate(dateString) {
        if (!dateString) return 'N/A';
        const date = new Date(dateString);
        return date.toLocaleDateString();
    }
    
    showFieldError(errorElementId, message) {
        const errorElement = document.getElementById(errorElementId);
        errorElement.textContent = message;
        errorElement.classList.add('show');
        
        const inputElement = errorElement.previousElementSibling;
        if (inputElement.classList.contains('form-input') || inputElement.classList.contains('form-select') || inputElement.classList.contains('form-textarea')) {
            inputElement.classList.add('error');
        }
    }
    
    clearError(errorElementId) {
        const errorElement = document.getElementById(errorElementId);
        errorElement.classList.remove('show');
        
        const inputElement = errorElement.previousElementSibling;
        if (inputElement.classList.contains('form-input') || inputElement.classList.contains('form-select') || inputElement.classList.contains('form-textarea')) {
            inputElement.classList.remove('error');
        }
    }
    
    clearAllErrors() {
        const errorElements = document.querySelectorAll('.error-message');
        errorElements.forEach(element => {
            element.classList.remove('show');
        });
        
        const inputElements = document.querySelectorAll('.form-input, .form-select, .form-textarea');
        inputElements.forEach(element => {
            element.classList.remove('error');
        });
    }
    
    setLoadingState(button, isLoading) {
        button.disabled = isLoading;
        
        if (isLoading) {
            button.classList.add('loading');
            const buttonText = button.querySelector('.button-text');
            if (buttonText) {
                buttonText.style.opacity = '0';
            }
            const spinner = button.querySelector('.loading-spinner');
            if (spinner) {
                spinner.style.opacity = '1';
            }
        } else {
            button.classList.remove('loading');
            const buttonText = button.querySelector('.button-text');
            if (buttonText) {
                buttonText.style.opacity = '1';
            }
            const spinner = button.querySelector('.loading-spinner');
            if (spinner) {
                spinner.style.opacity = '0';
            }
        }
    }
    
    showNotification(message, type = 'info') {
        const notification = document.createElement('div');
        notification.className = `notification ${type} slide-in`;
        
        const icon = this.getNotificationIcon(type);
        notification.innerHTML = `
            <i class="${icon}"></i>
            <span>${message}</span>
        `;
        
        this.notificationContainer.appendChild(notification);
        
        // Auto remove after 5 seconds
        setTimeout(() => {
            notification.classList.add('slide-out');
            setTimeout(() => {
                if (notification.parentNode) {
                    notification.parentNode.removeChild(notification);
                }
            }, 300);
        }, 5000);
    }
    
    getNotificationIcon(type) {
        const icons = {
            success: 'fas fa-check-circle',
            error: 'fas fa-exclamation-circle',
            warning: 'fas fa-exclamation-triangle',
            info: 'fas fa-info-circle'
        };
        return icons[type] || icons.info;
    }
}

// Initialize accountant dashboard when DOM is loaded
let accountantDashboard;
document.addEventListener('DOMContentLoaded', () => {
    accountantDashboard = new AccountantDashboard();
});
