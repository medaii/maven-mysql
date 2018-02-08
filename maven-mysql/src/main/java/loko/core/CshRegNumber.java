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
@Table(name="cshRegC")
public class CshRegNumber {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "id_osoby", insertable = false, updatable = false)	
	private int id_member;
	
	@Column(name="regCislo")
	private String regCislo;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="id_osoby")
	private Member member;

	public CshRegNumber() {
	}

	public CshRegNumber(int id, int id_member, String regCislo, Member member) {
		this.id = id;
		this.id_member = id_member;
		this.regCislo = regCislo;
		this.member = member;
	}

	public CshRegNumber(int id_member, String regCislo, Member member) {
		this.id_member = id_member;
		this.regCislo = regCislo;
		this.member = member;
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

	public String getRegCislo() {
		return regCislo;
	}

	public void setRegCislo(String regCislo) {
		this.regCislo = regCislo;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@Override
	public String toString() {
		return "CshRegNumber [id=" + id + ", regCislo=" + regCislo + ", member=" + member + "]";
	}
	
}
