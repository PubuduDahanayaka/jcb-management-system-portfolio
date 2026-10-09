// Login Page JavaScript
class LoginManager {
    constructor() {
        this.form = document.getElementById('loginForm');
        this.userIdInput = document.getElementById('userId');
        this.passwordInput = document.getElementById('password');
        this.passwordToggle = document.getElementById('passwordToggle');
        this.loginButton = document.getElementById('loginButton');
        this.loadingSpinner = document.getElementById('loadingSpinner');
        this.notificationContainer = document.getElementById('notificationContainer');
        
        this.initializeEventListeners();
        this.initializeForm();
    }
    
    initializeEventListeners() {
        // Form submission
        this.form.addEventListener('submit', (e) => this.handleLogin(e));
        
        // Password toggle
        this.passwordToggle.addEventListener('click', () => this.togglePasswordVisibility());
        
        // Input validation
        this.userIdInput.addEventListener('blur', () => this.validateUserId());
        this.passwordInput.addEventListener('blur', () => this.validatePassword());
        
        // Real-time validation
        this.userIdInput.addEventListener('input', () => this.clearError('userIdError'));
        this.passwordInput.addEventListener('input', () => this.clearError('passwordError'));
        
        // Enter key handling
        this.userIdInput.addEventListener('keypress', (e) => {
            if (e.key === 'Enter') {
                this.passwordInput.focus();
            }
        });
        
        this.passwordInput.addEventListener('keypress', (e) => {
            if (e.key === 'Enter') {
                this.handleLogin(e);
            }
        });
    }
    
    initializeForm() {
        // Add fade-in animation
        document.querySelector('.login-card').classList.add('fade-in');
        
        // Focus on first input
        setTimeout(() => {
            this.userIdInput.focus();
        }, 500);
        
        // Load saved credentials if remember me was checked
        this.loadSavedCredentials();
    }
    
    async handleLogin(event) {
        event.preventDefault();
        
        if (!this.validateForm()) {
            return;
        }
        
        this.setLoadingState(true);
        
        try {
            const loginData = {
                userId: this.userIdInput.value.trim(),
                password: this.passwordInput.value
            };
            
            const response = await fetch('/api/users/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(loginData)
            });
            
            const result = await response.json();
            
            if (response.ok) {
                this.handleLoginSuccess(result);
            } else {
                this.handleLoginError(result.message || 'Login failed');
            }
        } catch (error) {
            console.error('Login error:', error);
            this.handleLoginError('Network error. Please check your connection.');
        } finally {
            this.setLoadingState(false);
        }
    }
    
    handleLoginSuccess(userData) {
        // Save user data to sessionStorage
        sessionStorage.setItem('user', JSON.stringify(userData));
        localStorage.setItem('user', JSON.stringify(userData));
        sessionStorage.setItem('userId', userData.userId);
        localStorage.setItem('userId', userData.userId);
        
        // Save credentials if remember me is checked
        if (document.getElementById('rememberMe').checked) {
            this.saveCredentials();
        } else {
            this.clearSavedCredentials();
        }
        
        // Show success notification
        this.showNotification('Login successful! Redirecting...', 'success');
        
        // Redirect based on user role
        setTimeout(() => {
            this.redirectToDashboard(userData.userType);
        }, 1500);
    }
    
    handleLoginError(errorMessage) {
        this.showNotification(errorMessage, 'error');
        this.passwordInput.value = '';
        this.passwordInput.focus();
    }
    
    redirectToDashboard(userType) {
        const dashboardRoutes = {
            'ADMIN': '/admin-dashboard',
            'ACCOUNTANT': '/accountant-dashboard',
            'SUPERVISOR': '/supervisor-dashboard',
            'OPERATOR': '/operator-dashboard',
            'CUSTOMER': '/customer-dashboard'
        };
        
        const dashboardRoute = dashboardRoutes[userType] || '/';
        window.location.href = dashboardRoute;
    }
    
    validateForm() {
        let isValid = true;
        
        if (!this.validateUserId()) {
            isValid = false;
        }
        
        if (!this.validatePassword()) {
            isValid = false;
        }
        
        return isValid;
    }
    
    validateUserId() {
        const userId = this.userIdInput.value.trim();
        
        if (!userId) {
            this.showFieldError('userIdError', 'User ID is required');
            return false;
        }
        
        if (userId.length < 6) {
            this.showFieldError('userIdError', 'User ID must be at least 6 characters');
            return false;
        }
        
        this.clearError('userIdError');
        return true;
    }
    
    validatePassword() {
        const password = this.passwordInput.value;
        
        if (!password) {
            this.showFieldError('passwordError', 'Password is required');
            return false;
        }
        
        if (password.length < 6) {
            this.showFieldError('passwordError', 'Password must be at least 6 characters');
            return false;
        }
        
        this.clearError('passwordError');
        return true;
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
    
    togglePasswordVisibility() {
        const isPassword = this.passwordInput.type === 'password';
        this.passwordInput.type = isPassword ? 'text' : 'password';
        
        const icon = this.passwordToggle.querySelector('i');
        icon.classList.toggle('fa-eye', !isPassword);
        icon.classList.toggle('fa-eye-slash', isPassword);
    }
    
    setLoadingState(isLoading) {
        this.loginButton.disabled = isLoading;
        
        if (isLoading) {
            this.loginButton.classList.add('loading');
            this.loginButton.querySelector('.button-text').textContent = 'Signing In...';
        } else {
            this.loginButton.classList.remove('loading');
            this.loginButton.querySelector('.button-text').textContent = 'Sign In';
        }
    }
    
    showNotification(message, type = 'info') {
        const notification = document.createElement('div');
        notification.className = `notification ${type} slide-in`;
        
        const icon = this.getNotificationIcon(type);
        notification.innerHTML = `
            <div class="flex items-center gap-2">
                <i class="${icon}"></i>
                <span>${message}</span>
            </div>
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
    
    saveCredentials() {
        const credentials = {
            userId: this.userIdInput.value.trim(),
            rememberMe: true
        };
        localStorage.setItem('jcbCredentials', JSON.stringify(credentials));
    }
    
    clearSavedCredentials() {
        localStorage.removeItem('jcbCredentials');
    }
    
    loadSavedCredentials() {
        const savedCredentials = localStorage.getItem('jcbCredentials');
        if (savedCredentials) {
            const credentials = JSON.parse(savedCredentials);
            if (credentials.rememberMe) {
                this.userIdInput.value = credentials.userId;
                document.getElementById('rememberMe').checked = true;
            }
        }
    }
}

// Utility functions
class AuthUtils {
    static isLoggedIn() {
        return sessionStorage.getItem('user') !== null;
    }
    
    static getCurrentUser() {
        const userData = sessionStorage.getItem('user');
        return userData ? JSON.parse(userData) : null;
    }
    
    static logout() {
        sessionStorage.removeItem('user');
        window.location.href = '/login';
    }
    
    static hasRole(requiredRole) {
        const user = this.getCurrentUser();
        return user && user.userType === requiredRole;
    }
    
    static redirectIfNotLoggedIn() {
        if (!this.isLoggedIn()) {
            window.location.href = '/login';
        }
    }
}

// Initialize login manager when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    new LoginManager();
});

// Export for use in other files
window.AuthUtils = AuthUtils;
