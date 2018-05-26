package clases;

public class Articulo {
	
	private String nombreArticulo;
	private Fabricante fabricante;
	private int codFabricante;
	private int peso;
	private String categoria;
	private double precioVenta;
	private double precioCosto;
	private int existencias;
	
	public Articulo() {
		
	}

	public Articulo(String nombreArticulo, Fabricante fabricante, int codFabricante, int peso, String categoria,
			double precioVenta, double precioCosto, int existencias) {
		
		this.nombreArticulo = nombreArticulo;
		this.fabricante = fabricante;
		this.codFabricante = codFabricante;
		this.peso = peso;
		this.categoria = categoria;
		this.precioVenta = precioVenta;
		this.precioCosto = precioCosto;
		this.existencias = existencias;
	}

	public String getNombreArticulo() {
		return nombreArticulo;
	}

	public void setNombreArticulo(String nombreArticulo) {
		this.nombreArticulo = nombreArticulo;
	}

	public Fabricante getFabricante() {
		return fabricante;
	}

	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}

	public int getCodFabricante() {
		return codFabricante;
	}

	public void setCodFabricante(int codFabricante) {
		this.codFabricante = codFabricante;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public double getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(double precioVenta) {
		this.precioVenta = precioVenta;
	}

	public double getPrecioCosto() {
		return precioCosto;
	}

	public void setPrecioCosto(double precioCosto) {
		this.precioCosto = precioCosto;
	}

	public int getExistencias() {
		return existencias;
	}

	public void setExistencias(int existencias) {
		this.existencias = existencias;
	}

	@Override
	public String toString() {
		return "Articulo [nombreArticulo=" + nombreArticulo + ", fabricante=" + fabricante + ", codFabricante="
				+ codFabricante + ", peso=" + peso + ", categoria=" + categoria + ", precioVenta=" + precioVenta
				+ ", precioCosto=" + precioCosto + ", existencias=" + existencias + "]";
	}

	
}
