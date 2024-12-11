package com.pharmacy.modules.approved_medicines.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
public class ApiFdaService {
    private final WebClient webClient;

    @Value("${fda.api.client.medicines.search.baseurl}")
    private String baseUrl;

    @Value("${fda.api.client.medicines.search.path}")
    private String searchForMedicinePath;

    //https://api.fda.gov/drug/label.json?search=openfda.brand_name:ibuprofen
    public ApiFdaService(@Value("${fda.api.client.baseurl}")
                         String baseUrl) {
            this.webClient = WebClient.builder()
                    .baseUrl(baseUrl)
                    .defaultHeader("Accept", "application/json")
                    .defaultHeader("ContentList-Type", "application/json")
                    .build();
        log.info("------------------ WEBCLIENT INITIALIZED SUCCESS--------------");
    }

//    @ExecutionTimeLogger

    public String searchForMedicineByName(String medicine){
        log.info("SearchForMedicineByName: START");
        return this.webClient
                .get()
                .uri(builder -> builder
                        .path(this.searchForMedicinePath)
                        .queryParam("search", "openfda.brandname:" + medicine)
                        .build())
                .retrieve() // trimite request-ul
                .bodyToMono(String.class) // primeste răspunsul sub formă de Mono
                .block(); //
    }
//    public String searchForMedicineByName(String medicine){
//        log.info("SearchForMedicineByName: START");
//        return this.webClient
//                .get()
//                .uri(builder -> builder
//                        .path(this.searchForMedicinePath)
//                        .queryParam("search", "openfda.brandname:" + medicine)
//                        .build())
//                .exchangeToMono(clientResponse -> {
//                    if(clientResponse.statusCode().is2xxSuccessful()) {
//                        log.info("Sucess! Client response http status code: " + clientResponse.statusCode());
//                        String response = new JiraApiClientResponse();
//                        response.setHttpStatus(HttpStatus.OK);
//                        return Mono.just(response);
//                    } else {
//                        log.error("Fail. Client response http status code: " + clientResponse.statusCode());
//                        return clientResponse.bodyToMono(JiraApiClientResponse.class)
//                                .flatMap(response -> {
//                                    response.setHttpStatus(clientResponse.statusCode());
//                                    return Mono.just(response);
//                                });}
//                })
//                .doOnSuccess(clientResponse -> log.info("Update request done. Data Retrieved successfully."))
//                .doOnError(clientResponse -> log.error("ERROR!!"));
//    }


//    public JiraApiClientResponse createIssue(JiraApiClientRequest jiraRequest){
//        log.info("JiraRequest: " + jiraRequest);
//        log.info("CreateIssue: START");
//        JiraApiClientResponse jiraApiClientResponse = new JiraApiClientResponse();
//        try {
//            jiraApiClientResponse = this.webClient
//                    .post()
//                    .uri(this.issueEndpoint)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .accept(MediaType.APPLICATION_JSON)
//                    .body(Mono.just(jiraRequest), JiraApiClientRequest.class)
//                    .exchangeToMono(clientResponse -> clientResponse.bodyToMono(JiraApiClientResponse.class)
//                            .flatMap(response -> {
//                                response.setHttpStatus(clientResponse.statusCode());
//                                log.info("Request done with status: " +clientResponse.statusCode());
//                                return Mono.just(response);
//                            }))
//                    .doOnSuccess(clientResponse -> log.info("CreateIssue request done. Data Retrieved successfully."))
//                    .doOnError(clientResponse -> log.error("ERROR!!"))
//                    .block();
//
//        }catch (Exception e){
//            log.error("Exception caught! ");
//            e.printStackTrace();
//        }
//        log.info("CreateIssue: Response: " + jiraApiClientResponse);
//        log.info("Createissue: END.");
//        return jiraApiClientResponse;
//    }
}
