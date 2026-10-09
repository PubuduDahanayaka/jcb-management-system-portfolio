// Profile Management JavaScript
class ProfileManager {
    constructor() {
        this.currentUser = null;
        this.editModal = document.getElementById('editModal');
        this.confirmModal = document.getElementById('confirmModal');
        this.notificationContainer = document.getElementById('notificationContainer');
        
        this.initializeEventListeners();
        this.loadUserProfile();
    }
    
    initializeEventListeners() {
        // Edit Profile
        document.getElementById('editProfileBtn').addEventListener('click', () => this.openEditModal());
        document.getElementById('closeEditModal').addEventListener('click', () => this.closeEditModal());
        document.getElementById('cancelEdit').addEventListener('click', () => this.closeEditModal());
        document.getElementById('editProfileForm').addEventListener('submit', (e) => this.handleProfileUpdate(e));
        
        // Delete Account
        document.getElementById('deleteAccountBtn').addEventListener('click', () => this.confirmDeleteAccount());
        
        // Logout
        document.getElementById('logoutBtn').addEventListener('click', () => this.confirmLogout());
        
        // Confirmation Modal
        document.getElementById('confirmCancel').addEventListener('click', () => this.closeConfirmModal());
        document.getElementById('confirmAction').addEventListener('click', () => this.executeConfirmedAction());
        
        // Clear errors on input
        document.getElementById('editFirstName').addEventListener('input', () => this.clearError('editFirstNameError'));
        document.getElementById('editLastName').addEventListener('input', () => this.clearError('editLastNameError'));
        document.getElementById('editEmail').addEventListener('input', () => this.clearError('editEmailError'));
        document.getElementById('editContactNumber').addEventListener('input', () => this.clearError('editContactNumberError'));
        
        // Close modals on overlay click
        this.editModal.addEventListener('click', (e) => {
            if (e.target === this.editModal) this.closeEditModal();
        });
        
        this.confirmModal.addEventListener('click', (e) => {
            if (e.target === this.confirmModal) this.closeConfirmModal();
        });
    }
    
    async loadUserProfile() {
        try {
            // Get user ID from session storage or URL params
            const userId = this.getCurrentUserId();
            if (!userId) {
                this.showNotification('User session not found. Please login again.', 'error');
                setTimeout(() => {
                    window.location.href = '/login';
                }, 2000);
                return;
            }
            
            const response = await fetch(`/api/users/${userId}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                }
            });
            
            if (response.ok) {
                this.currentUser = await response.json();
                this.displayUserProfile();
            } else {
                this.showNotification('Failed to load profile. Please try again.', 'error');
            }
        } catch (error) {
            console.error('Error loading profile:', error);
            this.showNotification('Network error. Please check your connection.', 'error');
        }
    }
    
    getCurrentUserId() {
        // Try to get from session storage first
        const sessionUserId = sessionStorage.getItem('userId');
        
        if (sessionUserId) return sessionUserId;
        
        // Try to get from URL parameters
        const urlParams = new URLSearchParams(window.location.search);
        const urlUserId = urlParams.get('userId');
        if (urlUserId) return urlUserId;
        
        // Try to get from localStorage as fallback
        const localUserId = localStorage.getItem('userId');
        if (localUserId) return localUserId;
        
        return null;
    }
    
    displayUserProfile() {
        if (!this.currentUser) return;
        
        // Update header
        document.getElementById('userName').textContent = `${this.currentUser.firstName} ${this.currentUser.lastName}`;
        
        // Update profile header card
        document.getElementById('profileName').textContent = `${this.currentUser.firstName} ${this.currentUser.lastName}`;
        document.getElementById('profileEmail').textContent = this.currentUser.email;
        document.getElementById('profileId').textContent = this.currentUser.userId;
        
        // Update detail cards
        document.getElementById('detailUserId').textContent = this.currentUser.userId;
        document.getElementById('detailFirstName').textContent = this.currentUser.firstName;
        document.getElementById('detailLastName').textContent = this.currentUser.lastName;
        document.getElementById('detailEmail').textContent = this.currentUser.email;
        document.getElementById('detailContactNumber').textContent = this.currentUser.contactNumber;
        document.getElementById('detailUserType').textContent = this.currentUser.userType;
        
        // Add fade-in animation
        document.querySelector('.profile-content').classList.add('fade-in');
    }
    
    openEditModal() {
        if (!this.currentUser) return;
        
        // Populate form with current data
        document.getElementById('editFirstName').value = this.currentUser.firstName;
        document.getElementById('editLastName').value = this.currentUser.lastName;
        document.getElementById('editEmail').value = this.currentUser.email;
        document.getElementById('editContactNumber').value = this.currentUser.contactNumber;
        
        // Clear any previous errors
        this.clearAllErrors();
        
        this.editModal.classList.add('show');
        document.getElementById('editFirstName').focus();
    }
    
    closeEditModal() {
        this.editModal.classList.remove('show');
        this.clearAllErrors();
    }
    
    async handleProfileUpdate(event) {
        event.preventDefault();
        
        if (!this.validateEditForm()) {
            return;
        }
        
        const saveButton = document.getElementById('saveProfile');
        this.setLoadingState(saveButton, true);
        
        try {
            const formData = new FormData(event.target);
            const updateData = {
                firstName: formData.get('firstName').trim(),
                lastName: formData.get('lastName').trim(),
                email: formData.get('email').trim(),
                contactNumber: formData.get('contactNumber').trim()
            };
            
            const response = await fetch(`/api/users/${this.currentUser.userId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(updateData)
            });
            
            const result = await response.json();
            
            if (response.ok) {
                this.showNotification('Profile updated successfully!', 'success');
                this.closeEditModal();
                this.loadUserProfile(); // Reload profile data
            } else {
                this.showNotification(result.message || 'Failed to update profile', 'error');
            }
        } catch (error) {
            console.error('Profile update error:', error);
            this.showNotification('Network error. Please check your connection.', 'error');
        } finally {
            this.setLoadingState(saveButton, false);
        }
    }
    
