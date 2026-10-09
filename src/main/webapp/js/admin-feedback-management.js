// Admin Feedback Management JavaScript
class AdminFeedbackManager {
    constructor() {
        this.feedbacks = [];
        this.filteredFeedbacks = [];
        this.currentFeedbackId = null;
        this.init();
    }

    init() {
        this.setupEventListeners();
        this.loadFeedbacks();
        this.loadUserInfo();
    }

    setupEventListeners() {
        // Search functionality
        const searchInput = document.getElementById('searchInput');
        if (searchInput) {
            searchInput.addEventListener('input', (e) => {
                this.filterFeedbacks();
            });
        }

        // Filter functionality
        const typeFilter = document.getElementById('typeFilter');
        const ratingFilter = document.getElementById('ratingFilter');
        
        if (typeFilter) {
            typeFilter.addEventListener('change', () => {
                this.filterFeedbacks();
            });
        }
        
        if (ratingFilter) {
            ratingFilter.addEventListener('change', () => {
                this.filterFeedbacks();
            });
        }

        // Export PDF
        const exportPdfBtn = document.getElementById('exportPdfBtn');
        if (exportPdfBtn) {
            exportPdfBtn.addEventListener('click', () => {
                this.exportToPDF();
            });
        }

        // Modal controls
        this.setupModalControls();
    }

    setupModalControls() {
        // Details modal
        const detailsModal = document.getElementById('detailsModal');
        const closeDetailsModal = document.getElementById('closeDetailsModal');
        const closeDetailsBtn = document.getElementById('closeDetailsBtn');

        if (closeDetailsModal) {
            closeDetailsModal.addEventListener('click', () => {
                this.closeModal('detailsModal');
            });
        }

        if (closeDetailsBtn) {
            closeDetailsBtn.addEventListener('click', () => {
                this.closeModal('detailsModal');
            });
        }

        // Delete modal
        const deleteModal = document.getElementById('deleteModal');
        const cancelDelete = document.getElementById('cancelDelete');
        const confirmDelete = document.getElementById('confirmDelete');

        if (cancelDelete) {
            cancelDelete.addEventListener('click', () => {
                this.closeModal('deleteModal');
            });
        }

        if (confirmDelete) {
            confirmDelete.addEventListener('click', () => {
                this.deleteFeedback();
            });
        }

        // Close modals on overlay click
        if (detailsModal) {
            detailsModal.addEventListener('click', (e) => {
                if (e.target === detailsModal) {
                    this.closeModal('detailsModal');
                }
            });
        }

        if (deleteModal) {
            deleteModal.addEventListener('click', (e) => {
                if (e.target === deleteModal) {
                    this.closeModal('deleteModal');
                }
            });
        }
    }

    async loadUserInfo() {
        try {
            const userId = sessionStorage.getItem('userId');
            if (!userId) {
                window.location.href = '/login';
                return;
            }

            const response = await fetch(`/api/users/${userId}`);
            if (response.ok) {
                const user = await response.json();
                const userNameElement = document.getElementById('userName');
                if (userNameElement) {
                    userNameElement.textContent = `${user.firstName} ${user.lastName}`;
                }
            }
        } catch (error) {
            console.error('Error loading user info:', error);
        }
    }

    async loadFeedbacks() {
        try {
            this.showLoading(true);
            
            const response = await fetch('/api/feedbacks');
            if (response.ok) {
                const data = await response.json();
                console.log('Feedback data received:', data); // Debug log
                this.feedbacks = data;
                this.filteredFeedbacks = [...this.feedbacks];
                this.renderFeedbacks();
                this.updateStatistics();
            } else {
                this.showNotification('Failed to load feedbacks', 'error');
            }
        } catch (error) {
            console.error('Error loading feedbacks:', error);
            this.showNotification('Error loading feedbacks', 'error');
        } finally {
            this.showLoading(false);
        }
    }

    filterFeedbacks() {
        const searchTerm = document.getElementById('searchInput').value.toLowerCase();
        const typeFilter = document.getElementById('typeFilter').value;
        const ratingFilter = document.getElementById('ratingFilter').value;

        this.filteredFeedbacks = this.feedbacks.filter(feedback => {
            const matchesSearch = !searchTerm || 
                feedback.title.toLowerCase().includes(searchTerm) ||
                feedback.content.toLowerCase().includes(searchTerm) ||
                (feedback.userInfo && feedback.userInfo.firstName && feedback.userInfo.firstName.toLowerCase().includes(searchTerm)) ||
                (feedback.userInfo && feedback.userInfo.lastName && feedback.userInfo.lastName.toLowerCase().includes(searchTerm));

            const matchesType = !typeFilter || feedback.feedbackType === typeFilter;
            const matchesRating = !ratingFilter || feedback.rating === parseInt(ratingFilter);

            return matchesSearch && matchesType && matchesRating;
        });

        this.renderFeedbacks();
    }

