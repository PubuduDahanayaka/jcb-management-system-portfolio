// Admin Booking Management JavaScript
class AdminBookingManager {
    constructor() {
        this.bookings = [];
        this.filteredBookings = [];
        this.machines = [];
        this.currentBookingId = null;
        this.init();
    }

    init() {
        this.setupEventListeners();
        this.loadBookings();
        this.loadMachines();
        this.loadUserInfo();
    }

    setupEventListeners() {
        // Search functionality
        const searchInput = document.getElementById('searchInput');
        if (searchInput) {
            searchInput.addEventListener('input', (e) => {
                this.filterBookings();
            });
        }

        // Filter functionality
        const statusFilter = document.getElementById('statusFilter');
        const machineFilter = document.getElementById('machineFilter');
        
        if (statusFilter) {
            statusFilter.addEventListener('change', () => {
                this.filterBookings();
            });
        }
        
        if (machineFilter) {
            machineFilter.addEventListener('change', () => {
                this.filterBookings();
            });
        }

        // Export PDF
        const exportPdfBtn = document.getElementById('exportPdfBtn');
        if (exportPdfBtn) {
            exportPdfBtn.addEventListener('click', () => {
                this.exportToPDF();
            });
        }

        // Modal controls
        this.setupModalControls();
    }

    setupModalControls() {
        // Details modal
        const detailsModal = document.getElementById('detailsModal');
        const closeDetailsModal = document.getElementById('closeDetailsModal');
        const closeDetailsBtn = document.getElementById('closeDetailsBtn');

        if (closeDetailsModal) {
            closeDetailsModal.addEventListener('click', () => {
                this.closeModal('detailsModal');
            });
        }

        if (closeDetailsBtn) {
            closeDetailsBtn.addEventListener('click', () => {
                this.closeModal('detailsModal');
            });
        }

        // Status update modal
        const statusModal = document.getElementById('statusModal');
        const cancelStatusUpdate = document.getElementById('cancelStatusUpdate');
        const confirmStatusUpdate = document.getElementById('confirmStatusUpdate');

        if (cancelStatusUpdate) {
            cancelStatusUpdate.addEventListener('click', () => {
                this.closeModal('statusModal');
            });
        }

        if (confirmStatusUpdate) {
            confirmStatusUpdate.addEventListener('click', () => {
                this.updateBookingStatus();
            });
        }

        // Close modals on overlay click
        if (detailsModal) {
            detailsModal.addEventListener('click', (e) => {
                if (e.target === detailsModal) {
                    this.closeModal('detailsModal');
                }
            });
        }

        if (statusModal) {
            statusModal.addEventListener('click', (e) => {
                if (e.target === statusModal) {
                    this.closeModal('statusModal');
                }
            });
        }
    }

    async loadUserInfo() {
        try {
            const userId = sessionStorage.getItem('userId');
            if (!userId) {
                window.location.href = '/login';
                return;
            }

            const response = await fetch(`/api/users/${userId}`);
            if (response.ok) {
                const user = await response.json();
                const userNameElement = document.getElementById('userName');
                if (userNameElement) {
                    userNameElement.textContent = `${user.firstName} ${user.lastName}`;
                }
            }
        } catch (error) {
            console.error('Error loading user info:', error);
        }
    }

    async loadBookings() {
        try {
            this.showLoading(true);
            
            const response = await fetch('/api/booking-requests');
            if (response.ok) {
                const data = await response.json();
                console.log('Booking data received:', data); // Debug log
                this.bookings = data;
                this.filteredBookings = [...this.bookings];
                this.renderBookings();
                this.updateStatistics();
            } else {
                this.showNotification('Failed to load booking requests', 'error');
            }
        } catch (error) {
            console.error('Error loading booking requests:', error);
            this.showNotification('Error loading booking requests', 'error');
        } finally {
            this.showLoading(false);
        }
    }

    async loadMachines() {
        try {
            const response = await fetch('/api/machines');
            if (response.ok) {
                const data = await response.json();
                this.machines = data;
                this.populateMachineFilter();
            }
        } catch (error) {
            console.error('Error loading machines:', error);
        }
    }

    populateMachineFilter() {
        const machineFilter = document.getElementById('machineFilter');
        if (!machineFilter) return;

        // Clear existing options except the first one
        machineFilter.innerHTML = '<option value="">All Machines</option>';
        
        this.machines.forEach(machine => {
            const option = document.createElement('option');
            option.value = machine.machineId;
            option.textContent = `${machine.machineId} - ${machine.machineType}`;
            machineFilter.appendChild(option);
        });
    }

