// Machine Management JavaScript
class MachineManagement {
    constructor() {
        this.machines = [];
        this.filteredMachines = [];
        this.currentEditMachineId = null;
        
        this.initializeElements();
        this.initializeEventListeners();
        this.loadMachines();
    }
    
    initializeElements() {
        this.searchInput = document.getElementById('searchInput');
        this.machineTypeFilter = document.getElementById('machineTypeFilter');
        this.fuelTypeFilter = document.getElementById('fuelTypeFilter');
        this.statusFilter = document.getElementById('statusFilter');
        this.machinesTableBody = document.getElementById('machinesTableBody');
        this.loadingState = document.getElementById('loadingState');
        this.emptyState = document.getElementById('emptyState');
        this.notificationContainer = document.getElementById('notificationContainer');
        
        // Modal elements
        this.createModal = document.getElementById('createMachineModal');
        this.editModal = document.getElementById('editMachineModal');
        this.createForm = document.getElementById('createMachineForm');
        this.editForm = document.getElementById('editMachineForm');
    }
    
    initializeEventListeners() {
        // Search and filter
        this.searchInput.addEventListener('input', () => this.filterMachines());
        this.machineTypeFilter.addEventListener('change', () => this.filterMachines());
        this.fuelTypeFilter.addEventListener('change', () => this.filterMachines());
        this.statusFilter.addEventListener('change', () => this.filterMachines());
        
        // Modal close on outside click
        this.createModal.addEventListener('click', (e) => {
            if (e.target === this.createModal) {
                this.closeCreateModal();
            }
        });
        
        this.editModal.addEventListener('click', (e) => {
            if (e.target === this.editModal) {
                this.closeEditModal();
            }
        });
    }
    
    async loadMachines() {
        try {
            this.showLoading(true);
            const response = await fetch('/api/machines');
            
            if (response.ok) {
                this.machines = await response.json();
                this.filteredMachines = [...this.machines];
                this.renderMachines();
            } else {
                this.showNotification('Failed to load machines', 'error');
            }
        } catch (error) {
            console.error('Error loading machines:', error);
            this.showNotification('Network error while loading machines', 'error');
        } finally {
            this.showLoading(false);
        }
    }
    
    filterMachines() {
        const searchTerm = this.searchInput.value.toLowerCase();
        const machineTypeFilter = this.machineTypeFilter.value;
        const fuelTypeFilter = this.fuelTypeFilter.value;
        const statusFilter = this.statusFilter.value;
        
        this.filteredMachines = this.machines.filter(machine => {
            const matchesSearch = !searchTerm || 
                machine.machineId.toLowerCase().includes(searchTerm) ||
                machine.machineType.toLowerCase().includes(searchTerm) ||
                machine.fuelType.toLowerCase().includes(searchTerm);
            
            const matchesMachineType = !machineTypeFilter || machine.machineType === machineTypeFilter;
            const matchesFuelType = !fuelTypeFilter || machine.fuelType === fuelTypeFilter;
            const matchesStatus = !statusFilter || machine.isActive.toString() === statusFilter;
            
            return matchesSearch && matchesMachineType && matchesFuelType && matchesStatus;
        });
        
        this.renderMachines();
    }
    
