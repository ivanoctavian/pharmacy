package com.pharmacy.modules.approved_medicines.service;

import com.pharmacy.config.ApiException;
import com.pharmacy.modules.approved_medicines.model.FdaApprovedMedicine;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ApiFdaService {
    private final WebClient webClient;

    @Value("${fda.api.client.medicines.search.path}")
    private String searchForMedicinePath;

    public ApiFdaService(WebClient.Builder builder,
                         @Value("${fda.api.client.medicines.search.baseurl}") String baseUrl){
            this.webClient = builder
                    .baseUrl(baseUrl)
                    .defaultHeader("Accept", "application/json")
                    .build();
        log.info("------------------ WEBCLIENT INITIALIZED SUCCESS-------------");
    }

    public FdaApprovedMedicine searchForMedicineByName(String medicine){
        log.info("SearchForMedicineByName in FDA Api Start. Medicine name: " + medicine);
        return this.webClient
                .get()
                .uri(builder -> builder
                        .path(this.searchForMedicinePath)
                        .queryParam("search", "openfda.brand_name:" + medicine)
                        .build())
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().is2xxSuccessful()) {
                        return clientResponse.bodyToMono(FdaApprovedMedicine.class);
                    } else if (clientResponse.statusCode().is4xxClientError()) {
                        //inca nu m am decis daca arunc exceptie sau returnez null si mai departe in business logic/service fac eu ceva.
//                        return clientResponse.bodyToMono(ErrorResponse.class)
//                                .flatMap(errorResponse -> Mono.error(new ApiException(HttpStatus.NOT_FOUND, errorResponse.getError().getMessage())));
                        return Mono.empty();
                    } else {
                        return Mono.error(new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred"));
                    }
                })
                .block();
    }

    private String searchForMedicineByNamev0(String medicine){
        log.info("SearchForMedicineByName: START");
        return (String) this.webClient
                .get()
                .uri(builder -> builder
                        .path(this.searchForMedicinePath)
                        .queryParam("search", "openfda.brand_name:" + medicine)
                        .build())
                .exchangeToMono(clientResponse -> {
                    if(clientResponse.statusCode().is2xxSuccessful()){
                        return Mono.just(clientResponse);
                    }else
                    if(clientResponse.statusCode().is4xxClientError()){
                        return Mono.just("NOT_FOUND");
                    }else{
                        throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error.");
                    }
                })
                .block();
    }
}
