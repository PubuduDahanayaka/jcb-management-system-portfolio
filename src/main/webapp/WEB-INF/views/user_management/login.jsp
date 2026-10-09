<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>JCB Management System - Login</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="/css/login.css">
</head>
<body style="background: linear-gradient(135deg, #fbbf24, #f59e0b, #d97706); min-height: 100vh; display: flex; align-items: center; justify-content: center;">
    <div class="login-container">
        <div class="login-card">
            <div class="login-header">
                <div class="logo-container">
                    <i class="fas fa-truck-monster" style="font-size: 3.75rem; color: #eab308;"></i>
                </div>
                <h1 class="login-title">JCB Management System</h1>
                <p class="login-subtitle">Sign in to your account</p>
            </div>
            
            <form id="loginForm" class="login-form">
                <div class="form-group">
                    <label for="userId" class="form-label">
                        <i class="fas fa-user-circle"></i>
                        User ID
                    </label>
                    <input type="text" id="userId" name="userId" class="form-input" 
                           placeholder="Enter your User ID (for example, CUS0001)" required>
                    <div class="error-message" id="userIdError"></div>
                </div>
                
                <div class="form-group">
                    <label for="password" class="form-label">
                        <i class="fas fa-lock"></i>
                        Password
                    </label>
                    <div class="password-container">
                        <input type="password" id="password" name="password" class="form-input" 
                               placeholder="Enter your password" required>
                        <button type="button" class="password-toggle" id="passwordToggle">
                            <i class="fas fa-eye"></i>
                        </button>
                    </div>
                    <div class="error-message" id="passwordError"></div>
                </div>
                
                <div class="form-options">
                    <label class="remember-me">
                        <input type="checkbox" id="rememberMe">
                        <span class="checkmark"></span>
                        Remember me
                    </label>
                    <a href="#" class="forgot-password">Forgot Password?</a>
                </div>
                
                <button type="submit" class="login-button" id="loginButton">
                    <i class="fas fa-sign-in-alt"></i>
                    <span class="button-text">Sign In</span>
                    <div class="loading-spinner" id="loadingSpinner">
                        <i class="fas fa-spinner fa-spin"></i>
                    </div>
                </button>
            </form>
            
            <div class="login-footer">
                <div class="divider">
                    <span>or</span>
                </div>
                <div class="social-login">
                    <button class="social-button">
                        <i class="fab fa-google"></i>
                        Continue with Google
                    </button>
                </div>
                <p class="register-link">
                    Don't have an account? 
                    <a href="/register" class="register-text">Create Customer Account</a>
                </p>
            </div>
        </div>
        
        <div class="notification-container" id="notificationContainer"></div>
    </div>
    
    <script src="/js/login.js"></script>
</body>
</html>
