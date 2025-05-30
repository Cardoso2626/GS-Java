package br.com.fiap.gsjava.service;


import br.com.fiap.gsjava.repository.LembreteRepository;
import org.springframework.stereotype.Service;


@Service
public class LembreteService {

    private final LembreteRepository lembreteRepository;
    private final UsuarioRepository usuarioRepository;
    private final FCMService fcmService;

    public LembreteService(LembreteRepository lembreteRepository,
                           UsuarioRepository usuarioRepository,
                           FCMService fcmService) {
        this.lembreteRepository = lembreteRepository;
        this.usuarioRepository = usuarioRepository;
        this.fcmService = fcmService;
    }

    public LembreteResponse criarLembrete(LembreteRequestDTO dto, Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Lembrete lembrete = new Lembrete();
        lembrete.setMensagem(dto.mensagem());
        lembrete.setDataHora(dto.dataHora());
        lembrete.setUsuario(usuario);
        lembrete = lembreteRepository.save(lembrete);
        return new LembreteResponse(lembrete.getId(), lembrete.getMensagem(), lembrete.getDataHora(), usuario.getId());
    }

    public LembreteResponse atualizarLembrete(LembreteRequest dto) {
        Usuario usuario = usuarioRepository.findById(dto.idUsuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Lembrete lembrete = lembreteRepository.findById(dto.id())
                .orElseThrow(() -> new RuntimeException("Lembrete não encontrado"));
        lembrete.setMensagem(dto.mensagem());
        lembrete.setDataHora(dto.dataHora());
        lembrete.setUsuario(usuario);
        lembrete = lembreteRepository.save(lembrete);
        return new LembreteResponse(lembrete.getId(), lembrete.getMensagem(), lembrete.getDataHora(), usuario.getId());
    }

    public List<LembreteResponse> listarLembretesPorUsuario(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        List<Lembrete> lembretes = lembreteRepository.findByUsuario(usuario);
        return lembretes.stream()
                .map(l -> new LembreteResponse(l.getId(), l.getMensagem(), l.getDataHora(), usuario.getId()))
                .toList();
    }

    @Scheduled(fixedRate = 60000)
    public void verificarLembretes() {
        LocalDateTime agora = LocalDateTime.now();
        List<Lembrete> lembretesPendentes = lembreteRepository.findByDataHoraBefore(agora);
        for (Lembrete lembrete : lembretesPendentes) {
            if (lembrete.getUsuario() != null && lembrete.getUsuario().getTokenFCM() != null) {
                fcmService.enviarNotificacao(lembrete.getUsuario().getTokenFCM(), "Lembrete!", lembrete.getMensagem());
            }
        }
    }
}
