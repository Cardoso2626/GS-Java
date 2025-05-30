package br.com.fiap.gsjava.service;

import br.com.fiap.gsjava.dto.LocalizacaoRequestDTO;
import br.com.fiap.gsjava.dto.LocalizacaoResponse;
import br.com.fiap.gsjava.model.LugarSeguro;
import br.com.fiap.gsjava.repository.LugarSeguroRepository;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OverpassService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final LugarSeguroRepository lugarSeguroRepository;

    public OverpassService(LugarSeguroRepository lugarSeguroRepository) {
        this.lugarSeguroRepository = lugarSeguroRepository;
    }

    public List<LocalizacaoResponse> buscarLocaisSeguros(LocalizacaoRequestDTO localizacaoRequest) {
        double latitude = localizacaoRequest.latitude();
        double longitude = localizacaoRequest.longitude();

        String query = String.format("""
            [out:json];
            (
              node["amenity"~"school|police"](around:2000,%f,%f);
            );
            out body;
            """, latitude, longitude);

        String url = "https://overpass-api.de/api/interpreter";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> request = new HttpEntity<>("data=" + query, headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);
        Map<String, Object> responseBody = response.getBody();

        List<LocalizacaoResponse> dtos = new ArrayList<>();

        if (responseBody != null && responseBody.containsKey("elements")) {
            List<Map<String, Object>> elementos = (List<Map<String, Object>>) responseBody.get("elements");

            for (Map<String, Object> elemento : elementos) {
                Double lat = (Double) elemento.get("lat");
                Double lon = (Double) elemento.get("lon");
                Map<String, Object> tags = (Map<String, Object>) elemento.get("tags");
                String nome = tags != null ? (String) tags.getOrDefault("name", "Local seguro") : "Local seguro";

                // Cria o LugarSeguro e salva no banco
                LugarSeguro lugar = new LugarSeguro(nome, lat, lon);
                lugar = lugarSeguroRepository.save(lugar);

                // Adiciona na lista de DTOs com id do banco
                dtos.add(new LocalizacaoResponse(lugar.getId(), lat, lon, nome));
            }
        }

        return dtos;
    }
}
