package br.com.fiap.gsjava.controller;


import br.com.fiap.gsjava.repository.LembreteRepository;
import br.com.fiap.gsjava.service.LembreteService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/lembretes")
public class LembreteController {

    private final LembreteRepository lembreteService;

    public LembreteController(LembreteService lembreteService) {
        this.lembreteService = lembreteService;
    }

    @PostMapping("/{idUsuario}")
    public ResponseEntity<LembreteResponse> criarLembrete(
            @PathVariable Long idUsuario,
            @RequestBody LembreteRequestDTO dto) {
        return ResponseEntity.ok(lembreteService.criarLembrete(dto, idUsuario));
    }

    @PutMapping
    public ResponseEntity<LembreteResponse> atualizarLembrete(@RequestBody LembreteRequest dto) {
        return ResponseEntity.ok(lembreteService.atualizarLembrete(dto));
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<List<LembreteResponse>> listarLembretes(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(lembreteService.listarLembretesPorUsuario(idUsuario));
    }
}
