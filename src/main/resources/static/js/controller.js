let id = document.getElementsByName('id')[0].getAttribute('content');

connect(id, function (message) {

});

function registerUser() {
    send("registerUser()");
    document.getElementById('login').style.display = "none";
    document.getElementById('register').style.display = "block";
}

function retransmit(elem) {
    let v = document.getElementById(elem).value;
    send("printToElement('" + elem + "','" + v + "')");
}

function play() {
    send("play()");
    document.getElementById('login').style.display = "block";
    document.getElementById('start').style.display = "none";
}