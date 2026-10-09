<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Feedback Management - JCB Management System</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="/css/admin-feedback-management.css">
</head>
<body>
    <div class="admin-feedback-container">
        <!-- Header -->
        <header class="admin-feedback-header">
            <div class="header-content">
                <div class="logo-section">
                    <i class="fas fa-truck-monster"></i>
                    <h1 class="system-title">JCB Management System</h1>
                </div>
                <div class="user-info">
                    <div class="user-details">
                        <span class="user-name" id="userName">Loading...</span>
                        <span class="user-role">Admin Feedback Management</span>
                    </div>
                    <div class="user-avatar">
                        <i class="fas fa-user"></i>
                    </div>
                </div>
            </div>
        </header>

        <!-- Main Content -->
        <main class="admin-feedback-main">
            <div class="admin-feedback-content">
                <!-- Page Header -->
                <div class="page-header">
                    <div class="page-title-section">
                        <h2 class="page-title">
                            <i class="fas fa-comments"></i>
                            Feedback Management
                        </h2>
                        <p class="page-subtitle">View and manage all customer feedback</p>
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
                            <input type="text" id="searchInput" placeholder="Search by title, content, or user..." class="search-input">
                        </div>
                        <div class="filter-container">
                            <select id="typeFilter" class="filter-select">
                                <option value="">All Types</option>
                                <option value="COMPLAINT">Complaint</option>
                                <option value="SUGGESTION">Suggestion</option>
                                <option value="COMPLIMENT">Compliment</option>
                                <option value="BUG_REPORT">Bug Report</option>
                                <option value="FEATURE_REQUEST">Feature Request</option>
                                <option value="GENERAL_FEEDBACK">General Feedback</option>
                                <option value="SERVICE_RATING">Service Rating</option>
                                <option value="PRODUCT_RATING">Product Rating</option>
                            </select>
                            <select id="ratingFilter" class="filter-select">
                                <option value="">All Ratings</option>
                                <option value="1">1 Star</option>
                                <option value="2">2 Stars</option>
                                <option value="3">3 Stars</option>
                                <option value="4">4 Stars</option>
                                <option value="5">5 Stars</option>
                            </select>
                        </div>
                    </div>
                </div>

                <!-- Feedbacks Table -->
                <div class="table-section">
                    <div class="table-container">
                        <table class="feedback-table" id="feedbackTable">
                            <thead>
                                <tr>
                                    <th>Feedback ID</th>
                                    <th>User</th>
                                    <th>Title</th>
                                    <th>Type</th>
                                    <th>Rating</th>
                                    <th>Date</th>
                                    <th>Status</th>
                            
                                </tr>
                            </thead>
                            <tbody id="feedbackTableBody">
                                <tr class="loading-row">
                                    <td colspan="8" class="loading-cell">
                                        <div class="loading-spinner">
                                            <i class="fas fa-spinner fa-spin"></i>
                                            <span>Loading feedbacks...</span>
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    
                    <div class="no-data-message" id="noDataMessage" style="display: none;">
                        <i class="fas fa-comments"></i>
                        <h3>No Feedbacks Found</h3>
                        <p>No feedbacks match your search criteria. Try adjusting your filters.</p>
                    </div>
                </div>

                <!-- Statistics Section -->
                <div class="stats-section">
                    <h3 class="section-title">
                        <i class="fas fa-chart-bar"></i>
                        Feedback Overview
                    </h3>
                    
                    <div class="stats-grid">
                        <div class="stat-card">
                            <div class="stat-icon">
                                <i class="fas fa-comments"></i>
                            </div>
                            <div class="stat-content">
                                <span class="stat-number" id="totalFeedbacks">0</span>
                                <span class="stat-label">Total Feedbacks</span>
                            </div>
                        </div>

                        <div class="stat-card">
                            <div class="stat-icon">
                                <i class="fas fa-star"></i>
                            </div>
                            <div class="stat-content">
                                <span class="stat-number" id="averageRating">0.0</span>
                                <span class="stat-label">Average Rating</span>
                            </div>
                        </div>

                        <div class="stat-card">
                            <div class="stat-icon">
                                <i class="fas fa-exclamation-triangle"></i>
                            </div>
                            <div class="stat-content">
                                <span class="stat-number" id="complaintsCount">0</span>
                                <span class="stat-label">Complaints</span>
                            </div>
                        </div>

                        <div class="stat-card">
                            <div class="stat-icon">
                                <i class="fas fa-lightbulb"></i>
                            </div>
                            <div class="stat-content">
                                <span class="stat-number" id="suggestionsCount">0</span>
                                <span class="stat-label">Suggestions</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>

        <!-- Feedback Details Modal -->
        <div class="modal-overlay" id="detailsModal">
            <div class="modal-container large-modal">
                <div class="modal-header">
                    <h3 class="modal-title">
                        <i class="fas fa-comment"></i>
                        Feedback Details
                    </h3>
                    <button class="modal-close" id="closeDetailsModal">
                        <i class="fas fa-times"></i>
                    </button>
                </div>
                
                <div class="modal-body">
                    <div class="feedback-detail-content" id="feedbackDetailContent">
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

        <!-- Delete Confirmation Modal -->
        <div class="modal-overlay" id="deleteModal">
            <div class="modal-container small-modal">
                <div class="modal-header">
                    <h3 class="modal-title">
                        <i class="fas fa-exclamation-triangle"></i>
                        Delete Feedback
                    </h3>
                </div>
                
                <div class="modal-body">
                    <div class="confirm-icon">
                        <i class="fas fa-trash"></i>
                    </div>
                    <p class="confirm-message">Are you sure you want to delete this feedback? This action cannot be undone.</p>
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
    <script src="/js/admin-feedback-management.js"></script>
</body>
</html>
