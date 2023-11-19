package Classes;

import Classes.Database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import com.azure.communication.rooms.models.AddOrUpdateParticipantsResult;
import com.azure.communication.rooms.models.CommunicationRoom;
import com.azure.communication.rooms.models.CreateRoomOptions;
import com.azure.communication.rooms.models.ParticipantRole;
import com.azure.communication.rooms.models.RemoveParticipantsResult;
import com.azure.communication.rooms.models.RoomParticipant;
import com.azure.communication.rooms.models.UpdateRoomOptions;
import com.azure.communication.rooms.implementation.models.CommunicationErrorResponseException;
import com.azure.communication.common.CommunicationIdentifier;
import com.azure.communication.common.CommunicationUserIdentifier;
import com.azure.communication.common.implementation.CommunicationConnectionString;
import com.azure.communication.rooms.RoomsClient;
import com.azure.communication.rooms.RoomsClientBuilder;
import com.azure.communication.identity.CommunicationIdentityClient;
import com.azure.communication.identity.CommunicationIdentityClientBuilder;
import com.azure.communication.identity.models.CommunicationTokenScope;
import com.azure.core.http.rest.PagedIterable;
import com.azure.core.http.rest.PagedResponse;
import com.azure.core.util.Context;
import com.azure.core.credential.AccessToken;
import com.azure.core.credential.AzureKeyCredential;

public class Rooms2 {
    Database database = new Database();
    static String CONNECTION_STRING;
    static RoomParticipant participant_1;
    static RoomsClient roomsClient;
    static CommunicationIdentityClient communicationClient;

    public Rooms2() {
        loadCredentials();
        roomsClient = createRoomsClientWithConnectionString();
        communicationClient = getCommunicationIdentityClientBuilder().buildClient();
    }

    private void loadCredentials() {
        Properties properties = new Properties();

        try {
            // Loading application.properties file and storing url,username and password in
            // variables for conntion
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
            properties.load(inputStream);
            CONNECTION_STRING = properties.getProperty("acs.connectionstring");

        } catch (IOException e) {
            e.printStackTrace();
            // TO DO exception handling
        }
    }

    public static RoomsClient createRoomsClientWithConnectionString() {
        RoomsClient roomsClient = new RoomsClientBuilder().connectionString(CONNECTION_STRING).buildClient();

        return roomsClient;
    }

    public static CommunicationIdentityClientBuilder getCommunicationIdentityClientBuilder() {
        CommunicationIdentityClientBuilder builder = new CommunicationIdentityClientBuilder();
        CommunicationConnectionString connectionStringObject = new CommunicationConnectionString(CONNECTION_STRING);
        String endpoint = connectionStringObject.getEndpoint();
        String accessKey = connectionStringObject.getAccessKey();
        builder.endpoint(endpoint)
                .credential(new AzureKeyCredential(accessKey));

        return builder;
    }

    public static CommunicationIdentifier addOrUpdateParticipants(String roomId) {
        participant_1 = new RoomParticipant(communicationClient.createUser());
        System.out.println(roomId);
        System.out.println(participant_1.getCommunicationIdentifier().getRawId());
        try {
            List<RoomParticipant> participantsToAddAndUpdate = new ArrayList<>();

            // Updating current participant
            participantsToAddAndUpdate.add(participant_1.setRole(ParticipantRole.PRESENTER));

            System.out.print("Adding/Updating participants())...\n");

            AddOrUpdateParticipantsResult addOrUpdateParticipantsResult = roomsClient.addOrUpdateParticipants(roomId,
                    participantsToAddAndUpdate);

            System.out.println("Participant(s) added/updated");
        } catch (Exception ex) {
            System.out.println(ex);
        }

        // return participant_1 identifier
        return participant_1.getCommunicationIdentifier();
    }

    private String createRoom(int groupID, int channelID) {
        RoomsClient roomsClient = new RoomsClientBuilder().connectionString(CONNECTION_STRING).buildClient();
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

    public String generateToken(CommunicationIdentifier userIdentity) {
        CommunicationIdentityClient communicationIdentityClient = new CommunicationIdentityClientBuilder()
                .connectionString(CONNECTION_STRING)
                .buildClient();
        List<CommunicationTokenScope> scopes = new ArrayList<>(Arrays.asList(CommunicationTokenScope.VOIP));

        AccessToken accessToken = communicationIdentityClient.getToken((CommunicationUserIdentifier) userIdentity, scopes);
        OffsetDateTime expiresAt = accessToken.getExpiresAt();
        String token = accessToken.getToken();
        System.out.println("\nIssued an access token with 'voip' scope that expires at: " + expiresAt + ": " + token);

        return token;
    }

}