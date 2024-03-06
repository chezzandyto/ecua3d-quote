package com.ecua3d.quote.client;

import com.ecua3d.quote.vo.FilamentToQuoteResponse;
import com.ecua3d.quote.vo.QualityToQuoteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "clientFilamentQuote", url = "${quote.url.corporative}")
public interface ICorporativeClient {
    @RequestMapping(method = RequestMethod.GET, value = "/filament/{filamentId}")
    ResponseEntity<FilamentToQuoteResponse> getByFilamentId(@PathVariable Integer filamentId);

    @RequestMapping(method = RequestMethod.GET, value = "/quality/{qualityId}")
    ResponseEntity<QualityToQuoteResponse> getByQualityId(@PathVariable Integer qualityId);

}
