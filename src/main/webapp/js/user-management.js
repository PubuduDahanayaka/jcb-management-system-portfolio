// User Management JavaScript
class UserManagement {
    constructor() {
        this.users = [];
        this.filteredUsers = [];
        this.currentEditUserId = null;
        
        this.initializeElements();
        this.initializeEventListeners();
        this.loadUsers();
    }
    
    initializeElements() {
        this.searchInput = document.getElementById('searchInput');
        this.userTypeFilter = document.getElementById('userTypeFilter');
        this.statusFilter = document.getElementById('statusFilter');
        this.usersTableBody = document.getElementById('usersTableBody');
        this.loadingState = document.getElementById('loadingState');
        this.emptyState = document.getElementById('emptyState');
        this.notificationContainer = document.getElementById('notificationContainer');
        
        // Modal elements
        this.createModal = document.getElementById('createUserModal');
        this.editModal = document.getElementById('editUserModal');
        this.createForm = document.getElementById('createUserForm');
        this.editForm = document.getElementById('editUserForm');
    }
    
    initializeEventListeners() {
        // Search and filter
        this.searchInput.addEventListener('input', () => this.filterUsers());
        this.userTypeFilter.addEventListener('change', () => this.filterUsers());
        this.statusFilter.addEventListener('change', () => this.filterUsers());
        
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
    
    async loadUsers() {
        try {
            this.showLoading(true);
            const response = await fetch('/api/users');
            
            if (response.ok) {
                this.users = await response.json();
                this.filteredUsers = [...this.users];
                this.renderUsers();
            } else {
                this.showNotification('Failed to load users', 'error');
            }
        } catch (error) {
            console.error('Error loading users:', error);
            this.showNotification('Network error while loading users', 'error');
        } finally {
            this.showLoading(false);
        }
    }
    
    filterUsers() {
        const searchTerm = this.searchInput.value.toLowerCase();
        const userTypeFilter = this.userTypeFilter.value;
        const statusFilter = this.statusFilter.value;
        
        this.filteredUsers = this.users.filter(user => {
            const matchesSearch = !searchTerm || 
                user.firstName.toLowerCase().includes(searchTerm) ||
                user.lastName.toLowerCase().includes(searchTerm) ||
                user.email.toLowerCase().includes(searchTerm) ||
                user.userId.toLowerCase().includes(searchTerm);
            
            const matchesUserType = !userTypeFilter || user.userType === userTypeFilter;
            const matchesStatus = !statusFilter || user.isActive.toString() === statusFilter;
            
            return matchesSearch && matchesUserType && matchesStatus;
        });
        
        this.renderUsers();
    }
    
    renderUsers() {
        if (this.filteredUsers.length === 0) {
            this.showEmptyState(true);
            this.usersTableBody.innerHTML = '';
            return;
        }
        
        this.showEmptyState(false);
        
        this.usersTableBody.innerHTML = this.filteredUsers.map(user => `
            <tr>
                <td><strong>${user.userId}</strong></td>
                <td>${user.firstName} ${user.lastName}</td>
                <td>${user.email}</td>
                <td>${user.contactNumber}</td>
                <td><span class="user-type ${user.userType.toLowerCase()}">${user.userType}</span></td>
                <td><span class="status-badge ${user.isActive ? 'active' : 'inactive'}">${user.isActive ? 'Active' : 'Inactive'}</span></td>
                <td>${this.formatDate(user.createdAt)}</td>
                <td class="actions">
                    <button class="btn btn-sm btn-secondary" onclick="userManagement.editUser('${user.userId}')">
                        <i class="fas fa-edit"></i>
                    </button>
                    <button class="btn btn-sm btn-danger" onclick="userManagement.deleteUser('${user.userId}')">
                        <i class="fas fa-trash"></i>
                    </button>
                </td>
            </tr>
        `).join('');
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
        this.currentEditUserId = null;
    }
    
    async editUser(userId) {
        try {
            const response = await fetch(`/api/users/${userId}`);
            if (response.ok) {
                const user = await response.json();
                this.currentEditUserId = userId;
                
                // Populate edit form
                document.getElementById('editUserId').value = user.userId;
                document.getElementById('editFirstName').value = user.firstName;
                document.getElementById('editLastName').value = user.lastName;
                document.getElementById('editEmail').value = user.email;
                document.getElementById('editContactNumber').value = user.contactNumber;
                document.getElementById('editUserType').value = user.userType;
                document.getElementById('editIsActive').value = user.isActive.toString();
                document.getElementById('editPassword').value = '';
                
                this.openEditModal();
            } else {
                this.showNotification('Failed to load user details', 'error');
            }
        } catch (error) {
            console.error('Error loading user:', error);
            this.showNotification('Network error while loading user', 'error');
        }
    }
    
    async createUser() {
        try {
            const formData = new FormData(this.createForm);
            const userData = {
                firstName: formData.get('firstName'),
                lastName: formData.get('lastName'),
                email: formData.get('email'),
                contactNumber: formData.get('contactNumber'),
                password: formData.get('password'),
                userType: formData.get('userType')
            };
            
            const response = await fetch('/api/users', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(userData)
            });
            
            if (response.ok) {
                this.showNotification('User created successfully', 'success');
                this.closeCreateModal();
                this.loadUsers();
            } else {
                const error = await response.json();
                this.showNotification(error.message || 'Failed to create user', 'error');
            }
        } catch (error) {
            console.error('Error creating user:', error);
            this.showNotification('Network error while creating user', 'error');
        }
    }
    
