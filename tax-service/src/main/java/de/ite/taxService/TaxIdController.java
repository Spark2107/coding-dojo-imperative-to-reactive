package de.ite.taxService;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.lang.Thread.sleep;

@RestController
public class TaxIdController {

    private final Map<String, String> taxIds;

    TaxIdController(RestTemplateBuilder restTemplateBuilder) {
        taxIds = new HashMap<>();
    }

    @GetMapping("/{name}")
    public String getNewTaxId(@PathVariable("name") String name) throws InterruptedException {
        String taxId = taxIds.get(name);
        if (taxId == null || taxId.equals(""))
            taxId = UUID.randomUUID().toString();
        taxIds.put(name, taxId);
        sleep(1000);
        return taxId;
    }

}