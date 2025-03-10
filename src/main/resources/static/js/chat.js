'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');


var username = null;
var stompClient = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];
 


// Connect WebSocket and Subscribe to Chat Topic
function connect() {
    let socket = new SockJS('/ws');  // WebSocket endpoint in Spring Boot
    stompClient = Stomp.over(socket);
    
    stompClient.connect({}, function () {
        console.log("Connected to WebSocket");

        stompClient.subscribe("/topic/public", function (message) {
            let chatMessage = JSON.parse(message.body);
            displayMessage(chatMessage);
        });
    });
}

function connect(event) {
    username = document.querySelector('#name').value.trim();

    if (!username) {
        alert("Please enter a username!");
        return;
    }

    usernamePage.classList.add('hidden');
    chatPage.classList.remove('hidden');

    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, onConnected, onError);

    event.preventDefault();
}

function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/public', onMessageReceived);

    // Tell your username to the server
    stompClient.send("/app/chat.addUser",
        {},
        JSON.stringify({sender: username, type: 'JOIN'})
    )

    connectingElement.classList.add('hidden');
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}

 function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageContent,
            type: 'CHAT'
        };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
    messageInput.value = '';
    }
    event.preventDefault();
}
        function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    if(message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
    } else {
        messageElement.classList.add('chat-message');

        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);

        messageElement.appendChild(avatarElement);

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}


function getAvatarColor(messageSender) {
    let hash = 0;
    for (let i = 0; i < messageSender.length; i++) {
        hash = (hash << 5) - hash + messageSender.charCodeAt(i);
        hash |= 0;  // Convert to 32-bit integer
    }
    let index = Math.abs(hash % colors.length);
    return colors[index];
}

usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', sendMessage, true)

function leaveChat() {
    if (stompClient !== null) {
        let leaveMessage = {
            sender: username,
            type: "LEAVE"
        };

        stompClient.send("/app/chat.leave", {}, JSON.stringify(leaveMessage));

        stompClient.disconnect(() => {
            console.log("Disconnected from chat");
            sessionStorage.removeItem("username");  // Remove username from session
        });
    }
}
stompClient.subscribe("/topic/public", (message) => {
    let chatMessage = JSON.parse(message.body);

    if (chatMessage.type === "LEAVE") {
        showMessage(`${chatMessage.sender} has left the chat 🚪`);
    } else {
        showMessage(`${chatMessage.sender}: ${chatMessage.content}`);
    }
});

// Display Message in Chat Box
function displayMessage(message) {
    let messageElement = document.createElement("li");

    if (message.type === "LEAVE") {
        messageElement.classList.add("event-message");
        messageElement.textContent = `${message.sender} has left the chat.`;
    } else {
        messageElement.classList.add("chat-message");
        messageElement.innerHTML = `<span>${message.sender}:</span> ${message.content}`;
    }

    chatBox.appendChild(messageElement);
    chatBox.scrollTop = chatBox.scrollHeight; // Auto-scroll to the latest message
}


// Load Chat History
function loadChatHistory() {
    fetch("/api/chat/history")
        .then(response => response.json())
        .then(messages => {
            messages.forEach(displayMessage);
        })
        .catch(error => console.error("Error fetching chat history:", error));
}

// Initialize Chat
window.onload = function () {
    loadChatHistory();
    connect();
};

messageForm.addEventListener("submit", sendMessage);

