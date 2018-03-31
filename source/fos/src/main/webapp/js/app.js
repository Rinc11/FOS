function toggle(fieldset) {
    var x = document.getElementById(fieldset);

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
$(document).ready(function()
{
    $("#showhide").click(function()
    {
        if ($(this).data('val') == "1")
        {
            $("#pwd").prop('type','text');
            $("#eye").attr("class","glyphicon glyphicon-eye-close");
            $(this).data('val','0');
        }
        else
        {
            $("#pwd").prop('type', 'password');
            $("#eye").attr("class","glyphicon glyphicon-eye-open");
            $(this).data('val','1');
        }
    });
});

function password_generator(inputPWID, inputPWIDConfirm) {
    var string = "abcdefghijklmnopqrstuvwxyz"; //to upper
    var stringUpper = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
    var numeric = '0123456789';
    var password = "";
    var character = "";
    while( password.length<7) {
        entity1 = Math.ceil(string.length * Math.random()*Math.random());
        character += string.charAt( entity1 );
        entity2 = Math.ceil(numeric.length * Math.random()*Math.random());
        character += numeric.charAt( entity2 );
        entity3 = Math.ceil(stringUpper.length * Math.random()*Math.random());
        character += stringUpper.charAt( entity3 );

        password = character;
    }

    document.getElementById(inputPWID).value = password;
    document.getElementById(inputPWIDConfirm).value = password;
    alert("Neues Passwort: " + password);
}

