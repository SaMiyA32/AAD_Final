let currentRatingAptId = null;
let currentRatingVal = 5;

document.addEventListener('DOMContentLoaded', () => {
    const user = AppState.currentUser;
    if(!user) return;
     const welcomeName = user.userName ? user.userName.split(' ')[0] : 'User';
    document.getElementById('dashboard-title').innerText = `Welcome, ${welcomeName}`;

    async function renderAppointments() {
        const listContainer = document.getElementById('appointments-container');
        listContainer.innerHTML = '<div class="glassmorphism text-center dashboard-message-box"><i class="fas fa-spinner fa-spin fa-2x text-primary"></i></div>';

        try {
            const res = await API.get(`/appointments/user/${user.userId}`);
            let userAppointments = res.data || [];

            let appointmentsHtml = '';

            if (!userAppointments || userAppointments.length === 0) {
                appointmentsHtml = '<div class="glassmorphism text-center dashboard-message-box"><p class="text-muted">You have no booking history.</p><button onclick="window.location.href=\'book.html\'" class="primary-btn mt-3">Book Now</button></div>';
            } else {
                userAppointments.reverse().forEach(apt => {
                    let badgeClass = 'badge-pending';
                    if (apt.aStatus === 'Accepted') badgeClass = 'badge-accepted';
                    if (apt.aStatus === 'Cancelled') badgeClass = 'badge-cancelled';
                    if (apt.aStatus === 'Completed') badgeClass = 'badge-completed';

                    let extraInfo = '';
                    if (apt.aStatus === 'Cancelled' && apt.aCancelReason) {
                        extraInfo = `<div class="mt-2 text-danger"><small><i class="fas fa-exclamation-circle"></i> <strong>Reason:</strong> ${apt.aCancelReason}</small></div>`;
                    }

                    let rateBtn = '';
                    if (apt.aStatus === 'Accepted') {
                        rateBtn = `<button onclick="openRateModal('${apt.aId}')" class="secondary-btn rate-service-btn">Rate Service</button>`;
                    } else if (apt.aStatus === 'Completed') {
                        rateBtn = `<span class="text-muted rated-status-text"><i class="fa-solid fa-check-circle text-success"></i> Rated</span>`;
                    }

                    appointmentsHtml += `
                <div class="glassmorphism mb-4 appointment-card">
                    <div class="appointment-card-header">
                        <h4 class="appointment-card-title">${apt.aService}</h4>
                        <span class="badge ${badgeClass}">${apt.aStatus}</span>
                    </div>
                    <div class="text-muted appointment-details">
                        <div><i class="far fa-calendar-alt detail-icon"></i> <strong>Date & Time:</strong> ${apt.aDate} at ${apt.aTime}</div>
                        <div><i class="fas fa-map-marker-alt detail-icon"></i> <strong>Address:</strong> ${apt.aAddress}</div>
                    </div>
                    ${extraInfo}
                    <div class="appointment-action-footer">
                        ${rateBtn}
                    </div>
                </div>
                `;
                });
            }
            listContainer.innerHTML = appointmentsHtml;
        } catch (error) {
            listContainer.innerHTML = `<div class="glassmorphism text-center text-danger dashboard-message-box">Failed to load appointments: ${error.message}</div>`;
        }
    }

    renderAppointments();

    const stars = document.querySelectorAll('#star-rating i');
    stars.forEach(s => {
        s.addEventListener('click', (e) => {
            updateStars(parseInt(e.target.getAttribute('data-val')));
        });
    });

    const btnSubmit = document.getElementById('submit-rate');
    if (btnSubmit) {
        btnSubmit.addEventListener('click', async () => {
            const comment = document.getElementById('rate-comment').value.trim();
            if (!comment) {
                UI.showToast('Please enter a review comment', 'error');
                return;
            }

            const originalText = btnSubmit.innerHTML;
            btnSubmit.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Submitting...';
            btnSubmit.disabled = true;

            try {
                // Adjust payload according to your backend Rating DTO
                await API.post('/ratings', {
                    aptId: currentRatingAptId,
                    userId: AppState.currentUser.userId,
                    rRating: currentRatingVal,
                    rComment: comment
                });

                document.getElementById('rate-modal').style.display = 'none';
                document.getElementById('modal-overlay').style.display = 'none';
                UI.showToast('Thank you for your rating!', 'success');

                renderAppointments();
            } catch (error) {
                UI.showToast(error.message || 'Failed to submit rating', 'error');
            } finally {
                btnSubmit.innerHTML = originalText;
                btnSubmit.disabled = false;
            }
        });
    }
});

window.openRateModal = function (aptId, serviceName) {
    currentRatingAptId = aptId;

    // We pass serviceName directly since we don't have the full LocalStorage array anymore
    document.getElementById('rate-service-name').innerText = serviceName || 'Service';
    document.getElementById('rate-modal').style.display = 'block';
    document.getElementById('modal-overlay').style.display = 'block';
    updateStars(5);
}

window.updateStars = function (val) {
    currentRatingVal = val;
    const stars = document.querySelectorAll('#star-rating i');
    stars.forEach(s => {
        if (parseInt(s.getAttribute('data-val')) <= val) {
            s.classList.add('text-warning');
            s.classList.remove('text-muted');
        } else {
            s.classList.remove('text-warning');
            s.classList.add('text-muted');
        }
    });
}
