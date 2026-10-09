// Feedback Management JavaScript
class FeedbackManager {
    constructor() {
        this.currentUser = null;
        this.feedbacks = [];
        this.currentFeedbackId = null;
        
        this.createModal = document.getElementById('createModal');
        this.editModal = document.getElementById('editModal');
        this.deleteModal = document.getElementById('deleteModal');
        this.detailModal = document.getElementById('detailModal');
        this.notificationContainer = document.getElementById('notificationContainer');
        
        this.initializeEventListeners();
        this.loadInitialData();
    }
    
    initializeEventListeners() {
        // Create Feedback
        document.getElementById('addFeedbackBtn').addEventListener('click', () => this.openCreateModal());
        document.getElementById('createFirstFeedbackBtn').addEventListener('click', () => this.openCreateModal());
        document.getElementById('closeCreateModal').addEventListener('click', () => this.closeCreateModal());
        document.getElementById('cancelCreate').addEventListener('click', () => this.closeCreateModal());
        document.getElementById('createFeedbackForm').addEventListener('submit', (e) => this.handleCreateFeedback(e));
        
        // Edit Feedback
        document.getElementById('closeEditModal').addEventListener('click', () => this.closeEditModal());
        document.getElementById('cancelEdit').addEventListener('click', () => this.closeEditModal());
        document.getElementById('editFeedbackForm').addEventListener('submit', (e) => this.handleUpdateFeedback(e));
        
        // Delete Feedback
        document.getElementById('cancelDelete').addEventListener('click', () => this.closeDeleteModal());
        document.getElementById('confirmDelete').addEventListener('click', () => this.handleDeleteFeedback());
        
        // Detail Modal
        document.getElementById('closeDetailModal').addEventListener('click', () => this.closeDetailModal());
        document.getElementById('closeDetailBtn').addEventListener('click', () => this.closeDetailModal());
        
        // Search and Filter
        document.getElementById('searchInput').addEventListener('input', () => this.filterFeedbacks());
        document.getElementById('typeFilter').addEventListener('change', () => this.filterFeedbacks());
        document.getElementById('ratingFilter').addEventListener('change', () => this.filterFeedbacks());
        
        // Export PDF
        document.getElementById('exportPdfBtn').addEventListener('click', () => this.exportToPDF());
        
        // Character counters
        document.getElementById('createTitle').addEventListener('input', () => this.updateCharCounter('createTitleCounter', 'createTitle', 100));
        document.getElementById('createContent').addEventListener('input', () => this.updateCharCounter('createContentCounter', 'createContent', 1000));
        document.getElementById('editTitle').addEventListener('input', () => this.updateCharCounter('editTitleCounter', 'editTitle', 100));
        document.getElementById('editContent').addEventListener('input', () => this.updateCharCounter('editContentCounter', 'editContent', 1000));
        
        // Star ratings
        this.initializeStarRatings('create');
        this.initializeStarRatings('edit');
        
        // Clear errors on input
        document.getElementById('createTitle').addEventListener('input', () => this.clearError('createTitleError'));
        document.getElementById('createFeedbackType').addEventListener('change', () => this.clearError('createFeedbackTypeError'));
        document.getElementById('createContent').addEventListener('input', () => this.clearError('createContentError'));
        document.getElementById('editTitle').addEventListener('input', () => this.clearError('editTitleError'));
        document.getElementById('editFeedbackType').addEventListener('change', () => this.clearError('editFeedbackTypeError'));
        document.getElementById('editContent').addEventListener('input', () => this.clearError('editContentError'));
        
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
        
        this.detailModal.addEventListener('click', (e) => {
            if (e.target === this.detailModal) this.closeDetailModal();
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
            
            // Load user's feedback
            await this.loadFeedbacks(userId);
            
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
            }
        } catch (error) {
            console.error('Error loading user info:', error);
        }
    }
    
