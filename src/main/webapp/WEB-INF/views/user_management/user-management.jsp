<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Management - JCB Management System</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="/css/user-management.css">
</head>
<body>
    <div class="user-management-container">
        <!-- Header -->
        <header class="header">
            <div class="header-left">
                <a href="/admin-dashboard" class="back-btn">
                    <i class="fas fa-arrow-left"></i>
                    <span>Back to Dashboard</span>
                </a>
                <div class="page-title">
                    <i class="fas fa-users"></i>
                    <h1>User Management</h1>
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
            <!-- Action Bar -->
            <div class="action-bar">
                <div class="search-section">
                    <div class="search-box">
                        <i class="fas fa-search"></i>
                        <input type="text" id="searchInput" placeholder="Search users by name, email, or user ID...">
                    </div>
                    <div class="filter-section">
                        <select id="userTypeFilter">
                            <option value="">All User Types</option>
                            <option value="ADMIN">Admin</option>
                            <option value="CUSTOMER">Customer</option>
                            <option value="SUPERVISOR">Supervisor</option>
                            <option value="OPERATOR">Operator</option>
                            <option value="ACCOUNTANT">Accountant</option>
                        </select>
                        <select id="statusFilter">
                            <option value="">All Status</option>
                            <option value="true">Active</option>
                            <option value="false">Inactive</option>
                        </select>
                    </div>
                </div>
                <div class="action-buttons">
                    <button class="btn btn-primary" onclick="openCreateModal()">
                        <i class="fas fa-plus"></i>
                        <span>Add User</span>
                    </button>
                    <button class="btn btn-secondary" onclick="exportToPDF()">
                        <i class="fas fa-file-pdf"></i>
                        <span>Export PDF</span>
                    </button>
                </div>
            </div>

            <!-- Users Table -->
            <div class="table-container">
                <table class="users-table" id="usersTable">
                    <thead>
                        <tr>
                            <th>User ID</th>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Contact</th>
                            <th>Type</th>
                            <th>Status</th>
                            <th>Created</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody id="usersTableBody">
                        <!-- Users will be loaded here -->
                    </tbody>
                </table>
            </div>

            <!-- Loading State -->
            <div class="loading-state" id="loadingState">
                <div class="loading-spinner">
                    <i class="fas fa-spinner fa-spin"></i>
                </div>
                <p>Loading users...</p>
            </div>

            <!-- Empty State -->
            <div class="empty-state" id="emptyState" style="display: none;">
                <i class="fas fa-users"></i>
                <h3>No Users Found</h3>
                <p>No users match your current search criteria.</p>
            </div>
        </main>
    </div>

    <!-- Create User Modal -->
    <div class="modal" id="createUserModal">
        <div class="modal-content">
            <div class="modal-header">
                <h2>Create New User</h2>
                <button class="modal-close" onclick="closeCreateModal()">
                    <i class="fas fa-times"></i>
                </button>
            </div>
            <form id="createUserForm" class="modal-body">
                <div class="form-row">
                    <div class="form-group">
                        <label for="createFirstName">First Name *</label>
                        <input type="text" id="createFirstName" name="firstName" required>
                    </div>
                    <div class="form-group">
                        <label for="createLastName">Last Name *</label>
                        <input type="text" id="createLastName" name="lastName" required>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group">
                        <label for="createEmail">Email *</label>
                        <input type="email" id="createEmail" name="email" required>
                    </div>
                    <div class="form-group">
                        <label for="createContactNumber">Contact Number *</label>
                        <input type="tel" id="createContactNumber" name="contactNumber" required>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group">
                        <label for="createPassword">Password *</label>
                        <input type="password" id="createPassword" name="password" required>
                    </div>
                    <div class="form-group">
                        <label for="createUserType">User Type *</label>
                        <select id="createUserType" name="userType" required>
                            <option value="">Select User Type</option>
                            <option value="ADMIN">Admin</option>
                            <option value="CUSTOMER">Customer</option>
                            <option value="SUPERVISOR">Supervisor</option>
                            <option value="OPERATOR">Operator</option>
                            <option value="ACCOUNTANT">Accountant</option>
                        </select>
                    </div>
                </div>
            </form>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" onclick="closeCreateModal()">Cancel</button>
                <button type="button" class="btn btn-primary" onclick="createUser()">Create User</button>
            </div>
        </div>
    </div>

    <!-- Edit User Modal -->
    <div class="modal" id="editUserModal">
        <div class="modal-content">
            <div class="modal-header">
                <h2>Edit User</h2>
                <button class="modal-close" onclick="closeEditModal()">
                    <i class="fas fa-times"></i>
                </button>
            </div>
            <form id="editUserForm" class="modal-body">
                <input type="hidden" id="editUserId" name="userId">
                <div class="form-row">
                    <div class="form-group">
                        <label for="editFirstName">First Name *</label>
                        <input type="text" id="editFirstName" name="firstName" required>
                    </div>
                    <div class="form-group">
                        <label for="editLastName">Last Name *</label>
                        <input type="text" id="editLastName" name="lastName" required>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group">
                        <label for="editEmail">Email *</label>
                        <input type="email" id="editEmail" name="email" required>
                    </div>
                    <div class="form-group">
                        <label for="editContactNumber">Contact Number *</label>
                        <input type="tel" id="editContactNumber" name="contactNumber" required>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group">
                        <label for="editPassword">Password (leave blank to keep current)</label>
                        <input type="password" id="editPassword" name="password">
                    </div>
                    <div class="form-group">
                        <label for="editUserType">User Type *</label>
                        <select id="editUserType" name="userType" required>
                            <option value="">Select User Type</option>
                            <option value="ADMIN">Admin</option>
                            <option value="CUSTOMER">Customer</option>
                            <option value="SUPERVISOR">Supervisor</option>
                            <option value="OPERATOR">Operator</option>
                            <option value="ACCOUNTANT">Accountant</option>
                        </select>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group">
                        <label for="editIsActive">Status</label>
                        <select id="editIsActive" name="isActive">
                            <option value="true">Active</option>
                            <option value="false">Inactive</option>
                        </select>
                    </div>
                </div>
            </form>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" onclick="closeEditModal()">Cancel</button>
                <button type="button" class="btn btn-primary" onclick="updateUser()">Update User</button>
            </div>
        </div>
    </div>

    <!-- Notification Container -->
    <div class="notification-container" id="notificationContainer"></div>

    <!-- Scripts -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf-autotable/3.5.31/jspdf.plugin.autotable.min.js"></script>
    <script src="/js/user-management.js"></script>
</body>
</html>