    filterBookings() {
        const searchTerm = document.getElementById('searchInput').value.toLowerCase();
        const statusFilter = document.getElementById('statusFilter').value;
        const machineFilter = document.getElementById('machineFilter').value;

        this.filteredBookings = this.bookings.filter(booking => {
            const matchesSearch = !searchTerm || 
                (booking.userInfo && booking.userInfo.firstName && booking.userInfo.firstName.toLowerCase().includes(searchTerm)) ||
                (booking.userInfo && booking.userInfo.lastName && booking.userInfo.lastName.toLowerCase().includes(searchTerm)) ||
                (booking.machineInfo && booking.machineInfo.machineId && booking.machineInfo.machineId.toLowerCase().includes(searchTerm)) ||
                (booking.machineInfo && booking.machineInfo.machineType && booking.machineInfo.machineType.toLowerCase().includes(searchTerm)) ||
                (booking.additionalMessage && booking.additionalMessage.toLowerCase().includes(searchTerm));

            const matchesStatus = !statusFilter || booking.status === statusFilter;
            const matchesMachine = !machineFilter || (booking.machineInfo && booking.machineInfo.machineId === machineFilter);

            return matchesSearch && matchesStatus && matchesMachine;
        });

        this.renderBookings();
    }

    renderBookings() {
        const tbody = document.getElementById('bookingTableBody');
        const noDataMessage = document.getElementById('noDataMessage');
        
        if (!tbody) return;

        if (this.filteredBookings.length === 0) {
            tbody.innerHTML = '';
            if (noDataMessage) {
                noDataMessage.style.display = 'block';
            }
            return;
        }

        if (noDataMessage) {
            noDataMessage.style.display = 'none';
        }

        tbody.innerHTML = this.filteredBookings.map(booking => `
            <tr class="fade-in">
                <td>
                    <span class="font-mono text-sm">${booking.bookingId || 'N/A'}</span>
                </td>
                <td>
                    <div class="user-info">
                        <div class="user-name">${booking.userInfo ? `${booking.userInfo.firstName || ''} ${booking.userInfo.lastName || ''}` : 'Unknown User'}</div>
                        <div class="user-id text-xs text-gray-500">${booking.userInfo ? booking.userInfo.userId : 'N/A'}</div>
                    </div>
                </td>
                <td>
                    <div class="machine-info">
                        <div class="machine-id font-medium">${booking.machineInfo ? booking.machineInfo.machineId : 'N/A'}</div>
                        <div class="machine-type text-xs text-gray-500">${booking.machineInfo ? booking.machineInfo.machineType : 'N/A'}</div>
                    </div>
                </td>
                <td>
                    <div class="date-info">
                        <div class="date">${this.formatDate(booking.requestedDate)}</div>
                    </div>
                </td>
                <td>
                    <div class="venue-info">
                        <span class="venue-text">${booking.additionalMessage || 'N/A'}</span>
                    </div>
                </td>
                <td>
                    <span class="status-badge status-${(booking.status || 'PENDING').toLowerCase()}">
                        ${booking.status || 'PENDING'}
                    </span>
                </td>
                <td>
                    <div class="action-buttons">
                        <button class="btn-small btn-view" onclick="adminBookingManager.viewBooking(${booking.bookingId})" title="View Details">
                            <i class="fas fa-eye"></i>
                        </button>
                        <button class="btn-small btn-edit" onclick="adminBookingManager.updateStatus(${booking.bookingId}, '${booking.status || 'PENDING'}')" title="Update Status">
                            <i class="fas fa-edit"></i>
                        </button>
                    </div>
                </td>
            </tr>
        `).join('');
    }

    formatDate(dateString) {
        const date = new Date(dateString);
        return date.toLocaleDateString('en-US', {
            year: 'numeric',
            month: 'short',
            day: 'numeric'
        });
    }

    async viewBooking(bookingId) {
        try {
            const response = await fetch(`/api/booking-requests/${bookingId}/detailed`);
            if (response.ok) {
                const booking = await response.json();
                this.showBookingDetails(booking);
            } else {
                this.showNotification('Failed to load booking details', 'error');
            }
        } catch (error) {
            console.error('Error loading booking details:', error);
            this.showNotification('Error loading booking details', 'error');
        }
    }