    async updateUser() {
        try {
            const formData = new FormData(this.editForm);
            const userData = {
                firstName: formData.get('firstName'),
                lastName: formData.get('lastName'),
                email: formData.get('email'),
                contactNumber: formData.get('contactNumber'),
                userType: formData.get('userType'),
                isActive: formData.get('isActive') === 'true'
            };
            
            // Only include password if provided
            const password = formData.get('password');
            if (password) {
                userData.password = password;
            }
            
            const response = await fetch(`/api/users/${this.currentEditUserId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(userData)
            });
            
            if (response.ok) {
                this.showNotification('User updated successfully', 'success');
                this.closeEditModal();
                this.loadUsers();
            } else {
                const error = await response.json();
                this.showNotification(error.message || 'Failed to update user', 'error');
            }
        } catch (error) {
            console.error('Error updating user:', error);
            this.showNotification('Network error while updating user', 'error');
        }
    }
    
    async deleteUser(userId) {
        if (!confirm('Are you sure you want to delete this user? This action cannot be undone.')) {
            return;
        }
        
        try {
            const response = await fetch(`/api/users/${userId}`, {
                method: 'DELETE'
            });
            
            if (response.ok) {
                this.showNotification('User deleted successfully', 'success');
                this.loadUsers();
            } else {
                const error = await response.json();
                this.showNotification(error.message || 'Failed to delete user', 'error');
            }
        } catch (error) {
            console.error('Error deleting user:', error);
            this.showNotification('Network error while deleting user', 'error');
        }
    }
    
    exportToPDF() {
        try {
            const { jsPDF } = window.jspdf;
            const doc = new jsPDF();
            
            // Add title
            doc.setFontSize(20);
            doc.text('JCB Management System - Users Report', 20, 20);
            
            // Add date
            doc.setFontSize(10);
            doc.text(`Generated on: ${new Date().toLocaleDateString()}`, 20, 30);
            
            // Prepare table data
            const tableData = this.filteredUsers.map(user => [
                user.userId,
                `${user.firstName} ${user.lastName}`,
                user.email,
                user.contactNumber,
                user.userType,
                user.isActive ? 'Active' : 'Inactive',
                this.formatDate(user.createdAt)
            ]);
            
            // Add table
            doc.autoTable({
                head: [['User ID', 'Name', 'Email', 'Contact', 'Type', 'Status', 'Created']],
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
            doc.save(`users-report-${new Date().toISOString().split('T')[0]}.pdf`);
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
    userManagement.openCreateModal();
}

function closeCreateModal() {
    userManagement.closeCreateModal();
}

function openEditModal() {
    userManagement.openEditModal();
}

function closeEditModal() {
    userManagement.closeEditModal();
}

function createUser() {
    userManagement.createUser();
}

function updateUser() {
    userManagement.updateUser();
}

function exportToPDF() {
    userManagement.exportToPDF();
}

// Initialize when DOM is loaded
let userManagement;
document.addEventListener('DOMContentLoaded', () => {
    userManagement = new UserManagement();
});
