package clases;

public class Tienda {

	private String nif;
	private String nombreTienda;
	private String direccion;
	private String poblacion;
	private String provincia;
	private int codPostal;

	public Tienda() {

	}

	public Tienda(String nif, String nombreTienda, String direccion, String poblacion, String provincia,
			int codPostal) {

		this.nif = nif;
		this.nombreTienda = nombreTienda;
		this.direccion = direccion;
		this.poblacion = poblacion;
		this.provincia = provincia;
		this.codPostal = codPostal;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNombreTienda() {
		return nombreTienda;
	}

	public void setNombreTienda(String nombreTienda) {
		this.nombreTienda = nombreTienda;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public int getCodPostal() {
		return codPostal;
	}

	public void setCodPostal(int codPostal) {
		this.codPostal = codPostal;
	}

	@Override
	public String toString() {
		return "NOMBRE: " + getNombreTienda() + " NIF: " + getNif();
	}

}
