// Registration Page JavaScript
class RegistrationManager {
    constructor() {
        this.form = document.getElementById('registerForm');
        this.registerButton = document.getElementById('registerButton');
        this.loadingSpinner = document.getElementById('loadingSpinner');
        this.notificationContainer = document.getElementById('notificationContainer');
        
        this.initializeEventListeners();
        this.initializeForm();
    }
    
    initializeEventListeners() {
        // Form submission
        this.form.addEventListener('submit', (e) => this.handleRegistration(e));
        
        // Password toggles
        document.getElementById('passwordToggle').addEventListener('click', () => 
            this.togglePasswordVisibility('password', 'passwordToggle'));
        document.getElementById('confirmPasswordToggle').addEventListener('click', () => 
            this.togglePasswordVisibility('confirmPassword', 'confirmPasswordToggle'));
        
        // Real-time validation
        document.getElementById('firstName').addEventListener('blur', () => this.validateFirstName());
        document.getElementById('lastName').addEventListener('blur', () => this.validateLastName());
        document.getElementById('email').addEventListener('blur', () => this.validateEmail());
        document.getElementById('contactNumber').addEventListener('blur', () => this.validateContactNumber());
        document.getElementById('password').addEventListener('input', () => this.validatePassword());
        document.getElementById('confirmPassword').addEventListener('blur', () => this.validateConfirmPassword());
        document.getElementById('agreeTerms').addEventListener('change', () => this.validateTerms());
        
        // Clear errors on input
        document.getElementById('firstName').addEventListener('input', () => this.clearError('firstNameError'));
        document.getElementById('lastName').addEventListener('input', () => this.clearError('lastNameError'));
        document.getElementById('email').addEventListener('input', () => this.clearError('emailError'));
        document.getElementById('contactNumber').addEventListener('input', () => this.clearError('contactNumberError'));
        document.getElementById('password').addEventListener('input', () => this.clearError('passwordError'));
        document.getElementById('confirmPassword').addEventListener('input', () => this.clearError('confirmPasswordError'));
    }
    
    initializeForm() {
        // Add fade-in animation
        document.querySelector('.register-card').classList.add('fade-in');
        
        // Focus on first input
        setTimeout(() => {
            document.getElementById('firstName').focus();
        }, 500);
    }
    