    renderFeedbacks() {
        const tbody = document.getElementById('feedbackTableBody');
        const noDataMessage = document.getElementById('noDataMessage');
        
        if (!tbody) return;

        console.log(this.filteredFeedbacks);

        if (this.filteredFeedbacks.length === 0) {
            tbody.innerHTML = '';
            if (noDataMessage) {
                noDataMessage.style.display = 'block';
            }
            return;
        }

        if (noDataMessage) {
            noDataMessage.style.display = 'none';
        }

        tbody.innerHTML = this.filteredFeedbacks.map(feedback => `
            <tr class="fade-in">
                <td>
                    <span class="font-mono text-sm">${feedback.feedbackId || 'N/A'}</span>
                </td>
                <td>
                    <div class="user-info">
                        <div class="user-name">${feedback.userInfo ? `${feedback.userInfo.firstName || ''} ${feedback.userInfo.lastName || ''}` : 'Unknown User'}</div>
                        <div class="user-id text-xs text-gray-500">${feedback.userInfo ? feedback.userInfo.userId : 'N/A'}</div>
                    </div>
                </td>
                <td>
                    <div class="feedback-title font-medium">${feedback.title || 'N/A'}</div>
                </td>
                <td>
                    <span class="type-badge type-${(feedback.feedbackType || 'GENERAL_FEEDBACK').toLowerCase()}">
                        ${this.getTypeIcon(feedback.feedbackType)}
                        ${(feedback.feedbackType || 'GENERAL_FEEDBACK').replace('_', ' ')}
                    </span>
                </td>
                <td>
                    <div class="rating-stars">
                        ${this.renderStars(feedback.rating || 0)}
                        <span class="rating-text ml-1 text-sm text-gray-600">(${feedback.rating || 0})</span>
                    </div>
                </td>
                <td>
                    <div class="date-info">
                        <div class="date">${feedback.createdAt ? this.formatDate(feedback.createdAt) : 'N/A'}</div>
                        <div class="time text-xs text-gray-500">${feedback.createdAt ? this.formatTime(feedback.createdAt) : 'N/A'}</div>
                    </div>
                </td>
                <td>
                    <span class="status-badge status-${feedback.isActive ? 'active' : 'inactive'}">
                        ${feedback.isActive ? 'Active' : 'Inactive'}
                    </span>
                </td>
                
            </tr>
        `).join('');
    }

    getTypeIcon(type) {
        const icons = {
            'COMPLAINT': 'fas fa-exclamation-triangle',
            'SUGGESTION': 'fas fa-lightbulb',
            'COMPLIMENT': 'fas fa-heart',
            'BUG_REPORT': 'fas fa-bug',
            'FEATURE_REQUEST': 'fas fa-star',
            'GENERAL_FEEDBACK': 'fas fa-comment',
            'SERVICE_RATING': 'fas fa-star',
            'PRODUCT_RATING': 'fas fa-star'
        };
        return `<i class="${icons[type] || 'fas fa-comment'}"></i>`;
    }

    renderStars(rating) {
        let stars = '';
        for (let i = 1; i <= 5; i++) {
            if (i <= rating) {
                stars += '<i class="star fas fa-star"></i>';
            } else {
                stars += '<i class="star empty fas fa-star"></i>';
            }
        }
        return stars;
    }

    formatDate(dateString) {
        const date = new Date(dateString);
        return date.toLocaleDateString('en-US', {
            year: 'numeric',
            month: 'short',
            day: 'numeric'
        });
    }

    formatTime(dateString) {
        const date = new Date(dateString);
        return date.toLocaleTimeString('en-US', {
            hour: '2-digit',
            minute: '2-digit'
        });
    }

    async viewFeedback(feedbackId) {
        try {
            const response = await fetch(`/api/feedbacks/${feedbackId}/detailed`);
            if (response.ok) {
                const feedback = await response.json();
                this.showFeedbackDetails(feedback);
            } else {
                this.showNotification('Failed to load feedback details', 'error');
            }
        } catch (error) {
            console.error('Error loading feedback details:', error);
            this.showNotification('Error loading feedback details', 'error');
        }
    }

