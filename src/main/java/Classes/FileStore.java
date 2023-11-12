package Classes;

import com.azure.storage.blob.BlobClientBuilder;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.azure.storage.blob.models.BlobItem;
import com.azure.storage.blob.models.BlobProperties;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.io.InputStream;

public class FileStore {
    private String connectionString;
    private String containerName;

    public FileStore() {
        loadCredentials();
        System.out.println(connectionString);
        System.out.println(containerName);
    }

    private void loadCredentials() {
        Properties properties = new Properties();
        try {
            // Loading application.properties file and storing url,username and password in
            // variables for conntion
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
            properties.load(inputStream);

            connectionString = properties.getProperty("fs.connectionstring");
            containerName = properties.getProperty("fs.containername");
        } catch (IOException e) {
            e.printStackTrace();
            // TO DO exception handling
        }
    }

    public List<String> getFiles() {
        List<String> files = new ArrayList<>();
        BlobContainerClient blobContainerClient = new BlobContainerClientBuilder().connectionString(connectionString)
                .containerName(containerName).buildClient();
        for (BlobItem blobItem : blobContainerClient.listBlobs()) {
            files.add(blobItem.getName());
        }
        System.out.println(files);
        return files;
    }

    // function to upload a file to the blob storage from given local file path, and given file name
    public void uploadFile(InputStream fileStream, String fileName) throws IOException  {
        BlobContainerClient blobContainerClient = new BlobContainerClientBuilder().connectionString(connectionString)
                .containerName(containerName).buildClient();
        blobContainerClient.getBlobClient(fileName).upload(fileStream, fileStream.available());        
    }

    public void updateFile(InputStream fileContent, String username) throws IOException {
        // Delete the existing file, if it exists
        BlobContainerClient blobContainerClient = new BlobContainerClientBuilder().connectionString(connectionString)
                .containerName(containerName).buildClient();

        if (blobContainerClient.getBlobClient(username).exists()) {

            blobContainerClient.getBlobClient(username).delete();
        }

        // Upload the new file
        blobContainerClient.getBlobClient(username).upload(fileContent, fileContent.available());
    }
}