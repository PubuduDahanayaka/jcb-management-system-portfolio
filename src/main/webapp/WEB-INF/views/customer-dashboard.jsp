<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Dashboard - JCB Management System</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="/css/customer-dashboard.css">
</head>
<body>
    <div class="dashboard-container">
        <!-- Header -->
        <header class="dashboard-header">
            <div class="header-content">
                <div class="logo-section">
                    <i class="fas fa-truck-monster"></i>
                    <h1 class="system-title">JCB Management System</h1>
                </div>
                <div class="user-info">
                    <div class="user-details">
                        <span class="user-name">Welcome, Customer</span>
                        <span class="user-role">Customer Dashboard</span>
                    </div>
                    <div class="user-avatar">
                        <i class="fas fa-user"></i>
                    </div>
                </div>
            </div>
        </header>

        <!-- Main Content -->
        <main class="dashboard-main">
            <div class="dashboard-content">
                <!-- Welcome Section -->
                <section class="welcome-section">
                    <div class="welcome-card">
                        <div class="welcome-icon">
                            <i class="fas fa-hand-wave"></i>
                        </div>
                        <div class="welcome-text">
                            <h2>Welcome to Your Dashboard</h2>
                            <p>Manage your bookings, view machines, and provide feedback from here.</p>
                        </div>
                    </div>
                </section>

                <!-- Navigation Cards -->
                <section class="navigation-section">
                    <h3 class="section-title">
                        <i class="fas fa-th-large"></i>
                        Quick Access
                    </h3>
                    
                    <div class="cards-grid">
                        <!-- Profile Card -->
                        <div class="nav-card">
                            <div class="card-icon">
                                <i class="fas fa-user-circle"></i>
                            </div>
                            <div class="card-content">
                                <h4 class="card-title">My Profile</h4>
                                <p class="card-description">View and update your personal information</p>
                            </div>
                            <div class="card-action">
                                <a href="/profile" class="card-link">
                                    <i class="fas fa-arrow-right"></i>
                                    <span>View Profile</span>
                                </a>
                            </div>
                        </div>

                        <!-- Booking Requests Card -->
                        <div class="nav-card">
                            <div class="card-icon">
                                <i class="fas fa-calendar-check"></i>
                            </div>
                            <div class="card-content">
                                <h4 class="card-title">Booking Requests</h4>
                                <p class="card-description">Manage your machine booking requests</p>
                            </div>
                            <div class="card-action">
                                <a href="/booking-requests" class="card-link">
                                    <i class="fas fa-arrow-right"></i>
                                    <span>View Bookings</span>
                                </a>
                            </div>
                        </div>

                        <!-- Feedbacks Card -->
                        <div class="nav-card">
                            <div class="card-icon">
                                <i class="fas fa-comment-dots"></i>
                            </div>
                            <div class="card-content">
                                <h4 class="card-title">Feedbacks</h4>
                                <p class="card-description">Submit and view your feedback</p>
                            </div>
                            <div class="card-action">
                                <a href="/feedbacks" class="card-link">
                                    <i class="fas fa-arrow-right"></i>
                                    <span>Manage Feedback</span>
                                </a>
                            </div>
                        </div>

                        <!-- Machines Card -->
                        <div class="nav-card">
                            <div class="card-icon">
                                <i class="fas fa-truck-monster"></i>
                            </div>
                            <div class="card-content">
                                <h4 class="card-title">Available Machines</h4>
                                <p class="card-description">Browse available JCB machines</p>
                            </div>
                            <div class="card-action">
                                <a href="/machines" class="card-link">
                                    <i class="fas fa-arrow-right"></i>
                                    <span>View Machines</span>
                                </a>
                            </div>
                        </div>
                    </div>
                </section>

                <!-- Quick Stats Section -->
                <section class="stats-section">
                    <h3 class="section-title">
                        <i class="fas fa-chart-bar"></i>
                        Quick Overview
                    </h3>
                    
                    <div class="stats-grid">
                        <div class="stat-card">
                            <div class="stat-icon">
                                <i class="fas fa-calendar-plus"></i>
                            </div>
                            <div class="stat-content">
                                <span class="stat-number">0</span>
                                <span class="stat-label">Active Bookings</span>
                            </div>
                        </div>

                        <div class="stat-card">
                            <div class="stat-icon">
                                <i class="fas fa-clock"></i>
                            </div>
                            <div class="stat-content">
                                <span class="stat-number">0</span>
                                <span class="stat-label">Pending Requests</span>
                            </div>
                        </div>

                        <div class="stat-card">
                            <div class="stat-icon">
                                <i class="fas fa-comment"></i>
                            </div>
                            <div class="stat-content">
                                <span class="stat-number">0</span>
                                <span class="stat-label">Feedbacks Sent</span>
                            </div>
                        </div>

                        <div class="stat-card">
                            <div class="stat-icon">
                                <i class="fas fa-truck"></i>
                            </div>
                            <div class="stat-content">
                                <span class="stat-number">0</span>
                                <span class="stat-label">Machines Viewed</span>
                            </div>
                        </div>
                    </div>
                </section>

                <!-- Recent Activity Section -->
                <section class="activity-section">
                    <h3 class="section-title">
                        <i class="fas fa-history"></i>
                        Recent Activity
                    </h3>
                    
                    <div class="activity-card">
                        <div class="activity-item">
                            <div class="activity-icon">
                                <i class="fas fa-info-circle"></i>
                            </div>
                            <div class="activity-content">
                                <p class="activity-text">Welcome to JCB Management System!</p>
                                <span class="activity-time">Just now</span>
                            </div>
                        </div>
                        
                        <div class="activity-placeholder">
                            <i class="fas fa-inbox"></i>
                            <p>No recent activity</p>
                            <span>Your recent actions will appear here</span>
                        </div>
                    </div>
                </section>
            </div>
        </main>

        <!-- Footer -->
        <footer class="dashboard-footer">
            <div class="footer-content">
                <div class="footer-section">
                    <h4>JCB Management System</h4>
                    <p>Professional construction equipment management</p>
                </div>
                <div class="footer-section">
                    <h4>Quick Links</h4>
                    <ul>
                        <li><a href="/profile">Profile</a></li>
                        <li><a href="/booking-requests">Bookings</a></li>
                        <li><a href="/feedbacks">Feedback</a></li>
                        <li><a href="/machines">Machines</a></li>
                    </ul>
                </div>
                <div class="footer-section">
                    <h4>Support</h4>
                    <ul>
                        <li><a href="#">Help Center</a></li>
                        <li><a href="#">Contact Us</a></li>
                        <li><a href="#">Terms of Service</a></li>
                        <li><a href="#">Privacy Policy</a></li>
                    </ul>
                </div>
            </div>
            <div class="footer-bottom">
                <p>&copy; 2024 JCB Management System. All rights reserved.</p>
            </div>
        </footer>
    </div>
</body>
</html>
