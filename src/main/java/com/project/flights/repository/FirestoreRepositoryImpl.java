package com.project.flights.repository;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.project.flights.mapper.DocumentToFlightMapper;
import com.project.flights.model.Flight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ExecutionException;

import static java.lang.System.getProperty;
import static java.nio.charset.StandardCharsets.UTF_8;

@ConditionalOnProperty("firebaseKey")
@Repository
public class FirestoreRepositoryImpl implements FirestoreRepository{

    private Firestore firestoreDB;
    public static final String FLIGHT_COLLECTION = "Flight";
    private final DocumentToFlightMapper mapperToFlight;
    private static final Logger LOGGER = LoggerFactory.getLogger(FirestoreRepositoryImpl.class);

    @Autowired
    public FirestoreRepositoryImpl(DocumentToFlightMapper mapperToFlight) {
        this.mapperToFlight = mapperToFlight;
    }

    @PostConstruct
    private void initFirestore() throws IOException {
        InputStream serviceAccount = new ByteArrayInputStream(getProperty("firebaseKey").getBytes(UTF_8));
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .build();
        FirebaseApp.initializeApp(options);

        firestoreDB = FirestoreClient.getFirestore();
    }

    @Override
    public Flight addFlight(Flight flight) {

        Flight flightToReturn;

        Map<String, Object> flightData = new HashMap<>();
        flightData.put("flightNumber", flight.getFlightNumber());
        flightData.put("origin", flight.getOrigin());
        flightData.put("destination", flight.getDestination());
        flightData.put("flightDeparture", flight.getFlightDeparture());
        flightData.put("airlineCompany", flight.getAirlineCompany());
        flightData.put("airport1", flight.getAirport1());
        flightData.put("airport2", flight.getAirport2());
        flightData.put("adultPrice", flight.getAdultPrice());
        flightToReturn = flight;

        ApiFuture<WriteResult> document = firestoreDB.collection(FLIGHT_COLLECTION).document().set(flightData);
        return flightToReturn;
    }

    @Override
    public void removeFlightById(String flightNumber) {
        String documentId = this.getFlightDocumentId(flightNumber);
        ApiFuture<WriteResult> result = firestoreDB.collection(FLIGHT_COLLECTION).document(documentId).delete();
    }

    @Override
    public void updateFlight(String flightNumber, Map flightDetails) {
        String documentId = this.getFlightDocumentId(flightNumber);
        firestoreDB.collection(FLIGHT_COLLECTION).document(documentId).set(flightDetails);
    }

    public String getFlightDocumentId(String flightNumber){
        CollectionReference ref = firestoreDB.collection(FLIGHT_COLLECTION);
        Query flightNumberQuery = ref.whereEqualTo("flightNumber", flightNumber);
        ApiFuture<QuerySnapshot> flightNumberQuerySnapshot = flightNumberQuery.get();
        String documentId = "";
        try {
            QuerySnapshot querySnapshot = flightNumberQuerySnapshot.get();
            documentId = querySnapshot.getDocuments().get(0).getId();
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.error("No flight document found");
        }
        return documentId;
    }


    @Override
    public List<Flight> getAll() {
        ApiFuture<QuerySnapshot> query = firestoreDB.collection(FLIGHT_COLLECTION).get();
        List<Flight> flights = new ArrayList<>();

        try {
            QuerySnapshot querySnapshot = query.get();
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            for(QueryDocumentSnapshot document: documents){
                Flight flight = mapperToFlight.mapDocumentToFlight(document);
                flights.add(flight);
            }
        }catch (InterruptedException | ExecutionException e){
            LOGGER.error("Flights not found", e);
        }
        return flights;
    }

    @Override
    public Flight getFlightById(String flightNumber) {
        String documentId = this.getFlightDocumentId(flightNumber);
        ApiFuture<DocumentSnapshot> documentSnapshotApiFuture = firestoreDB.collection(FLIGHT_COLLECTION).document(documentId).get();
        Flight flight = new Flight();
        try {
            DocumentSnapshot documentSnapshot = documentSnapshotApiFuture.get();
            flight = mapperToFlight.mapDocumentToFlight(documentSnapshot);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return flight;
    }
}
