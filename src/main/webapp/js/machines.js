// Machines View JavaScript
class MachinesViewer {
    constructor() {
        this.currentUser = null;
        this.machines = [];
        
        this.detailModal = document.getElementById('detailModal');
        this.notificationContainer = document.getElementById('notificationContainer');
        
        this.initializeEventListeners();
        this.loadInitialData();
    }
    
    initializeEventListeners() {
        // Detail Modal
        document.getElementById('closeDetailModal').addEventListener('click', () => this.closeDetailModal());
        document.getElementById('closeDetailBtn').addEventListener('click', () => this.closeDetailModal());
        
        // Search and Filter
        document.getElementById('searchInput').addEventListener('input', () => this.filterMachines());
        document.getElementById('typeFilter').addEventListener('change', () => this.filterMachines());
        document.getElementById('fuelFilter').addEventListener('change', () => this.filterMachines());
        
        // Export PDF
        document.getElementById('exportPdfBtn').addEventListener('click', () => this.exportToPDF());
        
        // Close modal on overlay click
        this.detailModal.addEventListener('click', (e) => {
            if (e.target === this.detailModal) this.closeDetailModal();
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
            
            // Load machines
            await this.loadMachines();
            
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
                console.table(this.machines);
                this.displayMachines();
                this.updateStatistics();
            } else {
                this.showNotification('Failed to load machines.', 'error');
            }
        } catch (error) {
            console.error('Error loading machines:', error);
            this.showNotification('Network error. Please check your connection.', 'error');
        }
    }
    
    displayMachines() {
        const machinesGrid = document.getElementById('machinesGrid');
        const noDataMessage = document.getElementById('noDataMessage');
        
        if (this.machines.length === 0) {
            machinesGrid.innerHTML = '';
            noDataMessage.style.display = 'block';
            return;
        }
        
        noDataMessage.style.display = 'none';
        
        machinesGrid.innerHTML = this.machines.map(machine => `
            <div class="machine-card" onclick="machinesViewer.viewMachineDetails('${machine.machineId}')">
                <div class="machine-header">
                    <div class="machine-icon">
                        <i class="fas fa-truck-monster"></i>
                    </div>
                    <div class="machine-info">
                        <h3>${this.formatMachineType(machine.machineType)}</h3>
                        <div class="machine-id">${machine.machineId}</div>
                    </div>
                </div>
                
                <div class="machine-details">
                    <div class="detail-item">
                        <span class="detail-label">Machine ID</span>
                        <span class="detail-value">${machine.machineId}</span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Type</span>
                        <span class="detail-value">
                            <span class="type-badge type-${machine.machineType.toLowerCase()}">${this.formatMachineType(machine.machineType)}</span>
                        </span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Fuel Type</span>
                        <span class="detail-value">
                            <span class="fuel-badge fuel-${machine.fuelType.toLowerCase()}">${this.formatFuelType(machine.fuelType)}</span>
                        </span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Status</span>
                        <span class="detail-value">
                            <span class="status-badge status-available">Available</span>
                        </span>
                    </div>
                </div>
            </div>
        `).join('');
        
        // Add fade-in animation
        document.querySelector('.machines-grid-section').classList.add('fade-in');
    }
    
    filterMachines() {
        const searchTerm = document.getElementById('searchInput').value.toLowerCase();
        const typeFilter = document.getElementById('typeFilter').value;
        const fuelFilter = document.getElementById('fuelFilter').value;
        
        const filteredMachines = this.machines.filter(machine => {
            const matchesSearch = !searchTerm || 
                machine.machineId?.toLowerCase().includes(searchTerm) ||
                machine.machineType?.toLowerCase().includes(searchTerm) ||
                machine.fuelType?.toLowerCase().includes(searchTerm);
            
            const matchesType = !typeFilter || machine.machineType === typeFilter;
            const matchesFuel = !fuelFilter || machine.fuelType === fuelFilter;
            
            return matchesSearch && matchesType && matchesFuel;
        });
        
        this.displayFilteredMachines(filteredMachines);
    }
    
    displayFilteredMachines(filteredMachines) {
        const machinesGrid = document.getElementById('machinesGrid');
        const noDataMessage = document.getElementById('noDataMessage');
        
        if (filteredMachines.length === 0) {
            machinesGrid.innerHTML = `
                <div class="loading-card">
                    <div class="loading-spinner">
                        <i class="fas fa-search"></i>
                        <span>No machines match your search criteria</span>
                    </div>
                </div>
            `;
            noDataMessage.style.display = 'none';
            return;
        }
        
        noDataMessage.style.display = 'none';
        
        machinesGrid.innerHTML = filteredMachines.map(machine => `
            <div class="machine-card" onclick="machinesViewer.viewMachineDetails('${machine.machineId}')">
                <div class="machine-header">
                    <div class="machine-icon">
                        <i class="fas fa-truck-monster"></i>
                    </div>
                    <div class="machine-info">
                        <h3>${this.formatMachineType(machine.machineType)}</h3>
                        <div class="machine-id">${machine.machineId}</div>
                    </div>
                </div>
                
                <div class="machine-details">
                    <div class="detail-item">
                        <span class="detail-label">Machine ID</span>
                        <span class="detail-value">${machine.machineId}</span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Type</span>
                        <span class="detail-value">
                            <span class="type-badge type-${machine.machineType.toLowerCase()}">${this.formatMachineType(machine.machineType)}</span>
                        </span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Fuel Type</span>
                        <span class="detail-value">
                            <span class="fuel-badge fuel-${machine.fuelType.toLowerCase()}">${this.formatFuelType(machine.fuelType)}</span>
                        </span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Status</span>
                        <span class="detail-value">
                            <span class="status-badge status-available">Available</span>
                        </span>
                    </div>
                </div>
            </div>
        `).join('');
    }
    
