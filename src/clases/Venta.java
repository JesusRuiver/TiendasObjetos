package clases;

public class Venta {

	private String nif;
	private String nombreArticulo;
	private Fabricante fabricante;
	private Articulo articulo;
	private int codFabricante;
	private int peso;
	private String categoria;
	private String fechaVenta;
	private int unidadesVendidas;

	public Venta() {

	}

	public Venta(String nif, String nombreArticulo, Fabricante fabricante, Articulo articulo, int codFabricante,
			int peso, String categoria, String fechaVenta, int unidadesVendidas) {

		this.nif = nif;
		this.nombreArticulo = nombreArticulo;
		this.fabricante = fabricante;
		this.articulo = articulo;
		this.codFabricante = codFabricante;
		this.peso = peso;
		this.categoria = categoria;
		this.fechaVenta = fechaVenta;
		this.unidadesVendidas = unidadesVendidas;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
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

	public String getFechaVenta() {
		return fechaVenta;
	}

	public void setFechaVenta(String fechaVenta) {
		this.fechaVenta = fechaVenta;
	}

	public int getUnidadesVendidas() {
		return unidadesVendidas;
	}

	public void setUnidadesVendidas(int unidadesVendidas) {
		this.unidadesVendidas = unidadesVendidas;
	}

	public Fabricante getFabricante() {
		return fabricante;
	}

	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}

	public String getNombreArticulo() {
		return nombreArticulo;
	}

	public void setNombreArticulo(String nombreArticulo) {
		this.nombreArticulo = nombreArticulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}

	public Articulo getArticulo() {
		return articulo;
	}

	@Override
	public String toString() {
		return "Venta [nif=" + nif + ", nombreArticulo=" + nombreArticulo + ", fabricante=" + fabricante + ", articulo="
				+ articulo + ", codFabricante=" + codFabricante + ", peso=" + peso + ", categoria=" + categoria
				+ ", fechaVenta=" + fechaVenta + ", unidadesVendidas=" + unidadesVendidas + "]";
	}

}