    async loadFeedbacks(userId) {
        try {
            const response = await fetch(`/api/feedbacks/user/${userId}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                }
            });
            
            if (response.ok) {
                this.feedbacks = await response.json();
                console.table(this.feedbacks);
                this.displayFeedbacks();
            } else {
                this.showNotification('Failed to load feedback.', 'error');
            }
        } catch (error) {
            console.error('Error loading feedbacks:', error);
            this.showNotification('Network error. Please check your connection.', 'error');
        }
    }
    
    displayFeedbacks() {
        const tbody = document.getElementById('feedbackTableBody');
        const noDataMessage = document.getElementById('noDataMessage');
        
        if (this.feedbacks.length === 0) {
            tbody.innerHTML = '';
            noDataMessage.style.display = 'block';
            return;
        }
        
        noDataMessage.style.display = 'none';
        
        tbody.innerHTML = this.feedbacks.map(feedback => {
            const canEdit = this.canUserEditFeedback(feedback);
            
            return `
                <tr>
                    <td>${feedback.feedbackId || 'N/A'}</td>
                    <td>${feedback.title}</td>
                    <td><span class="type-badge type-${feedback.feedbackType.toLowerCase()}">${this.formatFeedbackType(feedback.feedbackType)}</span></td>
                    <td>${this.renderStarRating(feedback.rating)}</td>
                    <td>${this.formatDateTime(feedback.createdDate)}</td>
                    <td><span class="status-badge status-${feedback.isActive ? 'active' : 'inactive'}">${feedback.isActive ? 'Active' : 'Inactive'}</span></td>
                    <td>
                        <div class="action-buttons">
                            <button class="btn-small btn-view" onclick="feedbackManager.viewFeedbackDetails('${feedback.feedbackId}')" title="View Details">
                                <i class="fas fa-eye"></i>
                            </button>
                            <button class="btn-small btn-edit ${!canEdit ? 'btn-disabled' : ''}" 
                                    onclick="feedbackManager.openEditModal('${feedback.feedbackId}')" 
                                    ${!canEdit ? 'disabled title="You can only edit your own feedback"' : ''}>
                                <i class="fas fa-edit"></i>
                            </button>
                            <button class="btn-small btn-delete ${!canEdit ? 'btn-disabled' : ''}" 
                                    onclick="feedbackManager.confirmDeleteFeedback('${feedback.feedbackId}')" 
                                    ${!canEdit ? 'disabled title="You can only delete your own feedback"' : ''}>
                                <i class="fas fa-trash"></i>
                            </button>
                        </div>
                    </td>
                </tr>
            `;
        }).join('');
        
        // Add fade-in animation
        document.querySelector('.table-section').classList.add('fade-in');
    }
    
    canUserEditFeedback(feedback) {
        // Only allow editing if the feedback belongs to the current user
        return feedback.userId === this.currentUser.userId;
    }
    
    formatFeedbackType(type) {
        return type.replace(/_/g, ' ').replace(/\b\w/g, l => l.toUpperCase());
    }
    
    renderStarRating(rating) {
        if (!rating) return '<span class="no-rating">No rating</span>';
        
        let stars = '';
        for (let i = 1; i <= 5; i++) {
            if (i <= rating) {
                stars += '<i class="fas fa-star" style="color: #eab308;"></i>';
            } else {
                stars += '<i class="fas fa-star" style="color: #d1d5db;"></i>';
            }
        }
        return `<div class="inline-rating">${stars} <span class="rating-number">(${rating})</span></div>`;
    }
    
    filterFeedbacks() {
        const searchTerm = document.getElementById('searchInput').value.toLowerCase();
        const typeFilter = document.getElementById('typeFilter').value;
        const ratingFilter = document.getElementById('ratingFilter').value;
        
        const filteredFeedbacks = this.feedbacks.filter(feedback => {
            const matchesSearch = !searchTerm || 
                feedback.title?.toLowerCase().includes(searchTerm) ||
                feedback.content?.toLowerCase().includes(searchTerm) ||
                feedback.feedbackType?.toLowerCase().includes(searchTerm);
            
            const matchesType = !typeFilter || feedback.feedbackType === typeFilter;
            const matchesRating = !ratingFilter || feedback.rating == ratingFilter;
            
            return matchesSearch && matchesType && matchesRating;
        });
        
        this.displayFilteredFeedbacks(filteredFeedbacks);
    }
    
    displayFilteredFeedbacks(filteredFeedbacks) {
        const tbody = document.getElementById('feedbackTableBody');
        const noDataMessage = document.getElementById('noDataMessage');
        
        if (filteredFeedbacks.length === 0) {
            tbody.innerHTML = `
                <tr>
                    <td colspan="7" class="loading-cell">
                        <div class="loading-spinner">
                            <i class="fas fa-search"></i>
                            <span>No feedback matches your search criteria</span>
                        </div>
                    </td>
                </tr>
            `;
            noDataMessage.style.display = 'none';
            return;
        }
        
        noDataMessage.style.display = 'none';
        
        tbody.innerHTML = filteredFeedbacks.map(feedback => {
            const canEdit = this.canUserEditFeedback(feedback);
            
            return `
                <tr>
                    <td>${feedback.feedbackId || 'N/A'}</td>
                    <td>${feedback.title}</td>
                    <td><span class="type-badge type-${feedback.feedbackType.toLowerCase()}">${this.formatFeedbackType(feedback.feedbackType)}</span></td>
                    <td>${this.renderStarRating(feedback.rating)}</td>
                    <td>${this.formatDateTime(feedback.createdDate)}</td>
                    <td><span class="status-badge status-${feedback.isActive ? 'active' : 'inactive'}">${feedback.isActive ? 'Active' : 'Inactive'}</span></td>
                    <td>
                        <div class="action-buttons">
                            <button class="btn-small btn-view" onclick="feedbackManager.viewFeedbackDetails('${feedback.feedbackId}')" title="View Details">
                                <i class="fas fa-eye"></i>
                            </button>
                            <button class="btn-small btn-edit ${!canEdit ? 'btn-disabled' : ''}" 
                                    onclick="feedbackManager.openEditModal('${feedback.feedbackId}')" 
                                    ${!canEdit ? 'disabled title="You can only edit your own feedback"' : ''}>
                                <i class="fas fa-edit"></i>
                            </button>
                            <button class="btn-small btn-delete ${!canEdit ? 'btn-disabled' : ''}" 
                                    onclick="feedbackManager.confirmDeleteFeedback('${feedback.feedbackId}')" 
                                    ${!canEdit ? 'disabled title="You can only delete your own feedback"' : ''}>
                                <i class="fas fa-trash"></i>
                            </button>
                        </div>
                    </td>
                </tr>
            `;
        }).join('');
    }
    
    initializeStarRatings(prefix) {
        const starRating = document.getElementById(`${prefix}StarRating`);
        const ratingText = document.getElementById(`${prefix}RatingText`);
        
        if (!starRating || !ratingText) return;
        
        const inputs = starRating.querySelectorAll('input[type="radio"]');
        const labels = starRating.querySelectorAll('label');
        
        inputs.forEach((input, index) => {
            input.addEventListener('change', () => {
                const rating = parseInt(input.value);
                ratingText.textContent = `${rating} star${rating > 1 ? 's' : ''}`;
            });
        });
        
        labels.forEach((label, index) => {
            label.addEventListener('mouseenter', () => {
                const rating = 5 - index;
                ratingText.textContent = `${rating} star${rating > 1 ? 's' : ''}`;
            });
        });
        
        starRating.addEventListener('mouseleave', () => {
            const checkedInput = starRating.querySelector('input:checked');
            if (checkedInput) {
                const rating = parseInt(checkedInput.value);
                ratingText.textContent = `${rating} star${rating > 1 ? 's' : ''}`;
            } else {
                ratingText.textContent = 'No rating selected';
            }
        });
    }
    
    openCreateModal() {
        this.clearAllErrors();
        this.createModal.classList.add('show');
        document.getElementById('createTitle').focus();
    }
    
    closeCreateModal() {
        this.createModal.classList.remove('show');
        document.getElementById('createFeedbackForm').reset();
        this.clearAllErrors();
        this.updateCharCounter('createTitleCounter', 'createTitle', 100);
        this.updateCharCounter('createContentCounter', 'createContent', 1000);
        document.getElementById('createRatingText').textContent = 'No rating selected';
    }
    
    async handleCreateFeedback(event) {
        event.preventDefault();
        
        if (!this.validateCreateForm()) {
            return;
        }
        
        const saveButton = document.getElementById('saveFeedback');
        this.setLoadingState(saveButton, true);
        
        try {
            const formData = new FormData(event.target);
            const feedbackData = {
                userId: this.currentUser.userId,
                title: formData.get('title').trim(),
                content: formData.get('content').trim(),
                feedbackType: formData.get('feedbackType'),
                rating: formData.get('rating') ? parseInt(formData.get('rating')) : null,
                isAnonymous: formData.get('isAnonymous') === 'on'
            };
            
            const response = await fetch('/api/feedbacks', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(feedbackData)
            });
            
            const result = await response.json();
            
            if (response.ok) {
                this.showNotification('Feedback submitted successfully!', 'success');
                this.closeCreateModal();
                await this.loadFeedbacks(this.currentUser.userId);
            } else {
                this.showNotification(result.message || 'Failed to submit feedback', 'error');
            }
        } catch (error) {
            console.error('Create feedback error:', error);
            this.showNotification('Network error. Please check your connection.', 'error');
        } finally {
            this.setLoadingState(saveButton, false);
        }
    }
    
    validateCreateForm() {
        let isValid = true;
        
        if (!this.validateTitle('create')) isValid = false;
        if (!this.validateFeedbackType('create')) isValid = false;
        if (!this.validateContent('create')) isValid = false;
        
        return isValid;
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
    
    validateFeedbackType(formType) {
        const feedbackType = document.getElementById(`${formType}FeedbackType`).value;
        
        if (!feedbackType) {
            this.showFieldError(`${formType}FeedbackTypeError`, 'Please select a feedback type');
            return false;
        }
        
        this.clearError(`${formType}FeedbackTypeError`);
        return true;
    }
    
    validateContent(formType) {
        const content = document.getElementById(`${formType}Content`).value.trim();
        const contentRegex = /^.{10,1000}$/;
        
        if (!content) {
            this.showFieldError(`${formType}ContentError`, 'Content is required');
            return false;
        }
        
        if (!contentRegex.test(content)) {
            this.showFieldError(`${formType}ContentError`, 'Content must be between 10 and 1000 characters');
            return false;
        }
        
        this.clearError(`${formType}ContentError`);
        return true;
    }
    
    openEditModal(feedbackId) {
        const feedback = this.feedbacks.find(f => f.feedbackId == feedbackId);
        if (!feedback) {
            this.showNotification('Feedback not found.', 'error');
            return;
        }
        
        if (!this.canUserEditFeedback(feedback)) {
            this.showNotification('You can only edit your own feedback.', 'warning');
            return;
        }
        
        // Populate form with feedback data
        document.getElementById('editFeedbackId').value = feedback.feedbackId;
        document.getElementById('editTitle').value = feedback.title;
        document.getElementById('editFeedbackType').value = feedback.feedbackType;
        document.getElementById('editContent').value = feedback.content;
        document.getElementById('editCreatedDate').value = this.formatDateTime(feedback.createdDate);
        document.getElementById('editIsAnonymous').checked = feedback.isAnonymous || false;
        
        // Set rating
        if (feedback.rating) {
            document.getElementById(`edit-star${feedback.rating}`).checked = true;
            document.getElementById('editRatingText').textContent = `${feedback.rating} star${feedback.rating > 1 ? 's' : ''}`;
        }
        
        // Show admin response if available
        if (feedback.adminResponse) {
            document.getElementById('editAdminResponse').value = feedback.adminResponse;
            document.getElementById('adminResponseGroup').style.display = 'block';
        } else {
            document.getElementById('adminResponseGroup').style.display = 'none';
        }
        
        this.clearAllErrors();
        this.updateCharCounter('editTitleCounter', 'editTitle', 100);
        this.updateCharCounter('editContentCounter', 'editContent', 1000);
        this.editModal.classList.add('show');
    }
    
    closeEditModal() {
        this.editModal.classList.remove('show');
        this.clearAllErrors();
    }
    
    async handleUpdateFeedback(event) {
        event.preventDefault();
        
        if (!this.validateEditForm()) {
            return;
        }
        
        const saveButton = document.getElementById('updateFeedback');
        this.setLoadingState(saveButton, true);
        
        try {
            const formData = new FormData(event.target);
            const feedbackId = document.getElementById('editFeedbackId').value;
            
            const updateData = {
                title: formData.get('title').trim(),
                content: formData.get('content').trim(),
                feedbackType: formData.get('feedbackType'),
                rating: formData.get('rating') ? parseInt(formData.get('rating')) : null,
                isAnonymous: formData.get('isAnonymous') === 'on'
            };
            
            const response = await fetch(`/api/feedbacks/${feedbackId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(updateData)
            });
            
            const result = await response.json();
            
            if (response.ok) {
                this.showNotification('Feedback updated successfully!', 'success');
                this.closeEditModal();
                await this.loadFeedbacks(this.currentUser.userId);
            } else {
                this.showNotification(result.message || 'Failed to update feedback', 'error');
            }
        } catch (error) {
            console.error('Update feedback error:', error);
            this.showNotification('Network error. Please check your connection.', 'error');
        } finally {
            this.setLoadingState(saveButton, false);
        }
    }
    
