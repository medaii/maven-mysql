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
@Table(name="clen_trvala_adresa")
public class TrvaleBydliste {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name = "id_osoby", insertable = false, updatable = false)	
	private int id_member;
	
	@Column(name="adresa")
	private String adresa;

	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="id_osoby")
	private Member member;
	
	public TrvaleBydliste() {
	}
	
	public TrvaleBydliste(String adresa) {
		this.adresa = adresa;
	}
	public TrvaleBydliste(int id, int id_member, String adresa) {
		this.id = id;
		this.id_member = id_member;
		this.adresa = adresa;
	}

	public TrvaleBydliste(int id_member, String adresa) {
		this.id_member = id_member;
		this.adresa = adresa;
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
		
	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@Override
	public String toString() {
		return "Adresa [id=" + id + ", id_member=" + id_member + ", adresa=" + adresa + "]";
	}
		
}
