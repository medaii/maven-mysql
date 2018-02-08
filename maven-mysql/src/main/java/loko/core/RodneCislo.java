package loko.core;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="clen_rodne_cislo")
public class RodneCislo {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "id_osoby", insertable = false, updatable = false)	
	private int id_member;
	
	@Column(name="rodne_cislo")
	private String rodne_cislo;

	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="id_osoby")
	private Member member;
	
	public RodneCislo() {
	}

	public RodneCislo(int id, int id_member, String rodne_cislo) {
		this.id = id;
		this.id_member = id_member;
		this.rodne_cislo = rodne_cislo;
	}

	public RodneCislo(int id_member, String rodne_cislo) {
		this.id_member = id_member;
		this.rodne_cislo = rodne_cislo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_member() {
		return id_member;
	}

	public void setId_member(int id_member) {
		this.id_member = id_member;
	}

	public String getRodne_cislo() {
		return rodne_cislo;
	}

	public void setRodne_cislo(String rodne_cislo) {
		this.rodne_cislo = rodne_cislo;
	}
		
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@Override
	public String toString() {
		return "RodneCislo [id=" + id + ", id_member=" + id_member + ", rodne_cislo=" + rodne_cislo + "]";
	}
		
}
