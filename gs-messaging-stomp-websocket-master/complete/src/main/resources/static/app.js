var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    //NOTE: see how this corresponds to the web-socket defined in the WebSocketConfig.java
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        //NOTE: subscribes to the greetings topic over the websocket, we give it a function to call whenever a message
        //is received down the topic
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
            //NOTE: can we easily subscribe to > 1 topic in here?
        });
            //yes, u can, easy to subscribe to > 1 topic here
        stompClient.subscribe('/topic/testTopic/1', function (msg) {
            alert("MessageRecieved on subscription1 "+ msg.body);
        });

        stompClient.subscribe('/topic/testTopic/2', function (msg) {
            alert("MessageRecieved on subscription2 "+ msg.body);
        });

    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    //NOTE: this sends the name object over the web socket to the /app/hello destination,
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});

