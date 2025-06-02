package br.com.fiap.gsjava.controller;



import br.com.fiap.gsjava.dto.LembreteRequestDTO;
import br.com.fiap.gsjava.dto.LembreteResponse;
import br.com.fiap.gsjava.model.Lembrete;
import br.com.fiap.gsjava.service.LembreteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/lembretes")
public class LembreteController {

    private final LembreteService lembreteService;

    public LembreteController(LembreteService lembreteService) {
        this.lembreteService = lembreteService;
    }

    //CRIAR LEMBRETE
    @PostMapping()
    public ResponseEntity<LembreteResponse> post(@RequestBody LembreteRequestDTO lembreteRequestDTO) {
        LembreteResponse response = lembreteService.criarLembrete(lembreteRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    //DELETAR
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        lembreteService.deletarLembrete(id);
    }


}
