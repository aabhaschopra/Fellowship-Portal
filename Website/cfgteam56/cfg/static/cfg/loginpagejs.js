function toggle(){

    const loggedInLinks = document.querySelectorAll('.login');
    const signUpLinks = document.querySelectorAll('.signup');
    const toggleButton = document.getElementById('toggle-button');
    const accountHeader = document.getElementById('account-header');

    loggedInLinks.forEach(item => {
    if (item.style.display === "none") {
        item.style.display = "block";
    } else {
        item.style.display = "none";
    }});

    signUpLinks.forEach(item => {
    if (item.style.display === "none") {
        item.style.display = "block";
    } else {
        item.style.display = "none";
    }});

    if (toggleButton.textContent == "Back to Login."){
        toggleButton.textContent = "New? Sign-Up instead.";
    }

    else{
        toggleButton.textContent = "Back to Login."
    }

    if (accountHeader.textContent == "Account Login"){
        accountHeader.textContent = "Account Sign-Up";
    }

    else{
        accountHeader.textContent = "Account Login"
    }
}

function gotodash(){
    window.location.href = "/dashboard/main.html";
}