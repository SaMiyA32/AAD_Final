document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.getElementById('login-form');

    if (loginForm) {
        loginForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const email = document.getElementById('login-email').value.trim();
            const password = document.getElementById('login-password').value;

            const submitBtn = loginForm.querySelector('button[type="submit"]');
            const originalBtnText = submitBtn.innerHTML;
            submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Logging in...';
            submitBtn.disabled = true;

            try {
                // Adjust this payload based on your Spring Boot DTO (e.g., LoginRequest)
                const response = await API.post('/auth/login', {
                    email: email,
                    password: password
                });

                // Assuming response returns { user: {...}, token: "..." }
                // Adjust based on your actual backend response structure
                const userObj = response.user || response;
                const token = response.token || null;

                AppState.login(userObj, token);

            } catch (error) {
                UI.showToast(error.message || 'Invalid email or password', 'error');
            } finally {
                submitBtn.innerHTML = originalBtnText;
                submitBtn.disabled = false;
            }
        });
    }
});
