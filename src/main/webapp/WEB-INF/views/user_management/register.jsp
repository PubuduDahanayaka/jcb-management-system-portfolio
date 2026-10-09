<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Registration - JCB Management System</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="/css/register.css">
</head>
<body>
    <div class="register-container">
        <div class="register-card">
            <div class="register-header">
                <div class="logo-container">
                    <i class="fas fa-truck-monster"></i>
                </div>
                <h1 class="register-title">JCB Management System</h1>
                <p class="register-subtitle">Create your customer account</p>
            </div>
            
            <form id="registerForm" class="register-form">
                <div class="form-grid">
                    <!-- Personal Information Section -->
                    <div class="form-section">
                        <h3 class="section-title">
                            <i class="fas fa-user"></i>
                            Personal Information
                        </h3>
                        
                        <div class="form-row">
                            <div class="form-group">
                                <label for="firstName" class="form-label">
                                    <i class="fas fa-user"></i>
                                    First Name *
                                </label>
                                <input type="text" id="firstName" name="firstName" class="form-input" 
                                       placeholder="Enter your first name" required>
                                <div class="error-message" id="firstNameError"></div>
                            </div>
                            
                            <div class="form-group">
                                <label for="lastName" class="form-label">
                                    <i class="fas fa-user"></i>
                                    Last Name *
                                </label>
                                <input type="text" id="lastName" name="lastName" class="form-input" 
                                       placeholder="Enter your last name" required>
                                <div class="error-message" id="lastNameError"></div>
                            </div>
                        </div>
                    </div>
                    
                    <!-- Contact Information Section -->
                    <div class="form-section">
                        <h3 class="section-title">
                            <i class="fas fa-phone"></i>
                            Contact Information
                        </h3>
                        
                        <div class="form-row">
                            <div class="form-group">
                                <label for="email" class="form-label">
                                    <i class="fas fa-envelope"></i>
                                    Email Address *
                                </label>
                                <input type="email" id="email" name="email" class="form-input" 
                                       placeholder="Enter your email address" required>
                                <div class="error-message" id="emailError"></div>
                            </div>
                            
                            <div class="form-group">
                                <label for="contactNumber" class="form-label">
                                    <i class="fas fa-phone"></i>
                                    Contact Number *
                                </label>
                                <input type="tel" id="contactNumber" name="contactNumber" class="form-input" 
                                       placeholder="Enter your contact number" required>
                                <div class="error-message" id="contactNumberError"></div>
                            </div>
                        </div>
                    </div>
                    
                    <!-- Security Section -->
                    <div class="form-section">
                        <h3 class="section-title">
                            <i class="fas fa-lock"></i>
                            Security Information
                        </h3>
                        
                        <div class="form-row">
                            <div class="form-group">
                                <label for="password" class="form-label">
                                    <i class="fas fa-lock"></i>
                                    Password *
                                </label>
                                <div class="password-container">
                                    <input type="password" id="password" name="password" class="form-input" 
                                           placeholder="Create a strong password" required>
                                    <button type="button" class="password-toggle" id="passwordToggle">
                                        <i class="fas fa-eye"></i>
                                    </button>
                                </div>
                                <div class="error-message" id="passwordError"></div>
                                <div class="password-strength" id="passwordStrength"></div>
                            </div>
                            
                            <div class="form-group">
                                <label for="confirmPassword" class="form-label">
                                    <i class="fas fa-lock"></i>
                                    Confirm Password *
                                </label>
                                <div class="password-container">
                                    <input type="password" id="confirmPassword" name="confirmPassword" class="form-input" 
                                           placeholder="Confirm your password" required>
                                    <button type="button" class="password-toggle" id="confirmPasswordToggle">
                                        <i class="fas fa-eye"></i>
                                    </button>
                                </div>
                                <div class="error-message" id="confirmPasswordError"></div>
                            </div>
                        </div>
                    </div>
                    
                    <!-- Terms and Conditions -->
                    <div class="form-section">
                        <h3 class="section-title">
                            <i class="fas fa-file-contract"></i>
                            Terms & Conditions
                        </h3>
                        
                        <div class="form-group full-width">
                            <label class="checkbox-label">
                                <input type="checkbox" id="agreeTerms" name="agreeTerms" required>
                                <span class="checkmark"></span>
                                I agree to the <a href="#" class="terms-link">Terms of Service</a> and <a href="#" class="terms-link">Privacy Policy</a> *
                            </label>
                            <div class="error-message" id="agreeTermsError"></div>
                        </div>
                        
                        <div class="form-group full-width">
                            <label class="checkbox-label">
                                <input type="checkbox" id="agreeMarketing" name="agreeMarketing">
                                <span class="checkmark"></span>
                                I would like to receive promotional emails and updates about JCB services
                            </label>
                        </div>
                    </div>
                </div>
                
                <div class="form-actions">
                    <button type="submit" class="register-button" id="registerButton">
                        <i class="fas fa-user-plus"></i>
                        <span class="button-text">Create Account</span>
                        <div class="loading-spinner" id="loadingSpinner">
                            <i class="fas fa-spinner fa-spin"></i>
                        </div>
                    </button>
                </div>
            </form>
            
            <div class="register-footer">
                <div class="divider">
                    <span>Already have an account?</span>
                </div>
                <div class="login-link">
                    <a href="/login" class="login-text">
                        <i class="fas fa-sign-in-alt"></i>
                        Sign in to your account
                    </a>
                </div>
            </div>
        </div>
        
        <div class="notification-container" id="notificationContainer"></div>
    </div>
    
    <script src="/js/register.js"></script>
</body>
</html>
