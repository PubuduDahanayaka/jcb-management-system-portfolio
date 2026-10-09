// Supervisor Dashboard JavaScript
class SupervisorDashboard {
    constructor() {
        this.currentUser = null;
        this.jobs = [];
        this.operators = [];
        this.machines = [];
        this.currentJobId = null;
        this.currentTab = 'profile';
        
        this.createModal = document.getElementById('createModal');
        this.editModal = document.getElementById('editModal');
        this.deleteModal = document.getElementById('deleteModal');
        this.notificationContainer = document.getElementById('notificationContainer');
        
        this.initializeEventListeners();
        this.loadInitialData();
    }
    
    initializeEventListeners() {
        // Tab Navigation
        document.getElementById('profileTab').addEventListener('click', () => this.switchTab('profile'));
        document.getElementById('jobsTab').addEventListener('click', () => this.switchTab('jobs'));
        
        // Create Job
        document.getElementById('addJobBtn').addEventListener('click', () => this.openCreateModal());
        document.getElementById('createFirstJobBtn').addEventListener('click', () => this.openCreateModal());
        document.getElementById('closeCreateModal').addEventListener('click', () => this.closeCreateModal());
        document.getElementById('cancelCreate').addEventListener('click', () => this.closeCreateModal());
        document.getElementById('createJobForm').addEventListener('submit', (e) => this.handleCreateJob(e));
        
        // Edit Job
        document.getElementById('closeEditModal').addEventListener('click', () => this.closeEditModal());
        document.getElementById('cancelEdit').addEventListener('click', () => this.closeEditModal());
        document.getElementById('editJobForm').addEventListener('submit', (e) => this.handleUpdateJob(e));
        
        // Delete Job
        document.getElementById('cancelDelete').addEventListener('click', () => this.closeDeleteModal());
        document.getElementById('confirmDelete').addEventListener('click', () => this.handleDeleteJob());
        
        // Search and Filter
        document.getElementById('searchInput').addEventListener('input', () => this.filterJobs());
        document.getElementById('statusFilter').addEventListener('change', () => this.filterJobs());
        document.getElementById('operatorFilter').addEventListener('change', () => this.filterJobs());
        document.getElementById('machineFilter').addEventListener('change', () => this.filterJobs());
        
        // Export PDF
        document.getElementById('exportPdfBtn').addEventListener('click', () => this.exportToPDF());
        
        // Character counters
        document.getElementById('createVenue').addEventListener('input', () => this.updateCharCounter('createVenueCounter', 'createVenue', 200));
        document.getElementById('createDescription').addEventListener('input', () => this.updateCharCounter('createDescriptionCounter', 'createDescription', 500));
        document.getElementById('editVenue').addEventListener('input', () => this.updateCharCounter('editVenueCounter', 'editVenue', 200));
        document.getElementById('editDescription').addEventListener('input', () => this.updateCharCounter('editDescriptionCounter', 'editDescription', 500));
        
        // Clear errors on input
        document.getElementById('createAssignedUserId').addEventListener('change', () => this.clearError('createAssignedUserIdError'));
        document.getElementById('createMachineId').addEventListener('change', () => this.clearError('createMachineIdError'));
        document.getElementById('createVenue').addEventListener('input', () => this.clearError('createVenueError'));
        document.getElementById('createStartDate').addEventListener('change', () => this.clearError('createStartDateError'));
        document.getElementById('createStartTime').addEventListener('change', () => this.clearError('createStartTimeError'));
        document.getElementById('createEndDate').addEventListener('change', () => this.clearError('createEndDateError'));
        document.getElementById('createEndTime').addEventListener('change', () => this.clearError('createEndTimeError'));
        document.getElementById('editJobStatus').addEventListener('change', () => this.clearError('editJobStatusError'));
        document.getElementById('editVenue').addEventListener('input', () => this.clearError('editVenueError'));
        document.getElementById('editStartDate').addEventListener('change', () => this.clearError('editStartDateError'));
        document.getElementById('editStartTime').addEventListener('change', () => this.clearError('editStartTimeError'));
        document.getElementById('editEndDate').addEventListener('change', () => this.clearError('editEndDateError'));
        document.getElementById('editEndTime').addEventListener('change', () => this.clearError('editEndTimeError'));
        
        // Close modals on overlay click
        this.createModal.addEventListener('click', (e) => {
            if (e.target === this.createModal) this.closeCreateModal();
        });
        
        this.editModal.addEventListener('click', (e) => {
            if (e.target === this.editModal) this.closeEditModal();
        });
        
        this.deleteModal.addEventListener('click', (e) => {
            if (e.target === this.deleteModal) this.closeDeleteModal();
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
            
            // Load operators (users with OPERATOR role)
            await this.loadOperators();
            
            // Load machines
            await this.loadMachines();
            
            // Load jobs
            await this.loadJobs();
            
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
    
    async loadOperators() {
        try {
            const response = await fetch('/api/users', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                }
            });
            
            if (response.ok) {
                const allUsers = await response.json();
                this.operators = allUsers.filter(user => user.userType === 'OPERATOR');
                this.populateOperatorDropdowns();
            }
        } catch (error) {
            console.error('Error loading operators:', error);
        }
    }
    
    populateOperatorDropdowns() {
        const createSelect = document.getElementById('createAssignedUserId');
        const filterSelect = document.getElementById('operatorFilter');
        
        // Clear existing options
        createSelect.innerHTML = '<option value="">Select an operator...</option>';
        filterSelect.innerHTML = '<option value="">All Operators</option>';
        
        if (this.operators.length === 0) {
            const noOperatorsOption = document.createElement('option');
            noOperatorsOption.value = '';
            noOperatorsOption.textContent = 'No operators available';
            noOperatorsOption.disabled = true;
            createSelect.appendChild(noOperatorsOption);
            return;
        }
        
        // Add operator options
        this.operators.forEach(operator => {
            const optionText = `${operator.firstName} ${operator.lastName} (${operator.userId})`;
            
            const createOption = document.createElement('option');
            createOption.value = operator.userId;
            createOption.textContent = optionText;
            createSelect.appendChild(createOption);
            
            const filterOption = document.createElement('option');
            filterOption.value = operator.userId;
            filterOption.textContent = optionText;
            filterSelect.appendChild(filterOption);
        });
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
                this.populateMachineDropdowns();
            }
        } catch (error) {
            console.error('Error loading machines:', error);
        }
    }
    
    populateMachineDropdowns() {
        const createSelect = document.getElementById('createMachineId');
        const filterSelect = document.getElementById('machineFilter');
        
        // Clear existing options
        createSelect.innerHTML = '<option value="">Select a machine...</option>';
        filterSelect.innerHTML = '<option value="">All Machines</option>';
        
        if (this.machines.length === 0) {
            const noMachinesOption = document.createElement('option');
            noMachinesOption.value = '';
            noMachinesOption.textContent = 'No machines available';
            noMachinesOption.disabled = true;
            createSelect.appendChild(noMachinesOption);
            return;
        }
        
        // Add machine options
        this.machines.forEach(machine => {
            const optionText = `${machine.machineId} - ${this.formatMachineType(machine.machineType)}`;
            
            const createOption = document.createElement('option');
            createOption.value = machine.machineId;
            createOption.textContent = optionText;
            createSelect.appendChild(createOption);
            
            const filterOption = document.createElement('option');
            filterOption.value = machine.machineId;
            filterOption.textContent = optionText;
            filterSelect.appendChild(filterOption);
        });
    }
    
    async loadJobs() {
        try {
            const response = await fetch('/api/machine-jobs', {
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
            const operator = this.operators.find(o => o.userId === job.assignedUserId);
            const machine = this.machines.find(m => m.machineId === job.machineId);
            
            const operatorName = operator ? `${operator.firstName} ${operator.lastName}` : 'Unknown Operator';
            const machineName = machine ? `${machine.machineId} - ${this.formatMachineType(machine.machineType)}` : 'Unknown Machine';
            
            return `
                <tr>
                    <td>${job.jobId || 'N/A'}</td>
                    <td>${operatorName}</td>
                    <td>${machineName}</td>
                    <td>${job.venue}</td>
                    <td>${this.formatDateTime(job.startDate, job.startTime)}</td>
                    <td>${this.formatDateTime(job.endDate, job.endTime)}</td>
                    <td><span class="status-badge status-${job.status?.toLowerCase() || 'scheduled'}">${this.formatJobStatus(job.status || 'SCHEDULED')}</span></td>
                    <td>
                        <div class="action-buttons">
                            <button class="btn-small btn-edit" onclick="supervisorDashboard.openEditModal('${job.jobId}')" title="Edit Job">
                                <i class="fas fa-edit"></i>
                            </button>
                            <button class="btn-small btn-delete" onclick="supervisorDashboard.confirmDeleteJob('${job.jobId}')" title="Delete Job">
                                <i class="fas fa-trash"></i>
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
        const operatorFilter = document.getElementById('operatorFilter').value;
        const machineFilter = document.getElementById('machineFilter').value;
        
        const filteredJobs = this.jobs.filter(job => {
            const operator = this.operators.find(o => o.userId === job.assignedUserId);
            const machine = this.machines.find(m => m.machineId === job.machineId);
            
            const operatorName = operator ? `${operator.firstName} ${operator.lastName}`.toLowerCase() : '';
            const machineName = machine ? `${machine.machineId} - ${this.formatMachineType(machine.machineType)}`.toLowerCase() : '';
            
            const matchesSearch = !searchTerm || 
                job.venue?.toLowerCase().includes(searchTerm) ||
                job.description?.toLowerCase().includes(searchTerm) ||
                operatorName.includes(searchTerm) ||
                machineName.includes(searchTerm);
            
            const matchesStatus = !statusFilter || job.status === statusFilter;
            const matchesOperator = !operatorFilter || job.assignedUserId === operatorFilter;
            const matchesMachine = !machineFilter || job.machineId === machineFilter;
            
            return matchesSearch && matchesStatus && matchesOperator && matchesMachine;
        });
        
        this.displayFilteredJobs(filteredJobs);
    }
    
    displayFilteredJobs(filteredJobs) {
        const tbody = document.getElementById('jobTableBody');
        const noDataMessage = document.getElementById('noDataMessage');
        
        if (filteredJobs.length === 0) {
            tbody.innerHTML = `
                <tr>
                    <td colspan="8" class="loading-cell">
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
            const operator = this.operators.find(o => o.userId === job.assignedUserId);
            const machine = this.machines.find(m => m.machineId === job.machineId);
            
            const operatorName = operator ? `${operator.firstName} ${operator.lastName}` : 'Unknown Operator';
            const machineName = machine ? `${machine.machineId} - ${this.formatMachineType(machine.machineType)}` : 'Unknown Machine';
            
            return `
                <tr>
                    <td>${job.jobId || 'N/A'}</td>
                    <td>${operatorName}</td>
                    <td>${machineName}</td>
                    <td>${job.venue}</td>
                    <td>${this.formatDateTime(job.startDate, job.startTime)}</td>
                    <td>${this.formatDateTime(job.endDate, job.endTime)}</td>
                    <td><span class="status-badge status-${job.status?.toLowerCase() || 'scheduled'}">${this.formatJobStatus(job.status || 'SCHEDULED')}</span></td>
                    <td>
                        <div class="action-buttons">
                            <button class="btn-small btn-edit" onclick="supervisorDashboard.openEditModal('${job.jobId}')" title="Edit Job">
                                <i class="fas fa-edit"></i>
                            </button>
                            <button class="btn-small btn-delete" onclick="supervisorDashboard.confirmDeleteJob('${job.jobId}')" title="Delete Job">
                                <i class="fas fa-trash"></i>
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
    
    openCreateModal() {
        this.clearAllErrors();
        this.createModal.classList.add('show');
        document.getElementById('createVenue').focus();
    }
    
    closeCreateModal() {
        this.createModal.classList.remove('show');
        document.getElementById('createJobForm').reset();
        this.clearAllErrors();
        this.updateCharCounter('createVenueCounter', 'createVenue', 200);
        this.updateCharCounter('createDescriptionCounter', 'createDescription', 500);
    }
    
    async handleCreateJob(event) {
        event.preventDefault();
        
        if (!this.validateCreateForm()) {
            return;
        }
        
        const saveButton = document.getElementById('saveJob');
        this.setLoadingState(saveButton, true);
        
        try {
            const formData = new FormData(event.target);
            const jobData = {
                assignedUserId: formData.get('assignedUserId'),
                machineId: formData.get('machineId'),
                venue: formData.get('venue').trim(),
                startDate: formData.get('startDate'),
                startTime: formData.get('startTime'),
                endDate: formData.get('endDate'),
                endTime: formData.get('endTime'),
                description: formData.get('description')?.trim() || null,
                estimatedDurationHours: formData.get('estimatedDurationHours') ? parseFloat(formData.get('estimatedDurationHours')) : null
            };
            
            const response = await fetch('/api/machine-jobs', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(jobData)
            });
            
            const result = await response.json();
            
            if (response.ok) {
                this.showNotification('Job created successfully!', 'success');
                this.closeCreateModal();
                await this.loadJobs();
            } else {
                this.showNotification(result.message || 'Failed to create job', 'error');
            }
        } catch (error) {
            console.error('Create job error:', error);
            this.showNotification('Network error. Please check your connection.', 'error');
        } finally {
            this.setLoadingState(saveButton, false);
        }
    }
    
    validateCreateForm() {
        let isValid = true;
        
        if (!this.validateAssignedUser('create')) isValid = false;
        if (!this.validateMachine('create')) isValid = false;
        if (!this.validateVenue('create')) isValid = false;
        if (!this.validateStartDate('create')) isValid = false;
        if (!this.validateStartTime('create')) isValid = false;
        if (!this.validateEndDate('create')) isValid = false;
        if (!this.validateEndTime('create')) isValid = false;
        
        return isValid;
    }
    
    validateAssignedUser(formType) {
        const assignedUserId = document.getElementById(`${formType}AssignedUserId`).value;
        
        if (!assignedUserId) {
            this.showFieldError(`${formType}AssignedUserIdError`, 'Please select an operator');
            return false;
        }
        
        this.clearError(`${formType}AssignedUserIdError`);
        return true;
    }
    
    validateMachine(formType) {
        const machineId = document.getElementById(`${formType}MachineId`).value;
        
        if (!machineId) {
            this.showFieldError(`${formType}MachineIdError`, 'Please select a machine');
            return false;
        }
        
        this.clearError(`${formType}MachineIdError`);
        return true;
    }
    
    validateVenue(formType) {
        const venue = document.getElementById(`${formType}Venue`).value.trim();
        const venueRegex = /^.{3,200}$/;
        
        if (!venue) {
            this.showFieldError(`${formType}VenueError`, 'Venue is required');
            return false;
        }
        
        if (!venueRegex.test(venue)) {
            this.showFieldError(`${formType}VenueError`, 'Venue must be between 3 and 200 characters');
            return false;
        }
        
        this.clearError(`${formType}VenueError`);
        return true;
    }
    
    validateStartDate(formType) {
        const startDate = document.getElementById(`${formType}StartDate`).value;
        
        if (!startDate) {
            this.showFieldError(`${formType}StartDateError`, 'Start date is required');
            return false;
        }
        
        this.clearError(`${formType}StartDateError`);
        return true;
    }
    
    validateStartTime(formType) {
        const startTime = document.getElementById(`${formType}StartTime`).value;
        
        if (!startTime) {
            this.showFieldError(`${formType}StartTimeError`, 'Start time is required');
            return false;
        }
        
        this.clearError(`${formType}StartTimeError`);
        return true;
    }
    
    validateEndDate(formType) {
        const endDate = document.getElementById(`${formType}EndDate`).value;
        
        if (!endDate) {
            this.showFieldError(`${formType}EndDateError`, 'End date is required');
            return false;
        }
        
        this.clearError(`${formType}EndDateError`);
        return true;
    }
    
    validateEndTime(formType) {
        const endTime = document.getElementById(`${formType}EndTime`).value;
        
        if (!endTime) {
            this.showFieldError(`${formType}EndTimeError`, 'End time is required');
            return false;
        }
        
        this.clearError(`${formType}EndTimeError`);
        return true;
    }
    
    openEditModal(jobId) {
        const job = this.jobs.find(j => j.jobId == jobId);
        if (!job) {
            this.showNotification('Job not found.', 'error');
            return;
        }
        
        // Populate form with job data
        document.getElementById('editJobId').value = job.jobId;
        document.getElementById('editJobStatus').value = job.status || '';
        document.getElementById('editVenue').value = job.venue || '';
        document.getElementById('editStartDate').value = job.startDate || '';
        document.getElementById('editStartTime').value = job.startTime || '';
        document.getElementById('editEndDate').value = job.endDate || '';
        document.getElementById('editEndTime').value = job.endTime || '';
        document.getElementById('editEstimatedDurationHours').value = job.estimatedDurationHours || '';
        document.getElementById('editDescription').value = job.description || '';
        
        this.clearAllErrors();
        this.updateCharCounter('editVenueCounter', 'editVenue', 200);
        this.updateCharCounter('editDescriptionCounter', 'editDescription', 500);
        this.editModal.classList.add('show');
    }
    
    closeEditModal() {
        this.editModal.classList.remove('show');
        this.clearAllErrors();
    }
    
    async handleUpdateJob(event) {
        event.preventDefault();
        
        if (!this.validateEditForm()) {
            return;
        }
        
        const saveButton = document.getElementById('updateJob');
        this.setLoadingState(saveButton, true);
        
        try {
            const formData = new FormData(event.target);
            const jobId = document.getElementById('editJobId').value;
            
            const updateData = {
                venue: formData.get('venue').trim(),
                startDate: formData.get('startDate'),
                startTime: formData.get('startTime'),
                endDate: formData.get('endDate'),
                endTime: formData.get('endTime'),
                status: formData.get('jobStatus'),
                description: formData.get('description')?.trim() || null,
                estimatedDurationHours: formData.get('estimatedDurationHours') ? parseFloat(formData.get('estimatedDurationHours')) : null
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
                this.showNotification('Job updated successfully!', 'success');
                this.closeEditModal();
                await this.loadJobs();
            } else {
                this.showNotification(result.message || 'Failed to update job', 'error');
            }
        } catch (error) {
            console.error('Update job error:', error);
            this.showNotification('Network error. Please check your connection.', 'error');
        } finally {
            this.setLoadingState(saveButton, false);
        }
    }
    
    validateEditForm() {
        let isValid = true;
        
        if (!this.validateJobStatus('edit')) isValid = false;
        if (!this.validateVenue('edit')) isValid = false;
        if (!this.validateStartDate('edit')) isValid = false;
        if (!this.validateStartTime('edit')) isValid = false;
        if (!this.validateEndDate('edit')) isValid = false;
        if (!this.validateEndTime('edit')) isValid = false;
        
        return isValid;
    }
    
    validateJobStatus(formType) {
        const jobStatus = document.getElementById(`${formType}JobStatus`).value;
        
        if (!jobStatus) {
            this.showFieldError(`${formType}JobStatusError`, 'Please select a job status');
            return false;
        }
        
        this.clearError(`${formType}JobStatusError`);
        return true;
    }
    
    confirmDeleteJob(jobId) {
        this.currentJobId = jobId;
        this.deleteModal.classList.add('show');
    }
    
    closeDeleteModal() {
        this.deleteModal.classList.remove('show');
        this.currentJobId = null;
    }
    
    async handleDeleteJob() {
        if (!this.currentJobId) return;
        
        const deleteButton = document.getElementById('confirmDelete');
        this.setLoadingState(deleteButton, true);
        
        try {
            const response = await fetch(`/api/machine-jobs/${this.currentJobId}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                }
            });
            
            if (response.ok) {
                this.showNotification('Job deleted successfully!', 'success');
                this.closeDeleteModal();
                await this.loadJobs();
            } else {
                const result = await response.json();
                this.showNotification(result.message || 'Failed to delete job', 'error');
            }
        } catch (error) {
            console.error('Delete job error:', error);
            this.showNotification('Network error. Please check your connection.', 'error');
        } finally {
            this.setLoadingState(deleteButton, false);
        }
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
            doc.text('Job Management Report', 20, 20);
            
            // Add user info
            doc.setFontSize(12);
            doc.text(`Generated for: ${this.currentUser.firstName} ${this.currentUser.lastName}`, 20, 35);
            doc.text(`Generated on: ${new Date().toLocaleDateString()}`, 20, 45);
            
            // Prepare table data
            const tableData = this.jobs.map(job => {
                const operator = this.operators.find(o => o.userId === job.assignedUserId);
                const machine = this.machines.find(m => m.machineId === job.machineId);
                
                const operatorName = operator ? `${operator.firstName} ${operator.lastName}` : 'Unknown Operator';
                const machineName = machine ? `${machine.machineId} - ${this.formatMachineType(machine.machineType)}` : 'Unknown Machine';
                
                return [
                    job.jobId || 'N/A',
                    operatorName,
                    machineName,
                    job.venue,
                    this.formatDateTime(job.startDate, job.startTime),
                    this.formatDateTime(job.endDate, job.endTime),
                    this.formatJobStatus(job.status || 'SCHEDULED')
                ];
            });
            
            // Add table
            doc.autoTable({
                head: [['Job ID', 'Operator', 'Machine', 'Venue', 'Start Date/Time', 'End Date/Time', 'Status']],
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
            doc.save(`jobs-${this.currentUser.userId}-${new Date().toISOString().split('T')[0]}.pdf`);
            
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

// Initialize supervisor dashboard when DOM is loaded
let supervisorDashboard;
document.addEventListener('DOMContentLoaded', () => {
    supervisorDashboard = new SupervisorDashboard();
});