    validateEditForm() {
        let isValid = true;
        
        if (!this.validateTitle('edit')) isValid = false;
        if (!this.validateFeedbackType('edit')) isValid = false;
        if (!this.validateContent('edit')) isValid = false;
        
        return isValid;
    }
    
    viewFeedbackDetails(feedbackId) {
        const feedback = this.feedbacks.find(f => f.feedbackId == feedbackId);
        if (!feedback) {
            this.showNotification('Feedback not found.', 'error');
            return;
        }
        
        const detailContent = document.getElementById('feedbackDetailContent');
        detailContent.innerHTML = `
            <div class="detail-section">
                <h4><i class="fas fa-info-circle"></i> Basic Information</h4>
                <div class="detail-meta">
                    <div class="meta-item">
                        <span class="meta-label">Feedback ID</span>
                        <span class="meta-value">${feedback.feedbackId || 'N/A'}</span>
                    </div>
                    <div class="meta-item">
                        <span class="meta-label">Type</span>
                        <span class="meta-value"><span class="type-badge type-${feedback.feedbackType.toLowerCase()}">${this.formatFeedbackType(feedback.feedbackType)}</span></span>
                    </div>
                    <div class="meta-item">
                        <span class="meta-label">Rating</span>
                        <span class="meta-value">${this.renderStarRating(feedback.rating)}</span>
                    </div>
                    <div class="meta-item">
                        <span class="meta-label">Created Date</span>
                        <span class="meta-value">${this.formatDateTime(feedback.createdDate)}</span>
                    </div>
                    <div class="meta-item">
                        <span class="meta-label">Status</span>
                        <span class="meta-value"><span class="status-badge status-${feedback.isActive ? 'active' : 'inactive'}">${feedback.isActive ? 'Active' : 'Inactive'}</span></span>
                    </div>
                    <div class="meta-item">
                        <span class="meta-label">Anonymous</span>
                        <span class="meta-value">${feedback.isAnonymous ? 'Yes' : 'No'}</span>
                    </div>
                </div>
            </div>
            
            <div class="detail-section">
                <h4><i class="fas fa-heading"></i> Title</h4>
                <div class="detail-content">${feedback.title}</div>
            </div>
            
            <div class="detail-section">
                <h4><i class="fas fa-comment"></i> Content</h4>
                <div class="detail-content">${feedback.content}</div>
            </div>
            
            ${feedback.adminResponse ? `
            <div class="detail-section">
                <h4><i class="fas fa-reply"></i> Admin Response</h4>
                <div class="detail-content">${feedback.adminResponse}</div>
                ${feedback.respondedBy ? `<div class="meta-item"><span class="meta-label">Responded By</span><span class="meta-value">${feedback.respondedBy}</span></div>` : ''}
            </div>
            ` : ''}
        `;
        
        this.detailModal.classList.add('show');
    }
    
