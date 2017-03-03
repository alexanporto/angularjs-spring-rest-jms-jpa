package br.com.example.domain.model;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "mercadoria")
public class Mercadoria implements Serializable{

	private static final long serialVersionUID = 5769415974415185455L;
	private int id;
    private String name;
    private Set<Emissao> itens;

    
    public Mercadoria(){

    }

    public Mercadoria(String name){
        
    	this.name = name;
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
	
	@OneToMany(mappedBy = "mercadoria")
	public Set<Emissao> getItens() {
		return itens;
	}

	public void setItens(Set<Emissao> itens) {
		this.itens = itens;
	}
}
