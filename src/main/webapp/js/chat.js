// ajax request to /fetch-new-messages using jquery
function fetchNewMessages(groupId, channelId) {
    console.log("Fetching new messages...");
    $.ajax({
        url: "/fetch-new-messages",
        method: "GET",
        data: {
            groupId: groupId,
            channelId: channelId
        },
        success: function (data) {
            console.log(data);
            //clear existing messages
            $('#chat-box').html("");
            for(let i= 0; i < data.length; i++) {
                $('#chat-box').append(formatMessage(data[i].isSender, data[i].senderName, data[i].timestamp, data[i].message));
            }


        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("AJAX Request Error: " + errorThrown);
        }
    });
}

function formatMessage(isSender, userName, formattedTimestamp, messageText) {
    return `
      <div class="chat-message w-full flex my-6 ${isSender}">
                <div class="sender-img text-center mx-4"></div>
                <div class="message-content">
                    <div class="message-info flex mx-2">
                        <div class="messange-sender-name text-white mr-2">${userName}</div>
                        <div class="message-stats text-slate-400 text-xs italic" style="line-height: 24px;"> @ ${formattedTimestamp}</div>
                    </div>
                    <div class="message-text text-white mx-2">
                        ${messageText}
                    </div>
                </div>
            </div>
    `;
}