    closeDetailModal() {
        this.detailModal.classList.remove('show');
    }
    
    confirmDeleteFeedback(feedbackId) {
        const feedback = this.feedbacks.find(f => f.feedbackId == feedbackId);
        if (!feedback) {
            this.showNotification('Feedback not found.', 'error');
            return;
        }
        
        if (!this.canUserEditFeedback(feedback)) {
            this.showNotification('You can only delete your own feedback.', 'warning');
            return;
        }
        
        this.currentFeedbackId = feedbackId;
        this.deleteModal.classList.add('show');
    }
    
    closeDeleteModal() {
        this.deleteModal.classList.remove('show');
        this.currentFeedbackId = null;
    }
    
    async handleDeleteFeedback() {
        if (!this.currentFeedbackId) return;
        
        const deleteButton = document.getElementById('confirmDelete');
        this.setLoadingState(deleteButton, true);
        
        try {
            const response = await fetch(`/api/feedbacks/${this.currentFeedbackId}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                }
            });
            
            if (response.ok) {
                this.showNotification('Feedback deleted successfully!', 'success');
                this.closeDeleteModal();
                await this.loadFeedbacks(this.currentUser.userId);
            } else {
                const result = await response.json();
                this.showNotification(result.message || 'Failed to delete feedback', 'error');
            }
        } catch (error) {
            console.error('Delete feedback error:', error);
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
        if (this.feedbacks.length === 0) {
            this.showNotification('No feedback to export.', 'warning');
            return;
        }
        
        try {
            const { jsPDF } = window.jspdf;
            const doc = new jsPDF();
            
            // Add title
            doc.setFontSize(20);
            doc.text('My Feedback', 20, 20);
            
            // Add user info
            doc.setFontSize(12);
            doc.text(`Generated for: ${this.currentUser.firstName} ${this.currentUser.lastName}`, 20, 35);
            doc.text(`Generated on: ${new Date().toLocaleDateString()}`, 20, 45);
            
            // Prepare table data
            const tableData = this.feedbacks.map(feedback => [
                feedback.feedbackId || 'N/A',
                feedback.title,
                this.formatFeedbackType(feedback.feedbackType),
                feedback.rating ? `${feedback.rating}/5` : 'No rating',
                this.formatDateTime(feedback.createdDate),
                feedback.isActive ? 'Active' : 'Inactive'
            ]);
            
            // Add table
            doc.autoTable({
                head: [['Feedback ID', 'Title', 'Type', 'Rating', 'Created Date', 'Status']],
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
            doc.save(`feedback-${this.currentUser.userId}-${new Date().toISOString().split('T')[0]}.pdf`);
            
            this.showNotification('PDF exported successfully!', 'success');
        } catch (error) {
            console.error('PDF export error:', error);
            this.showNotification('Failed to export PDF. Please try again.', 'error');
        }
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
    
    formatDateTime(dateString) {
        if (!dateString) return 'N/A';
        const date = new Date(dateString);
        return date.toLocaleDateString() + ' ' + date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
    }
}

// Initialize feedback manager when DOM is loaded
let feedbackManager;
document.addEventListener('DOMContentLoaded', () => {
    feedbackManager = new FeedbackManager();
});
