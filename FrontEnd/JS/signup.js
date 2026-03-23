document.addEventListener('DOMContentLoaded', () => {
    const registerForm = document.getElementById('register-form');

    if (registerForm) {
        registerForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const name = document.getElementById('reg-name').value.trim();
            const email = document.getElementById('reg-email').value.trim();
            const password = document.getElementById('reg-password').value;
            const confirm = document.getElementById('reg-confirm').value;

            if (password !== confirm) {
                UI.showToast('Passwords do not match', 'error');
                return;
            }

            const submitBtn = registerForm.querySelector('button[type="submit"]');
            const originalBtnText = submitBtn.innerHTML;
            submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Registering...';
            submitBtn.disabled = true;

            const payload = {
                userName: name,
                userEmail: email,
                userPassword: password,
                userprofileCompleted: false
            };

            try {
                 await API.post('/auth/register', payload);
                 UI.showToast('Registration successful! Redirecting to Login...', 'success');
                 setTimeout(() => {
                     window.location.href = 'login.html';
                 }, 2000);

            } catch (error) {
                UI.showToast(error.message || 'Registration failed', 'error');
            } finally {
                submitBtn.innerHTML = originalBtnText;
                submitBtn.disabled = false;
            }
        });
    }
});