    showBookingDetails(booking) {
        const modal = document.getElementById('detailsModal');
        const content = document.getElementById('bookingDetailContent');
        
        if (!modal || !content) return;

        content.innerHTML = `
            <div class="detail-section">
                <h4><i class="fas fa-info-circle"></i> Booking Information</h4>
                <div class="detail-meta">
                    <div class="meta-item">
                        <span class="meta-label">Booking ID</span>
                        <span class="meta-value">${booking.bookingId || 'N/A'}</span>
                    </div>
                    <div class="meta-item">
                        <span class="meta-label">Status</span>
                        <span class="meta-value">
                            <span class="status-badge status-${(booking.status || 'PENDING').toLowerCase()}">
                                ${booking.status || 'PENDING'}
                            </span>
                        </span>
                    </div>
                    <div class="meta-item">
                        <span class="meta-label">Requested Date</span>
                        <span class="meta-value">${booking.requestedDate ? this.formatDate(booking.requestedDate) : 'N/A'}</span>
                    </div>
                    <div class="meta-item">
                        <span class="meta-label">Additional Message</span>
                        <span class="meta-value">${booking.additionalMessage || 'N/A'}</span>
                    </div>
                </div>
            </div>

            <div class="detail-section">
                <h4><i class="fas fa-user"></i> User Information</h4>
                <div class="detail-meta">
                    <div class="meta-item">
                        <span class="meta-label">Name</span>
                        <span class="meta-value">${booking.userInfo ? `${booking.userInfo.firstName || ''} ${booking.userInfo.lastName || ''}` : 'Unknown User'}</span>
                    </div>
                    <div class="meta-item">
                        <span class="meta-label">User ID</span>
                        <span class="meta-value">${booking.userInfo ? booking.userInfo.userId : 'N/A'}</span>
                    </div>
                    <div class="meta-item">
                        <span class="meta-label">Email</span>
                        <span class="meta-value">${booking.userInfo ? booking.userInfo.email : 'N/A'}</span>
                    </div>
                    <div class="meta-item">
                        <span class="meta-label">Contact</span>
                        <span class="meta-value">${booking.userInfo ? booking.userInfo.contactNumber : 'N/A'}</span>
                    </div>
                </div>
            </div>

            <div class="detail-section">
                <h4><i class="fas fa-truck"></i> Machine Information</h4>
                <div class="detail-meta">
                    <div class="meta-item">
                        <span class="meta-label">Machine ID</span>
                        <span class="meta-value">${booking.machineInfo ? booking.machineInfo.machineId : 'N/A'}</span>
                    </div>
                    <div class="meta-item">
                        <span class="meta-label">Machine Type</span>
                        <span class="meta-value">${booking.machineInfo ? booking.machineInfo.machineType : 'N/A'}</span>
                    </div>
                    <div class="meta-item">
                        <span class="meta-label">Fuel Type</span>
                        <span class="meta-value">${booking.machineInfo ? booking.machineInfo.fuelType : 'N/A'}</span>
                    </div>
                </div>
            </div>

            ${booking.additionalMessage ? `
            <div class="detail-section">
                <h4><i class="fas fa-comment"></i> Additional Message</h4>
                <div class="detail-content">
                    <p class="text-gray-700 leading-relaxed">${booking.additionalMessage}</p>
                </div>
            </div>
            ` : ''}
        `;

        this.showModal('detailsModal');
    }

    updateStatus(bookingId, currentStatus) {
        this.currentBookingId = bookingId;
        
        // Set current status display
        const currentStatusDisplay = document.getElementById('currentStatusDisplay');
        if (currentStatusDisplay) {
            currentStatusDisplay.innerHTML = `
                <span class="status-badge status-${currentStatus.toLowerCase()}">
                    ${currentStatus}
                </span>
            `;
        }

        // Reset form
        const form = document.getElementById('statusUpdateForm');
        if (form) {
            form.reset();
        }

        this.showModal('statusModal');
    }

