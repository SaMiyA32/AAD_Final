const AppState = {
    currentUser: JSON.parse(localStorage.getItem('currentUser')),
    isAdmin: false,

     login(user, token) {
        this.currentUser = user;
         this.isAdmin = user.userEmail === 'spasan42@gmail.com' || user.userRole === 'ADMIN' || user.isAdmin === true;

        localStorage.setItem('currentUser', JSON.stringify(user));
        if (token) {
            localStorage.setItem('authToken', token);
        }

        updateNav();
        window.location.href = this.isAdmin ? 'admin.html' : 'dashboard.html';
    },

    logout() {
        this.currentUser = null;
        this.isAdmin = false;
        localStorage.removeItem('currentUser');
        localStorage.removeItem('authToken');
        updateNav();
        window.location.href = 'index.html';
    }
};

 if (AppState.currentUser) {
    const user = AppState.currentUser;
    AppState.isAdmin = user.userEmail === 'spasan42@gmail.com' || user.userRole === 'ADMIN' || user.isAdmin === true;
}

 window.UI = {
    showToast(message, type = 'success') {
        let container = document.getElementById('toast-container');
        if (!container) {
            container = document.createElement('div');
            container.id = 'toast-container';
            document.body.appendChild(container);
        }
        const toast = document.createElement('div');
        toast.className = `toast ${type}`;

        const icon = type === 'success' ? 'fa-check-circle' : 'fa-exclamation-circle';

        toast.innerHTML = `
            <i class="fa-solid ${icon}"></i>
            <span>${message}</span>
        `;

        container.appendChild(toast);

        setTimeout(() => {
            toast.style.animation = 'fadeOut 0.3s ease forwards';
            setTimeout(() => {
                if (container.contains(toast)) container.removeChild(toast);
            }, 300);
        }, 3000);
    }
};

 const navSlide = () => {
    const burger = document.querySelector('.burger');
    const nav = document.querySelector('.nav-links');

    if (burger && nav) {
        burger.addEventListener('click', () => {
            nav.classList.toggle('nav-active');
            burger.classList.toggle('toggle');
        });

         nav.addEventListener('click', (e) => {
            if (e.target.tagName === 'A') {
                nav.classList.remove('nav-active');
                burger.classList.remove('toggle');
            }
        });
    }
};

function updateNav() {
    const navLinks = document.getElementById('nav-links');
    if (!navLinks) return;

    // Get current page name
    const path = window.location.pathname;
    const page = path.split('/').pop() || 'index.html';

    const isActive = (href) => page === href ? 'active' : '';

    if (AppState.isAdmin) {
        navLinks.innerHTML = `
            <li><a href="admin.html" class="${isActive('admin.html')}">Admin Panel</a></li>
            <li><a href="#" id="logout-btn" class="nav-btn secondary-btn">Logout</a></li>
        `;
    } else if (AppState.currentUser) {
        navLinks.innerHTML = `
            <li><a href="index.html" class="${isActive('index.html')}">Home</a></li>
            <li><a href="dashboard.html" class="${isActive('dashboard.html')}">Dashboard</a></li>
            <li><a href="profile.html" class="${isActive('profile.html')}">Profile</a></li>
            <li><a href="book.html" class="nav-btn primary-btn" style="margin-left: 10px;">Book Now</a></li>
            <li><a href="#" id="logout-btn" class="nav-btn secondary-btn" style="margin-left: 10px;">Logout</a></li>
        `;
    } else {
        navLinks.innerHTML = `
            <li><a href="index.html" class="${isActive('index.html')}">Home</a></li>
            <li><a href="login.html" class="nav-btn primary-btn">Login / Sign Up</a></li>
        `;
    }

    const logoutBtn = document.getElementById('logout-btn');
    if (logoutBtn) {
        logoutBtn.addEventListener('click', (e) => {
            e.preventDefault();
            AppState.logout();
        });
    }
}

 function checkAuth() {
    const path = window.location.pathname;
    const page = path.split('/').pop() || 'index.html';

    const protectedUserPages = ['dashboard.html', 'profile.html', 'book.html'];
    const protectedAdminPages = ['admin.html'];
    const guestPages = ['login.html', 'signup.html'];

    if (protectedUserPages.includes(page)) {
        if (!AppState.currentUser) {
            window.location.href = 'login.html';
            return false;
        }
        if (AppState.isAdmin) {
            window.location.href = 'admin.html';
            return false;
        }
    }

    if (protectedAdminPages.includes(page)) {
        if (!AppState.isAdmin) {
            window.location.href = 'index.html';
            return false;
        }
    }

    if (guestPages.includes(page)) {
        if (AppState.currentUser) {
            window.location.href = AppState.isAdmin ? 'admin.html' : 'dashboard.html';
            return false;
        }
    }
    return true;
}

 window.addEventListener('load', () => {
    if (checkAuth()) {
        updateNav();
        navSlide();

        // Show sections smoothly
        const section = document.querySelector('.view-section');
        if (section) {
            setTimeout(() => {
                section.classList.add('active');
            }, 50);
        }
    }
});
