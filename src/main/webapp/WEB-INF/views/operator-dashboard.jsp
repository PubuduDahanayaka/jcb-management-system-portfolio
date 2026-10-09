<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Operator Dashboard - JCB Management System</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="/css/operator-dashboard.css">
</head>
<body>
    <div class="operator-container">
        <!-- Header -->
        <header class="operator-header">
            <div class="header-content">
                <div class="logo-section">
                    <i class="fas fa-truck-monster"></i>
                    <h1 class="system-title">JCB Management System</h1>
                </div>
                <div class="user-info">
                    <div class="user-details">
                        <span class="user-name" id="userName">Loading...</span>
                        <span class="user-role">Operator Dashboard</span>
                    </div>
                    <div class="user-avatar">
                        <i class="fas fa-user"></i>
                    </div>
                </div>
            </div>
        </header>

        <!-- Main Content -->
        <main class="operator-main">
            <div class="operator-content">
                <!-- Navigation Tabs -->
                <section class="navigation-tabs">
                    <div class="tab-buttons">
                        <button class="tab-button active" id="profileTab">
                            <i class="fas fa-user"></i>
                            <span>My Profile</span>
                        </button>
                        <button class="tab-button" id="jobsTab">
                            <i class="fas fa-tasks"></i>
                            <span>My Jobs</span>
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

                <!-- Jobs Tab Content -->
                <section class="tab-content" id="jobsContent">
                    <!-- Job Management Header -->
                    <div class="job-header">
                        <h2 class="section-title">
                            <i class="fas fa-tasks"></i>
                            My Assigned Jobs
                        </h2>
                        <div class="job-actions">
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
                                <input type="text" id="searchInput" placeholder="Search by venue, description, or machine..." class="search-input">
                            </div>
                            <div class="filter-container">
                                <select id="statusFilter" class="filter-select">
                                    <option value="">All Status</option>
                                    <option value="SCHEDULED">Scheduled</option>
                                    <option value="IN_PROGRESS">In Progress</option>
                                    <option value="COMPLETED">Completed</option>
                                    <option value="CANCELLED">Cancelled</option>
                                    <option value="ON_HOLD">On Hold</option>
                                    <option value="OVERDUE">Overdue</option>
                                    <option value="PAUSED">Paused</option>
                                </select>
                                <select id="machineFilter" class="filter-select">
                                    <option value="">All Machines</option>
                                </select>
                            </div>
                        </div>
                    </div>

                    <!-- Jobs Table -->
                    <div class="table-section">
                        <div class="table-container">
                            <table class="job-table" id="jobTable">
                                <thead>
                                    <tr>
                                        <th>Job ID</th>
                                        <th>Machine</th>
                                        <th>Venue</th>
                                        <th>Start Date/Time</th>
                                        <th>End Date/Time</th>
                                        <th>Status</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody id="jobTableBody">
                                    <tr class="loading-row">
                                        <td colspan="7" class="loading-cell">
                                            <div class="loading-spinner">
                                                <i class="fas fa-spinner fa-spin"></i>
                                                <span>Loading jobs...</span>
                                            </div>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        
                        <div class="no-data-message" id="noDataMessage" style="display: none;">
                            <i class="fas fa-tasks"></i>
                            <h3>No Jobs Assigned</h3>
                            <p>You don't have any jobs assigned to you yet. Check back later for new assignments.</p>
                        </div>
                    </div>
                </section>
            </div>
        </main>

        <!-- Update Job Status Modal -->
        <div class="modal-overlay" id="statusModal">
            <div class="modal-container small-modal">
                <div class="modal-header">
                    <h3 class="modal-title">
                        <i class="fas fa-flag"></i>
                        Update Job Status
                    </h3>
                    <button class="modal-close" id="closeStatusModal">
                        <i class="fas fa-times"></i>
                    </button>
                </div>
                
                <form id="statusUpdateForm" class="modal-form">
                    <input type="hidden" id="statusJobId" name="jobId">
                    
                    <div class="form-group">
                        <label for="newJobStatus" class="form-label">
                            <i class="fas fa-flag"></i>
                            New Status *
                        </label>
                        <select id="newJobStatus" name="status" class="form-select" required>
                            <option value="">Select new status...</option>
                            <option value="SCHEDULED">Scheduled</option>
                            <option value="IN_PROGRESS">In Progress</option>
                            <option value="COMPLETED">Completed</option>
                            <option value="CANCELLED">Cancelled</option>
                            <option value="ON_HOLD">On Hold</option>
                            <option value="OVERDUE">Overdue</option>
                            <option value="PAUSED">Paused</option>
                        </select>
                        <div class="error-message" id="newJobStatusError"></div>
                    </div>
                    
                    <div class="form-group">
                        <label for="completionNotes" class="form-label">
                            <i class="fas fa-comment"></i>
                            Notes (Optional)
                        </label>
                        <textarea id="completionNotes" name="completionNotes" class="form-textarea" 
                                  placeholder="Add any notes about the job status change..." rows="3"></textarea>
                        <div class="error-message" id="completionNotesError"></div>
                        <div class="char-counter">
                            <span id="completionNotesCounter">0</span>/1000 characters
                        </div>
                    </div>
                    
                    <div class="modal-actions">
                        <button type="button" class="modal-button cancel-button" id="cancelStatusUpdate">
                            <i class="fas fa-times"></i>
                            Cancel
                        </button>
                        <button type="submit" class="modal-button save-button" id="updateStatus">
                            <i class="fas fa-save"></i>
                            <span class="button-text">Update Status</span>
                            <div class="loading-spinner" id="statusLoadingSpinner">
                                <i class="fas fa-spinner fa-spin"></i>
                            </div>
                        </button>
                    </div>
                </form>
            </div>
        </div>

        <!-- Job Details Modal -->
        <div class="modal-overlay" id="detailsModal">
            <div class="modal-container large-modal">
                <div class="modal-header">
                    <h3 class="modal-title">
                        <i class="fas fa-info-circle"></i>
                        Job Details
                    </h3>
                    <button class="modal-close" id="closeDetailsModal">
                        <i class="fas fa-times"></i>
                    </button>
                </div>
                
                <div class="modal-body">
                    <div class="job-detail-content" id="jobDetailContent">
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

        <!-- Notification Container -->
        <div class="notification-container" id="notificationContainer"></div>
    </div>
    
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf-autotable/3.5.31/jspdf.plugin.autotable.min.js"></script>
    <script src="/js/operator-dashboard.js"></script>
</body>
</html>
