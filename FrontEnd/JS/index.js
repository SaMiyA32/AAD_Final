document.addEventListener('DOMContentLoaded', async () => {
    // Generate CTA buttons based on auth state
    const ctaContainer = document.getElementById('home-cta-container');
    if (AppState.currentUser) {
        ctaContainer.innerHTML = '<a href="book.html" class="primary-btn cta-large-btn">Book Now</a>';
    } else {
        ctaContainer.innerHTML = '<a href="login.html" class="primary-btn cta-large-btn">Get Started</a>';
    }

    // Generate Ratings
    const ratingsContainer = document.getElementById('ratings-container');
    let ratings = [];
    try {
        ratings = await API.get('/ratings');
    } catch (e) {
        console.warn('Could not fetch ratings, using empty list.');
    }

    let ratingsHtml = '';

    if (!ratings || ratings.length === 0) {
        ratingsHtml = '<p class="text-muted text-center">No reviews yet. Be the first to try our services!</p>';
    } else {
        // Show latest 3 ratings
        ratings.slice(-3).reverse().forEach(r => {
            let stars = '';
            for (let i = 1; i <= 5; i++) {
                stars += `<i class="fa-solid fa-star ${i <= r.rRating ? 'text-warning' : 'text-muted'}"></i>`;
            }

            // Fix date format string based on r.rDate which could be an ISO string
            let dateStr = r.rDate;
            try {
                dateStr = new Date(r.rDate).toLocaleDateString();
            } catch (e) { }

            ratingsHtml += `
            <div class="glassmorphism testimonial-card">
                <div class="testimonial-header">
                    <div class="testimonial-user-info">
                        <div class="testimonial-avatar">
                            ${r.userName ? r.userName.charAt(0).toUpperCase() : 'U'}
                        </div>
                        <strong class="testimonial-name">${r.userName || 'User'}</strong>
                    </div>
                    <div>${stars}</div>
                </div>
                <p class="text-muted testimonial-comment">"${r.rComment}"</p>
                <div class="testimonial-meta">Service: ${r.serviceType} &nbsp;&bull;&nbsp; ${dateStr}</div>
            </div>
            `;
        });
    }

    ratingsContainer.innerHTML = `
        <div class="testimonials testimonials-container">
            <h2 class="text-center mb-4">Client <span class="text-gradient">Testimonials</span></h2>
            ${ratingsHtml}
        </div>
    `;
});
