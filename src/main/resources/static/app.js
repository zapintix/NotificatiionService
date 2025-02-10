async function getUserIdFromServer(jwtToken) {
    const response = await fetch('http://localhost:8080/api/auth/user', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${jwtToken}`,
            'Content-Type': 'application/json'
        }
    });

    if (!response.ok) {
        throw new Error('Failed to fetch user information');
    }

    const userData = await response.json();
    return userData.id;
}

document.getElementById('connectButton').onclick = async function () {
    const jwtToken = document.getElementById('jwtToken').value;

    if (!jwtToken) {
        alert('Пожалуйста, введите JWT токен.');
        return;
    }

    try {
        const userId = await getUserIdFromServer(jwtToken);
        console.log('User ID:', userId);

        const socket = new WebSocket('ws://localhost:8080/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect(
            { Authorization: 'Bearer ' + jwtToken },
            function (frame) {
                console.log('Connected: ' + frame);
                console.log('Headers: ', frame.headers);

                // Подписка на персональные уведомления
                stompClient.subscribe(`/user/${userId}/notifications`, function (message) {
                    console.log("Personal notification:", message.body);
                    showNotification(message);
                });

                // Подписка на общие уведомления
                stompClient.subscribe("/topic/notifications", function (message) {
                    console.log("Global notification:", message.body);
                    showNotification(message);
                });

                // Подписка на обновления
                stompClient.subscribe("/topic/newUpdate", function (message) {
                    console.log("Updates:", message.body);
                    showNotification(message);
                });
            },
            function (error) {
                console.error('Error connecting to WebSocket: ', error);
            }
        );
    } catch (error) {
        console.error('Error fetching user ID:', error);
    }
};

document.getElementById('getNotificationsButton').onclick = function (e) {
    e.preventDefault();

    const jwtToken = document.getElementById('jwtToken').value;
    if (!jwtToken) {
        alert('Пожалуйста, введите JWT токен.');
        return;
    }

    stompClient.send("/app/getNotifications", { Authorization: "Bearer " + jwtToken }, "{}");
};

function showNotification(notificationRoot) {
    const notificationsList = document.getElementById('notificationsList');
    const li = document.createElement('li');
    li.textContent = JSON.stringify(notificationRoot.body);
    notificationsList.appendChild(li);
}
