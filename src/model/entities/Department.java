package model.entities;

import java.io.Serializable;
import java.util.Objects;

public class Department implements Serializable{ 
	// Serializable = Permitir que os objetos sejam transformados em sequencia de bytes
	private static final long serialVersionUID = 1L;
	
	// Atributos
	private Integer id;
	private String name;
	
	// Construtor
	public Department () {
		
	}

	public Department(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	// M�todos Acessores
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// M�todos Comparativos (serem comparados pelo conte�do, n�o pela referencia de ponteiros
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Department other = (Department) obj;
		return Objects.equals(id, other.id);
	}

	// M�todos Espec�ficos
	@Override
	public String toString() {
		return "Department [id=" + id + ", name=" + name + "]";
	}	

}
