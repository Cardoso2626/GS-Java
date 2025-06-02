package br.com.fiap.gsjava.service;


import br.com.fiap.gsjava.dto.LembreteRequest;
import br.com.fiap.gsjava.dto.LembreteRequestDTO;
import br.com.fiap.gsjava.dto.LembreteResponse;
import br.com.fiap.gsjava.dto.LembreteResponseDTO;
import br.com.fiap.gsjava.mapper.LembreteMapper;
import br.com.fiap.gsjava.model.Lembrete;
import br.com.fiap.gsjava.model.Usuario;
import br.com.fiap.gsjava.repository.LembreteRepository;
import br.com.fiap.gsjava.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class LembreteService {

    private final LembreteRepository lembreteRepository;
    private final UsuarioRepository usuarioRepository;
    private final LembreteMapper lembreteMapper;

    public LembreteService(LembreteRepository lembreteRepository, UsuarioRepository usuarioRepository, LembreteMapper lembreteMapper) {
        this.lembreteRepository = lembreteRepository;
        this.usuarioRepository = usuarioRepository;
        this.lembreteMapper = lembreteMapper;

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

    //Atualiza lembrete pelo email pegando o id do lembrete específico
    public LembreteResponse atualizarPorEmailELembreteId(LembreteRequest request) {

        Usuario usuario = usuarioRepository.findOptionalByEmail(request.emailUsuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));


        Lembrete lembrete = lembreteRepository.findById(request.idLembrete())
                .orElseThrow(() -> new RuntimeException("Lembrete não encontrado"));


        if (!lembrete.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("Lembrete não pertence ao usuário informado");
        }


        lembrete.setMensagem(request.mensagem());
        lembrete.setDataHora(request.dataHora());

        Lembrete atualizado = lembreteRepository.save(lembrete);
        return new LembreteResponse(atualizado.getMensagem(), atualizado.getDataHora(), atualizado.getUsuario().getId());
    }

    //MÉTODO PARA LISTAR OS LEMBRETES
    public Page<LembreteResponseDTO> listarLembretesPaginado(Pageable pageable) {
        Page<Lembrete> lembretePage = lembreteRepository.findAll(pageable);
        return lembretePage.map(lembreteMapper::toResponseDTO);
    }
}





