<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Accountant Dashboard - JCB Management System</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="/css/accountant-dashboard.css">
</head>
<body>
    <div class="accountant-container">
        <!-- Header -->
        <header class="accountant-header">
            <div class="header-content">
                <div class="logo-section">
                    <i class="fas fa-truck-monster"></i>
                    <h1 class="system-title">JCB Management System</h1>
                </div>
                <div class="user-info">
                    <div class="user-details">
                        <span class="user-name" id="userName">Loading...</span>
                        <span class="user-role">Accountant Dashboard</span>
                    </div>
                    <div class="user-avatar">
                        <i class="fas fa-user"></i>
                    </div>
                </div>
            </div>
        </header>

        <!-- Main Content -->
        <main class="accountant-main">
            <div class="accountant-content">
                <!-- Navigation Tabs -->
                <section class="navigation-tabs">
                    <div class="tab-buttons">
                        <button class="tab-button active" id="profileTab">
                            <i class="fas fa-user"></i>
                            <span>My Profile</span>
                        </button>
                        <button class="tab-button" id="invoicesTab">
                            <i class="fas fa-file-invoice"></i>
                            <span>Invoice Management</span>
                        </button>
                    </div>
                </section>

                <!-- Profile Tab Content -->
                <section class="tab-content active" id="profileContent">
                    <div class="profile-section">
                        <h2 class="section-title">
                            <i class="fas fa-user"></i>
                            My Profile
                        </h2>
                        
                        <div class="profile-card">
                            <div class="profile-header">
                                <div class="profile-avatar">
                                    <i class="fas fa-user-circle"></i>
                                </div>
                                <div class="profile-info">
                                    <h3 class="profile-name" id="profileName">Loading...</h3>
                                    <p class="profile-email" id="profileEmail">Loading...</p>
                                    <span class="profile-id" id="profileId">Loading...</span>
                                </div>
                            </div>
                            
                            <div class="profile-details">
                                <div class="detail-grid">
                                    <div class="detail-item">
                                        <div class="detail-icon">
                                            <i class="fas fa-id-card"></i>
                                        </div>
                                        <div class="detail-content">
                                            <h4 class="detail-label">User ID</h4>
                                            <p class="detail-value" id="detailUserId">Loading...</p>
                                        </div>
                                    </div>

                                    <div class="detail-item">
                                        <div class="detail-icon">
                                            <i class="fas fa-user"></i>
                                        </div>
                                        <div class="detail-content">
                                            <h4 class="detail-label">First Name</h4>
                                            <p class="detail-value" id="detailFirstName">Loading...</p>
                                        </div>
                                    </div>

                                    <div class="detail-item">
                                        <div class="detail-icon">
                                            <i class="fas fa-user"></i>
                                        </div>
                                        <div class="detail-content">
                                            <h4 class="detail-label">Last Name</h4>
                                            <p class="detail-value" id="detailLastName">Loading...</p>
                                        </div>
                                    </div>

                                    <div class="detail-item">
                                        <div class="detail-icon">
                                            <i class="fas fa-envelope"></i>
                                        </div>
                                        <div class="detail-content">
                                            <h4 class="detail-label">Email Address</h4>
                                            <p class="detail-value" id="detailEmail">Loading...</p>
                                        </div>
                                    </div>

                                    <div class="detail-item">
                                        <div class="detail-icon">
                                            <i class="fas fa-phone"></i>
                                        </div>
                                        <div class="detail-content">
                                            <h4 class="detail-label">Contact Number</h4>
                                            <p class="detail-value" id="detailContactNumber">Loading...</p>
                                        </div>
                                    </div>

                                    <div class="detail-item">
                                        <div class="detail-icon">
                                            <i class="fas fa-user-tag"></i>
                                        </div>
                                        <div class="detail-content">
                                            <h4 class="detail-label">User Type</h4>
                                            <p class="detail-value" id="detailUserType">Loading...</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>

                <!-- Invoices Tab Content -->
                <section class="tab-content" id="invoicesContent">
                    <!-- Invoice Management Header -->
                    <div class="invoice-header">
                        <h2 class="section-title">
                            <i class="fas fa-file-invoice"></i>
                            Invoice Management
                        </h2>
                        <div class="invoice-actions">
                            <button class="action-button primary-button" id="addInvoiceBtn">
                                <i class="fas fa-plus"></i>
                                <span>New Invoice</span>
                            </button>
                            <button class="action-button secondary-button" id="exportPdfBtn">
                                <i class="fas fa-file-pdf"></i>
                                <span>Export to PDF</span>
                            </button>
                        </div>
                    </div>

                    <!-- Search and Filter Section -->
                    <div class="search-section">
                        <div class="search-container">
                            <div class="search-box">
                                <i class="fas fa-search"></i>
                                <input type="text" id="searchInput" placeholder="Search by invoice number, title, or description..." class="search-input">
                            </div>
                            <div class="filter-container">
                                <select id="typeFilter" class="filter-select">
                                    <option value="">All Types</option>
                                    <option value="SERVICE_INVOICE">Service Invoice</option>
                                    <option value="EQUIPMENT_RENTAL">Equipment Rental</option>
                                    <option value="MAINTENANCE_FEE">Maintenance Fee</option>
                                    <option value="PENALTY_FEE">Penalty Fee</option>
                                    <option value="REFUND">Refund</option>
                                    <option value="CREDIT_NOTE">Credit Note</option>
                                    <option value="DEBIT_NOTE">Debit Note</option>
                                    <option value="MONTHLY_SUBSCRIPTION">Monthly Subscription</option>
                                    <option value="ONE_TIME_PAYMENT">One Time Payment</option>
                                    <option value="DEPOSIT">Deposit</option>
                                </select>
                                <input type="date" id="dateFilter" class="filter-select" placeholder="Filter by date">
                            </div>
                        </div>
                    </div>

                    <!-- Invoices Table -->
                    <div class="table-section">
                        <div class="table-container">
                            <table class="invoice-table" id="invoiceTable">
                                <thead>
                                    <tr>
                                        <th>Invoice #</th>
                                        <th>Title</th>
                                        <th>Type</th>
                                        <th>Amount</th>
                                        <th>Date</th>
                                        <th>Due Date</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody id="invoiceTableBody">
                                    <tr class="loading-row">
                                        <td colspan="7" class="loading-cell">
                                            <div class="loading-spinner">
                                                <i class="fas fa-spinner fa-spin"></i>
                                                <span>Loading invoices...</span>
                                            </div>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        
                        <div class="no-data-message" id="noDataMessage" style="display: none;">
                            <i class="fas fa-file-invoice"></i>
                            <h3>No Invoices Found</h3>
                            <p>You haven't created any invoices yet. Create your first invoice to get started.</p>
                            <button class="action-button primary-button" id="createFirstInvoiceBtn">
                                <i class="fas fa-plus"></i>
                                <span>Create First Invoice</span>
                            </button>
                        </div>
                    </div>
                </section>
            </div>
        </main>

        <!-- Create Invoice Modal -->
        <div class="modal-overlay" id="createModal">
            <div class="modal-container">
                <div class="modal-header">
                    <h3 class="modal-title">
                        <i class="fas fa-plus"></i>
                        New Invoice
                    </h3>
                    <button class="modal-close" id="closeCreateModal">
                        <i class="fas fa-times"></i>
                    </button>
                </div>
                
                <form id="createInvoiceForm" class="modal-form">
                    <div class="form-grid">
                        <div class="form-group">
                            <label for="createInvoiceNumber" class="form-label">
                                <i class="fas fa-hashtag"></i>
                                Invoice Number *
                            </label>
                            <input type="text" id="createInvoiceNumber" name="invoiceNumber" class="form-input" 
                                   placeholder="Enter invoice number" required>
                            <div class="error-message" id="createInvoiceNumberError"></div>
                            <div class="char-counter">
                                <span id="createInvoiceNumberCounter">0</span>/20 characters
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label for="createInvoiceDate" class="form-label">
                                <i class="fas fa-calendar"></i>
                                Invoice Date *
                            </label>
                            <input type="date" id="createInvoiceDate" name="invoiceDate" class="form-input" required>
                            <div class="error-message" id="createInvoiceDateError"></div>
                        </div>
                        
                        <div class="form-group">
                            <label for="createAmount" class="form-label">
                                <i class="fas fa-dollar-sign"></i>
                                Amount *
                            </label>
                            <input type="number" id="createAmount" name="amount" class="form-input" 
                                   placeholder="0.00" step="0.01" min="0.01" required>
                            <div class="error-message" id="createAmountError"></div>
                        </div>
                        
                        <div class="form-group">
                            <label for="createInvoiceType" class="form-label">
                                <i class="fas fa-tag"></i>
                                Invoice Type *
                            </label>
                            <select id="createInvoiceType" name="invoiceType" class="form-select" required>
                                <option value="">Select invoice type...</option>
                                <option value="SERVICE_INVOICE">Service Invoice</option>
                                <option value="EQUIPMENT_RENTAL">Equipment Rental</option>
                                <option value="MAINTENANCE_FEE">Maintenance Fee</option>
                                <option value="PENALTY_FEE">Penalty Fee</option>
                                <option value="REFUND">Refund</option>
                                <option value="CREDIT_NOTE">Credit Note</option>
                                <option value="DEBIT_NOTE">Debit Note</option>
                                <option value="MONTHLY_SUBSCRIPTION">Monthly Subscription</option>
                                <option value="ONE_TIME_PAYMENT">One Time Payment</option>
                                <option value="DEPOSIT">Deposit</option>
                            </select>
                            <div class="error-message" id="createInvoiceTypeError"></div>
                        </div>
                        
                        <div class="form-group">
                            <label for="createDueDate" class="form-label">
                                <i class="fas fa-calendar-check"></i>
                                Due Date
                            </label>
                            <input type="date" id="createDueDate" name="dueDate" class="form-input">
                            <div class="error-message" id="createDueDateError"></div>
                        </div>
                        
                        <div class="form-group">
                            <label for="createTaxAmount" class="form-label">
                                <i class="fas fa-percentage"></i>
                                Tax Amount
                            </label>
                            <input type="number" id="createTaxAmount" name="taxAmount" class="form-input" 
                                   placeholder="0.00" step="0.01" min="0">
                            <div class="error-message" id="createTaxAmountError"></div>
                        </div>
                        
                        <div class="form-group">
                            <label for="createDiscountAmount" class="form-label">
                                <i class="fas fa-tag"></i>
                                Discount Amount
                            </label>
                            <input type="number" id="createDiscountAmount" name="discountAmount" class="form-input" 
                                   placeholder="0.00" step="0.01" min="0">
                            <div class="error-message" id="createDiscountAmountError"></div>
                        </div>
                        
                        <div class="form-group full-width">
                            <label for="createTitle" class="form-label">
                                <i class="fas fa-heading"></i>
                                Title *
                            </label>
                            <input type="text" id="createTitle" name="title" class="form-input" 
                                   placeholder="Enter invoice title" required>
                            <div class="error-message" id="createTitleError"></div>
                            <div class="char-counter">
                                <span id="createTitleCounter">0</span>/100 characters
                            </div>
                        </div>
                        
                        <div class="form-group full-width">
                            <label for="createDescription" class="form-label">
                                <i class="fas fa-comment"></i>
                                Description
                            </label>
                            <textarea id="createDescription" name="description" class="form-textarea" 
                                      placeholder="Enter invoice description..." rows="4"></textarea>
                            <div class="error-message" id="createDescriptionError"></div>
                            <div class="char-counter">
                                <span id="createDescriptionCounter">0</span>/500 characters
                            </div>
                        </div>
                    </div>
                    
                    <div class="modal-actions">
                        <button type="button" class="modal-button cancel-button" id="cancelCreate">
                            <i class="fas fa-times"></i>
                            Cancel
                        </button>
                        <button type="submit" class="modal-button save-button" id="saveInvoice">
                            <i class="fas fa-save"></i>
                            <span class="button-text">Create Invoice</span>
                            <div class="loading-spinner" id="createLoadingSpinner">
                                <i class="fas fa-spinner fa-spin"></i>
                            </div>
                        </button>
                    </div>
                </form>
            </div>
        </div>

        <!-- Edit Invoice Modal -->
        <div class="modal-overlay" id="editModal">
            <div class="modal-container">
                <div class="modal-header">
                    <h3 class="modal-title">
                        <i class="fas fa-edit"></i>
                        Edit Invoice
                    </h3>
                    <button class="modal-close" id="closeEditModal">
                        <i class="fas fa-times"></i>
                    </button>
                </div>
                
                <form id="editInvoiceForm" class="modal-form">
                    <input type="hidden" id="editInvoiceId" name="invoiceId">
                    
                    <div class="form-grid">
                        <div class="form-group">
                            <label for="editInvoiceNumber" class="form-label">
                                <i class="fas fa-hashtag"></i>
                                Invoice Number *
                            </label>
                            <input type="text" id="editInvoiceNumber" name="invoiceNumber" class="form-input" required>
                            <div class="error-message" id="editInvoiceNumberError"></div>
                            <div class="char-counter">
                                <span id="editInvoiceNumberCounter">0</span>/20 characters
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label for="editInvoiceDate" class="form-label">
                                <i class="fas fa-calendar"></i>
                                Invoice Date *
                            </label>
                            <input type="date" id="editInvoiceDate" name="invoiceDate" class="form-input" required>
                            <div class="error-message" id="editInvoiceDateError"></div>
                        </div>
                        
                        <div class="form-group">
                            <label for="editAmount" class="form-label">
                                <i class="fas fa-dollar-sign"></i>
                                Amount *
                            </label>
                            <input type="number" id="editAmount" name="amount" class="form-input" 
                                   step="0.01" min="0.01" required>
                            <div class="error-message" id="editAmountError"></div>
                        </div>
                        
                        <div class="form-group">
                            <label for="editInvoiceType" class="form-label">
                                <i class="fas fa-tag"></i>
                                Invoice Type *
                            </label>
                            <select id="editInvoiceType" name="invoiceType" class="form-select" required>
                                <option value="">Select invoice type...</option>
                                <option value="SERVICE_INVOICE">Service Invoice</option>
                                <option value="EQUIPMENT_RENTAL">Equipment Rental</option>
                                <option value="MAINTENANCE_FEE">Maintenance Fee</option>
                                <option value="PENALTY_FEE">Penalty Fee</option>
                                <option value="REFUND">Refund</option>
                                <option value="CREDIT_NOTE">Credit Note</option>
                                <option value="DEBIT_NOTE">Debit Note</option>
                                <option value="MONTHLY_SUBSCRIPTION">Monthly Subscription</option>
                                <option value="ONE_TIME_PAYMENT">One Time Payment</option>
                                <option value="DEPOSIT">Deposit</option>
                            </select>
                            <div class="error-message" id="editInvoiceTypeError"></div>
                        </div>
                        
                        <div class="form-group">
                            <label for="editDueDate" class="form-label">
                                <i class="fas fa-calendar-check"></i>
                                Due Date
                            </label>
                            <input type="date" id="editDueDate" name="dueDate" class="form-input">
                            <div class="error-message" id="editDueDateError"></div>
                        </div>
                        
                        <div class="form-group">
                            <label for="editTaxAmount" class="form-label">
                                <i class="fas fa-percentage"></i>
                                Tax Amount
                            </label>
                            <input type="number" id="editTaxAmount" name="taxAmount" class="form-input" 
                                   step="0.01" min="0">
                            <div class="error-message" id="editTaxAmountError"></div>
                        </div>
                        
                        <div class="form-group">
                            <label for="editDiscountAmount" class="form-label">
                                <i class="fas fa-tag"></i>
                                Discount Amount
                            </label>
                            <input type="number" id="editDiscountAmount" name="discountAmount" class="form-input" 
                                   step="0.01" min="0">
                            <div class="error-message" id="editDiscountAmountError"></div>
                        </div>
                        
                        <div class="form-group full-width">
                            <label for="editTitle" class="form-label">
                                <i class="fas fa-heading"></i>
                                Title *
                            </label>
                            <input type="text" id="editTitle" name="title" class="form-input" required>
                            <div class="error-message" id="editTitleError"></div>
                            <div class="char-counter">
                                <span id="editTitleCounter">0</span>/100 characters
                            </div>
                        </div>
                        
                        <div class="form-group full-width">
                            <label for="editDescription" class="form-label">
                                <i class="fas fa-comment"></i>
                                Description
                            </label>
                            <textarea id="editDescription" name="description" class="form-textarea" rows="4"></textarea>
                            <div class="error-message" id="editDescriptionError"></div>
                            <div class="char-counter">
                                <span id="editDescriptionCounter">0</span>/500 characters
                            </div>
                        </div>
                    </div>
                    
                    <div class="modal-actions">
                        <button type="button" class="modal-button cancel-button" id="cancelEdit">
                            <i class="fas fa-times"></i>
                            Cancel
                        </button>
                        <button type="submit" class="modal-button save-button" id="updateInvoice">
                            <i class="fas fa-save"></i>
                            <span class="button-text">Update Invoice</span>
                            <div class="loading-spinner" id="editLoadingSpinner">
                                <i class="fas fa-spinner fa-spin"></i>
                            </div>
                        </button>
                    </div>
                </form>
            </div>
        </div>

        <!-- Delete Confirmation Modal -->
        <div class="modal-overlay" id="deleteModal">
            <div class="modal-container small-modal">
                <div class="modal-header">
                    <h3 class="modal-title">
                        <i class="fas fa-exclamation-triangle"></i>
                        Delete Invoice
                    </h3>
                </div>
                
                <div class="modal-body">
                    <div class="confirm-icon">
                        <i class="fas fa-trash"></i>
                    </div>
                    <p class="confirm-message">Are you sure you want to delete this invoice? This action cannot be undone.</p>
                </div>
                
                <div class="modal-actions">
                    <button type="button" class="modal-button cancel-button" id="cancelDelete">
                        <i class="fas fa-times"></i>
                        Cancel
                    </button>
                    <button type="button" class="modal-button danger-button" id="confirmDelete">
                        <i class="fas fa-trash"></i>
                        <span class="button-text">Delete</span>
                        <div class="loading-spinner" id="deleteLoadingSpinner">
                            <i class="fas fa-spinner fa-spin"></i>
                        </div>
                    </button>
                </div>
            </div>
        </div>

        <!-- Notification Container -->
        <div class="notification-container" id="notificationContainer"></div>
    </div>
    
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf-autotable/3.5.31/jspdf.plugin.autotable.min.js"></script>
    <script src="/js/accountant-dashboard.js"></script>
</body>
</html>
