package PiComAPI;

import PiComAPI.Payload.MalformedPayloadException;
import PiComAPI.Payload.Payload;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

class TCPTestServer {

    public static void main(String[] args) throws Exception, MalformedPayloadException {

        String clientSentence;
        ServerSocket welcomeSocket = new ServerSocket(8000);

        while(true)
        {
            Socket connectionSocket = welcomeSocket.accept();
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            clientSentence = inFromClient.readLine();
            System.out.println(new Payload(clientSentence));
            connectionSocket.getOutputStream().write(ComUtils.setSerializedObject(new Payload(clientSentence)));
        }

    }
}