    validateEditForm() {
        let isValid = true;
        
        if (!this.validateFirstName()) isValid = false;
        if (!this.validateLastName()) isValid = false;
        if (!this.validateEmail()) isValid = false;
        if (!this.validateContactNumber()) isValid = false;
        
        return isValid;
    }
    
    validateFirstName() {
        const firstName = document.getElementById('editFirstName').value.trim();
        const firstNameRegex = /^[a-zA-Z\s]{2,50}$/;
        
        if (!firstName) {
            this.showFieldError('editFirstNameError', 'First name is required');
            return false;
        }
        
        if (!firstNameRegex.test(firstName)) {
            this.showFieldError('editFirstNameError', 'First name must be 2-50 characters and contain only letters');
            return false;
        }
        
        this.clearError('editFirstNameError');
        return true;
    }
    
    validateLastName() {
        const lastName = document.getElementById('editLastName').value.trim();
        const lastNameRegex = /^[a-zA-Z\s]{2,50}$/;
        
        if (!lastName) {
            this.showFieldError('editLastNameError', 'Last name is required');
            return false;
        }
        
        if (!lastNameRegex.test(lastName)) {
            this.showFieldError('editLastNameError', 'Last name must be 2-50 characters and contain only letters');
            return false;
        }
        
        this.clearError('editLastNameError');
        return true;
    }
    
    validateEmail() {
        const email = document.getElementById('editEmail').value.trim();
        const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        
        if (!email) {
            this.showFieldError('editEmailError', 'Email address is required');
            return false;
        }
        
        if (!emailRegex.test(email)) {
            this.showFieldError('editEmailError', 'Please enter a valid email address');
            return false;
        }
        
        this.clearError('editEmailError');
        return true;
    }
    
    validateContactNumber() {
        const contactNumber = document.getElementById('editContactNumber').value.trim();
        const phoneRegex = /^[\+]?[0-9\s\-\(\)]{10,15}$/;
        
        if (!contactNumber) {
            this.showFieldError('editContactNumberError', 'Contact number is required');
            return false;
        }
        
        if (!phoneRegex.test(contactNumber)) {
            this.showFieldError('editContactNumberError', 'Please enter a valid contact number (10-15 digits)');
            return false;
        }
        
        this.clearError('editContactNumberError');
        return true;
    }
    
    confirmDeleteAccount() {
        this.showConfirmModal(
            'Delete Account',
            'Are you sure you want to delete your account? This action cannot be undone and all your data will be permanently removed.',
            'delete',
            () => this.deleteAccount()
        );
    }
    
