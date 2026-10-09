<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Available Machines - JCB Management System</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="/css/machines.css">
</head>
<body>
    <div class="machines-container">
        <!-- Header -->
        <header class="machines-header">
            <div class="header-content">
                <div class="logo-section">
                    <i class="fas fa-truck-monster"></i>
                    <h1 class="system-title">JCB Management System</h1>
                </div>
                <div class="user-info">
                    <div class="user-details">
                        <span class="user-name" id="userName">Loading...</span>
                        <span class="user-role">Available Machines</span>
                    </div>
                    <div class="user-avatar">
                        <i class="fas fa-user"></i>
                    </div>
                </div>
            </div>
        </header>

        <!-- Main Content -->
        <main class="machines-main">
            <div class="machines-content">
                <!-- Page Header -->
                <section class="page-header">
                    <div class="page-title-section">
                        <h2 class="page-title">
                            <i class="fas fa-truck-monster"></i>
                            Available Machines
                        </h2>
                        <p class="page-subtitle">Browse our fleet of construction equipment</p>
                    </div>
                    <div class="page-actions">
                        <button class="action-button secondary-button" id="exportPdfBtn">
                            <i class="fas fa-file-pdf"></i>
                            <span>Export to PDF</span>
                        </button>
                        <a href="/customer-dashboard" class="action-button primary-button">
                            <i class="fas fa-arrow-left"></i>
                            <span>Back to Dashboard</span>
                        </a>
                    </div>
                </section>

                <!-- Search and Filter Section -->
                <section class="search-section">
                    <div class="search-container">
                        <div class="search-box">
                            <i class="fas fa-search"></i>
                            <input type="text" id="searchInput" placeholder="Search by machine ID, type, or fuel type..." class="search-input">
                        </div>
                        <div class="filter-container">
                            <select id="typeFilter" class="filter-select">
                                <option value="">All Types</option>
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
                            <select id="fuelFilter" class="filter-select">
                                <option value="">All Fuel Types</option>
                                <option value="DIESEL">Diesel</option>
                                <option value="PETROL">Petrol</option>
                                <option value="ELECTRIC">Electric</option>
                                <option value="HYBRID">Hybrid</option>
                                <option value="CNG">CNG</option>
                                <option value="LPG">LPG</option>
                            </select>
                        </div>
                    </div>
                </section>

                <!-- Machines Grid -->
                <section class="machines-grid-section">
                    <div class="machines-grid" id="machinesGrid">
                        <div class="loading-card">
                            <div class="loading-spinner">
                                <i class="fas fa-spinner fa-spin"></i>
                                <span>Loading machines...</span>
                            </div>
                        </div>
                    </div>
                    
                    <div class="no-data-message" id="noDataMessage" style="display: none;">
                        <i class="fas fa-truck-monster"></i>
                        <h3>No Machines Found</h3>
                        <p>No machines match your search criteria. Try adjusting your filters.</p>
                    </div>
                </section>

                <!-- Statistics Section -->
                <section class="stats-section">
                    <h3 class="section-title">
                        <i class="fas fa-chart-bar"></i>
                        Fleet Overview
                    </h3>
                    
                    <div class="stats-grid">
                        <div class="stat-card">
                            <div class="stat-icon">
                                <i class="fas fa-truck-monster"></i>
                            </div>
                            <div class="stat-content">
                                <span class="stat-number" id="totalMachines">0</span>
                                <span class="stat-label">Total Machines</span>
                            </div>
                        </div>

                        <div class="stat-card">
                            <div class="stat-icon">
                                <i class="fas fa-tags"></i>
                            </div>
                            <div class="stat-content">
                                <span class="stat-number" id="machineTypes">0</span>
                                <span class="stat-label">Machine Types</span>
                            </div>
                        </div>

                        <div class="stat-card">
                            <div class="stat-icon">
                                <i class="fas fa-gas-pump"></i>
                            </div>
                            <div class="stat-content">
                                <span class="stat-number" id="fuelTypes">0</span>
                                <span class="stat-label">Fuel Types</span>
                            </div>
                        </div>

                        <div class="stat-card">
                            <div class="stat-icon">
                                <i class="fas fa-calendar-check"></i>
                            </div>
                            <div class="stat-content">
                                <span class="stat-number" id="availableMachines">0</span>
                                <span class="stat-label">Available Now</span>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
        </main>

        <!-- Machine Detail Modal -->
        <div class="modal-overlay" id="detailModal">
            <div class="modal-container large-modal">
                <div class="modal-header">
                    <h3 class="modal-title">
                        <i class="fas fa-truck-monster"></i>
                        Machine Details
                    </h3>
                    <button class="modal-close" id="closeDetailModal">
                        <i class="fas fa-times"></i>
                    </button>
                </div>
                
                <div class="modal-body">
                    <div class="machine-detail-content" id="machineDetailContent">
                        <!-- Content will be populated by JavaScript -->
                    </div>
                </div>
                
                <div class="modal-actions">
                    <button type="button" class="modal-button cancel-button" id="closeDetailBtn">
                        <i class="fas fa-times"></i>
                        Close
                    </button>
                </div>
            </div>
        </div>

        <!-- Notification Container -->
        <div class="notification-container" id="notificationContainer"></div>
    </div>
    
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf-autotable/3.5.31/jspdf.plugin.autotable.min.js"></script>
    <script src="/js/machines.js"></script>
</body>
</html>
