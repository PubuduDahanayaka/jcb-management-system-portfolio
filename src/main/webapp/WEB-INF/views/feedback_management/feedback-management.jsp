<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Feedback Management - JCB Management System</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="/css/feedback-management.css">
</head>
<body>
    <div class="feedback-container">
        <!-- Header -->
        <header class="feedback-header">
            <div class="header-content">
                <div class="logo-section">
                    <i class="fas fa-truck-monster"></i>
                    <h1 class="system-title">JCB Management System</h1>
                </div>
                <div class="user-info">
                    <div class="user-details">
                        <span class="user-name" id="userName">Loading...</span>
                        <span class="user-role">Feedback Management</span>
                    </div>
                    <div class="user-avatar">
                        <i class="fas fa-user"></i>
                    </div>
                </div>
            </div>
        </header>

        <!-- Main Content -->
        <main class="feedback-main">
            <div class="feedback-content">
                <!-- Page Header -->
                <section class="page-header">
                    <div class="page-title-section">
                        <h2 class="page-title">
                            <i class="fas fa-comment-dots"></i>
                            My Feedback
                        </h2>
                        <p class="page-subtitle">Share your thoughts and suggestions with us</p>
                    </div>
                    <div class="page-actions">
                        <button class="action-button primary-button" id="addFeedbackBtn">
                            <i class="fas fa-plus"></i>
                            <span>New Feedback</span>
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
                            <input type="text" id="searchInput" placeholder="Search by title, content, or type..." class="search-input">
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
                                <option value="5">5 Stars</option>
                                <option value="4">4 Stars</option>
                                <option value="3">3 Stars</option>
                                <option value="2">2 Stars</option>
                                <option value="1">1 Star</option>
                            </select>
                        </div>
                    </div>
                </section>

                <!-- Feedback Table -->
                <section class="table-section">
                    <div class="table-container">
                        <table class="feedback-table" id="feedbackTable">
                            <thead>
                                <tr>
                                    <th>Feedback ID</th>
                                    <th>Title</th>
                                    <th>Type</th>
                                    <th>Rating</th>
                                    <th>Created Date</th>
                                    <th>Status</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody id="feedbackTableBody">
                                <tr class="loading-row">
                                    <td colspan="7" class="loading-cell">
                                        <div class="loading-spinner">
                                            <i class="fas fa-spinner fa-spin"></i>
                                            <span>Loading feedback...</span>
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    
                    <div class="no-data-message" id="noDataMessage" style="display: none;">
                        <i class="fas fa-comment-slash"></i>
                        <h3>No Feedback Found</h3>
                        <p>You haven't submitted any feedback yet. Share your thoughts and help us improve our services.</p>
                        <button class="action-button primary-button" id="createFirstFeedbackBtn">
                            <i class="fas fa-plus"></i>
                            <span>Submit First Feedback</span>
                        </button>
                    </div>
                </section>
            </div>
        </main>

        <!-- Create Feedback Modal -->
        <div class="modal-overlay" id="createModal">
            <div class="modal-container">
                <div class="modal-header">
                    <h3 class="modal-title">
                        <i class="fas fa-plus"></i>
                        New Feedback
                    </h3>
                    <button class="modal-close" id="closeCreateModal">
                        <i class="fas fa-times"></i>
                    </button>
                </div>
                
                <form id="createFeedbackForm" class="modal-form">
                    <div class="form-grid">
                        <div class="form-group">
                            <label for="createTitle" class="form-label">
                                <i class="fas fa-heading"></i>
                                Feedback Title *
                            </label>
                            <input type="text" id="createTitle" name="title" class="form-input" 
                                   placeholder="Enter a descriptive title" required>
                            <div class="error-message" id="createTitleError"></div>
                            <div class="char-counter">
                                <span id="createTitleCounter">0</span>/100 characters
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label for="createFeedbackType" class="form-label">
                                <i class="fas fa-tag"></i>
                                Feedback Type *
                            </label>
                            <select id="createFeedbackType" name="feedbackType" class="form-select" required>
                                <option value="">Select feedback type...</option>
                                <option value="COMPLAINT">Complaint</option>
                                <option value="SUGGESTION">Suggestion</option>
                                <option value="COMPLIMENT">Compliment</option>
                                <option value="BUG_REPORT">Bug Report</option>
                                <option value="FEATURE_REQUEST">Feature Request</option>
                                <option value="GENERAL_FEEDBACK">General Feedback</option>
                                <option value="SERVICE_RATING">Service Rating</option>
                                <option value="PRODUCT_RATING">Product Rating</option>
                            </select>
                            <div class="error-message" id="createFeedbackTypeError"></div>
                        </div>
                        
                        <div class="form-group">
                            <label for="createRating" class="form-label">
                                <i class="fas fa-star"></i>
                                Rating (1-5)
                            </label>
                            <div class="rating-container">
                                <div class="star-rating" id="createStarRating">
                                    <input type="radio" id="create-star5" name="rating" value="5">
                                    <label for="create-star5"><i class="fas fa-star"></i></label>
                                    <input type="radio" id="create-star4" name="rating" value="4">
                                    <label for="create-star4"><i class="fas fa-star"></i></label>
                                    <input type="radio" id="create-star3" name="rating" value="3">
                                    <label for="create-star3"><i class="fas fa-star"></i></label>
                                    <input type="radio" id="create-star2" name="rating" value="2">
                                    <label for="create-star2"><i class="fas fa-star"></i></label>
                                    <input type="radio" id="create-star1" name="rating" value="1">
                                    <label for="create-star1"><i class="fas fa-star"></i></label>
                                </div>
                                <span class="rating-text" id="createRatingText">No rating selected</span>
                            </div>
                            <div class="error-message" id="createRatingError"></div>
                        </div>
                        
                        <div class="form-group full-width">
                            <label for="createContent" class="form-label">
                                <i class="fas fa-comment"></i>
                                Feedback Content *
                            </label>
                            <textarea id="createContent" name="content" class="form-textarea" 
                                      placeholder="Please provide detailed feedback..." rows="6" required></textarea>
                            <div class="error-message" id="createContentError"></div>
                            <div class="char-counter">
                                <span id="createContentCounter">0</span>/1000 characters
                            </div>
                        </div>
                        
                        <div class="form-group full-width">
                            <label class="checkbox-label">
                                <input type="checkbox" id="createIsAnonymous" name="isAnonymous">
                                <span class="checkmark"></span>
                                Submit anonymously
                            </label>
                        </div>
                    </div>
                    
                    <div class="modal-actions">
                        <button type="button" class="modal-button cancel-button" id="cancelCreate">
                            <i class="fas fa-times"></i>
                            Cancel
                        </button>
                        <button type="submit" class="modal-button save-button" id="saveFeedback">
                            <i class="fas fa-save"></i>
                            <span class="button-text">Submit Feedback</span>
                            <div class="loading-spinner" id="createLoadingSpinner">
                                <i class="fas fa-spinner fa-spin"></i>
                            </div>
                        </button>
                    </div>
                </form>
            </div>
        </div>

        <!-- Edit Feedback Modal -->
        <div class="modal-overlay" id="editModal">
            <div class="modal-container">
                <div class="modal-header">
                    <h3 class="modal-title">
                        <i class="fas fa-edit"></i>
                        Edit Feedback
                    </h3>
                    <button class="modal-close" id="closeEditModal">
                        <i class="fas fa-times"></i>
                    </button>
                </div>
                
                <form id="editFeedbackForm" class="modal-form">
                    <input type="hidden" id="editFeedbackId" name="feedbackId">
                    
                    <div class="form-grid">
                        <div class="form-group">
                            <label for="editTitle" class="form-label">
                                <i class="fas fa-heading"></i>
                                Feedback Title *
                            </label>
                            <input type="text" id="editTitle" name="title" class="form-input" required>
                            <div class="error-message" id="editTitleError"></div>
                            <div class="char-counter">
                                <span id="editTitleCounter">0</span>/100 characters
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label for="editFeedbackType" class="form-label">
                                <i class="fas fa-tag"></i>
                                Feedback Type *
                            </label>
                            <select id="editFeedbackType" name="feedbackType" class="form-select" required>
                                <option value="">Select feedback type...</option>
                                <option value="COMPLAINT">Complaint</option>
                                <option value="SUGGESTION">Suggestion</option>
                                <option value="COMPLIMENT">Compliment</option>
                                <option value="BUG_REPORT">Bug Report</option>
                                <option value="FEATURE_REQUEST">Feature Request</option>
                                <option value="GENERAL_FEEDBACK">General Feedback</option>
                                <option value="SERVICE_RATING">Service Rating</option>
                                <option value="PRODUCT_RATING">Product Rating</option>
                            </select>
                            <div class="error-message" id="editFeedbackTypeError"></div>
                        </div>
                        
                        <div class="form-group">
                            <label for="editRating" class="form-label">
                                <i class="fas fa-star"></i>
                                Rating (1-5)
                            </label>
                            <div class="rating-container">
                                <div class="star-rating" id="editStarRating">
                                    <input type="radio" id="edit-star5" name="rating" value="5">
                                    <label for="edit-star5"><i class="fas fa-star"></i></label>
                                    <input type="radio" id="edit-star4" name="rating" value="4">
                                    <label for="edit-star4"><i class="fas fa-star"></i></label>
                                    <input type="radio" id="edit-star3" name="rating" value="3">
                                    <label for="edit-star3"><i class="fas fa-star"></i></label>
                                    <input type="radio" id="edit-star2" name="rating" value="2">
                                    <label for="edit-star2"><i class="fas fa-star"></i></label>
                                    <input type="radio" id="edit-star1" name="rating" value="1">
                                    <label for="edit-star1"><i class="fas fa-star"></i></label>
                                </div>
                                <span class="rating-text" id="editRatingText">No rating selected</span>
                            </div>
                            <div class="error-message" id="editRatingError"></div>
                        </div>
                        
                        <div class="form-group">
                            <label for="editCreatedDate" class="form-label">
                                <i class="fas fa-clock"></i>
                                Created Date
                            </label>
                            <input type="text" id="editCreatedDate" class="form-input" readonly>
                        </div>
                        
                        <div class="form-group full-width">
                            <label for="editContent" class="form-label">
                                <i class="fas fa-comment"></i>
                                Feedback Content *
                            </label>
                            <textarea id="editContent" name="content" class="form-textarea" rows="6" required></textarea>
                            <div class="error-message" id="editContentError"></div>
                            <div class="char-counter">
                                <span id="editContentCounter">0</span>/1000 characters
                            </div>
                        </div>
                        
                        <div class="form-group full-width">
                            <label class="checkbox-label">
                                <input type="checkbox" id="editIsAnonymous" name="isAnonymous">
                                <span class="checkmark"></span>
                                Submit anonymously
                            </label>
                        </div>
                        
                        <div class="form-group full-width" id="adminResponseGroup" style="display: none;">
                            <label for="editAdminResponse" class="form-label">
                                <i class="fas fa-reply"></i>
                                Admin Response
                            </label>
                            <textarea id="editAdminResponse" name="adminResponse" class="form-textarea" 
                                      placeholder="Admin response will appear here..." rows="3" readonly></textarea>
                        </div>
                    </div>
                    
                    <div class="modal-actions">
                        <button type="button" class="modal-button cancel-button" id="cancelEdit">
                            <i class="fas fa-times"></i>
                            Cancel
                        </button>
                        <button type="submit" class="modal-button save-button" id="updateFeedback">
                            <i class="fas fa-save"></i>
                            <span class="button-text">Update Feedback</span>
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

        <!-- Feedback Detail Modal -->
        <div class="modal-overlay" id="detailModal">
            <div class="modal-container large-modal">
                <div class="modal-header">
                    <h3 class="modal-title">
                        <i class="fas fa-eye"></i>
                        Feedback Details
                    </h3>
                    <button class="modal-close" id="closeDetailModal">
                        <i class="fas fa-times"></i>
                    </button>
                </div>
                
                <div class="modal-body">
                    <div class="feedback-detail-content" id="feedbackDetailContent">
                        <!-- Content will be populated by JavaScript -->
                    </div>
                </div>
                
                <div class="modal-actions">
                    <button type="button" class="modal-button cancel-button" id="closeDetailBtn">
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
    <script src="/js/feedback-management.js"></script>
</body>
</html>