    confirmLogout() {
        this.showConfirmModal(
            'Logout',
            'Are you sure you want to logout? You will need to login again to access your account.',
            'logout',
            () => this.logout()
        );
    }
    
    showConfirmModal(title, message, type, action) {
        document.getElementById('confirmTitle').innerHTML = `
            <i class="fas fa-exclamation-triangle"></i>
            ${title}
        `;
        document.getElementById('confirmMessage').textContent = message;
        
        const icon = document.getElementById('confirmIcon');
        const actionButton = document.getElementById('confirmAction');
        
        // Update icon and button styling based on type
        if (type === 'delete') {
            icon.innerHTML = '<i class="fas fa-trash"></i>';
            icon.className = 'confirm-icon delete';
            actionButton.className = 'modal-button danger-button';
            actionButton.innerHTML = `
                <i class="fas fa-trash"></i>
                <span class="button-text">Delete Account</span>
                <div class="loading-spinner" id="confirmLoadingSpinner">
                    <i class="fas fa-spinner fa-spin"></i>
                </div>
            `;
        } else if (type === 'logout') {
            icon.innerHTML = '<i class="fas fa-sign-out-alt"></i>';
            icon.className = 'confirm-icon logout';
            actionButton.className = 'modal-button logout-button';
            actionButton.innerHTML = `
                <i class="fas fa-sign-out-alt"></i>
                <span class="button-text">Logout</span>
                <div class="loading-spinner" id="confirmLoadingSpinner">
                    <i class="fas fa-spinner fa-spin"></i>
                </div>
            `;
        }
        
        // Store the action to execute
        this.pendingAction = action;
        
        this.confirmModal.classList.add('show');
    }
    
    closeConfirmModal() {
        this.confirmModal.classList.remove('show');
        this.pendingAction = null;
    }
    
    executeConfirmedAction() {
        if (this.pendingAction) {
            this.pendingAction();
        }
    }
    
    async deleteAccount() {
        const actionButton = document.getElementById('confirmAction');
        this.setLoadingState(actionButton, true);
        
        try {
            const response = await fetch(`/api/users/${this.currentUser.userId}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                }
            });
            
            if (response.ok) {
                this.showNotification('Account deleted successfully. Redirecting to login...', 'success');
                this.clearUserSession();
                setTimeout(() => {
                    window.location.href = '/login';
                }, 2000);
            } else {
                const result = await response.json();
                this.showNotification(result.message || 'Failed to delete account', 'error');
            }
        } catch (error) {
            console.error('Delete account error:', error);
            this.showNotification('Network error. Please check your connection.', 'error');
        } finally {
            this.setLoadingState(actionButton, false);
            this.closeConfirmModal();
        }
    }
    
    logout() {
        const actionButton = document.getElementById('confirmAction');
        this.setLoadingState(actionButton, true);
        
        // Simulate logout process
        setTimeout(() => {
            this.showNotification('Logged out successfully. Redirecting to login...', 'success');
            this.clearUserSession();
            setTimeout(() => {
                window.location.href = '/login';
            }, 1500);
        }, 1000);
    }
    
    clearUserSession() {
        // Clear all session data
        sessionStorage.clear();
        localStorage.removeItem('userId');
        localStorage.removeItem('userType');
        localStorage.removeItem('userName');
    }
    
    showFieldError(errorElementId, message) {
        const errorElement = document.getElementById(errorElementId);
        errorElement.textContent = message;
        errorElement.classList.add('show');
        
        const inputElement = errorElement.previousElementSibling;
        if (inputElement.classList.contains('form-input')) {
            inputElement.classList.add('error');
        }
    }
    
    clearError(errorElementId) {
        const errorElement = document.getElementById(errorElementId);
        errorElement.classList.remove('show');
        
        const inputElement = errorElement.previousElementSibling;
        if (inputElement.classList.contains('form-input')) {
            inputElement.classList.remove('error');
        }
    }
    
    clearAllErrors() {
        const errorElements = document.querySelectorAll('.error-message');
        errorElements.forEach(element => {
            element.classList.remove('show');
        });
        
        const inputElements = document.querySelectorAll('.form-input');
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
}

// Initialize profile manager when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    new ProfileManager();
});
