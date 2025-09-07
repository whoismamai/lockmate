let stompClient = null;
let selectedLockId = null;
let selectedSerialNumber = null;

async function fetchLocks() {
    const response = await fetch("/api/locks");
    const locks = await response.json();
    renderLocks(locks);
}

function renderLocks(locks) {
    const list = document.getElementById("lockList");
    list.innerHTML = "";

    if (!Array.isArray(locks)) {
        console.warn("Locks is not an array:", locks);
        return;
    }

    locks.forEach(lock => {
        const li = document.createElement("li");

        const statusText = lock.isOnline 
            ? (lock.isLocked ? "Locked" : "Unlocked")
            : "Offline";

        li.innerHTML = `
            <span>${lock.serialNumber}</span>
            <span class="${lock.isOnline ? (lock.isLocked ? "locked" : "unlocked") : "offline"}">
                ${statusText}
            </span>
        `;

        li.onclick = () => connectToLock(lock);
        list.appendChild(li);
    });
}

function connectToLock(lock) {
    selectedLockId = lock.id;
    selectedSerialNumber = lock.serialNumber;

    document.getElementById("lockDetails").style.display = "block";
    document.getElementById("selectedLockTitle").innerText =
        `Selected Lock: ${lock.serialNumber}`;
    // document.getElementById("status").innerText =
    //     `Current Status: ${lock.isLocked ? "Locked" : "Unlocked"} (Online: ${lock.isOnline})`;

    // Clear logs
    document.getElementById("logList").innerHTML = "";

    // Setup WebSocket
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        document.getElementById("lockStatus").innerText = "Connected";

        // Subscribe to lock-specific events
        stompClient.subscribe('/topic/status', (message) => {
            const lockUpdate = JSON.parse(message.body);

            // Update lock row in Available Locks table
            updateLockRow(lockUpdate);

            // If this lock is the one currently connected → also update status & logs
            if (selectedLockId === lockUpdate.id) {
                document.getElementById("lockStatus").innerText =
                    `Status: ${lockUpdate.status}`;
            }
        });

        function updateLockRow(lockUpdate) {
            const row = document.querySelector(`#lock-row-${lockUpdate.id}`);
            if (row) {
                // Update status cell
                row.querySelector(".lock-status").innerText =
                    lockUpdate.isOnline ? lockUpdate.status : "OFFLINE";
            }
        }

        stompClient.subscribe(`/topic/logs.${selectedSerialNumber}`, function (message) {
            const log = JSON.parse(message.body);

            // alert(JSON.stringify(log)); // Debugging line to inspect log structure

            appendLog(`[${log.serialNumber}] [${log.dateCreated}] ${log.message}`);
        });
        
    });
}

function appendLog(text) {
    const li = document.createElement("li");
    li.innerText = text;
    const list = document.getElementById("logList");
    list.appendChild(li);
    list.scrollTop = list.scrollHeight;
}

function lockDevice() {
    if (selectedSerialNumber) {
        stompClient.send("/app/lock", {}, JSON.stringify({ deviceId: selectedSerialNumber }));
    }
}

function unlockDevice() {
    if (selectedSerialNumber) {
        stompClient.send("/app/unlock", {}, JSON.stringify({ deviceId: selectedSerialNumber }));
    }
}

function buzzDevice() {
    if (selectedLockId) {
        fetch(`/api/locks/${selectedLockId}/buzz`, { method: "PUT" })
            .then(() => appendLog("Buzz command sent"));
    }
}

function startBuzz() {
    if (selectedSerialNumber && stompClient) {
        stompClient.send("/app/buzz", {}, JSON.stringify({ deviceId: selectedSerialNumber }));
        appendLog("Buzz started...");

        // Visual feedback on button
        const buzzbButton = document.querySelector(".buzz");
        buzzbButton.classList.add("pressed");
    }
}

function stopBuzz() {
    if (selectedSerialNumber && stompClient) {
        stompClient.send("/app/stopBuzz", {}, JSON.stringify({ deviceId: selectedSerialNumber }));
        appendLog("Buzz stopped.");
        const buzzbButton = document.querySelector(".buzz");
        buzzbButton.classList.remove("pressed");
    }
}

// Load locks on page start
window.onload = fetchLocks;
