const API_BASE_URL = 'http://localhost:8080/api'; // Update this to match your Spring Boot backend URL

 function getHeaders() {
    const headers = {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
    };

     const token = localStorage.getItem('authToken');
    if (token) {
        headers['Authorization'] = `Bearer ${token}`;
    }

    return headers;
}

 async function handleResponse(response) {
    if (!response.ok) {
        let errorMsg = 'An error occurred';
        try {
            const errData = await response.json();
            errorMsg = errData.message || errorMsg;
        } catch (e) {
            errorMsg = response.statusText || errorMsg;
        }
        throw new Error(errorMsg);
    }

     const contentType = response.headers.get('content-type');
    if (contentType && contentType.includes('application/json')) {
        return await response.json();
    }
    return null;
}

 const API = {
    async get(endpoint) {
        try {
            const response = await fetch(`${API_BASE_URL}${endpoint}`, {
                method: 'GET',
                headers: getHeaders()
            });
            return await handleResponse(response);
        } catch (error) {
            console.error(`GET ${endpoint} failed:`, error);
            throw error;
        }
    },

    async post(endpoint, data) {
        try {
            const response = await fetch(`${API_BASE_URL}${endpoint}`, {
                method: 'POST',
                headers: getHeaders(),
                body: JSON.stringify(data)
            });
            return await handleResponse(response);
        } catch (error) {
            console.error(`POST ${endpoint} failed:`, error);
            throw error;
        }
    },

    async put(endpoint, data) {
        try {
            const response = await fetch(`${API_BASE_URL}${endpoint}`, {
                method: 'PUT',
                headers: getHeaders(),
                body: JSON.stringify(data)
            });
            return await handleResponse(response);
        } catch (error) {
            console.error(`PUT ${endpoint} failed:`, error);
            throw error;
        }
    },

    async delete(endpoint) {
        try {
            const response = await fetch(`${API_BASE_URL}${endpoint}`, {
                method: 'DELETE',
                headers: getHeaders()
            });
            return await handleResponse(response);
        } catch (error) {
            console.error(`DELETE ${endpoint} failed:`, error);
            throw error;
        }
    }
};
