// Operator Dashboard JavaScript
class OperatorDashboard {
    constructor() {
        this.currentUser = null;
        this.jobs = [];
        this.machines = [];
        this.currentJobId = null;
        this.currentTab = 'profile';
        
        this.statusModal = document.getElementById('statusModal');
        this.detailsModal = document.getElementById('detailsModal');
        this.notificationContainer = document.getElementById('notificationContainer');
        
        this.initializeEventListeners();
        this.loadInitialData();
    }
    
    initializeEventListeners() {
        // Tab Navigation
        document.getElementById('profileTab').addEventListener('click', () => this.switchTab('profile'));
        document.getElementById('jobsTab').addEventListener('click', () => this.switchTab('jobs'));
        
        // Status Update Modal
        document.getElementById('closeStatusModal').addEventListener('click', () => this.closeStatusModal());
        document.getElementById('cancelStatusUpdate').addEventListener('click', () => this.closeStatusModal());
        document.getElementById('statusUpdateForm').addEventListener('submit', (e) => this.handleStatusUpdate(e));
        
        // Details Modal
        document.getElementById('closeDetailsModal').addEventListener('click', () => this.closeDetailsModal());
        document.getElementById('closeDetailsBtn').addEventListener('click', () => this.closeDetailsModal());
        
        // Search and Filter
        document.getElementById('searchInput').addEventListener('input', () => this.filterJobs());
        document.getElementById('statusFilter').addEventListener('change', () => this.filterJobs());
        document.getElementById('machineFilter').addEventListener('change', () => this.filterJobs());
        
        // Export PDF
        document.getElementById('exportPdfBtn').addEventListener('click', () => this.exportToPDF());
        
        // Character counter for completion notes
        document.getElementById('completionNotes').addEventListener('input', () => this.updateCharCounter('completionNotesCounter', 'completionNotes', 1000));
        
        // Clear errors on input
        document.getElementById('newJobStatus').addEventListener('change', () => this.clearError('newJobStatusError'));
        
        // Close modals on overlay click
        this.statusModal.addEventListener('click', (e) => {
            if (e.target === this.statusModal) this.closeStatusModal();
        });
        
        this.detailsModal.addEventListener('click', (e) => {
            if (e.target === this.detailsModal) this.closeDetailsModal();
        });
    }
    
    async loadInitialData() {
        try {
            // Get current user ID from session storage
            const userId = this.getCurrentUserId();
            if (!userId) {
                this.showNotification('User session not found. Please login again.', 'error');
                setTimeout(() => {
                    window.location.href = '/login';
                }, 2000);
                return;
            }
            
            // Load user info
            await this.loadUserInfo(userId);
            
            // Load machines for filtering
            await this.loadMachines();
            
            // Load operator's assigned jobs
            await this.loadJobs(userId);
            
        } catch (error) {
            console.error('Error loading initial data:', error);
            this.showNotification('Failed to load data. Please refresh the page.', 'error');
        }
    }
    
    getCurrentUserId() {
        // Try to get from session storage first
        const sessionUserId = sessionStorage.getItem('userId');
        if (sessionUserId) return sessionUserId;
        
        // Try to get from localStorage as fallback
        const localUserId = localStorage.getItem('userId');
        if (localUserId) return localUserId;
        
        return null;
    }
    
