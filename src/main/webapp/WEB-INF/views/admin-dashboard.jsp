<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - JCB Management System</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="/css/admin-dashboard.css">
</head>
<body>
    <div class="dashboard-container">
        <!-- Header -->
        <header class="header">
            <div class="header-left">
                <div class="logo">
                    <i class="fas fa-truck-monster"></i>
                    <span class="logo-text">JCB Admin Dashboard</span>
                </div>
            </div>
            <div class="header-right">
                <div class="user-info">
                    <i class="fas fa-user-circle"></i>
                    <span>Admin User</span>
                </div>
                <a href="/login" class="logout-btn">
                    <i class="fas fa-sign-out-alt"></i>
                    <span>Logout</span>
                </a>
            </div>
        </header>

        <!-- Main Content -->
        <main class="main-content">
            <div class="welcome-section">
                <h1 class="welcome-title">Welcome to Admin Dashboard</h1>
                <p class="welcome-subtitle">Manage your JCB Management System</p>
            </div>

            <!-- Management Cards -->
            <div class="cards-grid">
                <!-- User Management Card -->
                <div class="management-card">
                    <div class="card-header">
                        <div class="card-icon">
                            <i class="fas fa-users"></i>
                        </div>
                        <h3 class="card-title">User Management</h3>
                    </div>
                    <div class="card-content">
                        <p class="card-description">Manage users, roles, and permissions for your JCB system.</p>
                    </div>
                    <div class="card-footer">
                        <a href="/user-management" class="card-action-btn">
                            <i class="fas fa-arrow-right"></i>
                            <span>Manage Users</span>
                        </a>
                    </div>
                </div>

                <!-- Machine Management Card -->
                <div class="management-card">
                    <div class="card-header">
                        <div class="card-icon">
                            <i class="fas fa-truck"></i>
                        </div>
                        <h3 class="card-title">Machine Management</h3>
                    </div>
                    <div class="card-content">
                        <p class="card-description">Manage JCB machines, maintenance, and operational status.</p>
                    </div>
                    <div class="card-footer">
                        <a href="/machine-management" class="card-action-btn">
                            <i class="fas fa-arrow-right"></i>
                            <span>Manage Machines</span>
                        </a>
                    </div>
                </div>

                <!-- Feedback Management Card -->
                <div class="management-card">
                    <div class="card-header">
                        <div class="card-icon">
                            <i class="fas fa-comments"></i>
                        </div>
                        <h3 class="card-title">Feedback Management</h3>
                    </div>
                    <div class="card-content">
                        <p class="card-description">Manage customer feedback, reviews, and service ratings.</p>
                    </div>
                    <div class="card-footer">
                        <a href="/feedback-management-admin" class="card-action-btn">
                            <i class="fas fa-arrow-right"></i>
                            <span>Manage Feedback</span>
                        </a>
                    </div>
                </div>

                <!-- Booking Request Management Card -->
                <div class="management-card">
                    <div class="card-header">
                        <div class="card-icon">
                            <i class="fas fa-calendar-check"></i>
                        </div>
                        <h3 class="card-title">Booking Request Management</h3>
                    </div>
                    <div class="card-content">
                        <p class="card-description">Manage machine booking requests and scheduling.</p>
                    </div>
                    <div class="card-footer">
                        <a href="/booking-request-management-admin" class="card-action-btn">
                            <i class="fas fa-arrow-right"></i>
                            <span>Manage Bookings</span>
                        </a>
                    </div>
                </div>

               
            </div>

        </main>
    </div>
</body>
</html>
