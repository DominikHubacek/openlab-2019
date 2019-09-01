let serverURL = location.protocol + "//" + location.host + "/";
let sockURL = '/websocket';

let stompClient = null;
let numberCategory = 3;
let questionTime = 10;

//TODO remove after testing
if (location.hostname !== "localhost") {
    serverURL += "quizstudio/";
    sockURL = '/quizstudio' + sockURL;
}

function connect(id, handler = defHandler) {
    let socket = new SockJS(sockURL);
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/controller/' + id, handler);
    })
}

function defHandler(message) {
    let m = JSON.parse(message.body);
    if (m.func != null) {
        eval(m.func);
    }
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

function send(func, text, data) {
    stompClient.send("/app/message/" + localStorage.getItem('id'), {}, JSON.stringify({
        func: func,
        text: text,
        data: data
    }));
}