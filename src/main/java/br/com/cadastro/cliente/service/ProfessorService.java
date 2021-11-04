package br.com.cadastro.cliente.service;

import br.com.cadastro.cliente.domain.Professor;
import br.com.cadastro.cliente.domain.StatusResponse;
import br.com.cadastro.cliente.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    public List<Professor> getProfessores(){
        return professorRepository.findAll();
    }

    public Professor insertProfessor(Professor novoProfessor) {
        List<Professor> professores = professorRepository.findAll();

        for (Professor prof : professores) {
            if (novoProfessor.getUsuario().getId() == prof.getUsuario().getId()) {
                return null;
            }
        }

        professorRepository.save(novoProfessor);
        return novoProfessor;

    }

    public Professor findProfessorById(Long id){
        return professorRepository.findById(id).get();
    }

    public StatusResponse dropProfessor(Long id) {

        if (professorRepository.findById(id) == null){
            return new StatusResponse("Cliente não existe", "erro");
        }

        professorRepository.deleteById(id);
        return new StatusResponse("Cliente deletado com sucesso", "sucesso");
    }

    public StatusResponse updateProfessor(Professor novoProfessor) {

        Professor professor = professorRepository.findById(novoProfessor.getId()).get();

        if (novoProfessor.getNome() == "" || novoProfessor.getEmail() == "" || novoProfessor.getSobrenome() == ""){
            return new StatusResponse("Dados invalidos", "erro");
        }

        professor.setNome(novoProfessor.getNome());
        professor.setSobrenome(novoProfessor.getSobrenome());
        professor.setEmail(novoProfessor.getEmail());

        professorRepository.save(professor);
        return new StatusResponse("Cliente alterado com sucesso", "sucesso");
    }

    public List<Professor> pesquisar(String nome) {
        return professorRepository.findServicoByTitulo(nome);
    }

    public Professor getByEmail(String email) {
        return professorRepository.findByEmail(email);
    }
}