    renderMachines() {
        if (this.filteredMachines.length === 0) {
            this.showEmptyState(true);
            this.machinesTableBody.innerHTML = '';
            return;
        }
        
        this.showEmptyState(false);
        
        this.machinesTableBody.innerHTML = this.filteredMachines.map(machine => `
            <tr>
                <td><strong>${machine.machineId}</strong></td>
                <td><span class="machine-type ${machine.machineType.toLowerCase()}">${machine.machineType.replace('_', ' ')}</span></td>
                <td><span class="fuel-type ${machine.fuelType.toLowerCase()}">${machine.fuelType}</span></td>
                <td>
                    <div class="fuel-level">
                        <div class="fuel-level-bar">
                            <div class="fuel-level-fill ${this.getFuelLevelClass(machine.currentFuelLevel)}" 
                                 style="width: ${machine.currentFuelLevel || 0}%"></div>
                        </div>
                        <span>${machine.currentFuelLevel || 0}%</span>
                    </div>
                </td>
                <td>${machine.maxFuelCapacity || 'N/A'} L</td>
                <td>${machine.operatingHours || 0} hrs</td>
                <td><span class="status-badge ${machine.isActive ? 'active' : 'inactive'}">${machine.isActive ? 'Active' : 'Inactive'}</span></td>
                <td>${this.formatDate(machine.createdAt)}</td>
                <td class="actions">
                    <button class="btn btn-sm btn-secondary" onclick="machineManagement.editMachine('${machine.machineId}')">
                        <i class="fas fa-edit"></i>
                    </button>
                    <button class="btn btn-sm btn-danger" onclick="machineManagement.deleteMachine('${machine.machineId}')">
                        <i class="fas fa-trash"></i>
                    </button>
                </td>
            </tr>
        `).join('');
    }
    
    getFuelLevelClass(fuelLevel) {
        if (fuelLevel >= 70) return 'high';
        if (fuelLevel >= 30) return 'medium';
        return 'low';
    }
    
    showLoading(show) {
        this.loadingState.style.display = show ? 'flex' : 'none';
    }
    
    showEmptyState(show) {
        this.emptyState.style.display = show ? 'flex' : 'none';
    }
    
    formatDate(dateString) {
        const date = new Date(dateString);
        return date.toLocaleDateString() + ' ' + date.toLocaleTimeString();
    }
    
    // Modal Functions
    openCreateModal() {
        this.createModal.classList.add('show');
        this.createForm.reset();
    }
    
    closeCreateModal() {
        this.createModal.classList.remove('show');
    }
    
    openEditModal() {
        this.editModal.classList.add('show');
    }
    
    closeEditModal() {
        this.editModal.classList.remove('show');
        this.currentEditMachineId = null;
    }
    
    async editMachine(machineId) {
        try {
            const response = await fetch(`/api/machines/${machineId}`);
            if (response.ok) {
                const machine = await response.json();
                this.currentEditMachineId = machineId;
                
                // Populate edit form
                document.getElementById('editMachineId').value = machine.machineId;
                document.getElementById('editMachineType').value = machine.machineType;
                document.getElementById('editFuelType').value = machine.fuelType;
                document.getElementById('editCurrentFuelLevel').value = machine.currentFuelLevel || 0;
                document.getElementById('editMaxFuelCapacity').value = machine.maxFuelCapacity || 0;
                document.getElementById('editOperatingHours').value = machine.operatingHours || 0;
                document.getElementById('editIsActive').value = machine.isActive.toString();
                
                this.openEditModal();
            } else {
                this.showNotification('Failed to load machine details', 'error');
            }
        } catch (error) {
            console.error('Error loading machine:', error);
            this.showNotification('Network error while loading machine', 'error');
        }
    }
    
