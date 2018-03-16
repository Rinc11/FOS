
function toggle() {
    var x = document.getElementById("filteroptionen");
    if (x.style.display === "none") {
        x.style.display = "block";
    } else {
        x.style.display = "none";
    }
}


function saveDeleteUsernam(username){
    this.redirectPath = "deleteUser?username=" + username;
    console.log(username);
    document.getElementById("deleteUserYesButton").setAttribute("href", this.redirectPath);
}
