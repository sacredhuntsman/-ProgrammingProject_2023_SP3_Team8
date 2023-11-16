package Classes;

import java.io.IOException;
import java.io.InputStream;

import com.azure.communication.common.*;
import com.azure.communication.identity.*;
import com.azure.communication.identity.models.*;
import com.azure.core.credential.*;
import com.azure.core.http.rest.PagedIterable;
import com.azure.communication.rooms.*;
import com.azure.communication.rooms.implementation.models.ParticipantRole;
import com.azure.communication.rooms.implementation.models.RoomParticipant;
import com.azure.communication.rooms.models.AddOrUpdateParticipantsResult;
import com.azure.communication.rooms.models.CommunicationRoom;
import com.azure.communication.rooms.models.CreateRoomOptions;
import com.azure.communication.identity.CommunicationIdentityClient;
import com.azure.communication.identity.CommunicationIdentityClientBuilder;

import java.io.IOException;
import java.time.*;
import java.util.*;

import Classes.Database;

public class Rooms {
    private String connectionString;
    Database database = new Database();

    public Rooms() {
        loadCredentials();
    }

    private void loadCredentials() {
        Properties properties = new Properties();

        try {
            // Loading application.properties file and storing url,username and password in
            // variables for conntion
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
            properties.load(inputStream);
            connectionString = properties.getProperty("acs.connectionstring");

        } catch (IOException e) {
            e.printStackTrace();
            // TO DO exception handling
        }
    }

    private String createRoom(int groupID, int channelID) {
        RoomsClient roomsClient = new RoomsClientBuilder().connectionString(connectionString).buildClient();
        // create Room in ACS and return ID
        OffsetDateTime validFrom = OffsetDateTime.now();
        OffsetDateTime validUntil = validFrom.plusDays(30);

        CreateRoomOptions createRoomOptions = new CreateRoomOptions()
                .setValidFrom(validFrom)
                .setValidUntil(validUntil);

        CommunicationRoom roomCreated = roomsClient.createRoom(createRoomOptions);

        String roomID = roomCreated.getRoomId();
        // Insert room into Database
        database.addRoom(groupID, channelID, roomID);
        System.out.println("\nCreated a room with id: " + roomCreated.getRoomId());
        return roomID;
    }

    public String getRoom(int groupID, int channelID) {
        // check for room, if no room create room calling create room
        String roomID = database.getRoom(groupID, channelID);
        if (roomID == null || roomID.isEmpty()) {
            roomID = createRoom(groupID, channelID);
        }
        return roomID;
    }

    public String createIdentity(String roomID) {
        CommunicationIdentityClient communicationIdentityClient = new CommunicationIdentityClientBuilder()
                .connectionString(connectionString)
                .buildClient();

        List<CommunicationTokenScope> scopes = Arrays.asList(CommunicationTokenScope.VOIP);
        CommunicationUserIdentifierAndToken result = communicationIdentityClient.createUserAndToken(scopes);
        CommunicationUserIdentifier user = result.getUser();
        String userID = user.getId();
        System.out.println("\nCreated a user identity with ID: " + userID);
        AccessToken accessToken = result.getUserToken();
        OffsetDateTime expiresAt = accessToken.getExpiresAt();
        String token = accessToken.getToken();
        System.out.println("\nIssued an access token with 'voip' scope that expires at: " + expiresAt + ": " + token);

        addToRoom(roomID, user);

        return token;
    }

    // add user to room
    public void addToRoom(String roomId, CommunicationUserIdentifier user) {
        
    }


    private void returnAPI() {

    }
}
