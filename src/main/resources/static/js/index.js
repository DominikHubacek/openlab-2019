generateQR('qrCode');

function generateQR(target) {
    let metas = document.getElementsByName('id');
    let id = metas[0].getAttribute('content');
    localStorage.setItem('id', id);
    new QRCode(document.getElementById(target), {
        text: serverURL + "controller?id=" + id,
        width: screen.width * 0.1,
        height: screen.width * 0.1,
        colorDark : "#202020",
        colorLight : "#dda410",
        correctLevel : QRCode.CorrectLevel.M
    });
    if (location.hostname === "localhost") {
        connect(id);
    }
}

function registerUser() {
    document.getElementById('login').style.display = "none";
    document.getElementById('register').style.display = "block";
}

function play() {
    document.getElementById('login').style.display = "block";
    document.getElementById('start').style.display = "none";
}

function playWithoutSignIn() {
    let values = {
        method: 'none',
        name : null,
        email: null,
        password: null,
    };
    login(values);
}

function onSignIn(googleUser) {
    var profile = googleUser.getBasicProfile();
    let values = {
        method: 'google',
        name : profile.getGivenName() + ' ' + profile.getFamilyName(),
        email: profile.getEmail(),
        password: null,
    };
    gapi.auth2.getAuthInstance().disconnect();
    login(values);
}

function processForm(id) {
    let form = document.getElementById(id);
    if (id === 'registration') {
        registerNewUser({
            nickname: form.elements['nickname'].value,
            email: form.elements['email2'].value,
            password: form.elements['password2'].value,
        });
    } else {
        login({
            method: 'form',
            email: form.elements['email'].value,
            password: form.elements['password'].value,
        });
    }

}

function registerNewUser(user) {
    let request = new XMLHttpRequest();
    let url = serverURL + "register?email=" + user.email + "&name=" + user.nickname + "&pass=" + user.password;
    request.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            let resp = JSON.parse(this.responseText);
            if (resp === true) {
                Swal.fire({
                    position: 'center',
                    type: 'success',
                    title: 'Ste zaregistrovaný',
                    showConfirmButton: false,
                    timer: 3000
                });
                setTimeout(function() {
                    login({
                        method: 'form',
                        email: user.email,
                        password: user.password
                    })
                }, 3000);
            } else {
                Swal.fire({
                    position: 'center',
                    type: 'error',
                    title: 'Taký používateľ úž je zaregistrovaný',
                    showConfirmButton: false,
                    timer: 3000
                });
            }
        }
    };
    request.open("POST", url, false);
    request.send();
}

function login(values) {
    let request = new XMLHttpRequest();
    let url = serverURL + "login?email=" + values.email + "&name=" + values.name + "&pass=" + values.password + "&method=" + values.method;
    request.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            let response = JSON.parse(this.responseText);
            if (response === false) {
                Swal.fire({
                    position: 'center',
                    type: 'error',
                    title: 'Skontrolujte prihlasovacie údaje alebo zaregistrujte sa.',
                    showConfirmButton: false,
                    timer: 3000
                });
            } else {
                location.href = serverURL + "categories";
            }
        }
    };
    request.open("GET", url);
    request.send();
}

function printToElement(elem, text) {
    document.getElementById(elem).value = text;
}