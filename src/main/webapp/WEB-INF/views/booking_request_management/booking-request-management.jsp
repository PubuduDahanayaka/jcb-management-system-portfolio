<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Booking Requests - JCB Management System</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="/css/booking-management.css">
</head>
<body>
    <div class="booking-container">
        <!-- Header -->
        <header class="booking-header">
            <div class="header-content">
                <div class="logo-section">
                    <i class="fas fa-truck-monster"></i>
                    <h1 class="system-title">JCB Management System</h1>
                </div>
                <div class="user-info">
                    <div class="user-details">
                        <span class="user-name" id="userName">Loading...</span>
                        <span class="user-role">Booking Management</span>
                    </div>
                    <div class="user-avatar">
                        <i class="fas fa-user"></i>
                    </div>
                </div>
            </div>
        </header>

        <!-- Main Content -->
        <main class="booking-main">
            <div class="booking-content">
                <!-- Page Header -->
                <section class="page-header">
                    <div class="page-title-section">
                        <h2 class="page-title">
                            <i class="fas fa-calendar-check"></i>
                            My Booking Requests
                        </h2>
                        <p class="page-subtitle">Manage your machine booking requests</p>
                    </div>
                    <div class="page-actions">
                        <button class="action-button primary-button" id="addBookingBtn">
                            <i class="fas fa-plus"></i>
                            <span>New Booking Request</span>
                        </button>
                        <button class="action-button secondary-button" id="exportPdfBtn">
                            <i class="fas fa-file-pdf"></i>
                            <span>Export to PDF</span>
                        </button>
                    </div>
                </section>

                <!-- Search and Filter Section -->
                <section class="search-section">
                    <div class="search-container">
                        <div class="search-box">
                            <i class="fas fa-search"></i>
                            <input type="text" id="searchInput" placeholder="Search by machine, status, or message..." class="search-input">
                        </div>
                        <div class="filter-container">
                            <select id="statusFilter" class="filter-select">
                                <option value="">All Status</option>
                                <option value="PENDING">Pending</option>
                                <option value="APPROVED">Approved</option>
                                <option value="REJECTED">Rejected</option>
                                <option value="CANCELLED">Cancelled</option>
                                <option value="COMPLETED">Completed</option>
                            </select>
                            <select id="machineFilter" class="filter-select">
                                <option value="">All Machines</option>
                            </select>
                        </div>
                    </div>
                </section>

                <!-- Booking Requests Table -->
                <section class="table-section">
                    <div class="table-container">
                        <table class="booking-table" id="bookingTable">
                            <thead>
                                <tr>
                                    <th>Request ID</th>
                                    <th>Machine</th>
                                    <th>Requested Date</th>
                                    <th>Status</th>
                                    <th>Message</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody id="bookingTableBody">
                                <tr class="loading-row">
                                    <td colspan="6" class="loading-cell">
                                        <div class="loading-spinner">
                                            <i class="fas fa-spinner fa-spin"></i>
                                            <span>Loading booking requests...</span>
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    
                    <div class="no-data-message" id="noDataMessage" style="display: none;">
                        <i class="fas fa-calendar-times"></i>
                        <h3>No Booking Requests Found</h3>
                        <p>You haven't made any booking requests yet. Create your first booking request to get started.</p>
                        <button class="action-button primary-button" id="createFirstBookingBtn">
                            <i class="fas fa-plus"></i>
                            <span>Create First Booking</span>
                        </button>
                    </div>
                </section>
            </div>
        </main>

        <!-- Create Booking Request Modal -->
        <div class="modal-overlay" id="createModal">
            <div class="modal-container">
                <div class="modal-header">
                    <h3 class="modal-title">
                        <i class="fas fa-plus"></i>
                        New Booking Request
                    </h3>
                    <button class="modal-close" id="closeCreateModal">
                        <i class="fas fa-times"></i>
                    </button>
                </div>
                
                <form id="createBookingForm" class="modal-form">
                    <div class="form-grid">
                        <div class="form-group">
                            <label for="createMachineId" class="form-label">
                                <i class="fas fa-truck-monster"></i>
                                Select Machine *
                            </label>
                            <select id="createMachineId" name="machineId" class="form-select" required>
                                <option value="">Choose a machine...</option>
                            </select>
                            <div class="error-message" id="createMachineIdError"></div>
                        </div>
                        
                        <div class="form-group">
                            <label for="createRequestedDate" class="form-label">
                                <i class="fas fa-calendar"></i>
                                Requested Date *
                            </label>
                            <input type="datetime-local" id="createRequestedDate" name="requestedDate" class="form-input" required>
                            <div class="error-message" id="createRequestedDateError"></div>
                        </div>
                        
                        <div class="form-group full-width">
                            <label for="createAdditionalMessage" class="form-label">
                                <i class="fas fa-comment"></i>
                                Additional Message
                            </label>
                            <textarea id="createAdditionalMessage" name="additionalMessage" class="form-textarea" 
                                      placeholder="Any additional information about your booking request..." rows="4"></textarea>
                            <div class="error-message" id="createAdditionalMessageError"></div>
                            <div class="char-counter">
                                <span id="createMessageCounter">0</span>/500 characters
                            </div>
                        </div>
                    </div>
                    
                    <div class="modal-actions">
                        <button type="button" class="modal-button cancel-button" id="cancelCreate">
                            <i class="fas fa-times"></i>
                            Cancel
                        </button>
                        <button type="submit" class="modal-button save-button" id="saveBooking">
                            <i class="fas fa-save"></i>
                            <span class="button-text">Create Booking</span>
                            <div class="loading-spinner" id="createLoadingSpinner">
                                <i class="fas fa-spinner fa-spin"></i>
                            </div>
                        </button>
                    </div>
                </form>
            </div>
        </div>

        <!-- Edit Booking Request Modal -->
        <div class="modal-overlay" id="editModal">
            <div class="modal-container">
                <div class="modal-header">
                    <h3 class="modal-title">
                        <i class="fas fa-edit"></i>
                        Edit Booking Request
                    </h3>
                    <button class="modal-close" id="closeEditModal">
                        <i class="fas fa-times"></i>
                    </button>
                </div>
                
                <form id="editBookingForm" class="modal-form">
                    <input type="hidden" id="editBookingId" name="bookingId">
                    
                    <div class="form-grid">
                        <div class="form-group">
                            <label for="editMachineId" class="form-label">
                                <i class="fas fa-truck-monster"></i>
                                Machine
                            </label>
                            <input type="text" id="editMachineId" class="form-input" readonly>
                        </div>
                        
                        <div class="form-group">
                            <label for="editRequestedDate" class="form-label">
                                <i class="fas fa-calendar"></i>
                                Requested Date *
                            </label>
                            <input type="datetime-local" id="editRequestedDate" name="requestedDate" class="form-input" required>
                            <div class="error-message" id="editRequestedDateError"></div>
                        </div>
                        
                        <div class="form-group">
                            <label for="editStatus" class="form-label">
                                <i class="fas fa-info-circle"></i>
                                Status
                            </label>
                            <input type="text" id="editStatus" class="form-input" readonly>
                        </div>
                        
                        <div class="form-group">
                            <label for="editCreatedDate" class="form-label">
                                <i class="fas fa-clock"></i>
                                Created Date
                            </label>
                            <input type="text" id="editCreatedDate" class="form-input" readonly>
                        </div>
                        
                        <div class="form-group full-width">
                            <label for="editAdditionalMessage" class="form-label">
                                <i class="fas fa-comment"></i>
                                Additional Message
                            </label>
                            <textarea id="editAdditionalMessage" name="additionalMessage" class="form-textarea" 
                                      placeholder="Any additional information about your booking request..." rows="4"></textarea>
                            <div class="error-message" id="editAdditionalMessageError"></div>
                            <div class="char-counter">
                                <span id="editMessageCounter">0</span>/500 characters
                            </div>
                        </div>
                    </div>
                    
                    <div class="modal-actions">
                        <button type="button" class="modal-button cancel-button" id="cancelEdit">
                            <i class="fas fa-times"></i>
                            Cancel
                        </button>
                        <button type="submit" class="modal-button save-button" id="updateBooking">
                            <i class="fas fa-save"></i>
                            <span class="button-text">Update Booking</span>
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
                        Delete Booking Request
                    </h3>
                </div>
                
                <div class="modal-body">
                    <div class="confirm-icon">
                        <i class="fas fa-trash"></i>
                    </div>
                    <p class="confirm-message">Are you sure you want to delete this booking request? This action cannot be undone.</p>
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
    <script src="/js/booking-management.js"></script>
</body>
</html>
