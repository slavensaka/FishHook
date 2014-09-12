package com.fish.riba;

public class Riba {

	int id;
	String created_at;
	String vrsta;
	float tezina;
	String tehnika;
	float duljina;
	String vrstarijeke;
	String upecao;
	String dogadaj;
	String opis;

	byte[] slikajedan;

	// byte[] slikadva;
	// byte[] slikatri;

	public Riba() {
	}

	public Riba(int id) {
		this.id = id;
	}



	public Riba(int id, String vrsta, float tezina, String tehnika,
			float duljina, String vrstarijeke, String upecao, String opis) {
		this.id = id;
		this.vrsta = vrsta;
		this.tezina = tezina;
		this.tehnika = tehnika;
		this.duljina = duljina;
		this.vrstarijeke = vrstarijeke;
		this.upecao = upecao;

		this.opis = opis;
	}

	// public Riba(int id, String vrsta, float tezina,
	// String tehnika, float duljina, String vrstarijeke, String upecao,
	// String dogadaj, String opis, byte[] slikajedan, byte[] slikadva,
	// byte[] slikatri) {
	// this.id = id;
	// this.vrsta = vrsta;
	// this.tezina = tezina;
	// this.tehnika = tehnika;
	// this.duljina = duljina;
	// this.vrstarijeke = vrstarijeke;
	// this.upecao = upecao;
	// this.dogadaj = dogadaj;
	// this.opis = opis;
	// this.slikajedan = slikajedan;
	// this.slikadva = slikadva;
	// this.slikatri = slikatri;
	// }
	// Ovaj
	public Riba(String vrsta, float tezina, String tehnika, float duljina,
			String vrstarijeke, String upecao, String dogadaj, String opis,
			byte[] slikajedan
	// , byte[] slikadva,byte[] slikatri
	) {
		this.vrsta = vrsta;
		this.tezina = tezina;
		this.tehnika = tehnika;
		this.duljina = duljina;
		this.vrstarijeke = vrstarijeke;
		this.upecao = upecao;
		this.dogadaj = dogadaj;
		this.opis = opis;
		this.slikajedan = slikajedan;
		// this.slikadva = slikadva;
		// this.slikatri = slikatri;
	}

	// public Riba(String vrsta, float tezina,
	// String tehnika, float duljina, String vrstarijeke, String upecao,
	// String dogadaj, String opis) {
	// this.vrsta = vrsta;
	// this.tezina = tezina;
	// this.tehnika = tehnika;
	// this.duljina = duljina;
	// this.vrstarijeke = vrstarijeke;
	// this.upecao = upecao;
	// this.dogadaj = dogadaj;
	// this.opis = opis;
	// }

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public void setCreatedAt(String created_at) {
		this.created_at = created_at;
	}

	public String getCreatedAt() {
		return this.created_at;
	}

	public void setVrsta(String vrsta) {
		this.vrsta = vrsta;
	}

	public String getvrsta() {
		return this.vrsta;
	}

	public void setTezina(float tezina) {
		this.tezina = tezina;
	}

	public float getTezina() {
		return this.tezina;
	}

	public void setTehnika(String tehnika) {
		this.tehnika = tehnika;
	}

	public String getTehnika() {
		return this.tehnika;
	}

	public void setDuljina(float duljina) {
		this.duljina = duljina;
	}

	public float getDuljina() {
		return this.duljina;
	}

	public void setVrstarijeke(String vrstarijeke) {
		this.vrstarijeke = vrstarijeke;
	}

	public String getVrstarijeke() {
		return this.vrstarijeke;
	}

	public void setUpecao(String upecao) {
		this.upecao = upecao;
	}

	public String getUpecao() {
		return this.upecao;
	}

	public void setDogadaj(String dogadaj) {
		this.dogadaj = dogadaj;
	}

	public String getDogadaj() {
		return this.dogadaj;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public String getOpis() {
		return this.opis;
	}

	public void setSlikajedan(byte[] slikajedan) {
		this.slikajedan = slikajedan;
	}

	public byte[] getSlikajedan() {
		return this.slikajedan;
	}

	// public byte[] getSlikadva() {
	// return this.slikadva;
	// }
	//
	// public void setSlikadva(byte[] slikadva) {
	// this.slikadva = slikadva;
	// }
	//
	// public byte[] getSlikatri() {
	// return this.slikatri;
	// }
	//
	// public void setSlikatri(byte[] slikatri) {
	// this.slikatri = slikatri;
	// }

}