    updateStatistics() {
        const totalMachines = this.machines.length;
        const machineTypes = new Set(this.machines.map(m => m.machineType)).size;
        const fuelTypes = new Set(this.machines.map(m => m.fuelType)).size;
        const availableMachines = this.machines.length; // Assuming all are available for customers
        
        document.getElementById('totalMachines').textContent = totalMachines;
        document.getElementById('machineTypes').textContent = machineTypes;
        document.getElementById('fuelTypes').textContent = fuelTypes;
        document.getElementById('availableMachines').textContent = availableMachines;
    }
    
    viewMachineDetails(machineId) {
        const machine = this.machines.find(m => m.machineId === machineId);
        if (!machine) {
            this.showNotification('Machine not found.', 'error');
            return;
        }
        
        const detailContent = document.getElementById('machineDetailContent');
        detailContent.innerHTML = `
            <div class="detail-section">
                <h4><i class="fas fa-info-circle"></i> Basic Information</h4>
                <div class="detail-meta">
                    <div class="meta-item">
                        <span class="meta-label">Machine ID</span>
                        <span class="meta-value">${machine.machineId}</span>
                    </div>
                    <div class="meta-item">
                        <span class="meta-label">Type</span>
                        <span class="meta-value">
                            <span class="type-badge type-${machine.machineType.toLowerCase()}">${this.formatMachineType(machine.machineType)}</span>
                        </span>
                    </div>
                    <div class="meta-item">
                        <span class="meta-label">Fuel Type</span>
                        <span class="meta-value">
                            <span class="fuel-badge fuel-${machine.fuelType.toLowerCase()}">${this.formatFuelType(machine.fuelType)}</span>
                        </span>
                    </div>
                    <div class="meta-item">
                        <span class="meta-label">Status</span>
                        <span class="meta-value">
                            <span class="status-badge status-available">Available</span>
                        </span>
                    </div>
                </div>
            </div>
            
            <div class="detail-section">
                <h4><i class="fas fa-truck-monster"></i> Machine Specifications</h4>
                <div class="detail-content">
                    <p><strong>Machine Type:</strong> ${this.formatMachineType(machine.machineType)}</p>
                    <p><strong>Fuel Type:</strong> ${this.formatFuelType(machine.fuelType)}</p>
                    <p><strong>Machine ID:</strong> ${machine.machineId}</p>
                    <p><strong>Availability:</strong> Available for booking</p>
                </div>
            </div>
            
            <div class="detail-section">
                <h4><i class="fas fa-calendar-check"></i> Booking Information</h4>
                <div class="detail-content">
                    <p>This machine is currently available for booking. You can request this machine through the booking system.</p>
                    <p>To book this machine, please go to the <a href="/booking-requests" style="color: #eab308; text-decoration: none;">Booking Requests</a> page.</p>
                </div>
            </div>
        `;
        
        this.detailModal.classList.add('show');
    }
    
    closeDetailModal() {
        this.detailModal.classList.remove('show');
    }
    
    exportToPDF() {
        if (this.machines.length === 0) {
            this.showNotification('No machines to export.', 'warning');
            return;
        }
        
        try {
            const { jsPDF } = window.jspdf;
            const doc = new jsPDF();
            
            // Add title
            doc.setFontSize(20);
            doc.text('Available Machines', 20, 20);
            
            // Add user info
            doc.setFontSize(12);
            doc.text(`Generated for: ${this.currentUser.firstName} ${this.currentUser.lastName}`, 20, 35);
            doc.text(`Generated on: ${new Date().toLocaleDateString()}`, 20, 45);
            
            // Prepare table data
            const tableData = this.machines.map(machine => [
                machine.machineId,
                this.formatMachineType(machine.machineType),
                this.formatFuelType(machine.fuelType),
                'Available'
            ]);
            
            // Add table
            doc.autoTable({
                head: [['Machine ID', 'Type', 'Fuel Type', 'Status']],
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
            doc.save(`available-machines-${this.currentUser.userId}-${new Date().toISOString().split('T')[0]}.pdf`);
            
            this.showNotification('PDF exported successfully!', 'success');
        } catch (error) {
            console.error('PDF export error:', error);
            this.showNotification('Failed to export PDF. Please try again.', 'error');
        }
    }
    
    formatMachineType(type) {
        return type.replace(/_/g, ' ').replace(/\b\w/g, l => l.toUpperCase());
    }
    
    formatFuelType(type) {
        return type.replace(/_/g, ' ').replace(/\b\w/g, l => l.toUpperCase());
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

// Initialize machines viewer when DOM is loaded
let machinesViewer;
document.addEventListener('DOMContentLoaded', () => {
    machinesViewer = new MachinesViewer();
});
