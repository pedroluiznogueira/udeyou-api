package br.com.cadastro.cliente.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "wishlists")
    List<Curso> cursos;

    @OneToOne(cascade = CascadeType.ALL)
    private Usuario usuario;

    public Wishlist() {
    }

    public Wishlist(Long id, Usuario usuario) {
        this.id = id;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
