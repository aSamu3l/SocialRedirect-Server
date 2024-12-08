function logout() {
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    fetch('/logout', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken
        }
    })
        .then(response => {
            if (response.ok) {
                window.location.href = '/login?logout';
            } else {
                console.error('Logout failed');
            }
        })
        .catch(error => console.error('Error:', error));
}