    async loadUserInfo(userId) {
        try {
            const response = await fetch(`/api/users/${userId}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                }
            });
            
            if (response.ok) {
                this.currentUser = await response.json();
                document.getElementById('userName').textContent = `${this.currentUser.firstName} ${this.currentUser.lastName}`;
                this.displayUserProfile();
            }
        } catch (error) {
            console.error('Error loading user info:', error);
        }
    }
    
    displayUserProfile() {
        if (!this.currentUser) return;
        
        // Update profile header
        document.getElementById('profileName').textContent = `${this.currentUser.firstName} ${this.currentUser.lastName}`;
        document.getElementById('profileEmail').textContent = this.currentUser.email;
        document.getElementById('profileId').textContent = this.currentUser.userId;
        
        // Update detail items
        document.getElementById('detailUserId').textContent = this.currentUser.userId;
        document.getElementById('detailFirstName').textContent = this.currentUser.firstName;
        document.getElementById('detailLastName').textContent = this.currentUser.lastName;
        document.getElementById('detailEmail').textContent = this.currentUser.email;
        document.getElementById('detailContactNumber').textContent = this.currentUser.contactNumber;
        document.getElementById('detailUserType').textContent = this.currentUser.userType;
    }
    
    async loadMachines() {
        try {
            const response = await fetch('/api/machines', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                }
            });
            
            if (response.ok) {
                this.machines = await response.json();
                this.populateMachineFilter();
            }
        } catch (error) {
            console.error('Error loading machines:', error);
        }
    }
    
    populateMachineFilter() {
        const filterSelect = document.getElementById('machineFilter');
        
        // Clear existing options
        filterSelect.innerHTML = '<option value="">All Machines</option>';
        
        if (this.machines.length === 0) {
            return;
        }
        
        // Add machine options
        this.machines.forEach(machine => {
            const optionText = `${machine.machineId} - ${this.formatMachineType(machine.machineType)}`;
            
            const filterOption = document.createElement('option');
            filterOption.value = machine.machineId;
            filterOption.textContent = optionText;
            filterSelect.appendChild(filterOption);
        });
    }
    
    async loadJobs(userId) {
        try {
            const response = await fetch(`/api/machine-jobs/user/${userId}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                }
            });
            
