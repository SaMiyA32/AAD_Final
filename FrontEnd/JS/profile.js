document.addEventListener('DOMContentLoaded', async () => {
    const user = AppState.currentUser || {
        userId: 999,
        userName: "Design Viewer",
        userEmail: "viewer@example.com",
        userMobileNumber: "0771234567",
        userAddress: "123 Galle Road",
        userWorkplace: "Colombo",
        userGender: "Male"
    };

    let userAppointments = [];
    if (user.userId === 999) {
        userAppointments = [
            { aId: '1', aService: 'House Cleaning', aDate: '2026-03-20', aTime: '10:00', aAddress: '123 Main St', aStatus: 'Pending' },
            { aId: '2', aService: 'Business Cleaning', aDate: '2026-03-15', aTime: '14:00', aAddress: '456 Office Ave', aStatus: 'Completed', rRating: 5 },
            { aId: '3', aService: 'Floor Special Care', aDate: '2026-03-10', aTime: '09:00', aAddress: '123 Main St', aStatus: 'Accepted' }
        ];
    } else {
        try {
            userAppointments = await API.get(`/appointments/user/${user.userId}`) || [];
        } catch (e) { }
    }

    const completedApts = userAppointments.filter(a => a.aStatus === 'Completed');

    // Populate stats
    document.getElementById('profile-stats').innerHTML = `
        <div class="stat-box">
            <h3 class="text-primary stat-number">${userAppointments.length}</h3>
            <p class="text-muted stat-label">Total Orders</p>
        </div>
        <div class="stat-divider"></div>
        <div class="stat-box">
            <h3 class="text-primary stat-number">${completedApts.length}</h3>
            <p class="text-muted stat-label">Completed</p>
        </div>
    `;

    // Populate history
    let historyHtml = '';
    if (userAppointments.length === 0) {
        historyHtml = '<p class="text-muted empty-history-msg">No order history yet.</p>';
    } else {
        historyHtml = userAppointments.slice(0, 5).map(apt => `
            <div class="history-item">
                <div class="history-item-header">
                    <strong class="history-service-name">${apt.aService}</strong> 
                    <span class="badge ${apt.aStatus === 'Completed' ? 'badge-completed' : (apt.aStatus === 'Accepted' ? 'badge-accepted' : (apt.aStatus === 'Cancelled' ? 'badge-cancelled' : 'badge-pending'))}">${apt.aStatus}</span>
                </div>
                <div class="text-muted history-date"><i class="far fa-calendar-alt"></i> ${apt.aDate}</div>
            </div>
        `).join('');
    }
    document.getElementById('profile-history').innerHTML = historyHtml;

    // Populate form
    document.getElementById('prof-name').value = user.userName || '';
    document.getElementById('prof-email').value = user.userEmail || '';
    document.getElementById('prof-phone').value = user.userMobileNumber || '';
    document.getElementById('prof-address').value = user.userAddress || '';
    document.getElementById('prof-workplace').value = user.userWorkplace || '';
    if (user.userGender) {
        document.getElementById('prof-gender').value = user.userGender;
    }

    // Form submit
    const form = document.getElementById('profile-form');
    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        const submitBtn = form.querySelector('button[type="submit"]');
        const originalBtnText = submitBtn.innerHTML;
        submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Saving...';
        submitBtn.disabled = true;

        const updatedData = {
            userName: document.getElementById('prof-name').value.trim(),
            userMobileNumber: document.getElementById('prof-phone').value.trim(),
            userAddress: document.getElementById('prof-address').value.trim(),
            userWorkplace: document.getElementById('prof-workplace').value.trim(),
            userGender: document.getElementById('prof-gender').value,
            userprofileCompleted: true
        };

        try {
            // Update via API
            const response = await API.put(`/users/${user.userId}`, updatedData);

            // Assume response returns the complete updated user object
            const updatedUser = response.user || response;

            // Update AppState
            AppState.currentUser = { ...AppState.currentUser, ...updatedUser };
            localStorage.setItem('currentUser', JSON.stringify(AppState.currentUser));

            UI.showToast('Profile updated successfully!', 'success');
            updateNav();
        } catch (error) {
            UI.showToast(error.message || 'Failed to update profile', 'error');
        } finally {
            submitBtn.innerHTML = originalBtnText;
            submitBtn.disabled = false;
        }
    });
});
