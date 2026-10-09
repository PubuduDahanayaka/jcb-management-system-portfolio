// Booking Management JavaScript
class BookingManager {
    constructor() {
        this.currentUser = null;
        this.bookings = [];
        this.machines = [];
        this.currentBookingId = null;
        
        this.createModal = document.getElementById('createModal');
        this.editModal = document.getElementById('editModal');
        this.deleteModal = document.getElementById('deleteModal');
        this.notificationContainer = document.getElementById('notificationContainer');
        
        this.initializeEventListeners();
        this.loadInitialData();
    }
    
    initializeEventListeners() {
        // Create Booking
        document.getElementById('addBookingBtn').addEventListener('click', () => this.openCreateModal());
        document.getElementById('createFirstBookingBtn').addEventListener('click', () => this.openCreateModal());
        document.getElementById('closeCreateModal').addEventListener('click', () => this.closeCreateModal());
        document.getElementById('cancelCreate').addEventListener('click', () => this.closeCreateModal());
        document.getElementById('createBookingForm').addEventListener('submit', (e) => this.handleCreateBooking(e));
        
        // Edit Booking
        document.getElementById('closeEditModal').addEventListener('click', () => this.closeEditModal());
        document.getElementById('cancelEdit').addEventListener('click', () => this.closeEditModal());
        document.getElementById('editBookingForm').addEventListener('submit', (e) => this.handleUpdateBooking(e));
        
        // Delete Booking
        document.getElementById('cancelDelete').addEventListener('click', () => this.closeDeleteModal());
        document.getElementById('confirmDelete').addEventListener('click', () => this.handleDeleteBooking());
        
        // Search and Filter
        document.getElementById('searchInput').addEventListener('input', () => this.filterBookings());
        document.getElementById('statusFilter').addEventListener('change', () => this.filterBookings());
        document.getElementById('machineFilter').addEventListener('change', () => this.filterBookings());
        
        // Export PDF
        document.getElementById('exportPdfBtn').addEventListener('click', () => this.exportToPDF());
        
        // Character counters
        document.getElementById('createAdditionalMessage').addEventListener('input', () => this.updateCharCounter('createMessageCounter', 'createAdditionalMessage'));
        document.getElementById('editAdditionalMessage').addEventListener('input', () => this.updateCharCounter('editMessageCounter', 'editAdditionalMessage'));
        
        // Clear errors on input
        document.getElementById('createMachineId').addEventListener('change', () => this.clearError('createMachineIdError'));
        document.getElementById('createRequestedDate').addEventListener('change', () => this.clearError('createRequestedDateError'));
        document.getElementById('createAdditionalMessage').addEventListener('input', () => this.clearError('createAdditionalMessageError'));
        document.getElementById('editRequestedDate').addEventListener('change', () => this.clearError('editRequestedDateError'));
        document.getElementById('editAdditionalMessage').addEventListener('input', () => this.clearError('editAdditionalMessageError'));
        
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
            
            // Load machines for dropdowns (with retry)
            await this.loadMachinesWithRetry();
            
            // Load user's booking requests
            await this.loadBookings(userId);
            
            // Set minimum date for date inputs
            this.setMinDate();
            
        } catch (error) {
            console.error('Error loading initial data:', error);
            this.showNotification('Failed to load data. Please refresh the page.', 'error');
        }
    }
    
    async loadMachinesWithRetry(maxRetries = 3) {
        for (let attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                await this.loadMachines();
                if (this.machines && this.machines.length > 0) {
                    console.log(`Successfully loaded ${this.machines.length} machines on attempt ${attempt}`);
                    return;
                }
            } catch (error) {
                console.warn(`Attempt ${attempt} failed to load machines:`, error);
                if (attempt === maxRetries) {
                    this.showNotification('Failed to load machines after multiple attempts. Please refresh the page.', 'error');
                    throw error;
                }
                // Wait before retrying
                await new Promise(resolve => setTimeout(resolve, 1000 * attempt));
            }
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
            }
        } catch (error) {
            console.error('Error loading user info:', error);
        }
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
            } else {
                console.error('Failed to load machines:', response.status, response.statusText);
                this.showNotification('Failed to load machines. Please refresh the page.', 'error');
            }
        } catch (error) {
            console.error('Error loading machines:', error);
            this.showNotification('Network error loading machines. Please check your connection.', 'error');
        }
    }
    
    populateMachineDropdowns() {
        const createSelect = document.getElementById('createMachineId');
        const filterSelect = document.getElementById('machineFilter');
        
        if (!createSelect || !filterSelect) {
            console.error('Machine dropdown elements not found');
            return;
        }
        
        // Clear existing options
        createSelect.innerHTML = '<option value="">Choose a machine...</option>';
        filterSelect.innerHTML = '<option value="">All Machines</option>';
        
        if (!this.machines || this.machines.length === 0) {
            console.warn('No machines available');
            const noMachinesOption = document.createElement('option');
            noMachinesOption.value = '';
            noMachinesOption.textContent = 'No machines available';
            noMachinesOption.disabled = true;
            createSelect.appendChild(noMachinesOption);
            return;
        }
        
        // Add machine options
        this.machines.forEach(machine => {
            if (machine && machine.machineId && machine.machineType) {
                const createOption = document.createElement('option');
                createOption.value = machine.machineId;
                createOption.textContent = `${machine.machineId} - ${machine.machineType} (${machine.fuelType})`;
                createSelect.appendChild(createOption);
                
                const filterOption = document.createElement('option');
                filterOption.value = machine.machineId;
                filterOption.textContent = `${machine.machineId} - ${machine.machineType}`;
                filterSelect.appendChild(filterOption);
            }
        });
        
        console.log(`Loaded ${this.machines.length} machines into dropdowns`);
    }
    
    async loadBookings(userId) {
        try {
            const response = await fetch(`/api/booking-requests/user/${userId}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                }
            });


            
            if (response.ok) {
                this.bookings = await response.json();
                console.table(this.bookings)
                this.displayBookings();
            } else {
                this.showNotification('Failed to load booking requests.', 'error');
            }
        } catch (error) {
            console.error('Error loading bookings:', error);
            this.showNotification('Network error. Please check your connection.', 'error');
        }
    }
    
    displayBookings() {
        const tbody = document.getElementById('bookingTableBody');
        const noDataMessage = document.getElementById('noDataMessage');
        
        if (this.bookings.length === 0) {
            tbody.innerHTML = '';
            noDataMessage.style.display = 'block';
            return;
        }
        
        noDataMessage.style.display = 'none';
        
        tbody.innerHTML = this.bookings.map(booking => {
            const machine = this.machines.find(m => m.machineId === booking.machineId);
            const machineName = machine ? 
                `${machine.machineId} - ${machine.machineType} (${machine.fuelType})` : 
                `${booking.machineId} - Machine not found`;
            
            return `
                <tr>
                    <td>${booking.bookingId || 'N/A'}</td>
                    <td>${machineName}</td>
                    <td>${this.formatDateTime(booking.requestedDate)}</td>
                    <td><span class="status-badge status-${booking.status.toLowerCase()}">${booking.status}</span></td>
                    <td>${booking.additionalMessage || '-'}</td>
                    <td>
                        <div class="action-buttons">
                            <button class="btn-small btn-edit ${booking.status !== 'PENDING' ? 'btn-disabled' : ''}" 
                                    onclick="bookingManager.openEditModal('${booking.bookingId}')" 
                                    ${booking.status !== 'PENDING' ? 'disabled title="Only pending bookings can be edited"' : ''}>
                                <i class="fas fa-edit"></i>
                            </button>
                            <button class="btn-small btn-delete" onclick="bookingManager.confirmDeleteBooking('${booking.bookingId}')">
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
    
    filterBookings() {
        const searchTerm = document.getElementById('searchInput').value.toLowerCase();
        const statusFilter = document.getElementById('statusFilter').value;
        const machineFilter = document.getElementById('machineFilter').value;
        
        const filteredBookings = this.bookings.filter(booking => {
            const machine = this.machines.find(m => m.machineId === booking.machineId);
            const machineName = machine ? `${machine.machineId} - ${machine.machineType}` : booking.machineId;
            
            const matchesSearch = !searchTerm || 
                booking.bookingId?.toLowerCase().includes(searchTerm) ||
                machineName.toLowerCase().includes(searchTerm) ||
                booking.additionalMessage?.toLowerCase().includes(searchTerm);
            
            const matchesStatus = !statusFilter || booking.status === statusFilter;
            const matchesMachine = !machineFilter || booking.machineId === machineFilter;
            
            return matchesSearch && matchesStatus && matchesMachine;
        });
        
        this.displayFilteredBookings(filteredBookings);
    }
    
    displayFilteredBookings(filteredBookings) {
        const tbody = document.getElementById('bookingTableBody');
        const noDataMessage = document.getElementById('noDataMessage');
        
        if (filteredBookings.length === 0) {
            tbody.innerHTML = `
                <tr>
                    <td colspan="6" class="loading-cell">
                        <div class="loading-spinner">
                            <i class="fas fa-search"></i>
                            <span>No bookings match your search criteria</span>
                        </div>
                    </td>
                </tr>
            `;
            noDataMessage.style.display = 'none';
            return;
        }
        
        noDataMessage.style.display = 'none';
        
        tbody.innerHTML = filteredBookings.map(booking => {
            const machine = this.machines.find(m => m.machineId === booking.machineId);
            const machineName = machine ? 
                `${machine.machineId} - ${machine.machineType} (${machine.fuelType})` : 
                `${booking.machineId} - Machine not found`;
            
            return `
                <tr>
                    <td>${booking.bookingId || 'N/A'}</td>
                    <td>${machineName}</td>
                    <td>${this.formatDateTime(booking.requestedDate)}</td>
                    <td><span class="status-badge status-${booking.status.toLowerCase()}">${booking.status}</span></td>
                    <td>${booking.additionalMessage || '-'}</td>
                    <td>
                        <div class="action-buttons">
                            <button class="btn-small btn-edit ${booking.status !== 'PENDING' ? 'btn-disabled' : ''}" 
                                    onclick="bookingManager.openEditModal('${booking.bookingId}')" 
                                    ${booking.status !== 'PENDING' ? 'disabled title="Only pending bookings can be edited"' : ''}>
                                <i class="fas fa-edit"></i>
                            </button>
                            <button class="btn-small btn-delete" onclick="bookingManager.confirmDeleteBooking('${booking.bookingId}')">
                                <i class="fas fa-trash"></i>
                            </button>
                        </div>
                    </td>
                </tr>
            `;
        }).join('');
    }
    
    setMinDate() {
        const now = new Date();
        const minDateTime = new Date(now.getTime() + 24 * 60 * 60 * 1000); // Tomorrow
        const minDateTimeString = minDateTime.toISOString().slice(0, 16);
        
        document.getElementById('createRequestedDate').min = minDateTimeString;
        document.getElementById('editRequestedDate').min = minDateTimeString;
    }
    
    openCreateModal() {
        this.clearAllErrors();
        this.createModal.classList.add('show');
        document.getElementById('createMachineId').focus();
    }
    
    closeCreateModal() {
        this.createModal.classList.remove('show');
        document.getElementById('createBookingForm').reset();
        this.clearAllErrors();
        this.updateCharCounter('createMessageCounter', 'createAdditionalMessage');
    }
    
    async handleCreateBooking(event) {
        event.preventDefault();
        
        if (!this.validateCreateForm()) {
            return;
        }
        
        const saveButton = document.getElementById('saveBooking');
        this.setLoadingState(saveButton, true);
        
        try {
            const formData = new FormData(event.target);
            const bookingData = {
                machineId: formData.get('machineId'),
                userId: this.currentUser.userId,
                requestedDate: formData.get('requestedDate'),
                additionalMessage: formData.get('additionalMessage') || null
            };
            
            const response = await fetch('/api/booking-requests', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(bookingData)
            });
            
            const result = await response.json();
            
            if (response.ok) {
                this.showNotification('Booking request created successfully!', 'success');
                this.closeCreateModal();
                await this.loadBookings(this.currentUser.userId);
            } else {
                this.showNotification(result.message || 'Failed to create booking request', 'error');
            }
        } catch (error) {
            console.error('Create booking error:', error);
            this.showNotification('Network error. Please check your connection.', 'error');
        } finally {
            this.setLoadingState(saveButton, false);
        }
    }
    
    validateCreateForm() {
        let isValid = true;
        
        if (!this.validateMachineSelection()) isValid = false;
        if (!this.validateRequestedDate('create')) isValid = false;
        if (!this.validateAdditionalMessage('create')) isValid = false;
        
        return isValid;
    }
    
    validateMachineSelection() {
        const machineId = document.getElementById('createMachineId').value;
        
        if (!machineId) {
            this.showFieldError('createMachineIdError', 'Please select a machine');
            return false;
        }
        
        this.clearError('createMachineIdError');
        return true;
    }
    
    validateRequestedDate(formType) {
        const dateInput = document.getElementById(`${formType}RequestedDate`);
        const requestedDate = new Date(dateInput.value);
        const now = new Date();
        const tomorrow = new Date(now.getTime() + 24 * 60 * 60 * 1000);
        
        if (!dateInput.value) {
            this.showFieldError(`${formType}RequestedDateError`, 'Requested date is required');
            return false;
        }
        
        if (requestedDate < tomorrow) {
            this.showFieldError(`${formType}RequestedDateError`, 'Requested date must be at least tomorrow');
            return false;
        }
        
        this.clearError(`${formType}RequestedDateError`);
        return true;
    }
    
    validateAdditionalMessage(formType) {
        const message = document.getElementById(`${formType}AdditionalMessage`).value;
        
        if (message && message.length > 500) {
            this.showFieldError(`${formType}AdditionalMessageError`, 'Additional message cannot exceed 500 characters');
            return false;
        }
        
        this.clearError(`${formType}AdditionalMessageError`);
        return true;
    }
    
    openEditModal(bookingId) {
        console.log(bookingId)
        console.log(this.bookings)
        const booking = this.bookings.find(b => b.bookingId == bookingId);
        if (!booking) {
            this.showNotification('Booking request not found.', 'error');
            return;
        }
        
        if (booking.status !== 'PENDING') {
            this.showNotification('Only pending booking requests can be edited.', 'warning');
            return;
        }
        
        // Populate form with booking data
        document.getElementById('editBookingId').value = booking.bookingId;
        
        const machine = this.machines.find(m => m.machineId === booking.machineId);
        const machineDisplayName = machine ? 
            `${machine.machineId} - ${machine.machineType} (${machine.fuelType})` : 
            `${booking.machineId} - Machine not found`;
        document.getElementById('editMachineId').value = machineDisplayName;
        
        const requestedDate = new Date(booking.requestedDate);
        document.getElementById('editRequestedDate').value = requestedDate.toISOString().slice(0, 16);
        
        document.getElementById('editStatus').value = booking.status;
        document.getElementById('editCreatedDate').value = this.formatDateTime(booking.createdDate || booking.requestedDate);
        document.getElementById('editAdditionalMessage').value = booking.additionalMessage || '';
        
        this.clearAllErrors();
        this.updateCharCounter('editMessageCounter', 'editAdditionalMessage');
        this.editModal.classList.add('show');
    }
    
    closeEditModal() {
        this.editModal.classList.remove('show');
        this.clearAllErrors();
    }
    
    async handleUpdateBooking(event) {
        event.preventDefault();
        
        if (!this.validateEditForm()) {
            return;
        }
        
        const saveButton = document.getElementById('updateBooking');
        this.setLoadingState(saveButton, true);
        
        try {
            const formData = new FormData(event.target);
            const bookingId = document.getElementById('editBookingId').value;
            
            const updateData = {
                requestedDate: formData.get('requestedDate'),
                additionalMessage: formData.get('additionalMessage') || null
            };
            
            const response = await fetch(`/api/booking-requests/${bookingId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(updateData)
            });
            
            const result = await response.json();
            
            if (response.ok) {
                this.showNotification('Booking request updated successfully!', 'success');
                this.closeEditModal();
                await this.loadBookings(this.currentUser.userId);
            } else {
                this.showNotification(result.message || 'Failed to update booking request', 'error');
            }
        } catch (error) {
            console.error('Update booking error:', error);
            this.showNotification('Network error. Please check your connection.', 'error');
        } finally {
            this.setLoadingState(saveButton, false);
        }
    }
    
    validateEditForm() {
        let isValid = true;
        
        if (!this.validateRequestedDate('edit')) isValid = false;
        if (!this.validateAdditionalMessage('edit')) isValid = false;
        
        return isValid;
    }
    
    confirmDeleteBooking(bookingId) {
        this.currentBookingId = bookingId;
        this.deleteModal.classList.add('show');
    }
    
    closeDeleteModal() {
        this.deleteModal.classList.remove('show');
        this.currentBookingId = null;
    }
    
    async handleDeleteBooking() {
        if (!this.currentBookingId) return;
        
        const deleteButton = document.getElementById('confirmDelete');
        this.setLoadingState(deleteButton, true);
        
        try {
            const response = await fetch(`/api/booking-requests/${this.currentBookingId}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                }
            });
            
            if (response.ok) {
                this.showNotification('Booking request deleted successfully!', 'success');
                this.closeDeleteModal();
                await this.loadBookings(this.currentUser.userId);
            } else {
                const result = await response.json();
                this.showNotification(result.message || 'Failed to delete booking request', 'error');
            }
        } catch (error) {
            console.error('Delete booking error:', error);
            this.showNotification('Network error. Please check your connection.', 'error');
        } finally {
            this.setLoadingState(deleteButton, false);
        }
    }
    
    updateCharCounter(counterId, textareaId) {
        const textarea = document.getElementById(textareaId);
        const counter = document.getElementById(counterId);
        const length = textarea.value.length;
        
        counter.textContent = length;
        
        if (length > 450) {
            counter.className = 'char-counter error';
        } else if (length > 400) {
            counter.className = 'char-counter warning';
        } else {
            counter.className = 'char-counter';
        }
    }
    
    exportToPDF() {
        if (this.bookings.length === 0) {
            this.showNotification('No booking requests to export.', 'warning');
            return;
        }
        
        try {
            const { jsPDF } = window.jspdf;
            const doc = new jsPDF();
            
            // Add title
            doc.setFontSize(20);
            doc.text('My Booking Requests', 20, 20);
            
            // Add user info
            doc.setFontSize(12);
            doc.text(`Generated for: ${this.currentUser.firstName} ${this.currentUser.lastName}`, 20, 35);
            doc.text(`Generated on: ${new Date().toLocaleDateString()}`, 20, 45);
            
            // Prepare table data
            const tableData = this.bookings.map(booking => {
                const machine = this.machines.find(m => m.machineId === booking.machineId);
                const machineName = machine ? 
                    `${machine.machineId} - ${machine.machineType} (${machine.fuelType})` : 
                    `${booking.machineId} - Machine not found`;
                
                return [
                    booking.bookingId || 'N/A',
                    machineName,
                    this.formatDateTime(booking.requestedDate),
                    booking.status,
                    booking.additionalMessage || '-'
                ];
            });
            
            // Add table
            doc.autoTable({
                head: [['Request ID', 'Machine', 'Requested Date', 'Status', 'Message']],
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
            doc.save(`booking-requests-${this.currentUser.userId}-${new Date().toISOString().split('T')[0]}.pdf`);
            
            this.showNotification('PDF exported successfully!', 'success');
        } catch (error) {
            console.error('PDF export error:', error);
            this.showNotification('Failed to export PDF. Please try again.', 'error');
        }
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
    
    formatDateTime(dateString) {
        if (!dateString) return 'N/A';
        const date = new Date(dateString);
        return date.toLocaleDateString() + ' ' + date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
    }
}

// Initialize booking manager when DOM is loaded
let bookingManager;
document.addEventListener('DOMContentLoaded', () => {
    bookingManager = new BookingManager();
});
