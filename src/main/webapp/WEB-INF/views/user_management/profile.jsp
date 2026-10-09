<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Profile - JCB Management System</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="/css/profile.css">
</head>
<body>
    <div class="profile-container">
        <!-- Header -->
        <header class="profile-header">
            <div class="header-content">
                <div class="logo-section">
                    <i class="fas fa-truck-monster"></i>
                    <h1 class="system-title">JCB Management System</h1>
                </div>
                <div class="user-info">
                    <div class="user-details">
                        <span class="user-name" id="userName">Loading...</span>
                        <span class="user-role">Customer Profile</span>
                    </div>
                    <div class="user-avatar">
                        <i class="fas fa-user"></i>
                    </div>
                </div>
            </div>
        </header>

        <!-- Main Content -->
        <main class="profile-main">
            <div class="profile-content">
                <!-- Profile Header Card -->
                <section class="profile-header-card">
                    <div class="profile-avatar-section">
                        <div class="profile-avatar">
                            <i class="fas fa-user-circle"></i>
                        </div>
                        <div class="profile-info">
                            <h2 class="profile-name" id="profileName">Loading...</h2>
                            <p class="profile-email" id="profileEmail">Loading...</p>
                            <span class="profile-id" id="profileId">Loading...</span>
                        </div>
                    </div>
                    <div class="profile-actions">
                        <button class="action-button edit-button" id="editProfileBtn">
                            <i class="fas fa-edit"></i>
                            Edit Profile
                        </button>
                        <button class="action-button danger-button" id="deleteAccountBtn">
                            <i class="fas fa-trash"></i>
                            Delete Account
                        </button>
                        <button class="action-button logout-button" id="logoutBtn">
                            <i class="fas fa-sign-out-alt"></i>
                            Logout
                        </button>
                    </div>
                </section>

                <!-- Profile Details -->
                <section class="profile-details-section">
                    <h3 class="section-title">
                        <i class="fas fa-user"></i>
                        Personal Information
                    </h3>
                    
                    <div class="profile-details-grid">
                        <div class="detail-card">
                            <div class="detail-icon">
                                <i class="fas fa-id-card"></i>
                            </div>
                            <div class="detail-content">
                                <h4 class="detail-label">User ID</h4>
                                <p class="detail-value" id="detailUserId">Loading...</p>
                            </div>
                        </div>

                        <div class="detail-card">
                            <div class="detail-icon">
                                <i class="fas fa-user"></i>
                            </div>
                            <div class="detail-content">
                                <h4 class="detail-label">First Name</h4>
                                <p class="detail-value" id="detailFirstName">Loading...</p>
                            </div>
                        </div>

                        <div class="detail-card">
                            <div class="detail-icon">
                                <i class="fas fa-user"></i>
                            </div>
                            <div class="detail-content">
                                <h4 class="detail-label">Last Name</h4>
                                <p class="detail-value" id="detailLastName">Loading...</p>
                            </div>
                        </div>

                        <div class="detail-card">
                            <div class="detail-icon">
                                <i class="fas fa-envelope"></i>
                            </div>
                            <div class="detail-content">
                                <h4 class="detail-label">Email Address</h4>
                                <p class="detail-value" id="detailEmail">Loading...</p>
                            </div>
                        </div>

                        <div class="detail-card">
                            <div class="detail-icon">
                                <i class="fas fa-phone"></i>
                            </div>
                            <div class="detail-content">
                                <h4 class="detail-label">Contact Number</h4>
                                <p class="detail-value" id="detailContactNumber">Loading...</p>
                            </div>
                        </div>

                        <div class="detail-card">
                            <div class="detail-icon">
                                <i class="fas fa-user-tag"></i>
                            </div>
                            <div class="detail-content">
                                <h4 class="detail-label">User Type</h4>
                                <p class="detail-value" id="detailUserType">Loading...</p>
                            </div>
                        </div>
                    </div>
                </section>

                <!-- Quick Actions -->
                <section class="quick-actions-section">
                    <h3 class="section-title">
                        <i class="fas fa-bolt"></i>
                        Quick Actions
                    </h3>
                    
                    <div class="actions-grid">
                        <a href="/booking-requests" class="action-card">
                            <div class="action-icon">
                                <i class="fas fa-calendar-check"></i>
                            </div>
                            <div class="action-content">
                                <h4 class="action-title">My Bookings</h4>
                                <p class="action-description">View and manage your booking requests</p>
                            </div>
                        </a>

                        <a href="/feedbacks" class="action-card">
                            <div class="action-icon">
                                <i class="fas fa-comment-dots"></i>
                            </div>
                            <div class="action-content">
                                <h4 class="action-title">My Feedbacks</h4>
                                <p class="action-description">View and submit feedback</p>
                            </div>
                        </a>

                        <a href="/machines" class="action-card">
                            <div class="action-icon">
                                <i class="fas fa-truck-monster"></i>
                            </div>
                            <div class="action-content">
                                <h4 class="action-title">Available Machines</h4>
                                <p class="action-description">Browse available JCB machines</p>
                            </div>
                        </a>

                        <a href="/customer-dashboard" class="action-card">
                            <div class="action-icon">
                                <i class="fas fa-tachometer-alt"></i>
                            </div>
                            <div class="action-content">
                                <h4 class="action-title">Dashboard</h4>
                                <p class="action-description">Return to your dashboard</p>
                            </div>
                        </a>
                    </div>
                </section>
            </div>
        </main>

        <!-- Edit Profile Modal -->
        <div class="modal-overlay" id="editModal">
            <div class="modal-container">
                <div class="modal-header">
                    <h3 class="modal-title">
                        <i class="fas fa-edit"></i>
                        Edit Profile
                    </h3>
                    <button class="modal-close" id="closeEditModal">
                        <i class="fas fa-times"></i>
                    </button>
                </div>
                
                <form id="editProfileForm" class="modal-form">
                    <div class="form-grid">
                        <div class="form-group">
                            <label for="editFirstName" class="form-label">
                                <i class="fas fa-user"></i>
                                First Name *
                            </label>
                            <input type="text" id="editFirstName" name="firstName" class="form-input" required>
                            <div class="error-message" id="editFirstNameError"></div>
                        </div>
                        
                        <div class="form-group">
                            <label for="editLastName" class="form-label">
                                <i class="fas fa-user"></i>
                                Last Name *
                            </label>
                            <input type="text" id="editLastName" name="lastName" class="form-input" required>
                            <div class="error-message" id="editLastNameError"></div>
                        </div>
                        
                        <div class="form-group">
                            <label for="editEmail" class="form-label">
                                <i class="fas fa-envelope"></i>
                                Email Address *
                            </label>
                            <input type="email" id="editEmail" name="email" class="form-input" required>
                            <div class="error-message" id="editEmailError"></div>
                        </div>
                        
                        <div class="form-group">
                            <label for="editContactNumber" class="form-label">
                                <i class="fas fa-phone"></i>
                                Contact Number *
                            </label>
                            <input type="tel" id="editContactNumber" name="contactNumber" class="form-input" required>
                            <div class="error-message" id="editContactNumberError"></div>
                        </div>
                    </div>
                    
                    <div class="modal-actions">
                        <button type="button" class="modal-button cancel-button" id="cancelEdit">
                            <i class="fas fa-times"></i>
                            Cancel
                        </button>
                        <button type="submit" class="modal-button save-button" id="saveProfile">
                            <i class="fas fa-save"></i>
                            <span class="button-text">Save Changes</span>
                            <div class="loading-spinner" id="saveLoadingSpinner">
                                <i class="fas fa-spinner fa-spin"></i>
                            </div>
                        </button>
                    </div>
                </form>
            </div>
        </div>

        <!-- Confirmation Modal -->
        <div class="modal-overlay" id="confirmModal">
            <div class="modal-container small-modal">
                <div class="modal-header">
                    <h3 class="modal-title" id="confirmTitle">
                        <i class="fas fa-exclamation-triangle"></i>
                        Confirm Action
                    </h3>
                </div>
                
                <div class="modal-body">
                    <div class="confirm-icon" id="confirmIcon">
                        <i class="fas fa-question-circle"></i>
                    </div>
                    <p class="confirm-message" id="confirmMessage">Are you sure you want to proceed?</p>
                </div>
                
                <div class="modal-actions">
                    <button type="button" class="modal-button cancel-button" id="confirmCancel">
                        <i class="fas fa-times"></i>
                        Cancel
                    </button>
                    <button type="button" class="modal-button danger-button" id="confirmAction">
                        <i class="fas fa-check"></i>
                        <span class="button-text">Confirm</span>
                        <div class="loading-spinner" id="confirmLoadingSpinner">
                            <i class="fas fa-spinner fa-spin"></i>
                        </div>
                    </button>
                </div>
            </div>
        </div>

        <!-- Notification Container -->
        <div class="notification-container" id="notificationContainer"></div>
    </div>
    
    <script src="/js/profile.js"></script>
</body>
</html>
