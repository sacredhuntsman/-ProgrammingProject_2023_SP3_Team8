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
            if (data === null) {
                $('#chat-box').html("<p>You are not a member of this channel.</p>");
                return;
            }

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

function FetchPrivateMessages(groupId, urlPath, existingMessagesCount) {
    let _url = "";
    let msgs = [];
    console.log(urlPath);
    console.log(urlPath == null || urlPath === "undefined" || typeof urlPath === "undefined" || urlPath === "");
    if (!(urlPath == null || urlPath === "undefined" || typeof urlPath === "undefined" || urlPath === "")) {
        _url = urlPath + "/fetch-private-messages";
    } else {
        _url = "/fetch-private-messages";
    }
    console.log(_url);
    console.log("Fetching new messages...");
    $.ajax({
        url: _url,
        method: "GET",
        data: {
            groupId: groupId,
        },
        success: function (data) {
            console.log(data);
            //clear existing messages
            $('#chat-box').html("");
            for(let i= 0; i < data.length; i++) {
                if(data[i].messageText !== "") {
                    $('#chat-box').append(formatMessage(data[i].isSender, data[i].senderName, data[i].timestamp, data[i].message));

                }
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

    var formattedDate = convertUTCDateToLocalDate(new Date(formattedTimestamp));

    // convert the date and time to match this format Time AM/PM day/month/Year
    formattedDate = formattedDate.toLocaleString('en-US', { hour: 'numeric', minute: 'numeric', hour12: true }) + " " + formattedDate.toLocaleString('en-US', { day: 'numeric', month: 'numeric', year: 'numeric' });

    console.log(formattedDate);

    return `
      <div class="chat-message w-full flex my-6 ${isSender}">
                <div class="sender-img text-center">
                 <img src="https://chatterboxavatarstorage.blob.core.windows.net/blob/${userName}" alt="user image">
                </div>
                <div class="message-content">
                    <div class="message-info flex mx-2">
                        <div class="message-sender-name text-white mr-2">${userName}</div>
                        <div class="message-stats text-slate-200 text-xs italic" style="line-height: 24px;"> @ ${formattedDate}</div>
                    </div>
                    <div class="message-text text-white mx-2">
                        ${messageText}
                    </div>
                </div>
            </div>
    `;
}
function convertUTCDateToLocalDate(date) {
    var newDate = new Date(date.getTime()+date.getTimezoneOffset()*60*1000);

    var offset = date.getTimezoneOffset() / 60;
    var hours = date.getHours();

    newDate.setHours(hours - offset);

    return newDate;
}



