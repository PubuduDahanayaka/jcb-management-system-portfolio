<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Booking Management - JCB Management System</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="/css/admin-booking-management.css">
</head>
<body>
    <div class="admin-booking-container">
        <!-- Header -->
        <header class="admin-booking-header">
            <div class="header-content">
                <div class="logo-section">
                    <i class="fas fa-truck-monster"></i>
                    <h1 class="system-title">JCB Management System</h1>
                </div>
                <div class="user-info">
                    <div class="user-details">
                        <span class="user-name" id="userName">Loading...</span>
                        <span class="user-role">Admin Booking Management</span>
                    </div>
                    <div class="user-avatar">
                        <i class="fas fa-user"></i>
                    </div>
                </div>
            </div>
        </header>

        <!-- Main Content -->
        <main class="admin-booking-main">
            <div class="admin-booking-content">
                <!-- Page Header -->
                <div class="page-header">
                    <div class="page-title-section">
                        <h2 class="page-title">
                            <i class="fas fa-calendar-check"></i>
                            Booking Request Management
                        </h2>
                        <p class="page-subtitle">Manage all booking requests and update their status</p>
                    </div>
                    <div class="page-actions">
                        <button class="action-button secondary-button" id="exportPdfBtn">
                            <i class="fas fa-file-pdf"></i>
                            <span>Export to PDF</span>
                        </button>
                        <a href="/admin-dashboard" class="action-button primary-button">
                            <i class="fas fa-arrow-left"></i>
                            <span>Back to Dashboard</span>
                        </a>
                    </div>
                </div>

                <!-- Search and Filter Section -->
                <div class="search-section">
                    <div class="search-container">
                        <div class="search-box">
                            <i class="fas fa-search"></i>
                            <input type="text" id="searchInput" placeholder="Search by user, machine, or venue..." class="search-input">
                        </div>
                        <div class="filter-container">
                            <select id="statusFilter" class="filter-select">
                                <option value="">All Statuses</option>
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
                </div>

                <!-- Bookings Table -->
                <div class="table-section">
                    <div class="table-container">
                        <table class="booking-table" id="bookingTable">
                            <thead>
                                <tr>
                                    <th>Booking ID</th>
                                    <th>User</th>
                                    <th>Machine</th>
                                    <th>Requested Date</th>
                                    <th>Message</th>
                                    <th>Status</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody id="bookingTableBody">
                                <tr class="loading-row">
                                    <td colspan="7" class="loading-cell">
                                        <div class="loading-spinner">
                                            <i class="fas fa-spinner fa-spin"></i>
                                            <span>Loading bookings...</span>
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    
                    <div class="no-data-message" id="noDataMessage" style="display: none;">
                        <i class="fas fa-calendar-times"></i>
                        <h3>No Bookings Found</h3>
                        <p>No booking requests match your search criteria. Try adjusting your filters.</p>
                    </div>
                </div>

                <!-- Statistics Section -->
                <div class="stats-section">
                    <h3 class="section-title">
                        <i class="fas fa-chart-bar"></i>
                        Booking Overview
                    </h3>
                    
                    <div class="stats-grid">
                        <div class="stat-card">
                            <div class="stat-icon">
                                <i class="fas fa-calendar"></i>
                            </div>
                            <div class="stat-content">
                                <span class="stat-number" id="totalBookings">0</span>
                                <span class="stat-label">Total Bookings</span>
                            </div>
                        </div>

                        <div class="stat-card">
                            <div class="stat-icon">
                                <i class="fas fa-clock"></i>
                            </div>
                            <div class="stat-content">
                                <span class="stat-number" id="pendingBookings">0</span>
                                <span class="stat-label">Pending</span>
                            </div>
                        </div>

                        <div class="stat-card">
                            <div class="stat-icon">
                                <i class="fas fa-check-circle"></i>
                            </div>
                            <div class="stat-content">
                                <span class="stat-number" id="approvedBookings">0</span>
                                <span class="stat-label">Approved</span>
                            </div>
                        </div>

                        <div class="stat-card">
                            <div class="stat-icon">
                                <i class="fas fa-times-circle"></i>
                            </div>
                            <div class="stat-content">
                                <span class="stat-number" id="rejectedBookings">0</span>
                                <span class="stat-label">Rejected</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>

        <!-- Booking Details Modal -->
        <div class="modal-overlay" id="detailsModal">
            <div class="modal-container large-modal">
                <div class="modal-header">
                    <h3 class="modal-title">
                        <i class="fas fa-calendar-alt"></i>
                        Booking Request Details
                    </h3>
                    <button class="modal-close" id="closeDetailsModal">
                        <i class="fas fa-times"></i>
                    </button>
                </div>
                
                <div class="modal-body">
                    <div class="booking-detail-content" id="bookingDetailContent">
                        <!-- Content will be populated by JavaScript -->
                    </div>
                </div>
                
                <div class="modal-actions">
                    <button type="button" class="modal-button cancel-button" id="closeDetailsBtn">
                        <i class="fas fa-times"></i>
                        Close
                    </button>
                </div>
            </div>
        </div>

        <!-- Status Update Modal -->
        <div class="modal-overlay" id="statusModal">
            <div class="modal-container medium-modal">
                <div class="modal-header">
                    <h3 class="modal-title">
                        <i class="fas fa-edit"></i>
                        Update Booking Status
                    </h3>
                </div>
                
                <div class="modal-body">
                    <form id="statusUpdateForm">
                        <div class="form-group">
                            <label for="currentStatus" class="form-label">Current Status</label>
                            <div class="current-status-display" id="currentStatusDisplay">
                                <!-- Current status will be displayed here -->
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label for="newStatus" class="form-label">New Status</label>
                            <select id="newStatus" name="status" class="form-select" required>
                                <option value="">Select Status</option>
                                <option value="PENDING">Pending</option>
                                <option value="APPROVED">Approved</option>
                                <option value="REJECTED">Rejected</option>
                                <option value="CANCELLED">Cancelled</option>
                                <option value="COMPLETED">Completed</option>
                            </select>
                        </div>
                        
                        <div class="form-group">
                            <label for="statusMessage" class="form-label">Additional Message (Optional)</label>
                            <textarea id="statusMessage" name="message" class="form-textarea" rows="3" placeholder="Add any additional notes about this status change..."></textarea>
                        </div>
                    </form>
                </div>
                
                <div class="modal-actions">
                    <button type="button" class="modal-button cancel-button" id="cancelStatusUpdate">
                        <i class="fas fa-times"></i>
                        Cancel
                    </button>
                    <button type="button" class="modal-button primary-button" id="confirmStatusUpdate">
                        <i class="fas fa-save"></i>
                        <span class="button-text">Update Status</span>
                        <div class="loading-spinner" id="statusUpdateLoadingSpinner">
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
    <script src="/js/admin-booking-management.js"></script>
</body>
</html>
