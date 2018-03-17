
function toggle(fieldset) {
    var x = document.getElementById(fieldset);

    if (x.style.display === "none") {
        x.style.display = "block";
    } else {
        x.style.display = "none";
    }
}


function saveDeleteUsername(username){
    this.redirectPath = "deleteUser?username=" + username;
    console.log(username);
    document.getElementById("deleteUserYesButton").setAttribute("href", this.redirectPath);
}