    async createMachine() {
        try {
            const formData = new FormData(this.createForm);
            const machineData = {
                machineType: formData.get('machineType'),
                fuelType: formData.get('fuelType'),
                currentFuelLevel: parseFloat(formData.get('currentFuelLevel')) || 100,
                maxFuelCapacity: parseFloat(formData.get('maxFuelCapacity')) || 0,
                operatingHours: parseFloat(formData.get('operatingHours')) || 0
            };
            
            const response = await fetch('/api/machines', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(machineData)
            });
            
            if (response.ok) {
                this.showNotification('Machine created successfully', 'success');
                this.closeCreateModal();
                this.loadMachines();
            } else {
                const error = await response.json();
                this.showNotification(error.message || 'Failed to create machine', 'error');
            }
        } catch (error) {
            console.error('Error creating machine:', error);
            this.showNotification('Network error while creating machine', 'error');
        }
    }
    
    async updateMachine() {
        try {
            const formData = new FormData(this.editForm);
            const machineData = {
                machineType: formData.get('machineType'),
                fuelType: formData.get('fuelType'),
                currentFuelLevel: parseFloat(formData.get('currentFuelLevel')) || 0,
                maxFuelCapacity: parseFloat(formData.get('maxFuelCapacity')) || 0,
                operatingHours: parseFloat(formData.get('operatingHours')) || 0,
                isActive: formData.get('isActive') === 'true'
            };
            
            const response = await fetch(`/api/machines/${this.currentEditMachineId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(machineData)
            });
            
            if (response.ok) {
                this.showNotification('Machine updated successfully', 'success');
                this.closeEditModal();
                this.loadMachines();
            } else {
                const error = await response.json();
                this.showNotification(error.message || 'Failed to update machine', 'error');
            }
        } catch (error) {
            console.error('Error updating machine:', error);
            this.showNotification('Network error while updating machine', 'error');
        }
    }
    
    async deleteMachine(machineId) {
        if (!confirm('Are you sure you want to delete this machine? This action cannot be undone.')) {
            return;
        }
        
        try {
            const response = await fetch(`/api/machines/${machineId}`, {
                method: 'DELETE'
            });
            
            if (response.ok) {
                this.showNotification('Machine deleted successfully', 'success');
                this.loadMachines();
            } else {
                const error = await response.json();
                this.showNotification(error.message || 'Failed to delete machine', 'error');
            }
        } catch (error) {
            console.error('Error deleting machine:', error);
            this.showNotification('Network error while deleting machine', 'error');
        }
    }
    
    exportToPDF() {
        try {
            const { jsPDF } = window.jspdf;
            const doc = new jsPDF();
            
            // Add title
            doc.setFontSize(20);
            doc.text('JCB Management System - Machines Report', 20, 20);
            
            // Add date
            doc.setFontSize(10);
            doc.text(`Generated on: ${new Date().toLocaleDateString()}`, 20, 30);
            
            // Prepare table data
            const tableData = this.filteredMachines.map(machine => [
                machine.machineId,
                machine.machineType.replace('_', ' '),
                machine.fuelType,
                `${machine.currentFuelLevel || 0}%`,
                `${machine.maxFuelCapacity || 0} L`,
                `${machine.operatingHours || 0} hrs`,
                machine.isActive ? 'Active' : 'Inactive',
                this.formatDate(machine.createdAt)
            ]);
            
            // Add table
            doc.autoTable({
                head: [['Machine ID', 'Type', 'Fuel Type', 'Fuel Level', 'Max Capacity', 'Operating Hours', 'Status', 'Created']],
                body: tableData,
                startY: 40,
                styles: {
                    fontSize: 8,
                    cellPadding: 3
                },
                headStyles: {
                    fillColor: [0, 0, 0],
                    textColor: [255, 255, 255]
                },
                alternateRowStyles: {
                    fillColor: [248, 250, 252]
                }
            });
            
            // Save the PDF
            doc.save(`machines-report-${new Date().toISOString().split('T')[0]}.pdf`);
            this.showNotification('PDF exported successfully', 'success');
            
        } catch (error) {
            console.error('Error exporting PDF:', error);
            this.showNotification('Failed to export PDF', 'error');
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

// Global functions for onclick handlers
function openCreateModal() {
    machineManagement.openCreateModal();
}

function closeCreateModal() {
    machineManagement.closeCreateModal();
}

function openEditModal() {
    machineManagement.openEditModal();
}

function closeEditModal() {
    machineManagement.closeEditModal();
}

function createMachine() {
    machineManagement.createMachine();
}

function updateMachine() {
    machineManagement.updateMachine();
}

function exportToPDF() {
    machineManagement.exportToPDF();
}

// Initialize when DOM is loaded
let machineManagement;
document.addEventListener('DOMContentLoaded', () => {
    machineManagement = new MachineManagement();
});
