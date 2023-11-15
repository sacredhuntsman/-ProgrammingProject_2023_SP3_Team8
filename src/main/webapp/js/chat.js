function fetchNewMessages(groupId, channelId, urlPath, existingMessagesCount) {
    let _url = "";
    let msgs = [];
    console.log(urlPath);
    console.log(urlPath == null || urlPath === "undefined" || typeof urlPath === "undefined" || urlPath === "");
    if (!(urlPath == null || urlPath === "undefined" || typeof urlPath === "undefined" || urlPath === "")) {
        _url = urlPath + "/fetch-new-messages";
    } else {
        _url = "/fetch-new-messages";
    }
    console.log(_url);
    console.log("Fetching new messages...");
    $.ajax({
        url: _url,
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
            if(existingMessagesCount < data.length) {
                console.log("Scrolling to bottom as new msg found...")
                scrollChatToBottom();
            }


        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("AJAX Request Error: " + errorThrown);
        }
    });

}

function formatMessage(isSender, userName, formattedTimestamp, messageText) {

    // Input date string
    var inputDateStr = formattedTimestamp;

    // Parse the input date string into a JavaScript Date object
    var inputDate = new Date(inputDateStr);

    // Format the date as "hh:mma dd/MM"
    var formattedDate = inputDate.toLocaleTimeString('en-US', {
        hour: '2-digit',
        minute: '2-digit',
        hour12: true,
    }) + ' ' + inputDate.toLocaleDateString('en-US', {
        day: '2-digit',
        month: '2-digit',
    });

    console.log(formattedDate); // Output: "10:42AM 08/11"

    return `
      <div class="chat-message w-full flex my-6 ${isSender}">
                <div class="sender-img text-center">
                 <img src="https://chatterboxavatarstorage.blob.core.windows.net/blob/${userName}" alt=" ">
                </div>
                <div class="message-content">
                    <div class="message-info flex mx-2">
                        <div class="message-sender-name text-white mr-2">${userName}</div>
                        <div class="message-stats text-slate-400 text-xs italic" style="line-height: 24px;"> @ ${formattedDate}</div>
                    </div>
                    <div class="message-text text-white mx-2">
                        ${messageText}
                    </div>
                </div>
            </div>
    `;
}



