package br.com.example.domain.model;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "notafiscal")
public class NotaFiscal implements Serializable{
    
	private static final long serialVersionUID = -9022465056307525885L;
	private int id;
    private String name;
    private Set<Emissao> emissoes;

    public NotaFiscal() {
    
    }
    
    public NotaFiscal(String name) {
        this.name = name;
        this.emissoes = new HashSet<Emissao>();
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(mappedBy = "notaFiscal", cascade = CascadeType.ALL, orphanRemoval = true)
	public Set<Emissao> getEmissoes() {
		return emissoes;
	}

	public void setEmissoes(Set<Emissao> itens) {
		this.emissoes = itens;
	}
}
