package br.com.fiap.gsjava.service;

import br.com.fiap.gsjava.dto.LocalizacaoResponse;
import br.com.fiap.gsjava.model.Localizacao;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@Service
public class NominatimService {

    private final RestTemplate restTemplate = new RestTemplate();

    public String buscarEndereco(double latitude, double longitude) {
        String url = String.format(
                "https://nominatim.openstreetmap.org/reverse?format=json&lat=%f&lon=%f&zoom=18&addressdetails=1",
                latitude, longitude
        );
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "SeuApp/1.0");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        Map<String, Object> result = response.getBody();

        if (result != null && result.containsKey("display_name")) {
            return (String) result.get("display_name");
        }
        return "Endereço não encontrado";
    }

    public LocalizacaoResponse criarResposta(Localizacao localizacao) {
        String endereco = buscarEndereco(localizacao.getLatitude(), localizacao.getLongitude());
        return new LocalizacaoResponse(
                localizacao.getId(),
                localizacao.getLatitude(),
                localizacao.getLongitude(),
                endereco
        );
    }
}
