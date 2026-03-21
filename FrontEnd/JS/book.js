document.addEventListener('DOMContentLoaded', () => {
    const user = AppState.currentUser || {
        userId: 999,
        userName: "Design Viewer",
        userprofileCompleted: true,
        userAddress: "123 Main St",
        userWorkplace: "Office"
    };
    const container = document.getElementById('book-content-container');

    if (!user.userprofileCompleted) {
        container.innerHTML = `
            <div class="glassmorphism text-center incomplete-profile-card">
                <i class="fa-solid fa-user-pen text-warning mb-3 incomplete-profile-icon"></i>
                <h3 class="mb-3">Profile Incomplete</h3>
                <p class="text-muted mb-4">You need to complete your profile before you can book a service.</p>
                <button onclick="window.location.href='profile.html'" class="primary-btn">Complete Profile</button>
            </div>
        `;
        return;
    }

    container.innerHTML = `
        <div class="glassmorphism booking-card">
            <h2 class="mb-4 text-center booking-title">Book an Appointment</h2>
            <form id="book-form">
                <div class="form-group">
                    <label>Select Service</label>
                    <select id="book-service" class="form-control book-input-gray" required>
                        <option value="" disabled selected>Select a cleaning service</option>
                        <option value="Business Cleaning">Business Cleaning</option>
                        <option value="House Cleaning">House Cleaning</option>
                        <option value="Floor Special Care">Floor Special Care (Scrubbing/Polishing/Cutting)</option>
                    </select>
                </div>
                
                <div class="booking-grid">
                    <div class="form-group">
                        <label>Date</label>
                        <input type="date" id="book-date" class="form-control" required min="${new Date().toISOString().split('T')[0]}">
                    </div>
                    <div class="form-group">
                        <label>Time</label>
                        <input type="time" id="book-time" class="form-control" required>
                    </div>
                </div>

                <div class="form-group">
                    <label>Service Address</label>
                    <select id="book-address-select" class="form-control mb-2 book-input-gray">
                        <option value="home">Use Home Address</option>
                        ${user.userWorkplace ? '<option value="work">Use Workplace Address</option>' : ''}
                        <option value="custom">Enter Custom Address</option>
                    </select>
                    <textarea id="book-address" class="form-control" required>${user.userAddress || ''}</textarea>
                </div>

                <div class="form-group">
                    <label>Additional Notes (Optional)</label>
                    <textarea id="book-notes" class="form-control" placeholder="Any specific requirements or instructions?"></textarea>
                </div>

                <button type="submit" class="primary-btn booking-submit-btn">Confirm Booking</button>
            </form>
        </div>
    `;

    // Handle address selection changes
    const addrSelect = document.getElementById('book-address-select');
    const addrText = document.getElementById('book-address');
    if (addrSelect && addrText) {
        addrSelect.addEventListener('change', (e) => {
            if (e.target.value === 'home') {
                addrText.value = user.userAddress || '';
                addrText.readOnly = true;
            } else if (e.target.value === 'work') {
                addrText.value = user.userWorkplace || '';
                addrText.readOnly = true;
            } else {
                addrText.value = '';
                addrText.readOnly = false;
                addrText.focus();
            }
        });
        // initial state
        addrText.readOnly = true;
    }

    const form = document.getElementById('book-form');
    if (form) {
        form.addEventListener('submit', async (e) => {
            e.preventDefault();

            const submitBtn = form.querySelector('button[type="submit"]');
            const originalBtnText = submitBtn.innerHTML;
            submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Booking...';
            submitBtn.disabled = true;

            const appointment = {
                userId: AppState.currentUser.userId,
                aService: document.getElementById('book-service').value,
                aDate: document.getElementById('book-date').value,
                aTime: document.getElementById('book-time').value,
                aAddress: document.getElementById('book-address').value.trim(),
                aNote: document.getElementById('book-notes').value.trim(),
                aStatus: 'Pending',
                aCreatedAt: new Date().toISOString()
            };

            try {
                // Adjust this endpoint based on your backend
                await API.post('/appointments', appointment);

                UI.showToast('Booking successful!', 'success');
                window.location.href = 'dashboard.html';
            } catch (error) {
                UI.showToast(error.message || 'Failed to create booking', 'error');
            } finally {
                submitBtn.innerHTML = originalBtnText;
                submitBtn.disabled = false;
            }
        });
    }
});
