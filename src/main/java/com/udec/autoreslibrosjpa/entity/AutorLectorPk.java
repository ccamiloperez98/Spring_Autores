package com.udec.autoreslibrosjpa.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Embeddable
public class AutorLectorPk implements Serializable{

	private static final long serialVersionUID = 1L;

	    @ManyToOne
	    @JoinColumn(name = "id_autor", nullable = false)
		private Autor autor;
	        
	    @ManyToOne
	    @JoinColumn(name = "id_lector", nullable = false)
	    private Lector lector;

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((autor == null) ? 0 : autor.hashCode());
			result = prime * result + ((lector == null) ? 0 : lector.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			AutorLectorPk other = (AutorLectorPk) obj;
			if (autor == null) {
				if (other.autor != null)
					return false;
			} else if (!autor.equals(other.autor))
				return false;
			if (lector == null) {
				if (other.lector != null)
					return false;
			} else if (!lector.equals(other.lector))
				return false;
			return true;
		}

		
}