            if (response.ok) {
                this.jobs = await response.json();
                console.table(this.jobs);
                this.displayJobs();
            } else {
                this.showNotification('Failed to load jobs.', 'error');
            }
        } catch (error) {
            console.error('Error loading jobs:', error);
            this.showNotification('Network error. Please check your connection.', 'error');
        }
    }
    
    displayJobs() {
        const tbody = document.getElementById('jobTableBody');
        const noDataMessage = document.getElementById('noDataMessage');
        
        if (this.jobs.length === 0) {
            tbody.innerHTML = '';
            noDataMessage.style.display = 'block';
            return;
        }
        
        noDataMessage.style.display = 'none';
        
        tbody.innerHTML = this.jobs.map(job => {
            const machine = this.machines.find(m => m.machineId === job.machineId);
            const machineName = machine ? `${machine.machineId} - ${this.formatMachineType(machine.machineType)}` : 'Unknown Machine';
            
            return `
                <tr>
                    <td>${job.jobId || 'N/A'}</td>
                    <td>${machineName}</td>
                    <td>${job.venue}</td>
                    <td>${this.formatDateTime(job.startDate, job.startTime)}</td>
                    <td>${this.formatDateTime(job.endDate, job.endTime)}</td>
                    <td><span class="status-badge status-${job.status?.toLowerCase() || 'scheduled'}">${this.formatJobStatus(job.status || 'SCHEDULED')}</span></td>
                    <td>
                        <div class="action-buttons">
                            <button class="btn-small btn-view" onclick="operatorDashboard.viewJobDetails('${job.jobId}')" title="View Details">
                                <i class="fas fa-eye"></i>
                            </button>
                            <button class="btn-small btn-update" onclick="operatorDashboard.openStatusModal('${job.jobId}')" title="Update Status">
                                <i class="fas fa-flag"></i>
                            </button>
                        </div>
                    </td>
                </tr>
            `;
        }).join('');
        
        // Add fade-in animation
        document.querySelector('.table-section').classList.add('fade-in');
    }
    
    filterJobs() {
        const searchTerm = document.getElementById('searchInput').value.toLowerCase();
        const statusFilter = document.getElementById('statusFilter').value;
        const machineFilter = document.getElementById('machineFilter').value;
        
        const filteredJobs = this.jobs.filter(job => {
            const machine = this.machines.find(m => m.machineId === job.machineId);
            const machineName = machine ? `${machine.machineId} - ${this.formatMachineType(machine.machineType)}`.toLowerCase() : '';
            
            const matchesSearch = !searchTerm || 
                job.venue?.toLowerCase().includes(searchTerm) ||
                job.description?.toLowerCase().includes(searchTerm) ||
                machineName.includes(searchTerm);
            
            const matchesStatus = !statusFilter || job.status === statusFilter;
            const matchesMachine = !machineFilter || job.machineId === machineFilter;
            
            return matchesSearch && matchesStatus && matchesMachine;
        });
        
        this.displayFilteredJobs(filteredJobs);
    }
    
    displayFilteredJobs(filteredJobs) {
        const tbody = document.getElementById('jobTableBody');
        const noDataMessage = document.getElementById('noDataMessage');
        
        if (filteredJobs.length === 0) {
            tbody.innerHTML = `
                <tr>
                    <td colspan="7" class="loading-cell">
                        <div class="loading-spinner">
                            <i class="fas fa-search"></i>
                            <span>No jobs match your search criteria</span>
                        </div>
                    </td>
                </tr>
            `;
            noDataMessage.style.display = 'none';
            return;
        }
        
        noDataMessage.style.display = 'none';
        
        tbody.innerHTML = filteredJobs.map(job => {
            const machine = this.machines.find(m => m.machineId === job.machineId);
            const machineName = machine ? `${machine.machineId} - ${this.formatMachineType(machine.machineType)}` : 'Unknown Machine';
            
            return `
                <tr>
                    <td>${job.jobId || 'N/A'}</td>
                    <td>${machineName}</td>
                    <td>${job.venue}</td>
                    <td>${this.formatDateTime(job.startDate, job.startTime)}</td>
                    <td>${this.formatDateTime(job.endDate, job.endTime)}</td>
                    <td><span class="status-badge status-${job.status?.toLowerCase() || 'scheduled'}">${this.formatJobStatus(job.status || 'SCHEDULED')}</span></td>
                    <td>
                        <div class="action-buttons">
                            <button class="btn-small btn-view" onclick="operatorDashboard.viewJobDetails('${job.jobId}')" title="View Details">
                                <i class="fas fa-eye"></i>
                            </button>
                            <button class="btn-small btn-update" onclick="operatorDashboard.openStatusModal('${job.jobId}')" title="Update Status">
                                <i class="fas fa-flag"></i>
                            </button>
                        </div>
                    </td>
                </tr>
            `;
        }).join('');
    }
    
    switchTab(tabName) {
        // Update tab buttons
        document.querySelectorAll('.tab-button').forEach(btn => btn.classList.remove('active'));
        document.getElementById(`${tabName}Tab`).classList.add('active');
        
        // Update tab content
        document.querySelectorAll('.tab-content').forEach(content => content.classList.remove('active'));
        document.getElementById(`${tabName}Content`).classList.add('active');
        
        this.currentTab = tabName;
    }
    
    openStatusModal(jobId) {
        const job = this.jobs.find(j => j.jobId == jobId);
        if (!job) {
            this.showNotification('Job not found.', 'error');
            return;
        }
        
        this.currentJobId = jobId;
        document.getElementById('statusJobId').value = jobId;
        document.getElementById('newJobStatus').value = job.status || '';
        document.getElementById('completionNotes').value = '';
        
        this.clearAllErrors();
        this.updateCharCounter('completionNotesCounter', 'completionNotes', 1000);
        this.statusModal.classList.add('show');
    }
    
    closeStatusModal() {
        this.statusModal.classList.remove('show');
        this.currentJobId = null;
        this.clearAllErrors();
    }
    
    async handleStatusUpdate(event) {
        event.preventDefault();
        
        if (!this.validateStatusForm()) {
            return;
        }
        
        const saveButton = document.getElementById('updateStatus');
        this.setLoadingState(saveButton, true);
        
        try {
            const formData = new FormData(event.target);
            const jobId = document.getElementById('statusJobId').value;
            
            const updateData = {
                status: formData.get('status'),
                completionNotes: formData.get('completionNotes')?.trim() || null
            };
            
            const response = await fetch(`/api/machine-jobs/${jobId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(updateData)
            });
            
            const result = await response.json();
            
            if (response.ok) {
                this.showNotification('Job status updated successfully!', 'success');
                this.closeStatusModal();
                await this.loadJobs(this.currentUser.userId);
            } else {
                this.showNotification(result.message || 'Failed to update job status', 'error');
            }
        } catch (error) {
            console.error('Update job status error:', error);
            this.showNotification('Network error. Please check your connection.', 'error');
        } finally {
            this.setLoadingState(saveButton, false);
        }
    }
    
    validateStatusForm() {
        let isValid = true;
        
        const newStatus = document.getElementById('newJobStatus').value;
        
        if (!newStatus) {
            this.showFieldError('newJobStatusError', 'Please select a new status');
            isValid = false;
        } else {
            this.clearError('newJobStatusError');
        }
        
        return isValid;
    }
    
    viewJobDetails(jobId) {
        const job = this.jobs.find(j => j.jobId == jobId);
        if (!job) {
            this.showNotification('Job not found.', 'error');
            return;
        }
        
        const machine = this.machines.find(m => m.machineId === job.machineId);
        const machineName = machine ? `${machine.machineId} - ${this.formatMachineType(machine.machineType)}` : 'Unknown Machine';
        
        const detailContent = document.getElementById('jobDetailContent');
        detailContent.innerHTML = `
            <div class="detail-section">
                <h4><i class="fas fa-info-circle"></i> Job Information</h4>
                <div class="detail-meta">
                    <div class="meta-item">
                        <span class="meta-label">Job ID</span>
                        <span class="meta-value">${job.jobId || 'N/A'}</span>
                    </div>
                    <div class="meta-item">
                        <span class="meta-label">Status</span>
                        <span class="meta-value">
                            <span class="status-badge status-${job.status?.toLowerCase() || 'scheduled'}">${this.formatJobStatus(job.status || 'SCHEDULED')}</span>
                        </span>
                    </div>
                    <div class="meta-item">
                        <span class="meta-label">Machine</span>
                        <span class="meta-value">${machineName}</span>
                    </div>
                    <div class="meta-item">
                        <span class="meta-label">Venue</span>
                        <span class="meta-value">${job.venue}</span>
                    </div>
                </div>
            </div>
            
            <div class="detail-section">
                <h4><i class="fas fa-calendar"></i> Schedule Information</h4>
                <div class="detail-meta">
                    <div class="meta-item">
                        <span class="meta-label">Start Date & Time</span>
                        <span class="meta-value">${this.formatDateTime(job.startDate, job.startTime)}</span>
                    </div>
                    <div class="meta-item">
                        <span class="meta-label">End Date & Time</span>
                        <span class="meta-value">${this.formatDateTime(job.endDate, job.endTime)}</span>
                    </div>
                    <div class="meta-item">
                        <span class="meta-label">Estimated Duration</span>
                        <span class="meta-value">${job.estimatedDurationHours ? `${job.estimatedDurationHours} hours` : 'Not specified'}</span>
                    </div>
                </div>
            </div>
            
            ${job.description ? `
            <div class="detail-section">
                <h4><i class="fas fa-comment"></i> Description</h4>
                <div class="detail-content">
                    <p>${job.description}</p>
                </div>
            </div>
            ` : ''}
            
            ${job.completionNotes ? `
            <div class="detail-section">
                <h4><i class="fas fa-sticky-note"></i> Completion Notes</h4>
                <div class="detail-content">
                    <p>${job.completionNotes}</p>
                </div>
            </div>
            ` : ''}
        `;
        
        this.detailsModal.classList.add('show');
    }
    
    closeDetailsModal() {
        this.detailsModal.classList.remove('show');
    }
    
    updateCharCounter(counterId, textareaId, maxLength) {
        const textarea = document.getElementById(textareaId);
        const counter = document.getElementById(counterId);
        const length = textarea.value.length;
        
        counter.textContent = length;
        
        if (length > maxLength * 0.9) {
            counter.className = 'char-counter error';
        } else if (length > maxLength * 0.8) {
            counter.className = 'char-counter warning';
        } else {
            counter.className = 'char-counter';
        }
    }
    
    exportToPDF() {
        if (this.jobs.length === 0) {
            this.showNotification('No jobs to export.', 'warning');
            return;
        }
        
        try {
            const { jsPDF } = window.jspdf;
            const doc = new jsPDF();
            
            // Add title
            doc.setFontSize(20);
            doc.text('My Assigned Jobs Report', 20, 20);
            
            // Add user info
            doc.setFontSize(12);
            doc.text(`Generated for: ${this.currentUser.firstName} ${this.currentUser.lastName}`, 20, 35);
            doc.text(`Generated on: ${new Date().toLocaleDateString()}`, 20, 45);
            
            // Prepare table data
            const tableData = this.jobs.map(job => {
                const machine = this.machines.find(m => m.machineId === job.machineId);
                const machineName = machine ? `${machine.machineId} - ${this.formatMachineType(machine.machineType)}` : 'Unknown Machine';
                
                return [
                    job.jobId || 'N/A',
                    machineName,
                    job.venue,
                    this.formatDateTime(job.startDate, job.startTime),
                    this.formatDateTime(job.endDate, job.endTime),
                    this.formatJobStatus(job.status || 'SCHEDULED')
                ];
            });
            
            // Add table
            doc.autoTable({
                head: [['Job ID', 'Machine', 'Venue', 'Start Date/Time', 'End Date/Time', 'Status']],
                body: tableData,
                startY: 60,
                styles: {
                    fontSize: 8,
                    cellPadding: 3
                },
                headStyles: {
                    fillColor: [234, 179, 8],
                    textColor: [0, 0, 0]
                }
            });
            
            // Save the PDF
            doc.save(`my-jobs-${this.currentUser.userId}-${new Date().toISOString().split('T')[0]}.pdf`);
            
            this.showNotification('PDF exported successfully!', 'success');
        } catch (error) {
            console.error('PDF export error:', error);
            this.showNotification('Failed to export PDF. Please try again.', 'error');
        }
    }
    
    formatMachineType(type) {
        return type.replace(/_/g, ' ').replace(/\b\w/g, l => l.toUpperCase());
    }
    
    formatJobStatus(status) {
        return status.replace(/_/g, ' ').replace(/\b\w/g, l => l.toUpperCase());
    }
    
    formatDateTime(dateString, timeString) {
        if (!dateString) return 'N/A';
        const date = new Date(dateString);
        const formattedDate = date.toLocaleDateString();
        return timeString ? `${formattedDate} ${timeString}` : formattedDate;
    }
    
    showFieldError(errorElementId, message) {
        const errorElement = document.getElementById(errorElementId);
        errorElement.textContent = message;
        errorElement.classList.add('show');
        
        const inputElement = errorElement.previousElementSibling;
        if (inputElement.classList.contains('form-input') || inputElement.classList.contains('form-select') || inputElement.classList.contains('form-textarea')) {
            inputElement.classList.add('error');
        }
    }
    
    clearError(errorElementId) {
        const errorElement = document.getElementById(errorElementId);
        errorElement.classList.remove('show');
        
        const inputElement = errorElement.previousElementSibling;
        if (inputElement.classList.contains('form-input') || inputElement.classList.contains('form-select') || inputElement.classList.contains('form-textarea')) {
            inputElement.classList.remove('error');
        }
    }
    
    clearAllErrors() {
        const errorElements = document.querySelectorAll('.error-message');
        errorElements.forEach(element => {
            element.classList.remove('show');
        });
        
        const inputElements = document.querySelectorAll('.form-input, .form-select, .form-textarea');
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

// Initialize operator dashboard when DOM is loaded
let operatorDashboard;
document.addEventListener('DOMContentLoaded', () => {
    operatorDashboard = new OperatorDashboard();
});
