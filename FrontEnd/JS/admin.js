let currentCancelAptId = null;

document.addEventListener('DOMContentLoaded', () => {
    if (!AppState.isAdmin) return;

    window.renderAdminAppointments = async function () {
        const listContainer = document.getElementById('appointments-list');
        listContainer.innerHTML = '<div class="glassmorphism text-center admin-message-box"><i class="fas fa-spinner fa-spin fa-2x text-primary"></i></div>';

        try {
            const allAppointments = await API.get('/appointments');
            let appointmentsHtml = '';

            if (!allAppointments || allAppointments.length === 0) {
                appointmentsHtml = '<div class="glassmorphism text-center admin-message-box"><p class="text-muted">No appointments found.</p></div>';
            } else {
                allAppointments.forEach(apt => {
                    let badgeClass = 'badge-pending';
                    if (apt.aStatus === 'Accepted') badgeClass = 'badge-accepted';
                    if (apt.aStatus === 'Cancelled') badgeClass = 'badge-cancelled';
                    if (apt.aStatus === 'Completed') badgeClass = 'badge-completed';

                    let extraInfo = '';
                    if (apt.aStatus === 'Cancelled' && apt.aCancelReason) {
                        extraInfo = `<div class="mt-3 text-danger admin-cancel-reason-box"><small><i class="fas fa-exclamation-triangle"></i> <strong>Cancel Reason:</strong> ${apt.aCancelReason}</small></div>`;
                    }

                    let actionBtns = '';
                    if (apt.aStatus === 'Pending') {
                        actionBtns = `
                        <button onclick="acceptAppointment('${apt.aId}')" class="success-btn admin-action-btn admin-accept-btn"><i class="fa-solid fa-check"></i> Accept</button>
                        <button onclick="openCancelModal('${apt.aId}')" class="danger-btn admin-action-btn"><i class="fa-solid fa-xmark"></i> Cancel</button>
                    `;
                    } else {
                        actionBtns = `<span class="text-muted admin-processed-text"><i class="fa-solid fa-check-circle text-primary"></i> Processed Request</span>`;
                    }

                    let createdAtStr = apt.aCreatedAt;
                    try {
                        createdAtStr = new Date(apt.aCreatedAt).toLocaleString();
                    } catch (e) { }

                    appointmentsHtml += `
                <div class="glassmorphism mb-4 admin-appointment-card">
                    <div class="admin-card-header">
                        <h4 class="admin-card-title">${apt.aService} <span class="badge ${badgeClass} admin-status-badge">${apt.aStatus}</span></h4>
                        <small class="text-muted admin-time-text"><i class="far fa-clock"></i> Booked: ${createdAtStr}</small>
                    </div>
                    
                    <div class="admin-details-grid">
                        <div class="admin-details-box">
                            <strong class="text-primary admin-box-title"><i class="far fa-user"></i> User Details</strong>
                            <div class="admin-detail-row"><strong>Name:</strong> ${apt.userName}</div>
                            <div class="admin-detail-row-last"><strong>Phone:</strong> ${apt.userPhone || 'N/A'}</div>
                        </div>
                        <div class="admin-details-box">
                            <strong class="text-primary admin-box-title"><i class="fas fa-clipboard-list"></i> Service Details</strong>
                            <div class="admin-detail-row"><strong>Date:</strong> ${apt.aDate} at ${apt.aTime}</div>
                            <div class="admin-detail-row"><strong>Address:</strong> ${apt.aAddress}</div>
                            <div class="admin-detail-row-last"><strong>Notes:</strong> ${apt.aNote || 'None'}</div>
                        </div>
                    </div>
                    ${extraInfo}
                    <div class="admin-action-footer">
                        ${actionBtns}
                    </div>
                </div>
                `;
                });
            }
            listContainer.innerHTML = appointmentsHtml;
        } catch (error) {
            listContainer.innerHTML = `<div class="glassmorphism text-center text-danger admin-message-box">Error loading appointments: ${error.message}</div>`;
        }
    }

    renderAdminAppointments();

    const btnCancel = document.getElementById('submit-cancel');
    if (btnCancel) {
        btnCancel.addEventListener('click', async () => {
            const reason = document.getElementById('cancel-reason').value.trim();
            if (!reason) {
                UI.showToast('Cancellation reason is required.', 'error');
                return;
            }

            const originalText = btnCancel.innerHTML;
            btnCancel.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Cancelling...';
            btnCancel.disabled = true;

            try {
                // Adjust endpoint based on backend setup (e.g. PUT /appointments/{id}/cancel)
                await API.put(`/appointments/${currentCancelAptId}/cancel`, { aCancelReason: reason });

                document.getElementById('cancel-modal').style.display = 'none';
                document.getElementById('modal-overlay-admin').style.display = 'none';
                UI.showToast('Appointment Cancelled', 'success');
                renderAdminAppointments();
            } catch (error) {
                UI.showToast(error.message || 'Failed to cancel appointment', 'error');
            } finally {
                btnCancel.innerHTML = originalText;
                btnCancel.disabled = false;
            }
        });
    }
});

window.acceptAppointment = async function (aptId) {
    if (confirm('Are you sure you want to accept this appointment? Have you called the user to confirm details?')) {
        try {
            // Adjust endpoint based on backend setup (e.g. PUT /appointments/{id}/accept)
            await API.put(`/appointments/${aptId}/accept`);
            UI.showToast('Appointment Accepted', 'success');
            if (typeof window.renderAdminAppointments === 'function') {
                window.renderAdminAppointments();
            }
        } catch (error) {
            UI.showToast(error.message || 'Failed to accept appointment', 'error');
        }
    }
}

window.openCancelModal = function (aptId) {
    currentCancelAptId = aptId;
    document.getElementById('cancel-reason').value = '';
    document.getElementById('cancel-modal').style.display = 'block';
    document.getElementById('modal-overlay-admin').style.display = 'block';

}






