    async updateBookingStatus() {
        if (!this.currentBookingId) return;

        const newStatus = document.getElementById('newStatus').value;
        const message = document.getElementById('statusMessage').value;

        if (!newStatus) {
            this.showNotification('Please select a new status', 'warning');
            return;
        }

        const updateBtn = document.getElementById('confirmStatusUpdate');
        const loadingSpinner = document.getElementById('statusUpdateLoadingSpinner');
        
        try {
            // Show loading state
            updateBtn.classList.add('loading');
            updateBtn.disabled = true;
            if (loadingSpinner) {
                loadingSpinner.classList.add('show');
            }

            const updateData = {
                status: newStatus,
                message: message || null
            };

            const response = await fetch(`/api/booking-requests/${this.currentBookingId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(updateData)
            });

            if (response.ok) {
                this.showNotification('Booking status updated successfully', 'success');
                this.closeModal('statusModal');
                this.loadBookings(); // Reload bookings
            } else {
                const errorData = await response.json();
                this.showNotification(errorData.message || 'Failed to update booking status', 'error');
            }
        } catch (error) {
            console.error('Error updating booking status:', error);
            this.showNotification('Error updating booking status', 'error');
        } finally {
            // Hide loading state
            updateBtn.classList.remove('loading');
            updateBtn.disabled = false;
            if (loadingSpinner) {
                loadingSpinner.classList.remove('show');
            }
        }
    }

    updateStatistics() {
        const totalBookings = this.bookings.length;
        const pendingBookings = this.bookings.filter(b => b.status === 'PENDING').length;
        const approvedBookings = this.bookings.filter(b => b.status === 'APPROVED').length;
        const rejectedBookings = this.bookings.filter(b => b.status === 'REJECTED').length;

        // Update DOM elements
        const totalElement = document.getElementById('totalBookings');
        const pendingElement = document.getElementById('pendingBookings');
        const approvedElement = document.getElementById('approvedBookings');
        const rejectedElement = document.getElementById('rejectedBookings');

        if (totalElement) totalElement.textContent = totalBookings;
        if (pendingElement) pendingElement.textContent = pendingBookings;
        if (approvedElement) approvedElement.textContent = approvedBookings;
        if (rejectedElement) rejectedElement.textContent = rejectedBookings;
    }

    exportToPDF() {
        try {
            const { jsPDF } = window.jspdf;
            const doc = new jsPDF();

            // Add title
            doc.setFontSize(20);
            doc.text('Booking Request Management Report', 20, 20);

            // Add date
            doc.setFontSize(10);
            doc.text(`Generated on: ${new Date().toLocaleDateString()}`, 20, 30);

            // Prepare table data
            const tableData = this.filteredBookings.map(booking => [
                booking.bookingId,
                booking.userInfo ? `${booking.userInfo.firstName} ${booking.userInfo.lastName}` : 'Unknown User',
                booking.machineInfo ? booking.machineInfo.machineId : 'N/A',
                this.formatDate(booking.requestedDate),
                booking.additionalMessage || 'N/A',
                booking.status
            ]);

            // Add table
            doc.autoTable({
                head: [['ID', 'User', 'Machine', 'Date', 'Message', 'Status']],
                body: tableData,
                startY: 40,
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
            doc.save('booking-management-report.pdf');
            this.showNotification('PDF exported successfully', 'success');
        } catch (error) {
            console.error('Error exporting PDF:', error);
            this.showNotification('Error exporting PDF', 'error');
        }
    }

    showModal(modalId) {
        const modal = document.getElementById(modalId);
        if (modal) {
            modal.classList.add('show');
            document.body.style.overflow = 'hidden';
        }
    }

    closeModal(modalId) {
        const modal = document.getElementById(modalId);
        if (modal) {
            modal.classList.remove('show');
            document.body.style.overflow = '';
        }
    }

    showLoading(show) {
        const tbody = document.getElementById('bookingTableBody');
        const noDataMessage = document.getElementById('noDataMessage');
        
        if (show) {
            if (tbody) {
                tbody.innerHTML = `
                    <tr class="loading-row">
                        <td colspan="7" class="loading-cell">
                            <div class="loading-spinner">
                                <i class="fas fa-spinner fa-spin"></i>
                                <span>Loading bookings...</span>
                            </div>
                        </td>
                    </tr>
                `;
            }
            if (noDataMessage) {
                noDataMessage.style.display = 'none';
            }
        }
    }

    showNotification(message, type = 'info') {
        const container = document.getElementById('notificationContainer');
        if (!container) return;

        const notification = document.createElement('div');
        notification.className = `notification ${type}`;
        
        const icon = this.getNotificationIcon(type);
        notification.innerHTML = `
            <i class="${icon}"></i>
            <span>${message}</span>
        `;

        container.appendChild(notification);

        // Trigger animation
        setTimeout(() => {
            notification.classList.add('slide-in');
        }, 100);

        // Auto remove after 5 seconds
        setTimeout(() => {
            notification.classList.add('slide-out');
            setTimeout(() => {
                if (container.contains(notification)) {
                    container.removeChild(notification);
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

// Initialize the admin booking manager when the page loads
let adminBookingManager;
document.addEventListener('DOMContentLoaded', () => {
    adminBookingManager = new AdminBookingManager();
});

// Global functions for onclick handlers
window.adminBookingManager = adminBookingManager;
