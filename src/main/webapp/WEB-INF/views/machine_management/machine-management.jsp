<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Machine Management - JCB Management System</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="/css/machine-management.css">
</head>
<body>
    <div class="machine-management-container">
        <!-- Header -->
        <header class="header">
            <div class="header-left">
                <a href="/admin-dashboard" class="back-btn">
                    <i class="fas fa-arrow-left"></i>
                    <span>Back to Dashboard</span>
                </a>
                <div class="page-title">
                    <i class="fas fa-truck"></i>
                    <h1>Machine Management</h1>
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
                        <input type="text" id="searchInput" placeholder="Search machines by ID, type, or fuel type...">
                    </div>
                    <div class="filter-section">
                        <select id="machineTypeFilter">
                            <option value="">All Machine Types</option>
                            <option value="EXCAVATOR">Excavator</option>
                            <option value="LOADER">Loader</option>
                            <option value="BULLDOZER">Bulldozer</option>
                            <option value="CRANE">Crane</option>
                            <option value="FORKLIFT">Forklift</option>
                            <option value="COMPACTOR">Compactor</option>
                            <option value="GRADER">Grader</option>
                            <option value="BACKHOE">Backhoe</option>
                            <option value="SKID_STEER">Skid Steer</option>
                            <option value="DUMP_TRUCK">Dump Truck</option>
                        </select>
                        <select id="fuelTypeFilter">
                            <option value="">All Fuel Types</option>
                            <option value="DIESEL">Diesel</option>
                            <option value="PETROL">Petrol</option>
                            <option value="ELECTRIC">Electric</option>
                            <option value="HYBRID">Hybrid</option>
                            <option value="LPG">LPG</option>
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
                        <span>Add Machine</span>
                    </button>
                    <button class="btn btn-secondary" onclick="exportToPDF()">
                        <i class="fas fa-file-pdf"></i>
                        <span>Export PDF</span>
                    </button>
                </div>
            </div>

            <!-- Machines Table -->
            <div class="table-container">
                <table class="machines-table" id="machinesTable">
                    <thead>
                        <tr>
                            <th>Machine ID</th>
                            <th>Machine Type</th>
                            <th>Fuel Type</th>
                            <th>Fuel Level</th>
                            <th>Max Capacity</th>
                            <th>Operating Hours</th>
                            <th>Status</th>
                            <th>Created</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody id="machinesTableBody">
                        <!-- Machines will be loaded here -->
                    </tbody>
                </table>
            </div>

            <!-- Loading State -->
            <div class="loading-state" id="loadingState">
                <div class="loading-spinner">
                    <i class="fas fa-spinner fa-spin"></i>
                </div>
                <p>Loading machines...</p>
            </div>

            <!-- Empty State -->
            <div class="empty-state" id="emptyState" style="display: none;">
                <i class="fas fa-truck"></i>
                <h3>No Machines Found</h3>
                <p>No machines match your current search criteria.</p>
            </div>
        </main>
    </div>

    <!-- Create Machine Modal -->
    <div class="modal" id="createMachineModal">
        <div class="modal-content">
            <div class="modal-header">
                <h2>Create New Machine</h2>
                <button class="modal-close" onclick="closeCreateModal()">
                    <i class="fas fa-times"></i>
                </button>
            </div>
            <form id="createMachineForm" class="modal-body">
                <div class="form-row">
                    <div class="form-group">
                        <label for="createMachineType">Machine Type *</label>
                        <select id="createMachineType" name="machineType" required>
                            <option value="">Select Machine Type</option>
                            <option value="EXCAVATOR">Excavator</option>
                            <option value="LOADER">Loader</option>
                            <option value="BULLDOZER">Bulldozer</option>
                            <option value="CRANE">Crane</option>
                            <option value="FORKLIFT">Forklift</option>
                            <option value="COMPACTOR">Compactor</option>
                            <option value="GRADER">Grader</option>
                            <option value="BACKHOE">Backhoe</option>
                            <option value="SKID_STEER">Skid Steer</option>
                            <option value="DUMP_TRUCK">Dump Truck</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="createFuelType">Fuel Type *</label>
                        <select id="createFuelType" name="fuelType" required>
                            <option value="">Select Fuel Type</option>
                            <option value="DIESEL">Diesel</option>
                            <option value="PETROL">Petrol</option>
                            <option value="ELECTRIC">Electric</option>
                            <option value="HYBRID">Hybrid</option>
                            <option value="LPG">LPG</option>
                        </select>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group">
                        <label for="createCurrentFuelLevel">Current Fuel Level (%)</label>
                        <input type="number" id="createCurrentFuelLevel" name="currentFuelLevel" min="0" max="100" value="100">
                    </div>
                    <div class="form-group">
                        <label for="createMaxFuelCapacity">Max Fuel Capacity (L)</label>
                        <input type="number" id="createMaxFuelCapacity" name="maxFuelCapacity" min="0" step="0.1">
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group">
                        <label for="createOperatingHours">Operating Hours</label>
                        <input type="number" id="createOperatingHours" name="operatingHours" min="0" step="0.1" value="0">
                    </div>
                </div>
            </form>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" onclick="closeCreateModal()">Cancel</button>
                <button type="button" class="btn btn-primary" onclick="createMachine()">Create Machine</button>
            </div>
        </div>
    </div>

    <!-- Edit Machine Modal -->
    <div class="modal" id="editMachineModal">
        <div class="modal-content">
            <div class="modal-header">
                <h2>Edit Machine</h2>
                <button class="modal-close" onclick="closeEditModal()">
                    <i class="fas fa-times"></i>
                </button>
            </div>
            <form id="editMachineForm" class="modal-body">
                <input type="hidden" id="editMachineId" name="machineId">
                <div class="form-row">
                    <div class="form-group">
                        <label for="editMachineType">Machine Type *</label>
                        <select id="editMachineType" name="machineType" required>
                            <option value="">Select Machine Type</option>
                            <option value="EXCAVATOR">Excavator</option>
                            <option value="LOADER">Loader</option>
                            <option value="BULLDOZER">Bulldozer</option>
                            <option value="CRANE">Crane</option>
                            <option value="FORKLIFT">Forklift</option>
                            <option value="COMPACTOR">Compactor</option>
                            <option value="GRADER">Grader</option>
                            <option value="BACKHOE">Backhoe</option>
                            <option value="SKID_STEER">Skid Steer</option>
                            <option value="DUMP_TRUCK">Dump Truck</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="editFuelType">Fuel Type *</label>
                        <select id="editFuelType" name="fuelType" required>
                            <option value="">Select Fuel Type</option>
                            <option value="DIESEL">Diesel</option>
                            <option value="PETROL">Petrol</option>
                            <option value="ELECTRIC">Electric</option>
                            <option value="HYBRID">Hybrid</option>
                            <option value="LPG">LPG</option>
                        </select>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group">
                        <label for="editCurrentFuelLevel">Current Fuel Level (%)</label>
                        <input type="number" id="editCurrentFuelLevel" name="currentFuelLevel" min="0" max="100">
                    </div>
                    <div class="form-group">
                        <label for="editMaxFuelCapacity">Max Fuel Capacity (L)</label>
                        <input type="number" id="editMaxFuelCapacity" name="maxFuelCapacity" min="0" step="0.1">
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group">
                        <label for="editOperatingHours">Operating Hours</label>
                        <input type="number" id="editOperatingHours" name="operatingHours" min="0" step="0.1">
                    </div>
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
                <button type="button" class="btn btn-primary" onclick="updateMachine()">Update Machine</button>
            </div>
        </div>
    </div>

    <!-- Notification Container -->
    <div class="notification-container" id="notificationContainer"></div>

    <!-- Scripts -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf-autotable/3.5.31/jspdf.plugin.autotable.min.js"></script>
    <script src="/js/machine-management.js"></script>
</body>
</html>
