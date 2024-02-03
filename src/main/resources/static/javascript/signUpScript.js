let password = document.querySelector("input[name='password']")
let repeatedPassword = document.querySelector("input[name='repeated-password']")
let errorMessage = document.querySelector("#message")
let errorPane = document.querySelector(".container-box")

function checkPassword() {
    if (password.value != repeatedPassword.value && password.value != "" && repeatedPassword.value != "") {
        errorPane.style.display = "flex"
        errorPane.textContent = "Le password non corrispondono"
        errorPane.style.color = "red"
    } else {
        errorPane.style.display = "none"
        errorPane.textContent = ""
    }
}

document.querySelector("input[name='repeated-password']").addEventListener("focusout", (event) => {
    checkPassword()
})

document.querySelector("input[name='password']").addEventListener("focusout", (event) => {
    checkPassword()
})

document.querySelector("#signUpForm").addEventListener("submit", event =>{
    if (password.value != repeatedPassword.value) {
        event.preventDefault()
        return false
    }
})