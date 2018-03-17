
function toggle() {
    var x = document.getElementById("filteroptionen");
    if (x.style.display === "none") {
        x.style.display = "block";
    } else {
        x.style.display = "none";
    }
}


function saveDeleteUsername(username){
    this.userToDelete = username;
}

function deleteUser() {
    post("/benutzer", {command: "removeUser:"+ this.userToDelete})
}

function logout(uri) {
    post(uri, {command: "logout"})
}

//https://stackoverflow.com/questions/133925/javascript-post-request-like-a-form-submit
function post(path, params) {
    // The rest of this code assumes you are not using a library.
    // It can be made less wordy if you use one.
    var form = document.createElement("form");
    form.setAttribute("method", "post");
    form.setAttribute("action", path);

    for(var key in params) {
        if(params.hasOwnProperty(key)) {
            var hiddenField = document.createElement("input");
            hiddenField.setAttribute("type", "hidden");
            hiddenField.setAttribute("name", key);
            hiddenField.setAttribute("value", params[key]);

            form.appendChild(hiddenField);
        }
    }
    document.body.appendChild(form);
    form.submit();
}