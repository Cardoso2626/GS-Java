package br.com.fiap.gsjava.controller;


import br.com.fiap.gsjava.dto.LocalizacaoRequestDTO;
import br.com.fiap.gsjava.dto.LocalizacaoResponse;
import br.com.fiap.gsjava.model.Localizacao;
import br.com.fiap.gsjava.service.NominatimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/localizacoes")
public class LocalizacaoController {

    @Autowired
    private NominatimService nominatimService;

    @PostMapping("/criar")
    public ResponseEntity<LocalizacaoResponse> criarLocalizacao(@RequestBody LocalizacaoRequestDTO dto) {
        Localizacao localizacao = new Localizacao();
        localizacao.setLatitude(dto.latitude());
        localizacao.setLongitude(dto.longitude());
        // id será gerado automaticamente

        LocalizacaoResponse resposta = nominatimService.criarResposta(localizacao);
        return ResponseEntity.ok(resposta);
    }

}
