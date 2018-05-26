package clases;

public class Venta {

	private String nif;
	private String articulo;
	private int codFabricante;
	private int peso;
	private String categoria;
	private String fechaVenta;
	private int unidadesVendidas;

	public Venta() {

	}

	public Venta(String nif, String articulo, int codFabricante, int peso, String categoria, String fechaVenta,
			int unidadesVendidas) {

		this.nif = nif;
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

	public String getArticulo() {
		return articulo;
	}

	public void setArticulo(String articulo) {
		this.articulo = articulo;
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

	@Override
	public String toString() {
		return "Venta [nif=" + nif + ", articulo=" + articulo + ", codFabricante=" + codFabricante + ", peso=" + peso
				+ ", categoria=" + categoria + ", fechaVenta=" + fechaVenta + ", unidadesVendidas=" + unidadesVendidas
				+ "]";
	}

}