    showFeedbackDetails(feedback) {
        const modal = document.getElementById('detailsModal');
        const content = document.getElementById('feedbackDetailContent');
        
        if (!modal || !content) return;

        content.innerHTML = `
            <div class="detail-section">
                <h4><i class="fas fa-info-circle"></i> Basic Information</h4>
                <div class="detail-meta">
                    <div class="meta-item">
                        <span class="meta-label">Feedback ID</span>
                        <span class="meta-value">${feedback.feedbackId || 'N/A'}</span>
                    </div>
                    <div class="meta-item">
                        <span class="meta-label">Type</span>
                        <span class="meta-value">
                            <span class="type-badge type-${(feedback.feedbackType || 'GENERAL_FEEDBACK').toLowerCase()}">
                                ${this.getTypeIcon(feedback.feedbackType)}
                                ${(feedback.feedbackType || 'GENERAL_FEEDBACK').replace('_', ' ')}
                            </span>
                        </span>
                    </div>
                    <div class="meta-item">
                        <span class="meta-label">Rating</span>
                        <span class="meta-value">
                            <div class="rating-stars">
                                ${this.renderStars(feedback.rating || 0)}
                                <span class="rating-text ml-1">(${feedback.rating || 0}/5)</span>
                            </div>
                        </span>
                    </div>
                    <div class="meta-item">
                        <span class="meta-label">Created</span>
                        <span class="meta-value">${feedback.createdAt ? `${this.formatDate(feedback.createdAt)} at ${this.formatTime(feedback.createdAt)}` : 'N/A'}</span>
                    </div>
                </div>
            </div>

            <div class="detail-section">
                <h4><i class="fas fa-user"></i> User Information</h4>
                <div class="detail-meta">
                    <div class="meta-item">
                        <span class="meta-label">Name</span>
                        <span class="meta-value">${feedback.userInfo ? `${feedback.userInfo.firstName || ''} ${feedback.userInfo.lastName || ''}` : 'Unknown User'}</span>
                    </div>
                    <div class="meta-item">
                        <span class="meta-label">User ID</span>
                        <span class="meta-value">${feedback.userInfo ? feedback.userInfo.userId : 'N/A'}</span>
                    </div>
                    <div class="meta-item">
                        <span class="meta-label">Email</span>
                        <span class="meta-value">${feedback.userInfo ? feedback.userInfo.email : 'N/A'}</span>
                    </div>
                    <div class="meta-item">
                        <span class="meta-label">Contact</span>
                        <span class="meta-value">${feedback.userInfo ? feedback.userInfo.contactNumber : 'N/A'}</span>
                    </div>
                </div>
            </div>

            <div class="detail-section">
                <h4><i class="fas fa-comment"></i> Feedback Content</h4>
                <div class="detail-content">
                    <h5 class="font-semibold text-lg mb-2">${feedback.title || 'N/A'}</h5>
                    <p class="text-gray-700 leading-relaxed">${feedback.content || 'N/A'}</p>
                </div>
            </div>
        `;

        this.showModal('detailsModal');
    }

    confirmDelete(feedbackId) {
        this.currentFeedbackId = feedbackId;
        this.showModal('deleteModal');
    }

    async deleteFeedback() {
        if (!this.currentFeedbackId) return;

        const deleteBtn = document.getElementById('confirmDelete');
        const loadingSpinner = document.getElementById('deleteLoadingSpinner');
        
        try {
            // Show loading state
            deleteBtn.classList.add('loading');
            deleteBtn.disabled = true;
            if (loadingSpinner) {
                loadingSpinner.classList.add('show');
            }

            const response = await fetch(`/api/feedbacks/${this.currentFeedbackId}`, {
                method: 'DELETE'
            });

            if (response.ok) {
                this.showNotification('Feedback deleted successfully', 'success');
                this.closeModal('deleteModal');
                this.loadFeedbacks(); // Reload feedbacks
            } else {
                const errorData = await response.json();
                this.showNotification(errorData.message || 'Failed to delete feedback', 'error');
            }
        } catch (error) {
            console.error('Error deleting feedback:', error);
            this.showNotification('Error deleting feedback', 'error');
        } finally {
            // Hide loading state
            deleteBtn.classList.remove('loading');
            deleteBtn.disabled = false;
            if (loadingSpinner) {
                loadingSpinner.classList.remove('show');
            }
        }
    }

