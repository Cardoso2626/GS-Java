package br.com.fiap.gsjava.service;


import br.com.fiap.gsjava.dto.LembreteRequest;
import br.com.fiap.gsjava.dto.LembreteRequestDTO;
import br.com.fiap.gsjava.dto.LembreteResponse;
import br.com.fiap.gsjava.model.Lembrete;
import br.com.fiap.gsjava.model.Usuario;
import br.com.fiap.gsjava.repository.LembreteRepository;
import br.com.fiap.gsjava.repository.UsuarioRepository;
import org.apache.catalina.authenticator.SavedRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class LembreteService {

    private final LembreteRepository lembreteRepository;

    public LembreteService(LembreteRepository lembreteRepository) {
        this.lembreteRepository = lembreteRepository;

    }

    //Método para criar lembrete
    public LembreteResponse criarLembrete(LembreteRequestDTO lembreteRequestDTO) {
        Lembrete lembrete = new Lembrete();
        lembrete.setDataHora(lembreteRequestDTO.getDataHora());
        lembrete.setMensagem(lembreteRequestDTO.getMensagem());

        lembrete = lembreteRepository.save(lembrete);

        return new LembreteResponse(
                lembrete.getMensagem(),
                lembrete.getDataHora(),
                null
        );
    }

    //Método para deletar lembrete
    public void deletarLembrete(Long id){
        lembreteRepository.deleteById(id);
    }

    //Método para puxar lembretes a partir do emai do usuario
    public List<LembreteResponse> buscarLembretesPorEmail(String email){
        List<Lembrete> lembretes = lembreteRepository.findByUsuarioEmail(email);

        return lembretes.stream()
                .map(lembrete -> new LembreteResponse(
                        lembrete.getMensagem(),
                        lembrete.getDataHora(),
                        lembrete.getUsuario().getId()
                ))
                .collect(Collectors.toList());
    }




}
