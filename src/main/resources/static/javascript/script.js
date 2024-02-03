document.querySelector("#login-form").addEventListener("submit", (event) => {
    event.preventDefault()
    let email = document.querySelector("#email").value
    let password = document.querySelector("#password").value
    let token = btoa(email + ":" + password)

    const apiUrl = location.protocol + '//' + location.hostname + (location.port ? ':' + location.port : '') + "/login"


    // Make a GET request
    fetch(apiUrl, {
        headers: {
            "Authorization": "Basic " + token
        }
    })
    .then(response => {
        if (response.ok) {
            window.location.pathname = "/welcome";
        }
        else {
        window.location.pathname = "/loginError";
        }

    })
    return false;
})