    updateStatistics() {
        const totalFeedbacks = this.feedbacks.length;
        const complaintsCount = this.feedbacks.filter(f => f.feedbackType === 'COMPLAINT').length;
        const suggestionsCount = this.feedbacks.filter(f => f.feedbackType === 'SUGGESTION').length;
        
        // Calculate average rating
        const totalRating = this.feedbacks.reduce((sum, f) => sum + f.rating, 0);
        const averageRating = totalFeedbacks > 0 ? (totalRating / totalFeedbacks).toFixed(1) : '0.0';

        // Update DOM elements
        const totalElement = document.getElementById('totalFeedbacks');
        const averageElement = document.getElementById('averageRating');
        const complaintsElement = document.getElementById('complaintsCount');
        const suggestionsElement = document.getElementById('suggestionsCount');

        if (totalElement) totalElement.textContent = totalFeedbacks;
        if (averageElement) averageElement.textContent = averageRating;
        if (complaintsElement) complaintsElement.textContent = complaintsCount;
        if (suggestionsElement) suggestionsElement.textContent = suggestionsCount;
    }

    exportToPDF() {
        try {
            const { jsPDF } = window.jspdf;
            const doc = new jsPDF();

            // Add title
            doc.setFontSize(20);
            doc.text('Feedback Management Report', 20, 20);

            // Add date
            doc.setFontSize(10);
            doc.text(`Generated on: ${new Date().toLocaleDateString()}`, 20, 30);

            // Prepare table data
            const tableData = this.filteredFeedbacks.map(feedback => [
                feedback.feedbackId || 'N/A',
                feedback.userInfo ? `${feedback.userInfo.firstName || ''} ${feedback.userInfo.lastName || ''}` : 'Unknown',
                feedback.title || 'N/A',
                (feedback.feedbackType || 'GENERAL_FEEDBACK').replace('_', ' '),
                (feedback.rating || 0).toString(),
                feedback.createdAt ? this.formatDate(feedback.createdAt) : 'N/A'
            ]);

            // Add table
            doc.autoTable({
                head: [['ID', 'User', 'Title', 'Type', 'Rating', 'Date']],
                body: tableData,
                startY: 40,
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
            doc.save('feedback-management-report.pdf');
            this.showNotification('PDF exported successfully', 'success');
        } catch (error) {
            console.error('Error exporting PDF:', error);
            this.showNotification('Error exporting PDF', 'error');
        }
    }

    showModal(modalId) {
        const modal = document.getElementById(modalId);
        if (modal) {
            modal.classList.add('show');
            document.body.style.overflow = 'hidden';
        }
    }

    closeModal(modalId) {
        const modal = document.getElementById(modalId);
        if (modal) {
            modal.classList.remove('show');
            document.body.style.overflow = '';
        }
    }

    showLoading(show) {
        const tbody = document.getElementById('feedbackTableBody');
        const noDataMessage = document.getElementById('noDataMessage');
        
        if (show) {
            if (tbody) {
                tbody.innerHTML = `
                    <tr class="loading-row">
                        <td colspan="8" class="loading-cell">
                            <div class="loading-spinner">
                                <i class="fas fa-spinner fa-spin"></i>
                                <span>Loading feedbacks...</span>
                            </div>
                        </td>
                    </tr>
                `;
            }
            if (noDataMessage) {
                noDataMessage.style.display = 'none';
            }
        }
    }

    showNotification(message, type = 'info') {
        const container = document.getElementById('notificationContainer');
        if (!container) return;

        const notification = document.createElement('div');
        notification.className = `notification ${type}`;
        
        const icon = this.getNotificationIcon(type);
        notification.innerHTML = `
            <i class="${icon}"></i>
            <span>${message}</span>
        `;

        container.appendChild(notification);

        // Trigger animation
        setTimeout(() => {
            notification.classList.add('slide-in');
        }, 100);

        // Auto remove after 5 seconds
        setTimeout(() => {
            notification.classList.add('slide-out');
            setTimeout(() => {
                if (container.contains(notification)) {
                    container.removeChild(notification);
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

// Initialize the admin feedback manager when the page loads
let adminFeedbackManager;
document.addEventListener('DOMContentLoaded', () => {
    adminFeedbackManager = new AdminFeedbackManager();
});

// Global functions for onclick handlers
window.adminFeedbackManager = adminFeedbackManager;