    async handleRegistration(event) {
        event.preventDefault();
        
        if (!this.validateForm()) {
            return;
        }
        
        this.setLoadingState(true);
        
        try {
            const formData = new FormData(this.form);
            const registrationData = {
                firstName: formData.get('firstName').trim(),
                lastName: formData.get('lastName').trim(),
                email: formData.get('email').trim(),
                contactNumber: formData.get('contactNumber').trim(),
                password: formData.get('password'),
                userType: 'CUSTOMER' // Always set as CUSTOMER for registration
            };
            
            const response = await fetch('/api/users', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(registrationData)
            });
            
            const result = await response.json();
            
            if (response.ok) {
                this.handleRegistrationSuccess(result);
            } else {
                this.handleRegistrationError(result.message || 'Registration failed');
            }
        } catch (error) {
            console.error('Registration error:', error);
            this.handleRegistrationError('Network error. Please check your connection.');
        } finally {
            this.setLoadingState(false);
        }
    }
    
    handleRegistrationSuccess(userData) {
        this.showNotification(
            `Registration successful! Your User ID is ${userData.userId}. Use it to sign in. Redirecting to login...`,
            'success'
        );
        
        // Redirect to login page after 2 seconds
        setTimeout(() => {
            window.location.href = '/login';
        }, 2000);
    }
    
    handleRegistrationError(errorMessage) {
        this.showNotification(errorMessage, 'error');
    }
    
    validateForm() {
        let isValid = true;
        
        if (!this.validateFirstName()) isValid = false;
        if (!this.validateLastName()) isValid = false;
        if (!this.validateEmail()) isValid = false;
        if (!this.validateContactNumber()) isValid = false;
        if (!this.validatePassword()) isValid = false;
        if (!this.validateConfirmPassword()) isValid = false;
        if (!this.validateTerms()) isValid = false;
        
        return isValid;
    }
    
    validateFirstName() {
        const firstName = document.getElementById('firstName').value.trim();
        const firstNameRegex = /^[a-zA-Z\s]{2,50}$/;
        
        if (!firstName) {
            this.showFieldError('firstNameError', 'First name is required');
            return false;
        }
        
        if (!firstNameRegex.test(firstName)) {
            this.showFieldError('firstNameError', 'First name must be 2-50 characters and contain only letters');
            return false;
        }
        
        this.clearError('firstNameError');
        this.markFieldSuccess('firstName');
        return true;
    }
    
    validateLastName() {
        const lastName = document.getElementById('lastName').value.trim();
        const lastNameRegex = /^[a-zA-Z\s]{2,50}$/;
        
        if (!lastName) {
            this.showFieldError('lastNameError', 'Last name is required');
            return false;
        }
        
        if (!lastNameRegex.test(lastName)) {
            this.showFieldError('lastNameError', 'Last name must be 2-50 characters and contain only letters');
            return false;
        }
        
        this.clearError('lastNameError');
        this.markFieldSuccess('lastName');
        return true;
    }
    
    validateEmail() {
        const email = document.getElementById('email').value.trim();
        const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        
        if (!email) {
            this.showFieldError('emailError', 'Email address is required');
            return false;
        }
        
        if (!emailRegex.test(email)) {
            this.showFieldError('emailError', 'Please enter a valid email address');
            return false;
        }
        
        this.clearError('emailError');
        this.markFieldSuccess('email');
        return true;
    }
    
    validateContactNumber() {
        const contactNumber = document.getElementById('contactNumber').value.trim();
        const phoneRegex = /^[\+]?[0-9\s\-\(\)]{10,15}$/;
        
        if (!contactNumber) {
            this.showFieldError('contactNumberError', 'Contact number is required');
            return false;
        }
        
        if (!phoneRegex.test(contactNumber)) {
            this.showFieldError('contactNumberError', 'Please enter a valid contact number (10-15 digits)');
            return false;
        }
        
        this.clearError('contactNumberError');
        this.markFieldSuccess('contactNumber');
        return true;
    }
    
    validatePassword() {
        const password = document.getElementById('password').value;
        const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
        
        if (!password) {
            this.showFieldError('passwordError', 'Password is required');
            this.updatePasswordStrength('');
            return false;
        }
        
        if (password.length < 8) {
            this.showFieldError('passwordError', 'Password must be at least 8 characters long');
            this.updatePasswordStrength('weak');
            return false;
        }
        
        if (!passwordRegex.test(password)) {
            this.showFieldError('passwordError', 'Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character');
            this.updatePasswordStrength('weak');
            return false;
        }
        
        this.clearError('passwordError');
        this.markFieldSuccess('password');
        this.updatePasswordStrength(this.calculatePasswordStrength(password));
        return true;
    }
    
    validateConfirmPassword() {
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirmPassword').value;
        
        if (!confirmPassword) {
            this.showFieldError('confirmPasswordError', 'Please confirm your password');
            return false;
        }
        
        if (password !== confirmPassword) {
            this.showFieldError('confirmPasswordError', 'Passwords do not match');
            return false;
        }
        
        this.clearError('confirmPasswordError');
        this.markFieldSuccess('confirmPassword');
        return true;
    }
    
    validateTerms() {
        const agreeTerms = document.getElementById('agreeTerms').checked;
        
        if (!agreeTerms) {
            this.showFieldError('agreeTermsError', 'You must agree to the terms and conditions');
            return false;
        }
        
        this.clearError('agreeTermsError');
        return true;
    }
    
    calculatePasswordStrength(password) {
        let score = 0;
        
        if (password.length >= 8) score++;
        if (password.length >= 12) score++;
        if (/[a-z]/.test(password)) score++;
        if (/[A-Z]/.test(password)) score++;
        if (/\d/.test(password)) score++;
        if (/[@$!%*?&]/.test(password)) score++;
        
        if (score <= 2) return 'weak';
        if (score <= 4) return 'medium';
        return 'strong';
    }
    
    updatePasswordStrength(strength) {
        const strengthElement = document.getElementById('passwordStrength');
        
        if (!strength) {
            strengthElement.textContent = '';
            strengthElement.className = 'password-strength';
            return;
        }
        
        const messages = {
            weak: 'Weak password',
            medium: 'Medium strength password',
            strong: 'Strong password'
        };
        
        strengthElement.textContent = messages[strength];
        strengthElement.className = `password-strength ${strength}`;
    }
    
    showFieldError(errorElementId, message) {
        const errorElement = document.getElementById(errorElementId);
        errorElement.textContent = message;
        errorElement.classList.add('show');
        
        const inputElement = errorElement.previousElementSibling;
        if (inputElement.classList.contains('form-input')) {
            inputElement.classList.add('error');
            inputElement.classList.remove('success');
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
    
    markFieldSuccess(fieldId) {
        const inputElement = document.getElementById(fieldId);
        inputElement.classList.add('success');
        inputElement.classList.remove('error');
    }
    
    togglePasswordVisibility(fieldId, toggleId) {
        const field = document.getElementById(fieldId);
        const toggle = document.getElementById(toggleId);
        const icon = toggle.querySelector('i');
        
        const isPassword = field.type === 'password';
        field.type = isPassword ? 'text' : 'password';
        
        icon.classList.toggle('fa-eye', !isPassword);
        icon.classList.toggle('fa-eye-slash', isPassword);
    }
    
    setLoadingState(isLoading) {
        this.registerButton.disabled = isLoading;
        
        if (isLoading) {
            this.registerButton.classList.add('loading');
            this.registerButton.querySelector('.button-text').textContent = 'Creating Account...';
        } else {
            this.registerButton.classList.remove('loading');
            this.registerButton.querySelector('.button-text').textContent = 'Create Account';
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

// Initialize registration manager when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    new RegistrationManager();
});
