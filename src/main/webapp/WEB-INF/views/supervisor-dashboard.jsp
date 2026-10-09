<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Supervisor Dashboard - JCB Management System</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="/css/supervisor-dashboard.css">
</head>
<body>
    <div class="supervisor-container">
        <!-- Header -->
        <header class="supervisor-header">
            <div class="header-content">
                <div class="logo-section">
                    <i class="fas fa-truck-monster"></i>
                    <h1 class="system-title">JCB Management System</h1>
                </div>
                <div class="user-info">
                    <div class="user-details">
                        <span class="user-name" id="userName">Loading...</span>
                        <span class="user-role">Supervisor Dashboard</span>
                    </div>
                    <div class="user-avatar">
                        <i class="fas fa-user"></i>
                    </div>
                </div>
            </div>
        </header>

        <!-- Main Content -->
        <main class="supervisor-main">
            <div class="supervisor-content">
                <!-- Navigation Tabs -->
                <section class="navigation-tabs">
                    <div class="tab-buttons">
                        <button class="tab-button active" id="profileTab">
                            <i class="fas fa-user"></i>
                            <span>My Profile</span>
                        </button>
                        <button class="tab-button" id="jobsTab">
                            <i class="fas fa-tasks"></i>
                            <span>Job Management</span>
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
                            Job Management
                        </h2>
                        <div class="job-actions">
                            <button class="action-button primary-button" id="addJobBtn">
                                <i class="fas fa-plus"></i>
                                <span>New Job</span>
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
                                <input type="text" id="searchInput" placeholder="Search by venue, description, or assigned user..." class="search-input">
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
                                <select id="operatorFilter" class="filter-select">
                                    <option value="">All Operators</option>
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
                                        <th>Assigned Operator</th>
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
                                        <td colspan="8" class="loading-cell">
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
                            <h3>No Jobs Found</h3>
                            <p>You haven't created any jobs yet. Create your first job to get started.</p>
                            <button class="action-button primary-button" id="createFirstJobBtn">
                                <i class="fas fa-plus"></i>
                                <span>Create First Job</span>
                            </button>
                        </div>
                    </div>
                </section>
            </div>
        </main>

        <!-- Create Job Modal -->
        <div class="modal-overlay" id="createModal">
            <div class="modal-container">
                <div class="modal-header">
                    <h3 class="modal-title">
                        <i class="fas fa-plus"></i>
                        New Job
                    </h3>
                    <button class="modal-close" id="closeCreateModal">
                        <i class="fas fa-times"></i>
                    </button>
                </div>
                
                <form id="createJobForm" class="modal-form">
                    <div class="form-grid">
                        <div class="form-group">
                            <label for="createAssignedUserId" class="form-label">
                                <i class="fas fa-user"></i>
                                Assign to Operator *
                            </label>
                            <select id="createAssignedUserId" name="assignedUserId" class="form-select" required>
                                <option value="">Select an operator...</option>
                            </select>
                            <div class="error-message" id="createAssignedUserIdError"></div>
                        </div>
                        
                        <div class="form-group">
                            <label for="createMachineId" class="form-label">
                                <i class="fas fa-truck-monster"></i>
                                Machine *
                            </label>
                            <select id="createMachineId" name="machineId" class="form-select" required>
                                <option value="">Select a machine...</option>
                            </select>
                            <div class="error-message" id="createMachineIdError"></div>
                        </div>
                        
                        <div class="form-group full-width">
                            <label for="createVenue" class="form-label">
                                <i class="fas fa-map-marker-alt"></i>
                                Venue *
                            </label>
                            <input type="text" id="createVenue" name="venue" class="form-input" 
                                   placeholder="Enter job venue" required>
                            <div class="error-message" id="createVenueError"></div>
                            <div class="char-counter">
                                <span id="createVenueCounter">0</span>/200 characters
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label for="createStartDate" class="form-label">
                                <i class="fas fa-calendar"></i>
                                Start Date *
                            </label>
                            <input type="date" id="createStartDate" name="startDate" class="form-input" required>
                            <div class="error-message" id="createStartDateError"></div>
                        </div>
                        
                        <div class="form-group">
                            <label for="createStartTime" class="form-label">
                                <i class="fas fa-clock"></i>
                                Start Time *
                            </label>
                            <input type="time" id="createStartTime" name="startTime" class="form-input" required>
                            <div class="error-message" id="createStartTimeError"></div>
                        </div>
                        
                        <div class="form-group">
                            <label for="createEndDate" class="form-label">
                                <i class="fas fa-calendar-check"></i>
                                End Date *
                            </label>
                            <input type="date" id="createEndDate" name="endDate" class="form-input" required>
                            <div class="error-message" id="createEndDateError"></div>
                        </div>
                        
                        <div class="form-group">
                            <label for="createEndTime" class="form-label">
                                <i class="fas fa-clock"></i>
                                End Time *
                            </label>
                            <input type="time" id="createEndTime" name="endTime" class="form-input" required>
                            <div class="error-message" id="createEndTimeError"></div>
                        </div>
                        
                        <div class="form-group">
                            <label for="createEstimatedDurationHours" class="form-label">
                                <i class="fas fa-hourglass-half"></i>
                                Estimated Duration (Hours)
                            </label>
                            <input type="number" id="createEstimatedDurationHours" name="estimatedDurationHours" 
                                   class="form-input" placeholder="0.0" step="0.5" min="0">
                            <div class="error-message" id="createEstimatedDurationHoursError"></div>
                        </div>
                        
                        <div class="form-group full-width">
                            <label for="createDescription" class="form-label">
                                <i class="fas fa-comment"></i>
                                Description
                            </label>
                            <textarea id="createDescription" name="description" class="form-textarea" 
                                      placeholder="Enter job description..." rows="4"></textarea>
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
                        <button type="submit" class="modal-button save-button" id="saveJob">
                            <i class="fas fa-save"></i>
                            <span class="button-text">Create Job</span>
                            <div class="loading-spinner" id="createLoadingSpinner">
                                <i class="fas fa-spinner fa-spin"></i>
                            </div>
                        </button>
                    </div>
                </form>
            </div>
        </div>

        <!-- Edit Job Modal -->
        <div class="modal-overlay" id="editModal">
            <div class="modal-container">
                <div class="modal-header">
                    <h3 class="modal-title">
                        <i class="fas fa-edit"></i>
                        Edit Job
                    </h3>
                    <button class="modal-close" id="closeEditModal">
                        <i class="fas fa-times"></i>
                    </button>
                </div>
                
                <form id="editJobForm" class="modal-form">
                    <input type="hidden" id="editJobId" name="jobId">
                    
                    <div class="form-grid">
                        <div class="form-group">
                            <label for="editJobStatus" class="form-label">
                                <i class="fas fa-flag"></i>
                                Job Status *
                            </label>
                            <select id="editJobStatus" name="jobStatus" class="form-select" required>
                                <option value="">Select status...</option>
                                <option value="SCHEDULED">Scheduled</option>
                                <option value="IN_PROGRESS">In Progress</option>
                                <option value="COMPLETED">Completed</option>
                                <option value="CANCELLED">Cancelled</option>
                                <option value="ON_HOLD">On Hold</option>
                                <option value="OVERDUE">Overdue</option>
                                <option value="PAUSED">Paused</option>
                            </select>
                            <div class="error-message" id="editJobStatusError"></div>
                        </div>
                        
                        <div class="form-group full-width">
                            <label for="editVenue" class="form-label">
                                <i class="fas fa-map-marker-alt"></i>
                                Venue *
                            </label>
                            <input type="text" id="editVenue" name="venue" class="form-input" required>
                            <div class="error-message" id="editVenueError"></div>
                            <div class="char-counter">
                                <span id="editVenueCounter">0</span>/200 characters
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label for="editStartDate" class="form-label">
                                <i class="fas fa-calendar"></i>
                                Start Date *
                            </label>
                            <input type="date" id="editStartDate" name="startDate" class="form-input" required>
                            <div class="error-message" id="editStartDateError"></div>
                        </div>
                        
                        <div class="form-group">
                            <label for="editStartTime" class="form-label">
                                <i class="fas fa-clock"></i>
                                Start Time *
                            </label>
                            <input type="time" id="editStartTime" name="startTime" class="form-input" required>
                            <div class="error-message" id="editStartTimeError"></div>
                        </div>
                        
                        <div class="form-group">
                            <label for="editEndDate" class="form-label">
                                <i class="fas fa-calendar-check"></i>
                                End Date *
                            </label>
                            <input type="date" id="editEndDate" name="endDate" class="form-input" required>
                            <div class="error-message" id="editEndDateError"></div>
                        </div>
                        
                        <div class="form-group">
                            <label for="editEndTime" class="form-label">
                                <i class="fas fa-clock"></i>
                                End Time *
                            </label>
                            <input type="time" id="editEndTime" name="endTime" class="form-input" required>
                            <div class="error-message" id="editEndTimeError"></div>
                        </div>
                        
                        <div class="form-group">
                            <label for="editEstimatedDurationHours" class="form-label">
                                <i class="fas fa-hourglass-half"></i>
                                Estimated Duration (Hours)
                            </label>
                            <input type="number" id="editEstimatedDurationHours" name="estimatedDurationHours" 
                                   class="form-input" step="0.5" min="0">
                            <div class="error-message" id="editEstimatedDurationHoursError"></div>
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
                        <button type="submit" class="modal-button save-button" id="updateJob">
                            <i class="fas fa-save"></i>
                            <span class="button-text">Update Job</span>
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
                        Delete Job
                    </h3>
                </div>
                
                <div class="modal-body">
                    <div class="confirm-icon">
                        <i class="fas fa-trash"></i>
                    </div>
                    <p class="confirm-message">Are you sure you want to delete this job? This action cannot be undone.</p>
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
    <script src="/js/supervisor-dashboard.js"></script>
</body>
</html>
