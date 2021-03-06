package br.com.cadastro.cliente.controller;

import br.com.cadastro.cliente.domain.*;
import br.com.cadastro.cliente.repository.CursoRepository;
import br.com.cadastro.cliente.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/curso")
@CrossOrigin("*")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @Autowired
    private CursoRepository cursoRepository;

    // todos os cursos
    @GetMapping("cursos")
    public List<Curso> getCursos() {
        return cursoService.getCursos();
    }

    // curso por id
    @GetMapping("find/{id}")
    public ResponseEntity<Curso> findById(@PathVariable ("id") Long id) {
        Curso curso = cursoService.findCursoById(id);
        return new ResponseEntity<Curso>(curso, HttpStatus.OK);
    }

    // encontrar os cursos por professor
    @PostMapping("find-by-professor")
    public ResponseEntity<List<Curso>> findByProfessor(@RequestBody Professor professor){
        List<Curso> cursos = cursoService.getCursosByProfessorId(professor.getId());
        return new ResponseEntity<List<Curso>>(cursos, HttpStatus.OK);
    }

    // cadastrar curso
    @PostMapping("create")
    public ResponseEntity<Curso> insertCurso(@RequestBody Curso curso) {
        Curso cur = cursoService.insertCurso(curso);
        return new ResponseEntity<Curso>(cur, HttpStatus.OK);
    }

    // excluir curso
    @DeleteMapping("delete/{id}")
    public ResponseEntity<StatusResponse> dropCurso(@PathVariable ("id") Long idCurso) {
        StatusResponse statusResponse = cursoService.dropCurso(idCurso);
        return new ResponseEntity<StatusResponse>(statusResponse, HttpStatus.OK);
    }

    // atualizar curso
    @PutMapping("update")
    public ResponseEntity<StatusResponse> updateCurso(@RequestBody Curso curso) {
        StatusResponse statusResponse = cursoService.updateCurso(curso);
        return new ResponseEntity<StatusResponse>(statusResponse, HttpStatus.OK);
    }

    // fazer pesquisa por cursos
    @PostMapping("search")
    public ResponseEntity<List<Curso>> pesquisar(@RequestBody Curso cursoPesq) {
        List<Curso> cursos = cursoService.pesquisar(cursoPesq.getTitulo());
        return new ResponseEntity<List<Curso>>(cursos, HttpStatus.OK);
    }

    @PostMapping("add-curso-wish")
    public ResponseEntity<StatusResponse> addCursoWish(@RequestBody Cursowishlist cw) {
        StatusResponse resp = cursoService.addCursoWish(cw);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping("cursos/wish")
    public ResponseEntity<List<Long>> getCursosWish(@RequestBody Wishlist wishlist) {
        List<Long> ids = cursoRepository.findCursosIdByWishlistId(wishlist.getId());
        return new ResponseEntity<>(ids, HttpStatus.OK);
    }

    @PostMapping("cursos/wish/all")
    public ResponseEntity<List<Curso>> getCursosWishAll(@RequestBody List<Long> idsCursos) {
        List<Curso> cursos = cursoService.getCursosWishAll(idsCursos);
        return new ResponseEntity<>(cursos, HttpStatus.OK);
    }

    @PostMapping("add-curso-pedido")
    public ResponseEntity<StatusResponse> addCursoPedido(@RequestBody Cursopedido cp) {
        StatusResponse resp = new StatusResponse();
        CursopedidoDTO cpDTO = new CursopedidoDTO();

        for (Curso curso : cp.getCursos()) {
            cpDTO.setCurso(curso);
            cpDTO.setPedido(cp.getPedido());
            resp = cursoService.addCursoPedido(cpDTO);
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping("cursos/pedido")
    public ResponseEntity<List<Long>> getCursosPedidos(@RequestBody List<Pedido> pedidos) {
        List<Long> ids = cursoService.findCursosIdByPedidosIds(pedidos);
        return new ResponseEntity<>(ids, HttpStatus.OK);
    }

    @PostMapping("cursos/pedido/all")
    public ResponseEntity<List<Curso>> getCursosPedidosAll(@RequestBody List<Long> idsCursos) {
        List<Curso> cursos = cursoService.getCursosPedidosAll(idsCursos);
        return new ResponseEntity<>(cursos, HttpStatus.OK);
    }
}
